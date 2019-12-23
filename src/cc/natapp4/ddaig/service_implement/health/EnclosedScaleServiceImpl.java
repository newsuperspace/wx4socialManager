package cc.natapp4.ddaig.service_implement.health;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.HouseDao;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.health.EnclosedScaleService;

@Service("enclosedScaleService")
public class EnclosedScaleServiceImpl extends BaseServiceImpl<EnclosedScale> implements EnclosedScaleService {

	@Resource(name="houseDao")
	private EnclosedScaleDao  dao;
	
	@Override
	protected BaseDao<EnclosedScale> getBaseDao() {
		return dao;
	}
	

}
