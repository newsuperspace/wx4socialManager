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
}
