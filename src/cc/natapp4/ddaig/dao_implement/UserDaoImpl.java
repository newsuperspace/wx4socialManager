package cc.natapp4.ddaig.dao_implement;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

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
	public List<User> getManagers(String tag) {
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
		List<User> list = null;
		if (isAdmin) {
			// admin用户，在最大范围内查找目标tag的用户
			switch (tag) {
			case "minus_first":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?",
						"minus_first");
				break;
			case "zero":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?", "zero");
				break;
			case "first":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?", "first");
				break;
			case "second":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?", "second");
				break;
			case "third":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?", "third");
				break;
			case "fourth":
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag=?", "fourth");
				break;
			default:
				list = (List<User>) this.getHibernateTemplate().find("from User u where u.grouping.tag in(?,?,?,?,?,?)",
						"minus_first", "zero", "first", "second", "third", "fourth");
				break;
			}
		} else {
			// 非admin用户，根据其lid在有限范围内查找目标tag的用户
			if ("minus_first".equals(doingMan.getGrouping().getTag())) {
				// 执行人是街道层级管理者
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel l = null;
				for (MinusFirstLevel mfl : mfls) {
					l = mfl;
				}
				switch (tag) {
				case "zero":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag=? and mfl.mflid=?",
							"zero", l.getMflid());
					break;
				case "first":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag=? and mfl.mflid=?",
							"first", l.getMflid());
					break;
				case "second":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag=? and mfl.mflid=?",
							"second", l.getMflid());
					break;
				case "third":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag=? and mfl.mflid=?",
							"third", l.getMflid());
					break;
				case "fourth":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag=? and mfl.mflid=?",
							"fourth", l.getMflid());
					break;

				default: // 查询所有tag
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.minusFirstLevel mfl where u.grouping.tag in(?,?,?,?,?) and mfl.mflid=?",
							"zero", "first", "second", "third", "fourth", l.getMflid());
					break;
				}
			} else if ("zero".equals(doingMan.getGrouping().getTag())) {
				// 执行人是社区层级管理者
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				ZeroLevel l = null;
				for (ZeroLevel zl : zls) {
					l = zl;
				}
				switch (tag) {
				case "first":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.zeroLevel zl where u.grouping.tag=? and zl.zid=?",
							"first", l.getZid());
					break;
				case "second":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.zeroLevel zl where u.grouping.tag=? and zl.zid=?",
							"second", l.getZid());
					break;
				case "third":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.zeroLevel zl where u.grouping.tag=? and zl.zid=?",
							"third", l.getZid());
					break;
				case "fourth":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.zeroLevel zl where u.grouping.tag=? and zl.zid=?",
							"fourth", l.getZid());
					break;

				default: // 查询所有tag
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.zeroLevel zl where u.grouping.tag in(?,?,?,?) and zl.zid=?",
							"first", "second", "third", "fourth", l.getZid());
					break;
				}
			} else if ("first".equals(doingMan.getGrouping().getTag())) {
				// 执行人是第一层级管理者
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				FirstLevel l = null;
				for (FirstLevel fl : fls) {
					l = fl;
				}
				switch (tag) {
				case "second":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.firstLevel fl where u.grouping.tag=? and fl.flid=?",
							"second", l.getFlid());
					break;
				case "third":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.firstLevel fl where u.grouping.tag=? and fl.flid=?",
							"third", l.getFlid());
					break;
				case "fourth":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.firstLevel fl where u.grouping.tag=? and fl.flid=?",
							"fourth", l.getFlid());
					break;

				default: // 查询所有tag
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.firstLevel fl where u.grouping.tag in(?,?,?) and fl.flid=?",
							"second", "third", "fourth", l.getFlid());
					break;
				}
			} else if ("second".equals(doingMan.getGrouping().getTag())) {
				// 执行人是第二层级管理者
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				SecondLevel l = null;
				for (SecondLevel scl : scls) {
					l = scl;
				}
				switch (tag) {
				case "third":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.secondLevel scl where u.grouping.tag=? and scl.scid=?",
							"third", l.getScid());
					break;
				case "fourth":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.secondLevel scl where u.grouping.tag=? and scl.scid=?",
							"fourth", l.getScid());
					break;

				default: // 查询所有tag
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.secondLevel scl where u.grouping.tag in(?,?) and scl.scid=?",
							"third", "fourth", l.getScid());
					break;
				}
			} else if ("third".equals(doingMan.getGrouping().getTag())) {
				// 执行人是第三层级管理者
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				ThirdLevel l = null;
				for (ThirdLevel tl : tls) {
					l = tl;
				}
				switch (tag) {
				case "fourth":
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.thirdLevel tl where u.grouping.tag=? and tl.thid=?",
							"fourth", l.getThid());
					break;
				default:
					list = (List<User>) this.getHibernateTemplate().find(
							"from User u inner join fetch u.member m inner join fetch m.thirdLevel tl where u.grouping.tag=? and tl.thid=?",
							"fourth", l.getThid());
					break;
				}
			} else {
				// 执行人是其他层级（例如第四层级等）的管理者
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

}
