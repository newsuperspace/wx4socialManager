package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.Sample4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.Sample4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.Sample4EnclosedScaleService;

@Service("sample4EnclosedScaleService")
public class Sample4EnclosedScaleServiceImpl extends BaseServiceImpl<Sample4EnclosedScale>
		implements Sample4EnclosedScaleService {

	@Autowired
	private Sample4EnclosedScaleDao sample4EnclosedScaleDao;
	
	@Override
	protected BaseDao<Sample4EnclosedScale> getBaseDao() {
		return this.sample4EnclosedScaleDao;
	}
	
	// ===============个性化方法================

}
