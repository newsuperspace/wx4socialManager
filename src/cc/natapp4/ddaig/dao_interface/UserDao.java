package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.User;

public interface UserDao extends BaseDao<User>{

	/**
	 * 个性化方法
	 * 通过openID查找用户
	 * @return  User对象
	 */
	public User queryByOpenId(String openID);
	
	/**
	 * 查询出特定分组的用户
	 * 当前分组包括：
	 * community_user   社区管理者
	 * common_user   普通用户
	 * no_real_name_user  未实名认证用户
	 * @return
	 */
	public List<User>  queryByTagName(String tagName);
	
	/**
	 * 不同于Manager类的同名方法只能获取已经分配到层级对象的管理者
	 * 当前类的getManagers（）方法将会获取到tag为层级对象（minuse_first/zero/first/second/third/fourth）
	 * 的user，不论T是否已经被委派了层级对象。
	 * @param tag    希望查找的tagName
	 * @return
	 */
	public List<User> getManagers(String tag);
	
	/**
	 * 根据用户名获取指定对象
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
	
	
}
