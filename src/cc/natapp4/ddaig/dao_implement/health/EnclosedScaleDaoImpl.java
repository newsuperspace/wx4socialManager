package cc.natapp4.ddaig.dao_implement.health;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.hibernate.PageLimitHibernateCallback4QueryEnclosedScale;
import cc.natapp4.ddaig.hibernate.PageLimitHibernateCallback4QueryUser;


@Repository("enclosedScaleDao")
public class EnclosedScaleDaoImpl extends BaseDaoImpl<EnclosedScale> implements EnclosedScaleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	
	
	// ----------------------分页查询---------------------
	@Override
	public int getCount4EnclosedScale() {
		List<EnclosedScale> list = (List<EnclosedScale>) this.hibernateTemplate.find("from EnclosedScale", null);
		return list.size();
	}
	@Override
	public List<EnclosedScale> getEssByPageLimit(int targetPageNum, int pageItemNumLimit) {

		String hql = "from EnclosedScale";
		Object[] params = new Object[] {};
		List<EnclosedScale> list = this.hibernateTemplate.execute(new PageLimitHibernateCallback4QueryEnclosedScale(targetPageNum, pageItemNumLimit, hql, params));
		
		return list;
	}

	
	

}
