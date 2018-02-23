package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;


public class Activity implements Serializable {

	// 【主键】
	private String aid;				
	/*
	 *  发起活动的类型——
	 *  1、开放报名【现阶段只有这一种】
	 *  ①向活动所属项目的执行者（必定是一个层级对象）的管辖范围内的所有成员发送活动通知，公开进行招募，并限定准确的参与者人数上限
	 *  ②招募时间可以通过baoMingBeginTime和baoMingEndTime来进行约束
	 *  ③在报名时间内，收到通知的用户可以进行报名，报名的用户会在该活动对应的visitor表中生成一条该用户的visitor记录，只不过visitor的startTime和endTime两个字段都是null
	 *  ④当活动开始的时候，用户可以在签到时间内进行签到，非报名用户不能签到（因为在visitor中找不到该用户的记录），签到和签退后都会在该已报名用户对应的visitor的
	 *  startTime和endTime字段上记录下时间信息。
	 *  ⑤对于报名，但没有来参加的可以给予一定的惩罚。
	 *  TODO 2、指定团队【后续开发再支持】
	 *  ①活动发起方必定是一个项目的直接执行人，他可以选定其所辖范围内的其他执行团队参与活动，例如thirdLevel的管理者可以选定旗下的全部三个fourthLevel子团队参加活动
	 *  也可只选择三个中的一个或两个。
	 *  ②被确定参与活动的团队的成员才有资格在活动开始的时候进行扫码签到
	 *  ③只要扫码签到的成员就会在visitor上生成一条对应记录，没有来参加活动的自然不会存在visitor记录信息，完全是自愿来参加的。
	 */
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
	
	private int score;				// 为开展当前活动所分配到的积分（足够平均分配给每位参与者）
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
