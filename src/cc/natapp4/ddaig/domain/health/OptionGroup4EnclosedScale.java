package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;


/**
 * 封闭式问卷（EnclosedScale）中每个题目（Topic） 可以选择所使用的 选项组
 * @author Administrator
 *
 */
public class OptionGroup4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库字段
	 */
	private String ogid;  // 主键ID
	private int order;  // 选项组创建时的序数（从1开始）
	private long stamp; // 想想组创建时的时间戳
	private String name; // 选项组名称
	private String introduce;   // 选项描述
	
	/**
	 * 量表体系外键
	 */
	private List<Option4EnclosedScale> options;
	
	
	
}
