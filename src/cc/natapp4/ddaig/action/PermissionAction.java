package cc.natapp4.ddaig.action;


import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.PermissionType;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.PermissionLevelService;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;

@Controller("permissionAction")
@Lazy(true)
@Scope("prototype")
public class PermissionAction extends ActionSupport  implements ModelDriven<Permission> {

	// ====================DI注入=====================
	@Resource(name="permissionService")
	private PermissionService  permissionService;
	@Resource(name="permissionTypeService")
	private PermissionTypeService  permissionTypeService;
	@Resource(name="permissionLevelService")
	private PermissionLevelService permissionLevelService;
	// ====================模型驱动====================
	private Permission  permission  =  new Permission();
	@Override
	public Permission getModel() {
		return this.permission;
	}

	// ====================属性驱动====================
	// 新建权限时，承接从前端提交过来的权限状态（对应数据库中的available字段）
	private String  state;
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
	 * @return
	 */
	public String create(){
		
		ReturnMessage4Common  r  =  new  ReturnMessage4Common();
		
		if(StringUtils.isEmpty(this.getPtid()) || StringUtils.isEmpty(permission.getDescription()) || StringUtils.isEmpty(permission.getPermissionName())){
			r.setMessage("关键信息为NULL，创建失败");
			r.setResult(false);
		}else{
			
			PermissionType permissionType = permissionTypeService.queryEntityById(this.getPtid());
			Permission p  =  new  Permission();
			p.setPermissionType(permissionType);
			p.setDescription(permission.getDescription());
			p.setPermissionName(permission.getPermissionName());
			p.setEnabled(true);  // 新建的权限状态默认为true
			permissionService.save(p);
			r.setMessage("权限"+p.getPermissionName()+"创建成功");
			r.setResult(true);
		}
		
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	/**【完成】
	 * 更新权限
	 * @return
	 */
	public String  update(){
		
		return "json";
	}
	
	
	/**
	 * 获取权限列表
	 * @return
	 */
	public String getList(){
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
	 * 在前端通过Modal创建Permission的时候，有两个下拉列表框
	 * 第一个列出数据库中所有可用的PermissionLevel
	 * 第二个根据第一个选择列出所有可用的PermissionType
	 * 这两个下拉列表数据都是通过Ajax与后端交互的
	 * @return
	 */
	public String getPermissionTypeList(){
		
		PermissionLevel permissionLevel = permissionLevelService.queryEntityById(this.getPlid());
		Set<PermissionType> types = permissionLevel.getPermissionTypes();

		ActionContext.getContext().getValueStack().push(types);
		return "json";
	}

}
