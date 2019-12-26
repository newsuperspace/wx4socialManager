package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.Factor4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Factor4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.Factor4EnclosedScaleService;

@Service("factor4EnclosedScaleService")
public class Factor4EnclosedScaleServiceImpl extends BaseServiceImpl<Factor4EnclosedScale>
		implements Factor4EnclosedScaleService {

	@Autowired
	private Factor4EnclosedScaleDao factor4EnclosedScaleDao;
	@Override
	protected BaseDao<Factor4EnclosedScale> getBaseDao() {
		return this.factor4EnclosedScaleDao;
	}

}
