package cc.natapp4.ddaig.service_implement;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.sym.Name;

import cc.natapp4.ddaig.action.AjaxAction4weixin.Result4CheckRealName;
import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.exception.WxErrorException;

@Service("userService")
/*
 * 懒加载非常重要，因为当前类也是bean，也同样通过@Component注解将当前类IOC与Spring容器管理之下
 * 又因为当前bean类体中又依靠DI注入需要其他bean的支持（通过@Resource注解实现注入过程），因此在实例化当前Handler类这个
 * Bean的时候，又需要预先实例化DI注入的bean。
 * 
 * 因为，WeixinService4XXX（用来与公众号服务器直接交互的Service）是依靠java-weixin-tool.jar提供的API实现的，
 * 而在这个jar包体系下
 * 需要有一个包含目标公众号的config对象注入到公众号MP开发的入口service（也就是WeixinService4XXX的父类的父类——
 * WxMpServiceImpl）中， 而当前工程，
 * config的配置工作是在Servlet启动监听器中实现的并在实例化WeixinService4XXX的时候通过ServletContext域获取，
 * 所以要想让WxMpServiceImpl的所有子类正常启动就必须Servlet容器先启动。
 * 
 * 因此你会发现所有Service（ActivityService、UserService、GroupingService等）都是bean，
 * 并且其中DI注入了一个或多个WeixinService4XXX。
 * 
 * Spring容器有一个习惯就是在Spring容器初始化的时候会预先自动将所有Bean都实例化一次，可Spring容器的初始化要早于Servlet初始化，
 * 这就造成 当Spring初始化一个Bean的时候，如果这个Bean的DI注入链条中存在WeixinService4XXX，
 * 那么就势必会先初始化这些WeixinService4XXX，但在
 * 构建WeixinService4XXX实例的时候又需要先从ServletContext域中获取config，但此时Servlet容器还没启动，
 * 因此就得不到config，从而爆出错。
 * 
 * 解决的办法是——在每个WeixinService4XXX类体内，创建config，从而只需对WeixinService4XXX类添加@Lazy注解，
 * 告知Spring容器不初始化 次bean即可。
 * 或者，是像当前工程这样，凡事DI注入链条中存在WeixinService4XXX类的，从源头就添加@Lazy注解，
 * 从而禁止整个DI链条上所有bean的实例化。
 * 
 */
