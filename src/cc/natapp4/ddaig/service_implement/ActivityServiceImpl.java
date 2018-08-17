package cc.natapp4.ddaig.service_implement;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cc.natapp4.ddaig.dao_interface.ActivityDao;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.service_interface.ActivityService;

@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService {

	@Resource(name="activityDao")
	private ActivityDao  dao;
	
	@Override
	protected BaseDao<Activity> getBaseDao() {
		return dao;
	}

	@Override
	public List<Activity> getCanJoinActivityList(String openid) {
		dao.getCanJoinActivityList(openid);
		return null;
	}

	@Override
	public List<Activity> getJoiningActivityList(String openid) {
		dao.getJoiningActivityList(openid);
		return null;
	}

	@Override
	public List<Activity> getJoinedActivityList(String openid) {
		dao.getJoinedActivityList(openid);
		return null;
	}

	
}
