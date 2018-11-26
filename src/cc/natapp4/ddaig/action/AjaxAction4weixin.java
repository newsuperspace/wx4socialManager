package cc.natapp4.ddaig.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@Controller("ajaxAction4weixin")
/*
 * 所有Action类，由于每个实例有独自的ValueStack，因此对于每次请求都需要新建Action实例（也就是使用新值栈）
 * 而Spring容器中的Bean默认实例化都是单例，因此需要添加@Scope("prototype")注解来告诉Spring容器该bean需要多例
 */
@Scope("prototype")
/*
 * 懒加载非常重要，因为当前类也是bean，也同样通过@Component注解将当前类IOC与Spring容器管理之下
 * 又因为当前bean类体中又依靠DI注入需要其他bean的支持（通过@Resource注解实现注入过程），因此在实例化当前Handler类这个
 * Bean的时候，又需要预先实例化DI注入的bean。
 * 
 * 因为，WeixinService4XXX（用来与公众号服务器直接交互的Service）是依靠java-weixin-tool.jar提供的API实现的，
 * 而在这个jar包体系下
 * 需要有一个包含目标公众号的config对象注入到公众号MP开发的入口service（也就是WeixinService4XXX的父类的父类——
 * WxMpServiceImpl）中， 而当前工程，
 * config的配置工作是在Servlet启动监听器中实现的并在实例化WeixinService4XXX的时候通过ServletContext域获取，
 * 所以要想让WxMpServiceImpl的所有子类正常启动就必须Servlet容器先启动。
 * 
 * 因此你会发现所有Service（ActivityService、UserService、GroupingService等）都是bean，
 * 并且其中DI注入了一个或多个WeixinService4XXX。
 * 
 * Spring容器有一个习惯就是在Spring容器初始化的时候会预先自动将所有Bean都实例化一次，可Spring容器的初始化要早于Servlet初始化，
 * 这就造成 当Spring初始化一个Bean的时候，如果这个Bean的DI注入链条中存在WeixinService4XXX，
 * 那么就势必会先初始化这些WeixinService4XXX，但在
 * 构建WeixinService4XXX实例的时候又需要先从ServletContext域中获取config，但此时Servlet容器还没启动，
 * 因此就得不到config，从而爆出错。
 * 
 * 解决的办法是——在每个WeixinService4XXX类体内，创建config，从而只需对WeixinService4XXX类添加@Lazy注解，
 * 告知Spring容器不初始化 次bean即可。
 * 或者，是像当前工程这样，凡事DI注入链条中存在WeixinService4XXX类的，从源头就添加@Lazy注解，
 * 从而禁止整个DI链条上所有bean的实例化。
 * 
 */
@Lazy(true)
/**
 * AjaxAction4weixin 这个Action的作用是负责处理，前端中的JS文件与服务器的交互，并且这些交互与微信公众号有关
 * 例如当前端通过微信官方提供的JS-API调用手机设备的物理硬件的时候，需要预先进行安全检测，这就需要服务器端的配合将校验数据信息
 * 通过Ajax返回给前端，这就是本Action的作用
 * 
 * 与WeiXinAction直接负责接收微信端相应的功能定位是不同的。
 * 
 * @author Administrator
 *
 */
public class AjaxAction4weixin extends ActionSupport {

	@Resource(name = "weixinService4Recall")
	protected WeixinService4RecallImpl mpService4Recall; // 用于自动处理消息
	@Resource(name = "weixinService4Setting")
	protected WeixinService4SettingImpl mpService4Setting; // 用来主动向指定用户发送Text消息
	@Resource(name = "userService")
	protected UserService userService;
	@Resource(name = "activityService")
	protected ActivityService activityService;
	// 因为需要通过微信公众号服务器生成带参数二维码，这里需要DI注入WeixinService4Setting用来与微信服务器交互
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl mpService;

