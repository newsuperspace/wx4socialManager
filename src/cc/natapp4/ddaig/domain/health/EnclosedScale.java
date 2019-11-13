package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.util.List;



/**
 * 封闭式问卷
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
	private String description; // 量表使用说明（积分方法等）
	private String introduce; // 量表功能介绍
	private String chName; // 量表名称（中文学术名称）
	private String engName; // 量表名称（英文学术名称）
	private int useNum;  // 累计使用数量
	private float weigh; // 权重
	
	public String getEsid() {
		return esid;
	}
	public void setEsid(String esid) {
		this.esid = esid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public int getUseNum() {
		return useNum;
	}
	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}
	public float getWeigh() {
		return weigh;
	}
	public void setWeigh(float weigh) {
		this.weigh = weigh;
	}
	
	/**
	 *  ================外键关联================
	 */
	List<Topic4EnclosedScale> topics; // 容纳当前问卷的所有题目对象
	List<Sample4EnclosedScale> samples; // 容纳当前问卷所产生的
	List<Option4EnclosedScale> options; // 容纳当前问卷每个题目的选项

	
	public List<Topic4EnclosedScale> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic4EnclosedScale> topics) {
		this.topics = topics;
	}
	public List<Sample4EnclosedScale> getSamples() {
		return samples;
	}
	public void setSamples(List<Sample4EnclosedScale> samples) {
		this.samples = samples;
	}
	public List<Option4EnclosedScale> getOptions() {
		return options;
	}
	public void setOptions(List<Option4EnclosedScale> options) {
		this.options = options;
	}
	
	
	
	
	
	
	
}
