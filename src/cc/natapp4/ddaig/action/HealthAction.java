package cc.natapp4.ddaig.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cc.natapp4.ddaig.bean.GetData4UserListSelectors;
import cc.natapp4.ddaig.bean.Init4UserListSelectors;
import cc.natapp4.ddaig.bean.User4Ajax;
import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_implement.health.FactorResult4Sample4EnclosedScaleServiceImpl;
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
import cc.natapp4.ddaig.service_interface.health.EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Factor4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Option4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.OptionGroup4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Sample4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Section4Factor4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Topic4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.TopicResult4FactorResult4Sample4EnclosedScaleService;
import cc.natapp4.ddaig.utils.FileController;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import cn.com.obj.freemarker.ExportDoc;

@Controller("healthAction")
@Scope("prototype")
@Lazy(true)
public class HealthAction extends ActionSupport{

	private static final long serialVersionUID = 600271725750065543L;
	// ==========================================================DI注入Aspect
	@Autowired
	private UserService userService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private GroupingService groupingService;
	// -----------------层级相关----------------
	@Autowired
	private MinusFirstLevelService minusFirstLevelService;
	@Autowired
	private ZeroLevelService zeroLevelService;
	@Autowired
	private FirstLevelService firstLevelService;
	@Autowired
	private SecondLevelService secondLevelService;
	@Autowired
	private ThirdLevelService thirdLevelService;
	@Autowired
	private FourthLevelService fourthLevelService;
	@Autowired
	private WeixinService4SettingImpl weixinService4Setting;
	// -----------------健康相关----------------
	@Autowired
	private EnclosedScaleService  enclosedScaleService;
	@Autowired
	private Factor4EnclosedScaleService factor4EnclosedScaleService;
	@Autowired
	private FactorResult4Sample4EnclosedScaleServiceImpl factorResult4Sample4EnclosedScaleService;
	@Autowired
	private Option4EnclosedScaleService option4EnclosedScaleService;
	@Autowired
	private OptionGroup4EnclosedScaleService optionGroup4EnclosedScaleService;
	@Autowired
	private Sample4EnclosedScaleService sample4EnclosedScaleService;
	@Autowired
	private Section4Factor4EnclosedScaleService section4Factor4EnclosedScaleService;
	@Autowired
	private Topic4EnclosedScaleService topic4EnclosedScaleService;
	@Autowired
	private TopicResult4FactorResult4Sample4EnclosedScaleService topicResult4FactorResult4Sample4EnclosedScaleService;
	
	

