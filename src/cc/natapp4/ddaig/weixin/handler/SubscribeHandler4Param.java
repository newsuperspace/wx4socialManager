package cc.natapp4.ddaig.weixin.handler;

import java.util.ArrayList;
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
 * 【弃用】
 * 专门处理扫描【带参数（场景值）二维码】关注公众号的行为
 * 
 * @author Administrator
 *
 */
//@Component("subscribeHandler4Param")
//@Lazy(true)
public class SubscribeHandler4Param extends AbstractHandler {

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
		Member  member  =  null;
		if (null != user) {
			// 修改该用户的ishere字段为true，表明该用户重新回到了公众号
			user.setIshere(true);
			// 通过带参数二维码定位该新建用户所属的目标层级对象
			String qrcodeParam = mpMessage.getEventKey();
			System.out.println("当前用户扫描的带参数二维码的参数值是：" + qrcodeParam);
			if (StringUtils.isEmpty(qrcodeParam)) {
				// 如果用户扫描的是公众号二维码，不是各层级对象的带参数二维码加入的公众号，则qrcodeParam是""或null
				// 这种情况不用创建Member
				// 更新数据库
				userService.update(user);
				// 向该用户回复TEXT类型消息——“欢迎回来，xxx”
				String username = user.getUsername();
				if (StringUtils.isEmpty(username)) {
					outMessage = textBuilder.build("欢迎回来，" + userInfo.getNickname(), mpMessage, service);
				} else {
					outMessage = textBuilder.build("欢迎回来，" + username, mpMessage, service);
				}
			} else {
				// 如果用户扫描的是层级对象专属的带参数二维码加入的公众号，则qrcodeParam应该是形如："qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
				// 解析字符串，定位用户索要加入的目标层级对象
				String[] splits = qrcodeParam.split("_");
				String temp = splits[1];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				String[] temps = temp.split("\\$");
				// 得到扫码的层级对象的层级数（-1、0、1、2、3、4）
				String level = temps[1];
				
				temp = splits[2];
				// ★★split方法参数是一个正则表达式,由于$是正则表达式中的关键字符,所以需要加上转义字符进行转义.
				temps = temp.split("\\$");
				// 得到扫码的成绩对象的lid
				String lid = temps[1];
				
				// 开始检测用户是否已经加入过该层级，如果没加入过则创建对应该层级的新member
				List<Member> members = null;
				boolean createMember = true;
				switch (level) {
				case "-1":
					members = user.getMembers();
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("minus_first")){
							if(m.getMinusFirstLevel().getMflid().equals(lid)){
								// 扫码的层级对象在之前已经加入过了，不用创建该层级的member，直接跳出循环执行后续操作
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						// 创建member
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setMinusFirstLevel(minusFirstLevel);
					}
					break;
				case "0":
					members = user.getMembers();
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("zero")){
							if(m.getZeroLevel().getZid().equals(lid)){
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setZeroLevel(zeroLevel);
						member.setMinusFirstLevel(zeroLevel.getParent());
					}
					break;
				case "1":
					members = user.getMembers();
					FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("first")){
							if(m.getFirstLevel().getFlid().equals(lid)){
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setFirstLevel(firstLevel);
						member.setZeroLevel(firstLevel.getParent());
						member.setMinusFirstLevel(firstLevel.getParent().getParent());
					}
					break;
				case "2":
					members = user.getMembers();
					SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("second")){
							if(m.getSecondLevel().getScid().equals(lid)){
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setSecondLevel(secondLevel);
						member.setFirstLevel(secondLevel.getParent());
						member.setZeroLevel(secondLevel.getParent().getParent());
						member.setMinusFirstLevel(secondLevel.getParent().getParent().getParent());
					}
					break;
				case "3":
					members = user.getMembers();
					ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("third")){
							if(m.getThirdLevel().getThid().equals(lid)){
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setThirdLevel(thirdLevel);
						member.setSecondLevel(thirdLevel.getParent());
						member.setFirstLevel(thirdLevel.getParent().getParent());
						member.setZeroLevel(thirdLevel.getParent().getParent().getParent());
						member.setMinusFirstLevel(thirdLevel.getParent().getParent().getParent().getParent());
					}
					break;
				case "4":
					members = user.getMembers();
					FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
					for(Member m: members){
						if(m.getDirectLevel().equals("fourth")){
							if(m.getFourthLevel().getFoid().equals(lid)){
								createMember = false;
								break;
							}
						}
					}
					if(createMember){
						member  =  new  Member();
						member.setGrouping(groupingService.queryByTagName("common"));
						member.setUser(user);
						user.getMembers().add(member);
						member.setFourthLevel(fourthLevel);
						member.setThirdLevel(fourthLevel.getParent());
						member.setSecondLevel(fourthLevel.getParent().getParent());
						member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
						member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
						member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
					}
					break;
				}
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
			 * 第一个实例化的member是用作每个新建user用户都必须拥有的一个默认member，用来表示T是这个公众平台的成员
			 * 并且用来记录该成员是否已经完成了“实名认证”
			 * PerssionCenterAction中realName()实名认证方法就是通过查找user用户的这个默认member
			 * 来判断该用户是否已经实名认证了，已经实名认证的默认member.grouping.tag = common
			 * 未认证用户的默认member.grouping.tag = unreal
			 * 
			 * 所以说默认member的作用很大，主要体现在：
			 * （1）逻辑上代表该用户是公众号平台的用户，这是加入其他层级对象的先决条件；
			 * （2）用作判断用户是否已经实名制认证；
			 */
			member  =  new  Member();
			member.setGrouping(groupingService.queryByTagName("unreal"));
			user.getMembers().add(member);
			member.setUser(user);
			/*
			 * 所有用户由于必定作为公众号的成员，因此新加入的成员默认都有一个member
			 * 就是所有层级都为null
			 * 这是因为新改进的系统中由各个member负责保存user在层级对象中的角色标签tag
			 * 而只有minuseFirst\zero\first\second\third\fourth 都是null 才能被设置为tag=minus_first
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
			
			// 通过带参数二维码定位该新建用户所属的目标层级对象
			String qrcodeParam = mpMessage.getEventKey();
			System.out.println("当前用户扫描的带参数二维码的参数值是：" + qrcodeParam);
			if (!StringUtils.isEmpty(qrcodeParam)) {
				// 如果新用户是扫描某个层级对象专属的带参数二维码加入的公众号，则qrcodeParam应该是形如："qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
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
				// 第二次新建的member用来记录正式的层级信息
				member  =  new Member();
				switch (level) {
				case "-1":
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					member.setMinusFirstLevel(minusFirstLevel);
					member.setGrouping(groupingService.queryByTagName("common"));
					break;
				case "0":
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					member.setZeroLevel(zeroLevel);
					member.setMinusFirstLevel(zeroLevel.getParent());
					member.setGrouping(groupingService.queryByTagName("common"));
					break;
				case "1":
					FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
					member.setFirstLevel(firstLevel);
					member.setZeroLevel(firstLevel.getParent());
					member.setMinusFirstLevel(firstLevel.getParent().getParent());
					member.setGrouping(groupingService.queryByTagName("common"));
					break;
				case "2":
					SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
					member.setSecondLevel(secondLevel);
					member.setFirstLevel(secondLevel.getParent());
					member.setZeroLevel(secondLevel.getParent().getParent());
					member.setMinusFirstLevel(secondLevel.getParent().getParent().getParent());
					member.setGrouping(groupingService.queryByTagName("common"));
					break;
				case "3":
					ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
					member.setThirdLevel(thirdLevel);
					member.setSecondLevel(thirdLevel.getParent());
					member.setFirstLevel(thirdLevel.getParent().getParent());
					member.setZeroLevel(thirdLevel.getParent().getParent().getParent());
					member.setMinusFirstLevel(thirdLevel.getParent().getParent().getParent().getParent());
					member.setGrouping(groupingService.queryByTagName("common"));
					break;
				case "4":
					FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
					member.setFourthLevel(fourthLevel);
					member.setThirdLevel(fourthLevel.getParent());
					member.setSecondLevel(fourthLevel.getParent().getParent());
					member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
					member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
					member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
					member.setGrouping(groupingService.queryByTagName("common"));	
					break;
				}
			}
			member.setUser(user);
			user.getMembers().add(member);
			// 写入数据库
			userService.save(user);
			// 向该用户回复TEXT类型消息——“欢迎新成员，xxx”
			outMessage = textBuilder.build("欢迎新成员，" + userInfo.getNickname(), mpMessage, service);
		}
		return outMessage;
	}

}
