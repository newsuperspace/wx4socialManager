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
		System.out.println("本次访问的请求参数字符串：" + parameter);
		// 对比，通过request.getRequestURL()获取的URL是不包含请求参数的，如果要得到请求参数，需要request.getQueryString()用于获取参
		// 数信息
		StringBuffer urlStr = req.getRequestURL();
		System.out.println("本次访问的URL是:" + urlStr.toString());

		String code = req.getParameter("code");
		if (!StringUtils.isEmpty(code)) {
			// 获取session
			HttpSession session = req.getSession();
			// 打印请求参数code
			System.out.println("本次请求包含code值：" + code);
			urlStr.append("?");
			urlStr.append(parameter);
			System.out.println("最终存入Session的完整wxURL：" + urlStr);
			
			session.setAttribute("wxURL", urlStr.toString());
		}
		
		// -------------------调用父类的doFilter，这是Shiro功能的真正入口所在
		super.doFilter(request, response, filterChain);
	}
}
