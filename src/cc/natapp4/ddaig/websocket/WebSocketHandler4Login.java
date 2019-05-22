package cc.natapp4.ddaig.websocket;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler4Login extends TextWebSocketHandler {

	
	/**
	 * 当WebSocket连接关闭后，处理善后(释放资源)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		
		String uuid = (String) session.getAttributes().get("uuid");
		String qrcode = (String) session.getAttributes().get("qrcode");
		
		context.removeAttribute(uuid);
		String urlQR = context.getResource("/"+qrcode).getPath();
		System.out.println("删除登录用二维码URL：" + urlQR);
		File  file  =  new File(urlQR);
		// 删除用于登录用的密钥二维码
		file.delete();
		// 我们自己的善后处理完毕后，要记得将流程交还给父类，执行默认的关闭ws连接的逻辑 ★
		super.afterConnectionClosed(session, status);
	}

	/**
	 * 处理每次桌面前端通过WebSocket连接发来的Text信息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		/**
		 * ★★★★★
		 *  由于当前WebSocket处理类是基于SpringMVC框架入口进入的websocket协议请求（Struts2不支持WebSocket响应）
		 *  而不是像我们所写的Utils工具类中所使用ServletActionContext静态类是被基于Struts2框架下的Action所调用的
		 *  因此才可以使用ServletActionContext静态类获取Servlet相关的对象。
		 *  
		 *  因此如果要想在SpringMVC框架下获取到Servlet相关对象（例如四个作用域中的ServletContext）需要通过Spring框架
		 *  提供的API接口而不是使用Struts2框架提供的接口（ServletActionContext）。
		 *  
		 *  因此这里需要使用SpringMVC的静态类RequestContextHolder提供的接口来获取与servlet相关的对象.
		 *  这里需要特别注意一下，我的建议就是以后学学SpringMVC吧，既强大又方便！
		 */
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		
		// 首先获取从前端发送来的“自定义协议信息”字符串，形如 “type:logined,uuid:32947jfj92734,content:temp/1/2/ds83jfs.gif”
		String msg = message.getPayload();
		// 分解协议字符串
		String[] split = msg.split(",");
		String[]  typeArr = split[0].split(":");
		String[] uuidArr = split[1].split(":");
		String[] contentArr = split[2].split(":");
		// 查看type类型，了解前端意图（已经成功建立连接、寻问用户是否已经扫码、告知服务器端ws关闭连接）
		switch (typeArr[1].trim()) {
		case "start":   // 已经建立ws连接，有前端告知咱们服务器等待扫码的uuid是什么
			context.setAttribute(uuidArr[1], "waiting");
			System.out.println("WebSocket已开启，已将uuid为"+uuidArr[1]+"放入到了ServletContext域中，等待用户微信扫码认领");
			// 为了善后需要将必要信息放入WebSocket的session中保存起来备用
			session.getAttributes().put("uuid", uuidArr[1]);
			session.getAttributes().put("qrcode", contentArr[1]);
			break;
		case "logined":   // 前端让咱们服务器端，去ServletContext域中查找指定uuid是否已经被用户扫描了
			String  openid  = (String)context.getAttribute(uuidArr[1]);
			StringBuffer sb  =  new StringBuffer();
			if(!openid.equals("waiting")){
				// 用户已经扫描该码，并将该用户的openid放入，此时已经改将openid传给前端
				sb.append("success,");
				sb.append(openid);
				session.sendMessage(new TextMessage(sb.toString())); 
			}else{
				sb.append("waiting,");
				sb.append("继续等待吧");
				session.sendMessage(new TextMessage(sb.toString())); 
			}
			break;
		case "close":    // 前端告诉咱们是时候关闭ws连接了，但是我们后端要先做好收尾工作————自动执行afterConnectionClosed()中的收尾逻辑
			session.close();
			break;
		}
	}

	
}
