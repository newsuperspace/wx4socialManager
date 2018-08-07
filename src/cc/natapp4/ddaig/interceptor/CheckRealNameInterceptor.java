package cc.natapp4.ddaig.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * ChckRealNameInterceptor拦截器的作用是：
 * 当微信前端请求我们服务器的服务的时候（例如获取积分信息/扫码签到/登陆系统后台/扫码登陆...）这些功能的前提条件是
 * 必须先完成实名认证，否则不予响应。
 * 
 * 因此本拦截器就是考察以下情况：
 * （1）查看所请求的路径是否包含code，包含则为微信端请求，应该进行实名认证考察，否则是桌面端来访应该放行给shiro处理认证授权问题；
 * （2）包含code则
 * 
 * @author Administrator
 *
 */
public class CheckRealNameInterceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

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
		
		// 查看本次请求是否含有code请求参数
		String code = request.getParameter("code");
		if(StringUtils.isEmpty(code)){
			// 不含有code请求参数，本次请求不是来自微信端的，直接放行
			invocation.invoke();
		}
		
		// 剩下的部分就是着重处理从微信端发来的请求了
		
		
		
		
		return null;
	}

}
