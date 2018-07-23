package cc.natapp4.ddaig.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4CreateActivity;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("activityAction")
@Scope("prototype")
@Lazy(true)
public class ActivityAction extends ActionSupport implements ModelDriven<Activity> {

	// ==============================DI注入==============================
	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;

	// ==============================属性驱动==============================
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

	// 新建活动时，这里存放形如 2018-07-25 00:00:00 的活动日使劲按字符串，符合yyyy-MM-dd HH:mm:ss
	// 的DateFormat
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	 * 在后台，点击某个用户的积分，则会产生本次请求 请求会将所用户的uid传递过来，从而可以在数据库
	 * 中查找该用户所参与的全部Activity信息，并显示到后台指定JSP页面上
	 * 
	 * @return
	 */
	public String showList() {

		// User user = userService.queryEntityById(this.uid);
		// Set<Activity> activities = user.getActivities();
		//
		// ActionContext.getContext().put("activities", activities);

		return "showList";
	}

	/**
	 * 获取所有已经发起的活动Activity，并显示到后台指定的jsp页面上
	 * 
	 * @return
	 */
	public String showAllList() {

		List<Activity> list = activityService.queryEntities();

		ActionContext.getContext().put("activities", list);

		return "showAllList";
	}

	/**
	 * 得到参加当前活动的全部User，并返回到一个visitorList.jsp，用来展示哪些人参加了活动。
	 * 
	 * @return
	 */
	public String getVisitors() {
		//
		// String aid = this.activity.getAid();
		// Activity a = activityService.queryEntityById(aid);
		// Set<User> users = a.getUsers();
		//
		// ActionContext.getContext().put("visitors", users);
		return "visitorList";
	}

	public String createPage() {
		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		// 把活动所属的doingProject的dpid放入到map栈空间中，供给createActivity.jsp页面上通过struts的<s:property>标签显示在页面上
		ActionContext.getContext().put("dpid", this.getDpid());
		// 確定当前操作者（doingMan）所管理的层级对象，查找该层级对象之下的全部人员数量
		String tag = doingMan.getGrouping().getTag();
		Set<Member> members = new HashSet<Member>();
		switch (tag) {
		case "minus_first":
			Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
			MinusFirstLevel mfl = null;
			for (MinusFirstLevel m : mfls) {
				mfl = m;
			}
			// 获取到当前层级对象的所有成员的集合
			members = mfl.getMembers();
			break;
		case "zero":
			Set<ZeroLevel> zls = doingMan.getManager().getZls();
			ZeroLevel zl = null;
			for (ZeroLevel l : zls) {
				zl = l;
			}
			members = zl.getMembers();
			break;
		case "first":
			Set<FirstLevel> fls = doingMan.getManager().getFls();
			FirstLevel fl = null;
			for (FirstLevel l : fls) {
				fl = l;
			}
			members = fl.getMembers();
			break;
		case "second":
			Set<SecondLevel> scls = doingMan.getManager().getScls();
			SecondLevel sc = null;
			for (SecondLevel l : scls) {
				sc = l;
			}
			members = sc.getMembers();
			break;
		case "third":
			Set<ThirdLevel> tls = doingMan.getManager().getTls();
			ThirdLevel tl = null;
			for (ThirdLevel l : tls) {
				tl = l;
			}
			members = tl.getMembers();
			break;
		case "fourth":
			Set<FourthLevel> fols = doingMan.getManager().getFols();
			FourthLevel fol = null;
			for (FourthLevel l : fols) {
				fol = l;
			}
			members = fol.getMembers();
			break;
		}
		// 最终获知到当前层级对象的所有成员数量
		int size = members.size();
		// 将成员数量放入到Map栈空间中，以供createActivity.jsp中通过max='<s:property
		// value="%{'#size'}">' 使用
		ActionContext.getContext().put("size", size);
		// 执行跳转
		return "create";
	}

