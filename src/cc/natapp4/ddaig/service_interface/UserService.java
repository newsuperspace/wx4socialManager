package cc.natapp4.ddaig.service_interface;

import java.util.List;

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
}
