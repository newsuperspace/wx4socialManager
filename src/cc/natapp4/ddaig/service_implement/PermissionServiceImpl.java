package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.PermissionDao;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.service_interface.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

	@Resource(name="permissionDao")
	private PermissionDao  permissionDao;
	
	@Override
	protected BaseDao<Permission> getBaseDao() {
		return this.permissionDao;
	}

	// =========================真正的逻辑开始========================

}
