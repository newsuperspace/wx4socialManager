package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
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
import cc.natapp4.ddaig.hibernate.CommonHibernateCallback4QueryUser;
import cc.natapp4.ddaig.hibernate.PageLimitHibernateCallback4QueryUser;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Resource(name = "minusFirstLevelDao")
	private MinusFirstLevelDao minusFirstLevelDao;
	@Resource(name = "zeroLevelDao")
	private ZeroLevelDao zeroLevelDao;
	@Resource(name = "firstLevelDao")
	private FirstLevelDao firstLevelDao;
	@Resource(name = "secondLevelDao")
	private SecondLevelDao secondLevelDao;
	@Resource(name = "thirdLevelDao")
	private ThirdLevelDao thirdLevelDao;
	@Resource(name = "fourthLevelDao")
	private FourthLevelDao fourthLevelDao;

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
	public User queryByPhone(String phoneNum) {

		List<User> users = (List<User>) template.find("from User u where u.phone=?", phoneNum);
		if (null != users && users.size() > 0) {
			return users.get(0);
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
	public User queryByUsername(String username) {

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
	public List<User> getAllLevelUsers(String tag, String lid) {
		List<User> users = null;
		switch (tag) {
		case "admin":
			users = this.queryEntities();
			break;
		case "minus_first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=?",
					lid);
			break;
		case "zero":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=?", lid);
			break;
		case "first":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=?", lid);
			break;
		case "second":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=?",
					lid);
			break;
		case "third":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=?", lid);
			break;
		case "fourth":
			users = (List<User>) this.getHibernateTemplate().find(
					"from User u inner join fetch u.members m inner join fetch m.fourthLevel fl where fl.foid=?", lid);
			break;
		}
		return users;
	}

	@Override
	public long getAllLevelUsersCount(String tag, String lid) {

		long count = 0;
//		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
//		Query query = session.createSQLQuery("select count(*) from user");
//		count = ((Number)query.uniqueResult()).intValue();
		List<User> users = getAllLevelUsers(tag, lid);
		count = users.size();

		return count;
	}

	@Override
	public List<User> getAllLevelUsersByPage(String targetTag, String targetLid, int targetPageNum,
			int pageItemNumLimit) {

		// 根据当前操作者层级选择hql语句
		String hql = "";
		switch (targetTag) {
		case "admin":
			hql = "from User";
			break;
		case "minus_first":
			hql = "from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=?";
			break;
		case "zero":
			hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=?";
			break;
		case "first":
			hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=?";
			break;
		case "second":
			hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=?";
			break;
		case "third":
			hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=?";
			break;
		case "fourth":
			hql = "from User u inner join fetch u.members m inner join fetch m.fourthLevel fl where fl.foid=?";
			break;
		}

		// 按顺序准备用于hql语句所需要的参数数组
		Object[] params = new Object[] { targetLid };

		List<User> list = (List<User>) this.getHibernateTemplate()
				.execute(new PageLimitHibernateCallback4QueryUser(hql, params, targetPageNum, pageItemNumLimit));
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<User> getSelfLevelUsers(String targetLevelTag, String targetLevelId, String groupTag) {

		String hql = "";
		// 按顺序准备用于hql语句所需要的参数数组
		Object[] params = null;

		// 如果targetLevelTag表明当前操作者时admin，则
		if ("admin".equals(targetLevelTag)) {
			// 先准备好通用的hql语句
			hql = "from User u inner join fetch u.members m where m.grouping.tag=? and m.minusFirstLevel=null";
			switch (groupTag) {
			// 根据“不在其位，不谋其政”的管理原则，admin只能看到 街道/unreal/common 这三个tag或者是三个tag的总和
			case "minus_first":
				params = new Object[] { "minus_first" };
				break;
			case "common":
				params = new Object[] { "common" };
				break;
			case "unreal":
				params = new Object[] { "unreal" };
				break;
			default:
				hql = "from User u inner join fetch u.members m where m.grouping.tag in(?,?,?) and m.minusFirstLevel=null";
				params = new Object[] { "minus_first", "common", "unreal" };
				break;
			}
		} else {
			// 非admin用户，根据其lid在有限范围内查找目标用户
			if ("minus_first".equals(targetLevelTag)) {
				// 执行人是街道层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where m.grouping.tag=? and mfl.mflid=? and m.zeroLevel=null";
				switch (groupTag) {
				// 社区/unreal/common 这三个tag或者是三个tag的总和
				case "zero":
					params = new Object[] { "zero", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch  u.members m inner join fetch m.minusFirstLevel mfl where m.grouping.tag in(?,?) and mfl.mflid=? and m.zeroLevel=null";
					params = new Object[] { "zero", "common", targetLevelId };
					break;
				}
			} else if ("zero".equals(targetLevelTag)) {
				// 执行人是社区层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where m.grouping.tag=? and zl.zid=? and m.firstLevel = null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第一/unreal/common 这三个tag或者是三个tag的总和
				case "first":
					params = new Object[] { "first", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where m.grouping.tag in(?,?) and zl.zid=? and m.firstLevel = null";
					params = new Object[] { "first", "common", targetLevelId };
					break;
				}
			} else if ("first".equals(targetLevelTag)) {
				// 执行人是第一层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where m.grouping.tag=? and fl.flid=? and m.secondLevel=null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第二/unreal/common 这三个tag或者是三个tag的总和
				case "second":
					params = new Object[] { "second", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where m.grouping.tag in(?,?) and fl.flid=? and m.secondLevel=null";
					params = new Object[] { "second", "common", targetLevelId };
					break;
				}
			} else if ("second".equals(targetLevelTag)) {
				// 执行人是第二层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where m.grouping.tag=? and scl.scid=? and m.thirdLevel = null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第三/unreal/common 这三个tag或者是三个tag的总和
				case "third":
					params = new Object[] { "third", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where m.grouping.tag in(?,?) and scl.scid=? and m.thirdLevel = null";
					params = new Object[] { "third", "common", targetLevelId };
					break;
				}
			} else if ("third".equals(targetLevelTag)) {
				// 执行人是第三层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where m.grouping.tag=? and tl.thid=? and m.fourthLevel=null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第四/unreal/common 这三个tag或者是三个tag的总和
				case "fourth":
					params = new Object[] { "fourth", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default:
					hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where m.grouping.tag in(?,?) and tl.thid=? and m.fourthLevel=null";
					params = new Object[] { "fourth", "common", targetLevelId };
					break;
				}
			} else if ("fourth".equals(targetLevelTag)) {
				hql = "from User u inner join fetch u.members m inner join fetch m.fourthLevel fol where fol.foid=?";
				params = new Object[] { targetLevelId };
			}
		}

		List<User> users = this.getHibernateTemplate().execute(new CommonHibernateCallback4QueryUser(hql, params));
		return users;
	}

	@Override
	public long getSelfLevelUsersCount(String targetLevelTag, String targetLevelId, String groupTag) {

		List<User> selfLevelUsers = this.getSelfLevelUsers(targetLevelTag, targetLevelId, groupTag);
		if (null == selfLevelUsers) {
			return 0;
		} else {
			return selfLevelUsers.size();
		}
	}

	@Override
	public List<User> getSelfLevelUsersByPage(String targetLevelTag, String targetLevelId, int targetPageNum,
			int pageItemNumLimit, String groupTag) {

		String hql = "";
		// 按顺序准备用于hql语句所需要的参数数组
		Object[] params = null;

		// 如果targetLevelTag表明当前操作者时admin，则
		if ("admin".equals(targetLevelTag)) {
			// 先准备好通用的hql语句
			hql = "from User u inner join fetch u.members m where m.grouping.tag=? and m.minusFirstLevel=null";
			switch (groupTag) {
			// 根据“不在其位，不谋其政”的管理原则，admin只能看到 街道/unreal/common 这三个tag或者是三个tag的总和
			case "minus_first":
				params = new Object[] { "minus_first" };
				break;
			case "common":
				params = new Object[] { "common" };
				break;
			case "unreal":
				params = new Object[] { "unreal" };
				break;
			default:
				hql = "from User u inner join fetch u.members m where m.grouping.tag in(?,?,?) and m.minusFirstLevel=null";
				params = new Object[] { "minus_first", "common", "unreal" };
				break;
			}
		} else {
			// 非admin用户，根据其lid在有限范围内查找目标用户
			if ("minus_first".equals(targetLevelTag)) {
				// 执行人是街道层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where m.grouping.tag=? and mfl.mflid=? and m.zeroLevel=null";
				switch (groupTag) {
				// 社区/unreal/common 这三个tag或者是三个tag的总和
				case "zero":
					params = new Object[] { "zero", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch  u.members m inner join fetch m.minusFirstLevel mfl where m.grouping.tag in(?,?) and mfl.mflid=? and m.zeroLevel=null";
					params = new Object[] { "zero", "common", targetLevelId };
					break;
				}
			} else if ("zero".equals(targetLevelTag)) {
				// 执行人是社区层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where m.grouping.tag=? and zl.zid=? and m.firstLevel = null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第一/unreal/common 这三个tag或者是三个tag的总和
				case "first":
					params = new Object[] { "first", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where m.grouping.tag in(?,?) and zl.zid=? and m.firstLevel = null";
					params = new Object[] { "first", "common", targetLevelId };
					break;
				}
			} else if ("first".equals(targetLevelTag)) {
				// 执行人是第一层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where m.grouping.tag=? and fl.flid=? and m.secondLevel=null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第二/unreal/common 这三个tag或者是三个tag的总和
				case "second":
					params = new Object[] { "second", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where m.grouping.tag in(?,?) and fl.flid=? and m.secondLevel=null";
					params = new Object[] { "second", "common", targetLevelId };
					break;
				}
			} else if ("second".equals(targetLevelTag)) {
				// 执行人是第二层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where m.grouping.tag=? and scl.scid=? and m.thirdLevel = null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第三/unreal/common 这三个tag或者是三个tag的总和
				case "third":
					params = new Object[] { "third", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default: // 查询所有tag
					hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where m.grouping.tag in(?,?) and scl.scid=? and m.thirdLevel = null";
					params = new Object[] { "third", "common", targetLevelId };
					break;
				}
			} else if ("third".equals(targetLevelTag)) {
				// 执行人是第三层级管理者
				hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where m.grouping.tag=? and tl.thid=? and m.fourthLevel=null";
				switch (groupTag) {
				// 根据“不在其位，不谋其政”的管理原则，街道管理者只能看到他所管轄的（member对应位的lid是它）
				// 第四/unreal/common 这三个tag或者是三个tag的总和
				case "fourth":
					params = new Object[] { "fourth", targetLevelId };
					break;
				case "common":
					params = new Object[] { "common", targetLevelId };
					break;
				default:
					hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where m.grouping.tag in(?,?) and tl.thid=? and m.fourthLevel=null";
					params = new Object[] { "fourth", "common", targetLevelId };
					break;
				}
			} else if ("fourth".equals(targetLevelTag)) {
				hql = "from User u inner join fetch u.members m inner join fetch m.fourthLevel fol where fol.foid=?";
				params = new Object[] { targetLevelId };
			}
		}

		List<User> result = this.getHibernateTemplate()
				.execute(new PageLimitHibernateCallback4QueryUser(hql, params, targetPageNum, pageItemNumLimit));
		return result;
	}

	@Override
	public List<User> getChildrenLevelUsers(String targetLevelTag, String targetLevelId) {

		// 根据当前操作者层级选择hql语句
		String hql = "";
		switch (targetLevelTag) {
		case "admin":
			hql = "from User u inner join fetch u.members m where m.minusFirstLevel <> null";
			break;
		case "minus_first":
			hql = "from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=? and m.zeroLevel<>null";
			break;
		case "zero":
			hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=? and m.firstLevel<>null";
			break;
		case "first":
			hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=? and m.secondLevel<>null";
			break;
		case "second":
			hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=? and m.thirdLevel<>null";
			break;
		case "third":
			hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=? and m.fourthLevel<>null";
			break;
		}

		// 按顺序准备用于hql语句所需要的参数数组
		Object[] params = new Object[] { targetLevelId };

		List<User> result = this.getHibernateTemplate().execute(new CommonHibernateCallback4QueryUser(hql, params));
		return result;
	}

	@Override
	public long getChildrenLevelUsersCount(String targetLevelTag, String targetLevelId) {

		List<User> childrenLevelUsers = this.getChildrenLevelUsers(targetLevelTag, targetLevelId);
		if (null == childrenLevelUsers) {
			return 0;
		} else {
			return childrenLevelUsers.size();
		}
	}

	@Override
	public List<User> getChildrenLevelUsersByPage(String targetLevelTag, String targetLevelId, int targetPageNum,
			int pageItemNumLimit) {

		// 根据当前操作者层级选择hql语句
		String hql = "";
		// 按顺序准备用于hql语句所需要的参数数组
		Object[] params = new Object[] { targetLevelId };
		switch (targetLevelTag) {
		case "admin":
			hql = "from User u inner join fetch u.members m where m.minusFirstLevel <> null";
			params = new Object[] {};
			break;
		case "minus_first":
			hql = "from User u inner join fetch u.members m inner join fetch m.minusFirstLevel mfl where mfl.mflid=? and m.zeroLevel<>null";
			break;
		case "zero":
			hql = "from User u inner join fetch u.members m inner join fetch m.zeroLevel zl where zl.zid=? and m.firstLevel<>null";
			break;
		case "first":
			hql = "from User u inner join fetch u.members m inner join fetch m.firstLevel fl where fl.flid=? and m.secondLevel<>null";
			break;
		case "second":
			hql = "from User u inner join fetch u.members m inner join fetch m.secondLevel scl where scl.scid=? and m.thirdLevel<>null";
			break;
		case "third":
			hql = "from User u inner join fetch u.members m inner join fetch m.thirdLevel tl where tl.thid=? and m.fourthLevel<>null";
			break;
		}
		
		List<User> result = this.getHibernateTemplate().execute(new PageLimitHibernateCallback4QueryUser(hql, params, targetPageNum, pageItemNumLimit));
		return result;
	}

}
