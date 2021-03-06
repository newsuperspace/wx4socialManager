package cc.natapp4.ddaig.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.Signin;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4CreateActivity;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4StartDayAndEndDay;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.VisitorService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.ActivityUtils;
import cc.natapp4.ddaig.utils.FileController;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cn.com.obj.freemarker.ExportDoc;

@Controller("activityAction")
@Scope("prototype")
@Lazy(true)
public class ActivityAction extends ActionSupport implements ModelDriven<Activity> {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	// ==============================DI注入==============================
	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;
	@Resource(name = "houseService")
	private HouseService houseService;
	@Resource(name = "geographicService")
	private GeographicService geographicService;
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;
	@Resource(name = "secondLevelService")
	private SecondLevelService secondLevelService;
	@Resource(name = "thirdLevelService")
	private ThirdLevelService thirdLevelService;
	@Resource(name = "fourthLevelService")
	private FourthLevelService fourthLevelService;
	@Resource(name = "visitorService")
	private VisitorService visitorService;

	// ==============================属性驱动==============================
	private String hid;
	private String geoid;

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getGeoid() {
		return geoid;
	}

	public void setGeoid(String geoid) {
		this.geoid = geoid;
	}

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	// 新建活动时，这里传来需要新建活动的doingProject的ID
	private String dpid;

	public String getDpid() {
		return dpid;
	}

	public void setDpid(String dpid) {
		this.dpid = dpid;
	}

	// 新建活动时，这里传来新建活动的持续时长
	private int hour;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// JSP页面上创建室内活动时，date4calendar的input中形如"2018-09-18T09:00:00~2018-09-18T11:30:00"的字符串
	private String date4calendar;

	public String getDate4calendar() {
		return date4calendar;
	}

	public void setDate4calendar(String date4calendar) {
		this.date4calendar = date4calendar;
	}

	// 新建室外活动时，这里存放形如 "2018-07-25 00:00:00" 的活动日使劲按字符串，符合yyyy-MM-dd HH:mm:ss
	private String date4selector;

	public String getDate4selector() {
		return date4selector;
	}

	public void setDate4selector(String date4selector) {
		this.date4selector = date4selector;
	}

	// 获取createActivity.jsp中用于选择签到方式的name="sychronizeRadio"的 Radio表单的值
	private int sychronizeRadio;

	public int getSychronizeRadio() {
		return sychronizeRadio;
	}

	public void setSychronizeRadio(int sychronizeRadio) {
		this.sychronizeRadio = sychronizeRadio;
	}
	
	// 获取createActivity.jsp中用于选择是否“电子签名”的name="signinRadio"的 Radio表单的值
	private int signinRadio;

	public int getSigninRadio() {
		return signinRadio;
	}

	public void setSigninRadio(int signinRadio) {
		this.signinRadio = signinRadio;
	}

	// ==============================模型驱动==============================
	private Activity activity;

	@Override
	public Activity getModel() {
		activity = new Activity();
		return this.activity;
	}

	// ==============================Methods==============================

	/**
	 * 【未完成，缺少配套的前端JSP页面】
	 * 在doingProject的jsp页面上，当管理者点击某一个doingProject的活动总数量的时候，就会请求本方法
	 * 方法会获取某一个doingProject的全部活动信息，然后跳转到活动展示页面
	 * 
	 * @return
	 */
	public String showDoingProjectActivityList() {
		DoingProject doingProject = doingProjectService.queryEntityById(dpid);
		List<Activity> activities = doingProject.getActivities();
		// 更新每个activity的state状态信息，并保存到数据库
		for (Activity a : activities) {
			a.updateState();
			activityService.update(a);
		}
		// 下面的全部活动数据信息将会现在在activityList.jsp的表格中
		ActionContext.getContext().put("activities", activities);
		// 下面的信息将作为activityList.jsp的标题
		ActionContext.getContext().put("title", doingProject.getBesureProject().getName() + "的全部活动信息");
		return "doingProjectActivityList";
	}

	/**
	 * 在activityList.jsp页面上点击某个活动的报名人数后会调用本方法，用以获取该活动的所有报名数据信息
	 * 并展示到visitorList.jsp页面上
	 * 
	 * @return
	 */
	public String getVisitorList() {

		List<Visitor> visitors = null;
		Activity activity = activityService.queryEntityById(this.activity.getAid());
		visitors = activity.getVisitors();

		// 將需要展示到前端的数据送入到Map栈中
		ActionContext.getContext().put("visitors", visitors);
		ActionContext.getContext().put("aid", activity.getAid());
		return "visitorList";
	}


