package cc.natapp4.ddaig.action;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller("shiroAction")
@Scope("prototype")
@Lazy(true)
public class ShiroAction extends ActionSupport {

	// ====================Spring框架的DI注入=====================
	@Resource(name = "weixinService4Recall")
	protected WeixinService4RecallImpl mpService4Recall;
	// ====================基于Struts2框架值栈的属性驱动===================
	/**
	 * 后台系统扫码登录所传递过来的扫码着的openid用于身份认证
	 */
	private String openid;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 用作 动态结果集 之用
	 */
	private String wxURL;
	public String getWxURL() {
		return wxURL;
	}
	public void setWxURL(String wxURL) {
		this.wxURL = wxURL;
	}

	/**
	 * 用作 前端JSP页面向用户反馈信息 之用
	 */
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 仅仅用于signin.jsp页面上的表单输入登陆方式获取的请求参数
	 */
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	// ===================Action中的方法=====================
	
	public String login4Input(){
		
		String username  =  this.getUsername();
		String password  =  this.getPassword();
		// 基于前端提交的表单数据创建token
		UsernamePasswordToken  token  =  new  UsernamePasswordToken(username, password);
		// 获取唯一代表当前用户的subject对象
		Subject subject = SecurityUtils.getSubject();
		// 开始验证
		try {
			// 至此程序进程就会跳转到MyRealm的doGetAuthenticationInfo()中进行身份认证
			subject.login(token);
			// 如果在doGetAuthenticationInfo()方法的逻辑中没有出现任何“身份认证”异常，则说明当前用户身份认证通过
			return "login4Input";
		} catch (IncorrectCredentialsException e) {
			// 该异常由比对器Matcher抛出，不需要哦们自己手动在MyRealm.doGetAuthenticationInfo()中抛出
			msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
			System.out.println(msg);
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
			System.out.println(msg);
		} catch (LockedAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
			System.out.println(msg);
		} catch (DisabledAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
			System.out.println(msg);
		} catch (ExpiredCredentialsException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
			System.out.println(msg);
		} catch (UnknownAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
			System.out.println(msg);
		} catch (UnauthorizedException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出，用以表示当前用户不是临时授权登录的用户，有些时候用户登录需要管理员授权，没有授权就不能登录即便密码正确也不行。
			msg = "您没有得到相应的授权！" + e.getMessage();
			System.out.println(msg);
		}
		setMsg(msg);
		return "failure";
	}
	
	
	
