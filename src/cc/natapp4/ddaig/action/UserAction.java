package cc.natapp4.ddaig.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Appoint;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4NavbarGetLevelInfo;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.ManagerService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.ConfigUtils;
import cc.natapp4.ddaig.utils.FileController;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.exception.WxErrorException;

@Controller("userAction")
@Scope("prototype")
@Lazy(true)
public class UserAction extends ActionSupport implements ModelDriven<User> {

	// ==========================================================DI注入Aspect
	@Resource(name="zeroLevelAction")
	private ZeroLevelAction zeroLevelAction;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "managerService")
	private ManagerService managerService;
	@Resource(name = "groupingService")
	private GroupingService groupingService;
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
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl weixinService4Setting;
	@Resource(name = "roleService")
	private RoleService roleService;

	// ======================================================模型驱动——收纳请求参数
	private User user;

	@Override
	public User getModel() {
		user = new User();
		return this.user;
	}

	// ======================================================属性驱动——向前端页面传送经过处理的数据信息
	private int level;
	private String lid;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	// ==========================================================Method
	/*
	 * 向指定用户发送消息 一下是配套sendMessage2One()方法的属性驱动
	 */
	private String content; // 从前端提交过来的，需要发送给指定用户的消息内容
	private String openID; // 目标用户的openID

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String sendMessage2One() {

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (StringUtils.isBlank(this.openID)) {
			result.setResult(false);
			result.setMessage("用户的openID是空，不能发送信息");
		} else if (StringUtils.isBlank(this.content)) {
			result.setResult(false);
			result.setMessage("f消息内容不能是空");
		} else {
			boolean b = weixinService4Setting.sendTextMessage2One(this.openID, this.content);
			result.setResult(b);
			if (b) {
				result.setMessage("发送成功！");
			} else {
				result.setMessage("发送失败！");
			}
		}
		// 一定要将用来回复前端Ajax请求的JavaBean对象放入到栈顶后再返回json结果集索引字符串，才能让JSO插件进行解析
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 向前端navbar.jsp标题栏页面上出发的levelInfoModal 返回包括当前操作者管理的层级的名称/描述/二维码和操作者姓名等信息
	 * 
	 * @return
	 */
	public String preMyselfLevelInfo() {

		ReturnMessage4NavbarGetLevelInfo result = new ReturnMessage4NavbarGetLevelInfo();

		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}
		
		if(isAdmin){
			// 是管理员
			result.setMessage("当前操作者为系统管理员");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		String levelName = "";
		String levelDescription = "";
		String qrcode = "";
		String realPath = "";
		
		String t = doingMan.getGrouping().getTag();

		long month = 2592000;  // 30天的秒数
		switch (t) {
		case "minus_first":
			Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
			for (MinusFirstLevel minusFirstLevel : mfls) {
				levelName = minusFirstLevel.getName();
				levelDescription = minusFirstLevel.getDescription();
				
				long qrcodeTime = minusFirstLevel.getQrcodeTime();
				qrcode = minusFirstLevel.getQrcode();
				realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
				
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month || !new File(realPath).isDirectory()){
					// 带参数的临时二维码已经过期，需要更换
					// 获取过期qrcode在服务器磁盘上的真实路径
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(MinusFirstLevel.LEVEL_MINUS_FIRST);
					sb.append("_");
					sb.append("id$"); 
					sb.append(minusFirstLevel.getMflid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(minusFirstLevel.getMflid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					minusFirstLevel.setQrcode(qrcode);
					minusFirstLevel.setQrcodeTime(qrcodeTime);
					minusFirstLevelService.update(minusFirstLevel);
				}
			}
			break;
		case "zero":
			Set<ZeroLevel> zls = doingMan.getManager().getZls();
			for (ZeroLevel zeroLevel : zls) {
				levelName = zeroLevel.getName();
				levelDescription = zeroLevel.getDescription();

				long qrcodeTime = zeroLevel.getQrcodeTime();
				qrcode = zeroLevel.getQrcode();
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month || !new File(realPath).isDirectory()){
					// 带参数的临时二维码已经过期，需要更换
					System.out.println("当前日期毫秒："+System.currentTimeMillis());
					System.out.println("生成二维码日期："+qrcodeTime);
					// 获取过期qrcode在服务器磁盘上的真实路径
					realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(ZeroLevel.LEVEL_ZERO);
					sb.append("_");
					sb.append("id$"); 
					sb.append(zeroLevel.getZid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(zeroLevel.getZid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					zeroLevel.setQrcode(qrcode);
					zeroLevel.setQrcodeTime(qrcodeTime);
					zeroLevelService.update(zeroLevel);
				}
			}
			break;
		case "first":
			Set<FirstLevel> fls = doingMan.getManager().getFls();
			for (FirstLevel firstLevel : fls) {
				levelName = firstLevel.getName();
				levelDescription = firstLevel.getDescription();

				long qrcodeTime = firstLevel.getQrcodeTime();
				qrcode = firstLevel.getQrcode();
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month || !new File(realPath).isDirectory()){
					// 带参数的临时二维码已经过期，需要更换
					// 获取过期qrcode在服务器磁盘上的真实路径
					realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(FirstLevel.LEVEL_ONE);
					sb.append("_");
					sb.append("id$"); 
					sb.append(firstLevel.getFlid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(firstLevel.getFlid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					firstLevel.setQrcode(qrcode);
					firstLevel.setQrcodeTime(qrcodeTime);
					firstLevelService.update(firstLevel);
				}
			}
			break;
		case "second":
			Set<SecondLevel> scls = doingMan.getManager().getScls();
			for (SecondLevel secondLevel : scls) {
				levelName = secondLevel.getName();
				levelDescription = secondLevel.getDescription();
				
				long qrcodeTime = secondLevel.getQrcodeTime();
				qrcode = secondLevel.getQrcode();
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month || !new File(realPath).isDirectory()){
					// 带参数的临时二维码已经过期，需要更换
					// 获取过期qrcode在服务器磁盘上的真实路径
					realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(SecondLevel.LEVEL_TWO);
					sb.append("_");
					sb.append("id$"); 
					sb.append(secondLevel.getScid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(secondLevel.getScid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					secondLevel.setQrcode(qrcode);
					secondLevel.setQrcodeTime(qrcodeTime);
					secondLevelService.update(secondLevel);
				}
			}
			break;
		case "third":
			Set<ThirdLevel> tls = doingMan.getManager().getTls();
			for (ThirdLevel thirdLevel : tls) {
				levelName = thirdLevel.getName();
				levelDescription = thirdLevel.getDescription();

				long qrcodeTime = thirdLevel.getQrcodeTime();
				qrcode = thirdLevel.getQrcode();
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month || !new File(realPath).isDirectory()){
					// 带参数的临时二维码已经过期，需要更换
					// 获取过期qrcode在服务器磁盘上的真实路径
					realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(ThirdLevel.LEVEL_THREE);
					sb.append("_");
					sb.append("id$"); 
					sb.append(thirdLevel.getThid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(thirdLevel.getThid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					thirdLevel.setQrcode(qrcode);
					thirdLevel.setQrcodeTime(qrcodeTime);
					thirdLevelService.update(thirdLevel);
				}
			}
			break;
		case "fourth":
			Set<FourthLevel> fols = doingMan.getManager().getFols();
			for (FourthLevel fourthLevel : fols) {
				levelName = fourthLevel.getName();
				levelDescription = fourthLevel.getDescription();

				long qrcodeTime = fourthLevel.getQrcodeTime();
				qrcode = fourthLevel.getQrcode();
				if((System.currentTimeMillis()-qrcodeTime)/1000 >= month){
					// 带参数的临时二维码已经过期，需要更换
					// 获取过期qrcode在服务器磁盘上的真实路径
					realPath = ServletActionContext.getServletContext().getRealPath(qrcode);
					// 删除该失效二维码
					if(new File(realPath).isDirectory()){
						// 如果二维码图片存在，则删除该图片
						FileController.deleteFile(realPath);
					}
					/*
					 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
					 * 将该参数提交给微信端后会生成“带参数二维码”，
					 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
					 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
					 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
					 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
					 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
					 */
					StringBuffer sb = new StringBuffer();
					sb.append("level$");
					sb.append(FourthLevel.LEVEL_FOUR);
					sb.append("_");
					sb.append("id$"); 
					sb.append(fourthLevel.getFoid());
					// 重新从微信端服务器获取当前层级对象的带参数二维码，并且更新数据库中qrcode和qrcodeTime字段
					qrcode = zeroLevelAction.getQrcodeFromWeixin(fourthLevel.getFoid(), sb.toString(), new ReturnMessage4Common());
					qrcodeTime = System.currentTimeMillis();
					// 向数据库中更新数据
					fourthLevel.setQrcode(qrcode);
					fourthLevel.setQrcodeTime(qrcodeTime);
					fourthLevelService.update(fourthLevel);
				}
			}
			break;
		}

		result.setLevelManager(doingMan.getUsername());
		result.setLevelName(levelName);
		result.setLevelDescription(levelDescription);
		result.setQrcode(qrcode);
		result.setResult(true);
		result.setMessage("获取成功");

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 供给后台用户管理系统使用，获取所辖用户群体
	 * 
	 * @return 结果集索引字符串
	 */
	public String getUserList() {

		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		// --------------------------开始根据操作人的层级来获取所辖用户（Admin获取所有用户）---------------------------
		List<User> users = new ArrayList<User>();
		if (isAdmin) {
			users = userService.queryEntities();
		} else {
			Manager manager = doingMan.getManager();
			String tagName = doingMan.getGrouping().getTag();
			switch (tagName) {
			case "minus_first":
				// 街道层级管理者执行的创建用户操作
				MinusFirstLevel level = null;
				for (MinusFirstLevel mfl : manager.getMfls()) {
					level = mfl;
				}
				Set<Member> members = level.getMembers();
				for (Member m : members) {
					users.add(m.getUser());
				}
				break;
			case "zero":
				// 社区层级管理者执行的创建用户操作
				ZeroLevel level0 = null;
				for (ZeroLevel zl : manager.getZls()) {
					level0 = zl;
				}
				Set<Member> members0 = level0.getMembers();
				for (Member m : members0) {
					users.add(m.getUser());
				}
				break;
			case "first":
				// 第一层级管理者执行的创建用户操作
				FirstLevel level1 = null;
				for (FirstLevel fl : manager.getFls()) {
					level1 = fl;
				}
				Set<Member> members1 = level1.getMembers();
				for (Member m : members1) {
					users.add(m.getUser());
				}
				break;
			case "second":
				// 第二层级管理者执行的创建用户操作
				SecondLevel level2 = null;
				for (SecondLevel scl : manager.getScls()) {
					level2 = scl;
				}
				Set<Member> members2 = level2.getMembers();
				for (Member m : members2) {
					users.add(m.getUser());
				}
				break;
			case "third":
				// 第三层级用户执行的创建用户操作
				ThirdLevel level3 = null;
				for (ThirdLevel tl : manager.getTls()) {
					level3 = tl;
				}
				Set<Member> members3 = level3.getMembers();
				for (Member m : members3) {
					users.add(m.getUser());
				}
				break;
			case "fourth":
				// 第四层级用户执行的创建用户操作
				FourthLevel level4 = null;
				for (FourthLevel fol : manager.getFols()) {
					level4 = fol;
				}
				Set<Member> members4 = level4.getMembers();
				for (Member m : members4) {
					users.add(m.getUser());
				}
				break;
			}
		}

		// ------------------将数据库中保存的关于注册日期的格里高利里毫秒值偏移量翻译成yyyy-MM-dd HH:mm:ss
		// 的字符串格式------------------
		if (null != users) {
			// 如果当前查看的层级有用户，则修改用户的注册日期显示，否则就没必要了
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (User u : users) {
				u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));
			}
		}
		// 放入到值栈中的map栈中
		ActionContext.getContext().put("users", users);
		return "list";
	}

	/**
	 * 供给后台通过AJax技术，实现修改特定用户基本信息
	 */
	public String getUserInfo() {

		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				/*
				 * ★★★★ 这里出现一个BUG，由于Hibernate的二级缓存机制，导致即便我们在修改（通过SETTER方法）
				 * 持久化状态对象中的数据信息
				 * 后不显示地调用update()方法向数据库更新，由于在二级缓存中保存了一份持久化状态对象"原始"状态的拷贝，
				 * 如果此时我们再次调用 查询方法，则Hiberante会先比对二级缓存中的拷贝与持久化状态对象，
				 * 如果发现字段数据被改动则会自动向数据库commit提交数据 之后在进行查询操作。
				 * 也就是说Hibernate为了防止出现脏数据等问题，
				 * 会优先将session的二级缓存中保存的持久化状态对象的最新状态保存到数据库中后 再进行新的CRUD操作。
				 * 
				 * 具体到本例题来说，如果先对持久化状态对象u进行数据操作（我们加工了qrcode数据，为了方便前端能够显示出二维码），
				 * 注意我们并没有update这个U
				 * 而只希望将U通过Ajax返回到前端，而此时如果我们再次通过相同session对同一个数据库表（User）进行CRUD操作，
				 * 则Hiberante会优先将session的 二级缓存中保存的状态更新到User数据库后再执行操作，
				 * 所以才出现即便我们没有显示地执行update更新持久化状态对象U的数据，但是其中的qrcode也被更新到 数据库了。
				 * 解决办法就是在对持久化状态对象执行修改操作之前，将所有涉及持久化状态对象所属数据库表的查询操作先操作完成。
				 * 
				 */
				doingMan = userService.getUserByUsername(principal);
			}
		}

		// 从User中查询出被操作者索取的用户对象，前端操作者所需要的该用户的信息数据大部分都保存在这里，但有些数据信息仍需加工一下
		String uid = this.user.getUid();
		User u = userService.queryEntityById(uid);

		if (null == u) {
			System.out.println("查找不到待修改的用户数据");
		} else {
			// 修改该用户的qrcode中保存的相对路径 → 拼接成绝对路径url，以此供后端页面上的infoModal对话框的<img
			// src=""/>的src属性使用，以显示该用户的独有qrcode
			String qrcodeUrl = ServletActionContext.getServletContext().getContextPath() + "/" + u.getQrcode();
			u.setQrcode(qrcodeUrl);
			// 处理注册时间，根据long类型的格力高丽丽偏移量毫秒值 经过格式转化成前端用户可识别的字符串信息
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));

			if (null != u.getMember()) {
				Member m = new Member();
				// TODO 如果有Bean拷贝的jar就能一次性全部拷贝了
				m.setFirstLevel(u.getMember().getFirstLevel());
				m.setFourthLevel(u.getMember().getFourthLevel());
				m.setMinusFirstLevel(u.getMember().getMinusFirstLevel());
				m.setSecondLevel(u.getMember().getSecondLevel());
				m.setThirdLevel(u.getMember().getThirdLevel());
				m.setUser(u);
				m.setZeroLevel(u.getMember().getZeroLevel());
				u.setMember4Ajax(m);
			}
			if (null != u.getManager()) {
				// TODO
				// 这里是否应该进行浅拷贝？？防止我们在切断manager与user的一对一关系的时候会影响到数据库（即便我们没有显示调用update方法）？
				Manager manager = u.getManager();
				// 由于user与manager是一对一提关系，两者内都有彼此引用从而在JSON解析时出现系循环嵌套，因此我们需要先切断manager与user的关系
				manager.setUser(null);
				// 由于user中的manager的GETTER属性上设置了@JSON（serializable=false）因此不会被解析到JSON中传输给前端
				// 我们需要使用替代属性manager4Ajax向前端传输必要的manager数据
				u.setManager4Ajax(u.getManager());
			}
		}

		// -----------根据操作者的层级对象不同，来设置被索取的用户的tag数据信息----------
		ArrayList<String> tagsList = new ArrayList<String>();
		if (isAdmin) {
			/*
			 * adming管理员用户有权分配街道层级用户，而不能越级分配，防止出现人员在层级结构中的混乱（明明是某个第四层及的member，
			 * 却被分配了社区层级的tag，这是不合理的） 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
			 */
			tagsList.add("unreal");
			tagsList.add("common");
			tagsList.add("minus_first");
		} else {
			/*
			 * 同理 非admin，则根据实际情况来设置tags（只能设置低于当前操作者层次的tag）
			 * ，而不能越级分配，防止出现人员在层级结构中的混乱（明明是某个第四层及的member，却被分配了社区层级的tag，这是不合理的）
			 * 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
			 */
			String t = doingMan.getGrouping().getTag();
			switch (t) {
			case "minus_first":
				tagsList.add("zero");
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			case "zero":
				tagsList.add("first");
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			case "first":
				tagsList.add("second");
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			case "second":
				tagsList.add("third");
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			case "third":
				tagsList.add("fourth");
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			case "fourth":
				tagsList.add("common");
				tagsList.add("unreal");
				break;
			}
		}

		/*
		 * 当需要将List转化成数组Array的时候是需要像如下方式实现的， 给ArrayList.toArray()传递一个数组实例作为参数。★
		 */
		String[] tags = (String[]) tagsList.toArray(new String[0]);
		u.setTags(tags);

		// userService.clearSession();
		ActionContext.getContext().getValueStack().push(u);
		return "json";
	}

	/**
	 * Ajax 从前端接收uid（被指派的人员uid）, level（被指派到的层级对象的层级）, lid（被指派到的层级对象的id）
	 * 实现用户到该层级的派遣功能
	 * 
	 * @return
	 */
	public String assignedUser() {

		String uid = user.getUid();
		int level = this.getLevel();
		String lid = this.getLid();

		ReturnMessage4Common result = new ReturnMessage4Common("人员派遣成功！", true);
		User u = null;

		switch (level) {
		case -1:
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			if (minusFirstLevel == null) {
				result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
				result.setResult(false);
			}
			u = userService.queryEntityById(uid);
			u.getMember().setMinusFirstLevel(minusFirstLevel);
			userService.update(u);
			break;
		case 0:
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (zeroLevel == null) {
				result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
				result.setResult(false);
			}
			u = userService.queryEntityById(uid);
			u.getMember().setZeroLevel(zeroLevel);
			userService.update(u);
			break;
		case 1:
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			if (firstLevel == null) {
				result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
				result.setResult(false);
			}
			u = userService.queryEntityById(uid);
			u.getMember().setFirstLevel(firstLevel);
			userService.update(u);
			break;
		case 2:
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			if (secondLevel == null) {
				result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
				result.setResult(false);
			}
			u = userService.queryEntityById(uid);
			u.getMember().setSecondLevel(secondLevel);
			userService.update(u);
			break;
		case 3:
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			if (thirdLevel == null) {
				result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
				result.setResult(false);
			}
			u = userService.queryEntityById(uid);
			u.getMember().setThirdLevel(thirdLevel);
			userService.update(u);
			break;
		default:
			result.setMessage("不存在指定层级为" + level + "的层级对象，人员派遣失败");
			result.setResult(false);
			break;
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 向前端返回当前操作者层级对象,以便于前端从中通过children4Ajax获取可以指派人员的直接子层级
	 * 
	 * @return
	 */
	public String showUserAssignedModal() {

		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		if (isAdmin) {
			// 管理员，则将系统中的所有MinusFirstLevel层级对象返回给前端
			List<MinusFirstLevel> queryEntities = minusFirstLevelService.queryEntities();
			ActionContext.getContext().getValueStack().push(queryEntities);
		} else {
			// 非管理员
			// 分析出当前操作者掌管的层级对象，进而获取其全部子层级
			switch (doingMan.getGrouping().getTag()) {
			case "minus_first":
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel level_1 = null;
				for (MinusFirstLevel l : mfls) {
					level_1 = l;
					break;
				}
				ActionContext.getContext().getValueStack().push(level_1);
				break;
			case "zero":
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				ZeroLevel level0 = null;
				for (ZeroLevel l : zls) {
					level0 = l;
					break;
				}
				ActionContext.getContext().getValueStack().push(level0);
				break;
			case "first":
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				FirstLevel level1 = null;
				for (FirstLevel l : fls) {
					level1 = l;
					break;
				}
				ActionContext.getContext().getValueStack().push(level1);
				break;
			case "second":
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				SecondLevel level2 = null;
				for (SecondLevel l : scls) {
					level2 = l;
					break;
				}
				ActionContext.getContext().getValueStack().push(level2);
				break;
			case "third":
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				ThirdLevel level3 = null;
				for (ThirdLevel l : tls) {
					level3 = l;
					break;
				}
				ActionContext.getContext().getValueStack().push(level3);
				break;
			}
		}
		return "json";
	}

	/**
	 * 根据后台提交来的修改用户的ajax请求提交过来的数据， 同时修改本地数据库的user数据 和修改微信公众号的用户tag
	 * 
	 * @return 结果集索引字符串
	 */
	public String update() {

		String uid = this.user.getUid();
		String username = this.user.getUsername();
		String sickname = this.user.getSickname();
		String cardid = this.user.getCardid();
		int age = this.user.getAge();
		String phone = this.user.getPhone();
		String email = this.user.getEmail();
		String address = this.user.getAddress();
		String sex = this.user.getSex().equals("1") ? "男" : "女";
		String tag = this.tag;

		User u = userService.queryEntityById(uid);

		u.setUsername(username);
		u.setSickname(sickname);
		u.setCardid(cardid);
		u.setAge(age);
		u.setPhone(phone);
		u.setEmail(email);
		u.setAddress(address);
		u.setSex(sex);

		List<Grouping> list = groupingService.queryEntities();
		for (Grouping g : list) {
			if (g.getTag().equals(tag)) {
				u.setGrouping(g);

				String openid = u.getOpenid();
				if (org.springframework.util.StringUtils.isEmpty(openid)) {
					break;
				}
				/*
				 * 接入微信平台后需要开启这部分代码用来修改微信公众号的该用户的tag
				 */
				String[] ids = { openid };
				try {
					weixinService4Setting.getUserTagService().batchTagging(g.getTagid(), ids);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
		userService.update(u);
		return "json";
	}

	// 承接从前端修改dialog中提过过来的tag数据
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 接受从后台传递来的ajax请求，用来批量重新生成每个用户的qrcode
	 * 
	 * @return 结果集索引字符串
	 */
	public String batchCreateQR() {

		userService.batchCreateUserQR();
		ReturnMessage4Common result = new ReturnMessage4Common("用户二维码批量生成成功", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	public String getExchangeList() {

		// String uid = this.getModel().getUid();
		// 切记一定要不这样获取数据驱动的请求参数，getModal()是给Struts框架使用的，不是给人使用的★★★★
		String uid = this.user.getUid();
		User u = userService.queryEntityById(uid);
		Set<Exchange> set = u.getExchanges();

		ActionContext.getContext().put("exchanges", set);
		return "exchangeList";
	}

	public String create() {
		ReturnMessage4Common message = new ReturnMessage4Common();

		/**
		 * shiro需要权限认证，获取执行当前操作的管理者的所在权限， 然后新建的用户就置于该管理者的层级之下。
		 */
		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		// ---------------------------开始正式新建用户对象---------------------------
		// （1）处理User对象
		User u = new User();
		u.setUsername(user.getUsername());
		u.setSickname(user.getSickname());
		u.setCardid(user.getCardid());
		u.setAddress(user.getAddress());
		u.setEmail(user.getEmail());
		u.setPhone(user.getPhone());
		u.setAge(user.getAge());
		u.setRegistrationTime(System.currentTimeMillis());
		// 所有新建用户的tag都是common，如果需要提升到某个管理层需要更高级的管理员手动修改
		Grouping g = groupingService.queryByTagName("common");
		u.setGrouping(g);
		// 处理性别
		if ("1".equals(user.getSex())) {
			u.setSex("男");
			message.setMessage("新建成功！");
			message.setResult(true);
		} else if ("2".equals(user.getSex())) {
			u.setSex("女");
			message.setMessage("新建成功！");
			message.setResult(true);
		} else {
			message.setResult(false);
			message.setMessage("未选择性别，新建失败");
			ActionContext.getContext().getValueStack().push(message);
			return "json";
		}
		// （2）处理member层级数据，这是用来定位新建用户在层级结构中位置的关键，需要注意的是被新建的用户一定是默认置于当前操作者的层级对象之下的，然后向上不全至MinusFirst层级
		Member member = new Member();
		if (isAdmin) {
			// 如果是admin新建的用户就很简单了，该用户不属于任何一个层级，因此member中的外键都是null
			member.setUser(u);
			member.setFirstLevel(null);
			member.setFourthLevel(null);
			member.setMinusFirstLevel(null);
			member.setSecondLevel(null);
			member.setThirdLevel(null);
			member.setZeroLevel(null);
		} else {
			// 如果是非admin创建的用户那就需要老老实实的给member填入层级对象的外键关联了
			member.setUser(u);
			// 获取执行当前创建用户操作的管理者对象，并进一步获取其绑定的层级对象
			Manager manager = doingMan.getManager();
			switch (doingMan.getGrouping().getTag()) {
			case "minus_first":
				// 街道层级管理者执行的创建用户操作
				MinusFirstLevel level = null;
				for (MinusFirstLevel mfl : manager.getMfls()) {
					level = mfl;
				}
				member.setMinusFirstLevel(level);
				break;
			case "zero":
				// 社区层级管理者执行的创建用户操作
				ZeroLevel level0 = null;
				for (ZeroLevel zl : manager.getZls()) {
					level0 = zl;
				}
				member.setZeroLevel(level0);
				member.setMinusFirstLevel(level0.getParent());
				break;
			case "first":
				// 第一层级管理者执行的创建用户操作
				FirstLevel level1 = null;
				for (FirstLevel fl : manager.getFls()) {
					level1 = fl;
				}
				member.setFirstLevel(level1);
				member.setZeroLevel(level1.getParent());
				member.setMinusFirstLevel(level1.getParent().getParent());
				break;
			case "second":
				// 第二层级管理者执行的创建用户操作
				SecondLevel level2 = null;
				for (SecondLevel scl : manager.getScls()) {
					level2 = scl;
				}
				member.setSecondLevel(level2);
				member.setFirstLevel(level2.getParent());
				member.setZeroLevel(level2.getParent().getParent());
				member.setMinusFirstLevel(level2.getParent().getParent().getParent());
				break;
			case "third":
				// 第三层级用户执行的创建用户操作
				ThirdLevel level3 = null;
				for (ThirdLevel tl : manager.getTls()) {
					level3 = tl;
				}
				member.setThirdLevel(level3);
				member.setSecondLevel(level3.getParent());
				member.setFirstLevel(level3.getParent().getParent());
				member.setZeroLevel(level3.getParent().getParent().getParent());
				member.setMinusFirstLevel(level3.getParent().getParent().getParent().getParent());
				break;
			case "fourth":
				// 第四层级用户执行的创建用户操作
				FourthLevel level4 = null;
				for (FourthLevel fol : manager.getFols()) {
					level4 = fol;
				}
				member.setFourthLevel(level4);
				member.setThirdLevel(level4.getParent());
				member.setSecondLevel(level4.getParent().getParent());
				member.setFirstLevel(level4.getParent().getParent().getParent());
				member.setZeroLevel(level4.getParent().getParent().getParent().getParent());
				member.setMinusFirstLevel(level4.getParent().getParent().getParent().getParent().getParent());
				break;
			}
		}
		// （3）建立member与user的关联关系（一对一），然后向数据库中保存
		u.setMember(member);
		userService.save(u);

		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}

	/**
	 * 在managerList.jsp上显示当前操作执行者“直辖”的 “中间层”人员，包括
	 */
	public String getManagerList() {
		/*
		 * TODO 如果前端发来的是带tag的请求参数， 可以根据参数值是unreal、common和当前操作者层级次一级的tag
		 * 来进一步筛选前端所要获取的人员数据 不过如果tag为null，则说明前端所要的是所有“中间层的人员”
		 */
		String tag = this.getTag();
		if (null == tag) {
			tag = "nonono";
		}

		List<User> users = userService.getManagers(tag);
		ActionContext.getContext().put("users", users);
		return "managerList";
	}

	/**
	 * 用来供给managerList.jsp页面上的appointModal 显示必要的层级对象信息
	 * 
	 * @return
	 */
	public String getAppointInfo() {
		ReturnMessage4Appoint result = null;
		// 获取待任命者的uid
		String uid = this.user.getUid();
		// 得到待任命者的user对象
		User u = userService.queryEntityById(uid);
		// 得到待任命者的tag
		String t = u.getGrouping().getTag();
		// 进而判断待任命者的级别
		int lowest = 10086; // 如果lowest=10086，则表示超出系统层级范围，应该在前端报错
		switch (t) {
		case "minus_first":
			lowest = -1;
			break;
		case "zero":
			lowest = 0;
			break;
		case "first":
			lowest = 1;
			break;
		case "second":
			lowest = 2;
			break;
		case "third":
			lowest = 3;
			break;
		case "fourth":
			lowest = 4;
			break;
		default:
			lowest = 10086;
			break;
		}

		// ---------------------------Shiro认证操作者身份--------------------------
		// controller用來标记当前操作者的层级（10086表示admin）
		int controllerNum = 10086;
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}

		result = new ReturnMessage4Appoint();
		if (isAdmin) {
			// Admin操作者
			controllerNum = 10086;
		} else {
			// 非Admin操作者
			switch (doingMan.getGrouping().getTag()) {
			// 将当前操作执行者绑定的层级对象，放入到result中返回给前端，方便前端获取通过children4Ajax获取次一级层级对象，来补充select的option选项
			case "minus_first":
				controllerNum = -1;
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				for (MinusFirstLevel l : mfls) {
					result.setMinusFirst(l);
				}
				break;
			case "zero":
				controllerNum = 0;
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				for (ZeroLevel zl : zls) {
					result.setZero(zl);
				}
				break;
			case "first":
				controllerNum = 1;
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				for (FirstLevel fl : fls) {
					result.setFirst(fl);
				}
				break;
			case "second":
				controllerNum = 2;
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				for (SecondLevel sc : scls) {
					result.setSecond(sc);
				}
				break;
			case "third":
				controllerNum = 3;
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				for (ThirdLevel tl : tls) {
					result.setThird(tl);
				}
				break;
			}
		}

		// -----------------------开始准备向前端发送的数据---------------------------
		if (10086 == lowest) {
			result.setMessage("错误：待委任者的层级不在系统支持的管理层级可选范围内");
			result.setResult(false);
		} else if (10086 == controllerNum) {
			// 当前操作是Admin操作的，应该将所有层级对象数据信息获取出来
			List<MinusFirstLevel> minusLevels = minusFirstLevelService.queryEntities();
			// 需要剔除已经有委任的层级对象
			List<MinusFirstLevel> levels = new ArrayList<MinusFirstLevel>();
			for (MinusFirstLevel l : minusLevels) {
				// 只返回没有被“委任”的层级对象
				if (l.getManager() == null) {
					levels.add(l);
				}
			}
			// 填入数据
			result.setMessage("执行当前操作的是Admin");
			result.setResult(true);
			result.setControllerNum(controllerNum);
			result.setLowest(lowest);
			result.setMinusLevels(levels);
		} else {
			// 非admin操作者
			result.setMessage("执行当前操作的是非Admin管理者");
			result.setResult(true);
			result.setControllerNum(controllerNum);
			result.setLowest(lowest);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 本方法对应前端的managerModal.op.changeAppointSelect()中的Ajax请求
	 * 根据操作者在managerList.jsp的AppointModal中的选择情况（由id=appoint-1~
	 * 4的Select的onchange事件触发本方法） 然后更新下一级的以及后续层级（直到fourthLevel）中的显示内容（option选项）
	 * level:int类型 触发onchange事件的select对应的层级（也就是其id=appointX中的X的数字） lid：
	 * 为操作者选中的层级对象的id
	 */
	public String getAppointSelectInfo() {

		int level = this.getLevel();
		String lid = this.getLid();
		if (lid.isEmpty() || level > 4 || level < -1) {
			ReturnMessage4Common result = new ReturnMessage4Common();
			result.setMessage("必要参数level或lid为NULL，操作不予执行");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
		} else {
			ReturnMessage4Appoint result = new ReturnMessage4Appoint();
			switch (level) {
			case -1: // 从街道层级中查找
				MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
				if (null == minusFirst) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的MinusFirstLevel层级对象已获取");
					result.setResult(true);
					result.setMinusFirst(minusFirst);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 0: // 从社区层级中查找
				ZeroLevel zero = zeroLevelService.queryEntityById(lid);
				if (null == zero) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的ZeroLevel层级对象已获取");
					result.setResult(true);
					result.setZero(zero);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 1: // 从第一层级中查找
				FirstLevel first = firstLevelService.queryEntityById(lid);
				if (null == first) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的FirstLevel层级对象已获取");
					result.setResult(true);
					result.setFirst(first);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 2: // 从第二层级中查找
				SecondLevel second = secondLevelService.queryEntityById(lid);
				if (null == second) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的SecondLevel层级对象已获取");
					result.setResult(true);
					result.setSecond(second);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 3: // 从第三层级中查找
				ThirdLevel third = thirdLevelService.queryEntityById(lid);
				if (null == third) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的ThirdLevel层级对象已获取");
					result.setResult(true);
					result.setThird(third);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 4: // 从第四层级中查找
				FourthLevel fourth = fourthLevelService.queryEntityById(lid);
				if (null == fourth) {
					result.setMessage("不存在id为：" + lid + "的层级对象");
					result.setResult(false);
				} else {
					result.setMessage("查询的FourthLevel层级对象已获取");
					result.setResult(true);
					result.setFourth(fourth);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			default:
				ReturnMessage4Common result2 = new ReturnMessage4Common();
				result.setMessage("level的数值超出系统层级范围");
				result.setResult(false);
				ActionContext.getContext().getValueStack().push(result2);
				break;
			}
		}
		return "json";
	}

	/**
	 * 解除任命
	 * 
	 * @return
	 */
	public String doDisappoint() {

		String uid = this.user.getUid();
		User u = userService.queryEntityById(uid);
		Manager m = u.getManager();
		u.setManager(null);
		userService.update(u);
		managerService.delete(m);

		ReturnMessage4Common result = new ReturnMessage4Common("解任成功！", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 正式执行用户（manager）与层级对象的绑定（委任）操作
	 * 
	 * @return
	 */
	public String doAppoint() {
		// 层级对象的所属层级（-1、0、1、2、3、4）
		int level = this.getLevel();
		// 待委任的管理者的uid
		String uid = this.user.getUid();
		// 待委任的层级对象的id
		String lid = this.getLid();
		// 开始执行
		User u = null;
		Manager m = null;
		switch (level) {
		case -1:
			MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<MinusFirstLevel> mfls = new HashSet<MinusFirstLevel>();
			mfls.add(minusFirst);
			m.setMfls(mfls);
			break;
		case 0:
			ZeroLevel zero = zeroLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<ZeroLevel> zls = new HashSet<ZeroLevel>();
			zls.add(zero);
			m.setZls(zls);
			break;
		case 1:
			FirstLevel first = firstLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<FirstLevel> fls = new HashSet<FirstLevel>();
			fls.add(first);
			m.setFls(fls);
			break;
		case 2:
			SecondLevel second = secondLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<SecondLevel> scls = new HashSet<SecondLevel>();
			scls.add(second);
			m.setScls(scls);
			break;
		case 3:
			ThirdLevel third = thirdLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<ThirdLevel> tls = new HashSet<ThirdLevel>();
			tls.add(third);
			m.setTls(tls);
			break;
		case 4:
			FourthLevel fourth = fourthLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m = new Manager();
			m.setUser(u);
			Set<FourthLevel> fols = new HashSet<FourthLevel>();
			fols.add(fourth);
			m.setFols(fols);
			break;
		}
		managerService.save(m);

		ReturnMessage4Common result = new ReturnMessage4Common("委任成功", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

}
