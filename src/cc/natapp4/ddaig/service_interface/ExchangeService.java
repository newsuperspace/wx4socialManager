package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Exchange;

public interface ExchangeService extends BaseService<Exchange> {

	/**
	 * 根据用户的uid，查找该用户的所有积分兑换记录
	 * @param uid
	 * @return
	 */
	List<Exchange> getExchangesByUid(String uid);
	
	/**
	 * 根据用户的openid，朝朝该用户的所欲积分兑换记录
	 * @param openid
	 * @return
	 */
	List<Exchange> getExchangesByOpenid(String openid);
}
