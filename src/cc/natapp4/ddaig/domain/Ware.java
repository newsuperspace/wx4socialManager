package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.utils.DateTimeUtils;

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
	// 商品描述
	private String description;
	// 创建日期
	private long createDate;
	// 当前是否可用？
	private boolean canUse = true;
	// 商品价值（值多少积分）
	private Integer score;
	// 剩余商品数量（可随着现实采购而增加，减少是系统兑换自动完成的）
	private Integer surplus;
	// 累计兑换数
	private Integer total;
	// 存放商品qrcode兑换码图片的base64编码字符串
	private String base64str4qrcode;
	// 存放商品图片的base64编码字符串
	private String base64str4image1;
	private String base64str4image2;
	private String base64str4image3;
	
	// Foreign-KEY
	// 与当前商品有关的全部兑换记录
	private List<Exchange> exchanges;
	// 当前积分兑换奖品是被那个社区创建的？
	private ZeroLevel zeroLevel;
	
	// ======================================非数据库属性——用于前端展视
	private String str4CreateDate;
	public String getStr4CreateDate() {
		return DateTimeUtils.getSimpleDateTimeFormatString(getCreateDate());
	}

	// ========================================构造器
	public Ware() {
		// 默认（空）构造器
	}

	// ======================================SETTER/GETTER
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Exchange> getExchanges() {
		return exchanges;
	}

	public void setExchanges(List<Exchange> exchanges) {
		this.exchanges = exchanges;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public boolean isCanUse() {
		return canUse;
	}

	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}

	public String getBase64str4image1() {
		return base64str4image1;
	}

	public void setBase64str4image1(String base64str4image1) {
		this.base64str4image1 = base64str4image1;
	}

	public String getBase64str4image2() {
		return base64str4image2;
	}

	public void setBase64str4image2(String base64str4image2) {
		this.base64str4image2 = base64str4image2;
	}

	public String getBase64str4image3() {
		return base64str4image3;
	}

	public void setBase64str4image3(String base64str4image3) {
		this.base64str4image3 = base64str4image3;
	}

	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}

	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}
	
	
	
	
}
