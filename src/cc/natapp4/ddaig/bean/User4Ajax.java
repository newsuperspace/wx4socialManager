package cc.natapp4.ddaig.bean;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;

/**
 * 专供userAction的 getUserInfo()方法向前端返回用户数据
 * 通常用户前端展示用户信息、更新update等之用
 * 
 * 通过UserUtils.userToUser4Ajax() 方法可以实现User到user4AJax的批量数据拷贝
 * 
 * @author Administrator
 *
 */
public class User4Ajax implements Serializable {

	private String uid;  // 【主键】
	private String openid;  // 微信的openID值 ★
	private String cardid;  // 身份证号
	private String username;    // 真实名字
	private String sickname;  // 微信昵称
	private long registrationTime; // 注册日期(格里高利历毫秒值偏移量,存放到数据库之用)
	private String registrationTimeStr;  //  根据registrationTime转换成人类可辨识的yyyy-MM-dd HH:mm:ss (24小时制)的字符串，供给JSP页面显示之用，数据库中没有这个字段只是作为Struts模型驱动使用。
	private String address;      // 家庭住址
	private long serveTime;     // 累计服务时长（毫秒值，用它可以换算出用户等级）★
	private String email;    // 电子邮箱
	private int score;    // 积分  ★
	private String sex;      // 性别
	private int age;    // 年龄
	private String birth; // 出生年-月-日，形如: yyyy-MM-dd 
	private String phone;   // 电话号码
	private String qrcode;   // 用户专属qrcode的相对路径————qrcode/1/12/xxxx.gif
	private boolean ishere;   // 该用户当前是否在公众号中 
	private boolean locked;  // 是否被封禁 true=封禁  false或null = 正常
	
	// 对于当前层级来说，可以给该用户设置的所有member.grouping.tag的数据值
	private  String[]  tags;
	
	// 如果该用户在当前层级中不是管理员身份，则这里显示为空字符串，否则为对应的grouping.tag字符串（minus_first、zero、first、second、third、fourth）
	private String managerTag = "";
	// 该用户在当前管理者层级之下的memeber.grouping.tag
	private String memeberTag = "";
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSickname() {
		return sickname;
	}
	public void setSickname(String sickname) {
		this.sickname = sickname;
	}
	public long getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(long registrationTime) {
		this.registrationTime = registrationTime;
	}
	public String getRegistrationTimeStr() {
		return registrationTimeStr;
	}
	public void setRegistrationTimeStr(String registrationTimeStr) {
		this.registrationTimeStr = registrationTimeStr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getServeTime() {
		return serveTime;
	}
	public void setServeTime(long serveTime) {
		this.serveTime = serveTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getManagerTag() {
		return managerTag;
	}
	public void setManagerTag(String managerTag) {
		this.managerTag = managerTag;
	}
	public String getMemeberTag() {
		return memeberTag;
	}
	public void setMemeberTag(String memeberTag) {
		this.memeberTag = memeberTag;
	}
	
	

}
