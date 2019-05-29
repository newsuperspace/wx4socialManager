package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * visitor中记录着用户所参与的一次活动的一次信息记录，用来表示该用户在参加这次活动中的一些信息
 * 
 * @author Administrator
 *
 */
public class Visitor implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 981586115554197214L;
	// 主键(可以很方便的统计出活动参与累计人次)
	private int vid;
	// 报名也就是系统创建本visitor类型对象数据的时间
	private long  baomingTime;
	// 报名时间的形如yyyy-MM-dd HH:mm:ss 的时间格式字符串
	private String baomingTimeStr;	
	// 签到时间（格里高利历毫秒值偏移量）
	private long startTime;
	// 签到时间字符串
	private String startTimeStr;   		
	// 签退时间（格里高利历毫秒值偏移量）
	private long endTime;
	// 签退时间字符串
	private String endTimeStr;			
	
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
	// 存放活动签名
	private Signin signin;
	
	//==================SETTERs/GETTERs===================
	public Signin getSignin() {
		return signin;
	}
	public void setSignin(Signin signin) {
		this.signin = signin;
	}
	
	public long getBaomingTime() {
		return baomingTime;
	}
	public void setBaomingTime(long baomingTime) {
		this.baomingTime = baomingTime;
	}
	
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
	
	public String getBaomingTimeStr() {
		return baomingTimeStr;
	}
	public void setBaomingTimeStr(String baomingTimeStr) {
		this.baomingTimeStr = baomingTimeStr;
	}
	
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
