package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.dao_interface.ThirdLevelDao;
import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

@Service("zeroLevelService")
public class ZeroLevelServiceImpl extends BaseServiceImpl<ZeroLevel> implements ZeroLevelService {

	@Resource(name="zeroLevelDao")
	private ZeroLevelDao  zeroLevelDao;
	
	@Override
	protected BaseDao<ZeroLevel> getBaseDao() {
		return zeroLevelDao;
	}

	
}
