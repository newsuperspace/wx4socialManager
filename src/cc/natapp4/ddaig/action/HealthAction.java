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
	private WeixinService4Setting weixinService4Setting;
	// -----------------健康相关----------------
	@Autowired
	private EnclosedScaleService enclosedScaleService;
	@Autowired
	private Factor4EnclosedScaleService factor4EnclosedScaleService;
	@Autowired
	private FactorResult4Sample4EnclosedScaleService factorResult4Sample4EnclosedScaleService;
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
	 * 用户在前端通过 menu.jsp → 健康管理 → 被测者管理 时 用于跳转到页面，随后页面上的 $(function(){});
	 * 会自动执行后续的初始化分页查询数据的操作， 因此本方法无需任何持久层访问操作
	 * 
	 * @return
	 */
	public String toUsersPage() {
		return "users";
	}

	/**
	 * 
	 * 由users.jsp页面上的getCountandCreateFirstPage4InitLaypage() 方法调用
	 * 用于初始化users.jsp分页查询的总页数和首页数据
	 * 
	 * @return
	 */
	public String getCountandCreateFirstPage4InitLaypage() {

		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();

		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		List<User> users = null;
		if ("admin".equals(tag)) {
			// TODO (这里需要修改，在queryEntities中增加分页查询)如果是管理员则可以查看系统中的所有用户
			users = userService.queryEntities();
		} else {
			users = userService.getAllLevelUsersByPage(tag, lid, 1, 10);
		}

		if (null != users) {
			for (int i = 0; i < users.size(); i++) {
				User u = users.get(i);
				if (null == u.getSamples4EnclosedScale()) {
					u.setSamples4EnclosedScale(new ArrayList<Sample4EnclosedScale>());
				}
				u.setSampleNum(u.getSamples4EnclosedScale().size());
			}
		} else {
			users = new ArrayList<User>();
		}

		result.setUsers(users);
		result.setCount(userService.getAllLevelUsersCount(tag, lid));

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	private int targetPageNum;
	private int limit;

	public int getTargetPageNum() {
		return targetPageNum;
	}

	public void setTargetPageNum(int targetPageNum) {
		this.targetPageNum = targetPageNum;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 获取当前管理层级之下的所有人员（直辖和非直辖）的全部测量对象， 并基于分页查询向前端（health/users.jsp）返回数据结果，以动态组织数据展示
	 * 
	 * @return 结果集索引字符串
	 */
	public String getUsers() {
		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();

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
		// --------------------------开始根据操作人的层级来获取所辖用户（Admin获取所有用户）---------------------------
		List<User> users = null;
		if ("admin".equals(tag)) {
			// TODO (这里需要修改，在queryEntities中增加分页查询)如果是管理员则可以查看系统中的所有用户
			users = userService.queryEntities();
		} else {
			users = userService.getAllLevelUsersByPage(tag, lid, targetPageNum, limit);
		}

		if (null != users) {
			for (int i = 0; i < users.size(); i++) {
				User u = users.get(i);
				if (null == u.getSamples4EnclosedScale()) {
					u.setSamples4EnclosedScale(new ArrayList<Sample4EnclosedScale>());
				}
				u.setSampleNum(u.getSamples4EnclosedScale().size());
			}
		} else {
			users = new ArrayList<User>();
		}

		result.setUsers(users);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	// ===============配套userList.jsp页面的selector筛选用户功能的系列方法======================
	/**
	 * 根据当前操作者层级，从数据库获取父+子层级数据，组成susers.jsp页面中基于WEUI的层级过滤器（picker）
	 * 所需要的JSON数据格式，并返回给前端可以让其直接使用。
	 * 
	 * @return
	 */
	public String initSelector() {
		List<Bean4InitSelector> total = new ArrayList<Bean4InitSelector>();

		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");

		if ("admin".equals(tag)) {
			// 当前操作者为系统管理员，获取所有层级数据
			List<MinusFirstLevel> minusFirstLevels = minusFirstLevelService.queryEntities();
			for (int a = 0; a < minusFirstLevels.size(); a++) {
				Bean4InitSelector minusFirst = new Bean4InitSelector();
				minusFirst.setLabel(minusFirstLevels.get(a).getName());
				minusFirst.setValue("minusFirst_" + minusFirstLevels.get(a).getMflid());
				List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();

				Set<ZeroLevel> zeroLevels = minusFirstLevels.get(a).getChildren();
				for (ZeroLevel b : zeroLevels) {
					Bean4InitSelector zero = new Bean4InitSelector();
					zero.setLabel(b.getName());
					zero.setValue("zero_" + b.getZid());
					List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();

					Set<FirstLevel> firstLevels = b.getChildren();
					for (FirstLevel c : firstLevels) {
						Bean4InitSelector first = new Bean4InitSelector();
						first.setLabel(c.getName());
						first.setValue("first_" + c.getFlid());
						List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

						Set<SecondLevel> secondLevels = c.getChildren();
						for (SecondLevel d: secondLevels) {
							Bean4InitSelector second = new Bean4InitSelector();
							second.setLabel(d.getName());
							second.setValue("second_" + d.getScid());
							List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

							Set<ThirdLevel> thirdLevels = d.getChildren();
							for (ThirdLevel e: thirdLevels) {
								Bean4InitSelector third = new Bean4InitSelector();
								third.setLabel(e.getName());
								third.setValue("third_" + e.getThid());
								List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

								Set<FourthLevel> fourthLevels = e.getChildren();
								for (FourthLevel f: fourthLevels) {
									Bean4InitSelector fourth = new Bean4InitSelector();
									fourth.setLabel(f.getName());
									fourth.setValue("fourth_" + f.getFoid());
									fourth.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针

									children4Third.add(fourth);
								}

								third.setChildren(children4Third);
								children4Second.add(third);
							}

							second.setChildren(children4Second);
							children4First.add(second);
						}

						first.setChildren(children4First);
						children4Zero.add(first);
					}

					zero.setChildren(children4Zero);
					children4MinusFirst.add(zero);
				}

				minusFirst.setChildren(children4MinusFirst);
				total.add(minusFirst);
			}

		}else {
			// 非管理员用户
			switch (tag) {
			case "minus_first":
				// 当前层级管理者为minus_first
				MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);
				
				Bean4InitSelector minusFirstBean = new Bean4InitSelector();
				minusFirstBean.setLabel(minusFirstLevel.getName());
				minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
				List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
				
				for(ZeroLevel zeroLevel:minusFirstLevel.getChildren() ) {
					Bean4InitSelector zeroBean = new Bean4InitSelector();
					zeroBean.setLabel(zeroLevel.getName());
					zeroBean.setValue(zeroLevel.getZid());
					List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
					
					for() {
						
					}
				}
				
				minusFirstBean.setChildren(children4MinusFirst);
				total.add(minusFirstBean);
				break;
			case "zero":
				// 当前层级管理者为zeroLevel
				ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
				init.setTag("zero");
				init.setZeroLevel(zeroLevel);
				// TODO 修改ZeroLevel.children 为List容器，不要SET容器
				List<FirstLevel> firstLevelList = new ArrayList<FirstLevel>();
				Set<FirstLevel> firstLevelSet = zeroLevel.getChildren();
				firstLevelList.addAll(firstLevelSet);
				init.setFirstLevels(firstLevelList);
				break;
			case "first":
				// 当前层级管理者为firstLevel
				FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
				init.setTag("first");
				init.setFirstLevel(firstLevel);
				// TODO 修改FirstLevel.children 为List容器，不要SET容器
				List<SecondLevel> secondLevelList = new ArrayList<SecondLevel>();
				Set<SecondLevel> secondLevelSet = firstLevel.getChildren();
				secondLevelList.addAll(secondLevelSet);
				init.setSecondLevels(secondLevelList);
				break;
			case "second":
				// 当前层级管理者为secondLevel
				SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
				init.setTag("second");
				init.setSecondLevel(secondLevel);
				// TODO 修改SecondLevel.children 为List容器，不要SET容器
				List<ThirdLevel> thirdLevelList = new ArrayList<ThirdLevel>();
				Set<ThirdLevel> thirdLevelSet = secondLevel.getChildren();
				thirdLevelList.addAll(thirdLevelSet);
				init.setThirdLevels(thirdLevelList);
				break;
			case "third":
				// 当前层级管理者为thirdLevel
				ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
				init.setTag("third");
				init.setThirdLevel(thirdLevel);
				// TODO 修改ThirdLevel.children 为List容器，不要SET容器
				List<FourthLevel> fourthLevelList = new ArrayList<FourthLevel>();
				Set<FourthLevel> fourthLevelSet = thirdLevel.getChildren();
				fourthLevelList.addAll(fourthLevelSet);
				init.setFourthLevels(fourthLevelList);
				break;
			case "fourth":
				// 当前层级管理者为fourthLevel
				FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
				init.setTag("fourth");
				init.setFourthLevel(fourthLevel);
				break;
			}
			
		}

		ActionContext.getContext().getValueStack().push(total);
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

	private String jsonStr4CreateEnclosedScale;

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

		ReturnMessage4Common result = new ReturnMessage4Common();
		result.setMessage("数据通路正常");
		result.setResult(true);

		// 开始基于Gson的JSON字符串解析工作
		// GSON直接解析成对象
		ParseJson4CreateEnclosedScale parseResult = new Gson().fromJson(jsonStr4CreateEnclosedScale,
				ParseJson4CreateEnclosedScale.class);
		if (null != parseResult) {
			System.out.println(parseResult.toString());
		}
		// TODO 先校验数据规范性，然后创建到数据库
		EnclosedScale enclosedScale = new EnclosedScale();
		enclosedScale.setChName(parseResult.getGeneral().getChName());
		enclosedScale.setDescription(parseResult.getGeneral().getDescription());
		enclosedScale.setDisorder(true);
		enclosedScale.setEngName(parseResult.getGeneral().getEngName());
		enclosedScale.setEsid(UUID.randomUUID().toString());
		enclosedScale.setIntroduce(parseResult.getGeneral().getIntroduce());
		enclosedScale.setSamples(new ArrayList<Sample4EnclosedScale>());
		enclosedScale.setUseNum(0);
		enclosedScale.setWeigh(parseResult.getGeneral().getWeigh());

		// 开始解析factor
		List<Factor4EnclosedScale> factors = new ArrayList<Factor4EnclosedScale>();
		for (int i = 0; i < parseResult.getFactors().size(); i++) {
			Factor4EnclosedScale factor = new Factor4EnclosedScale();
			factor.setDescription(parseResult.getFactors().get(i).getDescription());
			factor.setEnclosedScale(enclosedScale);
			factor.setFactorResults(new ArrayList<FactorResult4Sample4EnclosedScale>());
			factor.setFid(UUID.randomUUID().toString());
			factor.setKeyword(parseResult.getFactors().get(i).getKeyword());
			factor.setName(parseResult.getFactors().get(i).getName());
			factor.setOrd(parseResult.getFactors().get(i).getOrder());
			factor.setStamp(parseResult.getFactors().get(i).getStamp());

			// 封装计分单元
			List<Section4Factor4EnclosedScale> sections = new ArrayList<Section4Factor4EnclosedScale>();
			for (int j = 0; j < parseResult.getFactors().get(i).getSections().size(); j++) {
				Section4Factor4EnclosedScale section = new Section4Factor4EnclosedScale();
				section.setDescription(parseResult.getFactors().get(i).getSections().get(j).getDescription());
				section.setDiagnosis(parseResult.getFactors().get(i).getSections().get(j).getDiagnosis());
				section.setFactor(factor);
				section.setMaxScore(parseResult.getFactors().get(i).getSections().get(j).getMaxScore());
				section.setMinScore(parseResult.getFactors().get(i).getSections().get(j).getMinScore());
				section.setName(parseResult.getFactors().get(i).getSections().get(j).getName());
				section.setOrd(parseResult.getFactors().get(i).getSections().get(j).getOrder());
				section.setSid(UUID.randomUUID().toString());
				sections.add(section);
			}
			factor.setSections(sections);

			// 封入空TopicList待用
			factor.setTopics(new ArrayList<Topic4EnclosedScale>());

			// 放入List列表中
			factors.add(factor);
		}
		enclosedScale.setFactors(factors);

		// 开始解析opGroup
		List<OptionGroup4EnclosedScale> opGroups = new ArrayList<OptionGroup4EnclosedScale>();
		for (int i = 0; i < parseResult.getOpGroups().size(); i++) {
			OptionGroup4EnclosedScale opGroup = new OptionGroup4EnclosedScale();
			opGroup.setIntroduce(parseResult.getOpGroups().get(0).getIntroduce());
			opGroup.setName(parseResult.getOpGroups().get(0).getName());
			opGroup.setOgid(UUID.randomUUID().toString());
			opGroup.setOrd(parseResult.getOpGroups().get(0).getOrder());
			opGroup.setStamp(parseResult.getOpGroups().get(0).getStamp());

			// 封装选项
			List<Option4EnclosedScale> options = new ArrayList<Option4EnclosedScale>();
			for (int j = 0; j < parseResult.getOpGroups().get(0).getOptions().size(); j++) {
				Option4EnclosedScale option = new Option4EnclosedScale();
				option.setContent(parseResult.getOpGroups().get(0).getOptions().get(j).getContent());
				option.setOpid(UUID.randomUUID().toString());
				option.setOptionGroup(opGroup);
				option.setOrd(parseResult.getOpGroups().get(0).getOptions().get(j).getOrder());
				option.setScore(parseResult.getOpGroups().get(0).getOptions().get(j).getScore());
				options.add(option);
			}
			opGroup.setOptions(options);

			// 封入空TopicList待用
			opGroup.setTopics(new ArrayList<Topic4EnclosedScale>());

			// 放入到List列表中
			opGroups.add(opGroup);
		}

		// 开始解析Topic
		for (int i = 0; i < parseResult.getTopics().size(); i++) {
			Topic4EnclosedScale topic = new Topic4EnclosedScale();
			topic.setDescription(parseResult.getTopics().get(i).getContent());
			topic.setKeyword(parseResult.getTopics().get(i).getKeyword());
			topic.setOrd(parseResult.getTopics().get(i).getOrder());
			topic.setTid(UUID.randomUUID().toString());
			topic.setTopicResults(new ArrayList<TopicResult4FactorResult4Sample4EnclosedScale>());
			// 开始判断当前topic以order序数关联的factor和opGroup，并进行外键关联
			Factor4EnclosedScale factor = enclosedScale.getFactors()
					.get(parseResult.getTopics().get(i).getFactor_order() - 1);
			factor.getTopics().add(topic);
			topic.setFactor(factor);

			OptionGroup4EnclosedScale opGroup = opGroups.get(parseResult.getTopics().get(i).getOpGroup_order() - 1);
			opGroup.getTopics().add(topic);
			topic.setOptionGroup(opGroup);
		}

		System.out.println(enclosedScale);

		// 级联（Cascade=save-update）保存/更新
		enclosedScaleService.save(enclosedScale);

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

	/**
	 * 跳转到封闭式量表的JSP页面——enclosedScalePage.jsp，进行数据采集
	 * 
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
	 * 
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
