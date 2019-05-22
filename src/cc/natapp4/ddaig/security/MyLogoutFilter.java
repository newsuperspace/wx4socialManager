package cc.natapp4.ddaig.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.LogoutFilter;


/**
 * 为什么要自己继承一个shiro退出的过滤器呢？
 * 那是因为我们在用户退出管理层后，必须清除session域中的lid和tag，
 * 以便在下次登录时重新填入需要登录的lid和tag
 * 
 * @author Administrator
 *
 */
public class MyLogoutFilter extends LogoutFilter {

	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		// 以下几个字段是涉及shiro认证、当前操作者层级定位的关键信息，为了防止对以后的认证产生干扰，必须在退出时清空数据
		// --------selectorManager 阶段，根据前端用户的选择保存在session中备用的----------
		System.out.println("打印session中的lid："+session.getAttribute("lid"));
		session.setAttribute("lid", "");
		System.out.println("打印session中的tag："+session.getAttribute("tag"));
		session.setAttribute("tag", "");
		// ---------在shiroAction_login方法中保存的登录用户的个人关键数据-----------
		System.out.println("打印session中的username："+session.getAttribute("username"));
		session.setAttribute("username", "");
		System.out.println("打印session中的password："+session.getAttribute("password"));
		session.setAttribute("password", "");
		System.out.println("打印session中的openid："+session.getAttribute("openid"));
		session.setAttribute("openid", "");
		// --------在MyRealm和MyRealm4Input的doGetAuthorizationInfo()中解析的（lid和tag指定的层级对象）权限字符串的List容器
		System.out.println("打印session中的permissionStrings："+session.getAttribute("permissionStrings"));
		session.setAttribute("oppermissionStringsenid", null);
		System.out.println("===session数据清空完毕，已完成退出操作===");
		return super.preHandle(request, response);
	}

	
}