	public String createActivity() {
		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		ReturnMessage4CreateActivity message = new ReturnMessage4CreateActivity();

		String name = this.activity.getName();
		String dpid = this.dpid;
		String description = this.activity.getDescription();
		String type = this.activity.getType(); // 默认为1
		int baoMingUplimit = this.activity.getBaoMingUplimit(); // 如果type=2
																// 则这个属性才有意义，最少为1，最大为活动所属层级的总人员数量上限
		String date = this.date;
		int hour = this.hour; // 默认为1
		int score = this.activity.getScore(); // 默认为0

		// 校验关键input字段信息是否为null
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(dpid) || StringUtils.isEmpty(description)
				|| StringUtils.isEmpty(date)) {
			// 如果name、description、dpid、date有一个为"",则不予新建活动
			message.setResult(false);
			message.setMessage("关键信息为空，不予新建活动");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// 校验当前所新建活动所属的doingProject是否存在
		DoingProject doingProject = doingProjectService.queryEntityById(dpid);
		if (null == doingProject) {
			message.setResult(false);
			message.setMessage("dpid为" + dpid + "的doingProject对象不存在");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// 校验活动时间是否相较于当前时间为提前1天
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
		try {
			activityDateTimeMillis = (formatter.parse(date.split(" ")[0])).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			message.setResult(false);
			message.setMessage("将date活动日期字符串转化为格里高利历偏移量是出现错误，新建终止");
			ActionContext.getContext().getValueStack().push(message);
			return "message";
		}
		if ((activityDateTimeMillis - currentTimeMillis) < (1000 * 60 * 60 * 24)) {
			// 新建活动的日期距离今天不足1天，不予创建
			message.setResult(false);
			message.setMessage("新建活动的日期距离今天不足1天，不予创建");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		// 验证参与人数限制类型和参与人数限制
		if (!"1".equals(type) && !"2".equals(type)) {
			// type的值既不等于1也不等于2，则修正为默认值1
			type = "1";
		}
		if ("2".equals(type)) {
			// 进一步验证人数限制是否在1~当前层级的max人数上限
			int min = 1;
			int max = 0;
			String tag = doingMan.getGrouping().getTag();
			Set<Member> members = new HashSet<Member>();
			switch (tag) {
			case "minus_first":
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel mfl = null;
				for (MinusFirstLevel m : mfls) {
					mfl = m;
				}
				// 获取到当前层级对象的所有成员的集合
				members = mfl.getMembers();
				break;
			case "zero":
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				ZeroLevel zl = null;
				for (ZeroLevel l : zls) {
					zl = l;
				}
				members = zl.getMembers();
				break;
			case "first":
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				FirstLevel fl = null;
				for (FirstLevel l : fls) {
					fl = l;
				}
				members = fl.getMembers();
				break;
			case "second":
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				SecondLevel sc = null;
				for (SecondLevel l : scls) {
					sc = l;
				}
				members = sc.getMembers();
				break;
			case "third":
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				ThirdLevel tl = null;
				for (ThirdLevel l : tls) {
					tl = l;
				}
				members = tl.getMembers();
				break;
			case "fourth":
				Set<FourthLevel> fols = doingMan.getManager().getFols();
				FourthLevel fol = null;
				for (FourthLevel l : fols) {
					fol = l;
				}
				members = fol.getMembers();
				break;
			}
			// 最终获知到当前层级对象的所有成员数量
			max = members.size();
			if(max<min){
				message.setResult(false);
				message.setMessage("你所管理的当前层级，人员数量低于最小值"+min+"请选择不设限制。");
				ActionContext.getContext().getValueStack().push(message);
				return "json";
			}else if(baoMingUplimit<min || baoMingUplimit>max){
				message.setResult(false);
				message.setMessage("你所设置的活动参与人数"+baoMingUplimit+"不在"+min+"~"+max+"之间，不予新建活动");
				ActionContext.getContext().getValueStack().push(message);
				return "json";
			}
		}else{
			// 如果活动人员限制类型（type）不是2（限制人数），则应该把baoMingUplimit字段设置为-1，表明此次活动没有人员数量限制。
			baoMingUplimit = -1;
		}
		
		// 校验活动持续时间——hour字段
		if(hour<1 || hour>12){
			hour = 1;
		}
		// TODO 校验活动积分：实际应该“参与人数*score”得到单次活动花费，不应小于当前doingProject的lastLaborCost（项目剩余积分）
		if(score<0 || score>5){
			score = 0;
		}
		// ----------------------校验完毕，当程序执行到这里的时候就代表所有参数都正确，可以着手执行新建活动的逻辑了----------------------
		Activity  activity =  new  Activity();
		activity.setName(name);
		activity.setDescription(description);
		activity.setScore(score);
		activity.setType(type);
		activity.setBaoMingUplimit(baoMingUplimit);
		
		// 报名的开始时间就是现在
		activity.setBaoMingBeginTime(System.currentTimeMillis());
		// 报名的截止时间是活动当天的00:00
		activity.setBaoMingEndTime(activityDateTimeMillis);
		// 设置活动开始时间的yyyy-MM-dd HH:mm:ss 格式的准确时间的格里高利历偏移量
		formatter = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			activityDateTimeMillis = (formatter.parse(date)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			message.setResult(false);
			message.setMessage("将today转化为格里高利历偏移量是出现错误，新建终止");
			ActionContext.getContext().getValueStack().push(message);
			return "message";
		}
		activity.setActivityBeginTime(activityDateTimeMillis);
		// 设置活动结束时间的准确格里高利历偏移量
		long activityEndTime = activityDateTimeMillis + 1000*60*60*hour;
		activity.setActivityEndTime(activityEndTime);
		
		// 设置剩余的其他内容
		activity.setProject(doingProject);
		String aid  =  UUID.randomUUID().toString();
		String qrcodeUri = QRCodeUtils.createActivityQR(aid);
		activity.setQrcodeUrl(qrcodeUri);
		activity.setAid(aid);
		
		// activity的bean数据填装完成，与所属的doingProject也已经进来关联，现在可以级联向数据库保存activity了
		activityService.save(activity);
		
		message.setMessage("名为"+name+"的活动创建成功！");
		message.setResult(true);
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}
}
