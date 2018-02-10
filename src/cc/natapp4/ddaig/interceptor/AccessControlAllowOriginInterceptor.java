package cc.natapp4.ddaig.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AccessControlAllowOriginInterceptor implements Interceptor {

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		/**
		 * 拦截器中获取response、request的方法 ActionContext ctx =
		 * ActionContext.getContext(); HttpServletRequest request
		 * =(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 * HttpServletResponse response = (HttpServletResponse)
		 * ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
		 */
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext()
				.get(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext()
				.get(ServletActionContext.HTTP_REQUEST);

		// 防止表单乱码，因此告诉应用程序以码表——utf-8来解码请求正文
//		request.setCharacterEncoding("UTF-8");
		// 设置允许跨域请求的响应头
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 设置响应头，告诉浏览器以何种码表对响应正文进行解码
//		response.setContentType("text/html;charset=gb2312");

		return invocation.invoke();
	}

}
