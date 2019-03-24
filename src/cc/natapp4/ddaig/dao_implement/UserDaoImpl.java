package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.FourthLevelDao;
import cc.natapp4.ddaig.dao_interface.MinusFirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.dao_interface.ThirdLevelDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Resource(name="minusFirstLevelDao")
	private MinusFirstLevelDao  minusFirstLevelDao;
	@Resource(name="zeroLevelDao")
	private ZeroLevelDao  zeroLevelDao;
	@Resource(name="firstLevelDao")
	private FirstLevelDao  firstLevelDao;
	@Resource(name="secondLevelDao")
	private SecondLevelDao  secondLevelDao;
	@Resource(name="thirdLevelDao")
	private ThirdLevelDao  thirdLevelDao;
	@Resource(name="fourthLevelDao")
	private FourthLevelDao  fourthLevelDao;
	
	/*
	 * Spring的DI诸如的注解引用法，name属性指定向目标数据成员中DI注入哪个Bean（Spring容器中的Bean），
	 * 就用applicationContext.xml 配置文件中声明的Bean的name来作为该属性的参数值
	 */
	@Resource(name = "hibernateTemplate")
	private HibernateTemplate template;

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.template;
	}

	@Override
	public User queryByOpenId(String openID) {

		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where openid =?", openID);

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<User> queryByTagName(String tagName) {

		List<User> list = (List<User>) this.getHibernateTemplate()
				.find("from User u inner join fetch u.grouping g where g.tag =? ", tagName);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<Member> getManagers(String tag, String levelTag, String lid) {
		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = this.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = this.getUserByUsername(principal);
			}
		}
		
		// 开始查询
		List<Member> list = null;
		if (isAdmin) {
			// admin用户，在最大范围内查找目标tag的用户
			switch (tag) {
			// 根据“不在其位，不谋其政”的管理原则，admin只能看到 街道/unreal/common 这三个tag或者是三个tag的总和
			case "minus_first":
				list = (List<Member>) this.getHibernateTemplate().find(
						"from Member m where m.grouping.tag=? and m.minusFirstLevel=?",
						"minus_first", null);
				break;
			case "common":
				list = (List<Member>) this.getHibernateTemplate().find(
						"from Member m where m.grouping.tag=? and m.minusFirstLevel=?",
						"common", null);
				break;
			case "unreal":
				list = (List<Member>) this.getHibernateTemplate().find(
						"from Member m where m.grouping.tag=? and m.minusFirstLevel=?",
						"unreal", null);
				break;
			default:
				list = (List<Member>) this.getHibernateTemplate().find(
						"from Member m where m.grouping.tag in(?,?,?) and m.minusFirstLevel=null",
						"minus_first", "common", "unreal");
				break;
			}
		} else {
			// 非admin用户，根据其lid在有限范围内查找目标tag的用户
			if ("minus_first".equals(levelTag)) {
				// 执行人是街道层级管理者
				switch (tag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 社区/unreal/common 这三个tag或者是三个tag的总和
				case "zero":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.minusFirstLevel mfl where m.grouping.tag=? and mfl.mflid=? and m.zeroLevel=null",
							"zero", lid);
					break;
				case "common":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.minusFirstLevel mfl where m.grouping.tag=? and mfl.mflid=? and m.zeroLevel=null",
							"common", lid);
					break;
				default: // 查询所有tag
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.minusFirstLevel mfl where m.grouping.tag in(?,?) and mfl.mflid=? and m.zeroLevel=null",
							"zero", "common", lid);
					break;
				}
			} else if ("zero".equals(levelTag)) {
				// 执行人是社区层级管理者
				switch (tag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第一/unreal/common 这三个tag或者是三个tag的总和
				case "first":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.zeroLevel zl where m.grouping.tag=? and zl.zid=? and m.firstLevel=null",
							"first", lid);
					break;
				case "common":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.zeroLevel zl where m.grouping.tag=? and zl.zid=? and m.firstLevel=null",
							"common", lid);
					break;
				default: // 查询所有tag
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.zeroLevel zl where m.grouping.tag in(?,?) and zl.zid=? and m.firstLevel=null",
							"first", "common", lid);
					break;
				}
			} else if ("first".equals(levelTag)) {
				// 执行人是第一层级管理者
				switch (tag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第二/unreal/common 这三个tag或者是三个tag的总和
				case "second":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.firstLevel fl where m.grouping.tag=? and fl.flid=? and m.secondLevel=null",
							"second", lid);
					break;
				case "common":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.firstLevel fl where m.grouping.tag=? and fl.flid=? and m.secondLevel=null",
							"common", lid);
					break;
				default: // 查询所有tag
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.firstLevel fl where m.grouping.tag in(?,?) and fl.flid=? and m.secondLevel=null",
							"second", "common", lid);
					break;
				}
			} else if ("second".equals(levelTag)) {
				// 执行人是第二层级管理者
				switch (tag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第三/unreal/common 这三个tag或者是三个tag的总和
				case "third":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.secondLevel scl where m.grouping.tag=? and scl.scid=? and m.thirdLevel=null",
							"third", lid);
					break;
				case "common":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.secondLevel scl where m.grouping.tag=? and scl.scid=? and m.thirdLevel=null",
							"common", lid);
					break;
				default: // 查询所有tag
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.secondLevel scl where m.grouping.tag in(?,?) and scl.scid=? and m.thirdLevel=null",
							"third", "common", lid);
					break;
				}
			} else if ("third".equals(levelTag)) {
				// 执行人是第三层级管理者
				switch (tag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第四/unreal/common 这三个tag或者是三个tag的总和
				case "fourth":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.thirdLevel tl where m.grouping.tag=? and tl.thid=? and m.fourthLevel=null",
							"fourth", lid);
					break;
				case "common":
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.thirdLevel tl where m.grouping.tag=? and tl.thid=? and m.fourthLevel=null",
							"common",lid);
					break;
				default:
					list = (List<Member>) this.getHibernateTemplate().find(
							"from Member m inner join fetch m.thirdLevel tl where m.grouping.tag in(?,?) and tl.thid=? and m.fourthLevel=null",
							"fourth", "common",lid);
					break;
				}
			} else {
				// 执行人是其他层级（例如第四层级等）的管理者,但是第四层级管理者是看不到menu.jsp页面上“人员派任”入口的，所以不可能调用到这里
			}
		}
		return list;
	}

	@Override
	public User getUserByUsername(String username) {

		try {
			/*
			 * 如果传入一个在数据库中不存在的username，就会爆出关于ArrayList的异常，这是因为查不到的数据为null
			 * 然后把null放入到ArrayList而产生的，因此需要一个捕获异常的机制来处理，否则该错误会遗祸到上层中去。
			 */
			List<?> list = template.find("from User u where username=?", username);
			return (User) list.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<User> getChildrenLevelUsers(String tag, String lid) {
		List<User> users = null;
		switch (tag) {
		case "minus_first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=? and m.zeroLevel<>null",
					lid);
			break;
		case "zero":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=? and m.firstLevel<>null",
					lid);
			break;
		case "first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=? and m.secondLevel<>null",
					lid);
			break;
		case "second":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=? and m.thirdLevel<>null",
					lid);
			break;
		case "third":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=? and m.fourthLevel<>null",
					lid);
			break;
		case "fourth":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.fourthLevel fl where fl.foid=?",
					lid);
			break;
		}
		return users;
	}

	@Override
	public List<User> getAllLevelUsers(String tag, String lid) {
		List<User> users = null;
		switch (tag) {
		case "minus_first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=?",
					lid);
			break;
		case "zero":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=?",
					lid);
			break;
		case "first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=?",
					lid);
			break;
		case "second":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=?",
					lid);
			break;
		case "third":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=?",
					lid);
			break;
		case "fourth":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.fourthLevel fl where fl.foid=?",
					lid);
			break;
		}
		return users;
	}
	
	
}
