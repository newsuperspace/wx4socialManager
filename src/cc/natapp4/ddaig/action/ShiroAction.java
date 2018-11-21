package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
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
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.security.MyUsernamePasswordToken;
import cc.natapp4.ddaig.service_interface.ManagerService;
import cc.natapp4.ddaig.service_interface.UserService;
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
	@Resource(name = "userService")
	private UserService userService;

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

	/**
	 * 在managerSelect.jsp页面上用户选定要登录的层级的lid和层级对应的标签类型 确定所要登录的层级
	 */
	private String lid; // 层级对象的主键ID
	private String tag; // grouping.tag字段
						// minus_first、zero、first、second、third、fourth

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	// ===================Action中的方法=====================

	/**
	 * 响应 微信端的用户身份认证的自动登陆（自动登录，不需要填写用户名、密码，也不需要扫码等操作
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
		 * 分析是否在session中存在目标访问的URL 存在——微信端来访者 不存在——桌面端来访者
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
			// 将OpenID保存到来访者的会话域中备用
			ServletActionContext.getRequest().getSession().setAttribute("openid", openId);
			// 微信端来访者默认登录密码是123，要保存到会话域中
			ServletActionContext.getRequest().getSession().setAttribute("password", "123");
			// 开始外部认证逻辑，所谓外部认证是暂时先不仅过Shiro深入认证而是通过openid查找该用户的信息，并进一步获取所有manager到前端供登陆者选取
			User user = userService.queryByOpenId(openId);
			String msg = "";
			if (null == user) {
				msg = "没有找到指定用户，请与管理员联系";
				setMsg(msg);
				return "failure";
			} else {
				List<Manager> managers = new ArrayList<Manager>();
				List<Member> members = user.getMembers();
				for (Member m : members) {
					List<Manager> ms = m.getManagers();
					managers.addAll(ms);
				}
				ServletActionContext.getContext().put("managers", managers);
				return "selectManager";
			}
		} else {
			// 对于桌面端来访者直接请求转发到带有qrcode的JSP页面，让用户通过微信扫码登录系统后台，从而实现桌面端的认证（基于Websocket，由ws4Login()负责）
			return "page4login";
		}
	}

	/**
	 * 通过输入用户名和密码完成登录，该方法用来测试环境下使用实际工作工作环境是通过微信扫码（wx4login()）完成的登录
	 * 本登录入口方法对应的realm是MyReal4Input类（wx4loging()方法对应的是MyRealm类）
	 * 
	 * @return
	 */
	public String login4Input() {
		// 获取登陆用的用户名和密码
		String username = this.getUsername();
		String password = this.getPassword();
		// 在session域中保存必要数据
		ServletActionContext.getRequest().getSession().setAttribute("username", username);
		ServletActionContext.getRequest().getSession().setAttribute("password", password);
		// 如果有错误的回复信息
		String msg = "";
		if("admin".equals(username)){
			// 管理员用户登录，不需要经过managerSelect.jsp页面选择，直接交予Realm进行Shiro认证和授权
			MyUsernamePasswordToken token = new MyUsernamePasswordToken(username, password, "myRealm4Input");
			// 获取唯一代表当前用户的subject对象
			Subject subject = SecurityUtils.getSubject();
			// 开始验证
			try {
				// 至此程序进程就会跳转到MyRealm的doGetAuthenticationInfo()中进行身份认证
				subject.login(token);
				/*
				 * 作为系统最高权限管理员登陆系统成功，应该在session的tag中自动插入"admin"字样，而lid不用写；
				 * 非admin用户登陆时tag和lid是通过managerSelector.jsp页面手动选定的，这是两种登陆类型的不同
				 */
				ServletActionContext.getRequest().getSession().setAttribute("tag", "admin");
				ServletActionContext.getRequest().getSession().setAttribute("lid", "");
				// 通过了Shiro的认证和授权没问题，正常访问后台主页
				return "access";
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
		}else{
			// 其他正常用户的登录
			// 开始类似login()中关于微信端登录的外部认证工作
			User u = userService.getUserByUsername(username);
			if (null != u && u.getPassword().equals(password)) {
				// 外部验证通过，可以放行到selectManager.jsp页面选择具体要登录的manager了
				List<Manager> managers = new ArrayList<Manager>();
				List<Member> members = u.getMembers();
				for (Member m : members) {
					List<Manager> ms = m.getManagers();
					managers.addAll(ms);
				}
				ActionContext.getContext().put("managers", managers);
				return "selectManager";
			} else {
				msg = "用户名或密码错误";
				setMsg(msg);
				return "failure";
			}
		}
	}

	public String jump2ManagerSelectFromWsLogin() {
		// 先從session中获取关键openid数据信息
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		String pwd = (String) ServletActionContext.getRequest().getSession().getAttribute("password");
		// 获取该用户，由于已经通过ws4login()进行了外部验证，所以openid的用户一定存在
		User u = userService.queryByOpenId(openid);
		// 从用户中遍历出所有管理员数据对象
		List<Manager> managers = new ArrayList<Manager>();
		List<Member> members = u.getMembers();
		for (Member m : members) {
			List<Manager> ms = m.getManagers();
			managers.addAll(ms);
		}
		// 放入到Map栈顶
		ActionContext.getContext().put("managers", managers);
		return "selectManager";
	}

	/**
	 * 桌面端扫码登陆的响应 负责处理桌面前端扫码登录后台系统的身份认证的业务逻辑 ★
	 * 前端页面的JS脚本会将从WebSocketHandler的handleTextMessage()中传递给它的登录用户的openID
	 * 以请求参数的形式再传递回来，因此方法中可以通过this.openid获取到登录用户的openid，来完成登录
	 * 
	 * @return
	 */
	public String ws4Login() {

		LoginResult4WebSocket result = new LoginResult4WebSocket();
		result.setResult(false);
		// 保存关键数据openid
		ServletActionContext.getRequest().getSession().setAttribute("openid", this.openid);
		// 由于openid登录一律使用默认密码123，所以默认密码也要记录在session中
		ServletActionContext.getRequest().getSession().setAttribute("password", "123");
		// 开始类似login()中关于微信端登录的外部认证工作
		User u = userService.queryByOpenId(openid);
		if (null != u) {
			result.setResult(true);
			result.setReLocal("shiroAction_jump2ManagerSelectFromWsLogin.action");
		} else {
			result.setResult(false);
			result.setMessage("扫码出现异常，无法定位您的管理者身份，请稍候重试");
			result.setReLocal("");
		}
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX
	 * 当微信端来或桌面端（非Admin）访者已经通过 外部认证并在managerSelect.jsp页面上选定要登录的层级的时候
	 * 会通过shiroAction_authenticationByMyRealm.action?lid=jsodf7932j2jf293jf2&tag
	 * =niuse_first 来请求本方法 这个方法才正式调用Shiro进行认证和授权操作
	 * 
	 * @return
	 */
	public String authenticationByRealms() {
		// 从managerSelect.jsp中会传递过来用户选定要登录的管理层级的tag和lid，保存这两个关键数据到session，之后各个系统部分中会频繁用到，非常关键
		ServletActionContext.getRequest().getSession().setAttribute("lid", this.lid);
		ServletActionContext.getRequest().getSession().setAttribute("tag", this.tag);
		// 当前登录用户的openid已经在login()方法中就已经存放在了，现在取出来
		String openId = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		// --------------------------------------------------------★开始进行Shiro的身份认证(Authentication)过程★-----------------------------------------------
		if (null!=openId && !"".equals(openId)) {
			// 存在openid说明本次登录是来自微信端或扫码登录方式的认证
			// 从后台取出openid方式登录的默认密码123（也就是说微信登录和用户名密码登录都会在session中存在一个password字段，但是基于微信的登录密码只是摆设而已）
			String pwd = (String) ServletActionContext.getRequest().getSession().getAttribute("password");
			// 使用Shiro的API功能开始进行认证操作，这里我们选择最基本的UsernamePasswordToken
			MyUsernamePasswordToken shiroToken = new MyUsernamePasswordToken(openId, pwd, "myRealm"); // credential

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
				return "access";
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
			// 身份认证失败也要清空wxURL,防止干扰下次微信端来访
			return "failure";
		} else {
			// 来自用户名/密码的传统登录方式
			String username = (String) ServletActionContext.getRequest().getSession().getAttribute("username");
			String password = (String) ServletActionContext.getRequest().getSession().getAttribute("password");
			// 基于前端提交的表单数据创建token
			MyUsernamePasswordToken token = new MyUsernamePasswordToken(username, password, "myRealmInput");
			// 获取唯一代表当前用户的subject对象
			Subject subject = SecurityUtils.getSubject();
			// 开始验证
			try {
				// 至此程序进程就会跳转到MyRealm的doGetAuthenticationInfo()中进行身份认证
				subject.login(token);
				// 通过了Shiro的认证和授权没问题，正常访问后台主页
				return "access";
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
	}

	/**
	 * openJSP/signin.jsp页面在$.ready() 中通过Ajax向服务器端所要当前这次用于登录 的QRCODE的相对路径URI
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

	// =========================用作JSON回复用的Bean内部类======================
	/**
	 * AJAX 封装有用于扫码登录的QRCODE的图片链接
	 * 
	 * ★内部类默认是private，因此为了能够让Struts2插件——JSON，访问该内部类，用来解析生成JSON格式字符串回复给前端，
	 * 需要显式地写出public
	 * 
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
	 * 返回登录结果
	 * 
	 * @author Administrator
	 *
	 */
	public class LoginResult4WebSocket {
		private boolean result;
		// 告诉前端的Ajax，需要重定向的地址
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

}
