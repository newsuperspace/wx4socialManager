package cc.natapp4.ddaig.json.returnMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
	
	// 如果当前操作者为Admin(controllerNum==10086)来说，系统中所有 街道层级对象都在这个容器里
	private List<MinusFirstLevel>  minusLevels;
	// 如果当前操作者为minus_first（controllerNum==-1），则这里应该存放的是该minus_first层级管理之下的所有zero层级
	private Set<ZeroLevel>  zeroLevels;
	// 如果当前操作者为zero（controllerNum==0），则这里应该存放的是该zero层级管理之下的所有first层级
	private Set<FirstLevel> firstLevels;
	// 如果当前操作者为first（controllerNum==1），则这里应该存放的是该first层级管理之下的所有second层级
	private Set<SecondLevel> secondLevels;
	// 如果当前操作者为Second（controllerNum==2），则这里应该存放的是该second层级管理之下的所有third层级
	private Set<ThirdLevel> thirdLevels;
	// 如果当前操作者为third（controllerNum==3），则这里应该存放的是该third层级管理之下的所有fourth层级
	private Set<FourthLevel> fourthLevels;
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
	public Set<ZeroLevel> getZeroLevels() {
		return zeroLevels;
	}
	public void setZeroLevels(Set<ZeroLevel> zeroLevels) {
		this.zeroLevels = zeroLevels;
	}
	public Set<FirstLevel> getFirstLevels() {
		return firstLevels;
	}
	public void setFirstLevels(Set<FirstLevel> firstLevels) {
		this.firstLevels = firstLevels;
	}
	public Set<SecondLevel> getSecondLevels() {
		return secondLevels;
	}
	public void setSecondLevels(Set<SecondLevel> secondLevels) {
		this.secondLevels = secondLevels;
	}
	public Set<ThirdLevel> getThirdLevels() {
		return thirdLevels;
	}
	public void setThirdLevels(Set<ThirdLevel> thirdLevels) {
		this.thirdLevels = thirdLevels;
	}
	public Set<FourthLevel> getFourthLevels() {
		return fourthLevels;
	}
	public void setFourthLevels(Set<FourthLevel> fourthLevels) {
		this.fourthLevels = fourthLevels;
	}
	public List<MinusFirstLevel> getMinusLevels() {
		return minusLevels;
	}
	public void setMinusLevels(List<MinusFirstLevel> minusLevels) {
		this.minusLevels = minusLevels;
	}

	
	
}
