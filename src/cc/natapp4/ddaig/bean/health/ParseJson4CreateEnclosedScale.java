package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;


/**
 *  用于 HealthAction.createEnclosedScale() 解析从 createEnclosedScale.jsp页面中提交过来的
 *  的JSON字符串
 *  
 *  
 *  
 * @author Administrator
 *
 */
public class ParseJson4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;
	
	// ============ 属性列表 =============
	private General4CreateEnclosedScale  general;
	private List<Factor4CreateEnclosedScale> factors;
	private List<OpGroup4CreateEnclosedScale> opGroups;
	private List<Topic4CreateEnclosedScale> topics;
	
	public ParseJson4CreateEnclosedScale() {
	}

	public General4CreateEnclosedScale getGeneral() {
		return general;
	}

	public void setGeneral(General4CreateEnclosedScale general) {
		this.general = general;
	}

	public List<Factor4CreateEnclosedScale> getFactors() {
		return factors;
	}

	public void setFactors(List<Factor4CreateEnclosedScale> factors) {
		this.factors = factors;
	}

	public List<OpGroup4CreateEnclosedScale> getOpGroups() {
		return opGroups;
	}

	public void setOpGroups(List<OpGroup4CreateEnclosedScale> opGroups) {
		this.opGroups = opGroups;
	}

	public List<Topic4CreateEnclosedScale> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic4CreateEnclosedScale> topics) {
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "ParseJson4CreateEnclosedScale [general=" + general + ", factors=" + factors + ", opGroups=" + opGroups
				+ ", topics=" + topics + "]";
	}
	

}
