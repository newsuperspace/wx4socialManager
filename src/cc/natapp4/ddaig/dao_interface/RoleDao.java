package cc.natapp4.ddaig.dao_interface;

import java.util.List;
import java.util.Set;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;

public interface RoleDao extends BaseDao<Role> {

	/**
	 * 创建新角色
	 * @param role   将该角色内容写入到数据库Role表中
	 * @return  返回该角色对象用以后续添加权限等操作
	 */
	public Role createRole(Role role);
	
	/**
	 * 删除某一角色
	 * @param roleID
	 */
	public void deleteRole(String roleID);
	
	/**
	 * 建立角色与权限之间的关系
	 * @param roleID  角色ID
	 * @param perms  List容器中包含需要添加到角色中的权限对象
	 */
	public void correlationPermissions(String roleID, Set<Permission> perms );

	/**
	 * 移除角色与权限之间的关系
	 * @param roleID  角色ID
	 * @param perms  List容器中包含需要与角色解除关系的权限对象
	 */
	public void uncorrelationPermissions(String roleID, Set<Permission> perms);
}
