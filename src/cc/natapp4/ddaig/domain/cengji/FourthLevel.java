package cc.natapp4.ddaig.domain.cengji;

import java.util.Set;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;

/**
 * 第四级——支队
 * 
 * @author Administrator
 *
 */
public class FourthLevel implements LevelInterface {

	private String foid;
	// 当前支队的名字
	private String name;
	// 当前支队的描述
	private String description;
	// 标注当前类所描述的管理层级别是4级（目前的最低级别）
	private int level = LEVEL_FOUR;
	// 当前层级所属的上一级（如果当前级别为LEVEL_ONE，则没有上一级）【个性】
	private ThirdLevel parent;
	// 存放属于当前级别的下一级别的级别对象【个性】
	private Set<User> children;
	// 当前层级的管理员（唯一）
	private User manager;
	// 当前层级所包含的权限集合（也就是角色）
	private Role role;

	// ========================SETTERs/GETTERs==========================
	public String getFoid() {
		return foid;
	}
	public void setFoid(String foid) {
		this.foid = foid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public ThirdLevel getParent() {
		return parent;
	}
	public void setParent(ThirdLevel parent) {
		this.parent = parent;
	}
	public Set<User> getChildren() {
		return children;
	}
	public void setChildren(Set<User> children) {
		this.children = children;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
}
