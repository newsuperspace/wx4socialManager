package cc.natapp4.ddaig.dao_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.Option4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Option4EnclosedScale;

@Repository("option4EnclosedScaleDao")
public class Option4EnclosedScaleDaoImpl extends BaseDaoImpl<Option4EnclosedScale> implements Option4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	// ============个性化方法===========

}
