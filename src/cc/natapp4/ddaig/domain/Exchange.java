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
	private String exchangeData;
	// 当时兑换所扣除的积分（因为兑换积分可能会调整，这里保留历史兑换积分的分值）
	private String score;
	
	// Foreign-Key
	// 当前兑换记录所属的用户
	private User user;
	// 当前记录是哪一个商品
	private Ware ware;
	
	// ============================SETTERS/GETTERS
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getExchangeData() {
		return exchangeData;
	}
	public void setExchangeData(String exchangeData) {
		this.exchangeData = exchangeData;
	}
	// 防止JSON结果集处理生JSON的时候，又从这里重新获取User类重新扫描，结果陷入死循环
	@JSON(serialize=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	// 前端通过ajax获取User或Ware信息的时候会通过json形式返回搜索结果，这个注解就是防止重新扫描User或Ware陷入死循环而设置的
	@JSON(serialize=false)
	public Ware getWare() {
		return ware;
	}
	public void setWare(Ware ware) {
		this.ware = ware;
	}
	
}
