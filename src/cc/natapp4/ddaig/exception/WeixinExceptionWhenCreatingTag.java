package cc.natapp4.ddaig.exception;

/**
 * 当在GroupingAction.createTag()方法中 调用WeixinService4SettingImpl.createTag()
 * 与微信服务器交互的时候，如果出现交互问题（可能是没认证、net故障等），则抛出这个异常。
 * @author Administrator
 *
 */
public class WeixinExceptionWhenCreatingTag extends Throwable {

	
}
