package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.WareDao;
import cc.natapp4.ddaig.domain.Ware;
import cc.natapp4.ddaig.service_interface.WareService;

@Service("wareService")
public class WareServiceImpl extends BaseServiceImpl<Ware> implements WareService {

	@Resource(name="wareDao")
	private WareDao  dao;
	
	@Override
	protected BaseDao<Ware> getBaseDao() {
		return dao;
	}
	

}
