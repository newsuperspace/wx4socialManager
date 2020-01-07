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
import java.util.UUID;

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
import cc.natapp4.ddaig.bean.health.Bean4InitSelector;
import cc.natapp4.ddaig.bean.health.ParseJson4CreateEnclosedScale;
import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage;
import cc.natapp4.ddaig.bean.health.ReturnMessage4InitSelector;
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
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.domain.health.Factor4EnclosedScale;
import cc.natapp4.ddaig.domain.health.FactorResult4Sample4EnclosedScale;
import cc.natapp4.ddaig.domain.health.Option4EnclosedScale;
import cc.natapp4.ddaig.domain.health.OptionGroup4EnclosedScale;
import cc.natapp4.ddaig.domain.health.Sample4EnclosedScale;
import cc.natapp4.ddaig.domain.health.Section4Factor4EnclosedScale;
import cc.natapp4.ddaig.domain.health.Topic4EnclosedScale;
import cc.natapp4.ddaig.domain.health.TopicResult4FactorResult4Sample4EnclosedScale;
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
import cc.natapp4.ddaig.service_interface.health.FactorResult4Sample4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.HealthService;
import cc.natapp4.ddaig.service_interface.health.Option4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.OptionGroup4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Sample4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Section4Factor4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.Topic4EnclosedScaleService;
import cc.natapp4.ddaig.service_interface.health.TopicResult4FactorResult4Sample4EnclosedScaleService;
import cc.natapp4.ddaig.utils.FileController;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Setting;
import cn.com.obj.freemarker.ExportDoc;

@Controller("healthAction")
@Scope("prototype")
@Lazy(true)
public class HealthAction extends ActionSupport {

