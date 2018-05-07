package cc.natapp4.ddaig.action;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.PermissionType;
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
import cc.natapp4.ddaig.service_interface.PermissionLevelService;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.ConfigUtils;

@Controller("permissionAction")
@Lazy(true)
@Scope("prototype")
public class PermissionAction extends ActionSupport implements ModelDriven<Permission> {

	// ====================DI注入=====================
	// 权限相关
	@Resource(name = "permissionService")
	private PermissionService permissionService;
	@Resource(name = "permissionTypeService")
	private PermissionTypeService permissionTypeService;
	@Resource(name = "permissionLevelService")
	private PermissionLevelService permissionLevelService;
	// 层级对象相关
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
	// ====================模型驱动====================
	private Permission permission = new Permission();

	@Override
	public Permission getModel() {
		return this.permission;
	}

	// ====================属性驱动====================
	// 新建权限时，承接从前端提交过来的权限状态（对应数据库中的available字段）
	private String state;
	private int level;
	private String lid;
	private String selected; // 包含有所有被选中的权限ID的字符串，彼此之间以逗号分隔

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// 新建权限的时候，从前端确认权限所属层级的plid
	private String plid;

	public String getPlid() {
		return plid;
	}

	public void setPlid(String plid) {
		this.plid = plid;
	}

