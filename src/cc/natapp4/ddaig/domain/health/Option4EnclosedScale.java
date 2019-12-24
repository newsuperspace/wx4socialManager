package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;

/**
 * OptionGroup4EnclosedScale → Option4EnclosedScale 选项组中包含的选项
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
	private String opid; // 主键ID
	private int ord; // 创建选项的序数（从1开始）,因为order为SQL关键字，因此简写为ord
	private String content; // 选项文本内容
	private int score; // 选择本选项后的得分
	/**
	 * 量表体系外键
	 */
	private OptionGroup4EnclosedScale optionGroup;

	// ======================SETTER/GETTER======================
	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public OptionGroup4EnclosedScale getOptionGroup() {
		return optionGroup;
	}

	public void setOptionGroup(OptionGroup4EnclosedScale optionGroup) {
		this.optionGroup = optionGroup;
	}

}
