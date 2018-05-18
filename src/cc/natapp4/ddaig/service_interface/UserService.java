package cc.natapp4.ddaig.service_interface;

import java.util.List;

import cc.natapp4.ddaig.domain.Manager;
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
	
	public void checkRealName(String openID, String username, String cardID,String address, String phone) throws WeixinExceptionWhenCheckRealName  ;

	public void saveInInit(User t);
	
	public void batchCreateUserQR();
	
	public List<User> queryByTagName(String tagName);
	
	/**
	 * 不同于Manager类的同名方法只能获取已经分配到层级对象的管理者
	 * 当前类的getManagers（）方法将会获取到tag为层级对象（minuse_first/zero/first/second/third/fourth）
	 * 的user，不论T是否已经被委派了层级对象。
	 * @param tag
	 * @return
	 */
	public List<User> getManagers(String tag);
	
	/**
	 * 通过用户的username获取user对象
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
	
}
