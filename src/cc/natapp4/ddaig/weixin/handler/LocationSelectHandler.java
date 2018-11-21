package cc.natapp4.ddaig.weixin.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.ExchangeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.utils.CheckRealNameUtils;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component("locationSelectHandler")
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
 * 处理微信公众号按钮类型为WxConsts.BUTTON_SCANCODE_WAITMSG 扫码推事件
 * 
 * @author Administrator
 *
 */
public class LocationSelectHandler extends AbstractHandler {

	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "wareService")
	private WareService wareService;
	@Resource(name = "exchangeService")
	private ExchangeService exchangeService;
	@Resource(name="checkRealNameUtils")
	private CheckRealNameUtils checkRealNameUtils;
	/*
	 * 需要向微信服务器回传文本信息，则获取文本构造器
	 */
	@Resource(name = "textBuilder")
	private TextBuilder textBuilder;

	/**
	 * wxMessage——是当前这次待处理消息所包含的全部信息（openID、key、message等等）的主体对象★★
	 * wxMpService——用来调用weixin-java-tools的API的服务对象★
	 * context——是一个容器对象，用来存放键值对儿数据，从而在同一次事件处理的过程中，在多个Handler之间传递数据。
	 * sessionManager——会话管理，weixin-java-tools也实现了类似Session的功能。
	 * 
	 * 目前来说当前Handler需要处理的功能包括：
	 * （1）签到/退（self_gn_qdqt）
	 * (2)扫码登录（self_gn_dl）
	 * (3)积分兑换（self_gn_dh）
	 */
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		// 首先要将wxMpService转化成咱们自己定义的service类型，这样才能调用咱们自定义的函数
		WeixinService4RecallImpl service = (WeixinService4RecallImpl) wxMpService;
		// 分析用户按下的扫码按钮究竟是(self_gn_qdqt/self_gn_dl/self_gn_dh)
		String eventKey = wxMessage.getEventKey();
		System.out.println("key是：" + eventKey);
		// 校验用户是否已经实名认证
		WxMpXmlOutMessage outMessage = checkRealNameUtils.check(wxMessage.getFromUser(), wxMessage, service);
		if(null!=outMessage){
			// 通过checkRealNameUtils返回的outMessage如果不是null说明该用户还没有实名认证
			return outMessage;
		}
		// ----------------------------------------------正式业务逻辑--------------------------------------------
		Double longitude = wxMessage.getLocationX();
		Double latitude = wxMessage.getLocationY();
		StringBuffer  sb  =  new  StringBuffer();
		sb.append("该位置的地理坐标是：");
		sb.append("纬度:"+latitude+", ");
		sb.append("经度:"+longitude);
		System.out.println(sb.toString());
		outMessage  =  textBuilder.build(sb.toString(), wxMessage, service);
		// 返回结果
		return outMessage;
	}

	
}
