package cc.natapp4.ddaig.servletListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cc.natapp4.ddaig.utils.ConfigUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;


/**
 *  对 微信公众号平台 进行 初始化配置
 *  创建WxMpInMemoryConfigStorage类型的config对象，然后进行appID、Secret、Token、AesKey这四个属性的设置，并将其放入到ServletContext域中备用
 * @author Administrator
 *
 */
public class InitWxConfigListener implements ServletContextListener {

	// 微信相关对象
	/*
	 * 有关微信公众号的参数信息（这些信息你应该从自己的微信平台账户后台
	 * https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index获取）
	 */
	protected WxMpInMemoryConfigStorage config; 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {	}

	@Override
	public void contextInitialized(ServletContextEvent arg) {
		// 现获取到ServletContext域,用以保存重要微信公众号平台数据对象config
		ServletContext context = arg.getServletContext();
		
		// STEP1 ------------------创建config配置对象----------------------
		config = new WxMpInMemoryConfigStorage();
		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
		config.setAppId(p.getProperty("appid")); // 设置微信公众号的appid
		config.setSecret(p.getProperty("secret")); // 设置微信公众号的app
		config.setToken(p.getProperty("token")); // 设置微信公众号的token
		// config.setAesKey("..."); // 设置微信公众号的EncodingAESKey 测试账号暂时不用设置
		
		// 将config对象放入到ServletContext域中待用
		context.setAttribute("config", config);
		
		// STEP2 ---------------------servletContext区域内要负责维护一个Map<String,Map<String,String>>类型容器，用作定位下载地址---------------------
		Map<String,Map<String,String>> downloadMap =  new HashMap<String,Map<String,String>>();
		// K=aid （活动的主键）；value为活动文章的本地磁盘文件路径
		Map<String,String> articleMap = new HashMap<String,String>();
		// K=aid ; value=url
		Map<String,String> signinMap = new HashMap<String,String>();
		// 存放层级人员目录下载文件
		Map<String,String> userList4MinusMap = new HashMap<String,String>();
		Map<String,String> userList4ZeroMap = new HashMap<String,String>();
		Map<String,String> userList4FirstMap = new HashMap<String,String>();
		Map<String,String> userList4SecondMap = new HashMap<String,String>();
		Map<String,String> userList4ThirdMap = new HashMap<String,String>();
		Map<String,String> userList4FourthMap = new HashMap<String,String>();
		// 其他可下载资源的URL容器。。。。。
		
		// 存放到downloadMap中
		downloadMap.put("articleMap", articleMap);
		downloadMap.put("signinMap" ,signinMap );
		downloadMap.put("userList4MinusMap" ,userList4MinusMap );
		downloadMap.put("userList4ZeroMap" , userList4ZeroMap);
		downloadMap.put("userList4FirstMap" , userList4FirstMap);
		downloadMap.put("userList4SecondMap" , userList4SecondMap);
		downloadMap.put("userList4ThirdMap" , userList4ThirdMap);
		downloadMap.put("userList4FourthMap" , userList4FourthMap);
		context.setAttribute("downloadMap", downloadMap);
		
		
	}

	
}
