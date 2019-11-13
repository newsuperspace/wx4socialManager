package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.User;


/**
 * 单个封闭式量表（EnclosedScale）的样本
 * @author Administrator
 *
 */
public class Sample4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ===============数据库相关字段=============
	 */
	/*
	 *  -----------外键----------
	 */
	private EnclosedScale enclosedScale;   // 当前样本所属量表
	private User user;  // 当前样本所属用户
	/*
	 * ----------普通字段--------
	 */
	private String seid;  // 当前样本的主键ID
	private String resultStr;  // 形如：“0、1、0、3、1、0、2、1、3”这样的字符串数据，resultStr.split("、")分解后的数字就是Option的order序号
	private long date;  // 样本产生的时间的格里高利历偏移量 
	private int oscore;   // 量表原始分
	private int sscore; // 量表标准分
	private float weigh; // 样本产生时的实时权重
	
	@JSON(serialize=false)
	public EnclosedScale getEnclosedScale() {
		return enclosedScale;
	}
	public void setEnclosedScale(EnclosedScale enclosedScale) {
		this.enclosedScale = enclosedScale;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSeid() {
		return seid;
	}
	public void setSeid(String seid) {
		this.seid = seid;
	}
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getOscore() {
		return oscore;
	}
	public void setOscore(int oscore) {
		this.oscore = oscore;
	}
	public int getSscore() {
		return sscore;
	}
	public void setSscore(int sscore) {
		this.sscore = sscore;
	}
	public float getWeigh() {
		return weigh;
	}
	public void setWeigh(float weigh) {
		this.weigh = weigh;
	}
	
	
	

}
