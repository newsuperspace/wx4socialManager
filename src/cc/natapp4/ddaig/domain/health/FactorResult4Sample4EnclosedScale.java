package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;

/**
 * Sample4EnclosedScale → FactorResult4Sample4EnclosedScale
 * 用于记录一个样本中，各个因子的测量结果，通常用于如下统计情景
 * （1）相同用户，随着时间推移的横向数据比对，建议使用折线图表示；
 * （2）同一个时期内，某一群体中的多个用户的纵向数据比对，建议以section为横轴坐标的柱状图表示
 * 
 * @author Administrator
 *
 */
public class FactorResult4Sample4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库字段
	 */
	private String frid;    // 主键ID
	private float oscore;  // 原始分
	private float sscore; // 标准分 = 原始分×权重
	private String conclusion;  //诊断结论，来自于Factor4EnclosedScale.diagnosis
	
	/**
	 * 样本体系外键
	 */
	private Sample4EnclosedScale sample;
	private List<TopicResult4FactorResult4Sample4EnclosedScale> topicResults;
	/**
	 * 量表体系外键
	 */
	
	
	
}
