package cc.natapp4.ddaig.service_implement.health;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cc.natapp4.ddaig.bean.health.Bean4InitSelector;
import cc.natapp4.ddaig.bean.health.ParseJson4CreateEnclosedScale;
import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage;
import cc.natapp4.ddaig.bean.health.ReturnMessage4InitSelector;
import cc.natapp4.ddaig.domain.User;
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
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Setting;

@Service("healthService")
@Lazy(true)
public class HealthServiceImpl implements HealthService {

	// ===================DI注入==================
	@Autowired
	private UserService userService;
	@Autowired
	private ManagerService managerService;
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

	@Override
	public ReturnMessage4Common sendMessage2One(String openID, String content) {

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (StringUtils.isBlank(openID)) {
			result.setResult(false);
			result.setMessage("该用户为非微信用户，不能发送信息");
		} else if (StringUtils.isBlank(content)) {
			result.setResult(false);
			result.setMessage("消息内容不能是空");
		} else {
			boolean b = weixinService4Setting.sendTextMessage2One(openID, content);
			result.setResult(b);
			if (b) {
				result.setMessage("发送成功！");
			} else {
				result.setMessage("发送失败！");
			}
		}
		return result;
	}

	@Override
	public ReturnMessage4CountandCreateFirstPage getCountandCreateFirstPage4InitLaypage(String targetTag,
			String targetLid, int targetPageNum, int pageItemNumLimit) {

		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();

		List<User> users = null;
		if ("admin".equals(targetTag)) {
			// TODO (这里需要修改，在queryEntities中增加分页查询)如果是管理员则可以查看系统中的所有用户
			users = userService.queryEntities();
		} else {
			users = userService.getAllLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
		}

		if (null != users) {
			for (int i = 0; i < users.size(); i++) {
				User u = users.get(i);
				if (null == u.getSamples4EnclosedScale()) {
					// 对于没有样本的用户，给它一个样本数列容器，方式JSON解析是空指针
					u.setSamples4EnclosedScale(new ArrayList<Sample4EnclosedScale>());
				}
				// 由于用户的getSamples4EnclosedScale() 之上加上了
				// @JSON（serializable=false）因此经过JSON解析后无法获取该容器的内容，因此准备一个字段负责像前端反馈样本数量
				u.setSampleNum(u.getSamples4EnclosedScale().size());
			}
		} else {
			users = new ArrayList<User>();
		}

		// 首页数据准备完毕
		result.setUsers(users);
		// 总数居量准备完毕
		result.setCount(userService.getAllLevelUsersCount(targetTag, targetLid));

		return result;
	}

