package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;


/**
 * 封闭问卷（EnclosedScale）中的单个题目
 * @author Administrator
 *
 */
public class Topic4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  数据库字段
	 */
	private String tid;  // 主键ID
	private String description;   // 形如：“我觉得比平时容易紧张和着急”的问题内容
	private int ord;				// 从1开始的题目创建序数（整体序数而非在因子中的序数），本应使用order，但作为SQL保留字不得不缩减使用
	private String keyword;			// 当前问题的关键词，例如“焦虑”，非必填
	/**
	 *  量表体系外键
	 */
	private Factor4EnclosedScale factor;			// 当前题目所属因子
	private OptionGroup4EnclosedScale optionGroup;	// 当前因子所使用的选项组
	/**
	 * 样本体系外键
	 */
	private List<TopicResult4FactorResult4Sample4EnclosedScale> topicResults;  // 该题目的所有选择结果
	
	
	// =======================SETTER/GETTER===================
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Factor4EnclosedScale getFactor() {
		return factor;
	}
	public void setFactor(Factor4EnclosedScale factor) {
		this.factor = factor;
	}
	public OptionGroup4EnclosedScale getOptionGroup() {
		return optionGroup;
	}
	public void setOptionGroup(OptionGroup4EnclosedScale optionGroup) {
		this.optionGroup = optionGroup;
	}
	public List<TopicResult4FactorResult4Sample4EnclosedScale> getTopicResults() {
		return topicResults;
	}
	public void setTopicResults(List<TopicResult4FactorResult4Sample4EnclosedScale> topicResults) {
		this.topicResults = topicResults;
	}
	
}