	/**
	 * 【完成】 新建活动前的必要准备 （1）将未来新建的活动所属的doingProject的dpid返回到前端页面createActivity.jsp
	 * （2）根据当前操作执行者所管理的层级对象所拥有的人员数量，设置前端页面createActivity.jsp中设置活动参与人数上线的max属性
	 * 最后一切设置完毕后，通过struts结果集索引字符串请求转发到用于新建活动的页面————createActivity.jsp
	 * 
	 * @return
	 */
	public String createPage() {

		// 把活动所属的doingProject的dpid放入到map栈空间中，供给createActivity.jsp页面上通过struts的<s:property>标签显示在页面上
		ActionContext.getContext().put("dpid", this.getDpid());
		// 確定当前操作者（doingMan）所管理的层级对象，查找该层级对象之下的全部人员数量
		Set<Member> members = null;
		List<House> houses = null;
		List<Geographic> geos = null;
		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		switch (levelTag) {
		// 街道层级只能使用自己管理的活动空间
		case "minus_first":
			MinusFirstLevel mfl = minusFirstLevelService.queryEntityById(lid);
			// 获取到当前层级对象的所有成员的集合
			members = mfl.getMembers();
			houses = mfl.getHouses();
			geos = mfl.getGeographics();
			break;
		// 社区及以下层级均只能使用社区管理的活动空间
		case "zero":
			ZeroLevel zl = zeroLevelService.queryEntityById(lid);
			members = zl.getMembers();
			houses = zl.getHouses();
			geos = zl.getGeographics();
			break;
		case "first":
			FirstLevel fl = firstLevelService.queryEntityById(lid);
			members = fl.getMembers();
			houses = fl.getParent().getHouses();
			geos = fl.getGeographics();
			break;
		case "second":
			SecondLevel sc = secondLevelService.queryEntityById(lid);
			members = sc.getMembers();
			houses = sc.getParent().getParent().getHouses();
			geos = sc.getGeographics();
			break;
		case "third":
			ThirdLevel tl = thirdLevelService.queryEntityById(lid);
			members = tl.getMembers();
			houses = tl.getParent().getParent().getParent().getHouses();
			geos = tl.getGeographics();
			break;
		case "fourth":
			FourthLevel fol = fourthLevelService.queryEntityById(lid);
			members = fol.getMembers();
			houses = fol.getParent().getParent().getParent().getParent().getHouses();
			geos = fol.getGeographics();
			break;
		}
		if (members == null) {
			// 这个步骤主要是为了防止在获取集合数量时出现空指针异常
			members = new HashSet<Member>();
		}
		// 最终获知到当前层级对象的所有成员数量
		int size = members.size();
		// 将成员数量放入到Map栈空间中，以供createActivity.jsp中通过max='<s:property
		// value="%{'#size'}">' 使用
		ActionContext.getContext().put("size", size);
		// 过滤出可用的house
		List<House> list = new ArrayList<House>();
		if (null != houses) {
			for (House h : houses) {
				if (h.isEnable()) {
					list.add(h);
				}
			}
		}
		ActionContext.getContext().put("houses", list);
		// 过滤出可用的geo
		List<Geographic> list2 = new ArrayList<Geographic>();
		for (Geographic g : geos) {
			if (g.isEnable()) {
				list2.add(g);
			}
		}
		ActionContext.getContext().put("geos", list2);
		// 执行跳转
		return "create";
	}

