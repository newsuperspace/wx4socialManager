package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;

public interface UserDao extends BaseDao<User> {

	/**
	 * 根据用户名获取指定对象
	 * 
	 * @param username
	 * @return
	 */
	public User queryByUsername(String username);

	/**
	 * 根据用户的电话（电话号码具有唯一性，是系统中判定用户唯一性的唯一证明，因此每个用户有且只有绑定一个电话号码）
	 * 
	 * @param phoneNum User.phone 中记录的用户的电话号码
	 * @return
	 */
	public User queryByPhone(String phoneNum);

	/**
	 * 个性化方法 通过openID查找用户
	 * 
	 * @return User对象
	 */
	public User queryByOpenId(String openID);

	/**
	 * 查询出特定分组的用户 当前分组包括： community_user 社区管理者 common_user 普通用户 no_real_name_user
	 * 未实名认证用户
	 * 
	 * @return
	 */
	public List<User> queryByTagName(String tagName);

	/**
	 * 【分页查询-前置】 [供给像同类中的getAllLevelUsersCount 内部调用]
	 * 获取指定层级对象管理之下的所有用户数据，即其“直辖”和“非直辖”的总和
	 * 
	 * @param tag 待查的目标层级的标记，可选用的是admin、minus_first、zero、first、second、third、fourth、
	 * @param lid 待查的目标层级的ID
	 * @return
	 */
	public List<User> getAllLevelUsers(String tag, String lid);

	/**
	 * 【分页查询-前置】 [内部调用 getAllLevelUsers来实现本方法功能] 获取指定层级管理之下的所有用户（包括直辖和非直辖）的总人数
	 * 
	 * @param tag 目标层级的tag（admin、minus_first、zero、first、second、third、fourth）
	 * @param lid 目标层级的ID
	 * @return
	 */
	public long getAllLevelUsersCount(String tag, String lid);

	/**
	 * 【分页查询】 对指定层级（通过tage和lid确定具体层级对象）管理层之下的所有用户（包括直辖和非直辖）的人员进行分页查询（通过页码和单页数据量）
	 * 
	 * @param targetTag        获取哪个层级（标签）的用户
	 * @param targetLid        获取哪个层级（主键ID）的用户
	 * @param targetPageNum    目标页码
	 * @param pageItemNumLimit 每页显示的数据条目数
	 * @return
	 */
	public List<User> getAllLevelUsersByPage(String targetTag, String targetLid, int targetPageNum,
			int pageItemNumLimit);

	/**
	 * 用于获取目标层级的全部直辖人员（仅用于被getSelfLevelUsersCount()调用，负责统计人数之用）
	 * 
	 * @param targetLevelTag 目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId  目标层级的主键ID
	 * @param groupTag       目标层级的人员分类标签grouping.tag, 如果为all或其他字段，表明要查询所有
	 * @return
	 */
	public List<User> getSelfLevelUsers(String targetLevelTag, String targetLevelId, String groupTag);

	/**
	 * 基于getSelfLevelUsers()方法的查询结果，获取数据总条目数，用于前端基于LayUI的分页查询初始化组件之用
	 * 
	 * @param targetLevelTag 目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId  目标层级的主键ID
	 * @param groupTag       目标层级的人员分类标签grouping.tag, 如果为空（null）表明要查询所有
	 * @return
	 */
	public long getSelfLevelUsersCount(String targetLevelTag, String targetLevelId, String groupTag);

	/**
	 * 目标层级直辖人员的分页查询逻辑入口。
	 * 
	 * @param targetLevelTag   目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId    目标层级的主键ID
	 * @param targetPageNum    分页查询的目标页码
	 * @param pageItemNumLimit 分页查询的单页数据条目数
	 * @param groupTag         目标层级的人员分类标签grouping.tag, 如果为all或其他字段，表明要查询所有
	 * @return
	 */
	public List<User> getSelfLevelUsersByPage(String targetLevelTag, String targetLevelId, int targetPageNum,
			int pageItemNumLimit, String groupTag);

	/**
	 * 用于获取目标层级的全部非直辖人员（仅用于被getSelfLevelUsersCount()调用，负责统计人数之用）
	 * 
	 * @param targetLevelTag 目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId  目标层级的主键ID
	 * @return
	 */
	public List<User> getChildrenLevelUsers(String targetLevelTag, String targetLevelId);

	/**
	 * 基于getChildrenLevelUsers()方法的查询结果，获取数据总条目数，用于前端基于LayUI的分页查询初始化组件之用
	 * 
	 * @param targetLevelTag 目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId  目标层级的主键ID
	 * @return
	 */
	public long getChildrenLevelUsersCount(String targetLevelTag, String targetLevelId);

	/**
	 * 目标层级非直辖人员的分页查询逻辑入口。
	 * 
	 * @param targetLevelTag   目标层级的标签（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLevelId    目标层级的主键ID
	 * @param targetPageNum    分页查询的目标页码
	 * @param pageItemNumLimit 分页查询的单页数据条目数
	 * @return
	 */
	public List<User> getChildrenLevelUsersByPage(String targetLevelTag, String targetLevelId, int targetPageNum,
			int pageItemNumLimit);
}
