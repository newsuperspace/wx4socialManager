package cc.natapp4.ddaig.domain;

import java.io.Serializable;

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
	private String uid; 
	// 外键关联
	private User user;    // 与User表一对一
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

	
	// ==========================SETTERs/GETTERs=============================

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	// 不设@JSON
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

	
	
}
