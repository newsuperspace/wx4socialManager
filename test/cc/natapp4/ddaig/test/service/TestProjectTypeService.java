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
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.PermissionTypeService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;

public class TestProjectTypeService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test  // pass
	public void testSave(){
		ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");
		ProjectType  t  =  new  ProjectType();
		t.setDescription("这是一个测试用项目类型");
		t.setName("ProjectType001");
		service.save(t);
		
	}
	
	@Test   // pass
	public void  testQueryAndUpdate(){
		ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");
		ProjectType t = service.queryEntityById("402881fa61d04d0f0161d04d3da80000");
		t.setDescription(t.getDescription()+"111");
		service.update(t);
	}
	
	@Test  // pass
	public void testDelete(){
		ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");
		ProjectType t = service.queryEntityById("402881fa61d04d0f0161d04d3da80000");
		service.delete(t);
	}
	

	
}