	// ==============================JS-SKD认证=============================
	/**
	 * 【已使用】 供给前端获取调用微信JS-SDK权限的签名的方法
	 * 在微信端的每次页面调用都会在DOM元素加载完毕后通过jQuery的$(function(){}); 调用JS-SDK的初始化方法
	 * 并向我们的服务器请求本方法（通过AJAX），作为在微信端的前端页面调用JS-SDK功能（如：获取坐标/扫描二维码）
	 * 的前提，因此本方法非常常用且重要。
	 * 
	 * @return
	 */
	public String getJsapiSignature() {

		WxJsapiSignature signature = null;
		try {
			signature = mpService4Recall.createJsapiSignature(this.url);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}

		Result4GetJsapiSignature result = new Result4GetJsapiSignature();
		result.setAppId(signature.getAppId());
		result.setNonceStr(signature.getNonceStr());
		result.setSignature(signature.getSignature());
		result.setTimestamp(String.valueOf(signature.getTimestamp()));

		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	/**
	 * 当前端需要使用微信的JS-SDK的时候需要通过wx.config 进行配置 其中有四个关键参数就需要通过服务器处理获取后通过ajax回传给前端
	 * 这个JavaBean类就是用来封装 这四个关键参数的。
	 * 
	 * @author Administrator
	 *
	 */
	public class Result4GetJsapiSignature {
		private String appId;
		private String timestamp;
		private String nonceStr;
		private String signature;

		public Result4GetJsapiSignature(String appId, String timestamp, String nonceStr, String signature) {
			this.appId = appId;
			this.timestamp = timestamp;
			this.nonceStr = nonceStr;
			this.signature = signature;
		}

		public Result4GetJsapiSignature() {
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getNonceStr() {
			return nonceStr;
		}

		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}
	}

	/**
	 * url（当前网页的URL，不包含#及其后面部分） 这个应该从前端传递过来，也就是当前与服务器ajax通讯的前端页面的完整url 形如：
	 * http://mp.weixin.qq.com?params=value
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// ==============================微信web认证授权=============================
	/**
	 * 【已使用】 用来通过前端页面传递过来的code码来换取究竟是哪个用户openID访问的该页面
	 * 
	 * @return
	 */
	public String getOpenIdthroughCode() {
		Result4GetOpenIdthouroughCode result = new Result4GetOpenIdthouroughCode();
		WxMpOAuth2AccessToken token = null;
		if (null == code || "".equals(code)) {
			// 如果来访连接没有带着code参数，说明不是从微信端来访的页面，直接PASS即可
			return null;
		}
		/*
		 * 检测该code是否是之前已经兑换过openid，如果兑换过还用相同code向微信端兑换，则会爆出
		 * me.chanjar.weixin.common.exception.WxErrorException:
		 * {"errcode":40163,"errmsg":
		 * "code been used, hints: [ req_id: ZT.I300234116 ]"} 的异常。
		 * 
		 * 解决办法是,由于整个系统中所以涉及通过code兑换openid的逻辑中都会首先检测session域中的名为“openid”
		 * 的字段是否已经有了openid，如果存在openid
		 * 直接使用即可（对于一个公众号来说，同一个用户的openid用换不会改变，一旦获取到可以在session有效期内永久使用），
		 * 就不用再麻烦使用code向微信服务器 兑换了。
		 */
		HttpSession session = ServletActionContext.getRequest().getSession();
		String storagedOpenid = (String) session.getAttribute("openid");
		if (StringUtils.isEmpty(storagedOpenid)) {
			try {
				token = mpService4Recall.oauth2getAccessToken(this.code);
				String openId = token.getOpenId();
				System.out.println("本次通过code换取的openID是：" + openId);
				result.setOpenid(openId);
				result.setResult(true);
				result.setMessage("openID兑换成功！");
				// 把已经兑换的code保存到session域中，用于判断下次请求参数的code是否是这次已经兑换过的code
				session.setAttribute("openid", openId);
			} catch (WxErrorException e) {
				e.printStackTrace();
				result.setResult(false);
				result.setMessage("openID兑换时出现异常！");
			}
		} else {
			result.setOpenid(storagedOpenid);
			result.setResult(true);
			result.setMessage("该code已经在之前兑换过openID，该openID已经保存到服务器的session域中了，无需重复兑换");
		}

		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	/**
	 * 配套getOpenIdthroughCode()方法，用来向前端回复必要数据的JavaBean
	 * 
	 * @author Administrator
	 *
	 */
	public class Result4GetOpenIdthouroughCode {
		// 将当前正在访问前端的用户的openID传递到前方，备用
		private boolean result;
		private String openid;
		private String message;

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public Result4GetOpenIdthouroughCode(String openid) {
			this.openid = openid;
		}

		public Result4GetOpenIdthouroughCode() {
		}
	}

	// =========================获取“带参数（场景值）二维码”==========================
	/**
	 * 	
	 * 【微信端交互】
	 *  由各个层级Action中的createLevel和createSonLevel调用，用于从微信端服务器获取带参数二维码（30天有效期），
	 *  这样当用户扫描带参数二维码加入公众号的时候就会自动划归到该层级对象的直接管理层之下。
	 *  【TODO 已弃用】
	 *  考虑到：
	 *  （1）只有服务号才有权创建带参数二维码，普通订阅号不能创建带参数二维码
	 *  （2）原计划是用户扫描各个层级的带参数二维码可以实现关注公众号（没有关注的）和加入层级之下的供呢个
	 *  但实际情况是，如果已经关注公众号的用户扫描一个还未加入的层级对象的带参数二维码的时候，只会打开公众号
	 *  而不会调用SubScribeHander中加入该层级对象（创建member等操作），因此这个功能目的已经失去价值
	 *  （3）会增加本地服务器与微信服务器交互压力
	 *  综上所述弃用本方法，但方法中与微信端交互获取带参数二维码的业务逻辑可以在今后有需要的时候修改重用
	 *  因此保留本方法。
	 * 
	 * @param lid
	 *            层级对象的ID
	 * @param param
	 *            形如:"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的参数字符串
	 * @param r
	 *            用来保存通过AJAX向前端返回JSON信息的RetrunMessage4Common类型对象
	 * @return 形如：
	 *         "qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"本地带参数二维码图片相对路径
	 */
	public String getQrcodeFromWeixin(String lid, String param, ReturnMessage4Common r) {

		// 与微信服务器进行交互
		WxMpQrCodeTicket qrTicket = null;
		File inFile = null;
		try {
			// 将参数文本提交给微信服务器，微信会生成带参数的临时二维码（30天）
			qrTicket = mpService.getQrcodeService().qrCodeCreateTmpTicket(param, 2592000);
			// 通过票据换取指定带参数二维码的File
			inFile = mpService.getQrcodeService().qrCodePicture(qrTicket);
			if (null == inFile) {
				throw new RuntimeException("从微信端获取的带参数二维码的File是null");
			}
		} catch (WxErrorException e1) {
			e1.printStackTrace();
			String message = "从微信端获取带参数二维码时出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 将带参数二维码图片的File保存到本地，备用
		String codePath = "";
		codePath = "qrcode";
		int hashCode = lid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		// 由于不同文件系统的层级分隔符不一样（/正斜线或\反斜线），可以通过File.separator来自动适配分隔符，保证当前应用在任何操作系统中都能使用
		codePath = codePath + File.separator + first + File.separator + second;
		ServletContext context = ServletActionContext.getServletContext();
		// realPath就是服务器本地的真实路径，形如：C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
		String realPath = context.getRealPath(File.separator + codePath);
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File outFile = new File(realPath); // 路径，而非文件
		if (!outFile.exists()) {
			// 如果该路径不存在，则逐层创建路径
			outFile.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名仍然以层级对象的id值为名字，然后拓展名为jpg
		codePath = codePath + File.separator + lid + ".jpg";
		System.out.println("最终的codePath：" + codePath);
		realPath = realPath + File.separator + lid + ".jpg";
		System.out.println("最终的realPath:" + realPath);
		outFile = new File(realPath); // 文件，而非路径
		// 创建输入流和输出流，准备开始流对接
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(outFile);
			fis = new FileInputStream(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String message = "在将从微信服务器获取的存有二维码jpq图片的File保存到本次磁盘时，创建输入或输出流出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 开始流对接，temp为字节缓冲（1024B=1KB）————字节数组就是缓冲区，8位（bit）=1字节（B），因此字节是仅次于位的最小信息单位，用它存放二进制数据最合适
		byte[] temp = new byte[1024];
		// 记录每次从输入流转入到输出流中的字节数
		int len = 0;
		// 开始流对接
		try {
			while ((len = fis.read(temp)) != -1) {
				// 边读边写，一旦len=-1表明输入流中的数据全都读入了，没有额外数据可供读入了
				fos.write(temp, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return codePath;
	}

	// =========================其他方法==========================
	/**
	 * 属性驱动 承接从前端通过ajax传递过来的名为code的请求参数的值
	 * 作为getOpenIdthroughCode()方法向微信端服务器换取来访者用户的openid之用
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 【TODO 暂时停用】 对没有加入公众号的用户的提供的活动签到功能
	 * 
	 * @return
	 */
	public String signIn() {

		// Activity activity = activityService.queryEntityById(this.aid);
		// User user = userService.queryEntityById(this.uid);
		//
		// // 判断当前用户是不是重复签到★
		// if (activity.getUsers() != null) {
		// // 当前活动中已经有人参与了，则
		// for (User u : activity.getUsers()) {
		// if (u.getUid().equals(user.getUid())) {
		// // 如果当前扫码用户已经存在于参与当前活动的用户集合中，那么应该终止后续积分逻辑，并告知该用户
		// ReturnMessage4Common result = new
		// ReturnMessage4Common("该用户已签到成功，请勿重复签到。", false);
		// ActionContext.getContext().getValueStack().push(result);
		// return SUCCESS;
		// }
		// }
		// }
		//
		// //
		// 将用户添加到当前活动activity的uses集合中，以此可以方便的通过user查找到该用户参与活动的历史和当前活动的参与者，以及防止相同user重复扫码参与
		// Set<User> users = activity.getUsers();
		// if (null == users) {
		// // 如果该activity的users集合是null，则说明当前扫码用户是第一个活动参与者
		// users = new HashSet<User>();
		// users.add(user);
		// activity.setUsers(users);
		// } else {
		// // 不是第一个扫码的，则直接添加到该activity的set集合中即可
		// activity.getUsers().add(user);
		// }
		//
		// // 添加积分
		// if (StringUtils.isEmpty(user.getScore())) {
		// user.setScore(activity.getScore());
		// } else {
		// int total = Integer.valueOf(user.getScore());
		// int score = Integer.valueOf(activity.getScore());
		// total += score;
		// user.setScore("" + total);
		// }
		//
		// // 保存user
		// userService.update(user);
		// // 保存activity
		// activityService.update(activity);
		//
		// // 签到成功，回复信息
		// StringBuffer sb = new StringBuffer();
		// sb.append(user.getUsername());
		// sb.append("在");
		// sb.append(activity.getName());
		// sb.append("中签到成功,");
		// sb.append("其当前积分是：");
		// sb.append(user.getScore()+"。");
		//
		// ReturnMessage4Common result = new ReturnMessage4Common(sb.toString(),
		// true);
		// ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	private String uid;
	private String aid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

}
