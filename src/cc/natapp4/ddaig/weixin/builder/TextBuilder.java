package cc.natapp4.ddaig.weixin.builder;

import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.weixin.service_implement.WeixinServiceAbstract;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

@Component("textBuilder")
public class TextBuilder extends AbstractBuilder {

	/**
	 * content 是需要回传给用户的字符串信息
	 */
	@Override
	public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WeixinServiceAbstract service) {

		// 通过WxMpXmlOutMessage类创建文本类型消息对象
		WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(content).fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();

		return m;
	}

}
