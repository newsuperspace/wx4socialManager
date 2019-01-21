package cc.natapp4.ddaig.service_implement;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.UserApply4JoinLevelDao;
import cc.natapp4.ddaig.domain.UserApply4JoinLevel;
import cc.natapp4.ddaig.service_interface.UserApply4JoinLevelService;

@Service("userApply4JoinLevelService")
public class UserApply4JoinLevelServiceImpl extends BaseServiceImpl<UserApply4JoinLevel> implements UserApply4JoinLevelService {

	@Resource(name="userApply4JoinLevelDao")
	private UserApply4JoinLevelDao  userApply4JoinLevelDao;
	
	@Override
	protected BaseDao<UserApply4JoinLevel> getBaseDao() {
		return userApply4JoinLevelDao;
	}

	// =========================个性化方法=====================
	@Override
	public List<UserApply4JoinLevel> getUserApplyList(String tag, String lid, int status) {
		return userApply4JoinLevelDao.getUserApplyList(tag, lid, status);
	}

}
