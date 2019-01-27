package cc.natapp4.ddaig.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.domain.Approve4UserJoinLevel;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.Reply4UserJoinLevelApprove;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.UserApply4JoinLevel;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.Approve4UserJoinLevelService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.Reply4UserJoinLevelApproveService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserApply4JoinLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

/**
 * 专门用于处理 一系列请求的功能入口，具体请求处理功能如下：
 *  1、用户加入组织层级的申请 
 * （1）allList2UserApply4JoinLevel()—— 获取对当前层级对象的所有加入申请 
 * （2）agreeUserApply4JoinLevel() —— 同意用户的加入申请
 * （3）refuseUserApply4JoinLevel() —— 不同意用户加入的申请
 * 
 * 2、
 * 
 * 
 * 3、
 * 
 * 
 * @author Administrator
 *
 */
@Controller("approveAction")
@Scope("prototype")
@Lazy(true)
public class ApproveAction extends ActionSupport {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * =================================================================
	 * ============================Spring的DI注入============================
	 * =================================================================
	 */
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	@Resource(name="memberService")
	private MemberService memberService;
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;
	@Resource(name = "secondLevelService")
	private SecondLevelService secondLevelService;
	@Resource(name = "thirdLevelService")
	private ThirdLevelService thirdLevelService;
	@Resource(name = "fourthLevelService")
	private FourthLevelService fourthLevelService;
	@Resource(name = "userApply4JoinLevelService")
	private UserApply4JoinLevelService userApply4JoinLevelService;
	@Resource(name = "approve4UserJoinLevelService")
	private Approve4UserJoinLevelService approve4UserJoinLevelService;
	@Resource(name = "reply4UserJoinLevelApproveService")
	private Reply4UserJoinLevelApproveService reply4UserJoinLevelApproveService;
	@Resource(name="weixinService4Setting")
	private WeixinService4SettingImpl weixinService4Setting;
	
	/*
	 * =================================================================
	 * ==============================属性驱动==============================
	 * =================================================================
	 */
	
	/*
	 * 组织层级的标签tag，包括 minus_first、zero、first、second、third、fourth
	 * 配合lid可以准确定位目标层级对象
	 */
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/*
	 * 组织层级的主键ID，配合tag可以准确定位目标层级对象
	 */
	private String lid;

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	/*
	 * userApply4JoinLevel 用户申请加入某个组织层级的申请对象的主键ID
	 */
	private String ua4jlid;

	public String getUa4jlid() {
		return ua4jlid;
	}

	public void setUa4jlid(String ua4jlid) {
		this.ua4jlid = ua4jlid;
	}

	/*
	 * 存放层级管理者对各种apply（申请）的 approve(审批) 意见
	 */
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * =================================================================
	 * ==============================Methods==============================
	 * =================================================================
	 */
	/**
	 * 获取某一层级对象的所有“用户申请加入的申请”，包括 未审批、已通过和未通过的所有
	 * 
	 * @return
	 */
	public String allList2UserApply4JoinLevel() {
		/*
		 * 早在shiro认证阶段，我们的系统就已经将当前登陆系统的用户的相关信息（openid/username/password）
		 * 以及所登陆的层级的层级对象的必要信息（tag和lid）存放到了session域中
		 * 方便我们在整个系统的各个需要的部分随时获取，提高系统运行效率。
		 */
		// 获取当前层级的tag和lid
		tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		List<UserApply4JoinLevel> applies = userApply4JoinLevelService.getUserApplyList(tag, lid, -1);

		ActionContext.getContext().put("applies", applies);
		return "allListUserApply4JoinLevel";
	}

	/**
	 * AJAX 根据ua4jlid 获取具体的userApply4JoinLevel持久化状态对象,用来组织infoModal4UserLevelApply的显示
	 * @return
	 */
	public	String getInfo4UserLevelApply(){
		UserApply4JoinLevel apply = userApply4JoinLevelService.queryEntityById(this.ua4jlid);
		
		ActionContext.getContext().getValueStack().push(apply);
		return "json";
	}
	
	
	
