package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;



/**
 * 封闭式问卷主体
 * @author Administrator
 *
 */
public class EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  ============== 数据库字段 ==============
	 */
	private String esid; // 主键
	private String description; // 量表使用说明（面向评测人员）
	private String introduce; // 量表功能介绍（面向用户）
	private String chName; // 量表名称（中文学术名称）
	private String engName; // 量表名称（英文学术名称）
	private float weigh; // 权重
	private int useNum;  // 累计使用数量
	
	/**
	 *  ============== 设置字段 ==============
	 */
	private boolean disorder;  // 出题时是否乱序排列
	
	
	
	/**
	 *  ================外键关联================
	 */
	/*
	 * ---------------量表体系----------------
	 */
	private List<Factor4EnclosedScale> factors;
	
	/*
	 * ---------------样本体系----------------
	 */
	private List<Sample4EnclosedScale> samples; // 容纳当前问卷所产生的
	
	
	
	
	
	
}
