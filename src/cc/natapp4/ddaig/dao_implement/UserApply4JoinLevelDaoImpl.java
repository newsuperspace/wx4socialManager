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
	 * 【管理者调用】 从数据库中获取对某个层级对象的用户加入申请（未审批、已通过、未通过）
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
					tag, lid);
		} else {
			list = (List<UserApply4JoinLevel>) hibernateTemplate.find(
					"from UserApply4JoinLevel apply inner join fetch apply.approve4UserJoinLevel approve where approve.tag=? and approve.lid=? and apply.status=?",
					tag, lid, status);
		}

		return list;
	}

	/**
	 * 【用户调用】
	 * 根据tag和lid，获取当前用户对某个特定层级对象的加入申请对象（历史申请——未通过和已通过是不算的，只有status=0也就是未处理的申请才算）
	 * 
	 * @param openid
	 *            用户的OPENID（因为必定通过扫描二维码提交组织申请，因此该用户必定有OPENID）
	 * @param tag
	 *            层级的类别（minus_first、zero、first、second、third、fourth）
	 * @param lid
	 *            层级的主键ID
	 * @return
	 */
	public UserApply4JoinLevel getUserApplyByTagAndLid(String openid, String tag, String lid) {

		UserApply4JoinLevel apply = null;
		List<UserApply4JoinLevel> list = (List<UserApply4JoinLevel>) hibernateTemplate.find(
				"from UserApply4JoinLevel apply inner join fetch apply.approve4UserJoinLevel approve inner join fetch apply.user u where apply.status=? and approve.tag=? and approve.lid=? and u.openid=?",
				0, tag, lid, openid);

		if(null==list || 0==list.size()){
			return null;
		}else{
			return list.get(0);
		}
	}

}
