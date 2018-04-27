package cc.natapp4.ddaig.service_interface;

import cc.natapp4.ddaig.domain.PermissionLevel;

public interface PermissionLevelService extends BaseService<PermissionLevel> {

	public PermissionLevel queryEntityByLevel(int level);
}
