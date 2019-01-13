package cc.natapp4.ddaig.domain;

import java.io.Serializable;

public class UserApply4JoinLevel implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	// ==================数据库字段=================
	private String  ua4jlid;  	// 主键UUID
	private String timeStr;		// 形如： yyyy-MM-dd HH:mm:ss 的日期字符串，记录提交申请的日期
	private long timeStamp;	// 格里高利历偏移量毫秒值
	private int status;				// 新建数据默认为0，代表“受理中”层级管理者“否决”申请，设置为1； 层级管理者“通过”申请，设置为2
	private boolean beread;	// 默认为NULL,只有当status为非0是，该标记位才会被关注，并且false表示组织层级管理者已经完成处理但是提交申请的用户还没看到，直到首次加载本数据的时候才会永久变更为true
	
	private String theReason; // 前端表单获取：申请加入的原因
	private String theExpertise; // 前端表单获取：你的专长
	private String theDesire; // 前端表单获取：你的期许
	
	// ----------------------外键----------------------
	private User user;  // 多对一
	private Approve4UserJoinLevel approve4UserJoinLevel; // 一对一

	
	// ==================SETTER/GETTER==============
	public String getUa4jlid() {
		return ua4jlid;
	}
	public void setUa4jlid(String ua4jlid) {
		this.ua4jlid = ua4jlid;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isBeread() {
		return beread;
	}
	public void setBeread(boolean beread) {
		this.beread = beread;
	}
	public String getTheReason() {
		return theReason;
	}
	public void setTheReason(String theReason) {
		this.theReason = theReason;
	}
	public String getTheExpertise() {
		return theExpertise;
	}
	public void setTheExpertise(String theExpertise) {
		this.theExpertise = theExpertise;
	}
	public String getTheDesire() {
		return theDesire;
	}
	public void setTheDesire(String theDesire) {
		this.theDesire = theDesire;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Approve4UserJoinLevel getApprove4UserJoinLevel() {
		return approve4UserJoinLevel;
	}
	public void setApprove4UserJoinLevel(Approve4UserJoinLevel approve4UserJoinLevel) {
		this.approve4UserJoinLevel = approve4UserJoinLevel;
	}
	
	
	
}
