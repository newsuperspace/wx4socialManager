package cc.natapp4.ddaig.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 由于Shiro filterChainDefinitions中 roles默认是and，
 * /** = user,roles[system,general]
 * 比如：roles[system,general] ，表示同时需要“system”和“general” 2个角色才通过认证
 * 所以需要自定义 继承 AuthorizationFilter的一个shiro过滤器，
 * 然后再在Spring的applicationContext.xml中文件中的有关 配置 过滤器简称 的 映射中进行配置
 * 将该处理类（cc.natapp4.ddaig.security.PermissionsAuthorizationFilter）
 * 与anyPerms这个名字关联在一起，然后在filterChainDefinitions的配置中，我们就能像使用默认的
 * perms[]、roles[]、authc等 一样使用anyPerms[]了
 * 
 * @author Administrator
 *
 */
@Component("permissionsAuthorizationFilter")
@Lazy(true)
public class PermissionsAuthorizationFilter extends AuthorizationFilter {

	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// 返回true通过授权（允许访问），返回false不通过授权（跳转到授权失败页面）
		
		// 获取session用来判断本次请求的来源（微信/桌面）
		HttpServletRequest  hsr  =  (HttpServletRequest)request;
		HttpSession session = hsr.getSession();
		// 先获取当前访问者对应的subject（已经完成了myRealm中的认证和授权过程）
		Subject subject = this.getSubject(request, response);
		// 将属性转变成权限名数组
		String[] perms =  (String[]) mappedValue;
		// 如果在applicationContext.xml中的使用anyPerms[]过滤器处中括号为空，说明并没有权限限制，直接放行
		if(null==perms || 0==perms.length){
			return true;
		}
		/**
		 *  剩下的就是要开始验证。
		 *  需要特别指出的是，如果anyPerms[]中包含的是其他权限，没问题只要subject中存在就放行，只是如果含有的权限是
		 *  all:system:access4desk
		 *  all:system:access4weixin
		 *  需要进一步判断当前访问是来自微信还是桌面，如果是微信则在通过我们自定义的过滤器MyShiroFilter的时候会在session中留下wxURL的变量，
		 *  可通过查看是否存在该变量来判断访问是来子微信还是桌面。
		 */
		for(String p: perms){
			if(subject.isPermitted(p)){
				if(p.equals("all:system:access4desk")){
					// 判断本次访问是否真的来自桌面
					String wxUrl = (String)session.getAttribute("wxURL");
					if(StringUtils.isEmpty(wxUrl)){
						return true;
					}
				}else if(p.equals("all:system:access4weixin")){
					// 判断本次访问是否真的来自微信
					String wxUrl = (String)session.getAttribute("wxURL");
					if(!StringUtils.isEmpty(wxUrl)){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		
		return false;
	}

}
