package cc.natapp4.ddaig.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.PermissionType;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.PermissionLevelService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;

@Controller("permissionTypeAction")
@Lazy(true)
@Scope("prototype")
public class PermissionTypeAction extends ActionSupport implements ModelDriven<PermissionType> {

	// ====================DI注入=====================
	@Resource(name = "permissionTypeService")
	private PermissionTypeService permissionTypeService;
	@Resource(name = "permissionLevelService")
	private PermissionLevelService permissionLevelService;
	// ====================模型驱动====================
	private PermissionType permissionType = new PermissionType();

	@Override
	public PermissionType getModel() {
		return this.permissionType;
	}

	// ====================属性驱动====================
	// 新建权限时，承接从前端提交过来的权限状态（对应数据库中的available字段）
	private String state;

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

	// ====================Action方法===================
	/**
	 * 新建权限类型
	 * @return
	 */
	public String create(){
		
		ReturnMessage4Common  r  =  new  ReturnMessage4Common();
		
		if(StringUtils.isEmpty(this.getPlid()) || StringUtils.isEmpty(permissionType.getDescription()) || StringUtils.isEmpty(permissionType.getPermissionTypeName())){
			r.setMessage("关键信息为NULL，创建失败");
			r.setResult(false);
		}else{
			
			PermissionLevel permissionLevel = permissionLevelService.queryEntityById(this.getPlid());
			PermissionType p  =  new  PermissionType();
			p.setPermissionLevel(permissionLevel);
			p.setDescription(permissionType.getDescription());
			p.setPermissionTypeName(permissionType.getPermissionTypeName());
			p.setEnabled(true);  // 新建的权限状态默认为true
			permissionTypeService.save(p);
			r.setMessage("权限类型"+p.getPermissionTypeName()+"创建成功");
			r.setResult(true);
		}
		
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	/**【完成】
	 * 更新权限类型
	 * @return
	 */
	public String  update(){
		
		return "json";
	}
	
	
	/**
	 * 获取权限类型列表
	 * @return
	 */
	public String getList(){
		// 获取权限类型层级的数据信息，放入到前端的newModal中的select中，用于在新建权限类型的时候选择之用
		List<PermissionLevel> permissionLevels = this.permissionLevelService.queryEntities();
		// 这才是真正要在前端显示的权限类型数据
		List<PermissionType> permissionTypes = permissionTypeService.queryEntities();
		// 注意这里将两个数据放入到了Map栈中，注意两个的名字
		ActionContext.getContext().put("permissionLevels", permissionLevels);
		ActionContext.getContext().put("permissionTypes", permissionTypes);
		return "list";
	}
	
	
}
