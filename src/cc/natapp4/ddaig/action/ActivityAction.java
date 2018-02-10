package cc.natapp4.ddaig.action;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("activityAction")
@Scope("prototype")
@Lazy(true)
public class ActivityAction extends ActionSupport implements ModelDriven<Activity>{

	//================================================DI 注入
	@Resource(name="activityService")
	private ActivityService  activityService;
	@Resource(name="userService")
	private  UserService userService;
	
	
	// 模型驱动——收纳请求参数
	private Activity activity;
	@Override
	public Activity getModel() {
		activity = new Activity();
		return this.activity;
	}

	/**
	 * 在后台，点击某个用户的积分，则会产生本次请求
	 * 请求会将所用户的uid传递过来，从而可以在数据库
	 * 中查找该用户所参与的全部Activity信息，并显示到后台指定JSP页面上
	 * @return
	 */
	public  String showList(){
		
		User user = userService.queryEntityById(this.uid);
		Set<Activity> activities = user.getActivities();

		ActionContext.getContext().put("activities", activities);
		
		return "showList";
	}
	private String uid;
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * 获取所有已经发起的活动Activity，并显示到后台指定的jsp页面上
	 * @return
	 */
	public String showAllList(){
		
		List<Activity> list = activityService.queryEntities();
		
		ActionContext.getContext().put("activities", list);
		
		return "showAllList";
	}
	
	/**
	 * 得到参加当前活动的全部User，并返回到一个visitorList.jsp，用来展示哪些人参加了活动。
	 * @return
	 */
	public String getVisitors(){
		
		String aid  =  this.activity.getAid();
		Activity a = activityService.queryEntityById(aid);
		Set<User> users = a.getUsers();
		
		ActionContext.getContext().put("visitors", users);
		return "visitorList";
	}
}
