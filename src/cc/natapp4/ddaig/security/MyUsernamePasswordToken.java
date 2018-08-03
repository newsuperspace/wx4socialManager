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

	public MyUsernamePasswordToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
	
}
