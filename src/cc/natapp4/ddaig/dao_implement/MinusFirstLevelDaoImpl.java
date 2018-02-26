package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.MinusFirstLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;

@Repository("minusFirstLevelDao")
public class MinusFirstLevelDaoImpl extends BaseDaoImpl<MinusFirstLevel> implements MinusFirstLevelDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
