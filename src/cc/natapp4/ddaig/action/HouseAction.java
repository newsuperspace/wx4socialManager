package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4ShowUpdateModal4House;
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
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.json.returnMessage.Json4FullCalendar;

@Controller("houseAction")
@Scope("prototype")
@Lazy(true)
public class HouseAction extends ActionSupport implements ModelDriven<House> {

	/**
	 * 
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

	// ==============================属性驱动==============================

	// ==============================模型驱动==============================
	private House house;

	@Override
	public House getModel() {
		this.house = new House();
		return this.house;
	}
	// ==============================Methods==============================

	/**
	 * 获取到当前社区管理者所管理的所有房屋数据，然后跳转到houseList.jsp页面
	 * 
	 * @return
	 */
	public String getHouseList() {
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

		// 获取当前操作者层级的相关数据
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		// 用来存放向前端反馈的活动室对象数据
		List<House> houses = null;
		if (isAdmin) {
			// 系统管理员，将获取系统中所有活动室（街道层级+社区层级两个层级所创建的活动室）的情况
			houses = houseService.queryEntities();
		} else {
			houses = new ArrayList<House>();
			/*
			 * 目前来说，对于非admin用户，只有社区能创建活动室（以后minus_first层级可能也要加入活动室创建功能）
			 * 但是为了能让一些子层级对象实现活动室管理排班功能，他们需要能“查询”到活动室，为此我们要进行一些层级上的筛选
			 */
			switch (tag) {
			case "minus_first": // 对于minus_first层级操作者，他应该可以看到自己所辖所有zero层级的活动室以及自己创建的活动室
				MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
				houses.addAll(minusFirstLevel.getHouses());
				for (ZeroLevel zl : minusFirstLevel.getChildren()) {
					houses.addAll(zl.getHouses());
				}
				break;
			case "zero": // 对于zero层级操作者，只需要返回其自己社区内的活动室即可
				ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
				houses.addAll(zeroLevel.getHouses());
				break;

			/*
			 * TODO 下面的case就比较特殊，他们没有创建活动时的功能，但是可以根据需要查询到指定活动室的使用详情，比如说：
			 * （1）高层级管理者想要知道自己子层级在社区内的室内活动的情况；
			 * （2）社区委派某个子层级（zero以下，例如一个first层级专门负责管理社区内活动时，
			 * 而旗下的second层级对象每个负责一个活动室） 则需要了解到一周的活动情况 （3）.。。。。
			 */
			case "first":

				break;
			case "second":

				break;
			case "third":

				break;
			case "fourth":

				break;
			}
		}

		ActionContext.getContext().put("houses", houses);
		return SUCCESS;
	}

	/**
	 * AJAX 创建活动室
	 * 同时肩负 zero层级和minus_first层级的创建活动室的功能
	 * 
	 * @return
	 */
	public String createHouse() {

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

		// 获取当前操作者层级的相关数据
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		ReturnMessage4Common result = new ReturnMessage4Common();
		// 开始一段严谨的逻辑判断，主要目的是判断当前操作者层级是否是minusFirstLevel或zeroLevel层级对象
		if (StringUtils.isEmpty(tag) || StringUtils.isEmpty(lid)) {
			result.setResult(false);
			result.setMessage("当前操作者的tag或lib数据为空，不能确定当前操作者身份，创建失败");
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		} else if ("zero".equals(tag)) {
			// 社区层级创建房屋
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				result.setResult(false);
				result.setMessage("当前操作者不存在，创建失败");
				ActionContext.getContext().getValueStack().push(result);
				return "json";
			}

			// 说明当前操作者不是管理员，而是社区层级管理者
			House h = new House();
			h.setName(this.house.getName());
			h.setDescription(this.house.getDescription());

			List<House> houses = zeroLevel.getHouses();
			if (null == houses) {
				houses = new ArrayList<House>();
				zeroLevel.setHouses(houses);
			}
			houses.add(h);
			h.setZeroLevel(zeroLevel);
			houseService.save(h);
		}else if("minus_first".equals(tag)){
			// 街道层级创建房屋
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			if (null == minusFirstLevel) {
				result.setResult(false);
				result.setMessage("当前操作者不存在，创建失败");
				ActionContext.getContext().getValueStack().push(result);
				return "json";
			}

			// 说明当前操作者不是管理员，而是社区层级管理者
			House h = new House();
			h.setName(this.house.getName());
			h.setDescription(this.house.getDescription());

			List<House> houses = minusFirstLevel.getHouses();
			if (null == houses) {
				houses = new ArrayList<House>();
				minusFirstLevel.setHouses(houses);
			}
			houses.add(h);
			h.setMinusFirstLevel(minusFirstLevel);
			houseService.save(h);
		}else{
			result.setResult(false);
			result.setMessage("现阶段只允许minus_first和zero层级创建和管理活动室，非法操作者创建失败");
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}
		

