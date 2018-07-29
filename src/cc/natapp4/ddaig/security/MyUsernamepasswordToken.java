package cc.natapp4.ddaig.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 本自定义类是在ShiroAction中的各个login登录验证方法中所使用，用来封装登录用的用户名和密码的数据
 * 
 * 然后再将封装好的token通过Subject→securityManager→Realm 交给我们所定义的realm的doGetAuthenticationInfo()
 * 进行登录验证，
 * 
 * 本类是shiro自带类型UsernamePasswordToken的子类
 * 
 * 自定义本类的目的是引入一个需要在多个realms中用来定位具体所需要调用的realm的标记字段——loginType，仅此而已。
 * 
 * 所以说当前系统中（具体就是shiroAction中）所使用的token都应该是本自定义类型的（只有涉及到多realm验证的定位）
 * @author Administrator
 *
 */
public class MyUsernamepasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	
	/*
	 * 本字段是在shiroAction中的login方法在创建本类token封装用户名和密码等数据的时候一并写入的，
	 * 用来指定希望哪个具体realm来验证本token。
	 * 当前系统中的realm分别是用于微信登录验证的MyRealm和用于开发阶段测试用的MyRealm4Input
	 */
	private String loginType;
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
	
	// 默认（空）构造器
	public MyUsernamepasswordToken() {
	}
	// 带参数构造器
	public MyUsernamepasswordToken(String username, String password) {
		super(username, password);
	}
	
}
