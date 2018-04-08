package cc.natapp4.ddaig.service_interface;

import cc.natapp4.ddaig.domain.Grouping;

public interface GroupingService extends BaseService<Grouping> {

	public  Grouping queryByTagName(String  tag);
	
	
}
