package cc.natapp4.ddaig.dao_implement;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.Reply4UserJoinLevelApproveDao;
import cc.natapp4.ddaig.domain.Reply4UserJoinLevelApprove;

@Repository("reply4UserJoinLevelApproveDao")
public class Reply4UserJoinLevelApproveDaoImpl extends BaseDaoImpl<Reply4UserJoinLevelApprove> implements Reply4UserJoinLevelApproveDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	

}
