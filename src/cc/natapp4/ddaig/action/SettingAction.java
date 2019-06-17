package cc.natapp4.ddaig.action;


import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.domain.setting.BaseSetting;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
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
	private String needJoinApply;
	public String getNeedJoinApply() {
		return needJoinApply;
	}
	public void setNeedJoinApply(String needJoinApply) {
		this.needJoinApply = StringUtils.trim(needJoinApply);
	}


	// =========================【Action方法列表】===========================
	/**
	 * 用户要访问基础设置页面，因此本方法的作用是从数据库的baseSetting表中将当前层级的所有基础设置读取出来，并返回到
	 * 其前端页面显示出来。
	 * 
	 * @return
	 */
	public String intoBaseSettingPage() {
	
		System.out.println("正在准备BaseSettingPage页面所需的设置数据");
		// 定位当前操作者层级
		String tag = (String)ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 查找当前操作者层级的配置数据
		BaseSetting setting = baseSettingService.getBaseSettingConfigByTagAndLid(tag, lid);
		// 将持久化状态对象放入到栈顶，直接返回给前端baseSetting.jsp页面通过Struts标签的OGNL表达式从中抽取数据组织页面显示
		ActionContext.getContext().getValueStack().push(setting);
		// 返回结果集索引字符串，跳转到指定JSP页面
		return "baseSettingPage";
	}
	
	
	/**
	 * 恢复初始化设置
	 * @return
	 */
	public String  defaultConfig(){
		ReturnMessage4Common message  = new ReturnMessage4Common("初始化成功",true);

		String tag = (String)ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		
		baseSettingService.defaultConfig(tag, lid);
		
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}
	
	
	/**
	 * 保存初始化设置
	 * @return
	 */
	public String saveConfig(){
		ReturnMessage4Common message  = new ReturnMessage4Common("保存成功",true);
		// 获取当前层级的关键信息
		String tag = (String)ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 从数据库中找到当前层级的配置对象
		BaseSetting baseSetting = this.baseSettingService.getBaseSettingConfigByTagAndLid(tag, lid);
		// 逐一将配置信息覆盖到配置对象中
		baseSetting.setNeedJoinApply(needJoinApply);
		
		// 在数据库中更新配置对象
		baseSettingService.update(baseSetting);
		// 保存成功，向前端返回信息
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}
	
	
	
}
