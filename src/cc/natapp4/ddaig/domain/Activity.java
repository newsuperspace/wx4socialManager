package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;


public class Activity implements Serializable {

	// 【主键】
	private String aid;				
	// 发起活动的类型——开放报名、指定团队
	private String type;
	// 如果type类型是开放报名，则需要在这里设置最高报名人数
	private int baoMingUplimit;   
	/*
	 * 通过endTime - beginTime，可以计算出活动的持续时间
	 * 同时给予SimpleDateFormate类可以方便地将毫秒值转换成类似2018-1-2 19:23:30这样的时间格式字符串
	 * 因此这个字段设置成long类型是最合适的
	 */
	private long activityBeginTime;   // 活动的开始时间（以1970-1-1日为起点的毫秒值）
	private long activityEndTime;		// 活动的借书时间（以1970-1-1日为起点的毫秒值）
	private long baoMingBeginTime; // 活动报名的开始时间
	private long baoMingEndTime;  // 活动报名的截止时间
	
	private int score;				// 当前活动所分配到的积分
	private String qrcodeUrl;    // 活动扫码签到所使用的二维码图片地址
	private String name;      // 活动名称
	private String description;     // 活动的描述
	private String state;  // 筹备中、进行中、已完成、已取消
	 
	/*
	 *  报名的用户列表
	 *  如果你报名了，但是截止到活动结束后你没有来（签到），则会降低用户的
	 *  信誉值？
	 *  经验值（时长）？
	 *  积分？
	 *  选择哪一个合适呢，想想？
	 */
	private List<Visitor>  visitors;  // 实际参与活动的用户列表
	// 当前活动所属的项目坚持———【项目为核心原则，有项目才能发起活动】
	private DoingProject  project;

	
	// ============SETTERs/GETTERs===============
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getBaoMingUplimit() {
		return baoMingUplimit;
	}
	public void setBaoMingUplimit(int baoMingUplimit) {
		this.baoMingUplimit = baoMingUplimit;
	}
	
	public long getActivityBeginTime() {
		return activityBeginTime;
	}
	public void setActivityBeginTime(long activityBeginTime) {
		this.activityBeginTime = activityBeginTime;
	}
	
	public long getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(long activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	
	public long getBaoMingBeginTime() {
		return baoMingBeginTime;
	}
	public void setBaoMingBeginTime(long baoMingBeginTime) {
		this.baoMingBeginTime = baoMingBeginTime;
	}
	
	public long getBaoMingEndTime() {
		return baoMingEndTime;
	}
	public void setBaoMingEndTime(long baoMingEndTime) {
		this.baoMingEndTime = baoMingEndTime;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@JSON(serialize=false)
	public List<Visitor> getVisitors() {
		return visitors;
	}
	public void setVisitors(List<Visitor> visitors) {
		this.visitors = visitors;
	}
	
	public DoingProject getProject() {
		return project;
	}
	public void setProject(DoingProject project) {
		this.project = project;
	}


	
	
}
