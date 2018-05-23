package cc.natapp4.ddaig.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
		Subject subject = this.getSubject(request, response);
		String[] perms =  (String[]) mappedValue;
		if(null==perms || 0==perms.length){
			return true;
		}
		
		for(String p: perms){
			if(subject.isPermitted(p)){
				return true;
			}
		}
		
		return false;
	}

}