	@Override
	public ReturnMessage4Common createEnclosedScale(String jsonStr4CreateEnclosedScale) {

		ReturnMessage4Common result = new ReturnMessage4Common();

		// 开始基于Gson的JSON字符串解析工作
		// GSON直接解析成对象
		ParseJson4CreateEnclosedScale parseResult = new Gson().fromJson(jsonStr4CreateEnclosedScale,
				ParseJson4CreateEnclosedScale.class);
		if (null != parseResult) {
			System.out.println(parseResult.toString());
		} else {
			result.setMessage("JSON解析失败，无法创建量表！");
			result.setResult(false);
			return result;
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

		result.setMessage("封闭式量表“" + enclosedScale.getChName() + "”创建成功！");
		result.setResult(true);
		return result;
	}

	@Override
	public ReturnMessage4CountandCreateFirstPage getUsersByPageLimit(String targetTag, String targetLid,
			int targetPageNum, int pageItemNumLimit) {

		ReturnMessage4CountandCreateFirstPage result = new ReturnMessage4CountandCreateFirstPage();

		List<User> users = null;
		if ("admin".equals(targetTag)) {
			// TODO (这里需要修改，在queryEntities中增加分页查询)如果是管理员则可以查看系统中的所有用户
			users = userService.queryEntities();
		} else {
			users = userService.getAllLevelUsersByPage(targetTag, targetLid, targetPageNum, pageItemNumLimit);
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
		return result;
	}

	@Override
	public ReturnMessage4InitSelector initSelector(String tag, String lid) {

		ReturnMessage4InitSelector result = new ReturnMessage4InitSelector();
		List<Bean4InitSelector> total = new ArrayList<Bean4InitSelector>();
		/*
		 * 前端负责使用当前方法给出的JSON数据源的时候，会检查每个Bean中的children的length长度
		 * 因此为防止出现空指针异常，哪怕默认项不含有子对象，也要给出一个空数组（Bean中就是List容器）
		 * 而不能是null
		 */
		Bean4InitSelector defaultBean = new Bean4InitSelector("", "0", new ArrayList<Bean4InitSelector>());

		if ("admin".equals(tag)) {
			// 当前操作者为系统管理员，获取所有层级数据
			List<MinusFirstLevel> minusFirstLevels = minusFirstLevelService.queryEntities();
			for (int a = 0; a < minusFirstLevels.size(); a++) {
				Bean4InitSelector minusFirst = new Bean4InitSelector();
				minusFirst.setLabel(minusFirstLevels.get(a).getName());
				minusFirst.setValue("minusFirst_" + minusFirstLevels.get(a).getMflid());
				List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
				Set<ZeroLevel> zeroLevels = minusFirstLevels.get(a).getChildren();

				if (null != zeroLevels && zeroLevels.size() > 0)
					children4MinusFirst.add(defaultBean);

				for (ZeroLevel b : zeroLevels) {
					Bean4InitSelector zero = new Bean4InitSelector();
					zero.setLabel(b.getName());
					zero.setValue("zero_" + b.getZid());
					List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
					Set<FirstLevel> firstLevels = b.getChildren();

					if (null != firstLevels && firstLevels.size() > 0)
						children4Zero.add(defaultBean);

					for (FirstLevel c : firstLevels) {
						Bean4InitSelector first = new Bean4InitSelector();
						first.setLabel(c.getName());
						first.setValue("first_" + c.getFlid());
						List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
						Set<SecondLevel> secondLevels = c.getChildren();

						if (null != secondLevels && secondLevels.size() > 0)
							children4First.add(defaultBean);

						for (SecondLevel d : secondLevels) {
							Bean4InitSelector second = new Bean4InitSelector();
							second.setLabel(d.getName());
							second.setValue("second_" + d.getScid());
							List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
							Set<ThirdLevel> thirdLevels = d.getChildren();

							if (null != thirdLevels && thirdLevels.size() > 0)
								children4Second.add(defaultBean);

							for (ThirdLevel e : thirdLevels) {
								Bean4InitSelector third = new Bean4InitSelector();
								third.setLabel(e.getName());
								third.setValue("third_" + e.getThid());
								List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();
								Set<FourthLevel> fourthLevels = e.getChildren();

								if (null != fourthLevels && fourthLevels.size() > 0)
									children4Third.add(defaultBean);

								for (FourthLevel f : fourthLevels) {
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

		} else if ("minus_first".equals(tag)) {
			// 当前层级管理者为minus_first
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(lid);

			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();

			if (null != minusFirstLevel.getChildren() && minusFirstLevel.getChildren().size() > 0)
				children4MinusFirst.add(defaultBean);

			for (ZeroLevel zeroLevel : minusFirstLevel.getChildren()) {
				Bean4InitSelector zeroBean = new Bean4InitSelector();
				zeroBean.setLabel(zeroLevel.getName());
				zeroBean.setValue("zero_" + zeroLevel.getZid());
				List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();

				if (null != zeroLevel.getChildren() && zeroLevel.getChildren().size() > 0)
					children4Zero.add(defaultBean);

				for (FirstLevel firstLevel : zeroLevel.getChildren()) {
					Bean4InitSelector firstBean = new Bean4InitSelector();
					firstBean.setLabel(firstLevel.getName());
					firstBean.setValue("first_" + firstLevel.getFlid());
					List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

					if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
						children4First.add(defaultBean);

					for (SecondLevel secondLevel : firstLevel.getChildren()) {
						Bean4InitSelector secondBean = new Bean4InitSelector();
						secondBean.setLabel(secondLevel.getName());
						secondBean.setValue("second_" + secondLevel.getScid());
						List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

						if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
							children4Second.add(defaultBean);

						for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
							Bean4InitSelector thirdBean = new Bean4InitSelector();
							thirdBean.setLabel(thirdLevel.getName());
							thirdBean.setValue("third_" + thirdLevel.getThid());
							List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

							if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
								children4Third.add(defaultBean);

							for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
								Bean4InitSelector fourthBean = new Bean4InitSelector();
								fourthBean.setLabel(fourthLevel.getName());
								fourthBean.setValue("fourth_" + fourthLevel.getFoid());
								fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
								children4Third.add(fourthBean);
							}

							thirdBean.setChildren(children4Third);
							children4Second.add(thirdBean);
						}

						secondBean.setChildren(children4Second);
						children4First.add(secondBean);
					}

					firstBean.setChildren(children4First);
					children4Zero.add(firstBean);
				}

				zeroBean.setChildren(children4Zero);
				children4MinusFirst.add(zeroBean);
			}

			minusFirstBean.setChildren(children4MinusFirst);
			total.add(minusFirstBean);

		} else if ("zero".equals(tag)) {

			// 当前层级管理者为zero
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();

			if (null != zeroLevel.getChildren() && zeroLevel.getChildren().size() > 0)
				children4Zero.add(defaultBean);

			for (FirstLevel firstLevel : zeroLevel.getChildren()) {
				Bean4InitSelector firstBean = new Bean4InitSelector();
				firstBean.setLabel(firstLevel.getName());
				firstBean.setValue("first_" + firstLevel.getFlid());
				List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

				if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
					children4First.add(defaultBean);

				for (SecondLevel secondLevel : firstLevel.getChildren()) {
					Bean4InitSelector secondBean = new Bean4InitSelector();
					secondBean.setLabel(secondLevel.getName());
					secondBean.setValue("second_" + secondLevel.getScid());
					List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

					if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
						children4Second.add(defaultBean);

					for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
						Bean4InitSelector thirdBean = new Bean4InitSelector();
						thirdBean.setLabel(thirdLevel.getName());
						thirdBean.setValue("third_" + thirdLevel.getThid());
						List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

						if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
							children4Third.add(defaultBean);

						for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
							Bean4InitSelector fourthBean = new Bean4InitSelector();
							fourthBean.setLabel(fourthLevel.getName());
							fourthBean.setValue("fourth_" + fourthLevel.getFoid());
							fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
							children4Third.add(fourthBean);
						}

						thirdBean.setChildren(children4Third);
						children4Second.add(thirdBean);
					}

					secondBean.setChildren(children4Second);
					children4First.add(secondBean);
				}

				firstBean.setChildren(children4First);
				children4Zero.add(firstBean);
			}
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("first".equals(tag)) {

			// 当前层级管理者为First
			FirstLevel firstLevel = firstLevelService.queryEntityById(lid);
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();

			if (null != firstLevel.getChildren() && firstLevel.getChildren().size() > 0)
				children4First.add(defaultBean);

			for (SecondLevel secondLevel : firstLevel.getChildren()) {
				Bean4InitSelector secondBean = new Bean4InitSelector();
				secondBean.setLabel(secondLevel.getName());
				secondBean.setValue("second_" + secondLevel.getScid());
				List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

				if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
					children4Second.add(defaultBean);

				for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
					Bean4InitSelector thirdBean = new Bean4InitSelector();
					thirdBean.setLabel(thirdLevel.getName());
					thirdBean.setValue("third_" + thirdLevel.getThid());
					List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

					if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
						children4Third.add(defaultBean);

					for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
						Bean4InitSelector fourthBean = new Bean4InitSelector();
						fourthBean.setLabel(fourthLevel.getName());
						fourthBean.setValue("fourth_" + fourthLevel.getFoid());
						fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
						children4Third.add(fourthBean);
					}

					thirdBean.setChildren(children4Third);
					children4Second.add(thirdBean);
				}

				secondBean.setChildren(children4Second);
				children4First.add(secondBean);
			}
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("second".equals(tag)) {

			// 当前层级管理者为Second
			SecondLevel secondLevel = secondLevelService.queryEntityById(lid);
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();

			if (null != secondLevel.getChildren() && secondLevel.getChildren().size() > 0)
				children4Second.add(defaultBean);

			for (ThirdLevel thirdLevel : secondLevel.getChildren()) {
				Bean4InitSelector thirdBean = new Bean4InitSelector();
				thirdBean.setLabel(thirdLevel.getName());
				thirdBean.setValue("third_" + thirdLevel.getThid());
				List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

				if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
					children4Third.add(defaultBean);

				for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
					Bean4InitSelector fourthBean = new Bean4InitSelector();
					fourthBean.setLabel(fourthLevel.getName());
					fourthBean.setValue("fourth_" + fourthLevel.getFoid());
					fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
					children4Third.add(fourthBean);
				}

				thirdBean.setChildren(children4Third);
				children4Second.add(thirdBean);
			}
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("third".equals(tag)) {

			// 当前层级管理者为Third

			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(lid);
			Bean4InitSelector thirdBean = new Bean4InitSelector();
			thirdBean.setLabel(thirdLevel.getName());
			thirdBean.setValue("third_" + thirdLevel.getThid());
			List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();

			if (null != thirdLevel.getChildren() && thirdLevel.getChildren().size() > 0)
				children4Third.add(defaultBean);

			for (FourthLevel fourthLevel : thirdLevel.getChildren()) {
				Bean4InitSelector fourthBean = new Bean4InitSelector();
				fourthBean.setLabel(fourthLevel.getName());
				fourthBean.setValue("fourth_" + fourthLevel.getFoid());
				fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针
				children4Third.add(fourthBean);
			}
			thirdBean.setChildren(children4Third);

			SecondLevel secondLevel = thirdLevel.getParent();
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
			children4Second.add(thirdBean);
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);

		} else if ("fourth".equals(tag)) {
			// 当前层级管理者为Fourth
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(lid);
			Bean4InitSelector fourthBean = new Bean4InitSelector();
			fourthBean.setLabel(fourthLevel.getName());
			fourthBean.setValue("fourth_" + fourthLevel.getFoid());
			fourthBean.setChildren(new ArrayList<Bean4InitSelector>()); // 防止JSON解析空指针

			ThirdLevel thirdLevel = fourthLevel.getParent();
			Bean4InitSelector thirdBean = new Bean4InitSelector();
			thirdBean.setLabel(thirdLevel.getName());
			thirdBean.setValue("third_" + thirdLevel.getThid());
			List<Bean4InitSelector> children4Third = new ArrayList<Bean4InitSelector>();
			children4Third.add(fourthBean);
			thirdBean.setChildren(children4Third);

			SecondLevel secondLevel = thirdLevel.getParent();
			Bean4InitSelector secondBean = new Bean4InitSelector();
			secondBean.setLabel(secondLevel.getName());
			secondBean.setValue("second_" + secondLevel.getScid());
			List<Bean4InitSelector> children4Second = new ArrayList<Bean4InitSelector>();
			children4Second.add(thirdBean);
			secondBean.setChildren(children4Second);

			FirstLevel firstLevel = secondLevel.getParent();
			Bean4InitSelector firstBean = new Bean4InitSelector();
			firstBean.setLabel(firstLevel.getName());
			firstBean.setValue("first_" + firstLevel.getFlid());
			List<Bean4InitSelector> children4First = new ArrayList<Bean4InitSelector>();
			children4First.add(secondBean);
			firstBean.setChildren(children4First);

			ZeroLevel zeroLevel = firstLevel.getParent();
			Bean4InitSelector zeroBean = new Bean4InitSelector();
			zeroBean.setLabel(zeroLevel.getName());
			zeroBean.setValue("zero_" + zeroLevel.getZid());
			List<Bean4InitSelector> children4Zero = new ArrayList<Bean4InitSelector>();
			children4Zero.add(firstBean);
			zeroBean.setChildren(children4Zero);

			MinusFirstLevel minusFirstLevel = zeroLevel.getParent();
			Bean4InitSelector minusFirstBean = new Bean4InitSelector();
			minusFirstBean.setLabel(minusFirstLevel.getName());
			minusFirstBean.setValue("minusFirst_" + minusFirstLevel.getMflid());
			List<Bean4InitSelector> children4MinusFirst = new ArrayList<Bean4InitSelector>();
			children4MinusFirst.add(zeroBean);
			minusFirstBean.setChildren(children4MinusFirst);

			total.add(minusFirstBean);
		}

		result.setDataSource(total);
		result.setDefaultValue(new ArrayList<String>());  // 前端picker-extend.js会检查数组深度，因此不能为null，需要给出一个空数组
		return result;
	}

}
