package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
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

import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4CreateActivity;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

@Controller("doingProjectAction")
@Scope("prototype")
@Lazy(true)
public class DoingProjectAction extends ActionSupport implements ModelDriven<DoingProject> {

	// ==========================================================DI注入Aspect
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;
	@Resource(name = "activityService")
	private ActivityService activityService;
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
		this.doingProject = new DoingProject();
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

	/*
	 * level = minusFirst/zero/first/second/third 从前端发来的，告知本地服务器索要是哪个层级的项目
	 * 
	 */
	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	// ==========================================================Method

	/**
	 * 筹备新建活动所需要的信息，包括doingProject的项目名称/doingProject项目dpid
	 * 
	 * @return
	 */
	public String showCreateActivityModal() {

		ReturnMessage4CreateActivity result = new ReturnMessage4CreateActivity();

		String dpid = this.doingProject.getDpid();
		// 判断请求参数是否为""
		if (dpid.equals("")) {
			result.setResult(false);
			result.setMessage("关键参数dpid为null，新建活动失败");

			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		DoingProject dp = doingProjectService.queryEntityById(dpid);
		// 判断是否能找到制定dpid的doingProject
		if (null == dp) {
			result.setMessage("不存在dpid为" + dpid + "的项目，新建活动失败");
			result.setResult(false);

			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		// 一切正常如是返回doingProject的数据信息
		result.setDp(dp);
		result.setMessage("doingProject的数据信息已经找到");
		result.setDp(dp);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/*
	 * AJAX 创建新项目
	 */
	public String create() {

		return "json";
	}

	/*
	 * 得到当前操作执行层级自己直接管辖的项目列表数据 以及 操作者下辖的子层级对象所辖的项目
	 * 所谓直接管辖就是DoingProject中的对应操作者的层级的外键是操作者层级的lid，但是次一级的lid是null（
	 * DoingProject中也是阶梯性层级外键） 则说明该项目是当前操作者的直管项目
	 */
	public String getProjects() {


		// 获取当前操作者层级的的grouping.tag和lid
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		
		boolean isAdmin = false;
		if("admin".equals(tag))
			isAdmin = true;
		
		// 判断前端请求的是那个层级的项目
		List<DoingProject> list = doingProjectService.queryEntities();
		// 存放筛选出来的符合前端索取条件的项目
		List<DoingProject> projects = new ArrayList<DoingProject>();
		// 存放操作者层级之下的所有DoingProject(包括自己的和所有子孙层级的)
		Set<DoingProject> allProjects = null;
		if (null == level) {
			// 如果从前端发来的请求参数level是null，说明前端请求的是“我的项目”，也就是当前操作者层级的项目
			level = "";
		}
		switch (level) {
		case "minusFirst": // 前端请求的是minusFirst层级对象的项目&&&只有admin能获取
			// 获取街道层级的项目,通常是Admin才能获取到街道项目
			if (isAdmin) {
				// 前端通过Shiro进行了控制，Admin用户是不可能看到“我的项目”的选项的，也就不可能访问本方法
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
		case "zero":  // 前端请求的是zero层级对象的项目&&&只有admin和minusFirst层级能获取
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getZeroLevel() && null == dp.getFirstLevel()) {
						projects.add(dp);
					}
				}
			} else {
				// 操作者总是能是minus_first层级，通过tag和lid首先判断当前操作者层级是不是真的是minusFirst层级，如果不是直接返回
				if(StringUtils.isEmpty(lid)||StringUtils.isEmpty(tag)||!tag.equals("minus_first")){
					System.out.println("错误：前端操作者要索取zeroLeve层级项目，但其本身并非admin和minus_first层级管理者");
					return SUCCESS;
				}
				
				MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
				if(null==minusFirstLevel){
					System.out.println("错误：前端操作者要索取zeroLeve层级项目，但其本身不存在");
					return SUCCESS;
				}
				/*
				 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
				 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
				 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
				 *  我们需要筛选出直接子层级的项目
				 */
				allProjects = minusFirstLevel.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getZeroLevel() != null && dp.getFirstLevel() == null) {
						projects.add(dp);
					}
				}
			}
			break;
		case "first": // 前端请求的是first层级对象的项目&&&只有admin\minusFirst\zero层级能获取
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有first层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getFirstLevel() && null == dp.getSecondLevel()) {
						projects.add(dp);
					}
				}
			} else {
				// 非admin用户
				if(StringUtils.isEmpty(lid)||StringUtils.isEmpty(tag)){
					System.out.println("错误：前端操作者要索取firstLeve层级项目，但因session域中缺少lid或tag获取失败");
					return SUCCESS;
				}
				if(!tag.equals("minus_first")&&!tag.equals("zero")){
					System.out.println("错误：前端操作者要索取firstLeve层级项目，但其并非minus_first或zero管理者");
					return SUCCESS;
				}
				switch (tag) {
				case "minus_first":
					// 操作者是街道层级
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					if(null==minusFirstLevel){
						System.out.println("错误：前端操作者要索取firstLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = minusFirstLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getFirstLevel() != null && dp.getSecondLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					if(null==zeroLevel){
						System.out.println("错误：前端操作者要索取firstLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = zeroLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getFirstLevel() != null && dp.getSecondLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		case "second":  // 前端请求的是second层级对象的项目&&&只有admin\minusFirst\zero\first层级能获取
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getSecondLevel() && null == dp.getThirdLevel()) {
						projects.add(dp);
					}
				}
			} else {
				// 非admin
				if(StringUtils.isEmpty(lid)||StringUtils.isEmpty(tag)){
					System.out.println("错误：前端操作者要索取secondLeve层级项目，但因session域中缺少lid或tag获取失败");
					return SUCCESS;
				}
				if(!tag.equals("minus_first")&&!tag.equals("zero")&&!tag.equals("first")){
					System.out.println("错误：前端操作者要索取secondLeve层级项目，但其并非minus_first、zero或first管理者");
					return SUCCESS;
				}
				switch (tag) {
				case "minus_first":
					// 操作者是街道层级
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					if(null==minusFirstLevel){
						System.out.println("错误：前端操作者要索取secondLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = minusFirstLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					if(null==zeroLevel){
						System.out.println("错误：前端操作者要索取secondLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = zeroLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				case "first":
					// 操作者是first层级
					FirstLevel  firstLevel  =  firstLevelService.queryEntityById(lid);
					if(null==firstLevel){
						System.out.println("错误：前端操作者要索取secondLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					allProjects = firstLevel.getDoingProjects();
					for(DoingProject dp: allProjects){
						if (dp.getSecondLevel() != null && dp.getThirdLevel() == null) {
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		case "third":  // 前端请求的是third层级对象的项目&&&只有admin\minusFirst\zero\first\second层级能获取
			if (isAdmin) {
				// 操作者是Admin，需要得到数据库中获取所有社区层级的项目
				for (DoingProject dp : list) {
					if (null != dp.getThirdLevel()) {
						projects.add(dp);
					}
				}
			} else {
				// 非admin
				if(StringUtils.isEmpty(lid)||StringUtils.isEmpty(tag)){
					System.out.println("错误：前端操作者要索取thirdLeve层级项目，但因session域中缺少lid或tag获取失败");
					return SUCCESS;
				}
				if(!tag.equals("minus_first")&&!tag.equals("zero")&&!tag.equals("first")&&!tag.equals("second")){
					System.out.println("错误：前端操作者要索取thirdLeve层级项目，但其并非minus_first、zero、first或second管理者");
					return SUCCESS;
				}
				switch (tag) {
				case "minus_first":
					// 操作者是街道层级
					MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
					if(null==minusFirstLevel){
						System.out.println("错误：前端操作者要索取thirdLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = minusFirstLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getThirdLevel() != null) {
							projects.add(dp);
						}
					}
					break;
				case "zero":
					// 操作者可能是社区层级
					ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
					if(null==zeroLevel){
						System.out.println("错误：前端操作者要索取thirdLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					/*
					 *  此时获取到allProjects中的是在创建DoingProjects时所有与当前minus_first层级对象产生外键关联的对象
					 *  由于doingProject与member类似其中层级字段也是层级化设置，因此包括所有子层级的doingProject也会
					 *  与minus_first产生外键关联，因此allProject存放的是当前minus_first及其所有子孙层级的doingProject项目
					 *  我们需要筛选出直接子层级的项目
					 */
					allProjects = zeroLevel.getDoingProjects();
					for (DoingProject dp : allProjects) {
						if (dp.getThirdLevel() != null) {
							projects.add(dp);
						}
					}
					break;
				case "first":
					// 操作者是first层级
					FirstLevel  firstLevel  =  firstLevelService.queryEntityById(lid);
					if(null==firstLevel){
						System.out.println("错误：前端操作者要索取thirdLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					allProjects = firstLevel.getDoingProjects();
					for(DoingProject dp: allProjects){
						if (dp.getThirdLevel() != null) {
							projects.add(dp);
						}
					}
					break;
				case "second":
					// 操作者是second层级
					SecondLevel  secondLevel  = secondLevelService.queryEntityById(lid);
					if(null==secondLevel){
						System.out.println("错误：前端操作者要索取thirdLeve层级项目，但其本身不存在");
						return SUCCESS;
					}
					allProjects  = secondLevel.getDoingProjects();
					for(DoingProject dp: allProjects){
						if(dp.getThirdLevel()!=null){
							projects.add(dp);
						}
					}
					break;
				}
			}
			break;
		default:    // 前端发来的请求参数为null，表示的是请求操作者自己运作的项目
			switch (tag) {
			case "minus_first":
				// 当前操作者层级是minus_first层级
				MinusFirstLevel level = minusFirstLevelService.queryEntityById(lid);
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getZeroLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "zero":
				// 当前操作者层级是zero层级
				ZeroLevel level0 = zeroLevelService.queryEntityById(lid);
				// 这里获取到的是当前层级及所有子层级的管理项目
				allProjects = level0.getDoingProjects();
				for (DoingProject dp : allProjects) {
					if (dp.getFirstLevel() == null) {
						projects.add(dp);
					}
				}
				break;
			case "first":
				// 当前操作者层级是first层级
				FirstLevel level1 = firstLevelService.queryEntityById(lid);
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
				SecondLevel level2 = secondLevelService.queryEntityById(lid);
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
				ThirdLevel level3 = thirdLevelService.queryEntityById(lid);
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
