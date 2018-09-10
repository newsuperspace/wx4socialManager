package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.GeographicDao;
import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.service_interface.GeographicService;

@Service("geographicService")
public class GeographicServiceImpl extends BaseServiceImpl<Geographic> implements GeographicService {

	@Resource(name="geographicDao")
	private GeographicDao  dao;
	
	@Override
	protected BaseDao<Geographic> getBaseDao() {
		return dao;
	}
	

}
