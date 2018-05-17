package cc.natapp4.ddaig.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Appoint;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.ManagerService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
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
	@Resource(name = "managerService")
	private ManagerService managerService;
	@Resource(name = "groupingService")
	private GroupingService groupingService;
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
	private int level;
	private String lid;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
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
	public String getUserInfo() {

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
	public String update() {

		String uid = this.user.getUid();
		String username = this.user.getUsername();
		String sickname = this.user.getSickname();
		String cardid = this.user.getCardid();
		int age = this.user.getAge();
		String phone = this.user.getPhone();
		String email = this.user.getEmail();
		String address = this.user.getAddress();
		String sex = this.user.getSex().equals("1") ? "男" : "女";
		String tag = this.tag;

		User u = userService.queryEntityById(uid);

		u.setUsername(username);
		u.setSickname(sickname);
		u.setCardid(cardid);
		u.setAge(age);
		u.setPhone(phone);
		u.setEmail(email);
		u.setAddress(address);
		u.setSex(sex);

		List<Grouping> list = groupingService.queryEntities();
		for (Grouping g : list) {
			if (g.getTag().equals(tag)) {
				u.setGrouping(g);

				String openid = u.getOpenid();
				if (org.springframework.util.StringUtils.isEmpty(openid)) {
					break;
				}
				/*
				 * 接入微信平台后需要开启这部分代码用来修改微信公众号的该用户的tag
				 */
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
	 * 接受从后台传递来的ajax请求，用来批量重新生成每个用户的qrcode
	 * 
	 * @return 结果集索引字符串
	 */
	public String batchCreateQR() {

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
		 * TODO shiro需要权限认证，获取执行当前操作的管理者的所在权限， 然后新建的用户就置于该管理者的层级之下。
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

	/**
	 * 在managerList.jsp上显示管理者目录列表
	 */
	public String getManagerList() {

		String tag = this.getTag();
		if (null == tag) {
			tag = "nonono";
		}

		List<User> users = userService.getManagers(tag);
		ActionContext.getContext().put("users", users);
		return "managerList";
	}

	/**
	 * 用来供给managerList.jsp页面上的appointModal 显示必要的层级对象信息
	 * 
	 * @return
	 */
	public String getAppointInfo() {
		ReturnMessage4Appoint  result = null;
		// 获取待任命者的uid
		String uid  = this.user.getUid();
		// 得到待任命者的user对象
		User u = userService.queryEntityById(uid);
		// 得到待任命者的tag
		String t = u.getGrouping().getTag();
		// 进而判断待任命者的级别
		int lowest = 10086;  // 10086表示超出系统层级范围，应该在前端报错
		switch (t) {
		case "minus_first":
			lowest = -1;
			break;
		case "zero":
			lowest = 0;
			break;
		case "first":
			lowest = 1;
			break;
		case "second":
			lowest = 2;
			break;
		case "third":
			lowest = 3;
			break;
		case "fourth":
			lowest = 4;
			break;
		default:
			lowest = 10086;
			break;
		}
		// TODO shiro 通过shiro获取subject进而获取到当前操作的管理者信息
		int controller = 10086; // 未启动shiro前，10086默认代表admin
			
		result = new ReturnMessage4Appoint();
		if(10086==lowest){
			result.setMessage("错误：待委任者层级超出系统范围10086");
			result.setResult(false);
		}else if(10086==controller){
			// 当前操作是Admin操作的，应该将所有层级对象数据信息获取出来
			List<MinusFirstLevel> minusLevels = minusFirstLevelService.queryEntities();
			result.setMessage("执行当前操作的是Admin");
			result.setResult(true);
			result.setController(controller);
			result.setLowest(lowest);
			result.setMinusLevels(minusLevels);
		}else{
			// TODO shiro 当前操作是非Admin操作的，应该根据操作者层级对象来构建返回数据
			// 为了防止在JSON解析时发生死循环应该切断“下层级对象”与“上层级对象”的联系（null化）
		}
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * 本方法对应前端的managerModal.op.changeAppointSelect()中的Ajax请求
	 * 根据操作者在managerList.jsp的AppointModal中的选择情况（由id=appoint-1~4的Select的onchange事件触发本方法）
	 * 然后更新下一级的以及后续层级（直到fourthLevel）中的显示内容（option选项） 
	 * level:int类型  触发onchange事件的select对应的层级（也就是其id=appointX中的X的数字）
	 * lid： 为操作者选中的层级对象的id
	 */
	public String getAppointSelectInfo(){
		
		int level = this.getLevel();
		String lid = this.getLid();
		if(lid.isEmpty()|| level>4 || level<-1){
			ReturnMessage4Common  result  =  new  ReturnMessage4Common();
			result.setMessage("必要参数level或lid为NULL，操作不予执行");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
		}else{
			ReturnMessage4Appoint  result  =  new  ReturnMessage4Appoint();
			switch (level) {
			case -1:  // 从街道层级中查找
				MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
				if(null==minusFirst){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的MinusFirstLevel层级对象已获取");
					result.setResult(true);
					result.setMinusFirst(minusFirst);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;  
			case 0:	// 从社区层级中查找
				ZeroLevel zero = zeroLevelService.queryEntityById(lid);
				if(null==zero){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的ZeroLevel层级对象已获取");
					result.setResult(true);
					result.setZero(zero);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;	
			case 1:	// 从第一层级中查找
				FirstLevel first = firstLevelService.queryEntityById(lid);
				if(null==first){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的FirstLevel层级对象已获取");
					result.setResult(true);
					result.setFirst(first);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 2:	// 从第二层级中查找
				SecondLevel second = secondLevelService.queryEntityById(lid);
				if(null==second){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的SecondLevel层级对象已获取");
					result.setResult(true);
					result.setSecond(second);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 3:	// 从第三层级中查找
				ThirdLevel third = thirdLevelService.queryEntityById(lid);
				if(null==third){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的ThirdLevel层级对象已获取");
					result.setResult(true);
					result.setThird(third);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			case 4:	// 从第四层级中查找
				FourthLevel fourth = fourthLevelService.queryEntityById(lid);
				if(null==fourth){
					result.setMessage("不存在id为："+lid+"的层级对象");
					result.setResult(false);
				}else{
					result.setMessage("查询的FourthLevel层级对象已获取");
					result.setResult(true);
					result.setFourth(fourth);
				}
				ActionContext.getContext().getValueStack().push(result);
				break;
			default:
				ReturnMessage4Common  result2  =  new  ReturnMessage4Common();
				result.setMessage("level的数值超出系统层级范围");
				result.setResult(false);
				ActionContext.getContext().getValueStack().push(result2);
				break;
			}
		}
		
		return "json";
	}
	

	/**
	 * 解除任命
	 * @return
	 */
	public String doDisappoint(){
		
		String uid  =  this.user.getUid();
		User u = userService.queryEntityById(uid);
		Manager m = u.getManager();
		u.setManager(null);
		managerService.delete(m);
		
		ReturnMessage4Common   result  =  new  ReturnMessage4Common("解任成功！", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	
	/**
	 * 正式执行用户（manager）与层级对象的绑定（委任）操作
	 * @return
	 */
	public String doAppoint(){
		// 层级对象的所属层级（-1、0、1、2、3、4）
		int level  = this.getLevel();
		// 待委任的管理者的uid
		String uid  = this.user.getUid();
		// 待委任的层级对象的id
		String lid  =  this.getLid();
		// 开始执行
		User u = null;
		Manager m = null;
		switch (level) {
		case -1:
			MinusFirstLevel minusFirst = minusFirstLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<MinusFirstLevel>  mfls  =  new  HashSet<MinusFirstLevel>();
			mfls.add(minusFirst);
			m.setMfls(mfls);
			break;
		case 0:
			ZeroLevel zero = zeroLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<ZeroLevel>  zls  =  new  HashSet<ZeroLevel>();
			zls.add(zero);
			m.setZls(zls);
			break;
		case 1:
			FirstLevel first = firstLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<FirstLevel>  fls  =  new  HashSet<FirstLevel>();
			fls.add(first);
			m.setFls(fls);
			break;
		case 2:
			SecondLevel second = secondLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<SecondLevel>  scls  =  new  HashSet<SecondLevel>();
			scls.add(second);
			m.setScls(scls);
			break;
		case 3:
			ThirdLevel third = thirdLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<ThirdLevel>  tls  =  new  HashSet<ThirdLevel>();
			tls.add(third);
			m.setTls(tls);
			break;
		case 4:
			FourthLevel fourth = fourthLevelService.queryEntityById(lid);
			u = userService.queryEntityById(uid);
			m  =   new  Manager();
			m.setUser(u);
			Set<FourthLevel>  fols  =  new  HashSet<FourthLevel>();
			fols.add(fourth);
			m.setFols(fols);
			break;
		}
		managerService.save(m);
		
		ReturnMessage4Common  result =  new  ReturnMessage4Common("委任成功", true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

}
