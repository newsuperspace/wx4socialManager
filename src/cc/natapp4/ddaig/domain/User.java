package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.health.Sample4EnclosedScale;

public class User implements Serializable {
	
	
	//====================================字段==================================
	// ---------------------------------------参与前端显示的字段-------------------------------------
	private int sampleNum;  // 该用户包含的样本量，供给health/users.jsp在列表中显示数据之用，不参与数据库范畴
	private Member member; // 用于在managerList.jsp上显示用户对应当前操作者层级的member对象，从而方便获取memberid和managers等数据
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	// ---------------------------------------数据库字段-------------------------------------
	private String uid;  // 【主键】

	private String openid;  // 微信的openID值 ★
	private String cardid;  // 身份证号
	private String username;    // 真实名字
	private String password = "123";   // 保存密码,默认密码123
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
	

	private List<Visitor> visits; // 该用户个人参加活动的信息列表
//	private Set<Exchange> exchanges; // 当前用户的消分记录
	private List<Exchange> exchanges; // 当前用户的消分记录
	// 用户申请加入某些层级组织时提交的申请数据对象
	private List<UserApply4JoinLevel> userApply4JoinLevels;
	
	private List<Sample4EnclosedScale> samples4EnclosedScale;  // 当前用户所做过的所有封闭式问卷的样本数据
	
	/*
	 * 作为一个通过扫码（可能是公众号二维码也可能是各个层级对象的带参二维码——直接加入到该层级对象的管辖下）
	 * 加入公众号的用户势必会存在一个member对应数据，用来定位其在层级化结构中的位置
	 */
	private List<Member> members;
	
	//=================================构造器================================
	/**
	 * 空（默认）构造器
	 */
	public User() {}
	//==============================SETTERs/GETTERs============================
	
	public String getSickname() {
		return sickname;
	}
	public void setSickname(String sickname) {
		this.sickname = sickname;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public long getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(long registrationTime) {
		this.registrationTime = registrationTime;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isIshere() {
		return ishere;
	}
	public void setIshere(boolean ishere) {
		this.ishere = ishere;
	}

	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * 因为前端在获取用户数据后会经常需要进一步得到用户的类别（tag标签等）
	 * 因此grouping必须时常跟随user数据发送到前端，因此这里不应该加@JSON注解
	 * 因此，为了防止在struts-json-plugin.jar插件扫描栈顶上的user或List<User>来组织JSON
	 * 格式字符串时出现“死循环”，所以需要在对方表Grouping的getUsers()方法上添加
	 * @JSON注解，来从“对面”阻断JSON扫描和解析。
	 */
	@JSON(serialize=false)
	public List<Visitor> getVisits() {
		return visits;
	}
	public void setVisits(List<Visitor> visits) {
		this.visits = visits;
	}
	
	
	/*
	 * ★★★★
	 * 该注解的意思是当struts的一个名叫struts-json.jar的插件在解析值栈栈顶的一个对象
	 * 为符合JSON格式的字符串并写入到SerlvetHttpResponse的正文中，用于向前端通过Ajax
	 * 来访的请求回复JSON数据的时候，由于对象与对象之间存在相互引用的现象，而JSON解析
	 * 是看不出这种相互相互引用的，这样就会造成JSON解析时的死循环。
	 * 
	 * 为了防止出现死循环，我们通常在从A→B或者从B→A的其中一个对象的引用的GETTER上添加
	 * @JSON(serialize=false)注解，这个注解的意思就是告诉JSON解析器，不用深入解析该GETTER
	 * 所引用的对象了。
	 * 
	 * 具体当前情况来说就是，由于从前端通过Ajax请求User对象的数据时，通常不会需要该对象的从表数据
	 * 因此我们也就无需将从表数据写入到JSON中，而只需要把User的主表数据解析到JSON应该就能应对多数
	 * 业务情况，如果说前端真的需要该User的有关兑换记录的所有数据，那么我们完全可以将包含有该用户的
	 * 所有兑换数据的List容器放入到栈顶供给JSON解析即可，从而既避免出现死循环，又能满足特殊的业务需要。
	 */
	@JSON(serialize=false)
	public List<Exchange> getExchanges() {
		return exchanges;
	}
	public void setExchanges(List<Exchange> exchanges) {
		this.exchanges = exchanges;
	}

	/*
	 * 这里设置JSON的理由同上
	 */
	@JSON(serialize=false)
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}


	public String getRegistrationTimeStr() {
		return registrationTimeStr;
	}
	public void setRegistrationTimeStr(String registrationTimeStr) {
		this.registrationTimeStr = registrationTimeStr;
	}

	/**
	 * 这是设置JSON理由同上
	 * @return
	 */
	@JSON(serialize=false)
	public List<UserApply4JoinLevel> getUserApply4JoinLevels() {
		return userApply4JoinLevels;
	}
	public void setUserApply4JoinLevels(List<UserApply4JoinLevel> userApply4JoinLevels) {
		this.userApply4JoinLevels = userApply4JoinLevels;
	}

	/**
	 * 
	 * @return
	 */
	@JSON(serialize=false)
	public List<Sample4EnclosedScale> getSamples4EnclosedScale() {
		return samples4EnclosedScale;
	}
	public void setSamples4EnclosedScale(List<Sample4EnclosedScale> samples4EnclosedScale) {
		this.samples4EnclosedScale = samples4EnclosedScale;
	}

	
	
	
	public int getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(int sampleNum) {
		this.sampleNum = sampleNum;
	}
	
	
	
}
