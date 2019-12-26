package cc.natapp4.ddaig.dao_implement.health;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.FactorResult4Sample4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.FactorResult4Sample4EnclosedScale;

@Repository("factorResult4Sample4EnclosedScaleDao")
public class FactorResult4Sample4EnclosedScaleDaoImpl extends BaseDaoImpl<FactorResult4Sample4EnclosedScale>
		implements FactorResult4Sample4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
