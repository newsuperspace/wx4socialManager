package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.Topic4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Topic4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.Topic4EnclosedScaleService;

@Service("topic4EnclosedScaleService")
public class Topic4EnclosedScaleServiceImpl extends BaseServiceImpl<Topic4EnclosedScale>
		implements Topic4EnclosedScaleService {

	@Autowired
	private Topic4EnclosedScaleDao topic4EnclosedScaleDao;
	@Override
	protected BaseDao<Topic4EnclosedScale> getBaseDao() {
		return this.topic4EnclosedScaleDao;
	}
	// =================个性化方法===================

}
