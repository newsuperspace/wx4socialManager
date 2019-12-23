package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;



/**
 * 量表 → 因子 → 计分单元
 * @author Administrator
 *
 */
public class Section4Factor4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据库字段
	 */
	private int order;			// 创建时的序数（从1开始）
	private String sid;			// 主键ID
	private String name;		// 计分单元名称
	private String description;	// 功能描述（给专业测评人员看）
	private float minScore;		// 最小得分（包含）
	private float maxScore; 	// 最大得分（包含）
	private String diagnosis;   // 诊断用语

	
	/**
	 * 量表体系外键字段
	 */
	private Factor4EnclosedScale factor;   // 当前计分单元所属因子


	// =========================GETTER/SETTER===========================
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


	public float getMinScore() {
		return minScore;
	}


	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}


	public float getMaxScore() {
		return maxScore;
	}


	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}


	public String getDiagnosis() {
		return diagnosis;
	}


	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}


	public Factor4EnclosedScale getFactor() {
		return factor;
	}


	public void setFactor(Factor4EnclosedScale factor) {
		this.factor = factor;
	}

	
	
	
}
