package cc.natapp4.ddaig.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Ware;
import cc.natapp4.ddaig.service_interface.ExchangeService;
import cc.natapp4.ddaig.service_interface.PermissionService;
import cc.natapp4.ddaig.service_interface.RoleService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.WareService;

/**
 * Junit测试“Service-Dao层”的健康度，不需要通过Maven的Compile编译过程，直接Run As指定测试方法即可。★★★★★
 * 
 * 
 * 由于当前项目整个工程都是通过Maven构成起来的，因此在正式发布工程到服务器的时候，需要先通过Maven的complie指令编译后，再部署到服务器上。
 * 而Test测试是运行在本地的，而不是正式需要部署到服务器上的，因此在运行前并不需要Maven进行编译。
 * 同时，Test测试涉及Hibernate，而Hibernate容器已经置于Spring容器的管理之下，因此需要通过Spring的ClassPathXmlApplicationContext()方法
 * 来手动加载Spring配置——applicationContext.xml，然后通过返回的Spring容器来获取指定Service（之所以必须通过Spring获取Bean而不能手动
 * new，是由于Service-Dao这两层是通过IOC和DI注入连接起来的，因此必须通过Spring容器来索取才能正常使用）。
 * 
 * 至此，Test中进行的测试实际上是一种线下行动，不需要通过Service，因此不需要预先通过Maven的compile后再执行（Maven的Compile是
 * 要将编译后的字节码上传到Tomcat服务器上运行的。）
 * @author Administrator
 *
 */
public class TestRole111PermissionService {

	/**
	 * 正常部署到Tomcat服务器上的工程中获取Spring容器的方式是通过Spring框架提供的一个servlet监听器，它会监听Servlet的启动，从而获取classpath指定路径下的Spring配置file（也就是applicationContext.xml）
	 * ，然后将Spring容器实例放入到ServletContext域中备用，之后再通过Spring框架提供的一个Struts2的插件来替换Struts2原生的ObjectFactory（对象工厂），该对象工厂会分析请求的url，并比对
	 * Struts2的各个配置，从中找到当前请求的Action的Bean名称，然后将该Bean名称交给Spring的对象工厂，就能实例化（IOC/DI）出Action-service-Dao层所涉及的所有Bean了，从而可以相应请求了。
	 * 
	 * 但是JUnit测试工程是线下测试，不涉及服务器，自然也就不能获取到ServletContext域，因此此时如果需要使用Spring-Hibernate（Struts层是不能通过Junit测试的，它与Servlet紧耦合），就需要另一种
	 * 实例化Spring容器的方式，也就是下面代码这样。
	 * 通过这种本地方式实例化的Spring容器与Servlet监听器实例化的Spring容器一样，因此也可以从中获取到Service-Dao层的Bean，从而实现对Service层业务类的连锁测试。
	 */
	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	private static PermissionService  ps = (PermissionService)context.getBean("permissionService");
	private static RoleService  rs  = (RoleService)context.getBean("roleService");
	
	@Test
	public  void  testCreatePermission(){

		Permission  p  = new  Permission();
		p.setAvailable(true);
		p.setDescription("活动创建");
		p.setPermission(" activity:create");

		ps.save(p);
	}
	
	@Test
	public void testCreateRole(){
		
		Role r =  new  Role();
		r.setAvailable(true);
		r.setDescription("管理员");
		r.setRole("admin");
		
		rs.save(r);
	}
	
	@Test
	public void testCorrelationPermissions(){
		List<Role> roles = rs.queryEntities();
		Role role = roles.get(0);
		String roleID =  role.getRid();
		
		Set<Permission>  perms =  new  HashSet<Permission>(ps.queryEntities());
		
		rs.correlationPermissions(roleID, perms);
	}
	
	@Test
	public void testUncorrelationPermissions(){
		List<Role> roles = rs.queryEntities();
		Role role = roles.get(0);
		
		String roleID =  role.getRid();
		Set<Permission> perms = role.getPermissions();
		
		rs.uncorrelationPermissions(roleID, perms);
	}
	
	@Test
	public void  testDeletePermission(){
		List<Permission> roles = ps.queryEntities();
		ps.deletePermission(roles.get(0).getPid());
	}
	
	@Test
	public void testDeleteRole(){
		List<Role> roles = rs.queryEntities();
		String roleID = roles.get(0).getRid();
		
		rs.deleteRole(roleID);
	}
	
	
	
}
