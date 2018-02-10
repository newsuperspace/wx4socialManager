package cc.natapp4.ddaig.domain.cengji;

import java.util.Set;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;

/**
 * 社区分层的第一层级——大类型
 * 
 * 党组织 区域化党建单位组织 居民代表 楼门组长 社区社会组织 志愿者
 * 
 * @author Administrator
 *
 */
public class FirstLevel implements LevelInterface {

	private String flid;
	// 当前类型或团队的名字
	private String name;
	// 当前类型或团队的描述
	private String description;
	// 标注当前类所描述的管理层级别是1级（最高级）
	private int level = LEVEL_ONE;
	// 存放属于当前级别的下一级别的级别对象【个性】
	private Set<SecondLevel> children;
	// 当前层级的管理员（唯一）
	private User manager;
	// 当前层级所属的上一级（如果当前级别为LEVEL_ONE，则没有上一级）【个性】
	// private ManagerLevelInterface parentLevel;
	// 当前层级所包含的权限集合（也就是角色）
	private Role  role;

	// ==================================SETTERs/GETTERs=====================================
	public String getName() {
		return name;
	}

	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
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


	public Set<SecondLevel> getChildren() {
		return children;
	}

	public void setChildren(Set<SecondLevel> children) {
		this.children = children;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public Role getRole() {
		return role;
	}

}
