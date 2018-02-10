package cc.natapp4.ddaig.test.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.UserService;

public class TestActivityService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	@Test
	public void testQueryByTagName(){
		
		UserService service = (UserService) context.getBean("testUserService");
		List<User> list = service.queryByTagName("community_user");
		for(User u: list){
			System.out.println(u.getUsername());
		}
	}
	
	
}
