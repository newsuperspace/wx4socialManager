package cc.natapp4.ddaig.service_implement.health;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.EnclosedScaleService;

@Service("enclosedScaleService")
public class EnclosedScaleServiceImpl extends BaseServiceImpl<EnclosedScale> implements EnclosedScaleService {

	@Autowired
	private EnclosedScaleDao  enclosedScaleDao;
	
	@Override
	protected BaseDao<EnclosedScale> getBaseDao() {
		return this.enclosedScaleDao;
	}


}
