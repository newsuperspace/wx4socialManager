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
	private  String[]  tags;
	
	/*
	 *  在任命和修改工作中该用户必定是操作者所管理层级的直辖用户
	 *  这个member就是记录直属用户的，当前端获取到的用户是当前
	 *  操作执行者层级的直辖人员是，就会在member属性中记录下该成员对象
	 *  从中可以方便直接获取到其grouping.tag，用于修改Modal和任命Modal
	 *  上的必要显示（修改）和判断（任命）之用。
	 */
	private Member  member;
	/**
	 * 在查看信息操作中，由于一个用户可以同时存在于当前（查阅）操作者
	 * 所管理层级之下任何一个子孙层级对象中（包括直接子层级和孙辈层级），这里存放的
	 * 就是被查看信息者，所有置于当前操作者管辖范围内的子孙层级对象的成员信息
	 */
	private  List<Member> members;	
	/*、
	 * 在任命工作中该用户必定是操作者所管理层级的直辖用户，
	 * 由于一个用户可以同时掌管当前操作者之下的多个直接子层级，
	 * 因此这里存放着所有已被该用户掌管的直接子层级的管理者对象
	 * 
	 * 由于任命工作中层级管理者只能任命直接子层级，不能隔代任命，因此用户是否在其子孙层级中有任职
	 * 对于当前操作者来说无关紧要，因此这里的managers中只存放当前操作者所直辖的管理员信息。
	 */
	private List<Manager>  managers;
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
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public List<Manager> getManagers() {
		return managers;
	}
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}
	

}
