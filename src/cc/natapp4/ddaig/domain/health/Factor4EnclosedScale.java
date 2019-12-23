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
	private int order;  // 该因子创建时在量表中的序数（从1开始）
	private long stamp; // 创建因子的时间戳
	private String fid; // 因子的主键ID
	private String name;  // 因子名
	private String keyword;   // 因子关键词
	private String description;   // 因子功能描述
	
	/**
	 * 量表体系外键
	 */
	private List<Section4Factor4EnclosedScale> sections;   // 当前因子所包含计分单元
	private List<Topic4EnclosedScale> topics;				// 当前因子所包含题目
	/**
	 * 样本体系外键
	 */
	private List<FactorResult4Sample4EnclosedScale> factorResults;
	
}
