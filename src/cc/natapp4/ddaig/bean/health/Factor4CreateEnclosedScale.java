package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;

public class Factor4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	public Factor4CreateEnclosedScale() {
	}


	private int order;
	private long stamp;
	private String fid;
	private String name;
	private String keyword;
	private String description;
	private List<Section4Factor4CreateEnclosedScale> sections;

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
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Section4Factor4CreateEnclosedScale> getSections() {
		return sections;
	}
	public void setSections(List<Section4Factor4CreateEnclosedScale> sections) {
		this.sections = sections;
	}
	@Override
	public String toString() {
		return "Factor4CreateEnclosedScale [order=" + order + ", stamp=" + stamp + ", fid=" + fid + ", name=" + name
				+ ", keyword=" + keyword + ", description=" + description + ", sections=" + sections + "]";
	}
	
	
	
}
