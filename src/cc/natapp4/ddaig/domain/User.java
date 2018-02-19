package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;



public class User implements Serializable {
	
	//====================================字段==================================
	// ---------------------------------------普通字段-------------------------------------
	private String openid;  // 微信的openID值 ★
	private String cardid;  // 身份证号
	private String uid;  // 【主键】
	private String username;    // 名字
	private String address;      // 家庭住址
	private long serveTime;     // 服务时长（毫秒值，用它可以换算出用户等级）★
	private String email;    // 电子邮箱
	private int score;    // 积分  ★
	private String sex;      // 性别
	private int age;    // 年龄
	private String phone;   // 电话号码
	private boolean ishere;   // 该用户当前是否在公众号中 
	private String qrcode;   // 用户专属qrcode的相对路径————qrcode/1/12/xxxx.gif
	private boolean locked;  // 是否被封禁 true=封禁  false或null = 正常
	// ---------------------------------------Foreign-KEY-------------------------------------
	private Grouping grouping;     // 所在分组（与微信的tag标签一一对应，每个tag对应一种前端的菜单样式和后端的管理层级★★★）
	private Set<Activity>  activities;  // 当前用户所参加过的活动列表
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

	/*
	 * 由于前端管理后台在查找某一用户的时候不需要服务器连带返回用户的参与活动信息，
	 * 因此这里添加上JSON注解来提高JSON组织效率。同时，如果前端真的需要所要某一
	 * 用户的活动记录的时候通过ajax连接再将该Set直接放入到栈顶后JSON解析传递过去也是来得及的。
	 */
	@JSON(serialize=false)
	public Set<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
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
	
}
