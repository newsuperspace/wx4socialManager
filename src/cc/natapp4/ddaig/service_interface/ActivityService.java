package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Activity;

public interface ActivityService extends BaseService<Activity> {
	
	
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
	 * 获取在某一个房子中开展的，从明天到15天以后的全部活动
	 * @param hid
	 * @return
	 */
	public List<Activity> getActivities4House(String hid);
}
