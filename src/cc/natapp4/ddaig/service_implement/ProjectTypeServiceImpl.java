package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.PermissionTypeDao;
import cc.natapp4.ddaig.dao_interface.ProjectTypeDao;
import cc.natapp4.ddaig.domain.PermissionType;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;

@Service("projectTypeService")
public class ProjectTypeServiceImpl extends BaseServiceImpl<ProjectType> implements ProjectTypeService {

	// =============实现基础数据库操作功能的必要准备工作==============
	@Resource(name="projectTypeDao")
	private ProjectTypeDao  projectTypeDao;
	
	/**
	 * 你需要把BaseDaoImpl实例对象给BaseServiceImpl
	 * 从而，在BaseServiceImpl中才可以通过调用你传给它的BaseDaoImpl中的方法来实现
	 * 对应DAO层基础数据库操作函数的基础Service方法。
	 */
	@Override
	protected BaseDao<ProjectType> getBaseDao() {
		// 由于每个Dao实现类都必定继承自BaseDaoImpl<T>，因此只需要将DAO子类实例传递过去就会实现自动转型操作
		return projectTypeDao;
	}

	// =================其他所需要的DI注入（基于Spring容器）====================

	
	
	// ================真正实现的业务逻辑方法===================
	
	
	

}
