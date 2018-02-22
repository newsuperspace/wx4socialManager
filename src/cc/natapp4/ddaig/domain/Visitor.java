package cc.natapp4.ddaig.domain;

import java.io.Serializable;

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
	
	
	
	
	
}
