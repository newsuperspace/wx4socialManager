package cc.natapp4.ddaig.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.bean.Info4RealName;
import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.VisitorService;
import cc.natapp4.ddaig.utils.PositionUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller("personalCenterAction")
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
public class PersonalCenterAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ================================== Spring的DI注入
	// ==================================
	@Resource(name = "weixinService4Recall")
	protected WeixinService4RecallImpl mpService4Recall; // 用于自动处理消息
	@Resource(name = "weixinService4Setting")
	protected WeixinService4SettingImpl mpService4Setting; // 用来主动向指定用户发送Text消息
	@Resource(name = "userService")
	protected UserService userService;
	@Resource(name = "activityService")
	protected ActivityService activityService;
	@Resource(name = "visitorService")
	protected VisitorService visitorService;

	// ================================== 属性驱动
	// ==================================
	/*
	 * 微信端菜单点击EVENT_VIEW类型按钮，基于OAUTH2.0认证的连接跳转访问本Action的入口方法
	 * accessPersonalCenter的时候会附带有code的请求参数，我们通过该code可以向微信端服务器
	 * 换取来访者的真正openID，从而确定来访者身份。
	 */
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	// 这个state是微信端服务器基于oauth2.0协议请求重定向时随同code属性一起发来的，一般情况下我们不会用到
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	// 基于位置的签到/签退，JS-SDK或获取签到/签退者的地理位置坐标，并以AJAX的post请求参数的形式传递过来，下面就是记录经度和纬度的属性驱动
	// 纬度
	private long latitude;
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	// 经度
	private long longitude;
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	/*
	 * checkRealName()进行实名认证时会通过表单形式提交来的请求参数
	 */
	private String username;
	private String phone;
	private String birth;
	private String sex;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	/*
	 * 活动报名/活动取消/扫码签到等有关Activity的属性驱动
	 */
	private String aid;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	// ================================== ACTIONS
	// ==================================
	/**
	 * 【已使用】 微信端的 “用户中心”
	 * 按钮（EVENT_VIEW类型）点击后就会请求personalCenterAction_accessPersonalCenter.action
	 * 本方法的作用就是引导用户访问自己的用户中心（在向用户的微信端返回JSP之前需要通过Struts2的值栈来组织好页面显示的内容） 其逻辑过程为：
	 * （1）首先，根据用户访问oauth2认证连接，然后被微信端服务器请求重定向过来的的路径中附带的名为code的请求参数换取到来访用户的openID
	 * （2）然后根据openID从数据库查找到该user，然后进一步判断该user是否完成了实名认证（user.grouping.tag !=
	 * "unreal"及可） ① 没有实名认证的————跳转到实名认证也买你 ②
	 * 完成实名认证的————将user数据放入到值栈栈顶，然后跳转到用户中心页面，该页面上的JSP标签会自动解析值栈中user的值来组织起页面显示内容
	 * 
	 * @return
	 */
	public String accessPersonalCenter() {
		String result = "personalCenter";
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		if (StringUtils.isEmpty(openid)) {
			// 如果session域中还没有openid，则说明这是该用户的第一次来访，需要通过code来向微信端服务器换取用户的openid
			WxMpOAuth2AccessToken token = null;
			try {
				token = mpService4Setting.oauth2getAccessToken(this.code);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			openid = token.getOpenId();
			/*
			 * 将openid放入到HttpServletSession中，这样方便后续用户从用户中心页面中请求其他action的url的时候
			 * 由于这些链接不是基于oauth2.0从微信端服务器请求重定向过来的，因此不可能再带有code值了，因此我们也就无法
			 * 再通过code值换取来访者的openid从而确定来访者身份了。
			 * 因此为了后续使用，accessPersonalCenter方法作为用户通过微信端来访的入口方法，
			 * 有责任将已经获取的openID放入到
			 * HttpServletSession域中（Servlet一共有四个作用域，分别是最大的servletContext域、
			 * 中等的HttpRequst请求转发链域、来访者用户个人的
			 * session会话域以及最小的pageContext域），这样只要相同用户之后的任何对本应用程序的来访，
			 * 都可以通过session会话域中获取到该用户的 openid了。
			 */
			ServletActionContext.getRequest().getSession().setAttribute("openid", openid);
		}

		User user = userService.queryByOpenId(openid);
		if ("unreal".equals(user.getGrouping().getTag())) {
			// 如果来访用户的tag标签还是unreal，说明是还未实名认证的用户，需要跳转到实名认证页面
			result = "realName";
		} else {
			ActionContext.getContext().getValueStack().push(user);
		}
		return result;
	}

	// ==============================实名制认证=============================
	/**
	 * 【已使用】 响应从微信前端 实名认证页面(/openJSP/realname.jsp)以GET方式提交过来的 表单数据
	 * 
	 * @return
	 */
	public String realName() {
		// 这里调用UserService，来完成实名制认证，并将结果返回
		Info4RealName info = new Info4RealName();
		info.setTotal("实名认证结果");

		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		System.out.println("提交实名制认证请求的用户的openID是:" + openid);
		System.out.println("提交的username是：" + this.username);
		System.out.println("提交的sex是：" + this.sex);
		System.out.println("提交的birth是：" + this.birth);
		System.out.println("提交的phone是：" + this.phone);

		/*
		 * 检查这次实名认证是不是重复提交，由于微信存在缓存机制，导致即便已经就该了用户的tag，
		 * 但是其在一段时间内所看到的Menu菜单仍然是之前的tag对应的菜单，这个时候用户可能就会
		 * 迫不及待的尝试重复提交验证，这里就是验证防止重复提交的。
		 */
		User user = userService.queryByOpenId(openid);
		Grouping grouping = user.getGrouping();
		String tag = grouping.getTag();
		if (!tag.equals("unreal")) {
			// 已经实名认证的用户,给予必要的信息提示
			info.setDetails("");
			info.setDetailsURL("");
			info.setIcon("weui-icon-info");
			info.setMessage("您已完成实名认证，无需重复认证");
			info.setTitle("禁止操作执行");
		} else {
			// 还没有实名认证的用户
			try {
				userService.checkRealName(openid, username, sex, birth, phone);
				info.setDetails("");
				info.setDetailsURL("");
				info.setIcon("weui-icon-success");
				info.setMessage("实名认证信息已通过，感谢您对社区公益的支持！");
				info.setTitle("实名认证成功");
			} catch (WeixinExceptionWhenCheckRealName e) {
				info.setDetails("");
				info.setDetailsURL("");
				info.setIcon("weui-icon-warn-red");
				info.setMessage("实名制认证失败，请稍候再试");
				info.setTitle("发生严重错误");
			}
		}

		// 放入到值栈栈顶，供给JSP页面组装页面时读取数据显示之用
		ActionContext.getContext().getValueStack().push(info);
		return "msgPage";
	}

	/**
	 * 获取当前用户可以报名参加的活动
	 * 
	 * @return
	 */
	public String getCanJoinActivityList() {
		String result = "canJoinActivityList";
		// 从当前用户的Servlet会话（session）中得到该用户的openid，该openid是accessPersonalCenter()方法在用户第一次通过微信端来访时通过code获取并放入到session域中的
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		List<Activity> canJoinActivityList = activityService.getCanJoinActivityList(openid);
		// 更新一下每个活动的state状态
		for (Activity a : canJoinActivityList) {
			a.updateState();
			activityService.update(a);
		}
		// 放入到值栈栈顶，供给JSP页面组装页面时读取数据显示之用
		ActionContext.getContext().put("list", canJoinActivityList);
		return result;
	}

	/**
	 * 获取当前用户已经报名的活动，而且活动还有效(非取消或过期状态)
	 * 
	 * @return
	 */
	public String getJoiningActivityList() {
		String result = "joiningActivityList";
		// 从当前用户的Servlet会话（session）中得到该用户的openid，该openid是accessPersonalCenter()方法在用户第一次通过微信端来访时通过code获取并放入到session域中的
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		List<Activity> joiningActivityList = activityService.getJoiningActivityList(openid);
		// 更新一下每个活动的state状态
		for (Activity a : joiningActivityList) {
			a.updateState();
			activityService.update(a);
		}
		// 放入到值栈栈顶，供给JSP页面组装页面时读取数据显示之用
		ActionContext.getContext().put("list", joiningActivityList);
		return result;
	}

	/**
	 * 获取当前用户已经参加过的活动，但是活动已经过期或被取消
	 * 
	 * @return
	 */
	public String getJoinedActivityList() {
		String result = "joinedActivityList";
		// 从当前用户的Servlet会话（session）中得到该用户的openid，该openid是accessPersonalCenter()方法在用户第一次通过微信端来访时通过code获取并放入到session域中的
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		List<Activity> joinedActivityList = activityService.getJoinedActivityList(openid);
		// 更新一下每个活动的state状态
		for (Activity a : joinedActivityList) {
			a.updateState();
			activityService.update(a);
		}
		// 放入到值栈栈顶，供给JSP页面组装页面时读取数据显示之用
		ActionContext.getContext().put("list", joinedActivityList);
		return result;
	}

	/**
	 * AJAX 当用户通过微信端的canJoinActivityList.jsp页面点击一个活动的“报名”按钮后
	 * 就会通过Ajax来请求本方法，完成报名操作逻辑。
	 * 
	 * 按道理来说，本方法应该放在ActivityAction中，但是由于Shiro需要所有请求ActivityAction
	 * 都必须先认证（personalCenterAction_*.action已经通过在applicationContext.
	 * xml中设置anno实现免认证了）
	 * 而本方法的访问者允许非管理层用户来访，因此他们肯定通不过权限认证，因此本方法还是放在PersonalCenterAction中吧
	 * 
	 * @return
	 */
	public String baoMing() {
		// -----------------准备必要数据信息------------------
		ReturnMessage4Common result = new ReturnMessage4Common();
		// 前端通过请求参数的方式将需要添加，这里以属性驱动获取到待报名的活动的aid
		Activity activity = activityService.queryEntityById(this.aid);
		// 获取报名用户的openid（该openid已经早在用户第一次访问OAUTH2.0授权的personalCenterAction_accessPersonalCenter.action的时候就已经换取并存放在session中）
		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		// 进一步获取用户对象
		User user = userService.queryByOpenId(openid);

		// -----------------检测活动的合法性，例如当前时间是否超过了报名最后期限/例如活动的人数限制是否超过等------------------
		// （1）获取当前时间的格里高利偏移量，检测是否报名超时
		long currentTimeMillis = System.currentTimeMillis();
		long baoMingEndTime = activity.getBaoMingEndTime();
		if (currentTimeMillis > baoMingEndTime) {
			// 报名时间已过，报名失败
			result.setResult(false);
			result.setMessage("报名时间已过，报名失败");
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}
		// （2）检测报名人数
		int baoMingUplimit = activity.getBaoMingUplimit();
		if (-1 != baoMingUplimit) {
			if (activity.getVisitors().size() == baoMingUplimit) {
				result.setMessage("报名人数已满额，报名失败");
				result.setResult(false);
				ActionContext.getContext().getValueStack().push(result);
				return "json";
			}
		}

		// -----------------开始执行报名逻辑------------------
		Visitor v = new Visitor();
		v.setUser(user);
		v.setActivity(activity);
		v.setScore(-1);
		v.setWorkTime(-1);
		v.setEndTime(-1);
		v.setStartTime(-1);
		// 处理新建的visitor在当前用户user中的次序问题
		List<Visitor> visits = user.getVisits();
		/*
		 * 由于User。visits是一个List容器，list容器对添加的visitor有顺序要求（
		 * 顺序记录在visitor表的index4user字段）
		 * 因此必须显示地将visitor添加到user.visits这个list容器中，这样index4user字段才能正常记录visitor的次序
		 */
		if (null == visits) {
			visits = new ArrayList<Visitor>();
			user.setVisits(visits);
		}
		visits.add(v);
		// 处理新建的visitor在所报名活动activity中的次序问题
		List<Visitor> visitors = activity.getVisitors();
		// 这里的道理同visits的操作
		if (null == visitors) {
			visitors = new ArrayList<Visitor>();
			activity.setVisitors(visitors);
		}
		visitors.add(v);
		// 执行级联save，这样就能在写入新建visitor数据到visitor数据库的时候也级联地保存user和activity的数据到数据库了
		visitorService.save(v);

		// 组织回复给前端Ajax的消息
		result.setMessage("活动 " + activity.getName() + " 报名成功！");
		result.setResult(true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 根据前端发来的活动的activity的aid，取消当前用户在该活动中的报名
	 * 
	 * @return
	 */
	public String cancelBaoMing() {
		ReturnMessage4Common result = new ReturnMessage4Common();
		HttpSession session = ServletActionContext.getRequest().getSession();
		String openid = (String) session.getAttribute("openid");
		User user = userService.queryByOpenId(openid);
		Activity activity = activityService.queryEntityById(this.aid);
		// 清楚user和activity的List容器中的visitor对象，然后再通过visitorService从数据库中彻底删除该对象
		List<Visitor> visitors = activity.getVisitors();
		Visitor visitor = null;
		// 遍历待取消报名的活动中的每个visitor，找到当前用户的visitor
		for (Visitor v : visitors) {
			if (v.getUser().getOpenid().equals(openid)) {
				visitor = v;
			}
		}
		/*
		 * 从user和Activity的list容器中移除该visitor，这里有个疑问
		 * 通过debug模式，我发现不论是User还是Activity中的visitor都是同一个对象，
		 * 这样当我们从Activity中找到该visitor后，就能同时从user和activity中的List容器中删除该对象了 ???
		 * 我不明的的是为什么Hibernate能从两个主表中找到相同的从表，这底层是怎么实现的呢？真方便啊
		 */
		visitors.remove(visitor);
		user.getVisits().remove(visitor);
		// 最后再从数据库中删除该visitor即大功告成
		visitorService.delete(visitor);

		// 最后就是想前端返回消息
		result.setResult(true);
		result.setMessage("删除成功");
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 执行扫码签到逻辑
	 * 
	 * @return
	 */
	public String qianDao() {
		Info4RealName info = new Info4RealName();
		info.setTotal("签到结果");

		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		Activity activity = activityService.queryEntityById(this.aid);
		List<Visitor> visitors = activity.getVisitors();
		for (Visitor v : visitors) {
			if (v.getUser().getOpenid().equals(openid)) {
				if (-1 == v.getStartTime()) {
					long currentTimeMillis = System.currentTimeMillis();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(currentTimeMillis));
					// 向visitor中记录签到时间
					v.setStartTime(currentTimeMillis);
					// 组织Info信息，用作msgPage页面显示
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-success");
					info.setTitle("签到成功");
					info.setMessage("您已于" + dateTimeStr + "完成签到!请准备参加活动，过程中务必注意安全");
					// 向数据库中保存签到时间
					visitorService.update(v);
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(v.getStartTime()));
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-warn-red");
					info.setTitle("发生错误");
					info.setMessage("您已于" + dateTimeStr + "您已签到，请勿重复操作");
				}

			}
		}
		ActionContext.getContext().getValueStack().push(info);
		return "msgPage";
	}

	/**
	 * 执行基于地理位置的签到逻辑
	 * 
	 * @return
	 */
	public String qianDao4Position() {
		Info4RealName info = new Info4RealName();
		info.setTotal("签到结果");

		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		Activity activity = activityService.queryEntityById(this.aid);
		List<Visitor> visitors = activity.getVisitors();
		for (Visitor v : visitors) {
			if (v.getUser().getOpenid().equals(openid)) {
				// 定位到了签到用户的visitor
				if (-1 == v.getStartTime()) {
					// 还没签到呢
					long currentTimeMillis = System.currentTimeMillis();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(currentTimeMillis));
					// 判断用户当前签到位置是否在活动的房屋或地理位置的规定范围内
					String positionName = "";
					double olat = 0;
					double olon = 0;
					int orad = 0;
					if(activity.getActivityType().equals("1")){
						// 室外活动
						Geographic g = activity.getGeographic();
						olat = g.getLatitude();
						olon = g.getLongitude();
						orad = g.getRadus();
						positionName = g.getName();
					}else{
						// 室内活动
						House h = activity.getHouse();
						olat = h.getLatitude();
						olon = h.getLongitude();
						orad = h.getRadus();
						positionName = h.getName();
					}
					
					if(PositionUtils.inTheAround(new double[]{olat,olon}, orad, new double[]{this.latitude,this.longitude})){
						// 签到位置在有效签到范围内，允许签到
						// 向visitor中记录签到时间
						v.setStartTime(currentTimeMillis);
						// 组织Info信息，用作msgPage页面显示
						info.setDetails("");
						info.setDetailsURL("");
						info.setIcon("weui-icon-success");
						info.setTitle("签到成功");
						info.setMessage("您已于" + dateTimeStr + "完成签到！请准备参加活动，过程中务必注意安全");
						// 向数据库中保存签到时间
						visitorService.update(v);
					}else{
						// 签到位置不再有效签到范围额内，禁止签到
						info.setDetails("");
						info.setDetailsURL("");
						info.setIcon("weui-icon-warn-red");
						info.setTitle("出现问题");
						
						StringBuffer  sb  =  new  StringBuffer();
						sb.append("您的当前位置不在活动地点 ");
						sb.append(positionName);
						sb.append(" 的有效签到范围(");
						sb.append(orad+"米)内,请尽量靠近活动位置后再试");
						info.setMessage(sb.toString());
					}
				} else {
					// 已经签到了
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(v.getStartTime()));
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-warn-red");
					info.setTitle("出现问题");
					info.setMessage("您已于" + dateTimeStr + "您已签到！请勿重复操作");
				}
			}
		}
		
		ActionContext.getContext().getValueStack().push(info);
		return "msgPage";
	}

	/**
	 * AJAX 执行扫码签退逻辑
	 * 
	 * @return
	 */
	public String qianTui() {
		Info4RealName info = new Info4RealName();
		info.setTotal("签退结果");

		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		User user = userService.queryByOpenId(openid);
		Activity activity = activityService.queryEntityById(this.aid);
		List<Visitor> visitors = activity.getVisitors();
		for (Visitor v : visitors) {
			if (v.getUser().getOpenid().equals(openid)) {
				if (-1 == v.getEndTime()) {
					long currentTimeMillis = System.currentTimeMillis();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(currentTimeMillis));
					// 向visitor中记录签退时间
					v.setEndTime(currentTimeMillis);
					// 给予用户积分
					v.setScore(activity.getScore());
					user.setScore(user.getScore() + activity.getScore());
					// 计算本次活动时常
					v.setWorkTime(v.getEndTime() - v.getStartTime());
					// 组织Info信息，用作msgPage页面显示
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-success");
					info.setTitle("签退成功");
					info.setMessage("您已于" + dateTimeStr + "完成签退，感谢您对社区公益的支持，祝您生活愉快");
					// 向数据库中保存签到时间
					visitorService.update(v);
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(v.getStartTime()));
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-warn-red");
					info.setTitle("发生错误");
					info.setMessage("您已于" + dateTimeStr + "您已签退，请勿重复操作");
				}
			}
		}
		ActionContext.getContext().getValueStack().push(info);
		return "msgPage";
	}

	/**
	 * 执行基于地理位置的签退逻辑
	 * 
	 * @return
	 */
	public String qianTui4Position() {
		Info4RealName info = new Info4RealName();
		info.setTotal("签退结果");

		String openid = (String) ServletActionContext.getRequest().getSession().getAttribute("openid");
		Activity activity = activityService.queryEntityById(this.aid);
		User user = userService.queryByOpenId(openid);
		List<Visitor> visitors = activity.getVisitors();
		
		for (Visitor v : visitors) {
			if (v.getUser().getOpenid().equals(openid)) {
				// 定位到了签退用户的visitor
				if (-1 == v.getEndTime()) {
					// 还没签退呢
					long currentTimeMillis = System.currentTimeMillis();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(currentTimeMillis));
					// 判断用户当前签退位置是否在活动的房屋或地理位置的规定范围内
					String positionName = "";
					double olat = 0;
					double olon = 0;
					int orad = 0;
					if(activity.getActivityType().equals("1")){
						// 室外活动
						Geographic g = activity.getGeographic();
						olat = g.getLatitude();
						olon = g.getLongitude();
						orad = g.getRadus();
						positionName = g.getName();
					}else{
						// 室内活动
						House h = activity.getHouse();
						olat = h.getLatitude();
						olon = h.getLongitude();
						orad = h.getRadus();
						positionName = h.getName();
					}
					
					if(PositionUtils.inTheAround(new double[]{olat,olon}, orad, new double[]{this.latitude,this.longitude})){
						// 签到位置在有效签到范围内，允许签到
						// 向visitor中记录签退时间
						v.setEndTime(currentTimeMillis);
						// 给予用户积分
						v.setScore(activity.getScore());
						user.setScore(user.getScore() + activity.getScore());
						// 计算本次活动时常
						v.setWorkTime(v.getEndTime() - v.getStartTime());
						// 组织Info信息，用作msgPage页面显示
						info.setDetails("");
						info.setDetailsURL("");
						info.setIcon("weui-icon-success");
						info.setTitle("签退成功");
						info.setMessage("您已于" + dateTimeStr + "完成签退，感谢您对社区公益的支持，祝您生活愉快");
						// 向数据库中保存签到时间
						visitorService.update(v);
					}else{
						// 签到位置不再有效签到范围额内，禁止签到
						info.setDetails("");
						info.setDetailsURL("");
						info.setIcon("weui-icon-warn-red");
						info.setTitle("出现问题");
						
						StringBuffer  sb  =  new  StringBuffer();
						sb.append("您的当前位置不在活动地点 ");
						sb.append(positionName);
						sb.append(" 的有效签退范围(");
						sb.append(orad+"米)内,请尽量靠近活动位置后再试");
						info.setMessage(sb.toString());
					}
				} else {
					// 已经签退了
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateTimeStr = formatter.format(new Date(v.getStartTime()));
					info.setDetails("");
					info.setDetailsURL("");
					info.setIcon("weui-icon-warn-red");
					info.setTitle("发生错误");
					info.setMessage("您已于" + dateTimeStr + "您已签退，请勿重复操作");
				}
			}
		}
		
		ActionContext.getContext().getValueStack().push(info);
		return "msgPage";
	}

	
	
	
}
