package cc.natapp4.ddaig.weixin.service_implement;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCreatingTag;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.ConfigUtils;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Setting;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

@Service("weixinService4Setting")
@Lazy(true)
public class WeixinService4SettingImpl extends WeixinServiceAbstract implements WeixinService4Setting {

	// ============================aspect==============================
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name="projectTypeService")
	private ProjectTypeService projectTypeService;

	// ============================Builder=============================
	@Resource(name = "textBuilder")
	private TextBuilder textBuilder;

	// ============================Method=============================

	@Override
	public WxMpXmlOutMessage route(WxMpXmlMessage message) {
		// 当前Service主要是管理员主动向微信服务器发送设置公众号的请求，因此不需要实现这个方法
		return null;
	}

	@PostConstruct
	public void init() {
		/**
		 * TODO  当进入Weixin开发层次的时候一定要处理这里
		 * 这里的这个写法非常的垃圾，极高的耦合性，使得在Spring初始化当前类————名叫weixinService4Settring的Bean的时候，严重与Servlet环境耦合
		 * 导致频繁出现异常。
		 * 我觉得如果是保存重要的数据可以放在properties文件等位置，并非一定需要ServletContext域来存储有关weixin公众号的数据信息。
		 */
		WxMpInMemoryConfigStorage config = (WxMpInMemoryConfigStorage) ServletActionContext.getServletContext()
				.getAttribute("config");
		super.setWxMpConfigStorage(config);
	}

	@Override
	public void createTag(Grouping grouping) throws WeixinExceptionWhenCreatingTag {

		WxUserTag res = null;
		try {
			res = this.getUserTagService().tagCreate(grouping.getTag());
		} catch (WxErrorException e) {
			e.printStackTrace();
			throw new WeixinExceptionWhenCreatingTag();
		}
		grouping.setTagid(res.getId());
		groupingService.save(grouping);
	}

	@Override
	public boolean sendTextMessage2One(String openID, String content) {
		/*
		 * 向用户主动发送消息就需要借助客服功能了
		 * 
		 * 先组织特定类型的消息对象 WxMpKefuMessage
		 */
		WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser(openID).content(content).build();

		try {
			// 使用专用的客服KefuService的方法来发送这个消息
			return this.getKefuService().sendKefuMessage(message);
		} catch (WxErrorException e) {
			logger.info("向openID是" + openID + "的用户发送客服消息出现异常，请检查故障原因");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setTag2One(String openID, Grouping grouping) {
		try {
			this.getUserTagService().tagUpdate(grouping.getTagid(), openID);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTag2Many(List<String> openIDs, Grouping grouping) {
		// TODO 还没实现呢
	}

	@Override
	public void InitPlatform() {

		/*
		 * TODO 关于本地数据库中的基础信息的初始化全在这里完成，其中包括
		 * grouping数据库 --- wxConfig/initTags.properties
		 * projecttypes数据库---projectTypes.properties
		 * 
		 */
		// 根据本地配置file，创建Grouping
		this.initLocalTag();
		this.initProjectTypes();
		
		// 本地标签数据（grouping）与公众号tag数据同步更新 // 与公众号交互次数少，可能不会出现“45011
		// API调用太频繁,请稍候再试”的异常
//		this.synchronizeTagInfo();

		// 同步用户数据 // 需要与公众号进行大量交互，可能会在一天中的不同时间段出现 “45011 API调用太频繁,请稍候再试”的异常
		// TODO 目前新工程中我不希望增加交互时间影响系统稳定性，并且最好全部用户都是用默认Menu，因此这里先暂时性地隐藏初始化人员的操作，有需要再打开
//		this.synchronizeUserInfo();

		// 设置微信公众号的菜单（默认菜单——用户没有设置tag或所设置的tag没有对应绑定的个性化菜单时可见；个性化菜单——菜单与某个tag匹配，则所有拥有该tag的用户都能见到该个性化菜单）
		this.createMenu();

		// TODO 其他公众号初始化设置（根据业务需求不断添加）
		System.out.println("公众号初始化成功！！！！！");

	} // -----------------------InitPlatform()结束

	@Override
	public String getOauth2Url(String redirectURI, String scope, String state) {
		String url = this.oauth2buildAuthorizationUrl(redirectURI, scope, state);
		System.out.println("认证后的URL为：" + url);
		return url;
	}


	/**
	 * STEP1 在本地数据库创建projectTypes信息
	 */
	private void initProjectTypes(){
		Properties p = ConfigUtils.getProperties("wxConfig/projectTypes.properties");
		Enumeration<String> keys = (Enumeration<String>)p.propertyNames();
		
		ProjectType pt  =  null;
		List<ProjectType> projectTypes = this.projectTypeService.queryEntities();
		if(null==projectTypes || 0==projectTypes.size()){
			// 数据库projecttype中没有任何数据，则直接重新创建
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				pt =  new  ProjectType();
				pt.setDescription(p.getProperty(key));
				pt.setName(key);
				projectTypeService.save(pt);
			}
		}else{
			// 数据库中存在旧projectType数据信息，需要经过比对修复
			while(keys.hasMoreElements()){
				boolean ishere = false;
				// 先获取到待比对的projectType的name字段值
				String name = keys.nextElement();
				for(ProjectType proT: projectTypes){
					// 与数据库中已有projectType的name字段值进行比较，相同则说明已经存在无需再在数据库中创建了
					if(name.equals(proT.getName())){
						// 以防万一更新一下该projectType的description
						proT.setDescription(p.getProperty(name));
						projectTypeService.update(proT);
						ishere = true;
						break;
					}
				}
				if(!ishere){
					pt =  new  ProjectType();
					pt.setDescription(p.getProperty(name));
					pt.setName(name);
					projectTypeService.save(pt);
				}
			}
		}
	}
	
	/**
	 * STEP2 根据本地配置file来向grouping数据库表中初始化本地tag标签
	 */
	private void initLocalTag() {
		// （必须，没有与公众号产生交互） 初始化Grouping
		List<Grouping> groupings = this.groupingService.queryEntities(); // 获取本地数据库中存放的Grouping对象，每个对象代表一个标签
		Properties p = ConfigUtils.getProperties("wxConfig/initTags.properties"); // 这里存放着系统初始化需要的标签
		Iterator it = null;
		if (null != groupings) {
			// 如果本地数据库中存在Grouping，那么需要比对现有grouping数量与initTag.properties记录的应有数量
			
			it = p.entrySet().iterator();
			while(it.hasNext()){
				// 标记属性——用来表示当前tag是否已经存在于数据库了，默认为false
				boolean isExist = false;
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				for(Grouping g: groupings){
					// 循环遍历数据库grouping表中已有的tag数据，目标是检测该tag是否已经存在于数据库中了，存在则pass不存在则向数据库新建
					if(g.getTag().equals(key)){
						// 数据库中存在该tag，直接结束循环
						isExist = true;
						break;
					}
				}
				if(!isExist){
					// 如果标签不存在，则向数据库中新建一个grouping数据
					Grouping grouping = new  Grouping();
					grouping.setTag(key);
					grouping.setGroupName(value);
					this.groupingService.save(grouping);
				}
			}
		} else {
			// 如果得到groupings容器是null，则说明grouping数据库还没有任何一个标签数据，则彻底按照iniTags.properties的标准创新Grouping数据
			it = p.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				Grouping g = new Grouping();
				g.setTag(key);
				g.setGroupName(value);
				this.groupingService.save(g);
			}
		}
	}

	/**
	 * STEP3 个性化菜单设置，第一期工程暂时不将菜单结构存放在xml或properties中，直接在源码中写了。
	 */
	private void createMenu() {
		
		// 先删除公众号中的全部菜单，再重新创建菜单
		try {
			// 先获取当前公众号中的所有Menu（一个menu+多个可能的个性化menu）
			WxMpMenu menuGet = this.getMenuService().menuGet();
			if (null != menuGet) {
				// 如果存在主Menu，则将包括主Menu和个性化菜单在内的所有菜单清除干净
				this.getMenuService().menuDelete();
			}
		} catch (WxErrorException e1) {
			e1.printStackTrace();
		}

		// 创建三个菜单对象，每个菜单对象对应一种用户标签的用户权限
		WxMenu selfMenu = new WxMenu(); // 默认菜单，其他没有标签的用户都将看到这个菜单，但当前项目是没有用的，因为每个新加入的用户都会被默认设定成no_real_name这个标签，只不过在创建菜单的时候必须有这么一个默认菜单
//		WxMenu firstMenu = new WxMenu(); // 没有认证的用户的个性化菜单
//		WxMenu secondMenu = new WxMenu(); // 普通用户的个性化菜单
//		WxMenu thirdMenu = new WxMenu(); // 社区用户的个性化菜单
//		// 创建三个个性化菜单规则对象
//		WxMenuRule firstRule = new WxMenuRule();
//		WxMenuRule secondRule = new WxMenuRule();
//		WxMenuRule thirdRule = new WxMenuRule();
//		// 配置规则，用以区分不同标签下的用户使用哪个个性化菜单
//		List<Grouping> list = groupingService.queryEntities();
//		for (Grouping g : list) {
//			String tag = g.getTag();
//			switch (tag) {
//			case "no_real_name_user":
//				firstRule.setTagId(String.valueOf(g.getTagid()));
//				break;
//			case "common_user":
//				secondRule.setTagId(String.valueOf(g.getTagid()));
//				break;
//			case "community_user":
//				thirdRule.setTagId(String.valueOf(g.getTagid()));
//				break;
//			}
//		}
//		// 将规则设置到menu对象上
//		firstMenu.setMatchRule(firstRule);
//		secondMenu.setMatchRule(secondRule);
//		thirdMenu.setMatchRule(thirdRule);
//		// 开始设置菜单项目,一级菜单最多只能有三个，切记！第二级菜单下只能有5个子按钮，且微信公众号的所有菜单按钮名称字数不能超过四个
		/**
		 * =======================开始设计默认菜单=======================
		 */
		// ------------------先创建菜单的第一级目录结构（微信公众号第一级最多有三个）------------------
		WxMenuButton selfMenuButton1 = new WxMenuButton();
		selfMenuButton1.setName("身边公益");
		WxMenuButton selfMenuButton2 = new WxMenuButton();
		selfMenuButton2.setName("便民服务");
		WxMenuButton selfMenuButton3 = new WxMenuButton();
		selfMenuButton3.setName("工具箱");
		// 将三个一级目录加入到公众号默认菜单中
		selfMenu.getButtons().add(selfMenuButton1);
		selfMenu.getButtons().add(selfMenuButton2);
		selfMenu.getButtons().add(selfMenuButton3);
		
		// ------------------然后创建每个第一级目录下的二级结构（二级菜单最多5个，二级菜单才是功能的直接入口！）------------------
		WxMenuButton  button = null;
		// ~~~~~~~~~~~~~~~~~设计“身边公益”~~~~~~~~~~~~~~~~~
		// (1)社区动态按钮(占位置)
		button = new WxMenuButton();
		button.setType(WxConsts.BUTTON_VIEW);
//		button.setKey("self_gy_dt");
		button.setKey("self_gy_dt");
		button.setName("社区动态");
		// ★★★微信菜单中的URL设置一定要以http://开头★★★
		button.setUrl("http://www.baidu.com");
		selfMenuButton1.getSubButtons().add(button);
		// （2）...
		// ~~~~~~~~~~~~~~~~~设计“便民服务”~~~~~~~~~~~~~~~~~
		// (1)进入用户中心的按钮，如果用户没有实名认证，则自动跳转到实名认证页面
		button = new WxMenuButton();
		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
		String url = p.getProperty("webroot") + "/" + "personalCenterAction_accessPersonalCenter.action";
		// 由于登录“实名认证”页面目前需要至少获取到用户的openID，因此需要将认证页面的url进行OAUTH2的认证，然后把返回的OAUTH2的路径作为该按钮点击后的访问路径设置
		String oauth2buildAuthorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
		button.setType(WxConsts.BUTTON_VIEW);
		button.setName("用户中心");
		button.setKey("self_bm_center");
		button.setUrl(oauth2buildAuthorizationUrl);
		selfMenuButton2.getSubButtons().add(button);
		// (2)获取积分
		button = new  WxMenuButton();
		button.setName("获取积分分值");
		button.setType(WxConsts.BUTTON_CLICK);
		button.setKey("self_bm_score");
		selfMenuButton2.getSubButtons().add(button);
		// (3)获取积分
		button = new  WxMenuButton();
		button.setName("选择地理位置");
		button.setType(WxConsts.BUTTON_LOCATION_SELECT);
		button.setKey("self_bm_location");
		selfMenuButton2.getSubButtons().add(button);
		// （4）....
		// ~~~~~~~~~~~~~~~~~设计“功能列表”~~~~~~~~~~~~~~~~~
		// （1）扫码登录
		button = new WxMenuButton();
		button.setName("扫码登录系统");
		button.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
		button.setKey("self_gn_login");
		selfMenuButton3.getSubButtons().add(button);
		// （2）管理平台
		button = new WxMenuButton();
		button.setName("进入系统后台");
		button.setType(WxConsts.BUTTON_VIEW);
		button.setKey("self_gn_system");
		url = p.getProperty("webroot");
		oauth2buildAuthorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
		button.setUrl(oauth2buildAuthorizationUrl);
		selfMenuButton3.getSubButtons().add(button);
		// （3）积分兑换
		button = new WxMenuButton();
		button.setName("积分兑换");
		button.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
		button.setKey("self_gn_dh");
		selfMenuButton3.getSubButtons().add(button);
		
		

//		// 设计第一个个性化菜单(实名认证功能)
//		WxMenuButton button4FirstMenu1 = new WxMenuButton();
//		firstMenu.getButtons().add(button4FirstMenu1);
//
//		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
//		String url = p.getProperty("webroot") + "/" + "www/index.html";
//		String authorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
//		System.out.println("oauth2授权的实名认证页面的url是：" + authorizationUrl);
//
//		button4FirstMenu1.setType(WxConsts.BUTTON_VIEW);
//		button4FirstMenu1.setName("实名认证");
//		button4FirstMenu1.setUrl(authorizationUrl);
//		// 设计第二个个性化菜单（普通用户）
//		WxMenuButton button4SecondMenu1 = new WxMenuButton();
//		WxMenuButton button4SecondMenu2 = new WxMenuButton();
//
//		secondMenu.getButtons().add(button4SecondMenu1);
//		secondMenu.getButtons().add(button4SecondMenu2);
//
//		// ★★这里的按键类型一定要选择BUTTON_SCANCODE_WAITMSG才能将扫码结果传到咱们的服务器执行后续处理，
//		// 如果选择的是BUTTON_SCANCODE_PUSH则在扫码的时候只会弹出扫码结果（一个字符串的页面）
//		button4SecondMenu1.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
//		button4SecondMenu1.setName("扫码签到");
//		// ★★★ 除了click，扫码推等按钮功能也要佩戴Key，用以在同类按钮中进行区分，切记！不然会爆出invalid button
//		// key的异常
//		button4SecondMenu1.setKey("second_1");
//		// 创建子菜单项
//		WxMenuButton button4SecondMenu2_1 = new WxMenuButton();
//		// WxMenuButton button4SecondMenu2_2 = new WxMenuButton();
//		// WxMenuButton button4SecondMenu2_3 = new WxMenuButton();
//		WxMenuButton button4SecondMenu2_4 = new WxMenuButton();
//		// 设置子菜单项
//		button4SecondMenu2_1.setType(WxConsts.BUTTON_CLICK);
//		button4SecondMenu2_1.setName("当前积分");
//		button4SecondMenu2_1.setKey("second_2_1");
//		// button4SecondMenu2_2.setType(WxConsts.BUTTON_VIEW);
//		// button4SecondMenu2_2.setName("公益商城");
//		// button4SecondMenu2_2.setUrl("http://www.baidu.com");
//		// button4SecondMenu2_3.setType(WxConsts.BUTTON_CLICK);
//		// button4SecondMenu2_3.setName("用户中心");
//		// button4SecondMenu2_3.setKey("second_2_3");
//		button4SecondMenu2_4.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
//		button4SecondMenu2_4.setKey("second_2_4");
//		button4SecondMenu2_4.setName("积分兑换");
//		// 向一级菜单项中添加添加子菜单项
//		button4SecondMenu2.setName("功能列表");
//		button4SecondMenu2.getSubButtons().add(button4SecondMenu2_1);
//		// button4SecondMenu2.getSubButtons().add(button4SecondMenu2_2);
//		// button4SecondMenu2.getSubButtons().add(button4SecondMenu2_3);
//		button4SecondMenu2.getSubButtons().add(button4SecondMenu2_4);
//
//		// 设计第三个个性化菜单
//		WxMenuButton button4ThirdMenu1 = new WxMenuButton();
//		WxMenuButton button4ThirdMenu2 = new WxMenuButton();
//		
//		thirdMenu.getButtons().add(button4ThirdMenu1);
//		thirdMenu.getButtons().add(button4ThirdMenu2);
//		
//		url = p.getProperty("webroot") + "/" + "list/index.html";
//		authorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
//		System.out.println("oauth2授权的发起活动页面的url是：" + authorizationUrl);
//		button4ThirdMenu1.setType(WxConsts.BUTTON_VIEW);
//		button4ThirdMenu1.setName("发起活动");
//		button4ThirdMenu1.setUrl(authorizationUrl);
//		
//		button4ThirdMenu2.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
//		button4ThirdMenu2.setName("登录后台");
//		button4ThirdMenu2.setKey("third_2");

		// 至此三个个性化菜单都准备好了，可以向公众号中创建了
		boolean b  =  false;
		// 创建菜单的时候必须先创建默认菜单，不然会报错。虽然当前工程不需要这个默认菜单 ★★
		System.out.println("=======开始创建默认菜单=======");
		do {
			b = true;
			try {
				this.getMenuService().menuCreate(selfMenu);
				b = false;
				System.out.println("默认菜单创建成功！");
			} catch (WxErrorException e) {
				e.printStackTrace();
				try {
					// 等待5秒
					Thread.sleep(5*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (b);
//		// 第一个菜单（实名认证）
//		do {
//			b = true;
//			try {
//				this.getMenuService().menuCreate(firstMenu); 
//				b = false;
//				System.out.println("第一个菜单创建成功！");
//			} catch (WxErrorException e) {
//				try {
//					// 等待3秒
//					Thread.sleep(3*1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		} while (b);
//		// 第二个菜单（普通用户）
//		do {
//			b = true;
//			try {
//				this.getMenuService().menuCreate(secondMenu); 
//				b = false;
//				System.out.println("第二个菜单创建成功！");
//			} catch (WxErrorException e) {
//				try {
//					// 等待3秒
//					Thread.sleep(3*1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		} while (b);
//		// 第三个菜单（社区用户）
//		do {
//			b = true;
//			try {
//				this.getMenuService().menuCreate(thirdMenu); 
//				b = false;
//				System.out.println("第三个菜单创建成功！");
//			} catch (WxErrorException e) {
//				try {
//					// 等待3秒
//					Thread.sleep(3*1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		} while (b);
		System.out.println("全部菜单初始化成功！");
	}

	/**
	 * STEP4 同步公众号与本地数据中的用户数据
	 */
//	private void synchronizeUserInfo() {
//		WxMpUserList mpUserList = null;
//		try {
//			// 获取公众号中的全部用户对象
//			mpUserList = this.getUserService().userList(null);
//		} catch (WxErrorException e) {
//			e.printStackTrace();
//		}
//		// 先将本地数据库中所有User的isHere标志设置成false
//		List<User> users = this.userService.queryEntities();
//		for (User user : users) {
//			user.setIshere(false);
//			this.userService.update(user);
//		}
//		// 然后再看看公众号中存在的用户
//		if (null != mpUserList && mpUserList.getCount() > 0) {
//			// 说明此时公众号中存在用户，则应该同步用户数据到本地数据库（老用户修改ishere，新用户新建User）
//			List<String> openids = mpUserList.getOpenids();
//			for (String openid : openids) {
//				User user = userService.queryByOpenId(openid);
//				if (null != user) {
//					// 说明该用户在公众号和本地都存在，是老用户，则需要对其进行tag同步
//					long tagid = user.getGrouping().getTagid();
//					String[] ids = { openid };
//					try {
//						this.getUserTagService().batchTagging(tagid, ids);
//					} catch (WxErrorException e) {
//						e.printStackTrace();
//					}
//					// 该用户还在公众平台，因此同步本地数据库的ishere标志
//					user.setIshere(true);
//					this.userService.update(user);
//				} else {
//					// 说明这是在服务器离线的时候新加入的用户，应该对其标签设置成"未认证"——no_real_name_user
//					user = new User();
//					user.setOpenid(openid);
//					user.setIshere(true);
//					List<Grouping> list = this.groupingService.queryEntities();
//					for (Grouping g : list) {
//						if (g.getTag().equals("no_real_name_user")) {
//
//							// 新建User对象到本地数据库保存
//							user.setGrouping(g); // 新建User一定要与Grouping进行绑定
//							this.userService.saveInInit(user);
//
//							// 最后在公众号中设置"非认证用户"的tag就可以了
//							String[] ids = { openid };
//							try {
//								this.getUserTagService().batchTagging(g.getTagid(), ids);
//							} catch (WxErrorException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			}
//		}
//	}

	/**
	 * STEP2 同步本地数据库（Grouping）与公众号中的tag
	 */
	private void synchronizeTagInfo() {
		List<WxUserTag> tagGet = null;
		try {
			// 获取公众号之前存在的全部tag的信息
			tagGet = this.getUserTagService().tagGet();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 获取公众号中每个标签下的用户，并将标签从用户中删除
		for (WxUserTag tag : tagGet) {
			long tagId = tag.getId();

			// 默认公众平台每个公众号都会有三个默认的保留tag，它们的id分别是
			// 0、1、2，这三个tag是不能删除的，其之下也不可能有任何用户
			if (0 == tagId || 1 == tagId || 2 == tagId)
				continue;

			WxTagListUser listUser = null;
			try {
				// 获取该tag下的所有用户对象
				listUser = this.getUserTagService().tagListUser(tagId, null);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}

			if (null != listUser && listUser.getCount() > 0) {
				// 建立一个容纳该标签下所有用户的openID的字符串数组（openID就是一串字符串）
				String[] openIDarray = new String[listUser.getCount()];
				for (int i = 0; i < listUser.getCount(); i++) {
					// 将用户的openID放入到数组中，备用
					openIDarray[i] = listUser.getNextOpenid();
				}

				try {
					// 然后批量解除该标签与用户的绑定关系，至此该标签下已经不存在任何用户了。
					this.getUserTagService().batchUntagging(tagId, openIDarray);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}

			// 删除tag。经过①中的循环，现在公众号中的所有旧tag下已经没有任何用户了，可以将这些旧tag删除了。
			try {
				this.getUserTagService().tagDelete(tagId);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}

		// 获取本地新建的tag，并向公众号中设置，同时将返回的tagID保存到本地对应的tag上，实现本地和公众号tag的彻底同步
		List<Grouping> groupingList = groupingService.queryEntities();
		for (Grouping g : groupingList) {
			String tag = g.getTag();
			try {
				WxUserTag userTag = this.getUserTagService().tagCreate(tag);
				g.setTagid(userTag.getId());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			this.groupingService.update(g);
		}
	}

}// --------------------------类结束
