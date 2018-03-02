package cc.natapp4.ddaig.test.service;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.service_interface.DoingProjectService;

public class TestDoingProjectService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	
	@Test
	public void testQuery(){
		DoingProjectService  service = (DoingProjectService) context.getBean("doingProjectService");
		DoingProject  dp  =  service.queryEntityById("4028810161e096af0161e096d20c0000");
		BesureProject bp = dp.getBesureProject();
		System.out.println(bp.getDescription());
	}

	
}