	/**
	 * 在userApply4JoinLevel.jsp页面上，管理者点击，某条申请的“同意”按钮 同意用户加入组织的申请 :
	 * （1）在approve中生成reply,记录审核意见（到message字段）；更新apply.status状态；更新approve的审核时间等信息
	 * （2）执行申请人加入到本组织的业务逻辑 ；
	 * （3）通过微信端，向该用户发布通知信息；
	 * 
	 * @return
	 */
	public String agreeUserApply4JoinLevel() {
		ReturnMessage4Common  result =  new  ReturnMessage4Common();
		result.setMessage("处理成功！该用户已成功加入我们");
		result.setResult(true);
		
		// 获取申请对象
		UserApply4JoinLevel apply = userApply4JoinLevelService.queryEntityById(this.ua4jlid);
		// 获取申请人
		User user = apply.getUser();
		// 获取审批对象
		Approve4UserJoinLevel approve = apply.getApprove4UserJoinLevel();
		SimpleDateFormat  formatter  =  new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeStamp = System.currentTimeMillis();
		String timeStr  =  formatter.format(new Date(timeStamp));
		approve.setTimeStamp(timeStamp);
		approve.setTimeStr(timeStr);
		
		// (1)
		Reply4UserJoinLevelApprove  reply  =  new Reply4UserJoinLevelApprove();
		reply.setBeread(false);
		reply.setMessage(this.message);
		reply.setTimeStamp(timeStamp);
		reply.setTimeStr(timeStr);
		reply.setApprove4UserJoinLevel(approve);
		
		List<Reply4UserJoinLevelApprove> replies = approve.getReplies();
		if(null==replies){
			replies  =  new ArrayList<Reply4UserJoinLevelApprove>();
			approve.setReplies(replies);
		}
		replies.add(reply);
		
		apply.setStatus(2);
		apply.setBeread(false);
		
		reply4UserJoinLevelApproveService.save(reply);
		approve4UserJoinLevelService.update(approve);
		userApply4JoinLevelService.update(apply);
		
		// (2)
		// 获取到用户扫码加入的层级对象的 级别标签和lid
		tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 重新获取当前用户的member信息，然后显示在joiningLevelList.jsp页面上
		// 从当前用户的Servlet会话（session）中得到该用户的openid，该openid是accessPersonalCenter()方法在用户第一次通过微信端来访时通过code获取并放入到session域中的
		// String openid = (String)
		// ServletActionContext.getRequest().getSession().getAttribute("openid");
		// 从数据库中精准定位到要加入的层级对象
		String message4weixinRecall = "您申请加入的组织";
		Member member = null;
		switch (tag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			if (null == minusFirstLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				// 新建member
				member = new Member();
				// 与user建立关系
				member.setUser(user);
				user.getMembers().add(member);
				// 设置member的必要数据
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setMinusFirstLevel(minusFirstLevel);
				message4weixinRecall += "\""+minusFirstLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				member = new Member();
				member.setUser(user);
				user.getMembers().add(member);
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setZeroLevel(zeroLevel);
				member.setMinusFirstLevel(zeroLevel.getParent());
				message4weixinRecall += "\""+zeroLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			if (null == firstLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				member = new Member();
				member.setUser(user);
				user.getMembers().add(member);
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setFirstLevel(firstLevel);
				member.setZeroLevel(firstLevel.getParent());
				member.setMinusFirstLevel(firstLevel.getParent().getParent());
				message4weixinRecall += "\""+firstLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			if (null == secondLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				member = new Member();
				member.setUser(user);
				user.getMembers().add(member);
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setSecondLevel(secondLevel);
				member.setFirstLevel(secondLevel.getParent());
				member.setZeroLevel(secondLevel.getParent().getParent());
				member.setMinusFirstLevel(secondLevel.getParent().getParent().getParent());
				message4weixinRecall += "\""+secondLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			if (null == thirdLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				member = new Member();
				member.setUser(user);
				user.getMembers().add(member);
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setThirdLevel(thirdLevel);
				member.setSecondLevel(thirdLevel.getParent());
				member.setFirstLevel(thirdLevel.getParent().getParent());
				member.setZeroLevel(thirdLevel.getParent().getParent().getParent());
				member.setMinusFirstLevel(thirdLevel.getParent().getParent().getParent().getParent());
				message4weixinRecall += "\""+thirdLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			if (null == fourthLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				member = new Member();
				member.setUser(user);
				user.getMembers().add(member);
				member.setGrouping(groupingService.queryByTagName("common"));
				member.setFourthLevel(fourthLevel);
				member.setThirdLevel(fourthLevel.getParent());
				member.setSecondLevel(fourthLevel.getParent().getParent());
				member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
				member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
				member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
				message4weixinRecall += "\""+fourthLevel.getName()+"\""+"已通过您的申请，欢迎加入我们！感谢您的支持！";
			}
			break;
		}
		// 向数据库存储数据
		if (null != member) {
			memberService.save(member);
			userService.update(user);
		}
		
		// (3) 
		boolean theDuelResult = weixinService4Setting.sendTextMessage2One(user.getOpenid(), message4weixinRecall);
		if(theDuelResult){
			result.setMessage(result.getMessage()+",通知也已成功发送给用户。");
		}else{
			result.setMessage(result.getMessage()+",但通知未能发送给用户请与用户电话确定或通过公众号内置客服功能联系。");
		}
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 拒绝用户加入组织的申请
	 * （1）在approve中生成reply,记录审核意见（到message字段）；更新apply.status状态；
	 * （2）通过微信通知申请人处理结果；
	 * 
	 * @return
	 */
	public String refuseUserApply4JoinLevel() {

		ReturnMessage4Common  result =  new  ReturnMessage4Common();
		result.setMessage("处理完成！已拒绝该用户的申请");
		result.setResult(true);
		
		// 获取申请对象
		UserApply4JoinLevel apply = userApply4JoinLevelService.queryEntityById(this.ua4jlid);
		// 获取申请人
		User user = apply.getUser();
		// 获取审批对象
		Approve4UserJoinLevel approve = apply.getApprove4UserJoinLevel();
		SimpleDateFormat  formatter  =  new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeStamp = System.currentTimeMillis();
		String timeStr  =  formatter.format(new Date(timeStamp));
		approve.setTimeStamp(timeStamp);
		approve.setTimeStr(timeStr);
		
		// (1)
		Reply4UserJoinLevelApprove  reply  =  new Reply4UserJoinLevelApprove();
		reply.setBeread(false);
		reply.setMessage(this.message);
		reply.setTimeStamp(timeStamp);
		reply.setTimeStr(timeStr);
		reply.setApprove4UserJoinLevel(approve);
		
		List<Reply4UserJoinLevelApprove> replies = approve.getReplies();
		if(null==replies){
			replies  =  new ArrayList<Reply4UserJoinLevelApprove>();
			approve.setReplies(replies);
		}
		replies.add(reply);
		
		apply.setStatus(1);
		apply.setBeread(false);
		
		reply4UserJoinLevelApproveService.save(reply);
		approve4UserJoinLevelService.update(approve);
		userApply4JoinLevelService.update(apply);
		
		// （2）
		tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		
		String message4weixinRecall = "很遗憾的告诉您，您所申请加入的组织";
		
		switch (tag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			if (null == minusFirstLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+minusFirstLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+zeroLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			if (null == firstLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+firstLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			if (null == secondLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+secondLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			if (null == thirdLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+thirdLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			if (null == fourthLevel) {
				System.out.println("用户要加入的层级不存在");
			} else {
				message4weixinRecall += "\""+fourthLevel.getName()+"\""+"未通过您的申请，详细原因请见申请回复。";
			}
			break;
		}
		
		boolean theDuelResult = weixinService4Setting.sendTextMessage2One(user.getOpenid(), message4weixinRecall);
		if(theDuelResult){
			result.setMessage(result.getMessage()+",通知也已成功发送给用户。");
		}else{
			result.setMessage(result.getMessage()+",但通知未能发送给用户请与用户电话确定或通过公众号内置客服功能联系。");
		}
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	
}
