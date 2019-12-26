package cc.natapp4.ddaig.dao_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.Factor4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Factor4EnclosedScale;

@Repository("factor4EnclosedScaleDao")
public class Factor4EnclosedScaleDaoImpl extends BaseDaoImpl<Factor4EnclosedScale> implements Factor4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// =========================个性化方法=========================
	
}
