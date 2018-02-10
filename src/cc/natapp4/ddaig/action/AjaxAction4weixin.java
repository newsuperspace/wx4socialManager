package cc.natapp4.ddaig.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.domain.Activity;
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
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

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
public class AjaxAction4weixin extends ActionSupport {

	@Resource(name = "weixinService4Recall")
	protected WeixinService4RecallImpl mpService4Recall; // 用于自动处理消息
	@Resource(name = "weixinService4Setting")
	protected WeixinService4SettingImpl mpService4Setting; // 用来主动向指定用户发送Text消息
	@Resource(name = "userService")
	protected UserService userService;
	@Resource(name = "activityService")
	protected ActivityService activityService;

	// ==============================JS-SKD认证=============================
	/**
	 * 供给前端获取调用微信JS-SDK权限的签名的方法
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
	 * 用来通过前端页面传递过来的code码来换取究竟是哪个用户openID访问的该页面
	 * 
	 * @return
	 */
	public String getOpenIdthroughCode() {
		WxMpOAuth2AccessToken token = null;
		try {
			token = mpService4Recall.oauth2getAccessToken(this.code);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		String openId = token.getOpenId();

		System.out.println("本次来访的用户hi：" + openId);

		Result4GetOpenIdthouroughCode result = new Result4GetOpenIdthouroughCode(openId);
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
		private String openid;

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

	/**
	 * 属性驱动 承接从前端通过ajax传递过来的名为code的请求参数的值
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// ==============================实名制认证=============================
	// 响应从微信前端 实名认证页面提交过来的 用户实名信息表单
	public String checkRealName() {
		// 这里调用UserService，来完成实名制认证，并将结果返回
		Result4CheckRealName result = new Result4CheckRealName();

		System.out.println("提交实名制认证请求的用户的openID是:" + openid);
		System.out.println("提交的username是：" + username);
		System.out.println("提交的cardid是：" + cardid);
		System.out.println("提交的address是：" + address);
		System.out.println("提交的phone是：" + phone);

		/*
		 * 检查这次实名认证是不是重复提交，由于微信存在缓存机制，导致即便已经就该了用户的tag，
		 * 但是其在一段时间内所看到的Menu菜单仍然是之前的tag对应的菜单，这个时候用户可能就会
		 * 迫不及待的尝试重复提交验证，这里就是验证防止重复提交的。
		 */
		User user = userService.queryByOpenId(openid);
		Grouping grouping = user.getGrouping();
		String tag = grouping.getTag();
		if (!tag.equals("no_real_name_user")) {
			// 已经实名认证的用户
			result.setMessage("您已通过实名认证，请耐心等待微信缓存刷新。");
			result.setResult(true);
			// 主动向该用户发送信息
			mpService4Setting.sendTextMessage2One(openid,
					"您已通过实名认证，请耐心等待微信缓存刷新后菜单栏出现改变，您也可以尝试主动清空微信或关闭微信后重新打开等方式加快缓存更新。");
		} else {
			// 非实名认证用户
			try {
				userService.checkRealName(openid, username, cardid, address, phone);
				result.setMessage("实名制认证成功！");
				result.setResult(true);
				// 主动向该用户发送信息
				mpService4Setting.sendTextMessage2One(openid, "实名制认证成功！");
			} catch (WeixinExceptionWhenCheckRealName e) {
				e.printStackTrace();
				result.setMessage("实名制认证失败");
				result.setResult(false);
				// 主动向该用户发送信息
				mpService4Setting.sendTextMessage2One(openid, "实名制认证失败，请稍候再试");
			}
		}

		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	/**
	 * 实名认证 配套对checkRealName()方法的ajax请求的响应
	 * 
	 * @author Administrator
	 *
	 */
	public class Result4CheckRealName {
		private String message;
		private boolean result;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public Result4CheckRealName() {
		}

		public Result4CheckRealName(String message, boolean result) {
			this.message = message;
			this.result = result;
		}
	}

	/*
	 * 属性驱动 前端通过ajax方式调用checkRealName()方法的时候，传递过来的请求参数
	 */
	private String openid;
	private String cardid;
	private String username;
	private String address;
	private String phone;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// ==============================新建活动=============================
	public String createActivity() {

		Result4CreateActivity result = new Result4CreateActivity();

		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(description) || StringUtils.isEmpty(score)) {
			result.setMessage("活动新建失败");
			result.setResult(false);
		} else {
			Activity activity = new Activity();
			activity.setScore(score);
			activity.setDescription(description);
			activity.setName(name);

			activityService.save(activity);

			result.setMessage("活动新建成功");
			result.setResult(true);

		}

		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	public class Result4CreateActivity {
		private String message;
		private boolean result;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public Result4CreateActivity(String message, boolean result) {
			this.message = message;
			this.result = result;
		}

		public Result4CreateActivity() {
		}
	}

	private String name;
	private String description;
	private String score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	// ==============================获取活动列表=============================
	/**
	 * TODO
	 * 这里应该根据拥有社区用户（community_user）Tag的OpenId，来获取本社区的Activity，但Demo中简单处理成直接获取所有Activity
	 * 以后正式给一个街道的多个社区同时使用的时候，应该在Activity中添加创建者和所在社区的id
	 * 并且也要创建社区表，以区分Activity、user所属的社区背景
	 * 
	 * @return
	 */
	public String getActivityList() {
		List<Activity> list = activityService.queryEntities();

		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}

	// ===========================对没有加入公众号的用户的提供的活动签到功能=======================
	public String signIn() {

		Activity activity = activityService.queryEntityById(this.aid);
		User user = userService.queryEntityById(this.uid);

		// 判断当前用户是不是重复签到★
		if (activity.getUsers() != null) {
			// 当前活动中已经有人参与了，则
			for (User u : activity.getUsers()) {
				if (u.getUid().equals(user.getUid())) {
					// 如果当前扫码用户已经存在于参与当前活动的用户集合中，那么应该终止后续积分逻辑，并告知该用户
					ReturnMessage4Common  result = new ReturnMessage4Common("该用户已签到成功，请勿重复签到。", false); 
					ActionContext.getContext().getValueStack().push(result);
					return SUCCESS;
				}
			}
		}

		// 将用户添加到当前活动activity的uses集合中，以此可以方便的通过user查找到该用户参与活动的历史和当前活动的参与者，以及防止相同user重复扫码参与
		Set<User> users = activity.getUsers();
		if (null == users) {
			// 如果该activity的users集合是null，则说明当前扫码用户是第一个活动参与者
			users = new HashSet<User>();
			users.add(user);
			activity.setUsers(users);
		} else {
			// 不是第一个扫码的，则直接添加到该activity的set集合中即可
			activity.getUsers().add(user);
		}

		// 添加积分
		if (StringUtils.isEmpty(user.getScore())) {
			user.setScore(activity.getScore());
		} else {
			int total = Integer.valueOf(user.getScore());
			int score = Integer.valueOf(activity.getScore());
			total += score;
			user.setScore("" + total);
		}

		// 保存user
		userService.update(user);
		// 保存activity
		activityService.update(activity);

		// 签到成功，回复信息
		StringBuffer sb = new StringBuffer();
		sb.append(user.getUsername());
		sb.append("在");
		sb.append(activity.getName());
		sb.append("中签到成功,");
		sb.append("其当前积分是：");
		sb.append(user.getScore()+"。");

		ReturnMessage4Common  result = new ReturnMessage4Common(sb.toString(), true); 
		ActionContext.getContext().getValueStack().push(result);
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
