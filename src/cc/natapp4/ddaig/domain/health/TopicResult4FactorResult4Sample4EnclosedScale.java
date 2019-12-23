package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;


/**
 * Sample4EnclosedScale → FactorResult4Sample4EnclosedScale → TopicResult4FactorResult4Sample4EnclosedScale
 * 用于记录一个样本中的某个因子中的某个题目的选取信息，通常用于如下统计情景：
 * （1）不同用户在同一时期，针对该题目的点状分布；
 * 
 * @author Administrator
 *
 */
public class TopicResult4FactorResult4Sample4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  数据库字段
	 */
	private String trid;  // 主键ID
	private int score;   // 该题目用户选择的得分（用于计分）
	private String content; // 该题目用户选择的题目内容（用于展示） 
	/**
	 *  样本体系外键
	 */
	private FactorResult4Sample4EnclosedScale  factorResult;   // 当前单个题目结果所属的因子结果
	/**
	 * 量表体系外键
	 */
	private 
	
}
