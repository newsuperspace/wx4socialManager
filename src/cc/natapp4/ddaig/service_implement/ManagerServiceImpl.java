package cc.natapp4.ddaig.service_implement;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.ManagerDao;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.service_interface.ManagerService;

@Service("managerService")
public class ManagerServiceImpl extends BaseServiceImpl<Manager> implements ManagerService {

	@Resource(name="managerDao")
	private ManagerDao  managerDao;
	
	@Override
	protected BaseDao<Manager> getBaseDao() {
		return this.managerDao;
	}

	@Override
	public List<Manager> getManagers(String tag) {
		return managerDao.getManagers(tag);
	}
	
	
}
