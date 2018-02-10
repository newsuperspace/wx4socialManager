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
	
}
