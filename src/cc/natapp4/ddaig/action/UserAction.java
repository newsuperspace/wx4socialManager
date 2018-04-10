package cc.natapp4.ddaig.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.ConfigUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.exception.WxErrorException;

@Controller("userAction")
@Scope("prototype")
@Lazy(true)
public class UserAction extends ActionSupport implements ModelDriven<User> {

	// ==========================================================DI注入Aspect
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl weixinService4Setting;
	@Resource(name = "roleService")
	private RoleService roleService;

	// ======================================================模型驱动——收纳请求参数
	private User user;

	@Override
	public User getModel() {
		user = new User();
		return this.user;
	}
	// ======================================================属性驱动——向前端页面传送经过处理的数据信息

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

	public String sendMessage2One() {

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (StringUtils.isBlank(this.openID)) {
			result.setResult(false);
			result.setMessage("用户的openID是空，不能发送信息");
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
	 * 供给后台用户管理系统使用
	 * 
	 * @return 结果集索引字符串
	 */
	public String getUserList() {
		List<User> users = userService.queryEntities();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (User u : users) {
			u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));
		}

		ActionContext.getContext().put("users", users);
		return "list";
	}

	/**
	 * 供给后台通过AJax技术，实现修改特定用户基本信息
	 */
	public String getUser4ajax() {

		String uid = this.user.getUid();

		User u = userService.queryEntityById(uid);

		if (null == u) {
			System.out.println("查找不到待修改的用户数据");
		} else {
			// 修改该用户的qrcode中保存的相对路径 → 拼接成绝对路径url，以此供后端页面上的infoModal对话框的<img
			// src=""/>的src属性使用，以显示该用户的独有qrcode
			String qrcodeUrl = ServletActionContext.getServletContext().getContextPath() + "/" + u.getQrcode();
			u.setQrcode(qrcodeUrl);
			// 处理注册时间，根据long类型的格力高丽丽偏移量毫秒值 经过格式转化成前端用户可识别的字符串信息
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));
		}

		ActionContext.getContext().getValueStack().push(u);
		return "json";
	}

	/**
	 * 根据后台提交来的修改用户的ajax请求提交过来的数据， 同时修改本地数据库的user数据 和修改微信公众号的用户tag
	 * 
	 * @return 结果集索引字符串
	 */
	public String update4Json() {
		String uid = this.user.getUid();
		String username = this.user.getUsername();
		String cardid = this.user.getCardid();
		String address = this.user.getAddress();
		String tag = this.tag;

		User u = userService.queryEntityById(uid);

		// u.setUsername(username);
		u.setCardid(cardid);
		u.setAddress(address);

		List<Grouping> list = groupingService.queryEntities();
		for (Grouping g : list) {
			if (g.getTag().equals(tag)) {
				u.setGrouping(g);

				String openid = u.getOpenid();
				if (org.springframework.util.StringUtils.isEmpty(openid)) {
					break;
				}
				// 修改微信公众号的该用户的tag
				String[] ids = { openid };
				try {
					weixinService4Setting.getUserTagService().batchTagging(g.getTagid(), ids);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
		userService.update(u);

		return "json";
	}

	// 承接从前端修改dialog中提过过来的tag数据
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 后台页面提交过来的新建用户的ajax请求
	 * 
	 * @return 结果集索引字符串
	 */
	public String addUser4ajax() {

		String username = user.getUsername();
		String address = user.getAddress();
		String tag = this.tag;
		String cardid = user.getCardid();

		User u = new User();
		u.setAddress(address);
		u.setCardid(cardid);
		u.setUsername(username);

		List<Grouping> list = groupingService.queryEntities();
		for (Grouping g : list) {
			if (g.getTag().equals(tag)) {
				u.setGrouping(g);
			}
		}
		userService.save(u);

		ReturnMessage4Common result = new ReturnMessage4Common("新建成功！", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 接受从后台传递来的ajax请求，用来批量重新生成每个用户的qrcode
	 * 
	 * @return 结果集索引字符串
	 */
	public String batchCreateUserQR4ajax() {

		userService.batchCreateUserQR();
		ReturnMessage4Common result = new ReturnMessage4Common("用户二维码批量生成成功", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	public String getExchangeList() {

		// String uid = this.getModel().getUid();
		// 切记一定要不这样获取数据驱动的请求参数，getModal()是给Struts框架使用的，不是给人使用的★★★★
		String uid = this.user.getUid();
		User u = userService.queryEntityById(uid);
		Set<Exchange> set = u.getExchanges();

		ActionContext.getContext().put("exchanges", set);
		return "exchangeList";
	}

	public String create() {

		/**
		 * TODO 需要权限认证，获取执行当前操作的管理者的所在权限， 然后新建的用户就置于该管理者的层级之下。
		 */

		ReturnMessage4Common message = new ReturnMessage4Common();

		User u = new User();
		u.setUsername(user.getUsername());
		u.setSickname(user.getSickname());
		u.setCardid(user.getCardid());
		u.setAddress(user.getAddress());
		u.setEmail(user.getEmail());
		u.setPhone(user.getPhone());
		u.setAge(user.getAge());
		u.setRegistrationTime(System.currentTimeMillis());

		Grouping g = groupingService.queryByTagName("common");
		u.setGrouping(g);

		if ("1".equals(user.getSex())) {
			u.setSex("男");
			userService.save(u);
			message.setMessage("新建成功！");
			message.setResult(true);
		} else if ("2".equals(user.getSex())) {
			u.setSex("女");
			userService.save(u);
			message.setMessage("新建成功！");
			message.setResult(true);
		} else {
			message.setResult(false);
			message.setMessage("未选择性别，新建失败");
		}

		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}

}
