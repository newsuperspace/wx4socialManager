package cc.natapp4.ddaig.dao_implement.health;


import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_implement.BaseDaoImpl;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.EnclosedScale;

@Repository("enclosedScaleDao")
public class EnclosedScaleDaoImpl extends BaseDaoImpl<EnclosedScale> implements EnclosedScaleDao {

	/*
	 * Spring的DI诸如的注解引用法，name属性指定向目标数据成员中DI注入哪个Bean（Spring容器中的Bean），
	 * 就用applicationContext.xml 配置文件中声明的Bean的name来作为该属性的参数值
	 */
	@Resource(name = "hibernateTemplate")
	public HibernateTemplate template;
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.template;
	}

	// =======================================Dao方法====================================


}
