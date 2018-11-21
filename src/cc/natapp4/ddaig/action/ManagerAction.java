package cc.natapp4.ddaig.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

@Controller("managerAction")
@Scope("prototype")
@Lazy(true)
public class ManagerAction extends ActionSupport implements ModelDriven<Manager> {

	@Resource(name="userService")
	private UserService  userService;
	@Resource(name="memberService")
	private MemberService  memberService;
	@Resource(name="minusFirstLevelService")
	private	MinusFirstLevelService minusFirstLevelService;
	@Resource(name="zeroLevelService")
	private	ZeroLevelService zeroLevelService;
	@Resource(name="firstLevelService")
	private	FirstLevelService firstLevelService;
	@Resource(name="secondLevelService")
	private	SecondLevelService secondLevelService;
	@Resource(name="thirdLevelService")
	private	ThirdLevelService thirdLevelService;
	@Resource(name="fourthLevelService")
	private	FourthLevelService fourthLevelService;
	
	// =======================模型驱动===================
	private Manager  manager =  null;
	@Override
	public Manager getModel() {
		this.manager =  new Manager();
		return this.manager;
	}
	// =======================属性驱动===================
	/*
	 * 从managerList.jsp页面中，通过调用myJS.managerModal.op.toManagedLevelList（）
	 * 传递过来的请求参与，用来告知需要获取该member成员对象之下的所有manager列表
	 */
	private  int memberid;
	public int getMemberid() {
		return memberid;
	}
	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}
	
	/*
	 * 接收来自myJS→managerModal.toManagersOfLevel()方法传递过来的来自minus_first.jsp/zero.jsp/first.jsp/second.jsp/thrid.jsp/fourth.jsp
	 * 上点击某个层级对象的管理者后以请求参数传递过来的层级对象的levelLid和对应的tag（minus_first、zero、first、second、third、fourth）
	 */
	private String tag;
	private String levelLid;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLevelLid() {
		return levelLid;
	}
	public void setLevelLid(String levelLid) {
		this.levelLid = levelLid;
	}
	// ======================ACTIONs====================
	/**
	 * 根据前端传递过来的memberid  跳转到用于显示该用户所管理的当前操作者层级
	 * @return
	 */
	public  String  toManagedLevelList(){
		Member member = memberService.queryEntityById(this.memberid);
		List<Manager> managers = member.getManagers();
		
		ActionContext.getContext().put("username", member.getUser().getUsername());
		ActionContext.getContext().put("managers", managers);
		return "managedLevelList";
	}
	
	
	/**
	 * 根据前端传递过来的managerid
	 * @return
	 */
	public String  toManagersOfLevel(){
		String  tag =  this.tag;
		String levelLid = this.levelLid;
		
		List<Manager>  managers  =  null;
		
		String  managerName = "";
		
		switch (tag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(levelLid);
			managers = minusFirstLevel.getManagers();
			managerName  =  minusFirstLevel.getName();
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(levelLid);
			managers = zeroLevel.getManagers();
			managerName = zeroLevel.getName();
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(levelLid);
			managers = firstLevel.getManagers();
			managerName = firstLevel.getName();
			break;
		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(levelLid);
			managers = secondLevel.getManagers();
			managerName = secondLevel.getName();
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(levelLid);
			managers = thirdLevel.getManagers();
			managerName = thirdLevel.getName();
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(levelLid);
			managers = fourthLevel.getManagers();
			managerName = fourthLevel.getName();
			break;
		}
		
		ActionContext.getContext().put("managerName", managerName);
		ActionContext.getContext().put("managers", managers);
		return "managersOfLevel";
	}
	
	
	
	
}
