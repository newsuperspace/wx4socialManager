package cc.natapp4.ddaig.utils;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_implement.WeixinServiceAbstract;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 【改造后，具体改造原因请参见标★的注释部分】
 * 本工具类的作用是对从微信端（通常是菜单）发来的功能请求，一般是通过weixinAction_recall.action 负责响应微信端请求
 * 最终会将请求下发到各个Handler负责处理，如果某些功能预先需要用户进行实名认证，则可以通过本类提供的工具方法进行自动判定。
 * 
 * @author Administrator
 *
 */
@Component("checkRealNameUtils")
@Lazy(true)
public class CheckRealNameUtils {

/*
	★★★★★ 在之前对opensessionInViewFilter的拦截路径有了更深入的认识之后，18-11-21日我又对其余
	Spring容器applicationContext的联动有了更深入的认识，由于整个系统应用的applicationContxt是
	通过最开始的ContextLoaderListener配置创建，因此之后每一次符合OpenSessionInViewFilter拦截
	规则的访问会被拦截并对自启动的applicationContext所创建的所有Bean（包括Action、Service、Dao等）
	进行全局性的Hibernate的session控制，而对于一开始设计的CheckRealNameUtils（原本最为静态类）中
	通过private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	新建的context与自动创建的applicationContext是两个不同的Spring容器，而OpenSessionInViewFilter
	只负责管理自动创建的applicationContext所产生的bean对数据库的访问时的session，而手动创建的context
	不再管辖范围内，所以才会导致当ScaneHandler处理扫描二维登录的业务逻辑时，在调用原CheckRealNameUtils.check()
	校验当前用户是否已经完成实名认证的时候会爆出Hibernate的懒加载异常 “no session”，这就是因为
	原CheckRealNameUtils中userService是通过手动创建的context获取的，其所有数据库访问都不在
	OpensessionInViewFilter的管辖范围内，因此保持了默认的用完session就关闭的策略， 从而当通过
	UserService查找出的user持久化状态对象（此时已经伴随session的关闭失效了）进一步获取被设置成
	懒加载的members的时候，会爆出“no session”异常的原因 ★★★★★
 */
//	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="textBuilder")
	private TextBuilder textBuilder;
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
	public  WxMpXmlOutMessage check(String openid, WxMpXmlMessage wxMessage, WeixinServiceAbstract service){

		WxMpXmlOutMessage outMessage = null;
		User user = userService.queryByOpenId(openid);
		//  用来存储用户user的默认member，该member作为用户加入公众号的证明，其不属于任何层级因此从minusFirstLevel开始都是null
		Member  member =  null;
		for(Member m: user.getMembers()){
			// 查找到该用户的默认member，因为只有默认member.grouping.tag 中才保存有unreal，用作是否实名认证的唯一证据
			if(null==m.getMinusFirstLevel()){
				member  =  m;
			}
		}
		// 开始判断默认member.grouping.tag是否为unreal
		if(member.getGrouping().getTag().equals("unreal")){
			// 如果没有实名认证成功，则直接向用户微信端回复信息
			String content = "您所使用的功能需要先完成实名认证 ，请点击“便民服务” → “用户中心” 完成实名认证";
			System.out.println(content);
			outMessage = textBuilder.build(content, wxMessage, service);
			return outMessage;
		}
		return outMessage;
	}

	
	
	
	
	
}
