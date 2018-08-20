package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.VisitorDao;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.service_interface.VisitorService;

@Service("visitorService")
public class VisitorServiceImpl extends BaseServiceImpl<Visitor> implements VisitorService {

	@Resource(name="visitorDao")
	private VisitorDao  visitorDao;
	
	@Override
	protected BaseDao<Visitor> getBaseDao() {
		return this.visitorDao;
	}

	

	
}
