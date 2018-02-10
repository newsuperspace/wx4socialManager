package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;


public class User implements Serializable {
	
	private String openid;  // 微信的openID值 ★
	private String cardid;  // 身份证号
	private String uid;
	private String username;    // 名字
	private String community;   // 服务社区
	private String address;      // 家庭住址
	private String level;     // 等级
	private String email;    // 电子邮箱
	private String score;    // 积分  ★
	private String sex;      // 性别
	private String age;    // 年龄
	private String phone;   // 电话号码
	private boolean ishere;   // 该用户当前是否在公众号中 
	private String qrcode;   // 用户专属qrcode的相对路径————qrcode/1/12/xxxx.gif
	private boolean locked;  // 是否被封禁 true=封禁  false或null = 正常
	
	// Foreign-KEY
	private Grouping grouping;     // 所在分组（与微信的tag标签一一对应，每个tag对应一种前端的菜单样式和后端的管理层级★★★）
	private Set<Activity>  activities;  // 当前用户所参加过的活动列表
	private Set<Exchange> exchanges; // 当前用户的消分记录
	
	// 【作为管理者时使用】
	// 一个人可以根据其tag来区分其职能，除去common，其他类型管理者都会对应一下一种层级
	// 且每个人可以拥有多个相同层级，因此是一对多的关系
	private Set<FirstLevel>  firstLevels;   // 当grouping.tag=first，对应最高层级
	private Set<SecondLevel>  secondLevels;  // 当grouping.tag=second，对应次一级管理层级
	private Set<ThirdLevel>  thirdLevels;   // 当grouping.tag=third，对应总队层级
	private Set<FourthLevel>  fourthLevels;   // 当grouping.tag=fourth，对应支队层级
	
	// 【作为普通成员时使用】
	// 当当前用户的grouping.tag = common 时要查看T是属于哪一个层级对象的。
	private FirstLevel  belongedFirstLevel;
	private SecondLevel belongedSecondLevel;
	private ThirdLevel belongedThirdLevel;
	private FourthLevel belongedFourthLevel;
	
	
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
	public boolean isLocked() {
		if(!locked){
			return false;
		}
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public Set<Exchange> getExchanges() {
		return exchanges;
	}
	public void setExchanges(Set<Exchange> exchanges) {
		this.exchanges = exchanges;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public boolean isIshere() {
		return ishere;
	}
	public void setIshere(boolean ishere) {
		this.ishere = ishere;
	}
//	@JSON(serialize=false)
	public Set<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
//	@JSON(serialize=false)
	public Grouping getGrouping() {
		return grouping;
	}
	public void setGrouping(Grouping group) {
		this.grouping = group;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public User() {
	}
	
}
