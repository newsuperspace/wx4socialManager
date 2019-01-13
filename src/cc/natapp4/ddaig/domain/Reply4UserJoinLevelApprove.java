package cc.natapp4.ddaig.domain;

import java.io.Serializable;

public class Reply4UserJoinLevelApprove implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	// ==================数据库字段=================
	private String  r4ujlaid;  	// 主键UUID
	private String timeStr;		// 形如： yyyy-MM-dd HH:mm:ss 的日期字符串，记录管理者处理申请的日期
	private long timeStamp;	// 格里高利历偏移量毫秒值
	private boolean beread;	// 随同userApply4JoinLevel新建数据后，默认为false，用来表示新请求是否被层级管理者阅读过了，首次加载数据的时候会优秀修正为true
	private String message;  // 层级管理者向申请人返回的审核信息
	// ----------------------外键----------------------
	private Approve4UserJoinLevel  approve4UserJoinLevel; // 多对一

	
	// ==================SETTER/GETTER==============
	public String getR4ujlaid() {
		return r4ujlaid;
	}
	public void setR4ujlaid(String r4ujlaid) {
		this.r4ujlaid = r4ujlaid;
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
	public boolean isBeread() {
		return beread;
	}
	public void setBeread(boolean beread) {
		this.beread = beread;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Approve4UserJoinLevel getApprove4UserJoinLevel() {
		return approve4UserJoinLevel;
	}
	public void setApprove4UserJoinLevel(Approve4UserJoinLevel approve4UserJoinLevel) {
		this.approve4UserJoinLevel = approve4UserJoinLevel;
	}
	
	
	
}
