package cc.natapp4.ddaig.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class MyUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginType = "";

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/**
	 * 创建封装有登陆用的用户名和密码的token对象，第三参数用作指定验证器Realm的名称，本自定义token可以选择目标验证器
	 * @param username
	 * @param password
	 * @param loginType
	 */
	public MyUsernamePasswordToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
	
}
