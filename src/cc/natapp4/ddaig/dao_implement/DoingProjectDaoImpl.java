package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.DoingProjectDao;
import cc.natapp4.ddaig.domain.DoingProject;

@Repository("doingProjectDao")
public class DoingProjectDaoImpl extends BaseDaoImpl<DoingProject> implements DoingProjectDao {

	//======必须要向BaseDaoImpl中提供的用来实现基础数据库操作方法的hibernateTemplate实例======
	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	// ===========下面实现的是各个个性化Interface接口提供的个性化业务数据库操作方法============
	
	
}
