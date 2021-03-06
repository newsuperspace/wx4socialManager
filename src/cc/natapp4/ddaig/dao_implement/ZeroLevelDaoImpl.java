package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

@Repository("zeroLevelDao")
public class ZeroLevelDaoImpl extends BaseDaoImpl<ZeroLevel> implements ZeroLevelDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
