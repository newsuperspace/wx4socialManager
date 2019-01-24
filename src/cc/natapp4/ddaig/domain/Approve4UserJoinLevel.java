package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class Approve4UserJoinLevel implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	// ==================数据库字段=================
	private String  a4ujlid;  	// 主键UUID
	private String timeStr;		// 形如： yyyy-MM-dd HH:mm:ss 的日期字符串，记录管理者处理申请的日期
	private long timeStamp;	// 格里高利历偏移量毫秒值
	private boolean beread;	// 随同userApply4JoinLevel新建数据后，默认为false，用来表示新请求是否被层级管理者阅读过了，首次加载数据的时候会优秀修正为true
	private String  tag;			// 形如：minus_first/zero/first/second/third/fourth  这样的组织层级字段，用来联合lid一起定位用户提交申请的目标组织
	private String lid;				// 组织的主键id，与tag合作可以从数据库中定位用户所要申请加入的具体组织
	// ----------------------外键----------------------
	private UserApply4JoinLevel userApply4JoinLevel;		// 一对一 
	private List<Reply4UserJoinLevelApprove> replies; 		// 一对多
	
	
	// ==================SETTER/GETTER==============
	public String getA4ujlid() {
		return a4ujlid;
	}
	public void setA4ujlid(String a4ujlid) {
		this.a4ujlid = a4ujlid;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public UserApply4JoinLevel getUserApply4JoinLevel() {
		return userApply4JoinLevel;
	}
	public void setUserApply4JoinLevel(UserApply4JoinLevel userApply4JoinLevel) {
		this.userApply4JoinLevel = userApply4JoinLevel;
	}
	
	// @JSON(serialize=false)   由于一般的加入申请的回复只有一条，因此这里并不需要防止死循环解析
	public List<Reply4UserJoinLevelApprove> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply4UserJoinLevelApprove> replies) {
		this.replies = replies;
	}
	
	
	
	
	
}
