package cc.natapp4.ddaig.dao_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.Section4Factor4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Section4Factor4EnclosedScale;

@Repository("section4Factor4EnclosedScaleDao")
public class Section4Factor4EnclosedScaleDaoImpl extends BaseDaoImpl<Section4Factor4EnclosedScale>
		implements Section4Factor4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	
	// ===============个性化方法===============
	

}
