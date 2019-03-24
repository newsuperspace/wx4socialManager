package cc.natapp4.ddaig.bean;

import java.io.Serializable;
import java.util.List;

import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

/**
 * 
 * 当userList.jsp页面加载成功后，基于$(functon(){//...}); 实现一些初始化操作
 * 通过AJAX请求 userAction.initSelector() 方法，获取当前操作者层级对象
 * 将操作者层级的tag（admin、minusFirst、zero、first、second、third、fourth）以及操作者层级对象自身
 * 和当前操作者层级的直接子层级（不包括子孙层级）放入到本bean的对应属性位置上，返回给前端页面
 * 用来组织userList.jsp页面上，用来筛选用户的selectors操作面板（panel）
 * 
 * 
 * @author Administrator
 *
 */
public class GetData4UserListSelectors implements Serializable {

	/**
	 * 	版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 *  当前bean是前端在userModal.op.getData4Selector()中通过AJAX方法
	 *  向服务器端的userAction.getData4Selector()获取的，
	 *  当前result属性用于前端验证服务器端处理结果是否正常，
	 *  true为正常，开展后续工作
	 *  false为不正常，只需要通过alert()向前端用户展示message信息即可
	 */
	private  boolean result;
	private  String message;
	
	/*
	 * 基于tag属性，定位下列这些属性
	 * 用于组建子selector的必要数据信息
	 */
	private List<MinusFirstLevel> misnusFirstLevels;
	private List<ZeroLevel> zeroLevels;
	private List<FirstLevel> firstLevels;
	private List<SecondLevel> secondLevels;
	private List<ThirdLevel> thirdLevels;
	private List<FourthLevel> fourthLevels;

	/*
	 *  在userList.jsp页面上显示的用户信息
	 */
	private List<User> users;
	
	/*
	 * TODO 分页查询
	 * 这里是重新组织前端页面用户数据分页查询的必要参数
	 */
	
	
	//======================SETTER/GETTER=====================
	public List<MinusFirstLevel> getMisnusFirstLevels() {
		return misnusFirstLevels;
	}
	public void setMisnusFirstLevels(List<MinusFirstLevel> misnusFirstLevels) {
		this.misnusFirstLevels = misnusFirstLevels;
	}
	public List<ZeroLevel> getZeroLevels() {
		return zeroLevels;
	}
	public void setZeroLevels(List<ZeroLevel> zeroLevels) {
		this.zeroLevels = zeroLevels;
	}
	public List<FirstLevel> getFirstLevels() {
		return firstLevels;
	}
	public void setFirstLevels(List<FirstLevel> firstLevels) {
		this.firstLevels = firstLevels;
	}
	public List<SecondLevel> getSecondLevels() {
		return secondLevels;
	}
	public void setSecondLevels(List<SecondLevel> secondLevels) {
		this.secondLevels = secondLevels;
	}
	public List<ThirdLevel> getThirdLevels() {
		return thirdLevels;
	}
	public void setThirdLevels(List<ThirdLevel> thirdLevels) {
		this.thirdLevels = thirdLevels;
	}
	public List<FourthLevel> getFourthLevels() {
		return fourthLevels;
	}
	public void setFourthLevels(List<FourthLevel> fourthLevels) {
		this.fourthLevels = fourthLevels;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	
	
}
