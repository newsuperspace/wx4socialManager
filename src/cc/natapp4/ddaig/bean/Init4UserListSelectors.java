package cc.natapp4.ddaig.bean;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

/**
 * 
 * 当userList.jsp页面加载成功后，基于$(functon(){//...}); 实现一些初始化操作
 * 通过AJAX请求 userAction.initSelector() 方法，获取当前操作者层级对象
 * 将操作者层级的tag（admin、minusFirst、zero、first、second、third、fourth）以及操作者层级对象自身
 * 和当前操作者层级的直接子层级（不包括子孙层级）放入到本bean的对应属性位置上，返回给前端页面
 * 用来组织userList.jsp页面上，用来筛选用户的selectors操作面板（panel）
 * 
 * 
 * @author Administrator
 *
 */
public class Init4UserListSelectors implements Serializable {

	/**
	 * 	版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 当前操作者层级的tag标签，可选值为 admin、minusFirst、zero、first、second、third、fourth
	 * 用于userList.jsp前端判定如何初始化selector，例如：
	 * 当前操作者层级是一个FirstLevel，则此处tag设置为“first”,之后userList.jsp前端就基于此设置first及以上(id=first、zero、minusFirst的selctor)
	 * 的selector隐藏（设置hidden属性）并且在id=first的selector中通过jQuery.append()方法扩展一个选项代表当前操作者层级的option
	 * （value属性来自于本bean的firstLevel.id，text值来自于本bean的firstLevel.name）并设置selected属性，作为默认选中的option
	 * 
	 * 随后开始组织子selector的option，也就是id=second的selector,从本bean的secondLevels容器中可以获取所有option的必要数据
	 * （value、text）用以组织显示出来。
	 */
	String tag = "";
	
	/*
	 * 存放当前层级，其他属性请保持null
	 * 例如，如果当前层级是一个FIrstLevel,则将该层级对象的持久化状态对象（基于Hibernate的数据库访问技术返回的当前操作层级的对象）
	 * 其他属性保持null即可
	 */
	MinusFirstLevel  minusFirstLevel;
	ZeroLevel	zeroLevel;
	FirstLevel firstLevel;
	SecondLevel secondLevel;
	ThirdLevel thirdLevel;
	FourthLevel fourthLevel;
	
	/*
	 * 下面这组属性存放的是 当前操作者层级的“直接子层级”的所有层级对象数据信息的容器，其他无关容器保持null，例如：
	 * 当前操作者层级为一个FirstLevel对象，则在下面的secondLevels中保存着当前操作者层级的全部直接子层级对象数据。 
	 */
	List<MinusFirstLevel> minusFirstLevels;
	List<ZeroLevel> zeroLevels;
	List<FirstLevel> firstLevels;
	List<SecondLevel> secondLevels;
	List<ThirdLevel> thirdLevels;
	List<FourthLevel> fourthLevels;
	
	
	//======================SETTER/GETTER=====================
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public MinusFirstLevel getMinusFirstLevel() {
		return minusFirstLevel;
	}
	public void setMinusFirstLevel(MinusFirstLevel minusFirstLevel) {
		this.minusFirstLevel = minusFirstLevel;
	}
	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}
	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}
	public FirstLevel getFirstLevel() {
		return firstLevel;
	}
	public void setFirstLevel(FirstLevel firstLevel) {
		this.firstLevel = firstLevel;
	}
	public SecondLevel getSecondLevel() {
		return secondLevel;
	}
	public void setSecondLevel(SecondLevel secondLevel) {
		this.secondLevel = secondLevel;
	}
	public ThirdLevel getThirdLevel() {
		return thirdLevel;
	}
	public void setThirdLevel(ThirdLevel thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	public FourthLevel getFourthLevel() {
		return fourthLevel;
	}
	public void setFourthLevel(FourthLevel fourthLevel) {
		this.fourthLevel = fourthLevel;
	}
	
	public List<MinusFirstLevel> getMinusFirstLevels() {
		return minusFirstLevels;
	}
	public void setMinusFirstLevels(List<MinusFirstLevel> minusFirstLevels) {
		this.minusFirstLevels = minusFirstLevels;
	}
	public List<ZeroLevel> getZeroLevels() {
		return zeroLevels;
	}
	public void setZeroLevels(List<ZeroLevel> zeroLevels) {
		this.zeroLevels = zeroLevels;
	}
	public List<FirstLevel> getFirstLevels() {
		return firstLevels;
	}
	public void setFirstLevels(List<FirstLevel> firstLevels) {
		this.firstLevels = firstLevels;
	}
	public List<SecondLevel> getSecondLevels() {
		return secondLevels;
	}
	public void setSecondLevels(List<SecondLevel> secondLevels) {
		this.secondLevels = secondLevels;
	}
	public List<ThirdLevel> getThirdLevels() {
		return thirdLevels;
	}
	public void setThirdLevels(List<ThirdLevel> thirdLevels) {
		this.thirdLevels = thirdLevels;
	}
	public List<FourthLevel> getFourthLevels() {
		return fourthLevels;
	}
	public void setFourthLevels(List<FourthLevel> fourthLevels) {
		this.fourthLevels = fourthLevels;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
