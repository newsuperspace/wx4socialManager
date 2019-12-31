package cc.natapp4.ddaig.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import cc.natapp4.ddaig.domain.User;

public class PageLimitHibernateCallback implements HibernateCallback<List<User>> {

	private String hql;
	private Object[] params;
	private int pageNumber; // 页面序号（从1开始）
	private int pageSize; // 每页的条目数量
	private int begin; // 数据库截取数据的头序号（从0开始）

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

		Query query = session.createQuery(hql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		query.setFirstResult(begin);
		query.setMaxResults(pageSize);

		return query.list();
	}

}
