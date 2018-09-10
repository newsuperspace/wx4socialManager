package cc.natapp4.ddaig.action;


import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("geographicAction")
@Scope("prototype")
@Lazy(true)
public class GeographicAction extends ActionSupport implements ModelDriven<Geographic>{
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
	

	// ==============================属性驱动==============================

	// ==============================模型驱动==============================
	private Geographic geographic;
	@Override
	public Geographic getModel() {
		this.geographic  =  new Geographic();
		return this.geographic;
	}
	// ==============================Methods==============================
	
	





}
