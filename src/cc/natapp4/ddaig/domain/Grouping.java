package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Grouping implements Serializable {
	
	// ===============字段=================
	private String gid;
	private String groupName;  // 组名
	private String description;  // 服务功能描述
	private String logoPath;     // logo的url
	
	/*
	 * ★★★
	 * 在微信公众号和本地系统中最主要使用的标签名（已经预定义在/wxConfig/initTags.properties文件中了）
	 */
	private String tag;  
	
	private long tagid; // 在微信公众号中的标签的ID值
	
	private Set<Member> members;    // 当前分组（标签）中的所有成员
	
	@JSON(serialize=false)
	public Set<Member> getMembers() {
		return members;
	}
	public void setMembers(Set<Member> members) {
		this.members = members;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getTagid() {
		return tagid;
	}
	public void setTagid(long tagid) {
		this.tagid = tagid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	
	// ==================构造器=======================
	
	public Grouping() {
	}

}
