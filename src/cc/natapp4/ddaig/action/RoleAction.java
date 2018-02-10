package cc.natapp4.ddaig.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("roleAction")
@Lazy(true)
@Scope("prototype")
public class RoleAction extends ActionSupport implements ModelDriven<Role> {

	// ====================DI注入=====================
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "permissionService")
	private PermissionService permissionService;
	@Resource(name = "userService")
	private UserService userService;

	// ====================属性驱动====================
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	// 承接 角色设置页面中 选择角色所拥有的权限的checkbox的请求参数
	private String pids;

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	// ====================模型驱动====================
	private Role role = new Role();

	@Override
	public Role getModel() {
		return this.role;
	}

	// ====================Action方法===================

	/**
	 * 用于assignedRole.jsp页面展示当前所有“社区管理者”，用以将管理者与特定角色绑定 实现权限分配工作
	 * 
	 * @return
	 */
	public String showAdmins() {
		List<User> admins = userService.queryByTagName("community_user");

		String openid = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println("showAdmins——当前索取管理组的用户的openID是：" +openid);
		
		User user = userService.queryByOpenId(openid);
		String roleName = user.getRole().getRole();
		
		if("admin".equals(roleName)){
			// 当前请求者是admin用户，可以看到所有社区管理者（包括自己） 所以这里不做任何事
			System.out.println("当前请求showAdmins()的是admin用户");
		}else{
			// 当前请求者是非admin，则不能让他看到admin用户
			System.out.println("当前请求showAdmins()的是非admin用户");
			for(int i=0; i<admins.size(); i++){
				User u = admins.get(i);
				if(null==u.getRole()){
					continue;
				}
				if(u.getRole().getRole().equals("admin")){
					// 从list中除去admin用户
					admins.remove(u);
					i -= 1;
				}
			}
		}
		
		ActionContext.getContext().put("admins", admins);
		return "admins";
	}

	/**
	 * 用于roleList.jsp页面，展示当前全部角色，从而实现对角色的CRUD操作。
	 * 
	 * @return
	 */
	public String showRoles() {

		List<Role> list = roleService.queryEntities();
		
		String  openid = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.queryByOpenId(openid);
		if(!user.getRole().getRole().equals("admin")){
			// 当前请求者不是admin，因此不能允许他们看到admin角色，从而可能对admin角色进行设置，非admin用户设置admin角色权限这种事儿是不能被允许的
			for(int i=0; i<list.size(); i++){
				if("admin".equals(list.get(i).getRole())){
					list.remove(i);
					break;
				}
			}
		}
		
		ActionContext.getContext().getValueStack().push(list);
		return "roles";
	}

	/**
	 * 根据前端传递过来的uid，获取该用户的信息和全部Role信息 以供前端进行 社区管理员与角色的绑定设置
	 * 
	 * @return
	 */
	public String getUserAndRole() {

		// 这个用户是前端需要修改角色的用户，并不是执行当前操作的用户
		User user = userService.queryEntityById(this.getUid());
		List<Role> roles = roleService.queryEntities();

		// 现在获取的是执行当前操作，也就是请求本函数的用户对象
		User  currentUser =  userService.queryByOpenId((String)SecurityUtils.getSubject().getPrincipal());
		
		if( !currentUser.getRole().getRole().equals("admin")){
			// 如果当前请求者用户不是管理员admin角色，那么就不能让他们能够设置admin角色，也就是不能让他们看到admin这个角色的设置项
			for(int i=0; i<roles.size(); i++){
				if("admin".equals(roles.get(i).getRole())){
					roles.remove(i);
					break;
				}
			}
		}
		
		Result4GetUserAndRole result = new Result4GetUserAndRole();
		result.setUser(user);
		result.setRoles(roles);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	public class Result4GetUserAndRole {
		private User user;
		private List<Role> roles;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<Role> getRoles() {
			return roles;
		}

		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}
	}

	/**
	 * 更新 “社区管理者”与“角色” 的绑定关系
	 * 
	 * @return
	 */
	public String updateAssignedRole() {

		ReturnMessage4Common result = new ReturnMessage4Common();

		if (StringUtils.isEmpty(getUid()) || StringUtils.isEmpty(role.getRid())) {
			result.setMessage("uid或rid为空，绑定失败");
			result.setResult(false);
		} else {
			if ("-1" == role.getRid()) {
				// 选择解除绑定角色
				User user = userService.queryEntityById(getUid());
				user.setRole(null);
				userService.update(user);

				result.setMessage("解除绑定成功！");
				result.setResult(true);
			} else {
				User user = userService.queryEntityById(getUid());
				Role r = roleService.queryEntityById(this.getModel().getRid());

				user.setRole(r);
				userService.update(user);

				result.setMessage("绑定成功！");
				result.setResult(true);
			}
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 供前端页面 roleModal.jsp 的infoModal模态对话框展示某一角色的详细信息
	 * 
	 * @return
	 */
	public String getRoleByRid() {

		String rid = role.getRid();
		Role r = roleService.queryEntityById(rid);

		Result4GetRoleByRid result = new Result4GetRoleByRid();
		result.setRole(r);
		result.setUsers(r.getUsers());

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	public class Result4GetRoleByRid {
		private Role role;
		
		/**
		 * ★★★★★
		 * 由于domain———Role中的users属性的GETTER上添加了@JSON注解，导致如果直接向前端返回Ajax的返回结果（也就是当前类的实例）
		 * 那么json结果集在扫描当前类实例（位于对象栈栈顶）的时候就不会将注解有@JSON的属性也就是users输出到json对象中，这样就导致
		 * 前端通过$.post()的回调函数等待解析json的代码不能获取到users数据，这样也就不能在前端展示与当前Role绑定的全部用户了。
		 * 解决的办法就是在返回结果类中增加额外的users属性，这样即不会干扰@JSON注解（该注解的作用就是防止json结果死循环解析）的工作
		 * 又能将users数据发送给前端了。
		 */
		private Set<User>  users;
		
		public Set<User> getUsers() {
			return users;
		}

		public void setUsers(Set<User> users) {
			this.users = users;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}
	}

	/**
	 * 当前端需要创建Role的时候，需要获取系统中现存的Permission 权限，来填充createModal，以供配置Role之用。
	 * 
	 * @return
	 */
	public String getInfo4CreateRole() {

		List<Permission> list = permissionService.queryEntities();
		
		String openid = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.queryByOpenId(openid);
		if(!user.getRole().getRole().equals("admin")){
			// 说明当前管理员用户不是admin阶层，则不允许其看到关于“权限crud”有关的权限名，这样也就防止使得非admin用户拥有操作“权限设置”的权限了。
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getPermission().contains("permission:")){
					list.remove(i);
					i -= 1;
				}
			}
		}
		
		Result4GetInfo4CreateRole result = new Result4GetInfo4CreateRole();
		result.setPermissions(list);

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	public class Result4GetInfo4CreateRole {

		List<Permission> permissions;

		public List<Permission> getPermissions() {
			return permissions;
		}

		public void setPermissions(List<Permission> permissions) {
			this.permissions = permissions;
		}
	}

	/**
	 * 新建角色
	 * 
	 * @return
	 */
	public String createRole() {

		ReturnMessage4Common result = new ReturnMessage4Common();

		if (StringUtils.isEmpty(role.getRole()) || StringUtils.isEmpty(role.getDescription())) {
			// 数据缺失，不可能新建角色
			result.setMessage("角色名或描述不能是空，角色创建失败");
			result.setResult(false);
		} else {
			Role r = new Role();
			r.setRole(role.getRole());
			r.setDescription(role.getDescription());
			r.setAvailable(true);

			if (!StringUtils.isEmpty(pids)) {
				String[] split = pids.split(",");

				Set<Permission> set = null;
				Permission p = null;

				if (null != split && split.length > 0) {
					set = new HashSet<Permission>();
					for (int i = 0; i < split.length; i++) {
						p = permissionService.queryEntityById(split[i]);
						if (null != p) {
							set.add(p);
						}
					}
				}
				r.setPermissions(set);
			}
			roleService.save(r);

			result.setMessage("角色创建成功！");
			result.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	
	/**
	 * 更新配置角色的时候，需要先通过本方法获取一些数据回显到前端
	 * 的updateModal中去
	 */
	public String getInfo4UpdateRole(){

		Result4GetInfo4UpdateRole  result  = new  Result4GetInfo4UpdateRole();
		
		Role r =  roleService.queryEntityById(role.getRid());
		List<Permission> list = permissionService.queryEntities();
		
		String openid = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.queryByOpenId(openid);
		if(!user.getRole().getRole().equals("admin")){
			// 说明当前管理员用户不是admin阶层，则不允许其看到关于“权限crud”有关的权限名，这样也就防止使得非admin用户拥有操作“权限设置”的权限了。
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getPermission().contains("permission:")){
					list.remove(i);
					i -= 1;
				}
			}
		}

		result.setPermissions(list);
		result.setRole(r);
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	public class Result4GetInfo4UpdateRole{
		
		Role role;
		List<Permission> permissions;
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public List<Permission> getPermissions() {
			return permissions;
		}
		public void setPermissions(List<Permission> permissions) {
			this.permissions = permissions;
		}
	}
	
	/**
	 * 更新role
	 * 
	 * @return
	 */
	public String updateRole() {
		ReturnMessage4Common result = new ReturnMessage4Common();

		if (StringUtils.isEmpty(role.getRole()) || StringUtils.isEmpty(role.getDescription())) {
			// 数据缺失，不可能新建角色
			result.setMessage("角色名或描述不能是空，角色更新失败");
			result.setResult(false);
		} else {
			Role r = roleService.queryEntityById(role.getRid());
			r.setRole(role.getRole());
			r.setDescription(role.getDescription());

			if (!StringUtils.isEmpty(pids)) {
				String[] split = pids.split(",");

				Set<Permission> set = null;
				Permission p = null;

				if (null != split && split.length > 0) {
					set = new HashSet<Permission>();
					for (int i = 0; i < split.length; i++) {
						p = permissionService.queryEntityById(split[i]);
						if (null != p) {
							set.add(p);
						}
					}
				}
				r.setPermissions(set);
			}
			roleService.update(r);

			result.setMessage("角色设置成功！");
			result.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

}
