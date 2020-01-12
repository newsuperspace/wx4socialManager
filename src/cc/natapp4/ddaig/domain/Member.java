package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

/**
 * 加入公众号平台的用户一定属于层级化管理结构中的某一位置 这个数据库表就是用来描述用户在层级化结构中的位置的。
 * 
 * @author Administrator
 *
 */
public class Member implements Serializable {

	// 主键
	private String memberid;
	/*
	 * 以下就是用来描述该用户作为层级化结构中的成员 其所在的具体位置的。 假设该用户属于third层级的成员，但不属于具体的fourth层级，那么
	 * 他的fourthLevel = null 而thirdLevel、secondLevel、firstLevle和
	 * minusFirstLevel、zeroLevel一定都是有值得，从而可以索引到其所归属 的具体层级对象。
	 */
	private MinusFirstLevel minusFirstLevel;
	private ZeroLevel zeroLevel;
	private FirstLevel firstLevel;
	private SecondLevel secondLevel;
	private ThirdLevel thirdLevel;
	private FourthLevel fourthLevel;
	// ---------------------------------------Foreign-KEY-------------------------------------
	private Grouping grouping; // 所在分组（与微信的tag标签一一对应）
	// 外键关联
	private User user;
	/*
	 * 每个用户可以挂靠在多个层级对象之下，因此可以拥有多个Memer
	 * 而作为一个层级对象的成员，每个member又可以担任多个同级别子层级的管理者，
	 * 于是每个member又拥有多个manager。
	 * 
	 * 可以说如果一个人要想成为某个层级的管理员，首先他必须加入到目标掌管层级的父层级成为该父层级
	 * 的成员member，然后他才有资格掌管目标层级从而产生对应的manager
	 */
	private  List<Manager> managers;

	
	// ---------------------------------------非数据库字段-------------------------------------
	
	// ==========================SETTERs/GETTERs=============================

	@JSON(serialize=false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 不设@JSON
	public MinusFirstLevel getMinusFirstLevel() {
		return minusFirstLevel;
	}

	public void setMinusFirstLevel(MinusFirstLevel minusFirstLevel) {
		this.minusFirstLevel = minusFirstLevel;
	}

	// 不设@JSON
	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}

	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}

	// 不设@JSON
	public FirstLevel getFirstLevel() {
		return firstLevel;
	}

	public void setFirstLevel(FirstLevel firstLevel) {
		this.firstLevel = firstLevel;
	}

	// 不设@JSON
	public SecondLevel getSecondLevel() {
		return secondLevel;
	}

	public void setSecondLevel(SecondLevel secondLevel) {
		this.secondLevel = secondLevel;
	}

	// 不设@JSON
	public ThirdLevel getThirdLevel() {
		return thirdLevel;
	}

	public void setThirdLevel(ThirdLevel thirdLevel) {
		this.thirdLevel = thirdLevel;
	}

	// 不设@JSON
	public FourthLevel getFourthLevel() {
		return fourthLevel;
	}

	public void setFourthLevel(FourthLevel fourthLevel) {
		this.fourthLevel = fourthLevel;
	}
	public Grouping getGrouping() {
		return grouping;
	}

	public void setGrouping(Grouping grouping) {
		this.grouping = grouping;
	}

	public List<Manager> getManagers() {
		return managers;
	}
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	public String getMemberid() {
		return memberid;
	}
	
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	
	// ====================业务逻辑方法=====================

	/*
	 * 
	 * ★★★
	 * 该属性内容为grouping.tag相同，用来供给通过Member获取用户作为对应层级的member时，该层级的级别
	 * 包括minus_first    zero   first   second   third   fourth
	 * 如果是platform，说明是扫描微信二维码进入的公众号，该member作为基础默认member（毕竟所有人最根本的就是公众号的成员，然后
	 * 才是其他层级的成员，并且该默认member记录着该user是否已经实名认证，是唯一可以设置unreal标签的member）
	 * 
	 * 用来确定本member直属层级
	 * 可以从
	 * private MinusFirstLevel minusFirstLevel;
		private ZeroLevel zeroLevel;
		private FirstLevel firstLevel;
		private SecondLevel secondLevel;
		private ThirdLevel thirdLevel;
		private FourthLevel fourthLevel;
		快速定位到直属的层级对象
	 */
	private  String directLevel;
	/**
	 * 用作直接获取当前member对应的层级对象的grouping.tag标签
	 * 如果返回的是platform，说明该member所代表的是User默认加入公众号的，默认member
	 * 从member.grouping.tag 是否等于unreal可以知道该用户是否已经完成实名认证了。
	 * @return
	 */
	public String getDirectLevel() {
		if(null != this.directLevel){
			return directLevel;
		}
		
		if(null==minusFirstLevel){
			this.directLevel = "platform";
		}else if(null==zeroLevel){
			this.directLevel = "minus_first";
		}else if(null==firstLevel){
			this.directLevel = "zero";
		}else if(null==secondLevel){
			this.directLevel = "first";
		}else if(null == thirdLevel){
			this.directLevel = "second";
		}else if(null == fourthLevel){
			this.directLevel = "third";
		}else{
			this.directLevel = "fourth";
		}
		return this.directLevel;
	}
	
	
}
