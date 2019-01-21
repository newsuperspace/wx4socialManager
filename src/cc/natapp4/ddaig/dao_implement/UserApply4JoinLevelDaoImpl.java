package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.UserApply4JoinLevelDao;
import cc.natapp4.ddaig.domain.UserApply4JoinLevel;

@Repository("userApply4JoinLevelDao")
public class UserApply4JoinLevelDaoImpl extends BaseDaoImpl<UserApply4JoinLevel> implements UserApply4JoinLevelDao {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	/**
	 * 从数据库中获取对某个层级对象的用户加入申请（未审批、已通过、未通过）
	 * 
	 * @param tag
	 *            层级对象的标记（minus_first、zero、first、second、third、fourth）
	 * @param lid
	 *            层级对象的主键ID
	 * @param status
	 *            申请的状态 -1获取所有申请； 层级管理者“受理中”，设置为0; 层级管理者“否决”申请，设置为1；
	 *            层级管理者“通过”申请，设置为2；
	 * 
	 * @return
	 */
	@Override
	public List<UserApply4JoinLevel> getUserApplyList(String tag, String lid, int status) {

		if (status > 2 || status < -1) {
			// 如果传递进来的申请状态代码不是正常的 -1 、0、 1、 2，那么统一设置成-1
			status = -1;
		}

		List<UserApply4JoinLevel> list = null;
		if (-1 == status) {
			list = (List<UserApply4JoinLevel>) hibernateTemplate.find(
					"from UserApply4JoinLevel apply inner join fetch apply.approve4UserJoinLevel approve where approve.tag=? and approve.lid=?",
					tag,lid);
		} else {
			list = (List<UserApply4JoinLevel>) hibernateTemplate.find(
					"from UserApply4JoinLevel apply inner join fetch apply.approve4UserJoinLevel approve where approve.tag=? and approve.lid=? and apply.status=?",
					tag, lid, status);
		}

		return list;
	}

}
