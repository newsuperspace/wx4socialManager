package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Permission implements Serializable {

	private String permission;   // 权限标识符
	private String description;   // 描述
	private boolean available;		// 是否可用
	private String pid;   // 主键
	
	private Set<Role>  roles;   // 拥有当前权限的角色列表

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	@JSON(serialize=false)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
