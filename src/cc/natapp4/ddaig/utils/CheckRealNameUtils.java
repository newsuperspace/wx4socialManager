package cc.natapp4.ddaig.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinServiceAbstract;
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Setting;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 本工具类的作用是对从微信端（通常是菜单）发来的功能请求，一般是通过weixinAction_recall.action 负责响应微信端请求
 * 最终会将请求下发到各个Handler负责处理，如果某些功能预先需要用户进行实名认证，则可以通过本类提供的工具方法进行自动判定。
 * 
 * @author Administrator
 *
 */
public class CheckRealNameUtils {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	/**
	 * 负责判断当前从微信端发来请求的用户是否已经完成实名认证（所有通过微信端加入公众号的用户都会自动在数据库user中创建基本数据信息，而所有新用户的grouping.tag == unreal）
	 * ，如果通过openid找到的该用户的grouping.tag == "unreal" 则说明该用户没有完成实名认证，因此组织好回复信息后，返回false
	 * 
	 * 这个时候所有调用本静态方法的handler中通过if语句进行判断，true就可以执行各个handler中的后续逻辑，如果一旦是false则直接return 作为参数传递进来的outMessage
	 * 由于引用类型是"址传递"方式，因此作为参数传递到方法中的outMessage也会保留有方法对其的更改，方法会将必要的问题提示信息写入到outMessage中，这样在外部调用
	 * 本方法的handler也直接返回已经写完回复信息的outMessage给微信端即可。
	 * 
	 * 需要注意的是：
	 * 非引用类型，例如第一个参数String类型，这种参数是"值传递"，外部作为参会的变量与方法的参数相当于两个变量，对参数的更改并不能影响外部的变量。
	 * 
	 * @param openid		来访用户的openid
	 * @param wxMessage		处理微信请求的handler的handler方法的一个参数
	 * @param service		处理微信请求的service
	 * @param outMessage	handler的handle方法中的一个用于向微信端做出回复的封装着回复信息的对象
	 * 
	 * @return
	 * null: 无话可说，通过实名认证
	 * !null: 有话可说，未通过实名认证
	 */
	public static WxMpXmlOutMessage check(String openid, WxMpXmlMessage wxMessage, WeixinServiceAbstract service){

		WxMpXmlOutMessage outMessage = null;
		
		WeixinService4Setting weixinService4Setting = (WeixinService4Setting) context.getBean("weixinService4Setting");
		UserService userService = (UserService) context.getBean("userService");
		User user = userService.queryByOpenId(openid);
		if(user.getGrouping().getTag().equals("unreal")){
			// 如果没有实名认证成功，则直接向用户微信端回复信息
			String content = "您所使用的功能需要先完成实名认证 ，请点击“便民服务” → “用户中心” 完成实名认证";
			System.out.println(content);
			TextBuilder  textBuilder  =  (TextBuilder) context.getBean("textBuilder");
			outMessage = textBuilder.build(content, wxMessage, service);
			return outMessage;
		}

		return outMessage;
	}

	
	
	
	
	
}
