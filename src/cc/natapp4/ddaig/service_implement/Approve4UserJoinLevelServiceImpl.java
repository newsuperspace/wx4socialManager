package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.Approve4UserJoinLevelDao;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.Approve4UserJoinLevel;
import cc.natapp4.ddaig.service_interface.Approve4UserJoinLevelService;

@Service("approve4UserJoinLevelService")
public class Approve4UserJoinLevelServiceImpl extends BaseServiceImpl<Approve4UserJoinLevel> implements Approve4UserJoinLevelService {

	@Resource(name="approve4UserJoinLevelDao")
	private Approve4UserJoinLevelDao  approve4UserJoinLevelDao;
	
	@Override
	protected BaseDao<Approve4UserJoinLevel> getBaseDao() {
		return approve4UserJoinLevelDao;
	}

	// =========================个性化方法=====================
}
