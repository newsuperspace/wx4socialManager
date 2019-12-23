package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;


/**
 * OptionGroup4EnclosedScale → Option4EnclosedScale
 * 选项组中包含的选项
 * 
 * @author Administrator
 *
 */
public class Option4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 数据库字段
	 */
	private int order; 			// 创建选项的序数（从1开始）
	private String opid;		// 主键ID
	private String content;		// 选项文本内容
	private int score;			// 选择本选项后的得分
	/**
	 * 量表体系外键
	 */
	 private OptionGroup4EnclosedScale optionGroup;
	
	
	
}
