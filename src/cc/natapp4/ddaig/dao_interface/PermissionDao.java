package cc.natapp4.ddaig.dao_interface;

import cc.natapp4.ddaig.domain.Permission;

public interface PermissionDao extends BaseDao<Permission> {

	/**
	 * 新建权限
	 * @param permission
	 * @return
	 */
	public Permission createPermission(Permission permission);
	
	/**
	 * 删除权限
	 * @param permissionId
	 */
	public void deletePermission(String permissionID );
}
