package cc.natapp4.ddaig.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
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
import cc.natapp4.ddaig.utils.FileController;
import cn.com.obj.freemarker.ExportDoc;

@Controller("managerAction")
@Scope("prototype")
@Lazy(true)
public class ManagerAction extends ActionSupport implements ModelDriven<Manager> {

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "memberService")
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

	// =======================模型驱动===================
	private Manager manager = null;

	@Override
	public Manager getModel() {
		this.manager = new Manager();
		return this.manager;
	}

	// =======================属性驱动===================
	// 通过Struts.xml中配置的全局结果集索引字符串实现错误信息的导出
	String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/*
	 * 从managerList.jsp页面中，通过调用myJS.managerModal.op.toManagedLevelList（）
	 * 传递过来的请求参与，用来告知需要获取该member成员对象之下的所有manager列表
	 */
	private String memberid;

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	/*
	 * 接收来自myJS→managerModal.toManagersOfLevel()方法传递过来的来自minus_first.jsp/zero.
	 * jsp/first.jsp/second.jsp/thrid.jsp/fourth.jsp
	 * 上点击某个层级对象的管理者后以请求参数传递过来的层级对象的levelLid和对应的tag（minus_first、zero、first、
	 * second、third、fourth）
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
	 * 根据前端传递过来的memberid 跳转到用于显示该用户所管理的当前操作者层级
	 * 
	 * @return
	 */
	public String toManagedLevelList() {
		Member member = memberService.queryEntityById(this.memberid);
		List<Manager> managers = member.getManagers();

		ActionContext.getContext().put("username", member.getUser().getUsername());
		ActionContext.getContext().put("managers", managers);
		return "managedLevelList";
	}

	/**
	 * 根据前端传递过来的managerid
	 * 
	 * @return
	 */
	public String toManagersOfLevel() {
		String tag = this.tag;
		String levelLid = this.levelLid;

		List<Manager> managers = null;

		String managerName = "";

		switch (tag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(levelLid);
			managers = minusFirstLevel.getManagers();
			managerName = minusFirstLevel.getName();
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

	// -------------------------------------------处理下载业务-----------------------------------------
	// 下载文件用的输入流
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	// 下载文件用的文件名
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String downloadWorkCard() throws IOException {
		String levelTag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		List<User> users = userService.getManagers(levelTag, lid, "all");
		
		
		String levelName = "";
		String minusFirstName = "";
		String zeroName = "";
		switch (levelTag) {
		case "minus_first":
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
			levelName = minusFirstLevel.getName();
			minusFirstName = levelName;
			break;
		case "zero":
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			levelName = zeroLevel.getName();
			zeroName = levelName;
			minusFirstName = zeroLevel.getParent().getName();
			break;
		case "first":
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			levelName = firstLevel.getName();
			zeroName = firstLevel.getParent().getName();
			minusFirstName = firstLevel.getParent().getParent().getName();
			break;
		case "second":
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			levelName = secondLevel.getName();
			zeroName = secondLevel.getParent().getParent().getName();
			minusFirstName = secondLevel.getParent().getParent().getParent().getName();
			break;
		case "third":
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			levelName = thirdLevel.getName();
			zeroName = thirdLevel.getParent().getParent().getParent().getName();
			minusFirstName = thirdLevel.getParent().getParent().getParent().getParent().getName();
			break;
		case "fourth":
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			levelName = fourthLevel.getName();
			zeroName = fourthLevel.getParent().getParent().getParent().getParent().getName();
			minusFirstName = fourthLevel.getParent().getParent().getParent().getParent().getParent().getName();
			break;

		}

		// 创建
		String fullPath;
		ExportDoc maker = new ExportDoc("UTF-8");
		fullPath = "download" + File.separator + "workCard";
		FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fullPath));
		fullPath += File.separator + levelName + "的成员电子工作证" + ".doc";
		fullPath = ServletActionContext.getServletContext().getRealPath(fullPath);
		try {
			maker.exportDoc4WorkCard(users, levelName, minusFirstName, zeroName, fullPath, "workCard.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------独立准备Struts2用的下载用输入流--------------------
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
		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流备用的全部操作，至此文件数据已经脱离源文件的关系，可以关闭源文件有关的流后删除之
		this.inputStream = new ByteArrayInputStream(swapStream.toByteArray());
		// 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
		fis.close();
		swapStream.close();
		file.delete();
		// ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
		String s =  levelName + "的成员电子工作证" + ".doc";
		this.fileName = new String(s.getBytes(), "ISO8859-1");

		return "download";

	}

}