	// 新建权限的时候，从前端确认权限所属类型ptid
	private String ptid;

	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	// ====================Action方法===================
	/**
	 * 新建权限
	 * 
	 * @return
	 */
	public String create() {

		ReturnMessage4Common r = new ReturnMessage4Common();

		if (StringUtils.isEmpty(this.getPtid()) || StringUtils.isEmpty(permission.getDescription())
				|| StringUtils.isEmpty(permission.getPermissionName())) {
			r.setMessage("关键信息为NULL，创建失败");
			r.setResult(false);
		} else {

			PermissionType permissionType = permissionTypeService.queryEntityById(this.getPtid());
			Permission p = new Permission();
			p.setPermissionType(permissionType);
			p.setDescription(permission.getDescription());
			p.setPermissionName(permission.getPermissionName());
			p.setEnabled(true); // 新建的权限状态默认为true
			permissionService.save(p);
			r.setMessage("权限" + p.getPermissionName() + "创建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * AJAX 【完成】 更新设置权限
	 * 
	 * @return
	 */
	public String update() {

		return "json";
	}

	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	public String getList() {
		// 获取权限层级的数据信息，放入到前端的newModal中的select中，用于在新建权限的时候选择之用
		List<PermissionLevel> permissionLevels = this.permissionLevelService.queryEntities();
		// 这才是真正要在前端显示的权限数据
		List<Permission> permissions = this.permissionService.queryEntities();
		// 注意这里将两个数据放入到了Map栈中，注意两个的名字
		ActionContext.getContext().put("permissionLevels", permissionLevels);
		ActionContext.getContext().put("permissions", permissions);
		return "list";
	}

	/**
	 * 在前端通过Modal创建Permission的时候，有两个下拉列表框 第一个列出数据库中所有可用的PermissionLevel
	 * 第二个根据第一个选择列出所有可用的PermissionType 这两个下拉列表数据都是通过Ajax与后端交互的
	 * 
	 * @return
	 */
	public String getPermissionTypeList() {

		PermissionLevel permissionLevel = permissionLevelService.queryEntityById(this.getPlid());
		Set<PermissionType> types = permissionLevel.getPermissionTypes();

		ActionContext.getContext().getValueStack().push(types);
		return "json";
	}

	/**
	 * 根据permisionConfig配置文件自动批量创建基础权限
	 * 
	 * @return
	 */
	public String batchCreatePermission() {

		ReturnMessage4Common r = new ReturnMessage4Common();
		// 先从后台permissionConfig中获取有关三层级权限配置的JSON信息
		String json = "";
		try {
			json = ConfigUtils.getPermissionConfig();
		} catch (IOException e) {
			e.printStackTrace();
			r.setMessage("权限配置信息读取异常，批量创建失败！");
		}
		// 再删除Permission、PermissionType和PermissionLevel的数据库内容
		List<Permission> permissions = permissionService.queryEntities();
		if (permissions.size() > 0) {
			for (Permission p : permissions) {
				permissionService.delete(p);
			}
		}
		List<PermissionType> permissionTypes = permissionTypeService.queryEntities();
		if (permissionTypes.size() > 0) {
			for (PermissionType p : permissionTypes) {
				permissionTypeService.delete(p);
			}
		}
		List<PermissionLevel> permissionLevels = permissionLevelService.queryEntities();
		if (permissionLevels.size() > 0) {
			for (PermissionLevel p : permissionLevels) {
				permissionLevelService.delete(p);
			}
		}
		// 压缩JSON格式字符串，将所有转义字符、空格等去除掉
		JsonPrimitive jsonPrimitive = new JsonPrimitive(json.replace("\t", "").replace(" ", ""));
		Type t = new TypeToken<List<PermissionLevel>>() {
		}.getType();
		json = jsonPrimitive.getAsString();
		Gson g = new Gson();
		ArrayList<PermissionLevel> list = g.fromJson(json, t);
		// 批量向数据库中存入PermissionLevel的数据，而其他PermissionType和Permission也级联地操作完成。
		for (PermissionLevel p : list) {
			permissionLevelService.save(p);
		}

		r.setMessage("创建成功！");
		r.setResult(true);
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * AJAX 用于更改/保存指定层级对象的权限集
	 */
	public String permissionSaving() {

		ReturnMessage4Common message = new ReturnMessage4Common();

		switch (this.getLevel()) {
		case -1:
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(this.getLid());
			if (minusFirstLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				minusFirstLevel.setPermissions(permissions);
			}
			minusFirstLevelService.update(minusFirstLevel);
			message.setMessage("关于街道层级对象“" + minusFirstLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		case 0:
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(this.getLid());
			if (zeroLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				zeroLevel.setPermissions(permissions);
			}
			zeroLevelService.update(zeroLevel);
			message.setMessage("关于社区层级对象“" + zeroLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		case 1:
			FirstLevel firstLevel = firstLevelService.queryEntityById(this.getLid());
			if (firstLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				firstLevel.setPermissions(permissions);
			}
			firstLevelService.update(firstLevel);
			message.setMessage("第一层级对象“" + firstLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		case 2:
			SecondLevel secondLevel = secondLevelService.queryEntityById(this.getLid());
			if (secondLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				secondLevel.setPermissions(permissions);
			}
			secondLevelService.update(secondLevel);
			message.setMessage("第二层级对象“" + secondLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		case 3:
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(this.getLid());
			if (thirdLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				thirdLevel.setPermissions(permissions);
			}
			thirdLevelService.update(thirdLevel);
			message.setMessage("第三层级对象“" + thirdLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		case 4:
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(this.getLid());
			if (fourthLevel != null && !this.getSelected().isEmpty()) {
				String[] pids = this.getSelected().split(",");
				Permission p = null;
				Set<Permission> permissions = new HashSet<Permission>();
				for (String pid : pids) {
					p = permissionService.queryEntityById(pid);
					permissions.add(p);
				}
				fourthLevel.setPermissions(permissions);
			}
			fourthLevelService.update(fourthLevel);
			message.setMessage("第四层级对象“" + fourthLevel.getName() + "”的权限设置成功。");
			message.setResult(true);
			break;
		default:
			message.setMessage("计划操作的层级对象是" + this.getLevel() + "该层级并不存在于系统中");
			message.setResult(false);
			break;
		}
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}

	/**
	 * AJAX 根据从前端提交过来的层级对象的level（-1、0、1、2、3、4）获知所要设置权限的层级对象属于哪一层级
	 * 然后根据lid得知所要设置权限的层级对象的ID 那么就能将必要的权限数据连同该层级对象当前的权限设置状态（True/false）一同返回给前端了
	 * 前端只需要便利返回的JSON，然后然找层级关系在页面上动态生成checkBox等元素就行了。
	 */
	public String permissionSetting() {
		Set<PermissionType> permissionTypes = null;
		PermissionLevel permissionLevel = null;
		Set<Permission> openedPermissions = null;

		switch (this.getLevel()) {
		case -1:
			// 获取需要设置权限的minusFirstLevel层级对象
			MinusFirstLevel minusFirstLevel = minusFirstLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = minusFirstLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，放置JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		case 0:
			// 获取需要设置权限的minusFirstLevel层级对象
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = zeroLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，防止JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		case 1:
			// 获取需要设置权限的minusFirstLevel层级对象
			FirstLevel firstLevel = firstLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = firstLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，防止JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		case 2:
			// 获取需要设置权限的minusFirstLevel层级对象
			SecondLevel secondLevel = secondLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = secondLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，防止JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		case 3:
			// 获取需要设置权限的minusFirstLevel层级对象
			ThirdLevel thirdLevel = thirdLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = thirdLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，防止JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		case 4:
			// 获取需要设置权限的minusFirstLevel层级对象
			FourthLevel fourthLevel = fourthLevelService.queryEntityById(this.getLid());
			// 获取该层级对象拥有的“权限”
			openedPermissions = fourthLevel.getPermissions();
			// 获取该层级对象可以设置的全部“权限类型”
			permissionLevel = permissionLevelService.queryEntityByLevel(this.getLevel());
			permissionTypes = permissionLevel.getPermissionTypes();
			// 遍历权限类型，与已拥有权限进行比对，将已经拥有的权限的isOpen标记设置成true；
			for (PermissionType pt : permissionTypes) {
				ArrayList<Permission> list = new ArrayList<Permission>();
				Set<Permission> permissions = pt.getPermissions();
				for (Permission p : permissions) {
					for (Permission opened : openedPermissions) {
						if (p.getPid().equals(opened.getPid())) {
							// 如果两个权限对象相同，则表示当前被进行设置的层级对象已经拥有了该权限，则设置标记isOpen为true
							p.setOpen(true);
							break;
						}
					}
					// 切断Permission与PermissionType的联系，防止JSON解析的时候出现死循环
					p.setPermissionType(null);
					list.add(p);
				}
				pt.setPermissions4Ajax(list);
			}
			break;
		default:
			
			break;
		}
		ActionContext.getContext().getValueStack().push(permissionTypes);
		return "json";
	}

}
