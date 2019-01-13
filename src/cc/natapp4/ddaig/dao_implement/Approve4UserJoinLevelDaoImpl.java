package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.Approve4UserJoinLevelDao;
import cc.natapp4.ddaig.domain.Approve4UserJoinLevel;

@Repository("approve4UserJoinLevelDao")
public class Approve4UserJoinLevelDaoImpl extends BaseDaoImpl<Approve4UserJoinLevel> implements Approve4UserJoinLevelDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
