package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 用来描述一级权限
 * 包括user/activity等
 * @author Administrator
 *
 */
public class PermissionLevel implements Serializable {

	// 主键
	private String plid;
	// 类型名
	private String permissionLevelName;
	// 对应的层级级别  从-1到4
	private int level;
	// 权限类型功能说明
	private String description;
	// 权限是否可用（默认是true）
	private boolean enabled;
	
	// ------------Foreign Key-------------
	// 当前类型中包含的所有权限
	private Set<PermissionType>  permissionTypes;
	
	//===========GETTERs/SETTERs=======

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getPlid() {
		return plid;
	}
	public void setPlid(String plid) {
		this.plid = plid;
	}
	public String getPermissionLevelName() {
		return permissionLevelName;
	}
	public void setPermissionLevelName(String permissionLevelName) {
		this.permissionLevelName = permissionLevelName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@JSON(serialize=false)
	public Set<PermissionType> getPermissionTypes() {
		return permissionTypes;
	}
	public void setPermissionTypes(Set<PermissionType> permissionTypes) {
		this.permissionTypes = permissionTypes;
	}


	
	
}
