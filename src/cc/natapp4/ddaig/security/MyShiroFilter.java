package cc.natapp4.ddaig.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * 本filter是真正的Servlet的Filter与PermissionsAuthorizationFilter这种shiro的Filter作用时机更靠前，作用机理也不同。
 * 本Filter并非真正的Shiro的filterChainDefinitions链条中使用的filter，而是Serlvetfilter
 * 作用是判断从前端发来的访问究竟是“微信端”还是“桌面端”，如果是桌面端不做任何处理可如果是微信端就需要做一些预处理工作，
 * 以配合微信的一些功能发挥作用。
 * 
 * @author Administrator
 *
 */
public class MyShiroFilter extends DelegatingFilterProxy {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// -------------------用来获取当前来访者的OpenID和所要请求的URL，并将其放入到当前来访者的session中保存待用
		// 注意要从Filter中获取当前web应用的ServletContext，不能直接通过this.getServletContext()，而是要像下面这样从FilterConfig中获取
		ServletContext context = this.getFilterConfig().getServletContext();
		// 一定要将请求对象转变成HttpServletRequest类型★
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取请求参数字符串
		String parameter = req.getQueryString();
		System.out.println("\"请求\"的参数字符串：" + parameter);
		// 对比，通过request.getRequestURL()获取的URL是不包含请求参数的，如果要得到请求参数，需要request.getQueryString()用于获取参数信息
		StringBuffer urlStr = req.getRequestURL();
		System.out.println("\"请求\"的无参数URL:" + urlStr.toString());

		String code = req.getParameter("code");
		// 如果存在名为code的请求参数，说明本次来访是微信通过Author2.0跳转过来的请求
		if (!StringUtils.isEmpty(code)) {
			// 获取session
			HttpSession session = req.getSession();
			// 打印请求参数code
			System.out.println("\"请求\"包含code值：" + code);
			urlStr.append("?");
			urlStr.append(parameter);
			System.out.println("\"请求\"的完整wxURL：" + urlStr);
			
			session.setAttribute("wxURL", urlStr.toString());
		}
		
		// -------------------调用父类的doFilter，这是Shiro功能的真正入口所在
		super.doFilter(request, response, filterChain);
	}
}
