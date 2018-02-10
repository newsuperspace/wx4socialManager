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
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.RoleService;

@Controller("permissionAction")
@Lazy(true)
@Scope("prototype")
public class PermissionAction extends ActionSupport  implements ModelDriven<Permission> {

	// ====================DI注入=====================
	@Resource(name="permissionService")
	private PermissionService  permissionService;
	@Resource(name="roleService")
	private RoleService roleService;
	
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

	// ====================Action方法===================
	/**【完成】
	 * 获取 拥有指定权限的 全部角色
	 * @param pid
	 * @return
	 */
	public String  getRolesByPid(){
		
		Permission p = permissionService.queryEntityById(permission.getPid());
		Set<Role> roles = p.getRoles();
		
		ActionContext.getContext().getValueStack().push(roles);
		return "roles";
	}
	
	/**【完成】
	 * 新建权限
	 * @return
	 */
	public String createPermission(){
		
		Permission  p  =  new Permission();
		ReturnMessage4Common  result =  new ReturnMessage4Common();
		
		if("able".equals(getState())){
			p.setAvailable(true);
		}else{
			p.setAvailable(false);
		}
		
		if(StringUtils.isEmpty(permission.getPermission()) || StringUtils.isEmpty(permission.getDescription())){
			result.setMessage("权限名或描述为空，创建失败");
			result.setResult(false);
		}else{
			p.setPermission(permission.getPermission());
			p.setDescription(permission.getDescription());
			permissionService.save(p);
			result.setMessage("权限创建成功！");
			result.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**【完成】
	 * 获取所有权限到对象栈栈顶，并跳转前端页面到/jsp/permissionList.jsp 显示结果
	 * @return
	 */
	public String showPermissions(){
		List<Permission> list = permissionService.queryEntities();
		
		ActionContext.getContext().getValueStack().push(list);
		return "permissions";
	}
	
	/**【完成】
	 * 通过Ajax从前端传递过来的权限的pid，来查询出封装该权限全部其他信息的对象
	 * @return
	 */
	public String getPermissionByPid(){
		String pid  = permission.getPid();
		Permission permission2 = permissionService.queryEntityById(pid);
		
		ActionContext.getContext().getValueStack().push(permission2);
		return "json";
	}
	
	/**【完成】
	 * 更新权限
	 * @return
	 */
	public String  updatePermission(){

		ReturnMessage4Common  result  =  new  ReturnMessage4Common();
		Permission  p  =  null;
	
		if(StringUtils.isEmpty(permission.getPid())){
			result.setMessage("PID为空，更新失败");
			result.setResult(false);
		}else{
			p =  permissionService.queryEntityById(permission.getPid());
			
			if(null == p){
				result.setMessage("权限不存在，更新失败");
				result.setResult(false);
			}else{
				if(StringUtils.isEmpty(permission.getPermission()) || StringUtils.isEmpty(permission.getDescription())){
					result.setMessage("权限名或描述为空，更新失败");
					result.setResult(false);
				}else{
					if("able".equals(getState())){
						p.setAvailable(true);
					}else{
						p.setAvailable(false);
					}
					
					p.setPermission(permission.getPermission());
					p.setDescription(permission.getDescription());

					permissionService.update(p);
					
					result.setMessage("权限创建成功！");
					result.setResult(true);
				}
			}
		}
		
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	
	
}
