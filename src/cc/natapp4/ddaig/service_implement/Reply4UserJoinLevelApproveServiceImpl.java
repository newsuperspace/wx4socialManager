package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.Reply4UserJoinLevelApproveDao;
import cc.natapp4.ddaig.domain.Reply4UserJoinLevelApprove;
import cc.natapp4.ddaig.service_interface.Reply4UserJoinLevelApproveService;

@Service("reply4UserJoinLevelApproveService")
public class Reply4UserJoinLevelApproveServiceImpl extends BaseServiceImpl<Reply4UserJoinLevelApprove> implements Reply4UserJoinLevelApproveService {

	@Resource(name="reply4UserJoinLevelApproveDao")
	private Reply4UserJoinLevelApproveDao  reply4UserJoinLevelApproveDao;
	
	@Override
	protected BaseDao<Reply4UserJoinLevelApprove> getBaseDao() {
		return reply4UserJoinLevelApproveDao;
	}

	// =========================个性化方法=====================
}
