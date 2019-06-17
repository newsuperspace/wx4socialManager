package cc.natapp4.ddaig.service_implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.BaseSettingDao;
import cc.natapp4.ddaig.dao_interface.FirstLevelDao;
import cc.natapp4.ddaig.dao_interface.FourthLevelDao;
import cc.natapp4.ddaig.dao_interface.MinusFirstLevelDao;
import cc.natapp4.ddaig.dao_interface.SecondLevelDao;
import cc.natapp4.ddaig.dao_interface.ThirdLevelDao;
import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.domain.setting.BaseSetting;
import cc.natapp4.ddaig.service_interface.BaseSettingService;

@Service(value="baseSettingService")
public class BaseSettingServiceImpl extends BaseServiceImpl<BaseSetting> implements BaseSettingService {

	// ============================【DI注入列表】==================================
	@Autowired
	private BaseSettingDao baseSettingDao;
	// 所有层级Dao，用以从数据库中查找层级对象
	@Autowired
	private MinusFirstLevelDao  minusfirstLevelDao;
	@Autowired
	private ZeroLevelDao  zeroLevelDao;
	@Autowired
	private FirstLevelDao  firstLevelDao;
	@Autowired
	private SecondLevelDao secondLevelDao;
	@Autowired
	private ThirdLevelDao thirdLevelDao;
	@Autowired
	private FourthLevelDao fourthLevelDao;
	
	
	
	@Override
	protected BaseDao<BaseSetting> getBaseDao() {
		return this.baseSettingDao;
	}

	// ============================【业务逻辑开始】==================================
	@Override
	public BaseSetting getBaseSettingConfigByTagAndLid(String tag, String lid) {
		return baseSettingDao.getBaseSettingConfigByTagAndLid(tag, lid);
	}

	@Override
	public void defaultConfig(String tag, String lid) {
		baseSettingDao.defaultConfig(tag, lid);
	}

	
	
	

}
