package cc.natapp4.ddaig.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.UserService;

/**
 * MyRealm 继承自 AuthorizingRealm父类 本Realm是自定义的Realm，用来承接Shiro的认证和授权逻辑。
 * 当前类中有两个主要方法，分别是 doGetAuthenticationInfo() 用来进行身份认证； doGetAuthorizationInfo()
 * 用来进行 权限认证 这两个方法的最主要功能就是 与响应的数据库进行交互，也就是从数据库中获取“用户信息”交给Shiro的Matcher匹配其进行匹配、
 * Role角色信息、Permission权限信息.
 * 
 * @author Administrator
 *
 */
@Component("myRealm")
@Lazy(true)
public class MyRealm extends AuthorizingRealm {

	private UserService userService = null;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "permissionService")
	private PermissionService permissionService;

	/**
	 * 该方法应该是有Shiro的SecurityManager 自动调用（在需要进行身份认证的时候？）？
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		// 授权操作
		String openid = (String) pc.getPrimaryPrincipal();

		if (StringUtils.isEmpty(openid))
			return null;

		if (null == userService) {
			// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userService = (UserService) webApplicationContext.getBean("userService");
		}

		User user = userService.queryByOpenId(openid);
		Manager manager = user.getManager();

		if (null == manager)
			return null;

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// // 添加 角色 信息[新系统下已经没有角色这一说法了]
		// info.addRole(role.getRole());

		// 开始获取权限对象
		Set<Permission> permissions = null;
		switch (user.getGrouping().getTag()) {
		case "minus_first":
			Set<MinusFirstLevel> mfls = manager.getMfls();
			Iterator<MinusFirstLevel> iterator = mfls.iterator();
			MinusFirstLevel minusFirst = null;
			if (iterator.hasNext()) {
				minusFirst = iterator.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = minusFirst.getPermissions();
			break;
		case "zero":
			Set<ZeroLevel> zls = manager.getZls();
			Iterator<ZeroLevel> iterator0 = zls.iterator();
			ZeroLevel zero = null;
			if (iterator0.hasNext()) {
				zero = iterator0.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = zero.getPermissions();
			break;
		case "first":
			Set<FirstLevel> fls = manager.getFls();
			Iterator<FirstLevel> iterator1 = fls.iterator();
			FirstLevel first = null;
			if (iterator1.hasNext()) {
				first = iterator1.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = first.getPermissions();
			break;
		case "second":
			Set<SecondLevel> scls = manager.getScls();
			Iterator<SecondLevel> iterator2 = scls.iterator();
			SecondLevel second = null;
			if (iterator2.hasNext()) {
				second = iterator2.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = second.getPermissions();
			break;
		case "third":
			Set<ThirdLevel> tls = manager.getTls();
			Iterator<ThirdLevel> iterator3 = tls.iterator();
			ThirdLevel third = null;
			if (iterator3.hasNext()) {
				third = iterator3.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = third.getPermissions();
			break;
		case "fourth":
			Set<FourthLevel> fols = manager.getFols();
			Iterator<FourthLevel> iterator4 = fols.iterator();
			FourthLevel fourth = null;
			if (iterator4.hasNext()) {
				fourth = iterator4.next();
			}
			// 获取该层级对象所拥有的所有权限对象
			permissions = fourth.getPermissions();
			break;
		}

		// 根据【PermissionLevel:PermissionType:Permission】规则解析并组装
		// 权限名称，然后放入到list容器中
		List<String> list = new ArrayList<String>();

		for (Permission p : permissions) {
			StringBuffer sb = new StringBuffer();
			sb.append(p.getPermissionType().getPermissionLevel().getPermissionLevelName());
			sb.append(":");
			sb.append(p.getPermissionType().getPermissionTypeName());
			sb.append(":");
			sb.append(p.getPermissionName());
			System.out.println("当前用户所拥有的权限：" + sb.toString());
			list.add(sb.toString());
		}
		System.out.println("当前用户权限数量是：" + list.size());
		info.addStringPermissions(list);
		// 返回AuthorizationInfo，完成权限的获取
		return info;
	}

	/**
	 * 在ShiroAction.login()方法中 → currentUser.login(shiroToken); 方法的调用 而进入当前方法
	 * 进行身份认证的逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 身份认证操作
		String openId = (String) token.getPrincipal();
		if (StringUtils.isEmpty(openId)) {
			// 如果openId是null或“” 则直接抛出异常, 向ShiroAction.login() 报告“账号不存在”
			throw new UnknownAccountException("【MyReal.doGetAuthenticationInfo()】openid为空");
		}

		if (null == userService) {
			// 从Spring容器中获取Bean——userService，但再此之前需要先获取Spring容器对象
			ServletContext servletContext = ServletActionContext.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userService = (UserService) webApplicationContext.getBean("userService");
		}

		// 现在根据openId从数据库中查找到了对应的用户对象
		User user = userService.queryByOpenId(openId);

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
		AuthenticationInfo info = new SimpleAuthenticationInfo(user.getOpenid(), "123", getName()); // 第三个参数getName()是调用当前Realm的getName()方法用来获取当前Realm的名字，用以在多个Realm中进行区分
		return info;
	}

}
