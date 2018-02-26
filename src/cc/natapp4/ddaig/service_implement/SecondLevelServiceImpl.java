package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;

@Service("secondLevelService")
public class SecondLevelServiceImpl extends BaseServiceImpl<SecondLevel> implements SecondLevelService {

	@Resource(name="secondLevelDao")
	private SecondLevelDao  secondLevelDao;
	
	@Override
	protected BaseDao<SecondLevel> getBaseDao() {
		return secondLevelDao;
	}

	
}
