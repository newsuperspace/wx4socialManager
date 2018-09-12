package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4ShowUpdateModal;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.UserService;

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
	@Resource(name = "geographicService")
	private GeographicService geographicService;

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

		List<House> houses = null;
		if (isAdmin) {
			// 系统管理员，将获取所有活动室的情况
			houses = houseService.queryEntities();
		} else {
			Set<ZeroLevel> zls = doingMan.getManager().getZls();
			for (ZeroLevel z : zls) {
				houses = z.getHouses();
			}
		}

		ActionContext.getContext().put("houses", houses);
		return SUCCESS;
	}

	/**
	 * AJAX 创建活动室
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

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (null != doingMan) {
			// 说明当前操作者不是管理员，而是社区层级管理者
			House h = new House();
			h.setName(this.house.getName());
			h.setDescription(this.house.getDescription());
			
			Set<ZeroLevel> zls = doingMan.getManager().getZls();
			ZeroLevel zero = null;
			for (ZeroLevel z : zls) {
				zero = z;
			}
			List<House> houses = zero.getHouses();
			if (null == houses) {
				houses = new ArrayList<House>();
				zero.setHouses(houses);
			}
			houses.add(h);
			h.setZeroLevel(zero);
			houseService.save(h);

			result.setResult(true);
			result.setMessage("创建成功！");
		} else {
			result.setResult(false);
			result.setMessage("您不是合法的社区层管理者，不能创建活动室");
		}

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

		ReturnMessage4ShowUpdateModal result = new ReturnMessage4ShowUpdateModal();
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
			// 删除房屋
			houseService.delete(h);
			result.setMessage("删除成功！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

}
