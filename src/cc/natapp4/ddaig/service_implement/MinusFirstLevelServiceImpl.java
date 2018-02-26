package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.MinusFirstLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;

@Service("minusFirstLevelService")
public class MinusFirstLevelServiceImpl extends BaseServiceImpl<MinusFirstLevel> implements MinusFirstLevelService {

	@Resource(name="minusFirstLevelDao")
	private MinusFirstLevelDao  minusFirstLevelDao;
	
	@Override
	protected BaseDao<MinusFirstLevel> getBaseDao() {
		return minusFirstLevelDao;
	}

	
}
