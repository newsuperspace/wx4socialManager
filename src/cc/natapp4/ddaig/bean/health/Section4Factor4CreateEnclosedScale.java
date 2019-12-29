package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;

public class Section4Factor4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

//	 "order": 1,
//     "sid": "djofsodf793u29jfdlsf",
//     "name": "正常",
//     "description": "正常状态",
//     "minScore": 1,
//     "maxScore": 4,
//     "diagnosis": "您的状态非常正常！"
	
	private int order;
	private String sid;
	private String name;
	private String description;
	private int minScore;
	private int maxScore;
	private String diagnosis;
	
	public Section4Factor4CreateEnclosedScale() {
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public String toString() {
		return "Section4Factor4CreateEnclosedScale [order=" + order + ", sid=" + sid + ", name=" + name
				+ ", description=" + description + ", minScore=" + minScore + ", maxScore=" + maxScore + ", diagnosis="
				+ diagnosis + "]";
	}
	
	
	
}
