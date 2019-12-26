package cc.natapp4.ddaig.dao_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.Topic4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Topic4EnclosedScale;

@Repository("topic4EnclosedScaleDao")
public class Topic4EnclosedScaleDaoImpl extends BaseDaoImpl<Topic4EnclosedScale> implements Topic4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	
	// ==============个性化方法===============

}
