package cc.natapp4.ddaig.service_implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.bean.health.Bean4InitSelector;
import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage;
import cc.natapp4.ddaig.bean.health.ReturnMessage4InitSelector;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.domain.health.Sample4EnclosedScale;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

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
	@Autowired
	private MinusFirstLevelService minusFirstLevelService;
	@Autowired
	private ZeroLevelService zeroLevelService;
	@Autowired
	private FirstLevelService firstLevelService;
	@Autowired
	private SecondLevelService secondLevelService;
	@Autowired
	private ThirdLevelService thirdLevelService;
	@Autowired
	private FourthLevelService fourthLevelService;
	
	/*
	 * TODO 下方涉及负责与微信公众号交互的weixinService4Setting等Service由于需要在Servlet环境的支持
	 * 因此在JUnit测试有关User/Manager/Member的Service→Dao层的时候请先注解掉这部分内容
	 */
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl wxService4Setting;

	@Override
	protected BaseDao<User> getBaseDao() {
		return dao;
	}

	@Override
	public User queryByOpenId(String openID) {
		return dao.queryByOpenId(openID);
	}
	
	@Override
	public User queryByPhone(String phoneNum) {
		return dao.queryByPhone(phoneNum);
	}

	@Override
	public List<User> queryByTagName(String tagName) {

		List<User> list = dao.queryByTagName(tagName);
		return list;
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
		String qrcode = QRCodeUtils.createUserQR(uid);
		t.setQrcode(qrcode);

		// 至此后台新建用户所需要的数据都已经放入到bean中了，最后再提交父类，调用UserDao保存到数据库
		super.save(t);
	}

	@Override
	public User queryByUsername(String username) {
		return dao.queryByUsername(username);
	}
	

	
	@Override
	public ReturnMessage4CountandCreateFirstPage getCountandCreateFirstPage4InitLaypage(String targetTag,
			String targetLid, int targetPageNum, int pageItemNumLimit, String whereFrom, String groupTag) {

		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();
		List<User> firstPageUsers = new ArrayList<User>();
		long total = 0;
		
		if("userList4currentLevel".equals(whereFrom)) {
			// 如果请求者来自userList且目标获取对象和当前登录的层级管理者相同，则只获取当前层级的非直辖成员
			firstPageUsers = this.dao.getChildrenLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
			total = this.dao.getChildrenLevelUsersCount(targetTag, targetLid);
		}else if("userList4childLevel".equals(whereFrom)) {
			// 如果请求者来自userList且目标获取对象和当前登录的层级管理者不同（目标是当前层级的子孙），则获取目标层级的所有成员(直辖+非直辖)
			firstPageUsers = this.dao.getAllLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
			total = this.dao.getAllLevelUsersCount(targetTag, targetLid);
		}else if("managerList".equals(whereFrom)) {
			// 如果请求来在managerList，则只获取
			firstPageUsers = this.dao.getSelfLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit, groupTag);
			total = this.dao.getSelfLevelUsersCount(targetTag, targetLid, targetTag);
		}
		
		result.setCount(total);
		result.setUsers(firstPageUsers);
		return result;
	}

	@Override
	public ReturnMessage4CountandCreateFirstPage getUsersByPageLimit(String targetTag, String targetLid,
			int targetPageNum, int pageItemNumLimit, String whereFrom, String groupTag) {

		
		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();
		List<User> users = new ArrayList<User>();;

		if("userList4currentLevel".equals(whereFrom)) {
			users = dao.getChildrenLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
		}else if("userList4childLevel".equals(whereFrom)) {
			users = dao.getAllLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
		}else if("managerList".equals(whereFrom)){
			users = dao.getSelfLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit, groupTag);
		}
		
		result.setUsers(users);
		return result;
	}
	

	@Override
	public ReturnMessage4InitSelector initSelector(String currentLevelTag, String currentLevelId) {
		ReturnMessage4InitSelector result = new ReturnMessage4InitSelector();
		List<Bean4InitSelector> total = new ArrayList<Bean4InitSelector>();
		/*
		 * 前端负责使用当前方法给出的JSON数据源的时候，会检查每个Bean中的children的length长度
		 * 因此为防止出现空指针异常，哪怕默认项不含有子对象，也要给出一个空数组（Bean中就是List容器） 而不能是null
		 */
		Bean4InitSelector defaultBean = new Bean4InitSelector("", "0", new ArrayList<Bean4InitSelector>());

		if ("admin".equals(currentLevelTag)) {
			// 当前操作者为系统管理员，获取所有层级数据
			List<MinusFirstLevel> minusFirstLevels = minusFirstLevelService.queryEntities();
			for (int a = 0; a < minusFirstLevels.size(); a++) {
				Bean4InitSelector minusFirst = new Bean4InitSelector();
				minusFirst.setLabel(minusFirstLevels.get(a).getName());
				minusFirst.setValue("minusFirst_" + minusFirstLevels.get(a).getMflid());
				List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
				Set<ZeroLevel> zeroLevels = minusFirstLevels.get(a).getChildren();

				if (null != zeroLevels && zeroLevels.size() > 0)
					children4MinusFirst.add(defaultBean);

				for (ZeroLevel b : zeroLevels) {
					Bean4InitSelector zero = new Bean4InitSelector();
					zero.setLabel(b.getName());
					zero.setValue("zero_" + b.getZid());
					List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
					Set<FirstLevel> firstLevels = b.getChildren();

					if (null != firstLevels && firstLevels.size() > 0)
						children4Zero.add(defaultBean);

					for (FirstLevel c : firstLevels) {
						Bean4InitSelector first = new Bean4InitSelector();
						first.setLabel(c.getName());
						first.setValue("first_" + c.getFlid());
						List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
						Set<SecondLevel> secondLevels = c.getChildren();

						if (null != secondLevels && secondLevels.size() > 0)
							children4First.add(defaultBean);

						for (SecondLevel d : secondLevels) {
							Bean4InitSelector second = new Bean4InitSelector();
							second.setLabel(d.getName());
							second.setValue("second_" + d.getScid());
							List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
							Set<ThirdLevel> thirdLevels = d.getChildren();

							if (null != thirdLevels && thirdLevels.size() > 0)
								children4Second.add(defaultBean);

							for (ThirdLevel e : thirdLevels) {
								Bean4InitSelector third = new Bean4InitSelector();
								third.setLabel(e.getName());
								third.setValue("third_" + e.getThid());
								List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();
								Set<FourthLevel> fourthLevels = e.getChildren();

								if (null != fourthLevels && fourthLevels.size() > 0)
									children4Third.add(defaultBean);

								for (FourthLevel f : fourthLevels) {
									Bean4InitSelector fourth = new Bean4InitSelector();
									fourth.setLabel(f.getName());
									fourth.setValue("fourth_" + f.getFoid());
									fourth.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针

									children4Third.add(fourth);
								}

								third.setChildren(children4Third);
								children4Second.add(third);
							}

							second.setChildren(children4Second);
							children4First.add(second);
						}

						first.setChildren(children4First);
						children4Zero.add(first);
					}

					zero.setChildren(children4Zero);
					children4MinusFirst.add(zero);
				}

				minusFirst.setChildren(children4MinusFirst);
				total.add(minusFirst);
			}

		} else if ("minus_first".equals(currentLevelTag)) {
			// 当前层级管理者为minus_first
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(currentLevelId);

			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();

			if (null != minusFirstLevel.getChildren() && minusFirstLevel.getChildren().size() > 0)
				children4MinusFirst.add(defaultBean);

			for (ZeroLevel zeroLevel : minusFirstLevel.getChildren()) {
				Bean4InitSelector zeroBean = new Bean4InitSelector();
				zeroBean.setLabel(zeroLevel.getName());
				zeroBean.setValue("zero_" + zeroLevel.getZid());
				List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();

				if (null != zeroLevel.getChildren() && zeroLevel.getChildren().size() > 0)
					children4Zero.add(defaultBean);

				for (FirstLevel firstLevel : zeroLevel.getChildren()) {
					Bean4InitSelector firstBean = new Bean4InitSelector();
					firstBean.setLabel(firstLevel.getName());
					firstBean.setValue("first_" + firstLevel.getFlid());
					List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

					if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
						children4First.add(defaultBean);

					for (SecondLevel secondLevel : firstLevel.getChildren()) {
						Bean4InitSelector secondBean = new Bean4InitSelector();
						secondBean.setLabel(secondLevel.getName());
						secondBean.setValue("second_" + secondLevel.getScid());
						List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

						if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
							children4Second.add(defaultBean);

						for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
							Bean4InitSelector thirdBean = new Bean4InitSelector();
							thirdBean.setLabel(thirdLevel.getName());
							thirdBean.setValue("third_" + thirdLevel.getThid());
							List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

							if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
								children4Third.add(defaultBean);

							for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
								Bean4InitSelector fourthBean = new Bean4InitSelector();
								fourthBean.setLabel(fourthLevel.getName());
								fourthBean.setValue("fourth_" + fourthLevel.getFoid());
								fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
								children4Third.add(fourthBean);
							}

							thirdBean.setChildren(children4Third);
							children4Second.add(thirdBean);
						}

						secondBean.setChildren(children4Second);
						children4First.add(secondBean);
					}

					firstBean.setChildren(children4First);
					children4Zero.add(firstBean);
				}

				zeroBean.setChildren(children4Zero);
				children4MinusFirst.add(zeroBean);
			}

			minusFirstBean.setChildren(children4MinusFirst);
			total.add(minusFirstBean);

		} else if ("zero".equals(currentLevelTag)) {

			// 当前层级管理者为zero
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(currentLevelId);
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();

			if (null != zeroLevel.getChildren() && zeroLevel.getChildren().size() > 0)
				children4Zero.add(defaultBean);

			for (FirstLevel firstLevel : zeroLevel.getChildren()) {
				Bean4InitSelector firstBean = new Bean4InitSelector();
				firstBean.setLabel(firstLevel.getName());
				firstBean.setValue("first_" + firstLevel.getFlid());
				List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

				if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
					children4First.add(defaultBean);

				for (SecondLevel secondLevel : firstLevel.getChildren()) {
					Bean4InitSelector secondBean = new Bean4InitSelector();
					secondBean.setLabel(secondLevel.getName());
					secondBean.setValue("second_" + secondLevel.getScid());
					List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

					if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
						children4Second.add(defaultBean);

					for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
						Bean4InitSelector thirdBean = new Bean4InitSelector();
						thirdBean.setLabel(thirdLevel.getName());
						thirdBean.setValue("third_" + thirdLevel.getThid());
						List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

						if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
							children4Third.add(defaultBean);

						for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
							Bean4InitSelector fourthBean = new Bean4InitSelector();
							fourthBean.setLabel(fourthLevel.getName());
							fourthBean.setValue("fourth_" + fourthLevel.getFoid());
							fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
							children4Third.add(fourthBean);
						}

						thirdBean.setChildren(children4Third);
						children4Second.add(thirdBean);
					}

					secondBean.setChildren(children4Second);
					children4First.add(secondBean);
				}

				firstBean.setChildren(children4First);
				children4Zero.add(firstBean);
			}
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("first".equals(currentLevelTag)) {

			// 当前层级管理者为First
			FirstLevel firstLevel = firstLevelService.queryEntityById(currentLevelId);
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

			if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
				children4First.add(defaultBean);

			for (SecondLevel secondLevel : firstLevel.getChildren()) {
				Bean4InitSelector secondBean = new Bean4InitSelector();
				secondBean.setLabel(secondLevel.getName());
				secondBean.setValue("second_" + secondLevel.getScid());
				List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

				if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
					children4Second.add(defaultBean);

				for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
					Bean4InitSelector thirdBean = new Bean4InitSelector();
					thirdBean.setLabel(thirdLevel.getName());
					thirdBean.setValue("third_" + thirdLevel.getThid());
					List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

					if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
						children4Third.add(defaultBean);

					for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
						Bean4InitSelector fourthBean = new Bean4InitSelector();
						fourthBean.setLabel(fourthLevel.getName());
						fourthBean.setValue("fourth_" + fourthLevel.getFoid());
						fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
						children4Third.add(fourthBean);
					}

					thirdBean.setChildren(children4Third);
					children4Second.add(thirdBean);
				}

				secondBean.setChildren(children4Second);
				children4First.add(secondBean);
			}
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("second".equals(currentLevelTag)) {

			// 当前层级管理者为Second
			SecondLevel secondLevel = secondLevelService.queryEntityById(currentLevelId);
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

			if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
				children4Second.add(defaultBean);

			for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
				Bean4InitSelector thirdBean = new Bean4InitSelector();
				thirdBean.setLabel(thirdLevel.getName());
				thirdBean.setValue("third_" + thirdLevel.getThid());
				List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

				if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
					children4Third.add(defaultBean);

				for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
					Bean4InitSelector fourthBean = new Bean4InitSelector();
					fourthBean.setLabel(fourthLevel.getName());
					fourthBean.setValue("fourth_" + fourthLevel.getFoid());
					fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
					children4Third.add(fourthBean);
				}

				thirdBean.setChildren(children4Third);
				children4Second.add(thirdBean);
			}
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("third".equals(currentLevelTag)) {

			// 当前层级管理者为Third

			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(currentLevelId);
			Bean4InitSelector thirdBean = new Bean4InitSelector();
			thirdBean.setLabel(thirdLevel.getName());
			thirdBean.setValue("third_" + thirdLevel.getThid());
			List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

			if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
				children4Third.add(defaultBean);

			for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
				Bean4InitSelector fourthBean = new Bean4InitSelector();
				fourthBean.setLabel(fourthLevel.getName());
				fourthBean.setValue("fourth_" + fourthLevel.getFoid());
				fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
				children4Third.add(fourthBean);
			}
			thirdBean.setChildren(children4Third);

			SecondLevel secondLevel = thirdLevel.getParent();
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
			children4Second.add(thirdBean);
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("fourth".equals(currentLevelTag)) {
			// 当前层级管理者为Fourth
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(currentLevelId);
			Bean4InitSelector fourthBean = new Bean4InitSelector();
			fourthBean.setLabel(fourthLevel.getName());
			fourthBean.setValue("fourth_" + fourthLevel.getFoid());
			fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针

			ThirdLevel thirdLevel = fourthLevel.getParent();
			Bean4InitSelector thirdBean = new Bean4InitSelector();
			thirdBean.setLabel(thirdLevel.getName());
			thirdBean.setValue("third_" + thirdLevel.getThid());
			List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();
			children4Third.add(fourthBean);
			thirdBean.setChildren(children4Third);

			SecondLevel secondLevel = thirdLevel.getParent();
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
			children4Second.add(thirdBean);
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);
		}

		result.setDataSource(total);
		result.setDefaultValue(new ArrayList<String>()); // 前端picker-extend.js会检查数组深度，因此不能为null，需要给出一个空数组
		return result;
	}

	
	
	
	
	
	
	
	@Override
	public List<User> getManagers(String targetLevelTag, String targetLevelId, String groupTag) {
		return dao.getSelfLevelUsers(targetLevelTag, targetLevelId, groupTag);
	}


	
}
