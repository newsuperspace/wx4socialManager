package cc.natapp4.ddaig.test.service;

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
		a.setActivityBeginTime(System.currentTimeMillis()+1000*60*60*10);
		a.setActivityEndTime(System.currentTimeMillis() + 1000*60*60*24);
		a.setBaoMingBeginTime(System.currentTimeMillis()+1000*60*60*4);
		a.setBaoMingEndTime(System.currentTimeMillis()+1000*60*60*5);
		a.setBaoMingUplimit(20);
		a.setDescription("这是一个短暂额测试活动");
		a.setName("这是一个测试活动");
		a.setScore(1000);
		a.setState("筹备中");
		a.setType("室内活动");
		service.save(a);
	}
	
	
	
	
}
