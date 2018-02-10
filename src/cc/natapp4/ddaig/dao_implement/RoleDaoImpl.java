package cc.natapp4.ddaig.dao_implement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.RoleDao;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate  hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
	
	// ===========================个性逻辑的实现==============================
	
	@Override
	public Role createRole(Role role) {
		// hibernateTemplate.save()方法返回的是Serializable接口，该结果的值就是你插入到数据库的新记录的主键值
		String roleID = (String)hibernateTemplate.save(role);
		return this.queryEntityById(roleID);
	}

	@Override
	public void deleteRole(String roleID) {
		Role role = this.queryEntityById(roleID);
		HashSet<Permission>  permissions  =   new HashSet<Permission>(); 
		// 由于Foreigin Key 因此这里必须先接触要角色与权限的关联后，再删除
		role.setPermissions(permissions);
		this.update(role);  // 保存一下，正式解除关联
		this.delete(role);  // 删除角色
	}

	@Override
	public void correlationPermissions(String roleID, Set<Permission> perms) {
		if(null == perms || 0 == perms.size())
			return;
		
		Role role = this.queryEntityById(roleID);
		if(null == role)
			return;   // 如果根据roleID查不到角色对象，则直接返回
		Set<Permission> permissions = role.getPermissions();
		
		// ------------------正常添加逻辑开始----------------
		boolean  b;  //标志—— true表示perm已存在，false表示perm不存在于Role中可以添加
		// 使用迭代器遍历
		Iterator<Permission> iterator = perms.iterator();
		while(iterator.hasNext()){
			Permission p = iterator.next();
			b = false;   // 重置标志
			
			// 如果角色中的权限列表不存在或者容器元素个数是空，则直接将包含权限的SET容器设置给它
			if(null==permissions || 0==permissions.size()){
				role.setPermissions(perms);
				this.update(role);
				break;
			}
			
			// Set集合也是容器，处理可以使用迭代器，还可以使用增强for遍历
			for(Permission pp: permissions){
				if(pp.getPermission().equals(p.getPermission())){
					b = true;   // 权限已存在
					break;
				}
			}
			// 判断当前迭代的Permission对象是否已经存在于角色中了
			if(!b){
				// 权限不存在，则将当前遍历的权限添加到角色的权限容器中，形成关联
				permissions.add(p);
			}
		}
		// 遍历结束，可以保存Role了
		this.update(role);
	}

	@Override
	public void uncorrelationPermissions(String roleID, Set<Permission> perms) {
		// 如果提交进来待删除的权限容器是null或空，则直接跳出
		if(null==perms || 0 == perms.size())
			return;
		// 根据roleID，从数据库Role表获取角色对象
		Role role =  this.queryEntityById(roleID);
		if(null == role)
			return;
		Set<Permission> permissions = role.getPermissions();
		// 如果角色对象的权限容器是null或0，则也直接返回
		if(null == permissions || 0 == permissions.size())
			return;
		
		// ------------------正常删除逻辑开始----------------------
		for(Permission p: perms){
			for(Permission p2: permissions){
				if(p2.getPermission().equals(p.getPermission())){
					// 如果在permissions中发现了与待删除同名的permission，则将其删除
					permissions.remove(p2);
				}
			}
		}
		// 删除结束，保存role
		this.update(role);
	}
}
