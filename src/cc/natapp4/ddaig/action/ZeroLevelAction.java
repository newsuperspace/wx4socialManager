package cc.natapp4.ddaig.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@Controller("zeroLevelAction")
@Scope("prototype")
@Lazy(true)
public class ZeroLevelAction implements ModelDriven<ZeroLevel> {

	// =================DI注入=================
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "projectTypeService")
	private ProjectTypeService projectTypeService;
	// 因为需要通过微信公众号服务器生成带参数二维码，这里需要DI注入WeixinService4Setting用来与微信服务器交互
	@Resource(name = "weixinService4Setting")
	private WeixinService4SettingImpl mpService;

	// =================模型驱动=================
	private ZeroLevel zeroLevel;

	@Override
	public ZeroLevel getModel() {
		zeroLevel = new ZeroLevel();
		return zeroLevel;
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

	// =================DI注入=================
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService  minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;
	@Resource(name = "firstLevelService")
	private FirstLevelService firstLevelService;

	// ====================Actions================
	/**
	 * 管理员权限者通过点击zero.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() {

		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		MinusFirstLevel parent = minusFirstLevelService.queryEntityById(lid);
		
		ReturnMessage4Common r = new ReturnMessage4Common();

		if ("".equals(zeroLevel.getDescription()) || "".equals(zeroLevel.getName())) {
			r.setMessage("关键数据为null，新建社区失败");
			r.setResult(false);
		}else if(null==parent){
			r.setResult(false);
			r.setMessage("在session域中未发现当前操作者的lid或当前操作者不存在于MinusFirstLevel目录中，创建失败");
		} else {
			ZeroLevel l = new ZeroLevel();
			/*
			 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
			 * 将该参数提交给微信端后会生成“带参数二维码”，
			 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
			 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
			 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
			 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
			 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
			 */
			StringBuffer sb = new StringBuffer();
			sb.append("level$");
			sb.append(ZeroLevel.LEVEL_ZERO);
			sb.append("_");
			sb.append("id$");
			String id = UUID.randomUUID().toString();
			sb.append(id);
			// 添加层级对象的id
			l.setZid(id);

			/*
			 * 通过调用getQrcodeFromWeixin方法获取带参数二维码，并将二维码图片保存到本地磁盘，在数据库保存函数
			 * 所返回的形如："qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"相对路径。
			 */
			String codePath = this.getQrcodeFromWeixin(id, sb.toString(), r);
			if("".equals(codePath)){
				r.setResult(false);
				r.setMessage("通过微信服务器创建新建层级的带参数二维码时出现异常，创建失败");
				ActionContext.getContext().getValueStack().push(r);
				return "json";
			}else{
				// 数据库要保存层级对象二维码的图片位置
				l.setQrcode(codePath);
				// 保存当前时刻的时间戳，用来记录临时带参数二维码的有效期
				l.setQrcodeTime(System.currentTimeMillis());
			}
			
			// 向持久化状态对象中存入其他数据信息（都是需要保存到数据库中去的）
			l.setDescription(zeroLevel.getDescription());
			l.setName(zeroLevel.getName());
			l.setParent(parent);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			/*
			 * ★★★
			 * 注意，不论是doingProject还是besureProject都与层级对象类似，
			 * 为了能够快速便利出某个层级对象之下所有子层级对象所执行的项目
			 * 也为了能够逐本溯源，找到项目的最初（通常是minusFirst或zeroLevel）
			 * 下发的层级对象，因此其中的MInusFirstLevel、ZeroLevel、FirstLevel、
			 * SecondLevel、ThirdLevel、FourthLevel都要从项目所属层级（当前就是ZeroLevel）
			 * 一直向上追溯到最顶层级（MinusFirstLevel）这样才能方便表现出项目的层级化关系
			 * 至少目前来说随层级创建的“默认项目”是这样的，
				简单言之就是已有层级字段是用来指示该项目当前执行者是谁，以及属于哪个更高层级之下；
				而新建字段是用来独立表示一个项目的层级关系，因为之后推行项目化运作方案，从minus（联合会或街道）可以向各个社区摊派积分，就是要通过带积分的大项目包的形式分派给各个社区，然后各个社区再次拆包成小项目分派各自的子层级，然后再拆包。而且社区有自己的经费也可以创建自己的带积分项目向下进行分派。
				所以所有拆出来的子项目都应该能追根溯源到其所属于的父项目包，然后每个项目包又能通过旧字段定位到当前执行项目的层级对象。
			 *
			 * 
			 */
			// 用作快速定位项目所属层级而设立属性
			dp.setMinusFirstLevel(parent);
			dp.setZeroLevel(l);
			// 用作快速定位项目所属层级而设立属性
			bp.setMinusFirstLevel(parent);
			bp.setZeroLevel(l);
			// 下面填写的是besureProject的信息字段的填写
			bp.setDescription("用作" + l.getName() + "层级对象默认使用的确认项目对象");
			bp.setDoingProject(dp);
			bp.setName("默认项目");
			bp.setCommitTime(System.currentTimeMillis());
			for (ProjectType pt : projectTypes) {
				// 所有新建的层级对象都必须默认绑定一个common类型的doingProject，用来开展日常（无积分）工作活动
				if (pt.getName().equals("common")) {
					bp.setProjectType(pt);
				}
			}
			Set<DoingProject> doingProjects = new HashSet<DoingProject>();
			doingProjects.add(dp);
			// 每个层级对象可以同时执行多个项目，因此要用一个集合来存放doingProject
			l.setDoingProjects(doingProjects);
			// 像数据库保存新建的层级对象，并级联地存储BesureProject/DoingProject等项目对象
			zeroLevelService.save(l);
			// 向前端返回的数据信息
			r.setMessage("新建成功");
			r.setResult(true);
		}
		// 返回JSON结果集
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 【微信端交互】
	 * 从微信端服务器获取带参数二维码（30天有效期），这样当用户扫描带参数二维码加入公众号的时候就会自动划归到该层级对象的直接管理层之下
	 * @param lid	层级对象的ID
	 * @param param	形如:"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的参数字符串
	 * @param r		用来保存通过AJAX向前端返回JSON信息的RetrunMessage4Common类型对象
	 * @return		形如："qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"本地带参数二维码图片相对路径
	 */
	public String getQrcodeFromWeixin(String lid,String param,ReturnMessage4Common r) {
		
		// 与微信服务器进行交互
		WxMpQrCodeTicket qrTicket = null;
		File inFile = null;
		try {
			// 将参数文本提交给微信服务器，微信会生成带参数的临时二维码（30天）
			qrTicket = mpService.getQrcodeService().qrCodeCreateTmpTicket(param, 2592000);
			// 通过票据换取指定带参数二维码的File
			inFile = mpService.getQrcodeService().qrCodePicture(qrTicket);
			if (null == inFile) {
				throw new RuntimeException("从微信端获取的带参数二维码的File是null");
			}
		} catch (WxErrorException e1) {
			e1.printStackTrace();
			String message = "从微信端获取带参数二维码时出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 将带参数二维码图片的File保存到本地，备用
		String codePath = "";
		codePath = "qrcode";
		int hashCode = lid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		// 由于不同文件系统的层级分隔符不一样（/正斜线或\反斜线），可以通过File.separator来自动适配分隔符，保证当前应用在任何操作系统中都能使用
		codePath = codePath + File.separator + first + File.separator + second;
		ServletContext context = ServletActionContext.getServletContext();
		// realPath就是服务器本地的真实路径，形如：C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
		String realPath = context.getRealPath(File.separator + codePath);
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File outFile = new File(realPath); // 路径，而非文件
		if (!outFile.exists()) {
			// 如果该路径不存在，则逐层创建路径
			outFile.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名仍然以层级对象的id值为名字，然后拓展名为jpg
		codePath = codePath + File.separator + lid + ".jpg";
		System.out.println("最终的codePath：" + codePath);
		realPath = realPath + File.separator + lid + ".jpg";
		System.out.println("最终的realPath:" + realPath);
		outFile = new File(realPath); // 文件，而非路径
		// 创建输入流和输出流，准备开始流对接
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(outFile);
			fis = new FileInputStream(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String message = "在将从微信服务器获取的存有二维码jpq图片的File保存到本次磁盘时，创建输入或输出流出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 开始流对接，temp为字节缓冲（1024B=1KB）————字节数组就是缓冲区，8位（bit）=1字节（B），因此字节是仅次于位的最小信息单位，用它存放二进制数据最合适
		byte[] temp = new byte[1024];
		// 记录每次从输入流转入到输出流中的字节数
		int len = 0;
		// 开始流对接
		try {
			while ((len = fis.read(temp)) != -1) {
				// 边读边写，一旦len=-1表明输入流中的数据全都读入了，没有额外数据可供读入了
				fos.write(temp, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return codePath;
	}

	/**
	 * 【AJAX交互】
	 * 通过点击zero.jsp页面上每个zeroLevel条目后的新建按钮，实现创建指定Zero层级对象的子对象，也就是FirstLevel的操作
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
			ZeroLevel parentLevel = zeroLevelService.queryEntityById(this.getParentId());

			FirstLevel sonLevel = new FirstLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			/*
			 * 参数是形如"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的字符串
			 * 将该参数提交给微信端后会生成“带参数二维码”，
			 * 用户扫码加入公众号后我们的服务器收到并转交由SubscribeHandler句柄处理的字符串信息是
			 * "qrscene_level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1",
			 * 我们通过解析该字符串就能获知用户扫码加入的是哪个层级对象
			 * split("_")分割出qrscene、level$0和id$f55669aa-b039-4919-ae23-
			 * 7c15472e29b1 三部分 再次split("$")第二段和第三段就可以获取到用户加入的是哪一层级的哪个层级对象了。
			 */
			StringBuffer sb = new StringBuffer();
			sb.append("level$");
			sb.append(FirstLevel.LEVEL_ONE);
			sb.append("_");
			sb.append("id$");
			String id = UUID.randomUUID().toString();
			sb.append(id);
			// 添加层级对象的id
			sonLevel.setFlid(id);

			/*
			 * 通过调用getQrcodeFromWeixin方法获取带参数二维码，并将二维码图片保存到本地磁盘，在数据库保存函数
			 * 所返回的形如："qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"相对路径。
			 */
			String codePath = this.getQrcodeFromWeixin(id, sb.toString(), r);
			if("".equals(codePath)){
				ActionContext.getContext().getValueStack().push(r);
				return "json";
			}else{
				// 数据库要保存层级对象二维码的图片位置
				sonLevel.setQrcode(codePath);
				// 保存当前时刻的时间戳，用来记录临时带参数二维码的有效期
				sonLevel.setQrcodeTime(System.currentTimeMillis());
			}

			sonLevel.setParent(parentLevel);

			// 准备层级对象的“默认项目”，用来该层级对象创建默认活动
			List<ProjectType> projectTypes = projectTypeService.queryEntities();
			BesureProject bp = new BesureProject();
			DoingProject dp = new DoingProject();

			dp.setBesureProject(bp);
			dp.setMinusFirstLevel(parentLevel.getParent());
			dp.setZeroLevel(parentLevel);
			dp.setFirstLevel(sonLevel);

			bp.setDoingProject(dp);
			bp.setMinusFirstLevel(parentLevel.getParent());
			bp.setZeroLevel(parentLevel);
			bp.setName("默认项目");
			bp.setDescription("用作" + sonLevel.getName() + "层级对象默认使用的确认项目对象");
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
			firstLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	
	/**
	 * 当前管理者获取下属的所有ZeroLeve对象
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

		List<ZeroLevel> list = null;
		// 分辨当前操作者是Admin还是非Admin
		if (isAdmin) {
			// 当前查访者是Admin,获取数据库中的所有ZeroLevel对象
			list = zeroLevelService.queryEntities();
		} else {
			list = new ArrayList<ZeroLevel>();
			// 当前查访者是非Admin管理者，进一步分析当前操作者执行者的层级位置，然后从children属性结构中获取当前操作者下属的层级对象
			String lid  = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			String tag  = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
			
			switch (tag) {
			// 对于非Admin用户来说，能够获取到Zeroleve层级对象信息的只可能是街道管理者
			case "minus_first":
				// 当前操作者是街道层级对象，要获取它下属的所有第一层级对象
				MinusFirstLevel minusFirstLevel = (MinusFirstLevel)minusFirstLevelService.queryEntityById(lid);
				// 然后获取到该街道层级下属的所有社区层级
				Set<ZeroLevel> children = minusFirstLevel.getChildren();
				// 从下属的社区层级中遍历出来的第一层级对象，就是当前操作者（街道层级）所管辖的全部第一层级对象
				for (ZeroLevel l : children) {
					list.add(l);
				}
				break;
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

		String id = zeroLevel.getZid();
		ZeroLevel l = zeroLevelService.queryEntityById(id);

		List<ZeroLevel> list = new ArrayList<ZeroLevel>();
		list.add(l);

		ActionContext.getContext().put("levels", list);
		return "list";
	}
}
