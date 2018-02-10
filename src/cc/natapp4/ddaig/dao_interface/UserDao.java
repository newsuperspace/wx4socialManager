package cc.natapp4.ddaig.dao_interface;

import java.util.List;

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
}
