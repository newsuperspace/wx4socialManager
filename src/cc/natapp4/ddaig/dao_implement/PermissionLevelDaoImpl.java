package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.PermissionLevelDao;
import cc.natapp4.ddaig.domain.PermissionLevel;

@Repository("permissionLevelDao")
public class PermissionLevelDaoImpl extends BaseDaoImpl<PermissionLevel> implements PermissionLevelDao {

	//======必须要向BaseDaoImpl中提供的用来实现基础数据库操作方法的hibernateTemplate实例======
	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	// ===========下面实现的是各个个性化Interface接口提供的个性化业务数据库操作方法============

	@Override
	public PermissionLevel queryEntityByLevel(int level) {
		
		List<PermissionLevel> list = (List<PermissionLevel>) this.hibernateTemplate.find("from PermissionLevel where level=?", level);
		return list.get(0);
	}

	
	
}
