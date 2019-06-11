package cc.natapp4.ddaig.action;


import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.service_interface.BaseSettingService;

@Controller(value="settingAction")
@Lazy(value=true)
@Scope(value="prototype")
public class SettingAction extends ActionSupport  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	// =========================【DI注入列表】===========================
	@Autowired
	private BaseSettingService baseSettingService;
	
	
	
	// =========================【属性驱动】===========================
	
	
	
	
	// =========================【Action方法列表】===========================
	/**
	 * 用户要访问基础设置页面，因此本方法的作用是从数据库的baseSetting表中将当前层级的所有基础设置读取出来，并返回到
	 * 其前端页面显示出来。
	 * 
	 * @return
	 */
	public String intoBaseSettingPage() {
	
		System.out.println("正在准备BaseSettingPage页面所需的设置数据");
		
		String tag = (String)ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		
		
		
		return "baseSettingPage";
	}
	
	
	
	
}
