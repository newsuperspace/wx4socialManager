package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 用于积分兑换的兑换品的Bean
 * @author Administrator
 *
 */
public class Ware implements Serializable {
	// 商品id
	private String wid;
	// 商品名
	private String wname;
	// 商品价值（值多少积分）
	private Integer score;
	// 剩余商品数量（可随着现实采购而增加，减少是系统兑换自动完成的）
	private Integer surplus;
	// 累计兑换数
	private Integer total;
	// 存放商品qrcode兑换码图片的base64编码字符串
	private String base64str4qrcode;
	// 存放商品图片的base64编码字符串
	private String base64str4image;
	
	// Foreign-KEY
	// 与当前商品有关的全部兑换记录
	private List<Exchange> exchanges;
	
	// ========================================构造器
	public Ware() {
		// 默认（空）构造器
	}

	// ======================================SETTER/GETTER
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getSurplus() {
		return surplus;
	}

	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getBase64str4qrcode() {
		return base64str4qrcode;
	}

	public void setBase64str4qrcode(String base64str4qrcode) {
		this.base64str4qrcode = base64str4qrcode;
	}

	public String getBase64str4image() {
		return base64str4image;
	}

	public void setBase64str4image(String base64str4image) {
		this.base64str4image = base64str4image;
	}

	public List<Exchange> getExchanges() {
		return exchanges;
	}

	public void setExchanges(List<Exchange> exchanges) {
		this.exchanges = exchanges;
	}

	
	
}
