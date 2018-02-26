package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;

@Service("firstLevelService")
public class FirstLevelServiceImpl extends BaseServiceImpl<FirstLevel> implements FirstLevelService {

	@Resource(name="firstLevelDao")
	private FirstLevelDao  firstLevelDao;
	
	@Override
	protected BaseDao<FirstLevel> getBaseDao() {
		return firstLevelDao;
	}

	
}
