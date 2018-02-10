package cc.natapp4.ddaig.weixin.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.natapp4.ddaig.weixin.service_implement.WeixinServiceAbstract;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

public abstract  class AbstractBuilder {

	// 打印日志信息用
	private  Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 向官方微信服务器回复消息用的Builder，其主要功能是创建包含要回复给微信服务器
	 * 各种类型（TEXT/IMAGE/VOICE/...）消息的WxMpXmlOutMessage类型对象
	 * @param content    于TEXT类型消息是文本内容；于IMAGE类型消息是mediaID；于VOICE消息是mediaID；于图文消息是mediaID
	 * @param wxMessage  从微信服务器服务器发过来的WxMpXmlMessage对象，从中可获取FromUser，ToUser等关键信息
	 * @param service   WeixinServiceAbstract抽象类，是WxMpServiceImpl的子类，也是当前工程所有创建的 WeixinService的父类
	 * 								通过它可以调用绝大多数 Weixin-java-tools的API
	 * @return   创建包含要回复给微信服务器各种类型（TEXT/IMAGE/VOICE/...）消息的WxMpXmlOutMessage类型对象
	 */
	public abstract WxMpXmlOutMessage build(String content,
		      WxMpXmlMessage wxMessage, WeixinServiceAbstract service) ;
	
}
