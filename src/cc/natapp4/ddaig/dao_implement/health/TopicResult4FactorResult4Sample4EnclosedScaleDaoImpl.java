package cc.natapp4.ddaig.dao_implement.health;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.TopicResult4FactorResult4Sample4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.TopicResult4FactorResult4Sample4EnclosedScale;

@Repository("topicResult4FactorResult4Sample4EnclosedScaleDao")
public class TopicResult4FactorResult4Sample4EnclosedScaleDaoImpl
		extends BaseDaoImpl<TopicResult4FactorResult4Sample4EnclosedScale>
		implements TopicResult4FactorResult4Sample4EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	// ==============个性化方法==============

}
