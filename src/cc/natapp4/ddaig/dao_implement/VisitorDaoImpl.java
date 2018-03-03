package cc.natapp4.ddaig.dao_implement;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.VisitorDao;
import cc.natapp4.ddaig.domain.Visitor;

@Repository("visitorDao")
public class VisitorDaoImpl extends BaseDaoImpl<Visitor> implements VisitorDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
