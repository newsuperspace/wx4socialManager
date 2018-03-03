package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.BesureProjectService;

public class TestActivityService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test
	public void testSave(){
		
		ActivityService  service  =  (ActivityService) context.getBean("activityService");
		Activity  a =  new  Activity();
		a.setAid(UUID.randomUUID().toString());
		a.setName("测试活动A");
		a.setDescription("这是一个用来参与Junit测试的活动数据");
		service.save(a);
	}
	
	
	
	
}
