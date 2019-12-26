package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.Option4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Option4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.Option4EnclosedScaleService;

@Service("option4EnclosedScaleService")
public class Option4EnclosedScaleServiceImpl extends BaseServiceImpl<Option4EnclosedScale>
		implements Option4EnclosedScaleService {

	@Autowired
	private Option4EnclosedScaleDao option4EnclosedScaleDao;
	@Override
	protected BaseDao<Option4EnclosedScale> getBaseDao() {
		return this.option4EnclosedScaleDao;
	}
	// ==============个性化方法==============

}
