package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Manager;

public interface ManagerService extends BaseService<Manager> {
	
	/**
	 * 只适用于筛选已经与某个层级对象进行绑定的Manager，而如果仅仅是修改了User.grouping.tag为某一层级的标签的话，
	 * 由于此时user.manager = null 因此是查不到的，这种情况下只能通过user来主导查询而不能以manager为主导。
	 * @param tag
	 * @return
	 */
	public List<Manager> getManagers(String tag);
}
