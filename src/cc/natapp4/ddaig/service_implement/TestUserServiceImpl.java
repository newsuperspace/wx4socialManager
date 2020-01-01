package cc.natapp4.ddaig.service_implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Service("testUserService")
/**
 * 该类是用来供测试类（TestExchangeService）使用的，与真正的userService类的区别就是去除了weixinService4Setting的DI注入
 * 这样在测试类中，通过Spring容器获取userService的时候，不会级联地初始化weixinService4Setting这个Bean，由于该Bean初始化时需要从
 * ServletContext域中获取config（有关公众号）的配置对象，而JUnit测试是在线下运行的，不涉及服务器线上运行，因此也就不存在ServletContext域
 * 自然也就获取不到config对象，自然也就不能成功初始化weixinService4Setting这个Bean，从而造成JUnit测试失败。
 * 
 * @author Administrator
 *
 */
@Lazy(true)
public class TestUserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource(name = "userDao")
	private UserDao dao;
	@Resource(name = "groupingService")
	private GroupingService groupingService;

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
	public void checkRealName(String openID, String username, String sex, String birth, String phone)
			throws WeixinExceptionWhenCheckRealName {
		
		// TODO 这里还是有问题的，因为必须要先检测是否有后台添加用户，这里为了尽快检测系统可用性而选择只有微信公众号添加用户这一种方式
		// TODO 因此这里需要在UserDao中添加根据cardid查找用户的方法，然后确认实名认证是否是相同用户
		User user = dao.queryByOpenId(openID);
		user.setPhone(phone);
		user.setUsername(username);
		if("1".equals(sex)){
			user.setSex("男");
		}else{
			user.setSex("女");
		}
		user.setBirth(birth);
		
		// 开始计算年龄
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		long time = 0;
		try {
			time = formatter.parse(birth).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*
		 * ★★★ 这里必须要注意，如果我们希望计算的是一个long类型的数学表达式，就像下面这种乘法式
		 * 那么必须在其中一个数字（通常是第一个数字）后面加上L标志，否则Java默认按照int型计算结果
		 * 所以如果不加L，那么计算出来的值最多只有10位，而long类型有11位
		 */
		int age  =  (int) Math.floor((System.currentTimeMillis() - time)/(1000L*60*60*24*365));
		user.setAge(age);
		// 变更该用户的标签
		List<Grouping> list = this.groupingService.queryEntities();
		for (Grouping g : list) {
			// 实名认证后的用户其member.grouping.tag = common
			if (g.getTag().equals("common")) {
				// 遍历待实名认证用户的所有member，找到默认member并修改其grouping.tag从unreal到common
				for(Member m: user.getMembers()){
					if(null==m.getMinusFirstLevel()){
						m.setGrouping(g);
					}
				}
				this.update(user);
			}
		}
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
//		String qrcode = QRCodeUtils.createUserQR(uid);
//		t.setQrcode(qrcode);

		// 至此后台新建用户所需要的数据都已经放入到bean中了，最后再提交父类，调用UserDao保存到数据库
		super.save(t);
	}

	/**
	 * 在公众号初始化阶段(WeiXinAction类中的recall()中调用mpService4Setting.InitPlatform()
	 * 实现公众号初始化) 调用的新建用户的方法，它不会生成qrcode图片
	 * 之所以不在初始化阶段直接使用UserServiceImpl的save()方法,是由于save()
	 * 方法中调用QRCodeUtils中的createUserQR()方法的时候会根据用户的uid生成个人专属qrcode
	 * 但是在创建保存qrcode的图片路径的时候需要用到ServletActionContext.getServletContext()
	 * 是依据TLS（线程局部存储技术）实现的，需要线程ID，但是公众号初始化是创建的新线程
	 * 因此通过ServletActionContext.getServletContext()根据新线程的ID（该线程不是servlet线程）
	 * 自然也就找不到ServletContext对象，从而爆出NullPointerException。
	 * 所以对于初始化平台时，新建用户的需求，只能向数据库新建除QRCODE的数据，等之后平台初始化成功后，在通过一个方法（
	 * batchCreateUserQR()）批量根据数据库中的每个用户的uid补创建qrcode
	 */
	public void saveInInit(User t) {
		// 由于需要使用uid来创建qrcode，所以这里手动uid并传入到bean中
		String uid = UUID.randomUUID().toString();
		t.setUid(uid);
		// 至此后台新建用户所需要的数据都已经放入到bean中了，最后再提交父类，调用UserDao保存到数据库
		super.save(t);
	}

	/**
	 * 批处理：批量生成用户数据库中所有用户的QRCODE 图片 适合初始化公众号后，或者应用新部署到服务器，旧的用户QRCODE已经丢失的时候
	 * 由在后台调用该方法，这样就可以根据每个用户的uid重新生成对应的QRCODE图片
	 */
	public void batchCreateUserQR() {
		List<User> list = this.queryEntities();
		for (User u : list) {
			String uid = u.getUid();
			String qr = QRCodeUtils.createUserQR(uid);
			u.setQrcode(qr);
			// 由于Hibernate的二级缓存机制，使得哦们根本不需要显式调用update()方法，就能自动向数据库保存修改了 ★
			// this.update(u);
		}
		System.out.println("全部用户的uid已重新生成！");
	}

	@Override
	public List<User> queryByTagName(String tagName) {

		List<User> list = dao.queryByTagName(tagName);
		return list;
	}

	@Override
	public List<Member> getManagers(String tag, String levelTag, String lid) {
		return dao.getManagers(tag, levelTag, lid);
	}

	@Override
	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}

	@Override
	public List<User> getChildrenLevelUsers(String tag, String lid) {
		return dao.getChildrenLevelUsers(tag, lid);
	}

	@Override
	public List<User> getAllLevelUsers(String tag, String lid) {
		return dao.getAllLevelUsers(tag, lid);
	}

	@Override
	public User queryByPhone(String phoneNum) {
		return dao.queryByPhone(phoneNum);
	}

	@Override
	public long getAllLevelUsersCount(String tag, String lid) {
		return dao.getAllLevelUsersCount(tag, lid);
	}

	@Override
	public List<User> getAllLevelUsersByPage(String tag, String lid, int targetPageNum, int limit) {
		return dao.getAllLevelUsersByPage(tag, lid, targetPageNum, limit);
	}

}
