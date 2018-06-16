package cc.natapp4.ddaig.service_interface;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

	/**
	 * 查询数据
	 * @param id   主键ID
	 * @return   持久化状态对象
	 */
	public T queryEntityById(Serializable id);
	
	/**
	 * 查询数据库表中的全部数据
	 * @return   以持久化状态对象为元素的容器对象
	 */
	public List<T> queryEntities();
	
	/**
	 * 新增数据
	 * @param t  封装有新增数据的持久化类实例
	 */
	public void save(T t);
	
	/**
	 * 更新数据
	 * @param t  持久化状态对象
	 */
	public void update(T t);
	
	/**
	 * 删除数据（不建议从数据库中删除数据）
	 * @param t   持久化状态对象
	 */
	public void  delete(T  t);
	
	/**
	 * 删除数据（当前工程中的所有数据库表的数据都不建议删除，because重用的可能性很大）
	 * @param id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * hibernate在每个session里都会做些处理，比如把查询过的对象缓存起来什么，这个时候这些，
	 * hibernate会记录session生命周期内所有缓存对象的操作过程，最后都会反映到数据库去，也就是所谓的托管状态，
	 * 所以才会有自动更新这种问题。只要每次都把查询到的对象用evict（或clear）清除（记得，是每次），
	 * 那么就不会有托管状态的entity，也就不会有自动更新，但这不会影响（应该）update（或saveOrUpdate）操作，
	 * evict只是清除实例与数据库的关联而已，不是清除实例本身。 
	 * 
	 * 清空Hibernate的session中所有二级缓存中的持久化状态对象的修改
	 */
	public void clearSession();
}
