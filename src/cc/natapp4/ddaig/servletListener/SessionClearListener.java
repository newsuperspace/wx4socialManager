package cc.natapp4.ddaig.servletListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.struts2.ServletActionContext;

import cc.natapp4.ddaig.utils.FileController;

/**
 * 主要用于负责处理系统访问者在下载一些动态生成的word、excel数据文档时
 * 在/download目录中生成的文件。
 * 
 * 如果用户主动退出，则基于Shiro的/logout 过滤器会调用我自定义的MyLogoutFilter.preHandle()
 * 从而删除session中的数据及download中动态生成的数据文件。
 * 
 * 而如果用户的session是超时（30分钟）自动销毁的，则会通过本类的sessionDestoryed()执行类似功能
 * 
 * 
 * @author Administrator
 *
 */
public class SessionClearListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// 来访者的HttpSession创建前调用，这里暂时没用
	}

	/**
	 * 执行销毁download文件的任务
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// 先获取到session对象
		HttpSession session = event.getSession();
		ServletContext context = session.getServletContext();
		// 查看session中是否保存了uid数据，只有用户存在下载临时数据文件的行为时才会存在uid
		String uid  = (String) session.getAttribute("uid");
		if(null==uid){
			return;
		}else{
			String realPath = context.getRealPath("download"+File.separator+uid);
			boolean deleteResult = FileController.deleteDir(realPath);
			if(deleteResult){
				System.out.println("用户创建并下载的临时文件目录已删除");
			}else{
				System.out.println("用户创建并下载的临时文件目录删除失败");
			}
		}
	}

}
