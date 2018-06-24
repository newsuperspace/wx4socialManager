package cc.natapp4.ddaig.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.nutz.qrcode.QRCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@Controller("zeroLevelAction")
@Scope("prototype")
@Lazy(true)
public class ZeroLevelAction implements ModelDriven<ZeroLevel> {

	// =================DI注入=================
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "projectTypeService")
	private ProjectTypeService projectTypeService;
	// 因为需要通过微信公众号服务器生成带参数二维码，这里需要DI注入WeixinService4Setting用来与微信服务器交互
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl mpService;

	// =================模型驱动=================
	private ZeroLevel zeroLevel;

	@Override
	public ZeroLevel getModel() {
		zeroLevel = new ZeroLevel();
		return zeroLevel;
	}

	// =================属性驱动=================
	private String parentId;
	private String sonName;
	private String sonDescription;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	public String getSonDescription() {
		return sonDescription;
	}

	public void setSonDescription(String sonDescription) {
		this.sonDescription = sonDescription;
	}

	// =================DI注入=================
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;

	// ====================Actions================
	/**
	 * 管理员权限者通过点击zero.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() {

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
		MinusFirstLevel parent = null;
		for (MinusFirstLevel l : doingMan.getManager().getMfls()) {
			parent = l;
			break;
		}

		ReturnMessage4Common r = new ReturnMessage4Common();

		if ("".equals(zeroLevel.getDescription()) || "".equals(zeroLevel.getName())) {
			r.setMessage("关键数据为null，新建社区失败");
			r.setResult(false);
		} else {
			ZeroLevel l = new ZeroLevel();

			/*
			 * 带参数二维码应该是形如 ""level$-1_id$c7ca3c4c-c084-41bc-babb-33c60f28fc30""
			 * 当微信端用户扫描该二维码后，微信服务器就会将该参数传递回来，此时只需要 先通过
			 * split("_")分割出level和lid两个部分 每一部分在通过
			 * split("$")分割出具体层级数值（-1/0/1/2/3/4）和具体层级的id 就能轻松定位出用户扫描的加入的是那个层级对象。
			 */
			StringBuffer sb = new StringBuffer();
			sb.append("level$");
			sb.append(ZeroLevel.LEVEL_ZERO);
			sb.append("_");
			sb.append("id$");
			String id = UUID.randomUUID().toString();
			sb.append(id);
			// 添加层级对象的id
			l.setZid(id);

			// 与微信服务器进行交互
			WxMpQrCodeTicket qrTicket = null;
			File inFile = null;
			try {
				qrTicket = mpService.getQrcodeService().qrCodeCreateLastTicket(sb.toString());
				inFile = mpService.getQrcodeService().qrCodePicture(qrTicket);
				if (null == inFile) {
					System.out.println("从微信端获取的带参数二维码的File是null");
					throw new WxErrorException(null);
				}
			} catch (WxErrorException e1) {
				e1.printStackTrace();
				String message = "从微信端获取带参数二维码时出现异常,层级对象创建失败";
				System.out.println(message);
				r.setMessage(message);
				r.setResult(false);
				ActionContext.getContext().getValueStack().push(r);
				return "json";
			}

			String codePath = "";
			codePath = "qrcode";
			int hashCode = id.hashCode();
			int first = hashCode & 0xf;
			int second = (hashCode & 0xf0) >> 4;
			codePath = codePath + File.separator + first + File.separator + second;
			ServletContext context = ServletActionContext.getServletContext();
			String realPath = context.getRealPath(File.separator + codePath); // C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
			// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
			File outFile = new File(realPath);
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			// 然后我们创建二维码图片的文件对象，文件名仍然以层级对象的id值为名字，然后拓展名为jpg
			codePath = codePath + File.separator + id + ".jpg";
			System.out.println("最终的codePath：" + codePath);
			realPath = realPath + File.separator + id + ".jpg";
			System.out.println("最终的realPath:" + realPath);
			outFile = new File(realPath);
			// 创建输入流和输出流
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(outFile);
				fis = new FileInputStream(inFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				String message = "在将从微信服务器获取的存有二维码jpq图片的File保存到本次磁盘时，创建输入或输出流出现异常,层级对象创建失败";
				System.out.println(message);
				r.setMessage(message);
				r.setResult(false);
				ActionContext.getContext().getValueStack().push(r);
				return "json";
			}
			// 开始流对接，temp为字节缓冲（1KB）
			byte[] temp = new byte[1024];
			int len = 0;

			try {
				while ((len = fis.read(temp)) != -1) {
					// 边读边写
					fos.write(temp, 0, len);
				}
				fis.close();
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// 数据库要保存层级对象二维码的图片位置
			l.setQrcode(codePath);
			l.setDescription(zeroLevel.getDescription());
			l.setName(zeroLevel.getName());
			l.setParent(parent);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(parent);
			dp.setZeroLevel(l);

			bp.setDescription("用作" + l.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(parent);
			bp.setZeroLevel(l);
			bp.setName("默认项目");
			bp.setCommitTime(System.currentTimeMillis());
			for (ProjectType pt : projectTypes) {
				if (pt.getName().equals("common")) {
					bp.setProjectType(pt);
				}
			}
			Set<DoingProject> doingProjects = new HashSet<DoingProject>();
			doingProjects.add(dp);
			l.setDoingProjects(doingProjects);

			// 像数据库保存新建的层级对象，并级联地存储BesureProject/DoingProject等项目对象
			zeroLevelService.save(l);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 通过点击zero.jsp页面上每个zeroLevel条目后的新建按钮，实现创建指定Zero层级对象的子对象，也就是FirstLevel的操作
	 * 
	 * @return
	 */
	public String createSonLevel() {

		ReturnMessage4Common r = new ReturnMessage4Common();

		if (getSonDescription().equals("") || "".equals(getSonName()) || this.getParentId().equals("")) {
			r.setMessage("关键数据为NULL，创建失败");
			r.setResult(false);
		} else {
			// 首先需要分析出“被新建次层级”的层级对象是哪个
			ZeroLevel parentLevel = zeroLevelService.queryEntityById(this.getParentId());

			FirstLevel sonLevel = new FirstLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(FirstLevel.LEVEL_ONE);
			sb.append("$");
			sb.append("id_");
			String sid = UUID.randomUUID().toString();
			sb.append(sid);

			sonLevel.setFlid(sid);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			sonLevel.setQrcode(qrcode);

			sonLevel.setParent(parentLevel);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(parentLevel.getParent());
			dp.setZeroLevel(parentLevel);
			dp.setFirstLevel(sonLevel);

			bp.setDescription("用作" + sonLevel.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(parentLevel.getParent());
			bp.setZeroLevel(parentLevel);
			bp.setName("默认项目");
			bp.setCommitTime(System.currentTimeMillis());
			for (ProjectType pt : projectTypes) {
				if (pt.getName().equals("common")) {
					bp.setProjectType(pt);
				}
			}
			Set<DoingProject> doingProjects = new HashSet<DoingProject>();
			doingProjects.add(dp);
			sonLevel.setDoingProjects(doingProjects);

			// 像数据库保存新建的层级对象，并级联地存储BesureProject/DoingProject等项目对象
			firstLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	public String getLevelList() {

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

		List<ZeroLevel> list = null;
		// 分辨当前操作者是Admin还是非Admin
		if (isAdmin) {
			// 当前查访者是Admin,获取数据库中的所有FirstLevel对象
			list = zeroLevelService.queryEntities();
		} else {
			list = new ArrayList<ZeroLevel>();
			// 当前查访者是非Admin管理者，进一步分析当前操作者执行者的层级位置，然后从children属性结构中获取当前操作者下属的层级对象
			switch (doingMan.getGrouping().getTag()) {
			// 对于非Admin用户来说，能够获取到Zeroleve层级对象信息的只可能是街道管理者
			case "minus_first":
				// 当前操作者是街道层级对象，要获取它下属的所有第一层级对象
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel level = null;
				for (MinusFirstLevel l : mfls) {
					level = l;
				}
				// 然后获取到该街道层级下属的所有社区层级
				Set<ZeroLevel> children = level.getChildren();
				// 从下属的社区层级中遍历出来的第一层级对象，就是当前操作者（街道层级）所管辖的全部第一层级对象
				for (ZeroLevel l : children) {
					list.add(l);
				}
				break;
			}
		}

		ActionContext.getContext().put("levels", list);
		return "list";
	}

	/**
	 * managerList.jsp页面中，当点击某个管理者所管理的层级对象的时候
	 * 会触发managerModal.op.jump2LevelPage()方法
	 * 从而根据不同的tag（层级）实现跳转到不同层级Level页面中显示详细信息 的功能，因此每个层级对象的Action都应该有对应的本方法。
	 * 
	 * @return
	 */
	public String getLevelInfo() {

		String id = zeroLevel.getZid();
		ZeroLevel l = zeroLevelService.queryEntityById(id);

		List<ZeroLevel> list = new ArrayList<ZeroLevel>();
		list.add(l);

		ActionContext.getContext().put("levels", list);
		return "list";
	}
}
