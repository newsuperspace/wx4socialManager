package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.ManagerDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;

@Repository("managerDao")
public class ManagerDaoImpl extends BaseDaoImpl<Manager> implements ManagerDao {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

//	@Override
//	public List<Manager> getManagers(String tag) {
//		// TODO shiro 先获取当前操作执行者的层级对象，然后再从该层级对象所管辖的人员范围内查找特定tag的manager
//		Grouping g = new Grouping();
//		g.setTag("admin");
//
//		List<Manager> list = null;
//
//		if (g.getTag().equals("admin")) {
//			// 管理员用户
//			switch (tag) {
//			case "minus_first":
//
//				break;
//			case "zero":
//				list = (List<Manager>) this.getHibernateTemplate().find(
//						"from Manager m inner join fetch m.user u where " + "u.grouping.tag=?",
//						 "zero");
//				break;
//			case "first":
//
//				break;
//			case "second":
//
//				break;
//			case "third":
//
//				break;
//			case "fourth":
//
//				break;
//			default: // 获得全部
//				list = (List<Manager>) this.getHibernateTemplate().find(
//						"from Manager m inner join fetch m.user u where " + "u.grouping.tag in(?,?,?,?,?,?)",
//						"minus_first", "zero", "first", "second", "third", "fourth");
//				break;
//			}
//		} else {
//			// 非管理员用户
//		}
//
//		return list;
//	}

}
