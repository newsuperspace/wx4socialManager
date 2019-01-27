package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.UserApply4JoinLevel;

public interface UserApply4JoinLevelDao extends BaseDao<UserApply4JoinLevel> {

	/**
	 * 从数据库中获取对某个层级对象的用户加入申请（未审批、已通过、未通过）
	 * @param tag 层级对象的标记（minus_first、zero、first、second、third、fourth）
	 * @param lid 层级对象的主键ID
	 * @param status 申请的状态
	 * 					-1获取所有申请；
	 * 					层级管理者“受理中”，设置为0;
	 * 					层级管理者“否决”申请，设置为1； 
	 * 					层级管理者“通过”申请，设置为2；
	 * 					
	 * @return
	 */
	public List<UserApply4JoinLevel> getUserApplyList(String tag, String lid, int status);
	
	/**
	 * 【用户调用】 根据tag和lid，获取当前用户对某个特定层级对象的加入申请对象（历史申请——未通过和已通过是不算的，只有status=0也就是未处理的申请才算）
	 * 
	 * @param openid
	 *            用户的OPENID（因为必定通过扫描二维码提交组织申请，因此该用户必定有OPENID）
	 * @param tag
	 *            层级的类别（minus_first、zero、first、second、third、fourth）
	 * @param lid
	 *            层级的主键ID
	 * @return
	 */
	public UserApply4JoinLevel getUserApplyByTagAndLid(String openid, String tag, String lid);
	
}
