package cc.natapp4.ddaig.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("minusFirstLevelAction")
@Scope("prototype")
@Lazy(true)
public class MinusFirstLevelAction implements ModelDriven<MinusFirstLevel> {

	// =================DI注入=================
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "projectTypeService")
	private ProjectTypeService projectTypeService;

	// =================模型驱动=================
	private MinusFirstLevel minusFirstLevel;

	@Override
	public MinusFirstLevel getModel() {
		minusFirstLevel = new MinusFirstLevel();
		return minusFirstLevel;
	}

	// =================属性驱动=================
	private String sonName;
	private String sonDescription;

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
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;

	// ====================Actions================
	/**
	 * 管理员权限者通过点击MinusFirstLevel.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（
	 * 街道对象） 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() {

		ReturnMessage4Common r = new ReturnMessage4Common();

		if ("".equals(minusFirstLevel.getDescription()) || "".equals(minusFirstLevel.getName())) {
			r.setMessage("关键信息为null，街道创建失败");
			r.setResult(false);
		} else {
			// 准备新建的层级对象
			MinusFirstLevel m = new MinusFirstLevel();

			StringBuffer sb = new StringBuffer();
			String id = UUID.randomUUID().toString();
			// 添加层级对象的id
			m.setMflid(id);
			// 拼装形如： tag=minus_first&lid=xjoduf7293jf2wjf9jd9suf9uw
			// 的字符串用作新建层级的带参数二维码的内容
			sb.append("tag=");
			sb.append("minus_first");
			sb.append("&");
			sb.append("lid=");
			sb.append(id);

			/*
			 * 通过QRCodeUtils.createLevelQR(code) 创建唯一标志当前新建层级的二维码 所返回的形如：
			 * "qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"相对路径。
			 */
			String codePath = QRCodeUtils.createLevelQR(sb.toString());
			if ("".equals(codePath)) {
				r.setResult(false);
				r.setMessage("创建层级对象二维码时出现异常");

				ActionContext.getContext().getValueStack().push(r);
				return "json";
			} else {
				// 数据库要保存层级对象二维码的图片位置
				m.setQrcode(codePath);
				// 保存当前时刻的时间戳，用来记录临时带参数二维码的创建时间（格里高利历偏移量）
				m.setQrcodeTime(System.currentTimeMillis());
			}

			m.setDescription(minusFirstLevel.getDescription());
			m.setName(minusFirstLevel.getName());

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(m);

			bp.setDescription("用作" + m.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(m);
			bp.setName("默认项目");
			bp.setCommitTime(System.currentTimeMillis());
			for (ProjectType pt : projectTypes) {
				if (pt.getName().equals("common")) {
					bp.setProjectType(pt);
				}
			}
			Set<DoingProject> doingProjects = new HashSet<DoingProject>();
			doingProjects.add(dp);
			m.setDoingProjects(doingProjects);

			// 像数据库保存新建的层级对象，并级联地存储BesureProject/DoingProject等项目对象
			minusFirstLevelService.save(m);
			r.setMessage("创建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 通过点击MinusFirstLevel.jsp页面上每个minusFirst条目后的新建按钮，实现创建制定minusFirst层级对象的子对象，
	 * 也就是zeroLevel的操作
	 * 
	 * @return
	 */
	public String createSonLevel() {
		ReturnMessage4Common r = new ReturnMessage4Common();

		if (getSonDescription().equals("") || "".equals(getSonName()) || minusFirstLevel.getMflid().equals("")) {
			r.setMessage("关键数据为NULL，创建失败");
			r.setResult(false);
		} else {
			// 首先需要分析出“被新建次层级”的层级对象是哪个
			String pid = minusFirstLevel.getMflid();
			MinusFirstLevel parentLevel = minusFirstLevelService.queryEntityById(pid);

			ZeroLevel sonLevel = new ZeroLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			String id = UUID.randomUUID().toString();
			// 添加层级对象的id
			sonLevel.setZid(id);
			// 拼装形如： tag=zero&lid=xjoduf7293jf2wjf9jd9suf9uw
			// 的字符串用作新建层级的带参数二维码的内容
			sb.append("tag=");
			sb.append("zero");
			sb.append("&");
			sb.append("lid=");
			sb.append(id);

			/*
			 * 通过QRCodeUtils.createLevelQR(code) 创建唯一标志当前新建层级的二维码 所返回的形如：
			 * "qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"相对路径。
			 */
			String codePath = QRCodeUtils.createLevelQR(sb.toString());
			if ("".equals(codePath)) {
				r.setResult(false);
				r.setMessage("创建层级对象二维码时出现异常");

				ActionContext.getContext().getValueStack().push(r);
				return "json";
			} else {
				// 数据库要保存层级对象二维码的图片位置
				sonLevel.setQrcode(codePath);
				// 保存当前时刻的时间戳，用来记录临时带参数二维码的创建时间（格里高利偏移量）
				sonLevel.setQrcodeTime(System.currentTimeMillis());
			}

			sonLevel.setParent(parentLevel);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(parentLevel);
			dp.setZeroLevel(sonLevel);

			bp.setDescription("用作" + sonLevel.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(parentLevel);
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
			zeroLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 获取当前操作者层级所管辖的minus_first层级对象数据
	 * 增加了新功能，判断二维码是否还存在（重新部署项目的时候存放二维码的qrcode会被删除）如果不存就自动重新创建二维码
	 * 
	 * @return
	 */
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

		ServletContext context = ServletActionContext.getServletContext();
		List<MinusFirstLevel> list = null;
		StringBuffer  sb  =  null;
		File file = null;
		if (isAdmin) {
			// 要获取到街道层级的数据信息，只能是Admin管理员，如果不是就是非法访问，应该直接返回空数据
			list = minusFirstLevelService.queryEntities();
			// 检查所有待展示层级的qrcode是否还存在，不存在的自动重新生成
			for (MinusFirstLevel mfl : list) {
				file = new File(context.getRealPath(File.separator + mfl.getQrcode()));
				if (!file.exists()) {
					// 如果不存在二维码文件，则重新创建二维码文件
					File parentFile = file.getParentFile();
					// 判断二维码图片的路径是否存在，不存在就逐层创建
					if (!parentFile.exists()) {
						parentFile.mkdirs();
					}
					sb  =  new StringBuffer();
					sb.append("tag=");
					sb.append("minus_first");
					sb.append("&");
					sb.append("lid=");
					sb.append(mfl.getMflid());
					QRCodeUtils.createQRcode(context.getRealPath(File.separator+mfl.getQrcode()), sb.toString());
				}
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

		String id = minusFirstLevel.getMflid();
		MinusFirstLevel m = minusFirstLevelService.queryEntityById(id);

		ServletContext context = ServletActionContext.getServletContext();
		List<MinusFirstLevel> list = null;
		StringBuffer  sb  =  null;
		File file =  new File(context.getRealPath(File.separator + m.getQrcode()));
		if (!file.exists()) {
			// 如果不存在二维码文件，则重新创建二维码文件
			File parentFile = file.getParentFile();
			// 判断二维码图片的路径是否存在，不存在就逐层创建
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			sb  =  new StringBuffer();
			sb.append("tag=");
			sb.append("minus_first");
			sb.append("&");
			sb.append("lid=");
			sb.append(m.getMflid());
			QRCodeUtils.createQRcode(context.getRealPath(File.separator+m.getQrcode()), sb.toString());
		}
		
		list = new ArrayList<MinusFirstLevel>();
		list.add(m);

		ActionContext.getContext().put("levels", list);
		return "list";
	}

}
