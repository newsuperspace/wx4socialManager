package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;


public class General4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String esid;
	private String chName;
	private String engName;
	private String introduce;
	private String description;
	private float weigh;
	
	
	public General4CreateEnclosedScale() {
	}
	
	public String getEsid() {
		return esid;
	}
	public void setEsid(String esid) {
		this.esid = esid;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getWeigh() {
		return weigh;
	}
	public void setWeigh(float weigh) {
		this.weigh = weigh;
	}

	@Override
	public String toString() {
		return "General4CreateEnclosedScale [esid=" + esid + ", chName=" + chName + ", engName=" + engName
				+ ", introduce=" + introduce + ", description=" + description + ", weigh=" + weigh + "]";
	}
	
	
}