	/**
	 * 响应 微信端的用户身份认证（自动登录，不需要填写用户名、密码，也不需要扫码等操作
	 * 只是通过微信端基于OAUTH2.0的协议访问指定页面的时候，会将访问者openid兑换码以请求参数的形式传递
	 * 到服务器，此时就可以将兑换名向微信服务器兑换真正的openid，从而完成自动登录和权限认证操作了） ★
	 * 
	 * @return
	 */
	public String login() {

		// 后台系统登录的时候会用到HttpServletContext 用来在这个Web应用作用域内共享数据（qrcode、倒计时有效期等）
		ServletContext servletContext = ServletActionContext.getServletContext();
		// wx登录需要从session域中获取wxURL
		HttpSession session = ServletActionContext.getRequest().getSession();

		// 获取由MyShiroFilter过滤器放入到session中的，前端所要请求的URL路径（带请求参数）★
		String wxURL = (String) session.getAttribute("wxURL");

		/*
		 * 分析是否在session中存在目标访问的URL
		 * 存在——微信端来访者
		 * 不存在——桌面端来访者
		 */
		if (!StringUtils.isEmpty(wxURL)) {
			// wxURL包含code请求参数。则说明本次请求是从微信平台发来的（oauth2.0认证）
			// 形如：redirect_uri/?code=CODE&state=STATE
			String[] split = wxURL.split("=")[1].split("&");
			String code = split[0];
			System.out.println("Shiro解析出的code是：" + code);
			// 使用weixin-java-tools的API功能
			WxMpOAuth2AccessToken token = null;
			try {
				// 由于myService4Recall的类继承了抽象类WeixinServiceAbstract（weixin-java-tools提供）因此具备许多基础功能
				// 通过基础功能oauth2getAccessToken来向微信服务器换取当前访问者的token，其中封装着包括访问者openID在内的微信数据信息（安全性考量）
				token = mpService4Recall.oauth2getAccessToken(code);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			// 至此访问页面的用户的openID已获取，我们就能在后端确认其对应的user了
			String openId = token.getOpenId();
			System.out.println("本次微信端来访用户的Shiro认证出的OpenID结果是：" + openId);

			// --------------------------------------------------------★开始进行Shiro的身份认证(Authentication)过程★-----------------------------------------------
			// 使用Shiro的API功能开始进行认证操作，这里我们选择最基本的UsernamePasswordToken
			UsernamePasswordToken shiroToken = new UsernamePasswordToken(openId, "123"); // credential
																							// 默认统一设定成“123”
			// shiroToken.setRememberMe(true);
			/*
			 * 先从SecurityUtils中获取唯一对应当前来访者的subject对象，
			 * 通过该对象可以调用我们自定义的myRealm中的认证\授权方法，完成认证和授权功能
			 */
			Subject currentUser = SecurityUtils.getSubject();
			String msg = "";
			try {
				// 至此程序进程就会跳转到MyRealm的doGetAuthenticationInfo()中进行身份认证
				currentUser.login(shiroToken);
				// 如果在doGetAuthenticationInfo()方法的逻辑中没有出现任何“身份认证”异常，则说明当前用户身份认证通过
				setWxURL(wxURL);
				System.out.println("微信端来访用户已成功通过身份认证，可以放行到其目标请求URL：" + wxURL);
				/**
				 * 在返回SUCCESS之前，需要先清除session中的wxURL，这是由于
				 * 防止使用相同浏览器进行wx前端登录和后台登陆时相互影响，wx前端登录由于OAUTH2.0 使得请求的URL包含？code=
				 * 这会被MyShiroFilter过滤器拦截，并将包含请求参数的整体URL放入到session的wxURL中，这样当来到ShiroAction.login()
				 * 系统检测出session域中的wxURL有值，才会明白本次“身份认证”是来自wx前端的。
				 * 而如果用户继续使用wx浏览器登录后台，由于请求的路径中不包含code，因此在经过MyShiroFilter时不会保存到wxURL，
				 * 但是由于之前wx前端登录已经向session域中的wxURL存入了值，则此时再次进入ShiroAction.login()
				 * 就又会进入wx前端 登录的身份认证逻辑分支，而不是扫描QRCODE的逻辑分支。
				 * 因此当wx前端登录成功后，就必须清除session域中的wxURL，这样即便之后继续使用wx浏览器在相同session会话生命周期内
				 * 登录后台管理系统，由于所请求的URL不包含code，因此不会被MyShiroFilter保存到Session域的wxURL中，这样跳转到
				 * ShiroAction.login()的时候，就会进入else{ }的扫描QRCODE的登录逻辑分支。
				 * 
				 * 其实貌似也不用这样，由于登录系统后台使用的是桌面浏览器，而wx前端登录使用的是wx浏览器，两个浏览器不共享session
				 * 所以也就不会相互影响。 可安全起见，还是清空一下吧.
				 */
				session.setAttribute("wxURL", "");
				setWxURL(wxURL);
				return SUCCESS;
			} catch (IncorrectCredentialsException e) {
				// 该异常由比对器Matcher抛出，不需要哦们自己手动在MyRealm.doGetAuthenticationInfo()中抛出
				msg = "登录密码错误. Password for account " + shiroToken.getPrincipal() + " was incorrect.";
				System.out.println(msg);
			} catch (ExcessiveAttemptsException e) {
				msg = "登录失败次数过多";
				System.out.println(msg);
			} catch (LockedAccountException e) {
				// MyRealm.doGetAuthenticationInfo()中手动抛出
				msg = "帐号已被锁定. The account for username " + shiroToken.getPrincipal() + " was locked.";
				System.out.println(msg);
			} catch (DisabledAccountException e) {
				// MyRealm.doGetAuthenticationInfo()中手动抛出
				msg = "帐号已被禁用. The account for username " + shiroToken.getPrincipal() + " was disabled.";
				System.out.println(msg);
			} catch (ExpiredCredentialsException e) {
				// MyRealm.doGetAuthenticationInfo()中手动抛出
				msg = "帐号已过期. the account for username " + shiroToken.getPrincipal() + "  was expired.";
				System.out.println(msg);
			} catch (UnknownAccountException e) {
				// MyRealm.doGetAuthenticationInfo()中手动抛出
				msg = "帐号不存在. There is no user with username of " + shiroToken.getPrincipal();
				System.out.println(msg);
			} catch (UnauthorizedException e) {
				// MyRealm.doGetAuthenticationInfo()中手动抛出，用以表示当前用户不是临时授权登录的用户，有些时候用户登录需要管理员授权，没有授权就不能登录即便密码正确也不行。
				msg = "您没有得到相应的授权！" + e.getMessage();
				System.out.println(msg);
			}
			setMsg(msg);
			// 身份认证失败也要清空wxURL
			session.setAttribute("wxURL", "");
			return "failure";
		} else {
			// 对于桌面端来访者直接请求转发到带有qrcode的JSP页面，让用户通过微信扫码登录系统后台，从而实现桌面端的认证（基于Websocket，由ws4Login()负责）
			return "page4login";
		}

	}

	/**
	 * AJAX
	 * 封装有用于扫码登录的QRCODE的图片链接
	 * 
	 * ★内部类默认是private，因此为了能够让Struts2插件——JSON，访问该内部类，用来解析生成JSON格式字符串回复给前端，需要显式地写出public
	 * @author Administrator
	 *
	 */
	public class Result4LoginGetQR {
		private String qrURI;
		private String uuid;

		public String getQrURI() {
			return qrURI;
		}

		public void setQrURI(String qrURI) {
			this.qrURI = qrURI;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

	}

	/**
	 * openJSP/login.jsp页面在$.ready() 中通过Ajax向服务器端所要当前这次用于登录 的QRCODE的相对路径URI
	 * 
	 * @return
	 */
	public String getLoginQR() {
		// 产生登录用随机UUID
		String uuid = UUID.randomUUID().toString();
		// 根据此UUID产生qrcode图片到临时Temp中
		String qrURI = QRCodeUtils.createQR2Temp(uuid); // 形如：“temp/2/10/3842-284-382-181-31.gif”的字符串返回

		Result4LoginGetQR qr = new Result4LoginGetQR();
		qr.setQrURI(qrURI);
		qr.setUuid(uuid);

		ActionContext.getContext().getValueStack().push(qr);
		return "json";
	}

	/**
	 * 返回登录结果
	 * 
	 * @author Administrator
	 *
	 */
	public class LoginResult4WebSocket {
		private boolean result;
		private String reLocal;
		private String message;

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public String getReLocal() {
			return reLocal;
		}

		public void setReLocal(String reLocal) {
			this.reLocal = reLocal;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	/**
	 * 负责处理桌面前端扫码登录后台系统的身份认证的业务逻辑 ★
	 * 前端页面的JS脚本会将从WebSocketHandler的handleTextMessage()中传递给它的登录用户的openID
	 * 以请求参数的形式再传递回来，因此方法中可以通过this.openid获取到登录用户的openid，来完成登录
	 * 
	 * @return
	 */
	public String ws4Login() {
		
		LoginResult4WebSocket result = new LoginResult4WebSocket();
		result.setResult(false);

		// 密码对于微信扫码登陆无关紧要，默认统一设定成“123”
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(this.openid, "123"); // credential
		// shiroToken.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		String msg = "";
		try {
			// 至此程序进程就会跳转到MyRealm的doGetAuthenticationInfo()中进行身份认证
			currentUser.login(shiroToken);
			// 如果在doGetAuthenticationInfo()方法的逻辑中没有出现任何“身份认证”异常，则说明当前用户身份认证通过
			msg = "扫码登录成功！";
			result.setResult(true);
			System.out.println(msg);
		} catch (IncorrectCredentialsException e) {
			// 该异常由比对器Matcher抛出，不需要哦们自己手动在MyRealm.doGetAuthenticationInfo()中抛出
			msg = "登录密码错误. Password for account " + shiroToken.getPrincipal() + " was incorrect.";
			System.out.println(msg);
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
			System.out.println(msg);
		} catch (LockedAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已被锁定. The account for username " + shiroToken.getPrincipal() + " was locked.";
			System.out.println(msg);
		} catch (DisabledAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已被禁用. The account for username " + shiroToken.getPrincipal() + " was disabled.";
			System.out.println(msg);
		} catch (ExpiredCredentialsException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号已过期. the account for username " + shiroToken.getPrincipal() + "  was expired.";
			System.out.println(msg);
		} catch (UnknownAccountException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出
			msg = "帐号不存在. There is no user with username of " + shiroToken.getPrincipal();
			System.out.println(msg);
		} catch (UnauthorizedException e) {
			// MyRealm.doGetAuthenticationInfo()中手动抛出，用以表示当前用户不是临时授权登录的用户，有些时候用户登录需要管理员授权，没有授权就不能登录即便密码正确也不行。
			msg = "您没有得到相应的授权！" + e.getMessage();
			System.out.println(msg);
		}
		
		result.setMessage(msg);
		if(result.isResult())
			result.setReLocal("directPageAction.action?forward=home");

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

}
