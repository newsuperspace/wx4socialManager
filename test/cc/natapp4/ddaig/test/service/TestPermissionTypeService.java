package cc.natapp4.ddaig.test.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.PermissionType;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;

public class TestPermissionTypeService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test  // pass
	public void testSave(){
		PermissionTypeService  service  = (PermissionTypeService) context.getBean("permissionTypeService");
		PermissionType t  = new PermissionType();
		t.setDescription("这是一个测试用的权限类型");
		t.setEnabled(true);
		t.setPermissionType("PermissionType001");
		service.save(t);
	}
	
	@Test   // pass
	public void  testQueryAndUpdate(){
		PermissionTypeService  service  = (PermissionTypeService) context.getBean("permissionTypeService");
		PermissionType t = service.queryEntityById("402881fa61d02ad20161d02aedb80000");
		t.setEnabled(false);
		service.update(t);
	}
	
	@Test  // pass
	public void testDelete(){
		PermissionTypeService  service  = (PermissionTypeService) context.getBean("permissionTypeService");
		PermissionType  t  =  service.queryEntityById("402881fa61d03fee0161d04005c40000");
		service.delete(t);
	}
	

	
}
