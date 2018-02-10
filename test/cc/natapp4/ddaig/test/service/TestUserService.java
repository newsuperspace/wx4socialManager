package cc.natapp4.ddaig.test.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.service_interface.ActivityService;

public class TestUserService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	@Test
	public void testAdd(){
		ActivityService  service = (ActivityService) context.getBean("activityService");
		// 这里调用的是ActivityService子类自己的个性化save()方法
		service.save(new Activity());
	}
	
	@Test
	public void testQuery(){
		ActivityService  service = (ActivityService) context.getBean("activityService");
		 List<Activity> list = service.queryEntities();
		for(Activity a: list){
			System.out.println(a.getDescription());
			System.out.println("=======================");
		}
		
		Activity a = service.queryEntityById("8a8ad1a45e74028c015e740295f50000");
		System.out.println(a.getName());
	}
	
	@Test
	public void testUpdate(){
		ActivityService  service = (ActivityService) context.getBean("activityService");
		Activity a = service.queryEntityById("8a8ad1a45e74028c015e740295f50000");
		a.setName("大逃杀2");
		service.update(a);
	}
	
	@Test
	public void testDelete(){
		ActivityService  service = (ActivityService) context.getBean("activityService");
		service.deleteById("8a8ad1a45e74028c015e740295f50000");
	}
	
	
	
}
