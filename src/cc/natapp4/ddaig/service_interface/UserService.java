package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage;
import cc.natapp4.ddaig.bean.health.ReturnMessage4InitSelector;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;

public interface UserService extends BaseService<User> {

	/**
	 * 通过用户的username获取user对象
	 * 
	 * @param username
	 * @return
	 */
	public User queryByUsername(String username);

	/**
	 * 当前UserService接口 对应UserDao接口 的个性化方法 用于通过openID 从数据库中查找用户
	 * 
	 * @param openID 目标用户在公众平台的openID值
	 * @return 找到用户返回该用户的User对象，否则返回null
	 */
	public User queryByOpenId(String openID);

	/**
	 * 根据用户的电话（电话号码具有唯一性，是系统中判定用户唯一性的唯一证明，因此每个用户有且只有绑定一个电话号码）
	 * 
	 * @param phoneNum User.phone 中记录的用户的电话号码
	 * @return
	 */
	public User queryByPhone(String phoneNum);

	public List<User> queryByTagName(String tagName);
	
	public void checkRealName(String openID, String username, String sex, String birth, String phone)
			throws WeixinExceptionWhenCheckRealName;


	
	public List<User> getManagers(String targetLevelTag, String targetLevelId, String groupTag);
	
	
	/**
	 * 【专门共给 userList.jsp 和 managerList.jsp 页面获取初始化分页查询数据之用】
	 * 
	 * @param targetTag				获取分页数据的目标层级的tag（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLid				获取分页数据的目标层级的lid
	 * @param targetPageNum			获取分页数据的页码数（默认为1）
	 * @param pageItemNumLimit		获取分页数据的单页数据量（默认为10）
	 * @param whereFrom				请求来源————userList（来自userList.jsp页面）和 managerList（来自managerList.jsp） 用于分支调用self系和children系的Dao方法
	 * @param groupTag				如果whereFrom为managerList，则这个参数用于表明进一步获取哪个group.tag 的群组数据
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage getCountandCreateFirstPage4InitLaypage(String targetTag,
			String targetLid, int targetPageNum, int pageItemNumLimit, String whereFrom, String groupTag);
	
	/**
	 * 【专门共给 userList.jsp 和 managerList.jsp 进行分页查询数据之用】
	 * 
	 * @param targetTag				获取分页数据的目标层级的tag（admin、minus_first、zero、first、second、third、fourth）
	 * @param targetLid				获取分页数据的目标层级的lid
	 * @param targetPageNum			获取分页数据的页码数（默认为1）
	 * @param pageItemNumLimit		获取分页数据的单页数据量（默认为10）
	 * @param whereFrom				请求来源————userList（来自userList.jsp页面）和 managerList（来自managerList.jsp） 用于分支调用self系和children系的Dao方法
	 * @param groupTag				如果whereFrom为managerList，则这个参数用于表明进一步获取哪个group.tag 的群组数据
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage getUsersByPageLimit(String targetTag,
			String targetLid, int targetPageNum, int pageItemNumLimit, String whereFrom, String groupTag);
	
	
	/**
	 * 【专门共给 userList.jsp 和 managerList.jsp 进行层级过滤器初始化之用（前端基于picker-extend.js）】
	 * 为当前层级操作者，在访问健康管理-被测者目录的时候，初始化用于过滤其不同子层级用户的selector数据，该selector是基于picker-extend.js实现的
	 * @param currenLevelTag		当前操作者层级的tag（admin、minus_first、zero、first、second、third、fourth）
	 * @param currentLevelId		当前操作者层级的lid主键ID
	 * @return
	 */
	public ReturnMessage4InitSelector initSelector(String currentLevelTag,String currentLevelId);
	
}
