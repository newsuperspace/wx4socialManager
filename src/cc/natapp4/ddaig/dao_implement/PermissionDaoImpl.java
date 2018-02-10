package cc.natapp4.ddaig.dao_implement;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.PermissionDao;
import cc.natapp4.ddaig.domain.Permission;

@Repository("permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements PermissionDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	protected final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	// ========================个性逻辑的实现========================
	@Override
	public Permission createPermission(Permission permission) {

		if(null == permission || StringUtils.isEmpty(permission.getPermission())){
			logger.error("新建Permission时参数是null或必要字段缺失，创建失败");
			return null;
		}
		List<Permission> list = this.queryEntities();
		for(Permission p: list){
			if(p.getPermission().equals(permission.getPermission())){
				logger.error("新建的权限名已存在");
				return null;
			}
		}
		
		this.save(permission);
		logger.info("新建-权限-"+permission.getPermission()+"成功");
		return null;
	}

	@Override
	public void deletePermission(String permissionID) {
		Permission permission = this.queryEntityById(permissionID);
		if(null == permission){
			// 找不到该权限
			logger.warn("删除权限操作失败-找不到该权限");
			return;
		}
		this.delete(permission);
		logger.info("删除-权限-"+permission.getPermission()+"成功");
	}

}
