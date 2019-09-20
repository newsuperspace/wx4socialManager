package cc.natapp4.ddaig.domain;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

/**
 * 用来记录兑换积分的历史记录
 * @author Administrator
 *
 */
public class Exchange implements Serializable {

	// 当前记录的ID
	private String exid;
	// 兑换日期 yyyy-MM-dd hh:mm:ss  这一次要精确到秒
	private String exchangeDate;
	// 兑换商品时所扣除的积分（因为兑换积分可能会调整，这里保留历史兑换积分的分值）
	private int score;
	
	// Foreign-Key
	// 当前兑换记录所属的用户
	private User user;
	// 当前记录是哪一个商品
	private Ware ware;
	
	// 电子签领图片的Base64编码字符串
	private String base64str4autograph;
	
	
	// ============================SETTERS/GETTERS
	public String getBase64str4autograph() {
		return base64str4autograph;
	}
	public void setBase64str4autograph(String base64str4autograph) {
		this.base64str4autograph = base64str4autograph;
	}
	public String getExid() {
		return exid;
	}
	public void setExid(String exid) {
		this.exid = exid;
	}
	public String getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Ware getWare() {
		return ware;
	}
	public void setWare(Ware ware) {
		this.ware = ware;
	}
	
	
	
}
