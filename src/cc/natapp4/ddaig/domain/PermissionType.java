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
public class PermissionType implements Serializable {

	// 主键
	private  String ptid;
	// 类型名
	private String permissionTypeName;
	// 权限类型功能说明
	private String description;
	// 权限是否可用（默认是true）
	private boolean enabled;
	
	// ------------Foreign Key-------------
	// 当前类型中包含的所有权限
	private Set<Permission>  permissions;
	// 当前类型所属的层级级别
	private PermissionLevel  permissionLevel;
	
	//===========GETTERs/SETTERs=======
	
	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}
	public void setPermissionLevel(PermissionLevel permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public String getPermissionTypeName() {
		return permissionTypeName;
	}
	public void setPermissionTypeName(String permissionTypeName) {
		this.permissionTypeName = permissionTypeName;
	}

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

	@JSON(serialize=false)
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
