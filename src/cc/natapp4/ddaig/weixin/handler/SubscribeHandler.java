package cc.natapp4.ddaig.weixin.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.domain.Grouping;
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
 * @author Administrator
 *
 */
@Component("subscribeHandler")
@Lazy(true)
public class SubscribeHandler extends AbstractHandler {
	
	/*
	 * 需要对新加入公众号的用户进行数据库操作，因此需要获取专门用于操作User用户的Service
	 * （1）通过openID与数据库中已有用户进行比对，如果发现已存在，则说明该用户是之前新加入的用户,重新设置flag ishere = true
	 * （2）如果不存在则创建新的User对象到数据库，保存该用户的openID并设置ishere = true
	 */
	@Resource(name="userService")
	private UserService  userService;
	/**
	 * 对于新加入的用户需要设置他的Grouping，因此需要通过GroupingService得到当前系统中（数据库中记录）
	 * 拥有那些tag标签（每个标签对应一个Grouping对象）
	 * 然后从中选择"未认证用户"标签设置给新用户
	 */
	@Resource(name="groupingService")
	private GroupingService  groupingService;
	/*
	 * 需要向微信服务器回传文本信息，则获取文本构造器
	 */
	@Resource(name="textBuilder")
	private TextBuilder  textBuilder;

	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage mpMessage, Map<String, Object> context, WxMpService mpService,
			WxSessionManager sessionManager) throws WxErrorException {

		logger.info("新加入公众平台的用户的openID是："+mpMessage.getFromUser());
		
		WeixinService4RecallImpl  service =   (WeixinService4RecallImpl) mpService;
		WxMpUser userInfo = service.getUserService().userInfo(mpMessage.getFromUser());
		
		WxMpXmlOutMessage outMessage=null;
		
		// 检查该用户是否之前加入过公众号
		User user = userService.queryByOpenId(userInfo.getOpenId());
		if(null!=user){
			// 修改该用户的ishere字段为true，表明该用户重新回到了公众号
			user.setIshere(true);
			userService.update(user);
			// 向该用户回复TEXT类型消息——“欢迎回来，xxx”
			outMessage = textBuilder.build("欢迎回来，"+userInfo.getNickname(), mpMessage, service);
			// 然后按照该用户之前的tag来设置该用户的公众平台tag
			long tagid = user.getGrouping().getTagid();
			service.getUserTagService().batchTagging(tagid, new String[]{user.getOpenid()});
		}else{
			// 在本地新建用户，然后再公众平台设置该用户的tag，以显示功能入口——个性化菜单
			user =  new  User();
			user.setOpenid(userInfo.getOpenId());
			user.setIshere(true);
			List<Grouping> list = this.groupingService.queryEntities();
			for(Grouping g: list){
				if(g.getTag().equals("no_real_name_user")){
					
					// 新建User对象到本地数据库保存
					user.setGrouping(g);  // 新建User一定要与Grouping进行绑定
					this.userService.save(user);
					
					// 最后在公众号中设置"非认证用户"的tag就可以了
					String[] ids = {userInfo.getOpenId()};
					try {
						service.getUserTagService().batchTagging(g.getTagid(), ids);
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
				}
			}
			// 向该用户回复TEXT类型消息——“欢迎新成员，xxx”
			outMessage = textBuilder.build("欢迎新成员，"+userInfo.getNickname(), mpMessage, service);
		}
		return outMessage;
	}

}
