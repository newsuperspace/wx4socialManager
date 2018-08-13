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

		// 清除该用户member中的所有层级关系，因此“取消关注”公众号是目前用户唯一可以脱离之前所处层级对象的方法
		Member member = user.getMember();
		if(null==member){
			member = new Member();
			// ★★★注意啊，user和member是一对一关系，因此在级联创建的时候必须相互set，否则如果只设置user中member不设置member中的user，会爆出attempted 投 assign id from null  one-to-one property的异常
			user.setMember(member);
			member.setUser(user);
		}else{
			member.setMinusFirstLevel(null);
			member.setZeroLevel(null);
			member.setFirstLevel(null);
			member.setSecondLevel(null);
			member.setFirstLevel(null);
			member.setSecondLevel(null);
			member.setThirdLevel(null);
			member.setFourthLevel(null);
		}
		// 清除Manager（如果有的话）
		Manager manager = user.getManager();
		if (null != manager) {
			// 切断其与user对象的关联
			manager.setUser(null);
			user.setManager(null);
			// 删除该manager对象
			managerService.delete(manager);
			// 如果该用户已经是管理员对象（user.manager!=null）
			// 则说明该用户的tag当前也是管理层级（minus_first/zero/first/second/third/fourth）,则取消关注后应该回复common
			Grouping g = groupingService.queryByTagName("common");
			user.setGrouping(g);
			// TODO weixin.tag 如果有需要变更微信个性化菜单的需要，这里需要同步向为新服务器变更该用户的tag（通过tagID）
		}
		// 向数据库保存所有修改
		userService.update(user);
		return null;
	}

}
