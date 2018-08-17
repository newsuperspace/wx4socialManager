package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Activity;

public interface ActivityDao extends BaseDao<Activity>{

	/**
	 * 查找出可参加的互动
	 * @return
	 */
	public List<Activity> getCanJoinActivityList(String openid);
	
	/**
	 * 获取当前用户已经报名的活动，而且活动还有效(非取消或过期状态)
	 * @return
	 */
	public List<Activity> getJoiningActivityList(String openid);
	
	/**
	 * 获取当前用户已经参加过的活动，但是活动已经过期或被取消
	 * @return
	 */
	public List<Activity> getJoinedActivityList(String openid);
	
	/**
	 * 【本方法不直接供service层调用，而本Dao中的其他方法内部调用】
	 * 根据用户的openid确定该用户，然后进一步通过用户的member去顶
	 * 用户在层级结构中的位置，然后将用户所属于的每个层级对象的所有状态
	 * （招募中的、已报名的、取消的、已经参加的....）
	 * 的活动都找出来，以供给其他方法从中筛选所需要的类型活动，
	 * 这样能够极大地提高代码复用，但会降低程序效率。
	 * @param openid
	 * @return
	 */
	public List<Activity> getAllActivities(String openid);
	
}
