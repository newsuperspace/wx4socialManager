package cc.natapp4.ddaig.dao_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
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
	 * 用于获取当前操作者层级对象直辖人员，并且可以通过传入（minus_first、zero、first、second、third、fourth、common、unreal）
	 * 实现特定管理层人员的查询
	 * 
	 * 例如当前操作者是minus_first层级，当他查找自己的直属人员时可以通过传入
	 * zero、common 或者all 来分类查找
	 * 再例如当前操作者是admin，当他查找直属人员的黑兽可以通过传入
	 * minus_first  查找街道层级管理者
	 * common 已认证的普通用户
	 * unreal   未认证的普通用户
	 * all   所有用户  
	 * 
	 * @param tag  希望查找的目标tag (common、unreal等)
	 * @param levelTag   操作者的层级tag（minus_first、zero、first、second、third、fourth）
	 * @param lid			操作者的层级ID
	 * @return
	 */
	public List<Member> getManagers(String tag, String levelTag, String lid);
	
	/**
	 * 获取某一层级对象的子孙层级所管辖的用户，但不包括当前层级的直辖用户
	 * 本方法与getManager相对
	 * @param tag   操作者层级的grouping.tag (minus_first、zero、first、second、third、fourth)
	 * @param lid    操作者层级的lid
	 * @return
	 */
	public List<User> getChildrenLevelUsers(String tag, String lid);
	
	
	/**
	 * 获取指定层级对象管理之下的所有用户数据，即其“直辖”和“非直辖”的总和
	 * 
	 * @param tag 待查的目标层级的标记，可选用的是minus_first、zero、first、second、third、fourth、
	 * @param lid	待查的目标层级的ID
	 * @return
	 */
	public List<User> getAllLevelUsers(String tag, String lid);
	
	
	/**
	 * 获取指定层级管理之下的所有用户（包括直辖和非直辖）的总人数
	 * @param tag
	 * @param lid
	 * @return
	 */
	public long getAllLevelUsersCount(String tag,String lid);
	
	/**
	 * 对当前管理层之下的所有用户（包括直辖和非直辖）的人员进行分页查询
	 * @param tag  操作者层级
	 * @param lid   操作者层级的id
	 * @param currentPageNum  分页查询的页码（从1开始，该值应由前端传入）
	 * @param limit    每页的数据数目（该值应由前端传入）
	 * @return
	 */
	public List<User> getAllLevelUsersByPage(String tag, String lid, int targetPageNum, int limit);
	
	
	
	
	/**
	 * 根据用户名获取指定对象
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
	
	
	/**
	 * 根据用户的电话（电话号码具有唯一性，是系统中判定用户唯一性的唯一证明，因此每个用户有且只有绑定一个电话号码）
	 * @param phoneNum   User.phone 中记录的用户的电话号码
	 * @return
	 */
	public User  queryByPhone(String phoneNum);

	List<User> getAllLevelUsersByPage();
	
}
