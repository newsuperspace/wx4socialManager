package cc.natapp4.ddaig.security;

import java.util.Collection;
import java.util.Map;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;


/**
 * 本类会在Spring的applicationContext.xml中进行配置（作为一个bean）
 * 并且将当前工程中自定义的两个realm作为引用属性添加到其中
 * 
 * 这样当方法doAuthenticate() 基于我们自定义的token类型（继承自shiro原生类型UsernamePasswordToken的MyUsernamePasswordToken）
 * 中的loginType字段中获取用户登录要使用的realm的名称后
 * 
 * 就可以将该realm的实例，通过形如： this.definedRealms.get("myRealm");  的方式取出来，随同token一起作为
 * doSingleRealmAuthentication() 的参数调用，该方法内部会执行 realm的“认证”逻辑，并且在认证成功后执行“授权”逻辑
 * 从而完成整个shiro的认证授权操作。
 * 
 * 因此我们会发现Modular对于shiro来说就是一个管控多个realm的调用中心
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

	private Map<String, Object> definedRealms;
	public Map<String, Object> getDefinedRealms() {
        return this.definedRealms;
    }

    public void setDefinedRealms(Map<String, Object> definedRealms) {
        this.definedRealms = definedRealms;
    }
    /**
     * 多realm同时发生作用时实现
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        System.out.println("MyModularRealmAuthenticator'message doMultiRealmAuthentication is running!");
    	return super.doMultiRealmAuthentication(realms, token);
    }
    /**
     * ★★★
     * 单个realm时执行认证操作时调用本操作
     */
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm,AuthenticationToken token) {

    	System.out.println("MyModularRealmAuthenticator'message doSingleRealmAuthentication is running 4 "+realm.getName());
        // 如果该realms不支持(不能验证)当前token
        if (!realm.supports(token)) {
            throw new ShiroException("token错误!");
        }
        AuthenticationInfo info = null;
        try {
        	/*
        	 *  本类会在Spring的applicationContext.xml中配置，并将当前工程中
        	 */
            info = realm.getAuthenticationInfo(token);

            if (info == null) {
                throw new ShiroException("token不存在!");
            }
        } catch (Exception e) {
            throw new ShiroException("用户名或者密码错误!");
        }
        return info;
    }


    /**
     * 判断登录类型执行操作,并根据类型结果指派返回的realm负责处理认证操作和授权操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)throws AuthenticationException {
        this.assertRealmsConfigured();
        Realm realm = null;
        MyUsernamePasswordToken token = (MyUsernamePasswordToken) authenticationToken;
        //判断是否是后台用户
        if (token.getLoginType().equals("myRealm")) {
            realm = (Realm) this.definedRealms.get("myRealm");
        }
        else{
            realm = (Realm) this.definedRealms.get("myRealm4Input");
        }

        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    /**
     * 判断realm是否为空
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        this.definedRealms = this.getDefinedRealms();
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("值传递错误!");
        }
    }

    
}
