package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

public class Activity implements Serializable {

	// 【主键】
	private String aid;
	/*
	 * 发起活动的类型—— 1、开放报名
	 * ①向活动所属项目的执行者（必定是一个层级对象）的管辖范围内的所有成员发送活动通知，公开进行招募，并限定准确的参与者人数上限
	 * ②招募时间可以通过baoMingBeginTime和baoMingEndTime来进行约束
	 * ③在报名时间内，收到通知的用户可以进行报名，报名的用户会在该活动对应的visitor表中生成一条该用户的visitor记录，
	 * 只不过visitor的startTime和endTime两个字段都是null
	 * ④当活动开始的时候，用户可以在签到时间内进行签到，非报名用户不能签到（因为在visitor中找不到该用户的记录），
	 * 签到和签退后都会在该已报名用户对应的visitor的 startTime和endTime字段上记录下时间信息。
	 * ⑤对于报名，但没有来参加的可以给予一定的惩罚。 2、限定人数 TODO 3、指定团队【后续开发再支持】
	 * ①活动发起方必定是一个项目的直接执行人，他可以选定其所辖范围内的其他执行团队参与活动，
	 * 例如thirdLevel的管理者可以选定旗下的全部三个fourthLevel子团队参加活动 也可只选择三个中的一个或两个。
	 * ②被确定参与活动的团队的成员才有资格在活动开始的时候进行扫码签到
	 * ③只要扫码签到的成员就会在visitor上生成一条对应记录，没有来参加活动的自然不会存在visitor记录信息，完全是自愿来参加的。
	 */
	private String type;
	/*
	 * 活动类型：
	 * 1=室外活动
	 * 2=室内活动
	 */
	private String activityType;
	// 如果type类型是限定人数，则需要在这里设置最高报名人数
	private int baoMingUplimit = -1;
	/*
	 * 通过endTime - beginTime，可以计算出活动的持续时间
	 * 同时给予SimpleDateFormate类可以方便地将毫秒值转换成类似2018-1-2 19:23:30这样的时间格式字符串
	 * 因此这个字段设置成long类型是最合适的
	 */
	private long activityBeginTime = 0; // 活动的开始时间（以1970-1-1日为起点的毫秒值）
	private long activityEndTime = 0; // 活动的结束时间（以1970-1-1日为起点的毫秒值）

	/*
	 * 默认报名开始时间为活动新建时 活动报名截止时间为活动那一天之前
	 */
	private long baoMingBeginTime = 0; // 活动报名的开始时间
	private long baoMingEndTime = 0; // 活动报名的截止时间

	private int score; // 为开展当前活动所分配到的积分（足够平均分配给每位参与者）
	private String qrcodeUrl; // 活动扫码签到所使用的二维码图片本地相对路径地址，内容为活动的ID
	private String name; // 活动名称
	private String description; // 活动的描述

	/*
	 * 存放如下四种字符串，用来标记当前活动的状态
	 * 
	 * 筹备中："preparing" currentTime<activityBeginTime 已取消："canceled"
	 * 已完成："finished" 进行中："doing" activityBeginTime<=current<activityEndTime
	 */
	private String state;

	/*
	 * 报名的用户列表 如果你报名了，但是截止到活动结束后你没有来（签到），则会降低用户的 信誉值？ 经验值（时长）？ 积分？ 选择哪一个合适呢，想想？
	 */
	private List<Visitor> visitors; // 实际参与活动的用户列表
	// 当前活动所属的项目坚持———【项目为核心原则，有项目才能发起活动】
	private DoingProject project;
	
	// 【室内活动】，则这里需要关联指定House对象（一对多外键关联）
	private House house;
	// 【室外活动】，则这里需要关联指定Geographic对象（一对多外键关联）
	private Geographic geographic;

	// ===============================用于前端显示的字段（不与数据库关联）==============================
	// 前端显示格式如 yyyy-MM-dd HH：mm:ss 的活动开始时间
	public String beginTimeStr;
	// 前端显示格式如 yyyy-MM-dd HH：mm:ss 的活动结束时间
	public String endTimeStr;
	// 活动的总积分花费
	private int scorePaid;
	// 活动所属项目的项目名
	private String dpName;