@Lazy(true)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource(name = "userDao")
	private UserDao dao;
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	/*
	 * TODO 
	 * 下方涉及负责与微信公众号交互的weixinService4Setting等Service由于需要在Servlet环境的支持
	 * 因此在JUnit测试有关User/Manager/Member的Service→Dao层的时候请先注解掉这部分内容
	 */
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl wxService4Setting;

	// TODO 一下这些DI注入式专门给 isAdmin()这个测试方法使用的，正式部署程序的使用应该弃用
	@Resource(name="roleService")
	private RoleService  roleService;

	@Override
	protected BaseDao<User> getBaseDao() {
		return dao;
	}

	@Override
	public User queryByOpenId(String openID) {
		return dao.queryByOpenId(openID);
	}

	/**
	 * 实名认证
	 */
	@Override
	public void checkRealName(String openID, String username, String cardID, String address, String phone)
			throws WeixinExceptionWhenCheckRealName {

		// TODO 这里还是有问题的，因为必须要先检测是否有后台添加用户，这里为了尽快检测系统可用性而选择只有微信公众号添加用户这一种方式
		// TODO 因此这里需要在UserDao中添加根据cardid查找用户的方法，然后确认实名认证是否是相同用户
		User user = dao.queryByOpenId(openID);
		user.setCardid(cardID);
		user.setAddress(address);
		user.setPhone(phone);
		user.setUsername(username);

		// TODO[测试用]  正式发布时请删除此处调用
		if(isAdmin(user, username, cardID, address, phone)){
			return;
		}
		
		List<Grouping> list = this.groupingService.queryEntities();
		for (Grouping g : list) {
			if (g.getTag().equals("common_user")) {

				// 新建User对象到本地数据库保存
				user.setGrouping(g); // 新建User一定要与Grouping进行绑定
				this.update(user);

				// 最后在公众号中设置"非认证用户"的tag就可以了
				String[] ids = { openID };
				try {
					wxService4Setting.getUserTagService().batchTagging(g.getTagid(), ids);
				} catch (WxErrorException e) {
					e.printStackTrace();
					throw new WeixinExceptionWhenCheckRealName();
				}
			}
		}
	}

	/**
	 * 【TODO 测试用】
	 * 实名认证的后门程序，如果在测试环境中我的手机上不了网，或者全部admin用户都消失了，则也就没人能拥有最高权限了
	 * 这个时候只要 String username, String cardID, String address, String phone  这四个从前端提交来的字段都是admin
	 * 那么就可以将该用户自动设置成admin角色用户
	 * 
	 * return  true表示已经将该用户设定成admin并且也绑定成了community_user ，一切都做妥当了，不需要checkRealName()多管闲事了
	 *   false 表示本方法没有发挥什么作用，将新建用户的工作交还给checkRealName()的剩余代码。
	 */
	private boolean isAdmin(User user,String username, String cardID, String address, String phone){
		
		if("admin".equals(username)&&"admin".equals(cardID)&&"admin".equals(address)&&"admin".equals(phone)){
			List<Grouping> list = this.groupingService.queryEntities();
			for (Grouping g : list) {
				if (g.getTag().equals("community_user")) {

					// 新建User对象到本地数据库保存
					user.setGrouping(g); // 新建User一定要与Grouping进行绑定
					// 直接升级成admin角色
					Role role = roleService.queryEntityById("402881eb6086931701608693244c0000");
					user.setRole(role);
					this.update(user);

					// 最后在公众号中设置"非认证用户"的tag就可以了
					String[] ids = { user.getOpenid() };
					try {
						wxService4Setting.getUserTagService().batchTagging(g.getTagid(), ids);
						return true;
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * 类洗浴新鲜活动Activity的子类个性化方法，bacause需要自动生成用来分别各个用户的unique的QRCODE
	 * 因此这里需要一个覆盖父类的子类方法
	 * 
	 * 非初始化阶段（），所调用的新建用户的save，它会顺便生成每个用户专属的qrcode
	 */
	@Override
	public void save(User t) {
		// 由于需要使用uid来创建qrcode，所以这里手动uid并传入到bean中
		String uid = UUID.randomUUID().toString();
		t.setUid(uid);
		// 根据uid生成qrcode，并将 qrcode/1/12/xxx.gif 这样的相对路径字符串传入到bean中
		String qrcode = QRCodeUtils.createUserQR(uid);
		t.setQrcode(qrcode);

		// 至此后台新建用户所需要的数据都已经放入到bean中了，最后再提交父类，调用UserDao保存到数据库
		super.save(t);
	}

	/**
	 * 在公众号初始化阶段(WeiXinAction类中的recall()中调用mpService4Setting.InitPlatform()实现公众号初始化)
	 * 调用的新建用户的方法，它不会生成qrcode图片
	 * 之所以不在初始化阶段直接使用UserServiceImpl的save()方法,是由于save()方法中调用QRCodeUtils中的createUserQR()方法的时候会根据用户的uid生成个人专属qrcode
	 * 但是在创建保存qrcode的图片路径的时候需要用到ServletActionContext.getServletContext() 是依据TLS（线程局部存储技术）实现的，需要线程ID，但是公众号初始化是创建的新线程
	 * 因此通过ServletActionContext.getServletContext()根据新线程的ID（该线程不是servlet线程）自然也就找不到ServletContext对象，从而爆出NullPointerException。
	 * 所以对于初始化平台时，新建用户的需求，只能向数据库新建除QRCODE的数据，等之后平台初始化成功后，在通过一个方法（batchCreateUserQR()）批量根据数据库中的每个用户的uid补创建qrcode
	 */
	public void saveInInit(User t) {
		// 由于需要使用uid来创建qrcode，所以这里手动uid并传入到bean中
		String uid = UUID.randomUUID().toString();
		t.setUid(uid);
		// 至此后台新建用户所需要的数据都已经放入到bean中了，最后再提交父类，调用UserDao保存到数据库
		super.save(t);
	}
	
	/**
	 * 批处理：批量生成用户数据库中所有用户的QRCODE 图片
	 * 适合初始化公众号后，或者应用新部署到服务器，旧的用户QRCODE已经丢失的时候
	 * 由在后台调用该方法，这样就可以根据每个用户的uid重新生成对应的QRCODE图片
	 */
	public void batchCreateUserQR(){
		List<User> list = this.queryEntities();
		for(User u: list){
			String uid = u.getUid();
			String qr = QRCodeUtils.createUserQR(uid);
			u.setQrcode(qr);
			// 由于Hibernate的二级缓存机制，使得哦们根本不需要显式调用update()方法，就能自动向数据库保存修改了 ★
//			this.update(u);
		}
		System.out.println("全部用户的uid已重新生成！");
	}

	@Override
	public List<User> queryByTagName(String tagName) {

		List<User> list = dao.queryByTagName(tagName);
		return list;
	}

	@Override
	public List<User> getManagers(String tag) {
		return dao.getManagers(tag);
	}

	@Override
	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}


}
