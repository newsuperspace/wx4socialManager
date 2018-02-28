package cc.natapp4.ddaig.service_implement;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.Receipt4BesureProjectDao;
import cc.natapp4.ddaig.domain.Receipt4BesureProject;
import cc.natapp4.ddaig.service_interface.Receipt4BesureProjectService;

@Service("receipt4BesureProjectService")
public class Receipt4BesureProjectServiceImpl extends BaseServiceImpl<Receipt4BesureProject> implements Receipt4BesureProjectService {

	@Resource(name="receipt4BesureProjectDao")
	private Receipt4BesureProjectDao   receipt4BesureProjectDao;
	
	@Override
	protected BaseDao<Receipt4BesureProject> getBaseDao() {
		return this.receipt4BesureProjectDao;
	}

	
	

}