	/**
	 * AJAX 基于前端通过AJAX提交过来的必要数据信息，首先通过后端校验，校验通过正式执行活动新建逻辑 并将数据写入数据库。
	 * 
	 * @return
	 */
	public String createActivity() {

		ReturnMessage4CreateActivity message = new ReturnMessage4CreateActivity();

		String s = ServletActionContext.getRequest().getParameter("score");
		System.out.println("score:" + s);
		String n = ServletActionContext.getRequest().getParameter("name");
		System.out.println("name:" + n);

		String name = this.activity.getName();
		String dpid = this.dpid;
		String description = this.activity.getDescription();
		String activityType = this.activity.getActivityType();
		String type = this.activity.getType(); // 默认为1
		int baoMingUplimit = this.activity.getBaoMingUplimit(); // 如果type=2
																// 则这个属性才有意义，最少为1，最大为活动所属层级的总人员数量上限
		String date4selector = this.date4selector;
		String date4calendar = this.date4calendar;

		int hour = this.hour; // 默认为1
		int score = this.activity.getScore(); // 默认为0

		// --------------------校验关键input字段信息是否为null--------------------
		boolean hasEmpty = false;
		switch (activityType) {
		// 室外活动
		case "1":
			if (type.equals("1")) {
				// 开放报名，不用校验baoMingUplimit
				if ("".equals(name) || "".equals(description) || "".equals(date4selector) || "".equals(hour)
						|| "".equals(score)) {
					hasEmpty = true;
				}
			} else {
				// 限制人数报名，还需要校验baoMingUplimit
				if ("".equals(name) || "".equals(description) || "".equals(date4selector) || "".equals(hour)
						|| "".equals(score) || "".equals(baoMingUplimit)) {
					hasEmpty = true;
				}
			}
			break;
		// 室内活动
		case "2":
			if ("1".equals(type)) {
				// 开放报名，不用校验baoMingUplimit
				if ("".equals(name) || "".equals(description) || "".equals(date4calendar) || "".equals(score)) {
					hasEmpty = true;
				}
			} else {
				// 限制人数报名，还需要校验baoMingUplimit
				if ("".equals(name) || "".equals(description) || "".equals(date4calendar) || "".equals(score)
						|| "".equals(baoMingUplimit)) {
					hasEmpty = true;
				}
			}
			break;
		}

		if (hasEmpty) {
			message.setResult(false);
			message.setMessage("关键信息为空，不予新建活动");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// --------------------校验当前所新建活动所属的doingProject是否存在--------------------
		DoingProject doingProject = doingProjectService.queryEntityById(dpid);
		if (null == doingProject) {
			message.setResult(false);
			message.setMessage("dpid为" + dpid + "的doingProject对象不存在");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// --------------------校验活动时间是否相较于当前时间为提前1天--------------------
		long currentTimeMillis = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(new Date(currentTimeMillis));
		try {
			Date parse = formatter.parse(today);
			currentTimeMillis = parse.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			message.setResult(false);
			message.setMessage("将today转化为格里高利历偏移量是出现错误，新建终止");
			ActionContext.getContext().getValueStack().push(message);
			return "message";
		}
		long activityDateTimeMillis = 0;
		if ("1".equals(activityType)) {
			// 室外活動
			try {
				activityDateTimeMillis = (formatter.parse(date4selector.split(" ")[0])).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
				message.setResult(false);
				message.setMessage("将date活动日期字符串转化为格里高利历偏移量是出现错误，新建终止");
				ActionContext.getContext().getValueStack().push(message);
				return "message";
			}
		} else {
			// 室內活動
			try {
				activityDateTimeMillis = formatter.parse(date4calendar.split("~")[0].split("T")[0]).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
				message.setResult(false);
				message.setMessage("将date活动日期字符串转化为格里高利历偏移量是出现错误，新建终止");
				ActionContext.getContext().getValueStack().push(message);
				return "message";
			}
		}

		if ((activityDateTimeMillis - currentTimeMillis) < (1000L * 60 * 60 * 24)) {
			// 新建活动的日期距离今天不足1天，不予创建
			message.setResult(false);
			message.setMessage("新建活动的日期距离今天不足1天，不予创建");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// --------------------验证参与人数限制类型和参与人数限制--------------------
		if (!"1".equals(type) && !"2".equals(type)) {
			// type的值既不等于1也不等于2，则修正为默认值1
			type = "1";
		}
		if ("2".equals(type)) {
			// 进一步验证人数限制是否在1~当前层级的max人数上限
			int min = 1;
			int max = 0;
			Set<Member> members = null;

			String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			switch (levelTag) {
			case "minus_first":
				MinusFirstLevel mfl = minusFirstLevelService.queryEntityById(lid);
				// 获取到当前层级对象的所有成员的集合
				members = mfl.getMembers();
				break;
			case "zero":
				ZeroLevel zl = zeroLevelService.queryEntityById(lid);
				members = zl.getMembers();
				break;
			case "first":
				FirstLevel fl = firstLevelService.queryEntityById(lid);
				members = fl.getMembers();
				break;
			case "second":
				SecondLevel sc = secondLevelService.queryEntityById(lid);
				members = sc.getMembers();
				break;
			case "third":
				ThirdLevel tl = thirdLevelService.queryEntityById(lid);
				members = tl.getMembers();
				break;
			case "fourth":
				FourthLevel fol = fourthLevelService.queryEntityById(lid);
				members = fol.getMembers();
				break;
			}
			if (null == members) {
				// 防止出现空指针异常
				members = new HashSet<Member>();
			}
			// 最终获知到当前层级对象的所有成员数量
			max = members.size();
			if (max < min) {
				message.setResult(false);
				message.setMessage("你所管理的当前层级，人员数量低于最小值" + min + "请选择不设限制。");
				ActionContext.getContext().getValueStack().push(message);
				return "json";
			} else if (baoMingUplimit < min || baoMingUplimit > max) {
				message.setResult(false);
				message.setMessage("你所设置的活动参与人数" + baoMingUplimit + "不在" + min + "~" + max + "之间，不予新建活动");
				ActionContext.getContext().getValueStack().push(message);
				return "json";
			}
		} else {
			// 如果活动人员限制类型（type）不是2（限制人数），则应该把baoMingUplimit字段设置为-1，表明此次活动没有人员数量限制。
			baoMingUplimit = -1;
		}

		// ---------------校验活动持续时间——hour字段------------------
		if ("1".equals(activityType)) {
			// 室外活動
			if (hour < 1 || hour > 12) {
				hour = 1;
			}
		}
		// TODO
		// 校验活动积分：实际应该“参与人数*score”得到单次活动花费，不应小于当前doingProject的lastLaborCost（项目剩余积分）
		if (score < 0 || score > 5) {
			score = 0;
		}
		// ----------------------校验完毕，当程序执行到这里的时候就代表所有参数都正确，可以着手执行新建活动的逻辑了----------------------
		Activity activity = new Activity();
		activity.setName(name);
		activity.setDescription(description);
		activity.setScore(score);
		activity.setType(type);
		activity.setBaoMingUplimit(baoMingUplimit);
		activity.setActivityType(activityType);
		// 报名的开始时间就是现在
		activity.setBaoMingBeginTime(System.currentTimeMillis());
		// 报名的截止时间是活动当天的00:00
		activity.setBaoMingEndTime(activityDateTimeMillis);
		// 设置活动开始时间的yyyy-MM-dd HH:mm:ss 格式的准确时间的格里高利历偏移量
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if ("1".equals(activityType)) {
				// 室外活動
				activityDateTimeMillis = (formatter.parse(date4selector)).getTime();
			} else {
				// 室內活動
				StringBuffer sb = new StringBuffer();
				String[] split = date4calendar.split("~")[0].split("T");
				sb.append(split[0]);
				sb.append(" ");
				sb.append(split[1]);
				activityDateTimeMillis = (formatter.parse(sb.toString())).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			message.setResult(false);
			message.setMessage("将today转化为格里高利历偏移量时出现错误，新建终止");
			ActionContext.getContext().getValueStack().push(message);
			return "message";
		}
		activity.setActivityBeginTime(activityDateTimeMillis);
		// 设置活动结束时间的准确格里高利历偏移量
		long activityEndTime = 0;
		if ("1".equals(activityType)) {
			// 室外活動
			activityEndTime = activityDateTimeMillis + 1000 * 60 * 60 * hour;
		} else {
			// 室內活動
			StringBuffer sb = new StringBuffer();
			String[] split = date4calendar.split("~")[1].split("T");
			sb.append(split[0]);
			sb.append(" ");
			sb.append(split[1]);
			try {
				activityEndTime = (formatter.parse(sb.toString())).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		activity.setActivityEndTime(activityEndTime);

		String aid = UUID.randomUUID().toString();
		String qrcodeUri = QRCodeUtils.createActivityQR(aid);
		activity.setQrcodeUrl(qrcodeUri);
		activity.setAid(aid);

		// 保存活动签到模式——“1”是签到和签退分开；“2”是同步签到签退
		if (1 == this.getSychronizeRadio()) {
			// 签到和签退分开进行
			activity.setSynchronize(false);
		} else if (2 == this.getSychronizeRadio()) {
			// 同步完成签到和签退
			activity.setSynchronize(true);
		}

		// 保存活动是否需要电子签名——“1”不需要；“2”需要
		if(1==this.getSigninRadio()){
			// 不需要
			activity.setNeedSignin(false);
		}else if(2==this.getSigninRadio()) {
			// 需要
			activity.setNeedSignin(true);
		}
		
		// 由于Activity和Article是一一对应的关系，因此必须在数据库同时存在activity和article
		Article art = new Article();
		art.setContent("");
		art.setForwardingNum(0);
		art.setReadingNum(0);
		art.setTitle("");
		art.setActivity(activity);
		activity.setArticle(art);

		// 设置剩余的其他内容
		activity.setProject(doingProject);
		/*
		 * ★★★★★ 容器来承装从表对象时，Hibernate为了能辨识出存入List容器中从表对象的先后顺序，
		 * 会在所关联的从表中加入一个我通常会命名为“index4主表名”的字段用来存放从表新建的先后次序，
		 * 这样当Hibernate级联地从从表中获取与主表管理的数据到List容器中的时候，就能通过这个次序在容器中排列好先后顺序。
		 * 因此在实际新建从表数据的时候，应该获取到主表对象，然后从主表中获取到list容器然后调用list的add()
		 * 方法将新建的从表对象放入到容器中，
		 * 然后从表中的主表字段也要引用到主表对象，最后再级联地保存从表对象就能完成新建从表和确定从表次序的工作。否则，
		 * 如果只是通过从表中的主表引用来引用主表，
		 * 而不在主表的list容器中添加新建的从表对象，那么新建的从表对象的“index4主表名”的字段就会为null缺少排列序号。
		 */
		doingProject.getActivities().add(activity);
		if ("1".equals(activityType)) {
			// 室外活动，关联Geo
			Geographic geo = geographicService.queryEntityById(geoid);
			activity.setGeographic(geo);
			geo.getActivities().add(activity);
		} else {
			// 室内活动，关联House
			House house = houseService.queryEntityById(hid);
			activity.setHouse(house);
			house.getActivities().add(activity);
		}

		// activity的bean数据填装完成，与所属的doingProject也已经进来关联，现在可以级联向数据库保存activity了
		activityService.save(activity);

		message.setMessage("名为" + name + "的活动创建成功！");
		message.setResult(true);
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}

	/**
	 * AJAX 获取指定activity的信息到前端显示
	 * 
	 * @return
	 */
	public String showDetialModal() {

		Activity a = activityService.queryEntityById(this.activity.getAid());
		Activity ra = new Activity();
		ra.setName(a.getName());
		ra.setActivityType(a.getActivityType());
		// 室外活动
		ra.setGeographic(a.getGeographic());
		// 室内活动
		ra.setHouse(a.getHouse());
		ra.setDescription(a.getDescription());
		ra.beginTimeStr = a.getBeginTimeStr();
		ra.endTimeStr = a.getEndTimeStr();
		// 判断qrcode的uri相对路径下的qrcode是否还存在
		ServletContext context = ServletActionContext.getServletContext();
		String realPath = context.getRealPath(File.separator + a.getQrcodeUrl());
		File file = new File(realPath);
		if(!file.exists()) {
			// 文件不存在则直接重新生成qrcode，并更新数据库的qrcodeUrl字段
			String uriPath = QRCodeUtils.createActivityQR(a.getAid());
			a.setQrcodeUrl(uriPath);
			activityService.update(a);
		}
		ra.setQrcodeUrl(context.getContextPath() + "/" + a.getQrcodeUrl());
		ra.setAid(a.getAid());

		ActionContext.getContext().getValueStack().push(ra);
		return "json";
	}

	/**
	 * 【完成】 AJAX 获取当前层级对象管理者所能发起活动的开始日期（最早也是明天）和最远的活动预约天数 活动预约天数随着层级对象的不同而不同，
	 * 社区管理者每周三就可以开始预约下周的活动（最大预约天数为11天）， 第一层级每周四可以开启下周活动的预约（最大提前10天），
	 * 第二层级为周五就可以提前安排下周活动（提前9天）， 第三层级周六可以安排下周活动（提前8天），
	 * 
	 * @return
	 */
	public String getStartDayAndEndDayByAjax() {

		ReturnMessage4StartDayAndEndDay result = null;

		// 由于创建活动页面是通过 层级对象 → 默认项目 → 新建活动 实现的，因此必定属于某个层级对象的
		DoingProject doingProject = doingProjectService.queryEntityById(this.dpid);
		if (null != doingProject.getMinusFirstLevel() && null == doingProject.getZeroLevel()) {
			// 新建活动的是街道层级对象
			// TODO 街道只能使用街道自己的房屋，而其他层级对象只能使用社区的房屋

		} else if (null != doingProject.getZeroLevel() && null == doingProject.getFirstLevel()) {
			// 新建活动的是社区层级
			result = ActivityUtils.getStartDayAndEndDay(0);
		} else if (null != doingProject.getFirstLevel() && null == doingProject.getSecondLevel()) {
			// 新建活动的是第一层级
			result = ActivityUtils.getStartDayAndEndDay(1);
		} else if (null != doingProject.getSecondLevel() && null == doingProject.getThirdLevel()) {
			// 新建活动的是第二层级
			result = ActivityUtils.getStartDayAndEndDay(2);
		} else if (null != doingProject.getThirdLevel()) {
			// 新建活动的是第三层级
			result = ActivityUtils.getStartDayAndEndDay(3);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 用于补签到的逻辑功能
	 * 
	 * @return
	 */
	public String buqian() {
		// 准备必要数据信息
		ReturnMessage4Common message = new ReturnMessage4Common();
		String aid = this.activity.getAid();
		String uid = this.getUid();
		Activity a = activityService.queryEntityById(aid);
		User u = userService.queryEntityById(uid);
		// 先检查是否能查找到该user，如果找不到说明前端扫描的二维码有问题，直接范围错误信息
		if (null == u) {
			message.setResult(false);
			message.setMessage("该二维码可能有误，不存在所指定用户");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}
		// 查看该用户是否已经加入层级，如果没有则直接返回错误信息
		if (null == u.getMembers() || 0 == u.getMembers().size()) {
			message.setResult(false);
			message.setMessage("该用户不在活动发起方的管理之下，无法完成补签");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}
		// 开始遍历该用户中的所有成员，看该用户是否属于当前活动发起方层级之下
		// 首先确定项目发起方是哪个层级的
		String lid = "";
		boolean userIsHere = false; // 待补签用户是否是活动发起方管辖之下
		if (a.getProject().getThirdLevel() != null) {
			// 活动发起方为一个thirdLevel层级对象
			lid = a.getProject().getThirdLevel().getThid();
			for (Member m : u.getMembers()) {
				if (null!=m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)) {
					userIsHere = true;
					break;
				}
			}
		} else if (a.getProject().getSecondLevel() != null) {
			// 活动发起方为一个secondLevel层级对象
			lid = a.getProject().getSecondLevel().getScid();
			for (Member m : u.getMembers()) {
				if (null!=m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)) {
					userIsHere = true;
					break;
				}
			}
		} else if (a.getProject().getFirstLevel() != null) {
			// 活动发起方为一个firstLevel层级对象
			lid = a.getProject().getFirstLevel().getFlid();
			for (Member m : u.getMembers()) {
				if (null!=m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)) {
					userIsHere = true;
					break;
				}
			}
		} else if (a.getProject().getZeroLevel() != null) {
			// 活动发起方为一个zeroLevel层级对象
			lid = a.getProject().getZeroLevel().getZid();
			for (Member m : u.getMembers()) {
				if (null!=m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid)) {
					userIsHere = true;
					break;
				}
			}

		} else if (a.getProject().getMinusFirstLevel() != null) {
			// 活动发起方为一个minusFirstLevel层级对象
			lid = a.getProject().getMinusFirstLevel().getMflid();
			for (Member m : u.getMembers()) {
				if (null!=m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)) {
					userIsHere = true;
					break;
				}
			}
		}
		// 最终判断被补签用户是否在层级管理之下
		if (!userIsHere) {
			message.setResult(false);
			message.setMessage("该用户不在活动发起方的层级管理之下，无法签到");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// 如果用户存在且属于层级管理之下，则遍历当前activity的visitor中是否已经存在该用户的信息，如果是则在此基础上完成签到，如果没有则新建visitor完成签到
		List<Visitor> visitors = a.getVisitors();
		Visitor visitor = null;
		Signin  signin = null;
		for (Visitor v : visitors) {
			if (v.getUser().getUid().equals(uid)) {
				visitor = v;
				break;
			}
		}
		if (null == visitor) {
			// 补签用户没有报名，则新建一个visitor
			visitor = new Visitor();
			signin = new Signin();
			visitor.setScore(a.getScore());
			u.setScore(u.getScore() + a.getScore());
			visitor.setActivity(a);
			visitor.setUser(u);
			// 设置签到和签退时间，均为当前补签时间
			long currentTimeMillis = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateTimeStr = formatter.format(new Date(currentTimeMillis));
			visitor.setStartTime(currentTimeMillis);
			visitor.setEndTime(currentTimeMillis);
			visitor.setStartTimeStr(dateTimeStr);
			visitor.setEndTimeStr(dateTimeStr);
			// 设置默认服务时常为活动总时间
			visitor.setWorkTime(a.getActivityEndTime() - a.getActivityBeginTime());
			// 检查activity的visitors容器是否存在，不存在手动创建
			if (null == a.getVisitors()) {
				a.setVisitors(new ArrayList<Visitor>());
			}
			
			visitor.setSignin(signin);
			signin.setVisitor(visitor);
			/*
			 * 因为activity和user中的visitors（List）容器会保留visitor的先后顺序，为了让Hibernate自动维护顺序，
			 * 也需要向a和u的List容器中添加新建的visitor
			 */
			a.getVisitors().add(visitor);
			u.getVisits().add(visitor);
			// 分别保存visitor和更新user、activity级联保存
			visitorService.save(visitor);
			activityService.update(a);
			userService.update(u);
		} else {
			// 补签用户已经报名,直接使用已有的visitor即可
			// 先检查该用户是否已经完成钱到了，防止重复签到
			if(-1!=visitor.getScore()){
				// 新建visitor的默认数值就是-1，如果没签到则其默认值仍然保持为-1
				message.setResult(true);
				message.setMessage("用户：" + u.getUsername() + "已于"+visitor.getEndTimeStr()+"完成签到，无需补签");
				ActionContext.getContext().getValueStack().push(message);
				return "json";
			}
			// 没有签到则执行补签逻辑
			visitor.setScore(a.getScore());
			u.setScore(u.getScore() + a.getScore());
			long currentTimeMillis = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateTimeStr = formatter.format(new Date(currentTimeMillis));
			visitor.setStartTime(currentTimeMillis);
			visitor.setEndTime(currentTimeMillis);
			visitor.setStartTimeStr(dateTimeStr);
			visitor.setEndTimeStr(dateTimeStr);
			visitor.setWorkTime(a.getActivityEndTime() - a.getActivityBeginTime());
			// visitor与user和activity级联更新
			visitorService.update(visitor);
		}
		// 补签完成，向前端返回结果
		message.setResult(true);
		message.setMessage("用户：" + u.getUsername() + "补签成功！");
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}

	
	// ===========================下载签到表===========================
	// 下载文件用的输入流
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	// 下载文件用的文件名
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 相应来自
	 * @return
	 * @throws IOException 
	 */
	public String downloadSigninList() throws IOException{
		String aid = this.activity.getAid();
		Activity a = activityService.queryEntityById(aid);
		
		// 创建
		String fullPath;
		ExportDoc maker = new ExportDoc("UTF-8");
		fullPath = "download"+File.separator+"signinList";
		FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fullPath));
		fullPath += File.separator+a.getName()+".doc";
		fullPath = ServletActionContext.getServletContext().getRealPath(fullPath);
        try {
			maker.exportDoc4SigninList(a, fullPath, "signinList.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		} 
        // -------------------独立准备Struts2用的下载用输入流--------------------
        File file = new File(fullPath);
        if(!file.exists()){
        	// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
        	this.errorMessage = "路径："+fullPath+"下，不存在指定文件，请稍后再试！";
        	// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
        	return "error";
        }
        // 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
        FileInputStream fis = new FileInputStream (file);
        // 准备字节数组（字节缓冲区）输出流备用
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        // 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
		byte[] buff = new byte[1024];  
		int rc = 0;  
		while ((rc = fis.read(buff, 0, 1024)) > 0) {  
			swapStream.write(buff, 0, rc);  
		}
		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
		this.inputStream=new ByteArrayInputStream(swapStream.toByteArray());
        // 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
        fis.close();
        swapStream.close();
        file.delete();
        // ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
        String s  =  a.getName()+".doc";
        this.fileName = new String(s.getBytes(),"ISO8859-1");
        
		return "download";
	}
	
}