	/*
	 * 为了方便joinedActivityList.jsp页面上Struts标签中获取当前用户的visitor对象，这个属性就是用来做这个的，哈哈哈正方便呀
	 */
	private Visitor theVisitor;
	public Visitor getTheVisitor() {
		if (null != this.getVisitors()) {
			// 先获取当前用户的openid
			String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
			// 然后从当前Activity的visitors中遍历出当前用户的visitor
			for (Visitor v : this.getVisitors()) {
				if (v.getUser().getOpenid().equals(openid)) {
					this.theVisitor = v;
				}
			}
		}
		return theVisitor;
	}

	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
	}
	public Geographic getGeographic() {
		return geographic;
	}
	public void setGeographic(Geographic geographic) {
		this.geographic = geographic;
	}

	/*
	 * joinedActivityList.jsp页面中需要根据当前活动的状态来动态展现按钮，
	 * 本属性就是给JSP页面上的struts标签动态显示不同按钮的依据。 
	 * （1）可取消————活动的报名期限还没结束，可以取消报名； √
	 * （2）可签到————activityBeginTime的前后半小时； 		√
	 * （3）可签退————baoMingEndTime的前后半小时；		
	 * 
	 * （4）已签到
	 *  （5）已签退 
	 *  （6）已爽约		√
	 * 
	 */
	private String buttonState;
	public String getButtonState() {
		// 当前用户的openid
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		// JSP页面的struts标签（s:if系列）会根据该返回值来部署不同的功能按钮
		String buttonState = "0";
		// 获取当前时间的格里高利里偏移量，用作下面的判断依据之一
		long currentTimeMillis = System.currentTimeMillis();
		// 开始判断
		if (this.getBaoMingEndTime() > currentTimeMillis) {
			// 因为报名截至时间还没有结束，因此可以取消报名
			buttonState = "可取消";
		} else if (currentTimeMillis < (this.getActivityBeginTime() + 1000L * 60 * 30)
				&& currentTimeMillis > (this.getActivityBeginTime() - 1000L * 60 * 30)) {
			// 在活动开始时间的前后30分钟的跨度内可以签到
			List<Visitor> visitors = this.getVisitors();
			for (Visitor v : visitors) {
				if (v.getUser().getOpenid().equals(openid)) {
					// 找到当前用户的visitor对象，如果该用户签到/签退完成后会在visitor对应字段写下签到时间，如果值为-1（新建visitor默认就是-1）则没有签到
					long startTime = v.getStartTime();
					if (-1 == startTime) {
						// 用户还未签到，因此可以签到
						buttonState = "可签到";
					} else {
						// 用户已签到，无需重复签到
						buttonState = "已签到";
					}
				}
			}

		} else if (currentTimeMillis < (this.getActivityEndTime() + 1000L * 60 * 60)
				&& currentTimeMillis > (this.getActivityEndTime() - 1000L * 60 * 30)) {
			// 在活动结束前的30分钟和结束后的30分钟之间可签退
			List<Visitor> visitors = this.getVisitors();
			for (Visitor v : visitors) {
				if (v.getUser().getOpenid().equals(openid)) {
					// 找到当前用户的visitor对象，如果该用户签到/签退完成后会在visitor对应字段写下签到时间，如果值为-1（新建visitor默认就是-1）则没有签退
					long endTime = v.getEndTime();
					long startTime = v.getStartTime();
					if (-1 == startTime) {
						// 用户由于迟到没有在规定时间内签到，因此无法完成签退
						buttonState = "已爽约";
					} else if (-1 == endTime) {
						buttonState = "可签退";
					} else {
						buttonState = "已签退";
					}
				}
			}
		}

		return buttonState;
	}

	public String getDpName() {
		if(null==project){
			return null;
		}else{
			String dpName = project.getBesureProject().getName();
			return dpName;
		}
	}

	public String getBeginTimeStr() {

		if(0==this.getActivityBeginTime()){
			return this.beginTimeStr;
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = formatter.format(new Date(this.getActivityBeginTime()));
			return format;
		}
	}

	public String getEndTimeStr() {
		if(0==this.getActivityEndTime()){
			return this.endTimeStr;
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = formatter.format(new Date(this.getActivityEndTime()));
			return format;
		}
	}

	public int getScorePaid() {
		if(null==visitors){
			return 0;
		}else{
			int scorePaid = this.visitors.size() * score;
			return scorePaid;
		}
	}

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

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

	/*
	 * 根据当前时刻的实际情况更新活动的状态信息到state位
	 * 
	 * 筹备中："preparing" currentTime<activityBeginTime 
	 * 已取消："canceled"
	 * 已完成："finished" 
	 * 进行中："doing" activityBeginTime<=current<activityEndTime
	 * 即将开始
	 */
	public void updateState() {
		if ("已取消".equals(state) || "已完成".equals(state)) {
			/*
			 * 如果数据库中已记录的活动状态为取消或完成，则该活动状态已经没有任何变动的余地，
			 * 直接返回数据库中记录的状态（canceled或finished）
			 */
		} else {
			// 如果不是取消状态，则进一步根据现状情况分析当前活动处于哪一个状态

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String format = formatter.format(new Date(activityBeginTime));
			long activityStartDateMillis = 0;
			try {
				activityStartDateMillis = formatter.parse(format).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long currentTimeMills = System.currentTimeMillis();
			if (currentTimeMills < activityStartDateMillis) {
				// 如果当前时间（格里高利历偏移）小于活动开始当天00:00的格里高利偏移，则说明活动处于筹备状态
				this.setState("筹备中");
			}else if(currentTimeMills >= activityStartDateMillis && currentTimeMills < activityBeginTime){
				// 从活动当天的0点到活动开始时间，这段时间
				this.setState("即将开始");
			} else if (currentTimeMills >= activityBeginTime
					&& currentTimeMills <= activityEndTime) {
				// 如果当前时间在活动开始和结束时间之间，则认定活动进行中
				this.setState("进行中");
			}else{
				// 当前时间超过活动结束时间，活动完成
				this.setState("已完成");
			}
		}
		return;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JSON(serialize = false)
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
