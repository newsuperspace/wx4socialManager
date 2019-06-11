package cc.natapp4.ddaig.dao_implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.BaseSettingDao;
import cc.natapp4.ddaig.domain.setting.BaseSetting;


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

	

}
