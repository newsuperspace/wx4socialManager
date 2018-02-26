package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.dao_interface.ThirdLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;

@Repository("thirdLevelDao")
public class ThirdLevelDaoImpl extends BaseDaoImpl<ThirdLevel> implements ThirdLevelDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
