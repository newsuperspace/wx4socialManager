package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.User;

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
		// TODO shiro 先获取当前操作执行者的层级对象，然后再从该层级对象所管辖的人员范围内查找特定tag的manager
		Grouping g = new Grouping();
		g.setTag("admin");

		List<User> list = null;

		if (g.getTag().equals("admin")) {
			// 管理员用户
			switch (tag) {
			case "minus_first":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?",
						"minus_first");
				break;
			case "zero":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?", "zero");
				break;
			case "first":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?",
						"first");
				break;
			case "second":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?",
						"second");
				break;
			case "third":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?",
						"third");
				break;
			case "fourth":
				list = (List<User>) this.getHibernateTemplate().find("from User u where " + "u.grouping.tag=?",
						"fourth");
				break;
			default: // 获得全部
				list = (List<User>) this.getHibernateTemplate().find(
						"from User u where " + "u.grouping.tag in(?,?,?,?,?,?)", "minus_first", "zero", "first",
						"second", "third", "fourth");
				break;
			}
		} else {
			// 非管理员用户
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
