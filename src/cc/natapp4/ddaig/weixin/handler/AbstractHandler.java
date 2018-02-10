package cc.natapp4.ddaig.weixin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;

/**
 * 定义一个 “消息路由处理器类”的抽象类
 * 用以规范所有处理器类的模态
 * 
 * 抽象类继承自处理器类都需要继承的父类——WxMpMessageHandler
 * 并实现其中的handle()方法
 * 你会在当前抽象类的所有实现类中都发现handle()方法的实现，
 * 而最重要的业务逻辑就存在于handle()中 ★★
 * 
 * @author Binary Wang
 *
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
	// 打印日志信息的logger对象
    protected Logger logger = LoggerFactory.getLogger(getClass());
    // 用于负责处理JSON的gson对象
    protected final Gson gson = new Gson();

}