	private static final long serialVersionUID = 600271725750065543L;
	// ==========================================================DI注入Aspect
	@Autowired
	private HealthService healthService;

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
	private String tag; // 用户数据过滤——目标层级的标签（minus_first、zero、first、second、third、fourth）
	private String lid; // 用户数据过滤——目标层级的主键ID

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	// ---------------------下载用属性-------------------
	private String fileName; // 下载用的保存文件名

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 下载数据流
	// 该inputStream属性将作为在struts-article.xml中，名为download的结果集中所使用的，作用是向前端提供下载的IO流
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
		// 一定要将用来回复前端Ajax请求的JavaBean对象放入到栈顶后再返回json结果集索引字符串，才能让JSO插件进行解析
		ActionContext.getContext().getValueStack().push(healthService.sendMessage2One(this.openID, this.content));
		return "json";
	}

	/**
	 * 用户在前端通过 menu.jsp → 健康管理 → 被测者管理 时 用于跳转到页面，随后页面上的 $(function(){});
	 * 会自动执行后续的初始化分页查询数据的操作， 因此本方法无需任何持久层访问操作
	 * 
	 * @return
	 */
	public String toUsersPage() {
		return "users";
	}

	/*
	 * 来自users.jsp 用于分页查询和初始化分页查询
	 */
	private String selectedTag; // 前端基于过滤器选中的目标层级标签
	private String selectedLid; // 前端基于过滤器选中的目标层级的主键ID

	public String getSelectedTag() {
		return selectedTag;
	}

	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
	}

	public String getSelectedLid() {
		return selectedLid;
	}

	public void setSelectedLid(String selectedLid) {
		this.selectedLid = selectedLid;
	}

	/**
	 * 
	 * 由health/users.jsp页面上的getCountandCreateFirstPage4InitLaypage() 方法调用
	 * 用于初始化health/users.jsp分页查询的总页数和首页数据
	 * 
	 * @return
	 */
	public String getCountandCreateFirstPage4InitLaypage() {
		String tag;
		String lid;
		if(!StringUtils.isEmpty(this.selectedTag)&&!StringUtils.isEmpty(this.selectedLid)) {
			tag = this.selectedTag;
			lid = this.selectedLid;
		}else {
			/**
			 * 不同于普通类中通过添加在web.xml中添加RequestContextListener监听器后就可以在任何类中 通过执行
			 * HttpServletRequest request =
			 * ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			 * HttpSession session = request.getSession(); 就能获取到Request和session对象
			 * 
			 * 而如果是Action类，就只需要通过在类内随时调用 ServletActionContext.getRequest.getSession();
			 * 就能得到session了
			 * 
			 */
			tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		}
		
		ReturnMessage4CountandCreateFirstPage result = healthService.getCountandCreateFirstPage4InitLaypage(tag, lid, 1, 10);
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	private int targetPageNum; // 分页查询时，前端返回的目标页的页码（从1开始的页码）
	private int pageItemNumLimit; // 分页查询时，前端页面返回的单页上的数据条目数

	public int getTargetPageNum() {
		return targetPageNum;
	}

	public void setTargetPageNum(int targetPageNum) {
		this.targetPageNum = targetPageNum;
	}

	public int getPageItemNumLimit() {
		return pageItemNumLimit;
	}

	public void setPageItemNumLimit(int pageItemNumLimit) {
		this.pageItemNumLimit = pageItemNumLimit;
	}

	/**
	 * 获取当前管理层级之下的所有人员（直辖和非直辖）的全部测量对象， 并基于分页查询向前端（health/users.jsp）返回数据结果，以动态组织数据展示
	 * 
	 * @return 结果集索引字符串
	 */
	public String getCurrentLevelUsersByPageLimit() {

		String tag;
		String lid;
		if(!StringUtils.isEmpty(this.selectedTag)&&!StringUtils.isEmpty(this.selectedLid)) {
			tag = this.selectedTag;
			lid = this.selectedLid;
		}else {
			/**
			 * 不同于普通类中通过添加在web.xml中添加RequestContextListener监听器后就可以在任何类中 通过执行
			 * HttpServletRequest request =
			 * ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			 * HttpSession session = request.getSession(); 就能获取到Request和session对象
			 * 
			 * 而如果是Action类，就只需要通过在类内随时调用 ServletActionContext.getRequest.getSession();
			 * 就能得到session了
			 * 
			 */
			tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		}

		ReturnMessage4CountandCreateFirstPage result = healthService.getUsersByPageLimit(tag, lid, this.targetPageNum,
				this.pageItemNumLimit);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 根据当前操作者层级，从数据库获取父+子层级数据，组成susers.jsp页面中基于WEUI的层级过滤器（picker）
	 * 所需要的JSON数据格式，并返回给前端可以让其直接使用。
	 * 
	 * @return
	 */
	public String initLevelSelector() {
		/**
		 * 不同于普通类中通过添加在web.xml中添加RequestContextListener监听器后就可以在任何类中 通过执行
		 * HttpServletRequest request =
		 * ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession(); 就能获取到Request和session对象
		 * 
		 * 而如果是Action类，就只需要通过在类内随时调用 ServletActionContext.getRequest.getSession();
		 * 就能得到session了
		 * 
		 */
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		ReturnMessage4InitSelector result = healthService.initSelector(tag, lid);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	// =================================EnclosedScale封闭式问卷功能区=======================================
	/**
	 * 跳转到创建“封闭式问卷”的JSP页面——createEnclosedScalePage.jsp
	 * 
	 * @return
	 */
	public String toCreateEnclosedScalePage() {
		return "createEnclosedScalePage";
	}

	private String jsonStr4CreateEnclosedScale; // 接收创建封闭式问卷的JSON格式字符串，后端基于Gson对其吉星解析封装

	public String getJsonStr4CreateEnclosedScale() {
		return jsonStr4CreateEnclosedScale;
	}

	public void setJsonStr4CreateEnclosedScale(String jsonStr4CreateEnclosedScale) {
		this.jsonStr4CreateEnclosedScale = jsonStr4CreateEnclosedScale;
	}

	/**
	 * AJAX 执行创建封闭式量表的操作
	 * 
	 * @return
	 */
	public String createEnclosedScale() {

		System.out.println(this.getJsonStr4CreateEnclosedScale());

		ReturnMessage4Common result = healthService.createEnclosedScale(this.jsonStr4CreateEnclosedScale);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 跳转到所有封闭式量表的列表JSP页面——enclosedScaleListPage.jsp
	 * 
	 * @return
	 */
	public String toEnclosedScaleListPage() {
		// 准备容器
		List<EnclosedScale> list = new ArrayList<EnclosedScale>();
		// TODO 开始从数据库获取数据

		// 以指定名称“list”填入Map栈中，供给前端通过OGNL表达式解析并显示到页面上
		ActionContext.getContext().put("list", list);
		return "enclosedScaleList";
	}

//	// =================================基于Freemaker下载电子报告的功能区=======================================
//	/**
//	 * 下载用于批量创建的Excel模板文件
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	public String downloadExcel4BatchCreate() throws IOException {
//
//		// --------------------------------准备下载---------------------------------
//		String fullPath = ServletActionContext.getServletContext()
//				.getRealPath(File.separator + "download" + File.separator + "batchUser.xlsx");
//		File file = new File(fullPath);
//		if (!file.exists()) {
//			// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
//			this.errorMessage = "路径：" + fullPath + "下，不存在指定文件，请稍后再试！";
//			// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
//			return "error";
//		}
//		// 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
//		FileInputStream fis = new FileInputStream(file);
//		// 准备字节数组（字节缓冲区）输出流备用
//		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//		// 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
//		byte[] buff = new byte[1024];
//		int rc = 0;
//		while ((rc = fis.read(buff, 0, 1024)) > 0) {
//			swapStream.write(buff, 0, rc);
//		}
//		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
//		this.inputStream = new ByteArrayInputStream(swapStream.toByteArray());
//		// 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
//		fis.close();
//		swapStream.close();
//		// ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
//		String s = "批量创建新用户的Excel模板文件.xlsx";
//		this.fileName = new String(s.getBytes(), "ISO8859-1");
//
//		return "download";
//	}
//
//	/**
//	 * 用于生成某个用户的健康档案的DOC文档并提供下载
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String downloadUserHealthReport() throws Exception {
//		// ----------------------------准备数据-----------------------------
//		// 先找到当前管理者的层级对象
//		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
//		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
//		// 查找出层级直辖的全部人员
//		Set<Member> members = null;
//		String levelName = "";
//		String levelTag = "";
//		String levelQrcodePath = "";
//		switch (tag) {
//		case "minus_first":
//			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
//			if (null == minusFirstLevel) {
//				// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = minusFirstLevel.getMembers();
//			levelName = minusFirstLevel.getName();
//			levelTag = "街道级";
//			levelQrcodePath = minusFirstLevel.getQrcode();
//			break;
//		case "zero":
//			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
//			if (null == zeroLevel) {
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = zeroLevel.getMembers();
//			levelName = zeroLevel.getName();
//			levelTag = "社区级";
//			levelQrcodePath = zeroLevel.getQrcode();
//			break;
//		case "first":
//			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
//			if (null == firstLevel) {
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = firstLevel.getMembers();
//			levelName = firstLevel.getName();
//			levelTag = "第一级";
//			levelQrcodePath = firstLevel.getQrcode();
//			break;
//		case "second":
//			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
//			if (null == secondLevel) {
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = secondLevel.getMembers();
//			levelName = secondLevel.getName();
//			levelTag = "第二级";
//			levelQrcodePath = secondLevel.getQrcode();
//			break;
//		case "third":
//			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
//			if (null == thirdLevel) {
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = thirdLevel.getMembers();
//			levelName = thirdLevel.getName();
//			levelTag = "第三级";
//			levelQrcodePath = thirdLevel.getQrcode();
//			break;
//		case "fourth":
//			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
//			if (null == fourthLevel) {
//				this.errorMessage = "查找不到id为" + lid + "的" + tag + "层级对象";
//				return "error";
//			}
//			members = fourthLevel.getMembers();
//			levelName = fourthLevel.getName();
//			levelTag = "第四级";
//			levelQrcodePath = fourthLevel.getQrcode();
//			break;
//		}
//
//		if (null == members) {
//			this.errorMessage = "当前层级的成员容器（members）为null";
//			return "error";
//		} else if (0 == members.size()) {
//			this.errorMessage = "当前层级的成员数量为0，无法生成电子台账";
//			return "error";
//		}
//		// 解析出每个member对应的user，要保持唯一性
//		Set<User> users = new HashSet<User>();
//		for (Member m : members) {
//			users.add(m.getUser());
//		}
//
//		// ----------------------------准备生成DOC文档-----------------------------
//		String fullPath;
//		ExportDoc maker = new ExportDoc("UTF-8");
//		fullPath = "download" + File.separator + "userLedger";
//		FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fullPath));
//		fullPath += File.separator + levelName + "人员电子台帐.docx";
//		fullPath = ServletActionContext.getServletContext().getRealPath(fullPath);
//		maker.exportDoc4UserLedger(levelName, levelTag, levelQrcodePath, users, fullPath, "userLedger.ftl");
//
//		// --------------------------------准备下载---------------------------------
//		File file = new File(fullPath);
//		if (!file.exists()) {
//			// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
//			this.errorMessage = "路径：" + fullPath + "下，不存在指定文件，请稍后再试！";
//			// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
//			return "error";
//		}
//		// 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
//		FileInputStream fis = new FileInputStream(file);
//		// 准备字节数组（字节缓冲区）输出流备用
//		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//		// 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
//		byte[] buff = new byte[1024];
//		int rc = 0;
//		while ((rc = fis.read(buff, 0, 1024)) > 0) {
//			swapStream.write(buff, 0, rc);
//		}
//		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
//		this.inputStream = new ByteArrayInputStream(swapStream.toByteArray());
//		// 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
//		fis.close();
//		swapStream.close();
//		// 因为生成的台帐文档的数据流已经拷贝到了下载流中，因此原文件可以删除了
//		file.delete();
//		// ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
//		String s = levelName + "人员电子台帐.docx";
//		this.fileName = new String(s.getBytes(), "ISO8859-1");
//
//		return "download";
//	}

}
