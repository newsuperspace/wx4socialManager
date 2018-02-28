package cc.natapp4.ddaig.dao_implement;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.Receipt4BesureProjectDao;
import cc.natapp4.ddaig.domain.Receipt4BesureProject;

@Repository("receipt4BesureProjectDao")
public class Receipt4BesureProjectDaoImpl extends BaseDaoImpl<Receipt4BesureProject> implements Receipt4BesureProjectDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	
}
