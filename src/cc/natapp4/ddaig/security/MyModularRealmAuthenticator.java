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
        System.out.println("MyModularRealmAtuenticator'message doMultiRealmAuthentication is running!");
    	return super.doMultiRealmAuthentication(realms, token);
    }
    /**
     * ★★★
     * 单个realm时执行认证操作时调用本操作
     */
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm,AuthenticationToken token) {

    	System.out.println("MyModularRealmAtuenticator'message doSingleRealmAuthentication is running 4 "+realm.getName());
        // 如果该realms不支持(不能验证)当前token
        if (!realm.supports(token)) {
            throw new ShiroException("token错误!");
        }
        AuthenticationInfo info = null;
        try {
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