	// ======================================================属性驱动——向前端页面传送经过处理的数据信息
	private String errorMessage; // 用作error全局结果集指定的页面——error.jsp中显示错误的信息内容
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/*
	 * (1) getData4Selector()
	 */
	private String tag;
	private String lid;
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}

	// ---------------------下载用属性-------------------
	private String fileName; // 下载用的存放文件名
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 下载数据流
	// 该inputStream属性将作为在struts-article.xml中，名为download的结果集中所使用的，用来向前端提供下载
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	 * 该方法是目前本系统的入口方法 与getManagerList()方法相对 供给后台用户管理系统使用，获取所有用户群体（包括直辖和子孙层级的直辖）
	 * 
	 * 本方法响应 userList.jsp页面的展示请求当前操作者层级的“非直辖人员”
	 * 
	 * @return 结果集索引字符串
	 */
	public String getUsers() {

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

		/**
		 * 不同于普通类中通过添加在web.xml中添加RequestContextListerner监听器后就可以在任何类中
		 * 通过执行   
		 *  HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 *  HttpSession session  =  request.getSession();
		 * 就能获取到Request和session对象
		 * 
		 * 而如果是Action类，就只需要通过在类内随时调用  ServletActionContext.getRequest.getSession(); 就能得到session了
		 *  
		 */
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// --------------------------开始根据操作人的层级来获取所辖用户（Admin获取所有用户）---------------------------
		List<User> users = null;
		if (isAdmin) {
			// 如果是管理员则可以查看系统中的所有用户
			users = userService.queryEntities();
		} else {
			// 如果当前层级（tag、lid）不是管理员，则只能获取当前操作者层级的子孙层级的管辖人员（包括每个子孙层级的直辖+非直辖）
			users = userService.getChildrenLevelUsers(tag, lid);
		}

		// 将数据库中保存的关于注册日期的格里高利里毫秒值偏移量调整成人类可阅读的yyyy-MM-dd HH:mm:ss形式
		if (null != users) {
			// 如果当前查看的层级有用户，则修改用户的注册日期显示，否则就没必要了
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (User u : users) {
				u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));
			}
		}
		// 放入到值栈中的map栈中，以共给userList.jsp中通过struts标签显示出来
		ActionContext.getContext().put("users", users);
		return "users";
	}

	

	// ===============配套userList.jsp页面的selector筛选用户功能的系列方法======================
	/**
	 * 当用户请求users.jsp页面的时候，会在也买你通过$(function(){//...}); 进行初始化工作
	 * 通过AJAX请求本方法，用以初始化userList.jsp页面上用于过滤用户的selector的panel面板。
	 * 
	 * TODO 分页查询
	 * 
	 * @return
	 */
	public String initSelector() {
		Init4UserListSelectors init = new Init4UserListSelectors();
		// ---------------------------Shiro认证操作者身份---------------------------
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			}
		}
		// 开始组织初始化数据用的domain
		if (isAdmin) {
			// 当前访问userList.jsp页面的是系统管理员
			// 系统中获取所有MinusFirstLevel层级对象
			List<MinusFirstLevel> queryEntities = minusFirstLevelService.queryEntities();
			init.setMinusFirstLevels(queryEntities);
			// 设置tag标记属性为"admin"
			init.setTag("admin");
		} else {
			// 当前访问userList.jsp页面的是层级管理者
			// 聚焦当前操作者的层级对象
			String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			switch (tag) {
			case "minus_first":
				// 当前层级管理者为minus_first
				MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
				init.setTag("minus_first");
				init.setMinusFirstLevel(minusFirstLevel);
				init.setZeroLevels(minusFirstLevel.getAllChildren4Ajax());
				break;
			case "zero":
				// 当前层级管理者为zeroLevel
				ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
				init.setTag("zero");
				init.setZeroLevel(zeroLevel);
				init.setFirstLevels(zeroLevel.getAllChildren4Ajax());
				break;
			case "first":
				// 当前层级管理者为firstLevel
				FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
				init.setTag("first");
				init.setFirstLevel(firstLevel);
				init.setSecondLevels(firstLevel.getAllChildren4Ajax());
				break;
			case "second":
				// 当前层级管理者为secondLevel
				SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
				init.setTag("second");
				init.setSecondLevel(secondLevel);
				init.setThirdLevels(secondLevel.getAllChildren4Ajax());
				break;
			case "third":
				// 当前层级管理者为thirdLevel
				ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
				init.setTag("third");
				init.setThirdLevel(thirdLevel);
				init.setFourthLevels(thirdLevel.getAllChildren4Ajax());
				break;
			case "fourth":
				// 当前层级管理者为fourthLevel
				FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
				init.setTag("fourth");
				init.setFourthLevel(fourthLevel);
				break;
			}
		}

		ActionContext.getContext().getValueStack().push(init);
		return "json";
	}

	
	/**
	 * AJAX 当users.jsp页面上，操作者通过selector下拉选项页面筛选层级用户的时候，都会onclick事件 触发
	 * userModal.op.getData4Selector()方法中的ajax来请求本方法，用以获取子selector和其之下
	 * 的所有用户的数据信息，返回给前端显示出来。
	 * 
	 * @return
	 */
	public String getData4Selector() {
		// 整个后台系统，就是通过如下方法获取到当前操作者的层级和层级ID的，用于精准定位当前层级
		String myTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String myLid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 基于属性驱动，获取从前端获取的请求参数，用以表达前端索要数据的意图
		String tag = this.tag;
		String lid = this.lid;
		// 准备通过JSON方式返回给前端的Bean
		GetData4UserListSelectors bean = new GetData4UserListSelectors();

		// 开始分析前端所要的数据
		switch (tag) {
		// 当userList.jsp中的minusFirst的selector选择“--请选择--”时，执行本分支获取系统全部用户数据显示在前端
		case "all": // 索要系统平台中的所有用户数据
			// 只有admin用户才能获取所有用户数据，出于安全考虑我们需要校验当前操作者是否真的是admin
			if ("admin".equals(myTag)) {
				// 当前操作者的确是admin，可以授权获取全部用户数据
				bean.setResult(true);
				bean.setUsers(userService.queryEntities());
			} else {
				// 当前请求为非法，驳回
				bean.setResult(false);
				bean.setMessage("非法请求，您不是administrator用户，请求被驳回");
			}
			break;
		// 当userList.jsp中的
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			// 准备子selector的数据
			/*
			 * ⭐⭐⭐⭐⭐ 一个常见的错误 ⭐⭐⭐⭐⭐ 这是一个常见的问题，本次请求处理分支需要在一次Hibernate事务中进行 三次查询，
			 * （1）从minusFirstLevel中获取所有子层级，基于Hibernate配置文件
			 * 设置了延迟加载，因此当我们通过minusFirstLevel.getChildren()的时候会调用查找语句获取数据
			 * （2）userService.getChildrenLevelUsers() 查询指定层级的非直辖用户数据
			 * （3）userService.getManagers() 查询指定层级的直辖用户数据
			 * 
			 * 因为之前在第一个查询中我们使用了minusFirstLevel.getAllChildren4Ajax() 这个方法会在内部断开
			 * 所有zeroLevel和minusFirstLevel的外键关联，但并不提交到数据库，仅仅用于向前端返回时避开可能
			 * 的基于JSON解析的死循环，但由于之后好要在同一个事务中进行两次查询，hibernate会将第一次查询
			 * 结果的持久化状态对象如果有修改的化提交到数据库后再进行下一个查询，这就导致刚刚我们切断了 与父层级的外键关联也被保存到了数据库中。
			 * 解决办法就是在父层级中关于子层级的集合属性上加上@JSON(serialize=false) 这样就会避免JSON
			 * 解析的时候通过父层级去解析该容器中的全部子层级了，从而避免了死循环的产生，因此我们在第一次
			 * 查询时直接使用minusFirstLevel.getChildren() 即可。
			 */
			bean.setZeroLevels(new ArrayList(minusFirstLevel.getChildren()));
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;

		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			bean.setFirstLevels(new ArrayList(zeroLevel.getChildren()));
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;

		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			bean.setSecondLevels(new ArrayList(firstLevel.getChildren()));
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;

		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			bean.setThirdLevels(new ArrayList(secondLevel.getChildren()));
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;

		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			bean.setFourthLevels(new ArrayList(thirdLevel.getChildren()));
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;

		case "fourth":
			// fourth部分只有user数据，没有子层级数据了
			// 准备users的数据
			bean.setUsers(userService.getAllLevelUsers(tag, lid));
			break;
		}

		ActionContext.getContext().getValueStack().push(bean);
		return "json";
	}


	
	// =================================EnclosedScale封闭式问卷功能区=======================================
	/**
	 * 跳转到创建“封闭式问卷”的JSP页面——createEnclosedScalePage.jsp
	 * @return
	 */
	public String toCreateEnclosedScalePage() {
		
		
		
		
		return "createEnclosedScalePage";
	}
	
	
	/**
	 * AJAX
	 * 执行创建封闭式量表的操作
	 * @return
	 */
	public String createEnclosedScale() {
		ReturnMessage4Common  result =  new ReturnMessage4Common();  
		
		
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	
	/**
	 * 跳转到所有封闭式量表的列表JSP页面——enclosedScaleListPage.jsp
	 * @return
	 */
	public String toEnclosedScaleListPage() {
		
		
		
		return "enclosedScaleListPage";
	}
	
	
	/**
	 * 跳转到封闭式量表的JSP页面——enclosedScalePage.jsp，进行数据采集
	 * @return
	 */
	public String toEnclosedScalePage() {
		
		
		
		return "enclosedScalePage";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// =================================基于Freemaker下载电子报告的功能区=======================================
	/**
	 * 下载用于批量创建的Excel模板文件
	 * 
	 * @return
	 * @throws IOException
	 */
	public String downloadExcel4BatchCreate() throws IOException {

		// --------------------------------准备下载---------------------------------
		String fullPath = ServletActionContext.getServletContext()
				.getRealPath(File.separator + "download" + File.separator + "batchUser.xlsx");
		File file = new File(fullPath);
		if (!file.exists()) {
			// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
			this.errorMessage = "路径：" + fullPath + "下，不存在指定文件，请稍后再试！";
			// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
			return "error";
		}
		// 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
		FileInputStream fis = new FileInputStream(file);
		// 准备字节数组（字节缓冲区）输出流备用
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		// 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
		byte[] buff = new byte[1024];
		int rc = 0;
		while ((rc = fis.read(buff, 0, 1024)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
		this.inputStream = new ByteArrayInputStream(swapStream.toByteArray());
		// 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
		fis.close();
		swapStream.close();
		// ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
		String s = "批量创建新用户的Excel模板文件.xlsx";
		this.fileName = new String(s.getBytes(), "ISO8859-1");

		return "download";
	}

	/**
	 * 用于生成某个用户的健康档案的DOC文档并提供下载
	 * @return
	 * @throws Exception
	 */
	public String downloadUserHealthReport() throws Exception {
		// ----------------------------准备数据-----------------------------
		// 先找到当前管理者的层级对象
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		// 查找出层级直辖的全部人员
		Set<Member> members = null;
		String levelName = "";
		String levelTag = "";
		String levelQrcodePath = "";
		switch (tag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			if (null == minusFirstLevel) {
				// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = minusFirstLevel.getMembers();
			levelName = minusFirstLevel.getName();
			levelTag = "街道级";
			levelQrcodePath = minusFirstLevel.getQrcode();
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = zeroLevel.getMembers();
			levelName = zeroLevel.getName();
			levelTag = "社区级";
			levelQrcodePath = zeroLevel.getQrcode();
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			if (null == firstLevel) {
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = firstLevel.getMembers();
			levelName = firstLevel.getName();
			levelTag = "第一级";
			levelQrcodePath = firstLevel.getQrcode();
			break;
		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			if (null == secondLevel) {
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = secondLevel.getMembers();
			levelName = secondLevel.getName();
			levelTag = "第二级";
			levelQrcodePath = secondLevel.getQrcode();
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			if (null == thirdLevel) {
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = thirdLevel.getMembers();
			levelName = thirdLevel.getName();
			levelTag = "第三级";
			levelQrcodePath = thirdLevel.getQrcode();
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			if (null == fourthLevel) {
				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
				return "error";
			}
			members = fourthLevel.getMembers();
			levelName = fourthLevel.getName();
			levelTag = "第四级";
			levelQrcodePath = fourthLevel.getQrcode();
			break;
		}

		if (null == members) {
			this.errorMessage = "当前层级的成员容器（members）为null";
			return "error";
		} else if (0 == members.size()) {
			this.errorMessage = "当前层级的成员数量为0，无法生成电子台账";
			return "error";
		}
		// 解析出每个member对应的user，要保持唯一性
		Set<User> users = new HashSet<User>();
		for (Member m : members) {
			users.add(m.getUser());
		}

		// ----------------------------准备生成DOC文档-----------------------------
		String fullPath;
		ExportDoc maker = new ExportDoc("UTF-8");
		fullPath = "download" + File.separator + "userLedger";
		FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fullPath));
		fullPath += File.separator + levelName + "人员电子台帐.docx";
		fullPath = ServletActionContext.getServletContext().getRealPath(fullPath);
		maker.exportDoc4UserLedger(levelName, levelTag, levelQrcodePath, users, fullPath, "userLedger.ftl");

		// --------------------------------准备下载---------------------------------
		File file = new File(fullPath);
		if (!file.exists()) {
			// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
			this.errorMessage = "路径：" + fullPath + "下，不存在指定文件，请稍后再试！";
			// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
			return "error";
		}
		// 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
		FileInputStream fis = new FileInputStream(file);
		// 准备字节数组（字节缓冲区）输出流备用
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		// 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
		byte[] buff = new byte[1024];
		int rc = 0;
		while ((rc = fis.read(buff, 0, 1024)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
		this.inputStream = new ByteArrayInputStream(swapStream.toByteArray());
		// 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
		fis.close();
		swapStream.close();
		// 因为生成的台帐文档的数据流已经拷贝到了下载流中，因此原文件可以删除了
		file.delete();
		// ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
		String s = levelName + "人员电子台帐.docx";
		this.fileName = new String(s.getBytes(), "ISO8859-1");

		return "download";
	}


}
