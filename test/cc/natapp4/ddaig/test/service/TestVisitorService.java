package cc.natapp4.ddaig.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.VisitorService;

public class TestVisitorService {

	private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext.xml");

	@Test
	public void testSave() {

		VisitorService	 visitorService = (VisitorService) context.getBean("visitorService");
		UserService  userService = (UserService) context.getBean("userService");
		ActivityService  activityService  =  (ActivityService) context.getBean("activityService");
		
		User user = userService.queryEntityById("e11caec6-a5e0-48a8-92fb-535a6d498c8c");
		Activity activity = activityService.queryEntityById("b2b9da28-e9ac-4262-8deb-8c9d5e451645");
		
		Visitor  visitor  =  new  Visitor();
		visitor.setStartTime(System.currentTimeMillis());
		visitor.setEndTime(System.currentTimeMillis()+1000*60*60*2);
		visitor.setScore(100);
		visitor.setWorkTime(visitor.getEndTime()-visitor.getStartTime());

//		if(user.getVisits().isEmpty()){
//			List<Visitor>  lis  =  new  ArrayList<Visitor>();
//			user.setVisits(lis);
//		}
//		if(activity.getVisitors().isEmpty()){
//			List<Visitor>  list  =  new  ArrayList<Visitor>();
//			activity.setVisitors(list);
//		}
//		
//		user.getVisits().add(visitor);
//		activity.getVisitors().add(visitor);

		visitor.setUser(user);
		visitor.setActivity(activity);
		
		visitorService.save(visitor);
	}

	@Test
	public void testQueryAndUpdate() {
		VisitorService	 visitorService = (VisitorService) context.getBean("visitorService");
		Visitor visitor = visitorService.queryEntityById(1);
		System.out.println(visitor.getScore());
	}

	@Test
	public void testDelete() {

	}
}
