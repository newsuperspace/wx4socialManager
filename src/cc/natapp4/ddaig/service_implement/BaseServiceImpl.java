package cc.natapp4.ddaig.service_implement;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.service_interface.BaseService;

/*
 * @Transactional 这个注解非常重要 ★★★ 时间长了哦竟然不记得这么重要的部分了，以至于在JUnit单元测试的时候出现org.springframework.dao.InvalidDataAccessApiUsageException: Write operations异常
 * 关于声明式事务处理是基于Spring的AOP面向切面技术实现的
 * 它可以帮哦们自动管理数据库非只读操作的事务管理工作，只需要先在Spring的配置file——applicationContext.xml
 * 中预先写入“基于注解扫描器的声明式事务处理的启动配置”，之后只需要在工程中任意涉及数据库CRUD操作的
 * 方法或整个类上添加@Transaction注解，则该方法或该类中的所有成员函数再被调用的时候就会执行自动化的声明式事务处理功能了。
 * 
 * 需要注意的是，如果没有在Web.xml中添加openSessionInViewFilter 这个拦截器，让Spring来统一管理数据库操作的session的话
 * 那么当注解有@Transactional的方法被执行的时候会通过session立刻开启事务，然后在方法执行完成后立刻调用session.close()关闭
 * 事务，因此如果所获取的"持久化状态对象"中存在"延迟加载"的数据部分，由于session已经被关闭，则这部分数据也就不能获取到了。
 * 解决这个question的方法就是通过openSessionInViewFilter过滤器，将事务统一由Spring管理，以保证只有在确实不在需要Access数据库的时候才关闭session并提交
 * 事务。
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected  abstract BaseDao<T>   getBaseDao();
	
	@Override
	public T queryEntityById(Serializable id) {
		return (T) this.getBaseDao().queryEntityById(id);
	}

	@Override
	public List<T> queryEntities() {
		return this.getBaseDao().queryEntities();
	}

	@Override
	public void save(T t) {
		this.getBaseDao().save(t);
	}

	@Override
	public void update(T t) {
		this.getBaseDao().update(t);
	}

	@Override
	public void delete(T t) {
		this.getBaseDao().delete(t);
	}

	@Override
	public void deleteById(Serializable id) {
		T t = this.queryEntityById(id);
		this.delete(t);
	}
	
	@Override
	public void clearSession() {
		this.getBaseDao().clearSession();
	}
	
}
