package cc.natapp4.ddaig.weixin.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component("menuClickHandler")
/*
 * 懒加载非常重要，因为当前类也是bean，也同样通过@Component注解将当前类IOC与Spring容器管理之下
 * 又因为当前bean类体中又依靠DI注入需要其他bean的支持（通过@Resource注解实现注入过程），因此在实例化当前Handler类这个
 * Bean的时候，又需要预先实例化DI注入的bean。
 * 
 * 因为，WeixinService4XXX（用来与公众号服务器直接交互的Service）是依靠java-weixin-tool.jar提供的API实现的，而在这个jar包体系下
 * 需要有一个包含目标公众号的config对象注入到公众号MP开发的入口service（也就是WeixinService4XXX的父类的父类——WxMpServiceImpl）中，
 * 而当前工程，config的配置工作是在Servlet启动监听器中实现的并在实例化WeixinService4XXX的时候通过ServletContext域获取，
 * 所以要想让WxMpServiceImpl的所有子类正常启动就必须Servlet容器先启动。
 * 
 * 因此你会发现所有Service（ActivityService、UserService、GroupingService等）都是bean，并且其中DI注入了一个或多个WeixinService4XXX。
 * 
 * Spring容器有一个习惯就是在Spring容器初始化的时候会预先自动将所有Bean都实例化一次，可Spring容器的初始化要早于Servlet初始化，这就造成
 * 当Spring初始化一个Bean的时候，如果这个Bean的DI注入链条中存在WeixinService4XXX，那么就势必会先初始化这些WeixinService4XXX，但在
 * 构建WeixinService4XXX实例的时候又需要先从ServletContext域中获取config，但此时Servlet容器还没启动，因此就得不到config，从而爆出错。
 * 
 * 解决的办法是——在每个WeixinService4XXX类体内，创建config，从而只需对WeixinService4XXX类添加@Lazy注解，告知Spring容器不初始化
 * 次bean即可。
 * 或者，是像当前工程这样，凡事DI注入链条中存在WeixinService4XXX类的，从源头就添加@Lazy注解，从而禁止整个DI链条上所有bean的实例化。
 * 
 */
@Lazy(true)
/**
 * 处理微信公众号按钮类型为WxConsts.BUTTON_CLICK 的事件
 * @author Administrator
 *
 */
public class MenuClickHandler extends AbstractHandler {

	@Resource(name="userService")
	private UserService  userService;
	
	@Resource(name="textBuilder")
	private TextBuilder  textBuilder;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		WeixinService4RecallImpl  service =  (WeixinService4RecallImpl)wxMpService;
		
		WxMpXmlOutMessage outMessage = null;	
		
		String eventKey = wxMessage.getEventKey();
		switch (eventKey) {
		case "self_bm_score":  // 用户在公众号菜单上按下的是获取自身积分的button
		
			 User user = userService.queryByOpenId(wxMessage.getFromUser());
			 
			 if(null==user){
				 // 用户不存在
				 outMessage = textBuilder.build("您的账号不存在，请联系管理员", wxMessage, service);
			 }else{
				 // 用户存在
				 int score = user.getScore();
				 String username =  user.getUsername();
				 
				 StringBuffer  sb  =  new  StringBuffer();
				 sb.append("尊敬的用户 ");
				 sb.append(user.getUsername()+"。您当前的积分是");
				
				 if(StringUtils.isEmpty(score)){
					 // 不存在分数，显示0
					 sb.append("0分，感谢您对社区公益事业的支持！");
				 }else{
					 // 存在分数，如实显示
					 sb.append(score+"分，感谢您对社区公益事业的支持！");
				 }
				 outMessage = textBuilder.build(sb.toString(), wxMessage, service);
			 }
			break;
		
		}
		
		return outMessage;
	}

}
