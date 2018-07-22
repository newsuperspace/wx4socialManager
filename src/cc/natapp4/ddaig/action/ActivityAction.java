package cc.natapp4.ddaig.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4CreateActivity;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("activityAction")
@Scope("prototype")
@Lazy(true)
public class ActivityAction extends ActionSupport implements ModelDriven<Activity> {

	// ==============================DI注入==============================
	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "doingProjectAction")
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
		ActionContext.getContext().put("dpid", this.getDpid());
		return "create";
	}

	public String createActivity() {
		ReturnMessage4CreateActivity message = new ReturnMessage4CreateActivity();

		String name = this.activity.getName();
		String dpid = this.dpid;
		String description = this.activity.getDescription();
		String type = this.activity.getType();  // 默认为1
		int baoMingUplimit = this.activity.getBaoMingUplimit();  // 如果type=2 则这个属性才有意义，默认为1
		String date = this.date;
		int hour = this.hour;  // 默认为1
		int score = this.activity.getScore();  // 默认为0

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
		}
		long activityDateTimeMillis = 0;
		try {
			activityDateTimeMillis = (formatter.parse(date.split(" ")[0])).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if ((activityDateTimeMillis - currentTimeMillis) < (1000 * 60 * 60 * 24)) {
			// 新建活动的日期距离今天不足1天，不予创建
			message.setResult(false);
			message.setMessage("新建活动的日期距离今天不足1天，不予创建");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}

		//

		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}
}
