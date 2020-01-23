package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 封闭式问卷主体
 * 
 * @author Administrator
 *
 */
public class EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ============== 数据库字段 ==============
	 */
	private String esid; // 主键
	private String description; // 量表使用说明（面向评测人员）
	private String introduce; // 量表功能介绍（面向用户）
	private String chName; // 量表名称（中文学术名称）
	private String engName; // 量表名称（英文学术名称）
	private float weigh; // 权重
	private int useNum; // 累计使用数量

	private int version = 1; // 版本号
	/**
	 * ============== 设置字段 ==============
	 */
	private boolean disorder; // 出题时是否乱序排列

	/**
	 * ================外键关联================
	 */
	/*
	 * ---------------量表体系----------------
	 */
	private List<Factor4EnclosedScale> factors;

	/*
	 * ---------------样本体系----------------
	 */
	private List<Sample4EnclosedScale> samples; // 容纳当前问卷所产生的

	
	/*
	 * ==============前端显示用字段==============
	 */
	private int topicNum;   // 向前端显示题目数量
	public int getTopicNum() {
		int total = 0;
		for(Factor4EnclosedScale factor: factors) {
			List<Topic4EnclosedScale> topics = factor.getTopics();
			total += topics.size();
		}
		return total;
	}

	private long sampleNum;   // 向前端显示样本数量
	public long getSampleNum() {
		return samples.size();
	}
	
	private int factorNum;		// 向前端显示因子数量
	public int getFactorNum() {
		return factors.size();
	}

	private String date = "2020-12-31";  // 向前端现实形如 yyyy-MM-dd 创建日期
	public String getDate() {
//		SimpleDateFormat  formater = new SimpleDateFormat("yyyy-MM-dd");
//		formater.format(new Date())
		return date;
	}

	// ==============================SETTER/GETTER===================================
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
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

	public float getWeigh() {
		return weigh;
	}

	public void setWeigh(float weigh) {
		this.weigh = weigh;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public boolean isDisorder() {
		return disorder;
	}

	public void setDisorder(boolean disorder) {
		this.disorder = disorder;
	}

	@JSON(serialize=false)
	public List<Factor4EnclosedScale> getFactors() {
		return factors;
	}
	public void setFactors(List<Factor4EnclosedScale> factors) {
		this.factors = factors;
	}

	@JSON(serialize=false)
	public List<Sample4EnclosedScale> getSamples() {
		return samples;
	}
	public void setSamples(List<Sample4EnclosedScale> samples) {
		this.samples = samples;
	}


	
}
