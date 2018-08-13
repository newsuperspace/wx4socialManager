package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class User implements Serializable {
	
	//=====================通过Ajax向前端返回数据信息时使用属性============================
	// 一下两个属性是在userAction.getUserInfo()中用来给前端myJS.userModal.op.userInfo()返回信息之用
	private Member member4Ajax;
	private Manager manager4Ajax;
	
	public Member getMember4Ajax() {
		return member4Ajax;
	}
	public void setMember4Ajax(Member member4Ajax) {
		this.member4Ajax = member4Ajax;
	}
	public Manager getManager4Ajax() {
		return manager4Ajax;
	}
	public void setManager4Ajax(Manager manager4Ajax) {
		this.manager4Ajax = manager4Ajax;
	}
	// 以下属性用于在userList.jsp - showUpdateUserModal中展示被修改用户可选用的tag（取决于操作者的等级，等级越高可用的tag越多）
	private String[] tags; 
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	//====================================字段==================================
	// ---------------------------------------普通字段-------------------------------------
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
	// ---------------------------------------Foreign-KEY-------------------------------------
	private Grouping grouping;     // 所在分组（与微信的tag标签一一对应）

	private List<Visitor> visits; // 该用户个人参加活动的信息列表
	private Set<Exchange> exchanges; // 当前用户的消分记录
	
	
	/*
	 *  我们通过“共用主键”的一对一映射配置实现了
	 *  Manager/User/member使用相同主键的设计
	 */
	/*
	 * 如果grouping.tag == minus_first/zero/first/second/third/fourth中的任何一个，
	 * 则可在此处找到与身为管理员的当前用户有关的数据信息
	 */
	private Manager manager;   
	/*
	 * 作为一个通过扫码（可能是公众号二维码也可能是各个层级对象的带参二维码——直接加入到该层级对象的管辖下）
	 * 加入公众号的用户势必会存在一个member对应数据，用来定位其在层级化结构中的位置
	 */
	private Member  member;  
	
	
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

	/*
	 * 因为前端在获取用户数据后会经常需要进一步得到用户的类别（tag标签等）
	 * 因此grouping必须时常跟随user数据发送到前端，因此这里不应该加@JSON注解
	 * 因此，为了防止在struts-json-plugin.jar插件扫描栈顶上的user或List<User>来组织JSON
	 * 格式字符串时出现“死循环”，所以需要在对方表Grouping的getUsers()方法上添加
	 * @JSON注解，来从“对面”阻断JSON扫描和解析。
	 */
	public Grouping getGrouping() {
		return grouping;
	}
	public void setGrouping(Grouping grouping) {
		this.grouping = grouping;
	}

	@JSON(serialize=false)
	public List<Visitor> getVisits() {
		return visits;
	}
	public void setVisits(List<Visitor> visits) {
		this.visits = visits;
	}
	
	@JSON(serialize=false)
	public Set<Exchange> getExchanges() {
		return exchanges;
	}
	public void setExchanges(Set<Exchange> exchanges) {
		this.exchanges = exchanges;
	}

	/*
	 * 这里阻断从User了解其manager信息是因为，从后端业务考虑，通常我们是在管理者列表中
	 * 想要详细了解某个管理员的详细信息，因此manager需要携带一些user信息，但是如果我们从
	 * user用户列表中想要了解某一用户身为管理者的详细内容（比如他管理的是哪些团体）大可
	 * 直接向manager对象放到栈顶后组织成JSON字符串发送过去也来得及。
	 * 
	 * 在这更重要的是防止JSON解析时出现死循环，毕竟manager中也有user字段，user中又有
	 * manager字段，你中有我我中有你，如此反复解析何时是头？
	 */
	@JSON(serialize=false)
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/*
	 * 这里设置JSON的理由同上
	 */
	@JSON(serialize=false)
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}



	public String getRegistrationTimeStr() {
		return registrationTimeStr;
	}



	public void setRegistrationTimeStr(String registrationTimeStr) {
		this.registrationTimeStr = registrationTimeStr;
	}
	
}
