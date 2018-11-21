package cc.natapp4.ddaig.weixin.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.ManagerService;
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 用户取消关注公众号事件发来的消息，由本handler处理
 * 
 * @author Administrator
 *
 */
@Component("unsubscribeHandler")
@Lazy(true)
public class UnsubscribeHandler extends AbstractHandler {

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "memberService")
	private MemberService memberService;
	@Resource(name = "managerService")
	private ManagerService managerService;
	@Resource(name = "groupingService")
	private GroupingService groupingService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage mpMessage, Map<String, Object> context, WxMpService mpService,
			WxSessionManager sessionManager) throws WxErrorException {

		logger.info("取消关注公众平台的用户的openID是：" + mpMessage.getFromUser());

		WeixinService4RecallImpl service = (WeixinService4RecallImpl) mpService;
		WxMpUser userInfo = service.getUserService().userInfo(mpMessage.getFromUser());

		User user = userService.queryByOpenId(userInfo.getOpenId());
		user.setIshere(false);

		// 向数据库保存所有修改
		userService.update(user);
		return null;
	}

}
