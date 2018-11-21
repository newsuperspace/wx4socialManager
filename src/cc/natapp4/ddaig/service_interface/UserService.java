package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;

public interface UserService extends BaseService<User> {

	/**
	 * 当前UserService接口 对应UserDao接口 的个性化方法
	 * 用于通过openID 从数据库中查找用户
	 * @param openID  目标用户在公众平台的openID值
	 * @return  找到用户返回该用户的User对象，否则返回null
	 */
	public User queryByOpenId(String openID);
	
	public void checkRealName(String openID, String username, String sex, String birth, String phone) throws WeixinExceptionWhenCheckRealName  ;
	
	/**
	 * 微信初始化阶段调用的新建用户方法
	 * @param t
	 */
	public void saveInInit(User t);
	
	public void batchCreateUserQR();
	
	public List<User> queryByTagName(String tagName);
	
	/**
	 * 与getChildrenLevelUsers()方法不同，本方法是用来获取当前操作者层级的直辖人员（由于这些人员可以为胜任为管理者因此
	 * 方法名叫getManagers()有获取潜在管理者之意），通过传输不同grouping.tag的参数，还可以进一步精细化从所有直辖人员中
	 * 获取特定tag标签（unreal、common以及各个层级标签包括：minus_first、zero、first、second、third、fourth）的人员对象
	 * @param tag  你所要寻找的指定用户的member.grouping.tag
	 * @param levelTag   从Action层传递过来的，存放在HttpSession中的，当前操作者的层级标签（也是minus_first、zero、first、second、third、fourth）
	 * @param lid			从Action层传递过来的，存放在HttpSession中的，当前操作者层级的lid主键
	 * @return
	 */
	public List<Member> getManagers(String tag, String levelTag, String lid);
	
	
	/**
	 * 获取某一层级对象的子孙层级所管辖的用户，不包含非直辖用户
	 * 本方法与getManager相对
	 * @param tag   操作者层级的grouping.tag (minus_first、zero、first、second、third、fourth)
	 * @param lid    操作者层级的lid
	 * @return
	 */
	public List<User> getChildrenLevelUsers(String tag, String lid);
	
	
	/**
	 * 通过用户的username获取user对象
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
	
}
