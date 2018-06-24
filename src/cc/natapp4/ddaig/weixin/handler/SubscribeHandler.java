package cc.natapp4.ddaig.weixin.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
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
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;
	@Resource(name = "secondLevelService")
	private SecondLevelService secondLevelService;
	@Resource(name = "thirdLevelService")
	private ThirdLevelService thirdLevelService;
	@Resource(name = "fourthLevelService")
	private FourthLevelService fourthLevelService;

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
		if (null != user) {
			// 修改该用户的ishere字段为true，表明该用户重新回到了公众号
			user.setIshere(true);

			// 由于用户在之前“取消关注”时（unsubscribeHandler中）其member中的层级关系已经清空，现在需要重新建立所属层级
			// 通过带参数二维码定位该新建用户所属的目标层级对象
			String qrcodeParam = mpMessage.getEventKey();
			System.out.println("当前用户扫描的带参数二维码的参数值是：" + qrcodeParam);
			Member member = user.getMember();
			if(null==member){
				member  =  new Member();
				member.setUser(user);
				user.setMember(member);
			}
			if (StringUtils.isEmpty(qrcodeParam)) {
				// 如果用户扫描的是公众号二维码，不是各层级对象的带参数二维码加入的公众号，则qrcodeParam是""或null
				member.setFirstLevel(null);
				member.setFourthLevel(null);
				member.setMinusFirstLevel(null);
				member.setSecondLevel(null);
				member.setThirdLevel(null);
				member.setZeroLevel(null);
			} else {
				// 如果用户扫描的是层级对象专属的带参数二维码加入的公众号，则qrcodeParam应该是形如："qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
				// 解析字符串，定位用户索要加入的目标层级对象
				String[] splits = qrcodeParam.split("_");
				String temp = splits[1];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				String[] temps = temp.split("\\$");
				String level = temps[1];
				
				temp = splits[2];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				temps = temp.split("\\$");
				String lid = temps[1];
				
				switch (level) {
				case "-1":
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					member.setMinusFirstLevel(minusFirstLevel);
					break;
				case "0":
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					member.setZeroLevel(zeroLevel);
					member.setMinusFirstLevel(zeroLevel.getParent());
					break;
				case "1":
					FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
					member.setFirstLevel(firstLevel);
					member.setZeroLevel(firstLevel.getParent());
					member.setMinusFirstLevel(firstLevel.getParent().getParent());
					break;
				case "2":
					SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
					member.setSecondLevel(secondLevel);
					member.setFirstLevel(secondLevel.getParent());
					member.setZeroLevel(secondLevel.getParent().getParent());
					member.setMinusFirstLevel(secondLevel.getParent().getParent().getParent());
					break;
				case "3":
					ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
					member.setThirdLevel(thirdLevel);
					member.setSecondLevel(thirdLevel.getParent());
					member.setFirstLevel(thirdLevel.getParent().getParent());
					member.setZeroLevel(thirdLevel.getParent().getParent().getParent());
					member.setMinusFirstLevel(thirdLevel.getParent().getParent().getParent().getParent());
					break;
				case "4":
					FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
					member.setFourthLevel(fourthLevel);
					member.setThirdLevel(fourthLevel.getParent());
					member.setSecondLevel(fourthLevel.getParent().getParent());
					member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
					member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
					member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
					break;
				}
			}
			// 更新数据库
			userService.update(user);
			// 向该用户回复TEXT类型消息——“欢迎回来，xxx”
			String username = user.getUsername();
			if (StringUtils.isEmpty(username)) {
				outMessage = textBuilder.build("欢迎回来，" + userInfo.getNickname(), mpMessage, service);
			} else {
				outMessage = textBuilder.build("欢迎回来，" + username, mpMessage, service);
			}
			// TODO weixin.tag
			// // 然后按照该用户之前的tag来设置该用户的公众平台tag
			// long tagid = user.getGrouping().getTagid();
			// service.getUserTagService().batchTagging(tagid, new
			// String[]{user.getOpenid()});
		} else {
			// 在本地新建用户
			user = new User();
			user.setOpenid(userInfo.getOpenId());
			user.setIshere(true);
			user.setSickname(userInfo.getNickname());
			user.setAddress(userInfo.getCountry() + "," + userInfo.getProvince() + "," + userInfo.getCity());
			user.setRegistrationTime(System.currentTimeMillis());
			user.setSex(userInfo.getSex());

			List<Grouping> list = this.groupingService.queryEntities();
			for (Grouping g : list) {
				if (g.getTag().equals("unreal")) {
					// 新建User对象到本地数据库保存
					user.setGrouping(g); // 新建User一定要与Grouping进行绑定
					// TODO weixin.tag
					// 如果公众号中的用户需要根据grouping的tag设置不同的标签，从而实现个性化菜单，那么可以激活下面的逻辑
					// String[] ids = {userInfo.getOpenId()};
					// try {
					// service.getUserTagService().batchTagging(g.getTagid(),
					// ids);
					// } catch (WxErrorException e) {
					// e.printStackTrace();
					// }
				}
			}
			// 通过带参数二维码定位该新建用户所属的目标层级对象
			String qrcodeParam = mpMessage.getEventKey();
			System.out.println("当前用户扫描的带参数二维码的参数值是：" + qrcodeParam);
			Member member = new Member();
			if (StringUtils.isEmpty(qrcodeParam)) {
				// 如果用户扫描的是公众号二维码，不是各层级对象的带参数二维码加入的公众号，则qrcodeParam是""或null
				member.setUser(user);
				member.setFirstLevel(null);
				member.setFourthLevel(null);
				member.setMinusFirstLevel(null);
				member.setSecondLevel(null);
				member.setThirdLevel(null);
				member.setZeroLevel(null);
			} else {
				// 如果用户扫描的是层级对象专属的带参数二维码加入的公众号，则qrcodeParam应该是形如："qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
				// 解析字符串，定位用户索要加入的目标层级对象
				String[] splits = qrcodeParam.split("_");
				String temp = splits[1];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				String[] temps = temp.split("\\$");
				String level = temps[1];
				
				temp = splits[2];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				temps = temp.split("\\$");
				String lid = temps[1];
				
				member.setUser(user);
				switch (level) {
				case "-1":
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					member.setMinusFirstLevel(minusFirstLevel);
					break;
				case "0":
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					member.setZeroLevel(zeroLevel);
					member.setMinusFirstLevel(zeroLevel.getParent());
					break;
				case "1":
					FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
					member.setFirstLevel(firstLevel);
					member.setZeroLevel(firstLevel.getParent());
					member.setMinusFirstLevel(firstLevel.getParent().getParent());
					break;
				case "2":
					SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
					member.setSecondLevel(secondLevel);
					member.setFirstLevel(secondLevel.getParent());
					member.setZeroLevel(secondLevel.getParent().getParent());
					member.setMinusFirstLevel(secondLevel.getParent().getParent().getParent());
					break;
				case "3":
					ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
					member.setThirdLevel(thirdLevel);
					member.setSecondLevel(thirdLevel.getParent());
					member.setFirstLevel(thirdLevel.getParent().getParent());
					member.setZeroLevel(thirdLevel.getParent().getParent().getParent());
					member.setMinusFirstLevel(thirdLevel.getParent().getParent().getParent().getParent());
					break;
				case "4":
					FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
					member.setFourthLevel(fourthLevel);
					member.setThirdLevel(fourthLevel.getParent());
					member.setSecondLevel(fourthLevel.getParent().getParent());
					member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
					member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
					member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
					break;
				}
			}
			// 写入数据库
			user.setMember(member);
			userService.save(user);
			// 向该用户回复TEXT类型消息——“欢迎新成员，xxx”
			outMessage = textBuilder.build("欢迎新成员，" + userInfo.getNickname(), mpMessage, service);
		}
		return outMessage;
	}

}
