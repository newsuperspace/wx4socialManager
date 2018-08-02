package cc.natapp4.ddaig.weixin.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Ware;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.ExchangeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.ScanCodeInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 用户扫描签到二维码，后交由本handler处理
 * 
 * 目前当前handler处理的工作有： （1）普通用户 菜单-第一个按钮（key是second_1）扫码签到 （2）普通用户
 * 菜单-第二个按钮下的子菜单-第四个按钮（key是second_2_4） 积分兑换 （3）third_2 扫描登录系统后台
 * 
 * @author Administrator
 *
 */
@Component("scaneHandler")
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
 * @author Administrator
 *
 */
public class ScaneHandler extends AbstractHandler {

	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "wareService")
	private WareService wareService;
	@Resource(name = "exchangeService")
	private ExchangeService exchangeService;
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
		// 获取触发当前信息的用户的openID
		String openID = wxMessage.getFromUser();
		// 通过openID从本地数据库查找到该用户，备用
		User user = userService.queryByOpenId(openID);
		// 然后获取本次qrcode扫码的结果
		ScanCodeInfo codeInfo = wxMessage.getScanCodeInfo();
		System.out.println("扫码结果是：" + codeInfo.getScanResult());

		// 分析用户按下的扫码按钮究竟是(self_gn_qdqt/self_gn_dl/self_gn_dh)
		String eventKey = wxMessage.getEventKey();
		System.out.println("触发当前扫码推事件的按钮的key是：" + eventKey);
		// 准备回复消息用的OutMessage对象
		WxMpXmlOutMessage outMessage = null;
		// 开始不同业务逻辑的分支
		switch (eventKey) {
		case "self_gn_qdqt": // 处理扫码签到
			// 向公众号扫码用户回复积分信息
			StringBuffer sb = new StringBuffer();
			sb.append("签到已成功，");
			sb.append("感谢您参与本次 ");
			sb.append("XX活动！");
			sb.append("您的当前积分是：");
			sb.append(user.getScore());
			outMessage = textBuilder.build(sb.toString(), wxMessage, service);
			break;

		case "self_gn_dh": // 处理积分兑换
			int score = user.getScore();
			Ware ware = wareService.queryEntityById(codeInfo.getScanResult());
			if (null == ware) { // ---------------------------------------------------------------------------------------------------判断扫码推过来的扫码结果——codeInfo.getScnResult()结果是否是兑换品的wid
				// 不是兑换品的二维码
				outMessage = textBuilder.build("您扫描的二维码不是兑换品专属二维码，请确认后重新扫码。", wxMessage, service);
			} else if (Integer.valueOf(score) < Integer.valueOf(ware.getCore())) { // ---------------------------------------判断用户是否拥有足够积分
				// 积分不足
				outMessage = textBuilder.build("抱歉，您的积分不足。如有疑问请与社区联系，感谢您对社区公益的支持！", wxMessage, service);
			} else if (Integer.valueOf(ware.getSurplus()) < 1) { // ------------------------------------------------------------判断剩余商品是否充足
				// 剩余商品不足
				outMessage = textBuilder.build("抱歉，您所兑换的物品库存不足，请联系社区。", wxMessage, service);
			} else { // ----------------------------------------------------------------------------------------------------------------正常兑换逻辑开始
				// 新建一条记录
				Exchange e = new Exchange();
				// 获取当前兑换日期/时间数据
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String exchangeDate = format.format(new Date(System.currentTimeMillis()));
				// 向exchange记录专用写入数据,并写入数据库
				e.setExchangeData(exchangeDate);
				e.setScore(ware.getCore());
				e.setUser(user);
				e.setWare(ware);
				exchangeService.save(e);
				// 更新用户的积分数据
				int orignScore = Integer.valueOf(score);
				int price = Integer.valueOf(ware.getCore());
				user.setScore(orignScore - price);
				userService.update(user);
				// 更新ware的相关数据（surplus和total）
				int newSurplus = Integer.valueOf(ware.getSurplus()) - 1;
				int newTotal = Integer.valueOf(ware.getTotal()) + 1;
				ware.setSurplus("" + newSurplus);
				ware.setTotal("" + newTotal);
				wareService.update(ware);
				// 组装回复用户微信的文本信息
				StringBuffer sb2 = new StringBuffer();
				sb2.append(exchangeDate);
				sb2.append(" 恭喜您使用了");
				sb2.append(price + "积分成功兑换了 ");
				sb2.append(ware.getWname() + "。");
				sb2.append("目前剩余积分 ");
				sb2.append(user.getScore() + "，感谢您对社区公益的支持。");
				outMessage = textBuilder.build(sb2.toString(), wxMessage, service);
			}
			break;
			
		case "self_gn_dl": // 系统后台登陆
			ServletContext servletContext = ServletActionContext.getServletContext();
			// 获取到登陆用的临时二维码中的UUID值
			String  info = (String) servletContext.getAttribute(codeInfo.getScanResult());
			
			if(StringUtils.isEmpty(info)){
				outMessage = textBuilder.build("未找到合法的身份识别信息.", wxMessage, service);
			}else{
				/*
				 * 默认情况下，位于servletContext域中的名为该uuid的Map，值是"waiting"字符串
				 * 一旦用户通过微信扫码登录功能扫描PC端页面上的二维码，就会将二维码的uuid传递到本handler来处理
				 * 我们就可以根据uuid在servletContext中找到该map，然后将扫码签到的openID的值代替waiting
				 * 放入到map中。
				 * 当代前端websocket轮询时，weSocketHandler中就会查找该uuid中的值是否还是waiting，如果不是
				 * 则将已经替换的openid作为shiro的UsernamePassword的用户名（密码默认为"123"）执行登录操作。
				 */
				servletContext.setAttribute(codeInfo.getScanResult(), openID);
				outMessage = textBuilder.build("扫码完成，等待系统确认...", wxMessage, service);
			}
			break;
		}
		// 返回结果
		return outMessage;
	}

}
