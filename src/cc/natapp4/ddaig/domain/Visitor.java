package cc.natapp4.ddaig.domain;

import java.io.Serializable;

/**
 * visitor中记录着用户所参与的一次活动的一次信息记录，用来表示该用户在参加这次活动中的一些信息
 * 
 * @author Administrator
 *
 */
public class Visitor implements Serializable {

	// 主键(可以很方便的统计出活动参与累计人次)
	private int vid;
	// 签到时间（格里高利历毫秒值偏移量）
	private long startTime;
	// 签退时间（格里高利历毫秒值偏移量）
	private long endTime;
	
	// ----------------------活动结束后（Activity对象的state="已完成"），自动同级并存入下面的对应字段-----------------------
	// 本次活动获得的积分
	private int score;
	// 本次活动获取到的服务时长（用于经验值加成），由所参与活动的activityEndTime-activityBeginTime计算得出
	private long workTime;
	
	// -----------------------------Foreign Key------------------------------
	// 当前活动参与者的真实身份对应的是哪个用户
	private User  user;
	// 当前活动参与者参与的活动是。。。
	private Activity  activity;

	//==================SETTERs/GETTERs===================
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public long getWorkTime() {
		return workTime;
	}
	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
}
