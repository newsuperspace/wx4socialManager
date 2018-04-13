package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.PermissionLevelDao;
import cc.natapp4.ddaig.dao_interface.PermissionTypeDao;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.PermissionType;
import cc.natapp4.ddaig.service_interface.PermissionLevelService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;

@Service("permissionLevelService")
public class PermissionLevelServiceImpl extends BaseServiceImpl<PermissionLevel> implements PermissionLevelService {

	// =============实现基础数据库操作功能的必要准备工作==============
	@Resource(name="permissionLevelDao")
	private PermissionLevelDao  permissionLevelDao;
	
	/**
	 * 你需要把BaseDaoImpl实例对象给BaseServiceImpl
	 * 从而，在BaseServiceImpl中才可以通过调用你传给它的BaseDaoImpl中的方法来实现
	 * 对应DAO层基础数据库操作函数的基础Service方法。
	 */
	@Override
	protected BaseDao<PermissionLevel> getBaseDao() {
		// 由于每个Dao实现类都必定继承自BaseDaoImpl<T>，因此只需要将DAO子类实例传递过去就会实现自动转型操作
		return permissionLevelDao;
	}

	// =================其他所需要的DI注入（基于Spring容器）====================

	
	
	// ================真正实现的业务逻辑方法===================
	
	
	

}
