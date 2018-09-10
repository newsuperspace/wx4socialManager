package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.HouseDao;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.service_interface.HouseService;

@Service("houseService")
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

	@Resource(name="houseDao")
	private HouseDao  dao;
	
	@Override
	protected BaseDao<House> getBaseDao() {
		return dao;
	}
	

}
