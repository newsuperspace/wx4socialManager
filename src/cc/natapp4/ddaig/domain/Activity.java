package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;


public class Activity implements Serializable {

	// 【主键】
	private String aid;				
	// 发起活动的类型——开放报名、指定团队
	private String type;
	// 如果type类型是开放报名，则需要在这里设置最高报名人数
	private int baoMingNumber;   
	/*
	 * 通过endTime - beginTime，可以计算出活动的持续时间
	 * 同时给予SimpleDateFormate类可以方便地将毫秒值转换成类似2018-1-2 19:23:30这样的时间格式字符串
	 * 因此这个字段设置成long类型是最合适的
	 */
	private long activityBeginTime;   // 活动的开始时间（以1970-1-1日为起点的毫秒值）
	private long activityEndTime;		// 活动的借书时间（以1970-1-1日为起点的毫秒值）
	private long baoMingBeginTime; // 活动报名的开始时间
	private long baoMingEndTime;  // 活动报名的截止时间
	
	private String score;				// 当前活动所分配到的积分
	private String qrcodeUrl;    // 活动扫码的二维码图片地址
	private String name;      // 活动名称
	private String description;     // 活动的描述
	private String state;  // 进行中，已完成、已取消
	private String author;   // 发起人（用户的uid）
	 
	private Set<User>  users;  // 实际参与活动的用户列表
	/*
	 *  报名的用户列表
	 *  如果你报名了，但是截止到活动结束后你没有来（签到），则会降低用户的
	 *  信誉值？
	 *  经验值（时长）？
	 *  积分？
	 *  选择哪一个合适呢，想想？
	 */
//	private Set<BaoMingUser>  baoMingUsers
	// 当前活动所属的项目坚持———【项目为核心原则，有项目才能发起活动】
	private DoingProject  project;
	

	
}
