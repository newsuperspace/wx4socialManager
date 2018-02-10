package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Activity implements Serializable {

	private String aid;				// 活动的id
	private String beginData;   // 活动的开始时间
	private String endData;		// 活动的借书时间
	private String score;				// 当前活动的积分
	private String qrcodeUrl;    // 活动扫码的二维码图片地址
	private String name;      // 活动名称
	private String description;     // 活动的描述
	private String state;  // 进行中，已完成、已取消
	private String author;   // 发起人（用户的uid）
	private String community;  // 活动所属社区（社区的cid）
	 
	private Set<User>  users;  // 参与该活动的用户列表

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getBeginData() {
		return beginData;
	}

	public void setBeginData(String beginData) {
		this.beginData = beginData;
	}

	public String getEndData() {
		return endData;
	}

	public void setEndData(String endData) {
		this.endData = endData;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(serialize=false)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
}
