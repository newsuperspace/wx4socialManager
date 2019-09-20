package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Ware;

public interface WareDao extends BaseDao<Ware>{
	

	/**
	 * 根据社区层级的zid查找该社区创建的所有兑换奖品对象
	 * @param zid
	 * @return
	 */
	public List<Ware> queryEntitiesByZid(String zid);
	
	
}
