package cc.natapp4.ddaig.action;

import java.io.File;
import java.util.ArrayList;
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
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("fourthLevelAction") // <!-- ● -->
@Scope("prototype")
@Lazy(true)
public class FourthLevelAction implements ModelDriven<FourthLevel> { // <!-- ●
																		// -->

	// =================DI注入=================
	@Resource(name = "userService")
	private UserService userService;

	// =================模型驱动================= <!-- ● -->
	private FourthLevel fourthLevel;

	@Override
	public FourthLevel getModel() {
		this.fourthLevel = new FourthLevel();
		return fourthLevel;
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

	// ====================Actions================
	/**
	 * 管理员权限者通过点击fourth.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() { // <!-- ● -->

		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		ThirdLevel parent = thirdLevelService.queryEntityById(lid);

		ReturnMessage4Common r = new ReturnMessage4Common();

		if ("".equals(fourthLevel.getDescription()) || "".equals(fourthLevel.getName())) {
			r.setMessage("关键数据为null，新建层级失败");
			r.setResult(false);
		} else if (null == parent) {
			r.setResult(false);
			r.setMessage("在session域中未发现当前操作者的lid或当前操作者不存在于ThirdLevel目录中，创建失败");
		} else {
			FourthLevel l = new FourthLevel();

			// 准备用于拼装二维码内容的StringBuffer
			StringBuffer sb = new StringBuffer();
			// 随机生成新建层级的主键id
			String id = UUID.randomUUID().toString();
			// 添加层级对象的id
			l.setFoid(id);
			// 拼装形如： tag=fourth&lid=xjoduf7293jf2wjf9jd9suf9uw
			// 的字符串用作新建层级的带参数二维码的内容
			sb.append("tag=");
			sb.append("fourth");
			sb.append("&");
			sb.append("lid=");
			sb.append(id);
			/*
			 * 通过QRCodeUtils.createLevelQR(code) 创建唯一标志当前新建层级的二维码 所返回的形如：
			 * "qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"相对路径。
			 */
			String codePath = QRCodeUtils.createLevelQR(sb.toString());
			if ("".equals(codePath)) {
				r.setResult(false);
				r.setMessage("通过微信服务器创建新建层级的带参数二维码时出现异常，创建失败");
				ActionContext.getContext().getValueStack().push(r);
				return "json";
			} else {
				// 数据库要保存层级对象二维码的图片位置
				l.setQrcode(codePath);
				// 保存当前时刻的时间戳，用来记录临时带参数二维码的有效期
				l.setQrcodeTime(System.currentTimeMillis());
			}

			l.setDescription(fourthLevel.getDescription());
			l.setName(fourthLevel.getName());
			l.setParent(parent);

			fourthLevelService.save(l);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	// /**
	// *
	// 通过点击fourth.jsp页面上每个fourthLevel条目后的新建按钮，实现创建指定fourth层级对象的子对象，也就是XXXXLevel的新建操作
	// * @return
	// */
	// public String createSonLevel(){
	//
	// ReturnMessage4Common r = new ReturnMessage4Common();
	//
	// if (getSonDescription().equals("") || "".equals(getSonName()) ||
	// this.getParentId().equals("")) {
	// r.setMessage("关键数据为NULL，创建失败");
	// r.setResult(false);
	// } else {
	// // 首先需要分析出“被新建次层级”的层级对象是哪个
	// ThirdLevel parentLevel =
	// thirdLevelService.queryEntityById(this.getParentId());
	//
	// FourthLevel sonLevel = new FourthLevel();
	// sonLevel.setDescription(getSonDescription());
	// sonLevel.setName(getSonName());
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("level_");
	// sb.append(FourthLevel.LEVEL_FOUR);
	// sb.append("$");
	// sb.append("id_");
	// String sid = UUID.randomUUID().toString();
	// sb.append(sid);
	//
	// sonLevel.setFoid(sid);
	//
	// String qrcode = QRCodeUtils.createLevelQR(sb.toString());
	// sonLevel.setQrcode(qrcode);
	//
	// sonLevel.setParent(parentLevel);
	//
	// fourthLevelService.save(sonLevel);
	//
	// r.setMessage("新建成功");
	// r.setResult(true);
	// }
	//
	// ActionContext.getContext().getValueStack().push(r);
	// return "json";
	// }

	public String getLevelList() { // <!-- ● -->

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
				doingMan = userService.queryByUsername(principal);
			}
		}

