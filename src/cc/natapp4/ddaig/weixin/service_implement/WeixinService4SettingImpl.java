package cc.natapp4.ddaig.weixin.service_implement;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCreatingTag;
import cc.natapp4.ddaig.service_implement.UserServiceImpl;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.ConfigUtils;
import cc.natapp4.ddaig.weixin.builder.TextBuilder;
import cc.natapp4.ddaig.weixin.service_interface.WeixinService4Setting;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.bean.menu.WxMenuRule;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu.WxMpConditionalMenu;
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

		// 根据本地配置file，创建Grouping
		this.initLocalTag();

		// 本地标签数据（grouping）与公众号tag数据同步更新 // 与公众号交互次数少，可能不会出现“45011
		// API调用太频繁,请稍候再试”的异常
		this.synchronizeTagInfo();

		// 同步用户数据 // 需要与公众号进行大量交互，可能会在一天中的不同时间段出现 “45011 API调用太频繁,请稍候再试”的异常
		this.synchronizeUserInfo();

		// 设置个性化菜单
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
	 * STEP1 根据本地配置file来想grouping中初始化本地tag标签
	 */
	private void initLocalTag() {
		// （必须，没有与公众号产生交互） 初始化Grouping
		List<Grouping> groupings = this.groupingService.queryEntities(); // 获取本地数据库中存放的Grouping对象，每个对象代表一个标签
		Properties p = ConfigUtils.getProperties("wxConfig/initTags.properties"); // 这里存放着系统初始化需要的标签
		if (null != groupings) {
			// 如果本地数据库中存在Grouping，那么需要比对现有grouping数量与initTag.properties记录的应有数量
			// 如果现有标签比应有标签数量少，则因该找出缺少哪个标签并进行创建；如果多或相同的话那就不用处理了
			if (groupings.size() < p.size()) {
				// 将本地数据库中已有的标签名放入到一个map容器中
				Map<String, String> m = new HashMap<String, String>();
				for (Grouping g : groupings) {
					String key = g.getTag();
					String value = "";
					m.put(key, value);
				}
				// 遍历initTags.properties,获取到应有标签并放到list容器中备用
				Enumeration<String> propertyNames = (Enumeration<String>) p.propertyNames();
				List<String> tagInitNames = new ArrayList<String>();
				while (propertyNames.hasMoreElements()) {
					String key = propertyNames.nextElement();
					tagInitNames.add(key);
				}
				// 最后遍历list容器，并比对map容器，如果存在则pass，如果不存在则在本地的Grouping中新建
				for (String tag : tagInitNames) {
					if (!m.containsKey(tag)) {
						Grouping g = new Grouping();
						g.setTag(tag);
						this.groupingService.save(g);
					}
				}
			}
		} else {
			// 如果得到groupings容器是null，则说明grouping数据库还没有任何一个标签数据，则彻底按照iniTags.properties的标准创新Grouping数据
			Iterator it = p.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				Grouping g = new Grouping();
				g.setTag(key);
				this.groupingService.save(g);
			}
		}
	}

	/**
	 * STEP3 个性化菜单设置，第一期工程暂时不将菜单结构存放在xml或properties中，直接在源码中写了。
	 */
	private void createMenu() {
		try {
			// 先删除公众号中的全部菜单，再重新创建菜单
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
		WxMenu selfmenu = new WxMenu(); // 默认菜单，其他没有标签的用户都将看到这个菜单，但当前项目是没有用的，因为每个新加入的用户都会被默认设定成no_real_name这个标签，只不过在创建菜单的时候必须有这么一个默认菜单
		WxMenu firstMenu = new WxMenu(); // 没有认证的用户的个性化菜单
		WxMenu secondMenu = new WxMenu(); // 普通用户的个性化菜单
		WxMenu thirdMenu = new WxMenu(); // 社区用户的个性化菜单
		// 创建三个个性化菜单规则对象
		WxMenuRule firstRule = new WxMenuRule();
		WxMenuRule secondRule = new WxMenuRule();
		WxMenuRule thirdRule = new WxMenuRule();
		// 配置规则，用以区分不同标签下的用户使用哪个个性化菜单
		List<Grouping> list = groupingService.queryEntities();
		for (Grouping g : list) {
			String tag = g.getTag();
			switch (tag) {
			case "no_real_name_user":
				firstRule.setTagId(String.valueOf(g.getTagid()));
				break;
			case "common_user":
				secondRule.setTagId(String.valueOf(g.getTagid()));
				break;
			case "community_user":
				thirdRule.setTagId(String.valueOf(g.getTagid()));
				break;
			}
		}
		// 将规则设置到menu对象上
		firstMenu.setMatchRule(firstRule);
		secondMenu.setMatchRule(secondRule);
		thirdMenu.setMatchRule(thirdRule);
		// 开始设置菜单项目,一级菜单最多只能有三个，切记！
		// 设计默认菜单
		WxMenuButton button4SelfMenu1 = new WxMenuButton();
		button4SelfMenu1.setType(WxConsts.BUTTON_CLICK);
		button4SelfMenu1.setName("初始化中");
		button4SelfMenu1.setKey("self_1_1");
		selfmenu.getButtons().add(button4SelfMenu1);

		// 设计第一个个性化菜单(实名认证功能)
		WxMenuButton button4FirstMenu1 = new WxMenuButton();
		firstMenu.getButtons().add(button4FirstMenu1);

		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
		String url = p.getProperty("webroot") + "/" + "www/index.html";
		String authorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
		System.out.println("oauth2授权的实名认证页面的url是：" + authorizationUrl);

		button4FirstMenu1.setType(WxConsts.BUTTON_VIEW);
		button4FirstMenu1.setName("实名认证");
		button4FirstMenu1.setUrl(authorizationUrl);
		// 设计第二个个性化菜单（普通用户）
		WxMenuButton button4SecondMenu1 = new WxMenuButton();
		WxMenuButton button4SecondMenu2 = new WxMenuButton();

		secondMenu.getButtons().add(button4SecondMenu1);
		secondMenu.getButtons().add(button4SecondMenu2);

		// ★★这里的按键类型一定要选择BUTTON_SCANCODE_WAITMSG才能将扫码结果传到咱们的服务器执行后续处理，
		// 如果选择的是BUTTON_SCANCODE_PUSH则在扫码的时候只会弹出扫码结果（一个字符串的页面）
		button4SecondMenu1.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
		button4SecondMenu1.setName("扫码签到");
		// ★★★ 除了click，扫码推等按钮功能也要佩戴Key，用以在同类按钮中进行区分，切记！不然会爆出invalid button
		// key的异常
		button4SecondMenu1.setKey("second_1");
		// 创建子菜单项
		WxMenuButton button4SecondMenu2_1 = new WxMenuButton();
		// WxMenuButton button4SecondMenu2_2 = new WxMenuButton();
		// WxMenuButton button4SecondMenu2_3 = new WxMenuButton();
		WxMenuButton button4SecondMenu2_4 = new WxMenuButton();
		// 设置子菜单项
		button4SecondMenu2_1.setType(WxConsts.BUTTON_CLICK);
		button4SecondMenu2_1.setName("当前积分");
		button4SecondMenu2_1.setKey("second_2_1");
		// button4SecondMenu2_2.setType(WxConsts.BUTTON_VIEW);
		// button4SecondMenu2_2.setName("公益商城");
		// button4SecondMenu2_2.setUrl("http://www.baidu.com");
		// button4SecondMenu2_3.setType(WxConsts.BUTTON_CLICK);
		// button4SecondMenu2_3.setName("用户中心");
		// button4SecondMenu2_3.setKey("second_2_3");
		button4SecondMenu2_4.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
		button4SecondMenu2_4.setKey("second_2_4");
		button4SecondMenu2_4.setName("积分兑换");
		// 向一级菜单项中添加添加子菜单项
		button4SecondMenu2.setName("功能列表");
		button4SecondMenu2.getSubButtons().add(button4SecondMenu2_1);
		// button4SecondMenu2.getSubButtons().add(button4SecondMenu2_2);
		// button4SecondMenu2.getSubButtons().add(button4SecondMenu2_3);
		button4SecondMenu2.getSubButtons().add(button4SecondMenu2_4);

		// 设计第三个个性化菜单
		WxMenuButton button4ThirdMenu1 = new WxMenuButton();
		WxMenuButton button4ThirdMenu2 = new WxMenuButton();
		
		thirdMenu.getButtons().add(button4ThirdMenu1);
		thirdMenu.getButtons().add(button4ThirdMenu2);
		
		url = p.getProperty("webroot") + "/" + "list/index.html";
		authorizationUrl = this.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, null);
		System.out.println("oauth2授权的发起活动页面的url是：" + authorizationUrl);
		button4ThirdMenu1.setType(WxConsts.BUTTON_VIEW);
		button4ThirdMenu1.setName("发起活动");
		button4ThirdMenu1.setUrl(authorizationUrl);
		
		button4ThirdMenu2.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
		button4ThirdMenu2.setName("登录后台");
		button4ThirdMenu2.setKey("third_2");

		// 至此三个个性化菜单都准备好了，可以向公众号中创建了
		boolean b  =  false;
		// 创建菜单的时候必须先创建默认菜单，不然会报错。虽然当前工程不需要这个默认菜单 ★★
		do {
			b = true;
			try {
				this.getMenuService().menuCreate(selfmenu);
				b = false;
				System.out.println("默认菜单创建成功！");
			} catch (WxErrorException e) {
				try {
					// 等待3秒
					Thread.sleep(3*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (b);
		// 第一个菜单（实名认证）
		do {
			b = true;
			try {
				this.getMenuService().menuCreate(firstMenu); 
				b = false;
				System.out.println("第一个菜单创建成功！");
			} catch (WxErrorException e) {
				try {
					// 等待3秒
					Thread.sleep(3*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (b);
		// 第二个菜单（普通用户）
		do {
			b = true;
			try {
				this.getMenuService().menuCreate(secondMenu); 
				b = false;
				System.out.println("第二个菜单创建成功！");
			} catch (WxErrorException e) {
				try {
					// 等待3秒
					Thread.sleep(3*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (b);
		// 第三个菜单（社区用户）
		do {
			b = true;
			try {
				this.getMenuService().menuCreate(thirdMenu); 
				b = false;
				System.out.println("第三个菜单创建成功！");
			} catch (WxErrorException e) {
				try {
					// 等待3秒
					Thread.sleep(3*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (b);
		System.out.println("全部菜单初始化成功！");
	}

	/**
	 * STEP4 同步公众号与本地数据中的用户数据
	 */
	private void synchronizeUserInfo() {
		WxMpUserList mpUserList = null;
		try {
			// 获取公众号中的全部用户对象
			mpUserList = this.getUserService().userList(null);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 先将本地数据库中所有User的isHere标志设置成false
		List<User> users = this.userService.queryEntities();
		for (User user : users) {
			user.setIshere(false);
			this.userService.update(user);
		}
		// 然后再看看公众号中存在的用户
		if (null != mpUserList && mpUserList.getCount() > 0) {
			// 说明此时公众号中存在用户，则应该同步用户数据到本地数据库（老用户修改ishere，新用户新建User）
			List<String> openids = mpUserList.getOpenids();
			for (String openid : openids) {
				User user = userService.queryByOpenId(openid);
				if (null != user) {
					// 说明该用户在公众号和本地都存在，是老用户，则需要对其进行tag同步
					long tagid = user.getGrouping().getTagid();
					String[] ids = { openid };
					try {
						this.getUserTagService().batchTagging(tagid, ids);
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
					// 该用户还在公众平台，因此同步本地数据库的ishere标志
					user.setIshere(true);
					this.userService.update(user);
				} else {
					// 说明这是在服务器离线的时候新加入的用户，应该对其标签设置成"未认证"——no_real_name_user
					user = new User();
					user.setOpenid(openid);
					user.setIshere(true);
					List<Grouping> list = this.groupingService.queryEntities();
					for (Grouping g : list) {
						if (g.getTag().equals("no_real_name_user")) {

							// 新建User对象到本地数据库保存
							user.setGrouping(g); // 新建User一定要与Grouping进行绑定
							this.userService.saveInInit(user);

							// 最后在公众号中设置"非认证用户"的tag就可以了
							String[] ids = { openid };
							try {
								this.getUserTagService().batchTagging(g.getTagid(), ids);
							} catch (WxErrorException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

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
