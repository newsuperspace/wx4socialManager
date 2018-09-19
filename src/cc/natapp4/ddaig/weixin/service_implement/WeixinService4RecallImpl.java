package cc.natapp4.ddaig.weixin.service_implement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.weixin.handler.LocationSelectHandler;
import cc.natapp4.ddaig.weixin.handler.MenuClickHandler;
import cc.natapp4.ddaig.weixin.handler.ScaneHandler;
import cc.natapp4.ddaig.weixin.handler.SubscribeHandler;
import cc.natapp4.ddaig.weixin.handler.UnsubscribeHandler;
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Recall;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 这里为什么要添加@lazy延迟加载的注解呢？ 因为Spring容器（BeanFactory）启动是在web.xml中的Spring监听器
 * 这个监听器早于我们自定义的设置WxMpInMemoryConfigStorage的监听器，
 * 又因为Spring容器启动就会首先创建所有Bean的实例对象，而当前Bean中的init()初始化设置方法中
 * 有需要用到WxMpInMemoryConfigStorage ，因此会爆出错误，所以必须禁止Spring容器提前加载本Bean
 * 而是在业务正式开始的时候，在外部访问的时候再创建当前Bean，而那个时候因为整个Web应用已经启动完毕 所以在ServletContext域中的
 * WxMpInMemoryConfigStorage 也已经存在，自然就不会在创建当前Bean的时候 执行init()初始化方法的时候爆出错误。
 * 
 * @author Administrator
 *
 */
@Service("weixinService4Recall")
@Lazy(true)
public class WeixinService4RecallImpl extends WeixinServiceAbstract implements WeixinService4Recall {

	private WxMpMessageRouter router; // 消息路由 ★ 共route()方法内部使用

	// =============================================DI
	// 注入service层的其他service（如果有需要的话）

	// =============================================DI 注入当前Service所需要的Handler
	// 用户订阅
	@Resource(name = "subscribeHandler")
	private SubscribeHandler subscribeHandler;
	// 用户退订
	@Resource(name = "unsubscribeHandler")
	private UnsubscribeHandler unsubscribeHandler;
	// 扫码推
	@Resource(name = "scaneHandler")
	private ScaneHandler scaneHandler;
	// 菜单点击
	@Resource(name = "menuClickHandler")
	private MenuClickHandler menuClickHandler;
	// 处理定位服务
	@Resource(name = "locationSelectHandler")
	private LocationSelectHandler locationSelectHandler;

	// =============================================当前Service做Bean的初始化和销毁方法
	@PostConstruct
	public void init() {
		// 先从ServletContext域中拿到公众号的config对象，它是在应用的启动监听器中被创建并放到ServletContext域中保存的
		WxMpInMemoryConfigStorage config = (WxMpInMemoryConfigStorage) ServletActionContext.getServletContext()
				.getAttribute("config");

		// 再向当前mpService类的祖父类WxMpServiceImpl中设置这个config，如此当前service类才能调用公众号平台的接口实现对公众号的操作
		super.setWxMpConfigStorage(config);

		// 最后调用refreshRouter()方法配置"消息路由器"，该路由器对象仅供给当前类的route()内部使用，负责处理并回复从Action层传递过来的公众平台消息
		this.refreshRouter();
	}

	// =============================================其他Method，供给内部和外部使用

	/**
	 * 关键一步，创建一个新的“消息路由器”——router，用来处理从微信服务器传递来的各种消息
	 * 通过向router中配置各种拦截规则（消息类型、事件类型、handler），这样router就能根据微信服务器
	 * 发来的消息来自动组织好回传消息，用以回复微信服务器了。
	 * 
	 * 实现这一过程就是微信开发的过程
	 * 
	 * 一定要注意——————设置Handler的时候细粒度要从“细”→“粗”★
	 * 
	 */
	private void refreshRouter() {
		// 这里新创建一个Router，该Router只服务于本Service类的route()方法
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);

		// // 接收客服会话管理"事件"（同步消息）
		// // 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// // 进一步的"事件类型"是———客服服务中的具体事件类型（客服建立、客服关闭和客服转介）
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
		// .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
		// .handler(this.kfSessionHandler).end();
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
		// .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
		// .handler(this.kfSessionHandler).end();
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
		// .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
		// .handler(this.kfSessionHandler).end();

		// 位置选择器服务
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.BUTTON_LOCATION_SELECT)
				.handler(this.locationSelectHandler).end();

		// 菜单"单击事件"（同步消息）
		// 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// 事件类型——BUTTON_CLICK 按钮点击触发的事件
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.BUTTON_CLICK)
				.handler(this.menuClickHandler).end();

		// // 菜单"打开URL事件"（同步消息）
		// // 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// // 事件类型——BUTTON_VIEW 点击按钮是用来打开URL页面的事件
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
		// .event(WxConsts.BUTTON_VIEW).handler(this.nullHandler).end();

		// 关注"事件"（同步消息）
		// 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// 事件类型——EVT_SUBSCRIBE 关注公众号触发的事件
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE)
				.handler(this.subscribeHandler).end();

		// 取消关注"事件"（同步消息）
		// 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// 事件类型——EVT_UNSUBSCRIBE 取消关注公众号触发的事件
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE)
				.handler(unsubscribeHandler).end();

		// // 用户点击了“上报地理位置”的菜单按钮后，主动上报所触发的"事件"（同步消息）——————主动上报地理信息
		// // 消息类型（msgType）是event（事件）类型——WxConsts.XML_MSG_EVENT
		// // 事件类型——EVT_LOCATION
		// // 接下来就能在handler中得到用户的地理位置了
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
		// .event(WxConsts.EVT_LOCATION).handler(this.getLocationHandler()).end();

		// // 微信客户端自动收集用户地理信息，所发送的消息（同步消息）——————被动收集地理信息
		// // 消息类型（msgType）是XML_MSG_LOCATION ，对应微信公众平台文档-消息管理-接收消息-接收普通消息
		// 中关于地理信息的location消息类型
		// // 之后哦们就可以在handler中得到用户的位置数据了
		// newRouter.rule().async(false).msgType(WxConsts.XML_MSG_LOCATION)
		// .handler(this.getLocationHandler()).end();

		// 扫码"事件"（同步消息）
		// 消息类型（msgType）是event(事件)类型——WxConsts.XML_MSG_EVENT
		// 事件类型——EVT_SCANCODE_PUSH
		// 当用户打开QRCODE扫描后得到的信息会回传给哦们的服务器后就会触发该类型的事件，此时哦们就可以在handler中获知用户扫描的QRCODE的字符串内容了
		newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SCANCODE_WAITMSG)
				.handler(this.scaneHandler).end();

		// // 默认（不属于以上任何一种消息类型处理的剩余消息都会被这个handler所处理）★★★★ 细粒度最“粗，所以放在最后设置”
		// newRouter.rule().async(false).handler(this.getMsgHandler()).end();

		// 最后，将新创建的router放入到router中备用
		this.router = newRouter;
	}

	@Override
	public WxMpXmlOutMessage route(WxMpXmlMessage message) {
		// 调用各个Handler的处理从微信官方服务器发来的信息，这是每个WeixinService实现类都必须实现的方法，是整个提供微信服务的关键入口
		try {
			return this.router.route(message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("WeixinService4RecallImpl的route出现异常：" + e.getMessage());
		}
		return null;
	}

	@Override
	public void InitPlatform() {
		// 初始化公众平台是一种设置，应该由Setting来实现，而不是Recall，所以Recal的InitPlatform()方法是空的
	}

}
