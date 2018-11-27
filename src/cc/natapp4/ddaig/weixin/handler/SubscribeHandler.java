package cc.natapp4.ddaig.weixin.handler;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 专门处理新加入公众号平台的消息
 * 
 * @author Administrator
 *
 */
@Component("subscribeHandler")
@Lazy(true)
public class SubscribeHandler extends AbstractHandler {

	// =============================DI注入=============================
	/*
	 * 需要对新加入公众号的用户进行数据库操作，因此需要获取专门用于操作User用户的Service
	 * （1）通过openID与数据库中已有用户进行比对，如果发现已存在，则说明该用户是之前新加入的用户,重新设置flag ishere = true
	 * （2）如果不存在则创建新的User对象到数据库，保存该用户的openID并设置ishere = true
	 */
	@Resource(name = "userService")
	private UserService userService;
	/**
	 * 对于新加入的用户需要设置他的Grouping，因此需要通过GroupingService得到当前系统中（数据库中记录）
	 * 拥有那些tag标签（每个标签对应一个Grouping对象） 然后从中选择"未认证用户"标签设置给新用户
	 */
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	/*
	 * 需要向微信服务器回传文本信息，则获取文本构造器
	 */
	@Resource(name = "textBuilder")
	private TextBuilder textBuilder;

	
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage mpMessage, Map<String, Object> context, WxMpService mpService,
			WxSessionManager sessionManager) throws WxErrorException {

		logger.info("新加入公众平台的用户的openID是：" + mpMessage.getFromUser());

		WeixinService4RecallImpl service = (WeixinService4RecallImpl) mpService;
		WxMpUser userInfo = service.getUserService().userInfo(mpMessage.getFromUser());
		WxMpXmlOutMessage outMessage = null;

		// 检查该用户是否之前加入过公众号,如果之前加入过公众号又退出了，那么他在本地数据库一定留有信息（不会随着退出而销毁）并且openid始终一致
		User user = userService.queryByOpenId(userInfo.getOpenId());
		Member member = null;
		if (null != user) {
			// 修改该用户的ishere字段为true，表明该用户重新回到了公众号
			user.setIshere(true);
			userService.update(user);
			// 向该用户回复TEXT类型消息——“欢迎回来，xxx”
			String username = user.getUsername();
			if (StringUtils.isEmpty(username)) {
				outMessage = textBuilder.build("欢迎回来，" + userInfo.getNickname(), mpMessage, service);
			} else {
				outMessage = textBuilder.build("欢迎回来，" + username, mpMessage, service);
			}
		} else {
			// 在本地新建用户
			user = new User();
			user.setOpenid(userInfo.getOpenId());
			user.setIshere(true);
			user.setSickname(userInfo.getNickname());
			user.setAddress(userInfo.getCountry() + "," + userInfo.getProvince() + "," + userInfo.getCity());
			user.setRegistrationTime(System.currentTimeMillis());
			user.setSex(userInfo.getSex());
			user.setMembers(new ArrayList<Member>());
			/*
			 * 第一个实例化的member是用作每个新建user用户都必须拥有的一个默认member，用来表示该用户是这个公众平台的成员（member）
			 * 并且用来记录该成员是否已经完成了“实名认证”
			 * PerssionCenterAction中realName()实名认证方法就是通过查找user用户的这个默认member
			 * 来判断该用户是否已经实名认证了，已经实名认证的默认member.grouping.tag = common
			 * 未认证用户的默认member.grouping.tag = unreal
			 * 
			 * 所以说默认member的作用很大，主要体现在： （1）逻辑上代表该用户是公众号平台的用户，这是加入其他层级对象的先决条件；
			 * （2）用作判断用户是否已经实名制认证；
			 */
			member = new Member();
			// 默认member初始设置的tag就是unreal，需要用户通过微信端的"用户中心"自行认证
			member.setGrouping(groupingService.queryByTagName("unreal"));
			user.getMembers().add(member);
			member.setUser(user);
			/*
			 * 所有用户由于必定作为公众号的成员，因此新加入的成员默认都有一个member 就是所有层级都为null
			 * 这是因为新改进的系统中由各个member负责保存user在层级对象中的角色标签tag
			 * 而只有minuseFirst\zero\first\second\third\fourth 都是null
			 * 才能被设置为tag=minus_first
			 * 用作minusFirst层级的管理者，因此为了保存这个tag就必须在新建用户的时候强制性创建
			 * 一个所有层级都为null的默认member，逻辑上用来表示该用户是当前公众号的
			 * 正如Member.getDirectLevel()会对这种member返回“platform”字样
			 */
			member.setMinusFirstLevel(null);
			member.setZeroLevel(null);
			member.setFirstLevel(null);
			member.setSecondLevel(null);
			member.setThirdLevel(null);
			member.setFourthLevel(null);
			// 将新建的user写入数据库，并级联地（save-update）将默认member也一并写入数据库
			userService.save(user);
			// 向该用户回复TEXT类型消息——“欢迎新成员，xxx”
			outMessage = textBuilder.build("欢迎新成员，" + userInfo.getNickname(), mpMessage, service);
		}
		return outMessage;
	}
	
	
	
}
