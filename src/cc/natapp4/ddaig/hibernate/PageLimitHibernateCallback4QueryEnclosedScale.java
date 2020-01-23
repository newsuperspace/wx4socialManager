package cc.natapp4.ddaig.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import cc.natapp4.ddaig.domain.health.EnclosedScale;

public class PageLimitHibernateCallback4QueryEnclosedScale implements HibernateCallback<List<EnclosedScale>> {

	private int firstResult;
	private int maxResult;
	private String hql;
	private Object[] params;

	public PageLimitHibernateCallback4QueryEnclosedScale(int targetPageNum, int pageItemNumLimit, String hql,
			Object[] params) {
		this.maxResult = pageItemNumLimit;
		this.firstResult = (targetPageNum - 1) * this.maxResult;
		this.hql = hql;
		this.params = params;
	}

	@Override
	public List<EnclosedScale> doInHibernate(Session session) throws HibernateException {

		Query<EnclosedScale> query = session.createQuery(hql);

		if (null != params)
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		return query.list();
	}

}