		result.setResult(true);
		result.setMessage("创建成功！");
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 获取指定活动室的数据信息，返回给前端用作修改Modal的信息显示
	 * 
	 * @return
	 */
	public String showUpdateModal() {
		String hid = this.house.getHid();

		House h = houseService.queryEntityById(hid);

		ReturnMessage4ShowUpdateModal4House result = new ReturnMessage4ShowUpdateModal4House();
		if (null != h) {
			result.setHouse(h);
			result.setResult(true);
		} else {
			result.setResult(false);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 更新活动室数据
	 * 
	 * @return
	 */
	public String updateHouse() {
		ReturnMessage4Common result = new ReturnMessage4Common();
		House h = houseService.queryEntityById(this.house.getHid());
		if (null == h) {
			result.setMessage("不存在您所要更新的活动室");
		} else {
			h.setName(house.getName());
			h.setDescription(house.getDescription());
			h.setLongitude(house.getLongitude());
			h.setLatitude(house.getLatitude());
			h.setRadus(house.getRadus());
			houseService.update(h);
			result.setMessage("更新成功！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 关闭房屋使用权,不影响已经预约的活动，但新建活动时不能再选择该房屋
	 * 
	 * @return
	 */
	public String closeHouse() {
		House h = houseService.queryEntityById(this.house.getHid());

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (null == h) {
			result.setMessage("不存在指定活动室");
		} else {
			h.setEnable(false);
			houseService.update(h);
			result.setMessage("活动室已关闭");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 开放房屋
	 * 
	 * @return
	 */
	public String openHouse() {
		House h = houseService.queryEntityById(this.house.getHid());
		ReturnMessage4Common result = new ReturnMessage4Common();
		if (null == h) {
			result.setMessage("不存在指定活动室");
		} else {
			h.setEnable(true);
			houseService.update(h);
			result.setMessage("活动室已开放");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 删除房屋
	 * 
	 * @return
	 */
	public String deleteHouse() {
		ReturnMessage4Common result = new ReturnMessage4Common();
		House h = houseService.queryEntityById(this.house.getHid());

		if (null == h) {
			result.setMessage("您所要删除的活动室不存在");
		} else {
			// 从zeroLevel的houses列表中移除待删除房屋，这样Hibernate会自动维护index序号
			h.getZeroLevel().getHouses().remove(h);
			// 切断house与原所属zeroLevel的外键关联
			h.setZeroLevel(null);
			// 正式从数据库中删除house
			houseService.delete(h);
			result.setMessage("删除成功！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 从数据库获取到活动室数据信息放入到值栈中， 然后跳转到house4month.jsp中显示
	 * 
	 * @return
	 */
	public String getMonthView() {

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
		// 用于存放向前端返回的活动室数据
		List<House> houses = null;    
		if (isAdmin) {
			// 如果当前来访者是系统管理员，则他有权查看系统中所有社区的所有活动室
			houses = houseService.queryEntities();
		} else {
			// 非Admin操作者目前只允许zero层级管理者
			String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			// 开始判断当前操作者是不是zeroLevel层级对象
			if (StringUtils.isEmpty(tag) || StringUtils.isEmpty(lid)) {
				System.out.println("当前操作者的tag或lib数据为空，不能确定当前操作者身份，创建失败");
				return "month";
			} else if ("zero".equals(tag)) {
				// 当前操作者为“社区”，获取社区管理之下的房屋
				ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
				if (null == zeroLevel) {
					System.out.println("当前操作者不存在，创建失败");
					return "month";
				}
				houses = zeroLevel.getHouses();
			}else if("minus_first".equals(tag)){
				MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
				if (null == minusFirstLevel) {
					System.out.println("当前操作者不存在，创建失败");
					return "month";
				}
				houses = minusFirstLevel.getHouses();
			}else{
				System.out.println("现阶段只允许minus_first、zero层级创建活动室，非法操作者获取房屋数据失败");
				return "month";
			}
		}

		ActionContext.getContext().put("houses", houses);
		return "month";
	}

	/**
	 * 【半完成】 AJAX 获取指定活动室当月的全部活动数据，并根据FullCalendar的规定属性组织成Json4FullCalendar
	 * 然后依次放入到List容器中以JSON形式返回给前端，则前端的FullCalendar的API会将该JSon作为
	 * 日历的数据源，在日历上动态显示事件图标信息
	 * 
	 * TODO 未来可能街道也有自己的活动场地，因此街道可以使用自己的活动场地，而社区及社区层级
	 * 之下的所有层级使用社区的场地，因此要根据未来的业务变化而更改
	 * 
	 * @return
	 */
	public String getEventSource4month() {

		// 再找到所查找的房屋
		String hid = this.house.getHid();
		House h = houseService.queryEntityById(hid);
		// 准备用作Fullcalendar数据源的容器
		Json4FullCalendar json = null;
		List<Json4FullCalendar> list = new ArrayList<Json4FullCalendar>();
		List<Activity> activities = activityService.getActivities4House(hid);
		for (Activity a : activities) {
			json = new Json4FullCalendar();
			String[] time = a.getBeginTimeStr().split(" ");
			json.setStart(time[0] + "T" + time[1]);
			time = a.getEndTimeStr().split(" ");
			json.setEnd(time[0] + "T" + time[1]);
			json.setId(a.getAid());
			json.setBackgroundColor("yellow");
			json.setTextColor("black");
			json.setTitle(a.getName());
			// json.setUrl("activityAction_*.action?aid="+a.getAid());
			list.add(json);
		}

		ActionContext.getContext().getValueStack().push(list);
		return "json";
	}

}
