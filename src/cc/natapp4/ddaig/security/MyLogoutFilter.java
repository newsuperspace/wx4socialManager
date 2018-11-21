package cc.natapp4.ddaig.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 为什么要自己继承一个shiro退出的过滤器呢？
 * 那是因为我们在用户退出管理层后，必须清除session域中的lid和tag，
 * 以便在下次登录时重新填入需要登录的lid和tag
 * 
 * @author Administrator
 *
 */
public class MyLogoutFilter extends LogoutFilter {

	/**
	 * 这里的两个属性是基于在web.xml中设置了监听器
	 * <listener>
	 *		<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
	 *	</listener>
	 *然后我们在任何类中通过如下方式都可以获取到当前容器的session和request了
	 */
//	@Autowired
//	private HttpSession session;
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		
		// 以下几个字段是涉及shiro认证、当前操作者层级定位的关键信息，为了防止对以后的认证产生干扰，必须在退出时清空数据
		session.setAttribute("lid", "");
		session.setAttribute("tag", "");
		session.setAttribute("username", "");
		session.setAttribute("password", "");
		session.setAttribute("openid", "");
		
		return super.preHandle(request, response);
	}

	
}
