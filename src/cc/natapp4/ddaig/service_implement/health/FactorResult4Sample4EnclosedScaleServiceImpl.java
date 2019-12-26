package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.FactorResult4Sample4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.FactorResult4Sample4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.FactorResult4Sample4EnclosedScaleService;

@Service("factorResult4Sample4EnclosedScaleService")
public class FactorResult4Sample4EnclosedScaleServiceImpl extends BaseServiceImpl<FactorResult4Sample4EnclosedScale>
		implements FactorResult4Sample4EnclosedScaleService {

	@Autowired
	private FactorResult4Sample4EnclosedScaleDao factorResult4Sample4EnclosedScaleDao;
	
	@Override
	protected BaseDao<FactorResult4Sample4EnclosedScale> getBaseDao() {
		return this.factorResult4Sample4EnclosedScaleDao;
	}
	
	// ====================个性化方法====================
	

}
