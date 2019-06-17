package cc.natapp4.ddaig.dao_implement;


import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.BaseSettingDao;
import cc.natapp4.ddaig.domain.setting.BaseSetting;
import cc.natapp4.ddaig.utils.ConfigUtils;


@Repository(value="baseSettingDao")
public class BaseSettingDaoImpl extends BaseDaoImpl<BaseSetting> implements BaseSettingDao {

	@Autowired
	@Qualifier(value="hibernateTemplate")
	private HibernateTemplate template;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.template;
	}

	// ============================【数据库CRUD操作开始】==================================
	@Override
	public BaseSetting getBaseSettingConfigByTagAndLid(String tag, String lid) {

		BaseSetting  bs  = null;
		List<BaseSetting> settings = (List<BaseSetting>)template.find("from BaseSetting bs where bs.tag=? and bs.lid=?", tag,lid);
		if(null!=settings && 0!=settings.size()){
			// 如果存在，则找到它直接返回
			bs = settings.get(0);
		}else{
			// 如果不存在配置项，则根据“settings/baseSetting.properties”中的默认配置创建一份数据
			bs  =  this.getOneDefaultBaseSetting(tag, lid);
		}
		return bs;
	}

	
	@Override
	public void defaultConfig(String tag, String lid) {
		BaseSetting  bs  = null;
		List<BaseSetting> settings = (List<BaseSetting>)template.find("from BaseSetting bs where bs.tag=? and bs.lid=?", tag,lid);
		if(null!=settings && 0!=settings.size()){
			// 如果存在，则找到它直接返回
			bs = settings.get(0);
			// 执行初始化过程
			Properties p = ConfigUtils.getProperties("settings/baseSetting.properties");
			bs.setNeedJoinApply(p.getProperty("needJoinApply"));
			this.update(bs);
		}else{
			this.getOneDefaultBaseSetting(tag, lid);
		}
	}
	
	
	/**
	 * 私有工具类，用于在数据库中创建默认配置数据项
	 * @return
	 */
	private  BaseSetting getOneDefaultBaseSetting(String tag, String lid){
		Properties p = ConfigUtils.getProperties("settings/baseSetting.properties");
		BaseSetting bs = new BaseSetting();
		bs.setLid(lid);
		bs.setTag(tag);
		bs.setNeedJoinApply(p.getProperty("needJoinApply"));
		// TODO 后续增加的其他配置信息....
		
		// 向数据库中保存新建的配置数据
		this.save(bs);
		return bs;
	}
	

}
