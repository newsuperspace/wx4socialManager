package cc.natapp4.ddaig.security;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.PermissionLevelService;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.ConfigUtils;

/**
 * 本Realm是配合/WEB-INF/openJSP/signin.jsp使用的，主要的功能还是为了开发阶段测试使用
 * 因为开发阶段不可能接入微信，因此运行阶段是基于微信完成授权登录操作的，因此本Realm是免去使用微信端 而专门设计的。
 * 
 * @author Administrator
 *
 */
@Component("myRealm4Input")
@Lazy(true)
public class MyRealm4Input extends AuthorizingRealm {

	private UserService userService = null;
	@Resource(name = "permissionService")
	private PermissionService permissionService;
	@Resource(name = "permissionTypeService")
	private PermissionTypeService permissionTypeService;
	@Resource(name = "permissionLevelService")
	private PermissionLevelService permissionLevelService;
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;
	@Resource(name = "secondLevelService")
	private SecondLevelService secondLevelService;
	@Resource(name = "thirdLevelService")
	private ThirdLevelService thirdLevelService;
	@Resource(name = "fourthLevelService")
	private FourthLevelService fourthLevelService;

	/**
	 * 该方法应该是有Shiro的SecurityManager 自动调用（在需要进行身份认证的时候？）？
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		/**
		 * 这里的作为普通类中获取Servlet容器中的HttpSession、HttpServletRequest等是基于在web.xml中设置了监听器
		 * <listener>
		 *		<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
		 *	</listener>
		 * 为前提的，然后我们在任何类中通过如下方式都可以获取到当前servlet容器的session和request了
		 */
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		// 授权操作
		if (null == session) {
			return null;
		}
		// --------------------开始解析权限-------------------
		// 存放权限名称的list容器，该容器充满权限名字符串后腰交予info
		List<String> list = (List<String>) session.getAttribute("permissionStrings");
		// 包含所有权限名的容器的返回对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		// -----------------先看看在session中是否已经存放了权限list，如果有我们直接获取即可无需重新解析------------------
		if(null==list){
			/*
			 * 从session域中取出在shiroAction.authenticationByRealms()时通过managerSelect.jsp页面
			 * 传来并放入到session域中的关于当前要登录层级的两个关键信息
			 */
			String lid = (String) session.getAttribute("lid");
			String tag = (String) session.getAttribute("tag");
			// 新建一个存放完整权限字符串（形如：zeroLevel:user:update）的容器
			list = new ArrayList<String>();
			// 当前登陆这是否是admin
			boolean isAdmin = false;
			if ("".equals(tag)) {
				return null;
			}
			// 判断当前登陆着是不是admin
			if ("admin".equals(tag)) {
				isAdmin = true;
			}
			/*
			 * admin用户只有一个名叫admin的权限，非admin用户需要根据【projectLevel:projectType:project】
			 * 三段式命名规则从数据库中获取数据并组装成"aaa:bbb:ccc"样式的字符串放入到list容器中
			 */
			if (isAdmin) {
				// Admin的权限只有一个——————就是admin
				list.add("admin");
			} else {
				// 对于非admin登陆，要根据tag和lib获取登陆的层级对象并进一步获取权限对象
				// // 添加 角色 信息[新系统下已经没有角色这一说法了]
				// info.addRole(role.getRole());
				// 依据tag确定管理者本次选择登陆的层级对象，然后从中解析出权限
				// 开始获取权限对象
				Set<Permission> permissions = null;
				switch (tag) {
				case "minus_first":
					if (null == minusFirstLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("minusFirstLevelService");
					}
					// 从数据库中定位到层级对象
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != minusFirstLevel) {
						permissions = minusFirstLevel.getPermissions();
					}
					break;
				case "zero":
					if (null == zeroLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("zeroLevelService");
					}
					// 从数据库中定位到层级对象
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != zeroLevel) {
						permissions = zeroLevel.getPermissions();
					}
					break;
				case "first":
					if (null == firstLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("firstLevelService");
					}
					// 从数据库中定位到层级对象
					FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != firstLevel) {
						permissions = firstLevel.getPermissions();
					}
					break;
				case "second":
					if (null == secondLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("secondLevelService");
					}
					// 从数据库中定位到层级对象
					SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != secondLevel) {
						permissions = secondLevel.getPermissions();
					}
					break;
				case "third":
					if (null == thirdLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("thirdLevelService");
					}
					// 从数据库中定位到层级对象
					ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != thirdLevel) {
						permissions = thirdLevel.getPermissions();
					}
					break;
				case "fourth":
					if (null == fourthLevelService) {
						// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
						ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
						WebApplicationContext webApplicationContext = WebApplicationContextUtils
								.getWebApplicationContext(servletContext);
						userService = (UserService) webApplicationContext.getBean("fourthLevelService");
					}
					// 从数据库中定位到层级对象
					FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
					// 获取该层级对象所拥有的所有权限对象
					if (null != fourthLevel) {
						permissions = fourthLevel.getPermissions();
					}
					break;
				}

				if (null == permissions) {
					return null;
				}

				// 根据【PermissionLevel:PermissionType:Permission】规则解析并组装
				// 遍历所有权限拼装成完成的权限字符串
				for (Permission p : permissions) {
					StringBuffer sb = new StringBuffer();
					sb.append(p.getPermissionType().getPermissionLevel().getPermissionLevelName());
					sb.append(":");
					sb.append(p.getPermissionType().getPermissionTypeName());
					sb.append(":");
					sb.append(p.getPermissionName());
					// TODO ？打印权限，可为什么shiro对于用户的每次操作都要重新执行一遍授权流程呢？
					System.out.println("[来自myRealm]当前用户所拥有的权限：" + sb.toString());
					list.add(sb.toString());
				}
				System.out.println("当前用户权限数量是：" + list.size());
				// 为了防止Shiro对每次用户操作都重复执行获取权限的操作，我们将组装好的权限字符串放入到session中备用
				session.setAttribute("permissionStrings", list);
			}
		}
		
		// ----------------------结尾---------------------
		info.addStringPermissions(list);
		// 返回AuthorizationInfo，完成权限的获取
		return info;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof MyUsernamePasswordToken;
	}

	/**
	 * 在ShiroAction.login4Input()方法中 → subject.login(token); 方法的调用 而进入当前方法
	 * 进行身份认证的逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 身份认证操作
		String username = (String) token.getPrincipal();

		if (StringUtils.isEmpty(username)) {
			// 如果username是null或“” 则直接抛出异常, 向ShiroAction.login() 报告“账号不存在”
			throw new UnknownAccountException();
		}

		// -----------判断是不是Admin用户————————用户名和密码都是admin------------
		Properties p = ConfigUtils.getProperties("wxConfig" + File.separator + "admins.properties");
		String pwd = p.getProperty("pwd4admin");

		if (username.equals("admin")) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(username, pwd, getName());
			return info;
		}

		// ----------------------判断非Admin用户--------------------------
		if (null == userService) {
			// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
			ServletContext servletContext = ServletActionContext.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userService = (UserService) webApplicationContext.getBean("userService");
		}

		// 现在根据username从数据库中查找到了对应的用户对象,并且判断TA是不是一个管理员（manager是否为null）
		User user = userService.getUserByUsername(username);
		if (null == user) {
			return null;
		}
		if (user.isLocked()) {
			// 用户被锁定
			throw new LockedAccountException();
		}

		/*
		 * 席间AuthenticationInfo对象，封装从User对象中获取的principal信息和credentials信息，
		 * 然后在当前方法返回的时候 将封装着必要信息的AuthenticationInfo做返回值交给Matcher密码匹配器，
		 * 匹配器会自动根据token和AuthenticationInfo 中的principal和credential
		 * 进行比对，匹配则正常返回到ShiroAction.login()方法继续程序流程，不匹配则抛出响应异常，这些
		 * 异常也会被ShiroAction.login()中的try...catch代码段捕获，并做分支处理。
		 */
		AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName()); // 第三个参数getName()是调用当前Realm的getName()方法用来获取当前Realm的名字，用以在多个Realm中进行区分
		return info;
	}

}
