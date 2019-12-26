package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.OptionGroup4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.OptionGroup4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.OptionGroup4EnclosedScaleService;

@Service("optionGroup4EnclosedScaleService")
public class OptionGroup4EnclosedScaleServiceImpl extends BaseServiceImpl<OptionGroup4EnclosedScale>
		implements OptionGroup4EnclosedScaleService {

	@Autowired
	private OptionGroup4EnclosedScaleDao optionGroup4EnclosedScaleDao;
	@Override
	protected BaseDao<OptionGroup4EnclosedScale> getBaseDao() {
		return optionGroup4EnclosedScaleDao;
	}
	
	// =================个性化方法=================

}
