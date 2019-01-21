package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.UserApply4JoinLevel;

public interface UserApply4JoinLevelService extends BaseService<UserApply4JoinLevel> {

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
	
	
	
}
