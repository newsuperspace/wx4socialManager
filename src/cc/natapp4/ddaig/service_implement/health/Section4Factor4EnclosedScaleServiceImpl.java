package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.Section4Factor4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Section4Factor4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.Section4Factor4EnclosedScaleService;


@Service("section4Factor4EnclosedScaleService")
public class Section4Factor4EnclosedScaleServiceImpl extends BaseServiceImpl<Section4Factor4EnclosedScale>
		implements Section4Factor4EnclosedScaleService {

	@Autowired
	private Section4Factor4EnclosedScaleDao section4Factor4EnclosedScaleDao;
	@Override
	protected BaseDao<Section4Factor4EnclosedScale> getBaseDao() {
		return this.section4Factor4EnclosedScaleDao;
	}
	
	// =================个性化方法================

}
