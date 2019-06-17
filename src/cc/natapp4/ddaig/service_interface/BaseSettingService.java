package cc.natapp4.ddaig.service_interface;

import cc.natapp4.ddaig.domain.setting.BaseSetting;

public interface BaseSettingService extends BaseService<BaseSetting> {

	
	/**
	 * 获取某个层级对象的“基础设置数据”
	 * （1）基于层级对象的tag和lid在数据库中定位该层级对象
	 * （2）从该层级对象的持久化状态对象中获取到“一对一外键关联”的BaseSetting基础设置数据全在里了
	 * 
	 * @param tag 层级对象的tag，目前可用tag为 minus_first、zero、first、second、third、fourth、
	 * @param lid 层级对象的主键ID
	 * @return
	 */
	public BaseSetting getBaseSettingConfigByTagAndLid(String tag,String lid);		
		
	
	/**
	 * 初始化设置
	 * @param tag
	 * @param lid
	 */
	public void defaultConfig(String tag,String lid);
		
	
	
}
