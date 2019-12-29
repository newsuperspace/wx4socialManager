package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;

public class OpGroup4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

//	 "order": 1,
//     "stamp": 1576903849001,
//     "ogid": "3284029384jfdjsufsd",
//     "name": "第一选项组",
//     "introduce": "这是用于描述第一因子中题目的选项",
//     "options":
	
	private int order;
	private long stamp;
	private String ogid;
	private String name;
	private String introduce;
	private List<Option4Opgroup4CreateEnclosedScale> options;
	
	public OpGroup4CreateEnclosedScale() {
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getStamp() {
		return stamp;
	}

	public void setStamp(long stamp) {
		this.stamp = stamp;
	}

	public String getOgid() {
		return ogid;
	}

	public void setOgid(String ogid) {
		this.ogid = ogid;
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

	public List<Option4Opgroup4CreateEnclosedScale> getOptions() {
		return options;
	}

	public void setOptions(List<Option4Opgroup4CreateEnclosedScale> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "OpGroup4CreateEnclosedScale [order=" + order + ", stamp=" + stamp + ", ogid=" + ogid + ", name=" + name
				+ ", introduce=" + introduce + ", options=" + options + "]";
	}
	
	
	
	
}
