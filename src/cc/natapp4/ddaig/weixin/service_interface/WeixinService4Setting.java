package cc.natapp4.ddaig.weixin.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCreatingTag;

/**
 * 这个接口用于设计主动调用微信API，实现对公众号的各种设置
 * 
 * 当前WeixinService会与后台页面所对应的WeixinAction_setting()方法频繁交互，
 * 以实现管理员通过JSP页面来实现对公众号和本地数据库关联设置的需要。
 * （1）设置Tag
 * （2）管理用户（设置特定用户的Tag标签，用以让它见到不同的Menu，从而实现对其所享有功能的管控）
 * （3）设置模板消息（待定）
 * （4）设置各种自动回复的消息（待定）
 * （5）客服
 * （6）永久和临时资源的上传、下载
 * （7）其他公众号API所能提供的各种功能
 * @author Administrator
 *
 */
public interface WeixinService4Setting {

	/**
	 * 添加用户标签的过程是
	 * 
	 * 后台JSP管理标签页面 → GroupingAction_createTag()根据属性驱动得到大部标签信息到Grouping对象（包括tag但缺少tagid）
	 *  → WeixinService4Setting.createTag() 主要以tag做参数与公众号交互获得tagid → 将包含全部属性的Grouping对象交给 GroupingService.save()
	 *  保存到数据库
	 * @param grouping
	 * 	gid;    // 调用GroupingService.save()方法的时候自动给出
	 *  groupName;  // 前端提交
	 *	description;  //  前端提交
	 *	logoPath;     //  前端上传图片后自动生成并填写到这里
	 *	tag;     //  前端提交
	 *	tagid; // 这个属性才是调用当前方法与公众号交互后才能获取到的
	 */
	public void createTag(Grouping grouping) throws WeixinExceptionWhenCreatingTag;
	
	/**
	 * 设置某一用户的tag，记住在当前这个系统下，每个用户有且仅有一个tag，便于区分其所享用的功能（菜单来体现）
	 * 所以当给用户设置tag的时候，会用新tag顶替旧tag
	 * 但是如果该用户已经经过实名制认证，通过openID可以找到包含身份证号的User对象，则不能将该User再设置成"未实名制"用户了
	 * @param openID      用户的openID
	 * @param grouping     想要设置成的tag
	 */
	public void  setTag2One(String openID, Grouping grouping);
	
	/**
	 * 设置某一批用户的tag，，记住在当前这个系统下，每个用户有且仅有一个tag，便于区分其所享用的功能（菜单来体现）
	 * @param openIDs    包含需要设置tag的全部用户的openID
	 * @param grouping   设置的目标tag
	 */
	public void  setTag2Many(List<String> openIDs, Grouping grouping);
	
	
	/**
	 * 向某一指定用户发送字符串消息
	 * @param openID   目标用户的OpenID
	 * @param content   字符串内容
	 * 
	 * @return  true表示发送成功，false表示发送失败
	 */
	public boolean sendTextMessage2One(String openID,String content);
	
	
	/**
	 * 当前方法用于在创建公众号菜单时，下方VIEW类型按钮的url参数的时候使用，将目标打开的url传入到该方法中得到的authUrl就是可以
	 * 设置在button的url属性上的认证授权连接了。
	 * 
	 * 
	 * @param redirectURI   形如： http;//www.baidu.com的URL，也就是需要打开的web应用的真实URL
	 * @param scope    		WxConsts.OAUTH2_SCOPE_USER_INFO 弹出授权页面，可通过openid拿到昵称、性别、所在地  
	 *									WxConsts.OAUTH2_SCOPE_BASE 不弹出授权页面，直接跳转到redirectURI，只能获取用户openid
	 * 
	 * @param state        非必须的一个字符串
	 * @return             授权页面的url的字符串authURL，该url可以用作微信公众号菜单view类型按钮跳转页面使用，从而这些页面就可以获取到code值了
	 */
	public String getOauth2Url(String redirectURI, String scope, String state);
	
}
