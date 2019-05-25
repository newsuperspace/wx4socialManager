package cc.natapp4.ddaig.security;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.struts2.ServletActionContext;

import cc.natapp4.ddaig.utils.FileController;


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
		session.setAttribute("permissionStrings", null);
		/*
		 *  uid是在/download目录下唯一分割各个用户临时文件下载区域的标记，
		 *  无论用户是通过微信扫码登录、微信端直登（在session中只存在openid的值，不存在username的值）
		 *  还是用户名密码登录（只存在username，不存在openid）
		 *  只要它有从系统中下载数据临时文件，都会通过openid或username找到他的uid，并以此作为/download/uid 目录的次级路径名
		 *  该路径下存放其生成的数据文件，并供其下载，只是如果该用户选择推出或它的HttpSession失效，就直接删除 /download/uid目录即可
		 */
		String uid = (String) session.getAttribute("uid");
		System.out.println("打印session中的uid："+uid);
		ServletContext context = session.getServletContext();
		// 因为用户执行下载文件操作就需要它的UID在download中用作临时文件夹的名称
		if(null!=uid){
			// 执行与SessionClearListener类中相同的逻辑，删除该用户的下载数据用的临时文件夹
			String realPath = context.getRealPath("download"+File.separator+uid);
			boolean deleteResult = FileController.deleteDir(realPath);
			if(deleteResult){
				System.out.println("用户创建并下载的临时文件目录已删除");
			}else{
				System.out.println("用户创建并下载的临时文件目录删除失败");
			}
		}
		session.setAttribute("uid", "");
		System.out.println("===session数据清空完毕，已完成退出操作===");
		return super.preHandle(request, response);
	}

	
}
