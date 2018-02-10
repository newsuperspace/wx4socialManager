package cc.natapp4.ddaig.service_implement;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.ExchangeDao;
import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.service_interface.ExchangeService;

@Service("exchangeService")
public class ExchangeServiceImpl extends BaseServiceImpl<Exchange> implements ExchangeService {

	@Resource(name="exchangeDao")
	private ExchangeDao  dao;
	
	@Override
	protected BaseDao<Exchange> getBaseDao() {
		return dao;
	}
	

}
