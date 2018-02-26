package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.DoingProjectDao;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.service_interface.DoingProjectService;

@Service("doingProjectService")
public class DoingProjectServiceImpl extends BaseServiceImpl<DoingProject> implements DoingProjectService {

	// =============实现基础数据库操作功能的必要准备工作==============
	@Resource(name="doingProjectDao")
	private DoingProjectDao  doingProjectDao;
	
	/**
	 * 你需要把BaseDaoImpl实例对象给BaseServiceImpl
	 * 从而，在BaseServiceImpl中才可以通过调用你传给它的BaseDaoImpl中的方法来实现
	 * 对应DAO层基础数据库操作函数的基础Service方法。
	 */
	@Override
	protected BaseDao<DoingProject> getBaseDao() {
		// 由于每个Dao实现类都必定继承自BaseDaoImpl<T>，因此只需要将DAO子类实例传递过去就会实现自动转型操作
		return doingProjectDao;
	}

	// =================其他所需要的DI注入（基于Spring容器）====================

	
	
	// ================真正实现的业务逻辑方法===================
	
	
	

}
