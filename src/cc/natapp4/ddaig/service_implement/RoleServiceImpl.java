package cc.natapp4.ddaig.service_implement;


import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.RoleDao;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.service_interface.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Resource(name="roleDao")
	private RoleDao  roleDao;
	
	@Override
	protected BaseDao<Role> getBaseDao() {
		return this.roleDao;
	}

	@Override
	public Role createRole(Role role) {
		return roleDao.createRole(role);
	}


	@Override
	public void deleteRole(String roleID) {
		roleDao.deleteRole(roleID);
	}

	@Override
	public void correlationPermissions(String roleID, Set<Permission> perms) {
		roleDao.correlationPermissions(roleID, perms);
	}


	@Override
	public void uncorrelationPermissions(String roleID, Set<Permission> perms) {
		roleDao.uncorrelationPermissions(roleID, perms);
	}

}
