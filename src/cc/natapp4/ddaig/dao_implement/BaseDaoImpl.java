package cc.natapp4.ddaig.dao_implement;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import cc.natapp4.ddaig.dao_interface.BaseDao;

/*
 * TODO 在Junit测试Dao层的时候 需要开启这个注解，进行声明式事务处理，
 * 正常开发时这个注解是注在BaseServiceImpl上的。
 */
//@Transactional  
public abstract class BaseDaoImpl<T> implements BaseDao<T> {


	/**
	 * 该方法由各个个性化Dao个性化接口实现，从而让父类Dao（就是当前类的实现类）可以调用到HibernateTemplate，从而实现数据库的通用操作部分的逻辑
	 * @return
	 */
	public abstract HibernateTemplate  getHibernateTemplate();
	// 存放实际类型参数，也就是T到底是什么类型
	private  Class  tClazz;   
	// 构造器（默认）
	public  BaseDaoImpl(){
		/*
		 *  得到继承当前类BaseDaoImpl的子类的class对象，注意在继承链条中热河位置的this指的都是子类实例。
		 *  如果想得到当前父类的实例，应该使用“父类名.this”的形式才能引用到当前所在父类的实例对象
		 */
		Class<? extends BaseDaoImpl> class1 = this.getClass();  
		/*
		 * 通过子类的class对象的getGenericSuperclass()方法可以得当父类，也就是当前类的class对象。
		 * 由于所有泛型类型都必须实现ParameterizedType接口，并实现其中的三个方法，因此我们这个父类也自然就能强制转换为
		 * ParameterizeType类型对象，该对象的三个方法中getAcutalTypeArguments()就能得到所有参数化类型的class对象了
		 */
		ParameterizedType  type  =  (ParameterizedType)class1.getGenericSuperclass();   
		Type[] types = type.getActualTypeArguments();
		this.tClazz  =  (Class) types[0];
	}
	
	
	@Override
	public T queryEntityById(Serializable id) {
		return (T)this.getHibernateTemplate().get(tClazz, id);
	}

	@Override
	public List<T> queryEntities() {
		return (List<T>)this.getHibernateTemplate().find("from "+this.tClazz.getName());
	}

	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}
	
	@Override
	public void clearSession() {
		this.getHibernateTemplate().clear();
	}
}
