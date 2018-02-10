package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Role implements Serializable {

	private  String role;  // 角色标识符
	private  String  rid; // 角色ID
	private  String   description; // 角色描述
	private  boolean   available;   // 角色是否可用
	
	private Set<User>  users;  // 拥有当前角色的用户列表
	private  Set<Permission>  permissions;  // 当前角色所拥有的权限列表
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
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
	
	/**
	 * ★★★★★
	 * 这里为了防止struts-json-plugin.jar 在解析Role对象的时候出现死循环（Role中解析到User，而User中又有Role，这样就会造成死循环）
	 * 影响前端的响应，struts-json-plugin.jar 不能自己识别出死循环，因此必须手动铜鼓o@JSON注解来告诉json结果集不用深入解析某个GETTER
	 * 所获得的属性的类了。
	 * 但是在前端页面roleList.jsp中显示 角色 详细信息的modal中又必须获取与模一角色绑定的全部User对象，因此这个时候就应该像
	 * RoleAction.getRoleByRid() 的用于响应前端Ajax请求的Result类——Result4GetRoleByRid那样，单独为Users提供一个容器属性位置
	 * 这样前端就能通过该属性获取与当前角色绑定的全部用户对象，而不用非得从data.role.users 中获取users（因为在domain中users属性的GETTER上
	 * 添加的@JSON注解，导致json结果集不会深入扫描该属性，于是返回给前端的json对象专用局部可能包含users的数据）
	 */
	@JSON(serialize=false)
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
