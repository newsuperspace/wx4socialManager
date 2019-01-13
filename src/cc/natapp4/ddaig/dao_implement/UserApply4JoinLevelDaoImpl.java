package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.UserApply4JoinLevelDao;
import cc.natapp4.ddaig.domain.UserApply4JoinLevel;

@Repository("userApply4JoinLevelDao")
public class UserApply4JoinLevelDaoImpl extends BaseDaoImpl<UserApply4JoinLevel> implements UserApply4JoinLevelDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
