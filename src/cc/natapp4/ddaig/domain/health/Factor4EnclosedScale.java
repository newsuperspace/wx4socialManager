package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;

/**
 * 量表 → 因子
 * @author Administrator
 *
 */
public class Factor4EnclosedScale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据库字段
	 */
	private int ord;  // 该因子创建时在量表中的序数（从1开始），由于order是SQL保留字因此使用缩写
	private long stamp; // 创建因子的时间戳
	private String fid; // 因子的主键ID
	private String name;  // 因子名
	private String keyword;   // 因子关键词
	private String description;   // 因子功能描述
	
	/**
	 * 量表体系外键
	 */
	private EnclosedScale enclosedScale;					// 当前因子所属量表
	private List<Section4Factor4EnclosedScale> sections;   // 当前因子所包含计分单元
	private List<Topic4EnclosedScale> topics;				// 当前因子所包含题目
	/**
	 * 样本体系外键
	 */
	private List<FactorResult4Sample4EnclosedScale> factorResults;
	
	
	// ===========================GETTER/SETTER=========================
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public long getStamp() {
		return stamp;
	}
	public void setStamp(long stamp) {
		this.stamp = stamp;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EnclosedScale getEnclosedScale() {
		return enclosedScale;
	}
	public void setEnclosedScale(EnclosedScale enclosedScale) {
		this.enclosedScale = enclosedScale;
	}
	public List<Section4Factor4EnclosedScale> getSections() {
		return sections;
	}
	public void setSections(List<Section4Factor4EnclosedScale> sections) {
		this.sections = sections;
	}
	public List<Topic4EnclosedScale> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic4EnclosedScale> topics) {
		this.topics = topics;
	}
	public List<FactorResult4Sample4EnclosedScale> getFactorResults() {
		return factorResults;
	}
	public void setFactorResults(List<FactorResult4Sample4EnclosedScale> factorResults) {
		this.factorResults = factorResults;
	}
	
}
