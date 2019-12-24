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
	private int ord;  // 选项组创建时的序数（从1开始）,因为order为SQL保留字，所以简写为ord
	private long stamp; // 想想组创建时的时间戳
	private String name; // 选项组名称
	private String introduce;   // 选项描述
	
	/**
	 * 量表体系外键
	 */
	private List<Option4EnclosedScale> options;
	private List<Topic4EnclosedScale> topics;
	
	
	// ===================SETTER/GETTER=====================
	public String getOgid() {
		return ogid;
	}
	public void setOgid(String ogid) {
		this.ogid = ogid;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public List<Option4EnclosedScale> getOptions() {
		return options;
	}
	public void setOptions(List<Option4EnclosedScale> options) {
		this.options = options;
	}
	public List<Topic4EnclosedScale> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic4EnclosedScale> topics) {
		this.topics = topics;
	}
	
	
	
	
	
	
	
	
}
