package cc.natapp4.ddaig.dao_interface;

import cc.natapp4.ddaig.domain.Grouping;

public interface GroupingDao extends BaseDao<Grouping>{

	public Grouping  queryByTagName(String tag);
		
	
}
