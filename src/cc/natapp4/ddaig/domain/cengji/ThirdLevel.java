package cc.natapp4.ddaig.domain.cengji;

import java.util.Set;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;

/**
 * 第三级—— 总队
 * 
 * @author Administrator
 *
 */
public class ThirdLevel implements LevelInterface {

	private String thid;
	// 当前类型或团队的名字
	private String name;
	// 当前类型或团队的描述
	private String description;
	// 标注当前类所描述的管理层级别是3级
	private int level = LEVEL_THREE;
	// 当前层级所属的上一级（如果当前级别为LEVEL_ONE，则没有上一级）【个性】
	private SecondLevel parent;
	// 存放属于当前级别的下一级别的级别对象【个性】
	private Set<FourthLevel> children;
	// 当前层级的管理员（唯一）
	private User manager;
	// 当前层级所包含的权限集合（也就是角色）
	private Role role;

	// ==================================SETTERs/GETTERs=====================================
	public String getThid() {
		return thid;
	}
	public void setThid(String thid) {
		this.thid = thid;
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
	public SecondLevel getParent() {
		return parent;
	}
	public void setParent(SecondLevel parent) {
		this.parent = parent;
	}
	public Set<FourthLevel> getChildren() {
		return children;
	}
	public void setChildren(Set<FourthLevel> children) {
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
