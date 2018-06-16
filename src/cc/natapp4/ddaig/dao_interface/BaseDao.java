package cc.natapp4.ddaig.dao_interface;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

	/**
	 * 根据数据的主键ID查找该持久化状态对象
	 * @param id
	 * @return
	 */
	public  T  queryEntityById(Serializable  id);
	
	/**
	 * 查询数据库表中的所有数据
	 * @return  返回容器对象，容器元素为指定数据库表对应的持久化状态对象
	 */
	public  List<T>  queryEntities();
	
	/**
	 * 新增数据
	 * @param t
	 */
	public void  save(T  t);
	
	/**
	 * 更新数据
	 * @param t
	 */
	public  void  update(T t);
	
	/**
	 * 删除数据（不推荐删除数据）
	 * @param t
	 */
	public void  delete(T t);
	
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
