package cc.natapp4.ddaig.json.returnMessage;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

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
public class ReturnMessage4Appoint implements Serializable {

	private String message;   // 包含回复信息
	private boolean result;   // 处理结果True为成功;false为失败
	
	private int lowest;   // 被任命的管理者级别（controllerNum+1,遵循“不在其位，不谋其政”原则使得当前层次管理者只能委任次一级的层次，不能越级委任）
	private int controllerNum;   // 执行任命操作的管理者的级别（-1、0、1、2、3、4、10086）
	
	// 对于Admin来说，系统中所有 街道层级对象都在这个容器里
	List<MinusFirstLevel>  minusLevels;
	
	/*
	 * 功能1： 对于非Admin的操作者来说，当前操作者的层级对象会保存在下列属性中的一个里，
	 * 并根据controllerNum来定位
	 * 功能2： 供给userAction.getAppointSelectInfo()中存放被查找的层级对象
	 */
	MinusFirstLevel  minusFirst;
	ZeroLevel zero;
	FirstLevel first;
	SecondLevel second;
	ThirdLevel third;
	FourthLevel fourth;
	
	
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
	public int getLowest() {
		return lowest;
	}
	public void setLowest(int lowest) {
		this.lowest = lowest;
	}
	public int getControllerNum() {
		return controllerNum;
	}
	public void setControllerNum(int controllerNum) {
		this.controllerNum = controllerNum;
	}
	public List<MinusFirstLevel> getMinusLevels() {
		return minusLevels;
	}
	public void setMinusLevels(List<MinusFirstLevel> minusLevels) {
		this.minusLevels = minusLevels;
	}
	public MinusFirstLevel getMinusFirst() {
		return minusFirst;
	}
	public void setMinusFirst(MinusFirstLevel minusFirst) {
		this.minusFirst = minusFirst;
	}
	public ZeroLevel getZero() {
		return zero;
	}
	public void setZero(ZeroLevel zero) {
		this.zero = zero;
	}
	public FirstLevel getFirst() {
		return first;
	}
	public void setFirst(FirstLevel first) {
		this.first = first;
	}
	public SecondLevel getSecond() {
		return second;
	}
	public void setSecond(SecondLevel second) {
		this.second = second;
	}
	public ThirdLevel getThird() {
		return third;
	}
	public void setThird(ThirdLevel third) {
		this.third = third;
	}
	public FourthLevel getFourth() {
		return fourth;
	}
	public void setFourth(FourthLevel fourth) {
		this.fourth = fourth;
	}
	
}
