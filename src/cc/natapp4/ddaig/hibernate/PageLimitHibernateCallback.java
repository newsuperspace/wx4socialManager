package cc.natapp4.ddaig.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import cc.natapp4.ddaig.domain.User;

public class PageLimitHibernateCallback implements HibernateCallback<List<User>> {

	private String hql;       	// HQL语句
	private Object[] params;  	// HQL中“？”占位符对应的参数
	private int pageNumber; 	// 页面序号（从1开始）
	private int pageSize; 		// 每页的条目数量
	private int begin; 			// 基于目标页面的序号和每页的条目数量，计算出数据库截取数据的起始序号（从0开始）

	public PageLimitHibernateCallback(String hql, Object[] params, int pageNumber, int pageSize) {
		super();
		this.hql = hql;
		this.params = params;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.begin = (pageNumber - 1) * pageSize;
	}

	@Override
	public List<User> doInHibernate(Session session) throws HibernateException {
		// 通过Spring传入的线程绑定的session获取Hibernate原生查询对象
		Query<User> query = session.createQuery(hql);
		// 按？顺序遍历对应的查询参数给查询对象
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		// 设置分页查询的两个关键参数，数据起始位置和单次查询的数据量
		query.setFirstResult(begin);
		query.setMaxResults(pageSize);
		// 调用list（）的同时才开始正式执行查询并封装返回结果
		return query.list();
	}

}
