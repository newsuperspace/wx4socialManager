package cc.natapp4.ddaig.service_implement;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.ExchangeDao;
import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.service_interface.ExchangeService;

@Service("exchangeService")
public class ExchangeServiceImpl extends BaseServiceImpl<Exchange> implements ExchangeService {

	@Resource(name="exchangeDao")
	private ExchangeDao  exchangeDao;
	
	@Override
	protected BaseDao<Exchange> getBaseDao() {
		return this.exchangeDao;
	}

	@Override
	public List<Exchange> getExchangesByUid(String uid) {
		return this.exchangeDao.getExchangesByUid(uid);
	}

	@Override
	public List<Exchange> getExchangesByOpenid(String openid) {
		return this.exchangeDao.getExchangesByOpenid(openid);
	}
	

}
