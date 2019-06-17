package cc.natapp4.ddaig.dao_interface;

import cc.natapp4.ddaig.domain.setting.BaseSetting;

public interface BaseSettingDao extends BaseDao<BaseSetting> {

	/**
	 * 以tag字段和lid字段，从数据库表——basesetting中查找目标数据条目
	 * @param tag  该配置数据所属层级的级别（minus_first、zero、first、second、third、fourth）
	 * @param lid	 该配置数据所属层级的主键ID
	 * @return
	 */
	public BaseSetting getBaseSettingConfigByTagAndLid(String tag, String lid);
	
	/**
	 * 初始化设置
	 * @param tag
	 * @param lid
	 */
	public void defaultConfig(String tag,String lid);
	
}
