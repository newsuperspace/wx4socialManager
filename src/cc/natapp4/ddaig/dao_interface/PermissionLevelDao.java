package cc.natapp4.ddaig.dao_interface;

import cc.natapp4.ddaig.domain.PermissionLevel;

public interface PermissionLevelDao extends BaseDao<PermissionLevel> {

	public PermissionLevel queryEntityByLevel(int level);
}
