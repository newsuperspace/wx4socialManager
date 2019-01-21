package cc.natapp4.ddaig.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

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

import cc.natapp4.ddaig.bean.User4Ajax;
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
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

@Controller("userAction")
@Scope("prototype")
@Lazy(true)
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private static final long serialVersionUID = 600271725750065543L;
	// ==========================================================DI注入Aspect
	@Resource(name = "zeroLevelAction")
	private ZeroLevelAction zeroLevelAction;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "managerService")
	private ManagerService managerService;
	@Resource(name = "memberService")
	private MemberService memberService;
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

	/*
	 * managedLevelList.jsp → myJS.managedLevelList.disappoint() 中
	 * 通过AJAX传递来的请求参数，用于标定被“解除任命”的manager对象 本属性由doDisappoint()使用
	 */
	private String managerid;
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
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

	/**
	 * 点击用户列表中某个用户的 发送信息 按钮时 弹出一个填写消息的Modal，然后在之上的文本框中输入内容点击发送后就会发送到 该用户的微信上了
	 * 
	 * @return
	 */
	public String sendMessage2One() {

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (StringUtils.isBlank(this.openID)) {
			result.setResult(false);
			result.setMessage("该用户为非微信用户，不能发送信息");
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
	 * 向前端navbar.jsp标题栏页面上出发的levelInfoModal 返回包括当前操作者管理的层级的名称/描述/带参数二维码和操作者姓名等信息
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

		if (isAdmin) {
			// 是管理员
			result.setMessage("当前操作者为系统管理员");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		String levelName = "";
		String levelDescription = "";
		String qrcode = "";
		// 当前操作者层级的关键数据（从登陆开始用户在managerSelect.jsp页面选定所要登录的层级对象后就一直存放在session域中）
		String t = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		long qrcodeTime = -1;
		// servletContext域对象
		ServletContext context = ServletActionContext.getServletContext();
		StringBuffer sb = null;
		File file = null;
		switch (t) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			levelName = minusFirstLevel.getName();
			levelDescription = minusFirstLevel.getDescription();
			qrcodeTime = minusFirstLevel.getQrcodeTime();
			qrcode = minusFirstLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + minusFirstLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("minus_first");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + minusFirstLevel.getQrcode()),
						sb.toString());
			}
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			levelName = zeroLevel.getName();
			levelDescription = zeroLevel.getDescription();
			qrcodeTime = zeroLevel.getQrcodeTime();
			qrcode = zeroLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + zeroLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("zero");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + zeroLevel.getQrcode()), sb.toString());
			}
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			levelName = firstLevel.getName();
			levelDescription = firstLevel.getDescription();
			qrcodeTime = firstLevel.getQrcodeTime();
			qrcode = firstLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + firstLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("first");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + firstLevel.getQrcode()), sb.toString());
			}
			break;
		case "second":

			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			levelName = secondLevel.getName();
			levelDescription = secondLevel.getDescription();
			qrcodeTime = secondLevel.getQrcodeTime();
			qrcode = secondLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + secondLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("second");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + secondLevel.getQrcode()), sb.toString());
			}
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			levelName = thirdLevel.getName();
			levelDescription = thirdLevel.getDescription();
			qrcodeTime = thirdLevel.getQrcodeTime();
			qrcode = thirdLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + thirdLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("third");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + thirdLevel.getQrcode()), sb.toString());
			}
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			levelName = fourthLevel.getName();
			levelDescription = fourthLevel.getDescription();
			qrcodeTime = fourthLevel.getQrcodeTime();
			qrcode = fourthLevel.getQrcode();
			// 查看当前操作者层级的二维码是否存在，如果不存在自动创建
			file = new File(context.getRealPath(File.separator + fourthLevel.getQrcode()));
			if (!file.exists()) {
				// 如果不存在二维码文件，则重新创建二维码文件
				File parentFile = file.getParentFile();
				// 判断二维码图片的路径是否存在，不存在就逐层创建
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				sb = new StringBuffer();
				sb.append("tag=");
				sb.append("fourth");
				sb.append("&");
				sb.append("lid=");
				sb.append(lid);
				QRCodeUtils.createQRcode(context.getRealPath(File.separator + fourthLevel.getQrcode()), sb.toString());
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
	 * 该方法是目前本系统的入口方法 与getManagerList()方法相对 供给后台用户管理系统使用，获取所有用户群体（包括直辖和子孙层级的直辖）
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

		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// --------------------------开始根据操作人的层级来获取所辖用户（Admin获取所有用户）---------------------------
		List<User> users = null;
		if (isAdmin) {
			users = userService.queryEntities();
		} else {
			users = userService.getChildrenLevelUsers(tag, lid);
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
	 * 【11月1日可行，不在向前端返回user或user4ajax等domain】 与getUserList()相对
	 * 在managerList.jsp上显示当前操作执行者的“直辖人员"，包括 XX层级管理者、普通用户
	 * 和只有admin才能见到的未认证unreal用户 当然还可以获取所有直辖用户，不论这些用户的tag是什么
	 */
	public String getManagerList() {
		/*
		 * TODO 如果前端发来的是带tag的请求参数， 可以根据参数值是unreal、common和当前操作者层级次一级的tag
		 * 来进一步筛选前端所要获取的人员数据 不过如果tag为null，则说明前端所要的是所有“中间层的人员”
		 */
		String tag = this.getTag();
		if (null == tag) {
			// 我们要找的是所有tag的直辖人员用户，因此将tag设置成all
			tag = "all";
		}
		// 从HttpSession域中获取当前操作者层级的相关数据库信息
		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		// 得到所有用户的默认member（也就是新建用户时其作为公众号成员的表示，minusFirstLevel等都为null）
		List<Member> members = userService.getManagers(tag, levelTag, lid);
		ActionContext.getContext().put("members", members);
		return "managerList";
	}

	/**
	 * 【AJAX】 供给后台通过AJax技术，实现修改特定用户基本信息时在前端回显数据
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
				 * 注意我们并没有要update这个U的意思
				 * 而只希望将U通过Ajax返回到前端原原本本显示出来，而此时如果我们再次通过相同session对同一个数据库表（User）
				 * 进行CRUD操作， 则Hiberante会优先将session的 二级缓存中保存的状态更新到User数据库后再执行操作，
				 * 所以才出现即便我们没有显示地执行update更新持久化状态对象U的数据，但是其中被更改的qrcode却被更新到
				 * 数据库里了。 解决办法就是在对持久化状态对象执行修改操作之前，将所有涉及持久化状态对象所属数据库表的查询操作先操作完成。
				 * 
				 */
				doingMan = userService.getUserByUsername(principal);
			}
		}

		// 从User中查询出被操作者索取的用户对象，前端操作者所需要的该用户的信息数据大部分都保存在这里，但有些数据信息仍需加工一下
		String uid = this.user.getUid();
		User user = userService.queryEntityById(uid); // 等待修改的用户对象
		User4Ajax u = new User4Ajax(); // 向前端返回信息之用

		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");

		if (null == user) {
			System.out.println("查找不到待修改的用户数据");
		} else {
			// 修改该用户的qrcode中保存的相对路径 → 拼接成绝对路径url，以此供前端页面上的infoModal对话框的<img
			// src=""/>的src属性直接使用，以显示该用户的qrcode图片
			String qrcodeUrl = ServletActionContext.getServletContext().getContextPath() + "/" + user.getQrcode();
			u.setQrcode(qrcodeUrl);
			// 处理注册时间，根据long类型的格力高丽丽偏移量毫秒值 经过格式转化成前端用户可识别的字符串信息
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			u.setRegistrationTimeStr(format.format(new Date(user.getRegistrationTime())));

			/*
			 * 通过user.getMembers()获取到的是该用户在整个系统中的组织加入情况，
			 * 但我们要的是其在当前操作者层级管理范围之内的组织加入情况
			 * 也就是说该用户可能加入了当前操作执行人所管理的层级对象的多个子层级和孙层级，那么如何才能过滤出这些管辖范围内的组织的
			 * 加入情况，而不会暴露出其他不相关层级的信息呢？下面三步走就是过滤的逻辑：
			 */
			List<Member> members = new ArrayList<Member>();
			// ①首先我们要将所有层级找到
			for (Member m : user.getMembers()) {
				// ②再根据当前操作执行人所掌管的层级的tag确定当前操作人的层级是属于哪一级
				switch (tag) {
				case "minus_first":
					/*
					 * ③然后我们在通过遍历所有member中的层级字段，在对应位置上比对lid，
					 * 相同的说明该用户必然是当前操作人管理层级或子孙层级的成员 则直接提取出来即可
					 */
					// minus_first层级我们就直接遍历这些member的minusFirstLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)) {
						members.add(m);
					}
					break;
				case "zero":
					// zero层级我们就直接遍历这些member的zeroLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid)) {
						members.add(m);
					}
					break;
				case "first":
					// first层级我们就直接遍历这些member的FirstLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)) {
						members.add(m);
					}
					break;
				case "second":
					// second层级我们就直接遍历这些member的secondLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)) {
						members.add(m);
					}
					break;
				case "third":
					// third层级我们就直接遍历这些member的thirdLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)) {
						members.add(m);
					}
					break;
				case "fourth":
					// fourth层级我们就直接遍历这些member的fourthLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
						members.add(m);
					}
					break;

				default: // admin
					// 如果当前查看该用户的操作者是Admin，那么就将它的所有member都放进去
					members = user.getMembers();
					break;
				}
			}
			u.setMembers(members);

			/*
			 * 接下来我们要找的就是被查看用户是否为当前操作执行者层级的直辖人员，如果是
			 * 则在User4Ajax.member位置上保存该直辖关系的member，用作未来通过member变更该用户的tag
			 * 并进一步任命为当前操作执行者层级的直接子层级的管理者做好准备。
			 * 
			 * 而如果被查看的用户与当前操作者层级没有直属关系，则user4Ajax.member ==null
			 *
			 * 具体操作步骤如下：
			 */
			// ①通过tag和lid可以确定当前操作者的层级类型和lid
			/*
			 * ②由于被查看用户必定先为当前操作者层级的“直辖人员”或子孙层级的成员，
			 * 因此必定存在member记录着操作者层级的数据信息，我们要做的就是遍历然后找到定位它，
			 * 然后进一步判断这个保存当前操作者层级信息的member是否是操作者层级的直辖人员信息对象
			 * 如果是则设置到User4Ajax.member位
			 */
			for (Member m : user.getMembers()) {
				switch (tag) {
				case "minus_first":
					/*
					 * 判断标准解读： 如果便利出的member存在MinusFirstLevel层级对象，且该层级对象与操作者对象（
					 * 同样也是MinusFirstLevel层级）的lid相同
					 * 并且MinusFirst次一级，也就是Zero级对象zeroLevel为null，
					 * 则说明当前遍历出来的member代表的是用户所加入的一个
					 * MinusFirst层级且这个MinusFirst层级就是当前操作者层级（因为lid相同）
					 */
					if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)
							&& null == m.getZeroLevel()) {
						u.setMember(m);
					}
					break;
				case "zero":
					if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid)
							&& null == m.getFirstLevel()) {
						u.setMember(m);
					}
					break;
				case "first":
					if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)
							&& null == m.getSecondLevel()) {
						u.setMember(m);
					}
					break;
				case "second":
					if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)
							&& null == m.getThirdLevel()) {
						u.setMember(m);
					}
					break;
				case "third":
					if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)
							&& null == m.getFourthLevel()) {
						u.setMember(m);
					}
					break;
				case "fourth":
					if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
						u.setMember(m);
					}
					break;

				default:
					// 对于admin来说，由于所有用户必定永远存在默认member，因此都是admin直辖对象
					if (null == m.getMinusFirstLevel() && null == m.getZeroLevel() && null == m.getFirstLevel()
							&& null == m.getSecondLevel() && null == m.getThirdLevel() && null == m.getFourthLevel()) {
						u.setMember(m);
					}
					break;
				}
			}

			/**
			 * managers中存放的是用户作为操作者层级的直辖人员对于其直接自层级的管理信息
			 * 如果用户是当前操作者层级的直辖人员（u.member!=null），则可以从中找到该用户的所有任职情况
			 */
			if (null != u.getMember()) {
				List<Manager> managers = u.getMember().getManagers();
				u.setManagers(managers);
			}
		}

		// -----------根据操作者的层级对象不同，来设置被索取的用户的tag数据信息----------
		ArrayList<String> tagsList = null;
		// 注意只有该用户是当前操作者层级的直辖人员，才有权利更改其member.grouping.tag 否则不可以
		if (null != u.getMember()) {
			tagsList = new ArrayList<String>();
			if (isAdmin) {
				/*
				 * adming管理员用户有权分配街道层级用户，而不能越级分配，防止出现人员在层级结构中的混乱（
				 * 明明是某个第四层及的member， 却被分配了社区层级的tag，这是不合理的）
				 * 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
				 */
				tagsList.add("unreal");
				tagsList.add("common");
				tagsList.add("minus_first");
			} else {
				/*
				 * 同理 非admin，则根据实际情况来设置tags（只能设置低于当前操作者层次的tag）
				 * ，而不能越级分配，防止出现人员在层级结构中的混乱（明明是某个第四层及的member，却被分配了社区层级的tag，
				 * 这是不合理的） 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
				 */
				switch (tag) {
				case "minus_first":
					tagsList.add("zero");
					tagsList.add("common");
					break;
				case "zero":
					tagsList.add("first");
					tagsList.add("common");
					break;
				case "first":
					tagsList.add("second");
					tagsList.add("common");
					break;
				case "second":
					tagsList.add("third");
					tagsList.add("common");
					break;
				case "third":
					tagsList.add("fourth");
					tagsList.add("common");
					break;
				case "fourth":
					tagsList.add("common");
					break;
				}
			}
		}

		/*
		 * 当需要将List转化成数组Array的时候是需要像如下方式实现的， 给ArrayList.toArray()传递一个数组实例作为参数。★
		 */
		String[] tags = (String[]) tagsList.toArray(new String[0]);
		u.setTags(tags);

		// 最后就是将user中的常规属性设置到user4ajax上即可
		u.setAddress(user.getAddress());
		u.setAge(user.getAge());
		u.setBirth(user.getBirth());
		u.setCardid(user.getCardid());
		u.setEmail(user.getEmail());
		u.setIshere(user.isIshere());
		u.setLocked(user.isLocked());
		u.setOpenid(user.getOpenid());
		u.setPhone(user.getPhone());
		u.setRegistrationTime(user.getRegistrationTime());
		u.setScore(user.getScore());
		u.setServeTime(user.getServeTime());
		u.setSex(user.getSex());
		u.setSickname(user.getSickname());
		u.setUid(user.getUid());
		u.setUsername(user.getUsername());

		// userService.clearSession();
		ActionContext.getContext().getValueStack().push(u);
		return "json";
	}

	/**
	 * TODO 派遣功能待定 Ajax 从前端接收uid（被指派的人员uid）, level（被指派到的层级对象的层级）,
	 * lid（被指派到的层级对象的id） 实现用户到该层级的派遣功能
	 * 
	 * @return
	 */
	// public String assignedUser() {
	//
	// String uid = user.getUid();
	// int level = this.getLevel();
	// String lid = this.getLid();
	//
	// ReturnMessage4Common result = new ReturnMessage4Common("人员派遣成功！", true);
	// User u = null;
	//
	// switch (level) {
	// case -1:
	// MinusFirstLevel minusFirstLevel =
	// minusFirstLevelService.queryEntityById(lid);
	// if (minusFirstLevel == null) {
	// result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
	// result.setResult(false);
	// }
	// u = userService.queryEntityById(uid);
	// u.getMember().setMinusFirstLevel(minusFirstLevel);
	// userService.update(u);
	// break;
	// case 0:
	// ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
	// if (zeroLevel == null) {
	// result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
	// result.setResult(false);
	// }
	// u = userService.queryEntityById(uid);
	// u.getMember().setZeroLevel(zeroLevel);
	// userService.update(u);
	// break;
	// case 1:
	// FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
	// if (firstLevel == null) {
	// result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
	// result.setResult(false);
	// }
	// u = userService.queryEntityById(uid);
	// u.getMember().setFirstLevel(firstLevel);
	// userService.update(u);
	// break;
	// case 2:
	// SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
	// if (secondLevel == null) {
	// result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
	// result.setResult(false);
	// }
	// u = userService.queryEntityById(uid);
	// u.getMember().setSecondLevel(secondLevel);
	// userService.update(u);
	// break;
	// case 3:
	// ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
	// if (thirdLevel == null) {
	// result.setMessage("不存在lid为" + lid + "的层级对象，指派失败");
	// result.setResult(false);
	// }
	// u = userService.queryEntityById(uid);
	// u.getMember().setThirdLevel(thirdLevel);
	// userService.update(u);
	// break;
	// default:
	// result.setMessage("不存在指定层级为" + level + "的层级对象，人员派遣失败");
	// result.setResult(false);
	// break;
	// }
	//
	// ActionContext.getContext().getValueStack().push(result);
	// return "json";
	// }

	/**
	 * TODO 派遣功能先待定 AJAX 向前端返回当前操作者层级对象,以便于前端从中通过allChildren4Ajax获取可以指派人员的直接子层级
	 * 
	 * @return
	 */
	// public String showUserAssignedModal() {
	//
	// // ---------------------------Shiro认证操作者身份---------------------------
	// Subject subject = SecurityUtils.getSubject();
	// String principal = (String) subject.getPrincipal();
	// // 执行当前新建操作的管理者的User对象
	// User doingMan = null;
	// // 标记当前执行者是否是admin
	// boolean isAdmin = false;
	// if (28 == principal.length()) {
	// // openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
	// doingMan = userService.queryByOpenId(principal);
	// } else {
	// // 用户名登陆（通过signin.jsp页面的表单提交的登陆）
	// // 先判断是不是使用admin+admin 的方式登录的测试管理员
	// if ("admin".equals(principal)) {
	// isAdmin = true;
	// } else {
	// // 非admin用户登录
	// doingMan = userService.getUserByUsername(principal);
	// }
	// }
	//
	// String tag = (String)
	// ServletActionContext.getRequest().getSession().getAttribute("tag");
	// String lid = (String)
	// ServletActionContext.getRequest().getSession().getAttribute("lid");
	// if (isAdmin) {
	// // 管理员，则将系统中的所有MinusFirstLevel层级对象返回给前端
	// List<MinusFirstLevel> queryEntities =
	// minusFirstLevelService.queryEntities();
	// ActionContext.getContext().getValueStack().push(queryEntities);
	// } else {
	// // 非管理员
	// // 分析出当前操作者掌管的层级对象，进而获取其全部子层级
	// switch (tag) {
	// case "minus_first":
	// MinusFirstLevel level_1 = minusFirstLevelService.queryEntityById(lid);
	// ActionContext.getContext().getValueStack().push(level_1);
	// break;
	// case "zero":
	// ZeroLevel level0 = zeroLevelService.queryEntityById(lid);
	// ActionContext.getContext().getValueStack().push(level0);
	// break;
	// case "first":
	// FirstLevel level1 = firstLevelService.queryEntityById(lid);
	// ActionContext.getContext().getValueStack().push(level1);
	// break;
	// case "second":
	// SecondLevel level2 = secondLevelService.queryEntityById(lid);
	// ActionContext.getContext().getValueStack().push(level2);
	// break;
	// case "third":
	// ThirdLevel level3 = thirdLevelService.queryEntityById(lid);
	// ActionContext.getContext().getValueStack().push(level3);
	// break;
	// }
	// }
	// return "json";
	// }

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
		List<Member> members = u.getMembers();
		// 这个member就是被修改用户作为当前操作者层级的直属成员的member，其中grouping就就保存与此
		Member member = null;

		u.setUsername(username);
		u.setSickname(sickname);
		u.setCardid(cardid);
		u.setAge(age);
		u.setPhone(phone);
		u.setEmail(email);
		u.setAddress(address);
		u.setSex(sex);

		List<Grouping> list = groupingService.queryEntities();

		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		for (Member m : members) {
			switch (levelTag) {
			case "minus_first":
				if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)
						&& null == m.getZeroLevel()) {
					member = m;
				}
				break;
			case "zero":
				if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid) && null == m.getFirstLevel()) {
					member = m;
				}
				break;
			case "first":
				if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)
						&& null == m.getSecondLevel()) {
					member = m;
				}
				break;
			case "second":
				if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)
						&& null == m.getThirdLevel()) {
					member = m;
				}
				break;
			case "third":
				if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)
						&& null == m.getFourthLevel()) {
					member = m;
				}
				break;
			case "fourth":
				if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
					member = m;
				}
				break;

			// 如果当前操作者执行者为admin用户
			case "admin":
			default:
				if (null == m.getMinusFirstLevel()) {
					member = m;
				}
				break;
			}
		}
		// 修改grouping的tag标签
		for (Grouping g : list) {
			if (g.getTag().equals(tag)) {
				member.setGrouping(g);
				break;
				// String openid = u.getOpenid();
				// if (org.springframework.util.StringUtils.isEmpty(openid)) {
				// break;
				// }
				/*
				 * 接入微信平台后需要开启这部分代码用来修改微信公众号的该用户的tag
				 */
				// String[] ids = { openid };
				// try {
				// weixinService4Setting.getUserTagService().batchTagging(g.getTagid(),
				// ids);
				// } catch (WxErrorException e) {
				// e.printStackTrace();
				// }
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

	/**
	 * 获取某个用户用积分兑换奖品的记录列表
	 * 
	 * @return
	 */
	public String getExchangeList() {

		// String uid = this.getModel().getUid();
		// 切记一定要不这样获取数据驱动的请求参数，getModal()是给Struts框架使用的，不是给人使用的★★★★
		String uid = this.user.getUid();
		User u = userService.queryEntityById(uid);
		List<Exchange> list = u.getExchanges();

		ActionContext.getContext().put("exchanges", list);
		return "exchangeList";
	}

	/**
	 * 手动（非通过微信公众号关注）新建的用户
	 * 
	 * @return
	 */
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
		// 所有新建用户的tag默认都是common，如果需要提升到某个管理层需要更高级的管理员手动修改
		Grouping g = groupingService.queryByTagName("common");
		member.setGrouping(g);
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
			String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			switch (tag) {
			case "minus_first":
				// 街道层级管理者执行的创建用户操作
				MinusFirstLevel level = minusFirstLevelService.queryEntityById(lid);
				if (null == level) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
				}
				member.setMinusFirstLevel(level);
				break;
			case "zero":
				// 社区层级管理者执行的创建用户操作
				ZeroLevel level0 = zeroLevelService.queryEntityById(lid);
				if (null == level0) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
				}
				member.setZeroLevel(level0);
				member.setMinusFirstLevel(level0.getParent());
				break;
			case "first":
				// 第一层级管理者执行的创建用户操作
				FirstLevel level1 = firstLevelService.queryEntityById(lid);
				if (null == level1) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
				}
				member.setFirstLevel(level1);
				member.setZeroLevel(level1.getParent());
				member.setMinusFirstLevel(level1.getParent().getParent());
				break;
			case "second":
				// 第二层级管理者执行的创建用户操作
				SecondLevel level2 = secondLevelService.queryEntityById(lid);
				if (null == level2) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
				}
				member.setSecondLevel(level2);
				member.setFirstLevel(level2.getParent());
				member.setZeroLevel(level2.getParent().getParent());
				member.setMinusFirstLevel(level2.getParent().getParent().getParent());
				break;
			case "third":
				// 第三层级用户执行的创建用户操作
				ThirdLevel level3 = thirdLevelService.queryEntityById(lid);
				if (null == level3) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
				}
				member.setThirdLevel(level3);
				member.setSecondLevel(level3.getParent());
				member.setFirstLevel(level3.getParent().getParent());
				member.setZeroLevel(level3.getParent().getParent().getParent());
				member.setMinusFirstLevel(level3.getParent().getParent().getParent().getParent());
				break;
			case "fourth":
				// 第四层级用户执行的创建用户操作
				FourthLevel level4 = fourthLevelService.queryEntityById(lid);
				if (null == level4) {
					message.setResult(false);
					message.setMessage("当前操作者层级不存，新建失败");
					ActionContext.getContext().getValueStack().push(message);
					return "json";
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
		List<Member> list = new ArrayList<Member>();
		u.setMembers(list);
		u.getMembers().add(member);
		userService.save(u);

		ActionContext.getContext().getValueStack().push(message);
		return "json";
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
		// 被任命人员在当前操作者层级中的member成员对象
		Member member = null;
		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		for (Member m : u.getMembers()) {
			switch (levelTag) {
			case "minus_first":
				if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)
						&& null == m.getZeroLevel()) {
					member = m;
				}
				break;
			case "zero":
				if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid) && null == m.getFirstLevel()) {
					member = m;
				}
				break;
			case "first":
				if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)
						&& null == m.getSecondLevel()) {
					member = m;
				}
				break;
			case "second":
				if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)
						&& null == m.getThirdLevel()) {
					member = m;
				}
				break;
			case "third":
				if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)
						&& null == m.getFourthLevel()) {
					member = m;
				}
				break;
			case "fourth":
				if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
					member = m;
				}
				break;

			default:
				if (null == m.getMinusFirstLevel()) {
					member = m;
				}
				break;
			}
		}
		// 得到待任命者的tag
		String t = member.getGrouping().getTag();
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
			switch (levelTag) {
			// 将当前操作执行者绑定的层级对象，放入到result中返回给前端，方便前端获取通过children4Ajax获取次一级层级对象，来补充select的option选项
			case "minus_first":
				controllerNum = -1;
				result.setMinusFirst(minusFirstLevelService.queryEntityById(lid));
				break;
			case "zero":
				controllerNum = 0;
				result.setZero(zeroLevelService.queryEntityById(lid));
				break;
			case "first":
				controllerNum = 1;
				result.setFirst(firstLevelService.queryEntityById(lid));
				break;
			case "second":
				controllerNum = 2;
				result.setSecond(secondLevelService.queryEntityById(lid));
				break;
			case "third":
				controllerNum = 3;
				result.setThird(thirdLevelService.queryEntityById(lid));
				break;
			}
		}

		// -----------------------开始准备向前端发送的数据---------------------------
		if (10086 == lowest) {
			result.setMessage("错误：待委任者的层级不在系统支持的管理层级可选范围内");
			result.setResult(false);
		} else if (10086 == controllerNum) {
			// 当前操作是Admin操作的，应该将所有minusFirst层级对象(admin也只能委任这一个层级)的数据信息获取出来
			List<MinusFirstLevel> minusLevels = minusFirstLevelService.queryEntities();
			// 填入数据
			result.setMessage("执行当前操作的是Admin");
			result.setResult(true);
			result.setControllerNum(controllerNum);
			result.setLowest(lowest);
			result.setMinusLevels(minusLevels);
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
	 * 解除任命
	 * 
	 * @return
	 */
	public String doDisappoint() {
		ReturnMessage4Common result = new ReturnMessage4Common("解任成功！", true);

		String managerid = this.managerid;
		Manager manager = managerService.queryEntityById(managerid);
		Member member = manager.getMember();
		boolean removeResult = member.getManagers().remove(manager);
		if (removeResult) {
			// 移除成功
			manager.setMember(null);
			managerService.delete(manager);
			memberService.update(member);
		} else {
			// 移除失败
			result.setResult(false);
			result.setMessage("卸任失败！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX 正式执行用户（manager）与层级对象的绑定（委任）操作 从前端提交过来三个请求参数 uid——被任命者的uid
	 * level———被任命的层级对象的层级（-1、0、1、2、3、4） lid————被任命的层级对象的lid
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
		User u = userService.queryEntityById(uid);
		// 当前操作者
		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String levellid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 记录被任命用户在当前操作者层级之下的直接成员对象
		Member member = null;
		for (Member m : u.getMembers()) {
			switch (levelTag) {
			case "minus_first":
				if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(levellid)
						&& null == m.getZeroLevel()) {
					member = m;
				}
				break;
			case "zero":
				if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(levellid)
						&& null == m.getFirstLevel()) {
					member = m;
				}
				break;
			case "first":
				if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(levellid)
						&& null == m.getSecondLevel()) {
					member = m;
				}
				break;
			case "second":
				if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(levellid)
						&& null == m.getThirdLevel()) {
					member = m;
				}
				break;
			case "third":
				if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(levellid)
						&& null == m.getFourthLevel()) {
					member = m;
				}
				break;
			case "fourth":
				if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(levellid)) {
					member = m;
				}
				break;

			default:
				if (null == m.getMinusFirstLevel()) {
					member = m;
				}
				break;
			}
		}
		// 新建的manager
		Manager m = null;
		// 存放返回数据信息
		ReturnMessage4Common result = null;
		// 作为member.managers 和 层级类型.managers的容器对象
		List<Manager> managers = null;
		switch (level) {
		case -1:
			// 定位被任命的对象
			MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getMinusFirstLevel().getMflid().equals(minusFirst.getMflid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setMinusFirstLevel(minusFirst);
			managers = minusFirst.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				minusFirst.setManagers(managers);
			}
			managers.add(m);
			break;
		case 0:
			// 定位被任命的对象
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getZeroLevel().getZid().equals(zeroLevel.getZid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setZeroLevel(zeroLevel);
			managers = zeroLevel.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				zeroLevel.setManagers(managers);
			}
			managers.add(m);
			break;
		case 1:
			// 定位被任命的对象
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getFirstLevel().getFlid().equals(firstLevel.getFlid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setFirstLevel(firstLevel);
			managers = firstLevel.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				firstLevel.setManagers(managers);
			}
			managers.add(m);
			break;
		case 2:
			// 定位被任命的对象
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getSecondLevel().getScid().equals(secondLevel.getScid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setSecondLevel(secondLevel);
			managers = secondLevel.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				secondLevel.setManagers(managers);
			}
			managers.add(m);
			break;
		case 3:
			// 定位被任命的对象
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getThirdLevel().getThid().equals(thirdLevel.getThid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setThirdLevel(thirdLevel);
			managers = thirdLevel.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				thirdLevel.setManagers(managers);
			}
			managers.add(m);
			break;
		case 4:
			// 定位被任命的对象
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			// 遍历被任命者的member.manager中是否已经存在该层级了
			for (Manager manager : member.getManagers()) {
				if (manager.getFourthLevel().getFoid().equals(fourthLevel.getFoid())) {
					result = new ReturnMessage4Common("请勿重复任命", false);
					ActionContext.getContext().getValueStack().push(result);
					return "json";
				}
			}
			m = new Manager();
			m.setMember(member);
			managers = member.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				member.setManagers(managers);
			}
			managers.add(m);

			m.setFourthLevel(fourthLevel);
			managers = fourthLevel.getManagers();
			if (null == managers) {
				managers = new ArrayList<Manager>();
				fourthLevel.setManagers(managers);
			}
			managers.add(m);
			break;
		}
		// 好了，数据准备完毕可以创建新的manager了
		managerService.save(m);

		result = new ReturnMessage4Common("委任成功", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 这个方法并未被选用，因为现行功能原则是层级对象管理者只能管理自己直属的成员（例如任命工作） 不能越级行事，否则组织层级化就没有意义了
	 * 
	 * 本方法对应前端的managerModal.op.changeAppointSelect()中的Ajax请求
	 * 根据操作者在managerList.jsp的AppointModal中的选择情况（由id=appoint-1~
	 * 4的Select的onchange事件触发本方法） 然后更新下一级的以及后续层级（直到fourthLevel）中的显示内容（option选项）
	 * level:int类型 触发onchange事件的select对应的层级（也就是其id=appointX中的X的数字） lid：
	 * 为操作者选中的层级对象的id
	 */
	// public String getAppointSelectInfo() {
	//
	// int level = this.getLevel();
	// String lid = this.getLid();
	// if (lid.isEmpty() || level > 4 || level < -1) {
	// ReturnMessage4Common result = new ReturnMessage4Common();
	// result.setMessage("必要参数level或lid为NULL，操作不予执行");
	// result.setResult(false);
	// ActionContext.getContext().getValueStack().push(result);
	// } else {
	// ReturnMessage4Appoint result = new ReturnMessage4Appoint();
	// switch (level) {
	// case -1: // 从街道层级中查找
	// MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
	// if (null == minusFirst) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的MinusFirstLevel层级对象已获取");
	// result.setResult(true);
	// result.setMinusFirst(minusFirst);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// case 0: // 从社区层级中查找
	// ZeroLevel zero = zeroLevelService.queryEntityById(lid);
	// if (null == zero) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的ZeroLevel层级对象已获取");
	// result.setResult(true);
	// result.setZero(zero);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// case 1: // 从第一层级中查找
	// FirstLevel first = firstLevelService.queryEntityById(lid);
	// if (null == first) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的FirstLevel层级对象已获取");
	// result.setResult(true);
	// result.setFirst(first);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// case 2: // 从第二层级中查找
	// SecondLevel second = secondLevelService.queryEntityById(lid);
	// if (null == second) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的SecondLevel层级对象已获取");
	// result.setResult(true);
	// result.setSecond(second);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// case 3: // 从第三层级中查找
	// ThirdLevel third = thirdLevelService.queryEntityById(lid);
	// if (null == third) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的ThirdLevel层级对象已获取");
	// result.setResult(true);
	// result.setThird(third);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// case 4: // 从第四层级中查找
	// FourthLevel fourth = fourthLevelService.queryEntityById(lid);
	// if (null == fourth) {
	// result.setMessage("不存在id为：" + lid + "的层级对象");
	// result.setResult(false);
	// } else {
	// result.setMessage("查询的FourthLevel层级对象已获取");
	// result.setResult(true);
	// result.setFourth(fourth);
	// }
	// ActionContext.getContext().getValueStack().push(result);
	// break;
	// default:
	// ReturnMessage4Common result2 = new ReturnMessage4Common();
	// result.setMessage("level的数值超出系统层级范围");
	// result.setResult(false);
	// ActionContext.getContext().getValueStack().push(result2);
	// break;
	// }
	// }
	// return "json";
	// }

}
