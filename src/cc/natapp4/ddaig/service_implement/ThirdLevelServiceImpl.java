package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.dao_interface.ThirdLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;

@Service("thirdLevelService")
public class ThirdLevelServiceImpl extends BaseServiceImpl<ThirdLevel> implements ThirdLevelService {

	@Resource(name="thirdLevelDao")
	private ThirdLevelDao  thirdLevelDao;
	
	@Override
	protected BaseDao<ThirdLevel> getBaseDao() {
		return thirdLevelDao;
	}

	
}