		List<FourthLevel> list = null;
		ServletContext context = ServletActionContext.getServletContext();
		StringBuffer sb = null;
		File file = null;
		// 分辨当前操作者是Admin还是非Admin
		if (isAdmin) {
			// 当前查访者是Admin,获取数据库中的所有ThirdLevel对象
			list = fourthLevelService.queryEntities();
			for (FourthLevel fl : list) {
				file = new File(context.getRealPath(File.separator + fl.getQrcode()));
				if (!file.exists()) {
					// 如果不存在二维码文件，则重新创建二维码文件
					File parentFile = file.getParentFile();
					// 判断二维码图片的路径是否存在，不存在就逐层创建
					if (!parentFile.exists()) {
						parentFile.mkdirs();
					}
					sb = new StringBuffer();
					sb.append("tag=");
					sb.append("fourth");
					sb.append("&");
					sb.append("lid=");
					sb.append(fl.getFoid());
					QRCodeUtils.createQRcode(context.getRealPath(File.separator + fl.getQrcode()), sb.toString());
				}
			}
		} else {
			list = new ArrayList<FourthLevel>();
			// 当前查访者是非Admin管理者，进一步分析当前操作者执行者的层级位置，然后从children属性结构中获取当前操作者下属的层级对象
			String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
			String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");

			switch (tag) {
			// 对于非Admin用户来说，能够获取到Fourth层级对象信息的只可能是街道/社区层级/第一层级/第二层级/第三层级的管理者
			case "minus_first":
				// 当前操作者是街道层级对象，要获取它下属的所有第一层级对象
				MinusFirstLevel minusFirstLevel = (MinusFirstLevel) minusFirstLevelService.queryEntityById(lid);
				// 然后获取到该街道层级下属的所有社区层级
				Set<ZeroLevel> children = minusFirstLevel.getChildren();
				// 从下属的社区层级中遍历出来的第一层级对象，就是当前操作者（街道层级）所管辖的全部第一层级对象
				for (ZeroLevel l : children) {
					Set<FirstLevel> children2 = l.getChildren();
					for (FirstLevel l2 : children2) {
						for (SecondLevel l3 : l2.getChildren()) {
							for (ThirdLevel l4 : l3.getChildren()) {
								for (FourthLevel l5 : l4.getChildren()) {
									file = new File(context.getRealPath(File.separator + l5.getQrcode()));
									if (!file.exists()) {
										// 如果不存在二维码文件，则重新创建二维码文件
										File parentFile = file.getParentFile();
										// 判断二维码图片的路径是否存在，不存在就逐层创建
										if (!parentFile.exists()) {
											parentFile.mkdirs();
										}
										sb  =  new StringBuffer();
										sb.append("tag=");
										sb.append("fourth");
										sb.append("&");
										sb.append("lid=");
										sb.append(l5.getFoid());
										QRCodeUtils.createQRcode(context.getRealPath(File.separator + l5.getQrcode()), sb.toString());
									}
									list.add(l5);
								}
							}
						}
					}
				}
				break;
			case "zero":
				// 当前操作者是社区测功机对象，要获取它下属的所有第一层级对象
				ZeroLevel level2 = zeroLevelService.queryEntityById(lid);
				Set<FirstLevel> children2 = level2.getChildren();
				for (FirstLevel l : children2) {
					for (SecondLevel l2 : l.getChildren()) {
						for (ThirdLevel l3 : l2.getChildren()) {
							for (FourthLevel l4 : l3.getChildren()) {
								file = new File(context.getRealPath(File.separator + l4.getQrcode()));
								if (!file.exists()) {
									// 如果不存在二维码文件，则重新创建二维码文件
									File parentFile = file.getParentFile();
									// 判断二维码图片的路径是否存在，不存在就逐层创建
									if (!parentFile.exists()) {
										parentFile.mkdirs();
									}
									sb  =  new StringBuffer();
									sb.append("tag=");
									sb.append("fourth");
									sb.append("&");
									sb.append("lid=");
									sb.append(l4.getFoid());
									QRCodeUtils.createQRcode(context.getRealPath(File.separator + l4.getQrcode()), sb.toString());
								}
								list.add(l4);
							}
						}
					}
				}
				break;
			case "first":
				FirstLevel level3 = firstLevelService.queryEntityById(lid);
				for (SecondLevel l : level3.getChildren()) {
					for (ThirdLevel l2 : l.getChildren()) {
						for (FourthLevel l3 : l2.getChildren()) {
							file = new File(context.getRealPath(File.separator + l3.getQrcode()));
							if (!file.exists()) {
								// 如果不存在二维码文件，则重新创建二维码文件
								File parentFile = file.getParentFile();
								// 判断二维码图片的路径是否存在，不存在就逐层创建
								if (!parentFile.exists()) {
									parentFile.mkdirs();
								}
								sb  =  new StringBuffer();
								sb.append("tag=");
								sb.append("fourth");
								sb.append("&");
								sb.append("lid=");
								sb.append(l3.getFoid());
								QRCodeUtils.createQRcode(context.getRealPath(File.separator + l3.getQrcode()), sb.toString());
							}
							list.add(l3);
						}
					}
				}
				break;
			case "second":
				SecondLevel level4 = secondLevelService.queryEntityById(lid);
				for (ThirdLevel l : level4.getChildren()) {
					for (FourthLevel l2 : l.getChildren()) {
						file = new File(context.getRealPath(File.separator + l2.getQrcode()));
						if (!file.exists()) {
							// 如果不存在二维码文件，则重新创建二维码文件
							File parentFile = file.getParentFile();
							// 判断二维码图片的路径是否存在，不存在就逐层创建
							if (!parentFile.exists()) {
								parentFile.mkdirs();
							}
							sb  =  new StringBuffer();
							sb.append("tag=");
							sb.append("fourth");
							sb.append("&");
							sb.append("lid=");
							sb.append(l2.getFoid());
							QRCodeUtils.createQRcode(context.getRealPath(File.separator + l2.getQrcode()), sb.toString());
						}
						list.add(l2);
					}
				}
				break;
			case "third":
				ThirdLevel level5 = thirdLevelService.queryEntityById(lid);
				for (FourthLevel l : level5.getChildren()) {
					file = new File(context.getRealPath(File.separator + l.getQrcode()));
					if (!file.exists()) {
						// 如果不存在二维码文件，则重新创建二维码文件
						File parentFile = file.getParentFile();
						// 判断二维码图片的路径是否存在，不存在就逐层创建
						if (!parentFile.exists()) {
							parentFile.mkdirs();
						}
						sb  =  new StringBuffer();
						sb.append("tag=");
						sb.append("fourth");
						sb.append("&");
						sb.append("lid=");
						sb.append(l.getFoid());
						QRCodeUtils.createQRcode(context.getRealPath(File.separator + l.getQrcode()), sb.toString());
					}
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

		String id = fourthLevel.getFoid();
		FourthLevel l = fourthLevelService.queryEntityById(id);

		ServletContext context = ServletActionContext.getServletContext();
		StringBuffer  sb  =  null;
		File file =  new File(context.getRealPath(File.separator + l.getQrcode()));
		if (!file.exists()) {
			// 如果不存在二维码文件，则重新创建二维码文件
			File parentFile = file.getParentFile();
			// 判断二维码图片的路径是否存在，不存在就逐层创建
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			sb  =  new StringBuffer();
			sb.append("tag=");
			sb.append("fourth");
			sb.append("&");
			sb.append("lid=");
			sb.append(l.getFoid());
			QRCodeUtils.createQRcode(context.getRealPath(File.separator+l.getQrcode()), sb.toString());
		}
		
		List<FourthLevel> list = new ArrayList<FourthLevel>();
		list.add(l);

		ActionContext.getContext().put("levels", list);
		return "list";
	}

}
