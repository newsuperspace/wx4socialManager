package cc.natapp4.ddaig.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.bean.SignBean;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4RecallImpl;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;


/**
 * 用来处理来自微信服务器的请求的专属Action，the one and only！
 * @author Administrator
 *
 */
@Controller("weixinAction")
@Scope("prototype")
@Lazy(true)
public class WeiXinAction extends ActionSupport implements ModelDriven<SignBean> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// ===========================================微信相关对象
	// 有关微信公众号的参数信息（这些信息你应该从自己的微信平台账户后台获取）
	protected WxMpInMemoryConfigStorage config=(WxMpInMemoryConfigStorage) ServletActionContext.getServletContext().getAttribute("config");
	
	// ===========================================各种Service
	// recall从官方服务器发来的消息
	@Resource(name="weixinService4Recall")
	protected WeixinService4RecallImpl mpService4Recall;   // 用于自动处理消息
	@Resource(name="weixinService4Setting")
	protected WeixinService4SettingImpl  mpService4Setting;   // 用于公众平台初始化和一般设置
	
	
	//============================================模型驱动
	// struts2的action相关对象
	private SignBean signBean = new SignBean();

	// ============================================Action方法
	/**
	 * 浏览器访入，用以验证当前服务器端应用是否运行正常
	 * 
	 * @return  Struts结果集识别字符串
	 */
	public String welcome() {
		return SUCCESS;
	}
	
	/**
	 * 负责响应从微信官方服务器发来的请求，是公众号与当前服务器的入口点
	 * 
	 * http://ddaig.nat200.top/weixin/weixinAction_recall.action  这个连接会设置在
	 * https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index的
	 * 接口配置信息一栏中的URL中，微信公众号服务器会通过访问我们自己的服务器来建立公众号与我们自己服务器的连接
	 * 这样当微信端用户通过微信执行某种操作的时候，微信服务器就会知道将用户的操作数据传递到我们这里，我们的服务器处理完成后再通过微信服务器返回给用户展示
	 * 
	 * @return  必须是null
	 */
	public String recall(){
		// STEP1 首先验证当前这次访问是否来自微信官方服务器
		String signResult = mpService4Recall.sign(this);
		switch (signResult) {
		case "first":
			System.out.println("本地服务器与微信服务器握手成功");
			/*
			 *  首次哦手 应该立刻返回给微信服务器，并且创建一个线程——睡眠5秒后,
			 *  确保微信服务器已经接收到回复的确认哦手信息并与当前第三方服务器建立开发关系后
			 *  执行InitPlatform()方法中对公众号进行初始化的设置操作
			 */
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// 先睡眠10秒 = 10000毫秒，确保微信官方服务器已经与当前第三方服务器建立关系
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						logger.error("WeixinAction中执行初始化公众号配置的线程出现问题");
						e.printStackTrace();
					}
					// 然后执行初始化设置公众号的逻辑
					mpService4Setting.InitPlatform();
				}
			}).start();
			
			/**
			 * ★★★★★Struts2根据不同情况返回的结果也不同★★★★★
			 * （1）正常页面的请求/响应，由于需要将响应的jsp页面传递给浏览器显示，因此需要return "结果集索引字符串"，然后struts2就可以根据struts-xxx.xml配置的package中的结果集索引来返回应用中指定的jsp页面了
			 * （2）Ajax请求/响应，由于ajax通常是通过json对象进行数据交换的，而不需要响应具体JSP页面以供前端显示，因此Struts2首先需要在容器初始化的时候通过整个项目buildpath一个名叫
			 * struts-json-plugin.jar的插件，该插件会拓展一个新结果集，然后就可以通过return “结果集索引字符串” 索引到struts-xxx.xml配置中的一个结果集，该结果集的type就是新拓展出来的
			 * json类型（（1）中的类型是默认的dispatcher请求转发类型），这样Struts2就会调用struts-json.plugin.jar中定义的这个名叫json的结果集处理类来从当前请求的值栈的对象栈栈顶中取出
			 * 你预先放入的javaBean，并对其进行反射，自动构建出一个符合json格式的字符串（用属性名/属性值构建），并通过ajax回传给前端，前端就可以获取这份从服务器返回的数据了。
			 * （3）WebSocket通讯是通过SpringMVC实现的，不在Struts2的讨论范畴。
			 * （4）如果你回传的数据已经通过原生的Serlvet的方式写入到了HttpServletResponse的响应输出流中去了（response.getWriter().write("xxxxxxx")），然后你想直接返回这段字符数据流
			 * 那么只需要return null 就可以了。方法返回null，则代表不需要转交给任何结果集进行处理，自然也就原封不动的将response回传给请求者了。这一点对于微信开发非常重要！！！★★
			 */
			return null;  // 用来与微信服务器进行握手的“signature”加密签名已经写入到了HttpServletResponse中了，可以通过返回null告知Struts2的结果集不用处理该响应，直接向请求方返回即可。
		case "normal":
			// 确认是来自微信官方服务器的常态化消息，直接放行到STEP2中去处理即可
			System.out.println("开始处理微信端发来的消息");
			break;
		case "error":
			// 直接ruturn null 即可，Error信息已经在调用sign()方法的时候就写入到了HttpServletResponse的字节输出流中了
			System.out.println("本地服务器与微信服务器握手失败");
			return null;
		}

		// STEP2 判断加密类型
		String encryptType = mpService4Recall.encrypt();
		switch (encryptType) {
		case "raw":
			// STEP 3 真正的业务逻辑都在这里 ★
			try {
				/*
				 * 从微信服务器发来的格式化（Xml字符串格式）数据都在HttpServletRequst的输入流中呢
				 * 通过WxMpXmlMessage.fromXml()方法对输入流的处理，会解析其中的Xml格式字符串并自动生成WxMpXmlMessage类型对象方便实用
				 * 然后通过调用mpService4Recal.route()方法会根据在mypServiceRecall初始化时就准备好的router来实现对包含微信端意图消息的
				 * WxMapXmlMessage对象参数进行解析，并根据router中设置的handler来执行预定的处理逻辑。
				 * handler在处理完微信端发来的请求后会返回包含有回复信息的WxMapXmlOutMessage对象，剩下的就是将对象通过toXml()转化为Xml字符串
				 * 并放入到HttpServletResponse的输出流中返回给微信服务器就完成了"应答"。
				 */
				WxMpXmlOutMessage outMessage = mpService4Recall.route(WxMpXmlMessage.fromXml(ServletActionContext.getRequest().getInputStream()));
				if(null!=outMessage){
					// 向包含回复信息的XML格式字符串写入到HttpResponse响应实体部分后，完成全部被动回复消息的处理过程
					ServletActionContext.getResponse().getWriter().write(outMessage.toXml());
				}else{
					// 没有任何回复内容的时候建议向微信服务器回复“”或“success”，否则微信服务器会认为你没收到，而每个5秒重试一次相同事件，连续三次
					ServletActionContext.getResponse().getWriter().write("");
				}
			} catch (IOException e) {
				logger.error("获取HttpRequest的InputStream时出现异常");
				e.printStackTrace();
			}
			break;
		case "aes":
			// 处理加密信息
//			mpService4Recall.route(WxMpXmlMessage.fromEncryptedXml(is, wxMpConfigStorage, timestamp, nonce, msgSignature))
			break;
		case "unknown":
			// 处理不知名加密类型信息
			logger.warn("接收到来自微信服务器的不知名加密类型信息");
			try {
				// 给微信服务器回复空字符串或者"success"，避免它5秒后再重复发送两次请求
				ServletActionContext.getResponse().getWriter().write("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		
		/*
		 * ★★★返回值为null，可以执行完action中的方法后直接返回响应response，而不必要再通过Result结果集来组织返回结果
		 * 因此也就不会返回该action之前经过的所有Interceptor了，
		 * 所以必须在此之前手动设置response的字符集能头部信息，不然response的实体内容的输出流在浏览器端解析时会出现乱码 ★★★
		 * 
		 * 而对于response编码的设置已经在一开始的sign()方法中实现了，不用再设置了。
		 */
		return null;
	}

	
	

	@Override
	public SignBean getModel() {
//		StringBuffer requestURL = ServletActionContext.getRequest().getRequestURL();
//		System.out.println(requestURL.toString());
		return this.signBean;
	}

}
