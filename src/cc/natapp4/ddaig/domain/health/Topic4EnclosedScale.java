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
	private int ord;				// 从1开始的题目序号，本应使用order，但作为SQL保留字不得不缩减使用
	private boolean rever;		// 当前问题是否为反向计分，本来应该使用英文词汇全程reverse，但此为SQL保留词，因此必须缩减
	private String keyword;			// 当前问题的关键词，例如“焦虑”，非必填
	/**
	 *  量表体系外键
	 */
	private Factor4EnclosedScale factor;
	private OptionGroup4EnclosedScale optionGroup;
	/**
	 * 样本体系外键
	 */
	private List<TopicResult4FactorResult4Sample4EnclosedScale> topicResults;

}
