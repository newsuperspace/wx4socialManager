package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;

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
	 * ----------普通字段---------
	 */
	private String sid;  // 当前样本的主键ID
	private long date;  // 样本产生的时间的格里高利历偏移量 
	
	/**
	 * -----------样本体系外键-----------
	 */
	private List<FactorResult4Sample4EnclosedScale> factorResults;
	/**
	 *  -----------量表体系外键----------
	 */
	private EnclosedScale enclosedScale;   // 当前样本所属量表
	/**
	 * -----------其他体系外键-----------
	 */
	private User user;  				   // 当前样本所属用户
	
	// ================SETTER/GETTER==============
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public List<FactorResult4Sample4EnclosedScale> getFactorResults() {
		return factorResults;
	}
	public void setFactorResults(List<FactorResult4Sample4EnclosedScale> factorResults) {
		this.factorResults = factorResults;
	}
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

}
