package cc.natapp4.ddaig.dao_implement;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.WareDao;
import cc.natapp4.ddaig.domain.Ware;


@Repository("wareDao")
public class WareDaoImpl extends BaseDaoImpl<Ware> implements WareDao {

	/*
	 *  Spring的DI诸如的注解引用法，name属性指定向目标数据成员中DI注入哪个Bean（Spring容器中的Bean），就用applicationContext.xml
	 *  配置文件中声明的Bean的name来作为该属性的参数值
	 */
	@Resource(name="hibernateTemplate")
	public  HibernateTemplate  template;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.template;
	}

	@Override
	public List<Ware> queryEntitiesByZid(String zid) {

		List<Ware> list = (List<Ware>) template.find("from Ware w inner join fetch w.zeroLevel zl where zl.zid=?", zid);
		return list;
	}

}
