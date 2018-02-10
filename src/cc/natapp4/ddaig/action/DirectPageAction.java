package cc.natapp4.ddaig.action;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 当前Action用来 根据前端页面提交来的请求参数（？forward=“xxxx”）来请求转发到指定jsp页面（because所有JSP都在/wEB-INFO/jsp目录下）
 * 浏览器不能直接请求，只能通过本Action进行请求转发跳转，而请求参数forward的值就是这个jsp页面的名字
 * @author Administrator
 *
 */
@Controller("directPageAction")
@Scope("prototype")
@Lazy(true)
public class DirectPageAction extends ActionSupport {

	// -----------------属性驱动，获取用户请求的页面名称----------------------------
	private String forward;

	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}

	// ---------------------------方法-------------------------------
	@Override
	public String execute() throws Exception {
		return forward;
	}
	
	
	
	
	
	
}
