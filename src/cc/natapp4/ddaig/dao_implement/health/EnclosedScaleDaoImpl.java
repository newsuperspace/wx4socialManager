package cc.natapp4.ddaig.dao_implement.health;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.EnclosedScale;


@Repository("enclosedScaleDao")
public class EnclosedScaleDaoImpl extends BaseDaoImpl<EnclosedScale> implements EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	
	

}
