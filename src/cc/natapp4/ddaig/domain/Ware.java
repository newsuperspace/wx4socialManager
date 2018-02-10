package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * 用于积分兑换的商品的Bean
 * @author Administrator
 *
 */
public class Ware implements Serializable {
	// 商品id
	private String wid;
	// 商品名
	private String wname;
	// 商品价值（值多少积分）
	private String core;
	// 剩余商品数量
	private String surplus;
	// 累计兑换数
	private String total;
	// 存放商品qrcode的路径字符串
	private String qrcodeUrl;
	// 存放商品图片的路径字符串
	private String imageUrl;
	
	// Foreign-KEY
	// 与当前商品有关的全部兑换记录
	private Set<Exchange> exchanges;
	
	// ========================================构造器
	public Ware() {
		// 默认（空）构造器
	}

	// ======================================SETTER/GETTER
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
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
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getSurplus() {
		return surplus;
	}
	public void setSurplus(String surplus) {
		this.surplus = surplus;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Set<Exchange> getExchanges() {
		return exchanges;
	}
	public void setExchanges(Set<Exchange> exchanges) {
		this.exchanges = exchanges;
	}
	
	
}
