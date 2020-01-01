package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.User;

public class ReturnMessage4CountandCreateFirstPage implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	
	private List<User> users;
	private long count;
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
}
