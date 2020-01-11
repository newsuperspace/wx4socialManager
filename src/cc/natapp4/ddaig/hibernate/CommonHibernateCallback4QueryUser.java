package cc.natapp4.ddaig.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import cc.natapp4.ddaig.domain.User;

public class CommonHibernateCallback4QueryUser implements HibernateCallback<List<User>> {

	private String hql; // HQL语句
	private Object[] params; // HQL中“？”占位符对应的参数

	public CommonHibernateCallback4QueryUser(String hql, Object[] params) {
		this.hql = hql;
		this.params = params;
	}

	@Override
	public List<User> doInHibernate(Session session) throws HibernateException {

		Query<User> query = session.createQuery(hql);
		
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		return query.list();
	}

}
