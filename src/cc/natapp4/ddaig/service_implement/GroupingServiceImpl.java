package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.GroupingDao;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.service_interface.GroupingService;

@Service("groupingService")
public class GroupingServiceImpl extends BaseServiceImpl<Grouping> implements GroupingService {

	@Resource(name="groupingDao")
	private GroupingDao  dao;
	
	@Override
	protected BaseDao<Grouping> getBaseDao() {
		return dao;
	}

	@Override
	public Grouping queryByTagName(String tag) {

		Grouping grouping = dao.queryByTagName(tag);
		
		return grouping;
	}

}
