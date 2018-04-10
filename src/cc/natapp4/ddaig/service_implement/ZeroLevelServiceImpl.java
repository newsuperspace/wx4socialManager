package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

@Service("zeroLevelService")
public class ZeroLevelServiceImpl extends BaseServiceImpl<ZeroLevel> implements ZeroLevelService {

	@Resource(name="zeroLevelDao")
	private ZeroLevelDao  zeroLevelDao;
	
	@Override
	protected BaseDao<ZeroLevel> getBaseDao() {
		return zeroLevelDao;
	}

	// =========================个性化方法=====================
	
}
