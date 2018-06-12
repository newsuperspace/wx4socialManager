package cc.natapp4.ddaig.test.service;



import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.utils.ConfigUtils;

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
	

	/**
	 * 用于在项目的构建阶段，根据projectTypes.properties中的内容批量向数据ProjectType创建数据。
	 * 类似于grouping的作用
	 */
	@Test   // pass
	public void batchCreateProjectType(){
		
		Properties p = ConfigUtils.getProperties("wxConfig/projectTypes.properties");
		Enumeration<String> keys = (Enumeration<String>)p.propertyNames();
		
		ProjectType pt  =  null;
		
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			pt =  new  ProjectType();
			pt.setDescription(p.getProperty(key));
			pt.setName(key);
			ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");
			service.save(pt);
		}
		
	}
	
	
}
