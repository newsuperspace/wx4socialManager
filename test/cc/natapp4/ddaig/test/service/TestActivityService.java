package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.service_interface.BesureProjectService;

public class TestActivityService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test
	public void testSave() {

		BesureProject  p=  new BesureProject();
		p.setActivityTotal(10);
		p.setCommitTime(System.currentTimeMillis());
		p.setDescription("这是一个非常非常非常非常非常NB的项目，能够改变人类的命运。");

		BesureProjectService  service  =  (BesureProjectService) context.getBean("besureProjectService");
		service.save(p);
		
	}
	

	
	
	
}
