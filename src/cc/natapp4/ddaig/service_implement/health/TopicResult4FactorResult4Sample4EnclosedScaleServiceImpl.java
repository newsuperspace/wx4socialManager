package cc.natapp4.ddaig.service_implement.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.TopicResult4FactorResult4Sample4EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.TopicResult4FactorResult4Sample4EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.TopicResult4FactorResult4Sample4EnclosedScaleService;

@Service("topicResult4FactorResult4Sample4EnclosedScaleService")
public class TopicResult4FactorResult4Sample4EnclosedScaleServiceImpl
		extends BaseServiceImpl<TopicResult4FactorResult4Sample4EnclosedScale>
		implements TopicResult4FactorResult4Sample4EnclosedScaleService {

	@Autowired
	private TopicResult4FactorResult4Sample4EnclosedScaleDao topicResult4FactorResult4Sample4EnclosedScaleDao;
	
	@Override
	protected BaseDao<TopicResult4FactorResult4Sample4EnclosedScale> getBaseDao() {
		return this.topicResult4FactorResult4Sample4EnclosedScaleDao;
	}
	
	// ===========个性化方法==========

}
