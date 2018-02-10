package cc.natapp4.ddaig.json.returnMessage;

import java.io.Serializable;

/**
 * 用于回复前端通过Ajax发来的请求
 * 当前JavaBean类中存放服务器处理结果的回复信息
 * 然后将当前类实例放入到值栈栈顶
 * 同时被请求的Action的方法应该返回"json"结果集索引字符串
 * 如此struts-json-plugin.jar这个Struts2插件中用于向前端返回JSON格式数据的结果集
 * 就能从栈顶中取出取出这个对象，然后对类进行反射分析，将对象中数据解析成JSON格式字符串
 * 然后返回给前端正在等待的AJAX
 * 
 * 当前类是最普通的回复消息，只包含两个字段——
 * boolean result  true表示处理成功；false表示处理失败
 * String message  说明信息
 * 
 * @author Administrator
 *★★ 用作JSON插件扫描解析的JavaBean类必须是public，private会让该类不可见也就不能进行反射了 ★★
 */
public class ReturnMessage4Common implements Serializable {

	private String message;
	private boolean result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public ReturnMessage4Common(String message, boolean result) {
		this.message = message;
		this.result = result;
	}

	public ReturnMessage4Common() {
		// 默认构造器
	}

}
