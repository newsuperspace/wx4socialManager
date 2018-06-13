package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("thirdLevelAction") // <!-- ● -->
@Scope("prototype")
@Lazy(true)
public class ThirdLevelAction implements ModelDriven<ThirdLevel> { // <!-- ● -->

	// =================DI注入=================
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "projectTypeService")
	private ProjectTypeService projectTypeService;

	// =================模型驱动================= <!-- ● -->
	private ThirdLevel thirdLevel;

	@Override
	public ThirdLevel getModel() {
		this.thirdLevel = new ThirdLevel();
		return thirdLevel;
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

	// =================DI注入================= <!-- ● -->
	@Resource(name = "thirdLevelService")
	private ThirdLevelService thirdLevelService;
	@Resource(name = "fourthLevelService")
	private FourthLevelService fourthLevelService;

	// ====================Actions================
	/**
	 * 管理员权限者通过点击third.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() { // <!-- ● -->

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
		SecondLevel parent = null;
		for (SecondLevel l : doingMan.getManager().getScls()) {
			parent = l;
			break;
		}

		ReturnMessage4Common r = new ReturnMessage4Common();

		if ("".equals(thirdLevel.getDescription()) || "".equals(thirdLevel.getName())) {
			r.setMessage("关键数据为null，新建层级失败");
			r.setResult(false);
		} else {
			ThirdLevel l = new ThirdLevel();

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(ThirdLevel.LEVEL_THREE);
			sb.append("$");
			sb.append("id_");
			String id = UUID.randomUUID().toString();
			sb.append(id);

			l.setThid(id);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			l.setQrcode(qrcode);

			l.setDescription(thirdLevel.getDescription());
			l.setName(thirdLevel.getName());
			l.setParent(parent);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(parent.getParent().getParent().getParent());
			dp.setZeroLevel(parent.getParent().getParent());
			dp.setFirstLevel(parent.getParent());
			dp.setSecondLevel(parent);
			dp.setThirdLevel(l);
			
			bp.setDescription("用作" + l.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(parent.getParent().getParent().getParent());
			bp.setZeroLevel(parent.getParent().getParent());
			bp.setFirstLevel(parent.getParent());
			bp.setSecondLevel(parent);
			bp.setThirdLevel(l);
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
			thirdLevelService.save(l);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 通过点击third.jsp页面上每个thirdLevel条目后的新建按钮，实现创建指定third层级对象的子对象，
	 * 也就是fourthLevel的新建操作
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
			ThirdLevel parentLevel = thirdLevelService.queryEntityById(this.getParentId());

			FourthLevel sonLevel = new FourthLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(FourthLevel.LEVEL_FOUR);
			sb.append("$");
			sb.append("id_");
			String sid = UUID.randomUUID().toString();
			sb.append(sid);

			sonLevel.setFoid(sid);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			sonLevel.setQrcode(qrcode);

			sonLevel.setParent(parentLevel);

			fourthLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	public String getLevelList() { // <!-- ● -->

		List<ThirdLevel> list = thirdLevelService.queryEntities();

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

		String id = thirdLevel.getThid();
		ThirdLevel l = thirdLevelService.queryEntityById(id);

		List<ThirdLevel> list = new ArrayList<ThirdLevel>();
		list.add(l);

		ActionContext.getContext().put("levels", list);
		return "list";
	}
}
