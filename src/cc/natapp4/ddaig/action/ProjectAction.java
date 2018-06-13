package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

@Controller("projectAction")
@Scope("prototype")
@Lazy(true)
public class ProjectAction extends ActionSupport implements ModelDriven<DoingProject> {

	// ==========================================================DI注入Aspect
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;
	@Resource(name = "userService")
	private UserService userService;
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
	private DoingProject doingProject;

	@Override
	public DoingProject getModel() {
		doingProject = new DoingProject();
		return this.doingProject;
	}

	// ======================================================属性驱动——向前端页面传送经过处理的数据信息
	// ProjectType.description
	private int description;

	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

	// level = minusFirst/zero/first/second/third
	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	// ==========================================================Method

	/*
	 * AJAX 创建新项目
	 */
	public String create() {

		return "json";
	}

	/*
	 * 得到当前操作执行者自己直接管辖的项目列表数据 以及 操作者下辖的子层级对象所辖的项目
	 * 所谓直接管辖就是DoingProject中的对应操作者的层级的外键是操作者层级的id但是次一级的id是null 则说明该项目是当前操作者的直管项目
	 */
	public String getProjects() {

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

		// 判断前端请求的是那个层级的项目
		List<DoingProject> list = doingProjectService.queryEntities();
		List<DoingProject> projects = new ArrayList<DoingProject>();
		Set<DoingProject> allProjects = null;
		switch (level) {
		case "minusFirst":
			// 获取街道层级的项目,通常是Admin才能获取到街道项目
			if (isAdmin) {
				// 前端通过Shiro进行了控制，Amin用户是不可能看到“我的项目”的选项的，也就不可能访问本方法
				// 如果身为Admin用户能够访问到本方法说明是通过了不正常的渠道进入的，直接返回空内容给请求者即可
				for (DoingProject dp : list) {
					// MinusFirstLevel存在层级对象，而次一级的ZeroLevel不存在层级对象，则说明该项目是只属于街道层级的
					if (dp.getMinusFirstLevel() != null && dp.getZeroLevel() == null) {
						projects.add(dp);
					}
				}
			} else {
				// TODO 没有比街道层级更高的层级了
				return SUCCESS;
			}
			break;
		case "zero":
			// 获取社区层级的项目
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getZeroLevel() && null == dp.getThirdLevel()) {
						projects.add(dp);
					}
				}
			} else {
				// 操作者是街道层级，需要得到数据库中在该街道层级之下的社区层级的项目
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel level = null;
				for (MinusFirstLevel l : mfls) {
					level = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getZeroLevel() != null && dp.getThirdLevel() == null) {
						projects.add(dp);
					}
				}
			}
			break;
		case "first":
			// 获取第一层级的项目
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getFirstLevel() && null == dp.getSecondLevel()) {
						projects.add(dp);
					}
				}
			} else {
				switch (doingMan.getGrouping().getTag()) {
				case "minus_first":
					// 操作者可能是街道层级
					Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
					MinusFirstLevel level = null;
					for (MinusFirstLevel l : mfls) {
						level = l;
					}
					allProjects = level.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getFirstLevel() != null && dp.getSecondLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					Set<ZeroLevel> zls = doingMan.getManager().getZls();
					ZeroLevel level2 = null;
					for (ZeroLevel l : zls) {
						level2 = l;
					}
					allProjects = level2.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getFirstLevel() != null && dp.getSecondLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		case "second":
			// 获取第二层级的项目
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getSecondLevel() && null == dp.getThirdLevel()) {
						projects.add(dp);
					}
				}
			} else {
				switch (doingMan.getGrouping().getTag()) {
				case "minus_first":
					// 操作者可能是街道层级
					Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
					MinusFirstLevel level = null;
					for (MinusFirstLevel l : mfls) {
						level = l;
					}
					allProjects = level.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					Set<ZeroLevel> zls = doingMan.getManager().getZls();
					ZeroLevel level2 = null;
					for (ZeroLevel l : zls) {
						level2 = l;
					}
					allProjects = level2.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "first":
					Set<FirstLevel> fls = doingMan.getManager().getFls();
					FirstLevel level3 = null;
					for (FirstLevel l : fls) {
						level3 = l;
					}
					allProjects = level3.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		case "third":
			// 获取第三层级的项目
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getThirdLevel()) {
						projects.add(dp);
					}
				}
			} else {
				switch (doingMan.getGrouping().getTag()) {
				case "minus_first":
					// 操作者可能是街道层级
					Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
					MinusFirstLevel level = null;
					for (MinusFirstLevel l : mfls) {
						level = l;
					}
					allProjects = level.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (null != dp.getThirdLevel()) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					Set<ZeroLevel> zls = doingMan.getManager().getZls();
					ZeroLevel level2 = null;
					for (ZeroLevel l : zls) {
						level2 = l;
					}
					allProjects = level2.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (null != dp.getThirdLevel()) {
							projects.add(dp);
						}
					}
					break;
				case "first":
					Set<FirstLevel> fls = doingMan.getManager().getFls();
					FirstLevel level3 = null;
					for (FirstLevel l : fls) {
						level3 = l;
					}
					allProjects = level3.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (null != dp.getThirdLevel()) {
							projects.add(dp);
						}
					}
					break;
				case "second":
					Set<SecondLevel> scls = doingMan.getManager().getScls();
					SecondLevel level4 = null;
					for (SecondLevel l : scls) {
						level4 = l;
					}
					allProjects = level4.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (null != dp.getThirdLevel()) {
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		default:
			// level == null 表示的是请求操作者自己运作的项目
			switch (doingMan.getGrouping().getTag()) {
			case "minus_first":
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				MinusFirstLevel  level =null;
				for (MinusFirstLevel l : mfls) {
					level = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getZeroLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "zero":
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				ZeroLevel level0 = null;
				for (ZeroLevel l : zls) {
					level0 = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level0.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getFirstLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "first":
				// 当前操作者是第一层级管理者
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				FirstLevel level1 = null;
				for (FirstLevel l : fls) {
					level1 = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level1.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getSecondLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "second":
				// 当前操作者是第二层级管理者
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				SecondLevel level2 = null;
				for (SecondLevel l : scls) {
					level2 = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level2.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getThirdLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "third":
				// 当前操作者是第三层级管理者
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				ThirdLevel level3 = null;
				for (ThirdLevel l : tls) {
					level3 = l;
				}
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level3.getDoingProjects();
				projects = new ArrayList<DoingProject>(allProjects);
				break;
			}
			break;
		}

		ActionContext.getContext().put("projects", projects);
		return SUCCESS;
	}


	
	
	
}
