package cc.natapp4.ddaig.security;

import java.util.Collection;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.sym.Name;

/**
 * 我们知道Shiro的登录流程是： 前端登陆页面（用户名、密码信息） → shirAction.login() →
 * subject（token（username，password）） →
 * 
 * 正确 → realm.doGetAuthorizationInfo() 从Dao获取该用户的权限字符串 / →
 * realm.doGetAuthenticationInfo() \ 错误 →
 * 跳转到spring的配置文件applicationContext.xml中配置的提示信息页面
 * 
 * 
 * 以上是系统中只有一个realm时的流程，而如果系统中有多个realm，
 * 那么就要先将多个realm统一放到一个ModularRealmAuthenticator中（也就是本自定义类）
 * 然后subject()的验证会先交给ModularRealm，
 * 然后再通过根据我们自定义的MyUsernamepasswordToken中的loginType属性 指定具体所要使用的realm。
 * 
 * 
 * @author Administrator
 *
 */
@Component("myModularRealm")
@Lazy(true)
public class MyModularRealm extends ModularRealmAuthenticator {

	/**
	 * 该Map容器用来存放当前系统中的需要用到的realm。
	 * 所有realm都会通过spring的applicationContext.xml中关于当前类的bean的配置中注入进来
	 * <property name="definedRealms"> 
	 * 		<map> 
	 * 			<entry key="myRealm" value-ref="myRealm" /> 
	 * 			<entry key="myRealm4Input" value-ref="myRealm4Input" /> 
	 * 		</map> 
	 * </property>
	 */
	private Map<String, Object> definedRealms;
	public Map<String, Object> getDefinedRealms() {
		return definedRealms;
	}
	public void setDefinedRealms(Map<String, Object> definedRealms) {
		this.definedRealms = definedRealms;
	}

	/**
	 * 这里通常用来在将token提交给realm进行判断前，对realm进行配置 具体当前情况就是判断realm是否为空，是否可用
	 */
	@Override
	protected void assertRealmsConfigured() throws IllegalStateException {

		this.definedRealms = this.getDefinedRealms();
		if (CollectionUtils.isEmpty(this.definedRealms)) {
			throw new RuntimeException("MyModularRealm.doSignleRealmAuthentication()——值传递错误,definedRealms中不包含任何realm!");
		}

	}

	/**
	 * 从众多realm中挑选出一个来执行验证（我们需要的就是在这里从realm中找到当前需要的验证方式来具体执行验证）
	 */
	@Override
	protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {

		// 如果该realms不支持(不能验证)当前token
		if (!realm.supports(token)) {
			throw new RuntimeException("MyModularRealm.doSignleRealmAuthentication()——当前realm不支持该token，不能执行登录操作");
		}

		AuthenticationInfo info = null;

		try {
			info = realm.getAuthenticationInfo(token);
			if (info == null) {
				throw new RuntimeException("MyModularRealm.doSignleRealmAuthentication()——token不存在,不能执行登录操作");
			}
		} catch (Exception e) {
			throw new RuntimeException("MyModularRealm.doSignleRealmAuthentication()——用户名或者密码错误!");
		}
		return info;
	}

	/**
	 * 多个realm都来参与验证
	 */
	@Override
	protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
		// 当前系统并不需要多个realm同时参与登录认证，因此这个多Realm认证保持默认处理即可
		return super.doMultiRealmAuthentication(realms, token);
	}

	/**
	 * 验证方式的流转（上面的Multi和single中选择一种方式，当前我们需要的是single，因此在这里就会将验证交给single来处理）
	 */
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		Realm realm = null;

		// 现将传递过来的token转变为我们自定义的token类型（因为该token就是从ShiroAction传递过来的我们自定义类型的对象，因此可以直接强制转型，不用担心类型不匹配）
		MyUsernamepasswordToken token = (MyUsernamepasswordToken) authenticationToken;

		// 开始根据我们自定义的token中的loginType属性的信息来确定本次登录希望使用的realm的名字
		if (token.getLoginType().equals("myRealm")) {
			// 微信扫码登录
			realm = (Realm) this.definedRealms.get("myRealm");
		}

		if (token.getLoginType().equals("myRealm4Input")) {
			// 传统的输入用户名和密码的登录（只在测试情况下使用）
			realm = (Realm) this.definedRealms.get("myRealm4Input");
		}

		if (realm == null) {
			return null;
		}

		// 交给单一realm验证方法，进行登录验证
		return this.doSingleRealmAuthentication(realm, authenticationToken);

	}

}
