package cc.natapp4.ddaig.test.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.UserService;

public class TestUserService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	@Test
	public void testAdd(){
		UserService userService = (UserService) context.getBean("userService");
		
		User  user  =  new  User();
		
		user.setAddress("呼家楼向军北里13楼607");
		user.setAge(25);
		user.setCardid("110105198808211118");
		user.setEmail("isthereanybody@foxmail.com");
		
		Grouping g = new  Grouping();
		g.setDescription("测试用Grouping");
		g.setGroupName("first");
		g.setLogoPath("xxxx/xxx/1/2/xxx.gif");
		g.setTag("first");
		g.setTagid(101);
		user.setGrouping(g);
		
		user.setIshere(true);
		user.setLocked(false);
		
		Manager manager  =  new  Manager();
		Set<FirstLevel>   fls =  new  HashSet<FirstLevel>();
		FirstLevelService firstLevelService = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel firstLevel = firstLevelService.queryEntityById("402881fa61d079800161d0799dce0000");
		fls.add(firstLevel);
		manager.setFls(fls);
		manager.setUser(user);   // ★
		user.setManager(manager);
		
		Member  member  =  new  Member();
		member.setZeroLevel(firstLevel.getParent());
		member.setMinusFirstLevel(firstLevel.getParent().getParent()); 
		member.setUser(user); // ★
		user.setMember(member);
		
		user.setOpenid("okowe7923u2hj42nndosf79f23j1jjfdfs9fs");
		user.setPhone("13718805500");
		String  uid  =  UUID.randomUUID().toString();
		user.setUid(uid);
		user.setQrcode("xxxx/1/5/xxx.png");
		user.setRegistrationTime(System.currentTimeMillis());
		user.setScore(0);
		user.setServeTime(0);
		user.setSex("男");
		user.setSickname("isthereanybody");
		user.setUsername("积累");
		
		userService.save(user);
	}
	
	@Test
	public void testQuery(){
		
	}
	
	@Test
	public void testUpdate(){

	}
	
	@Test
	public void testDelete(){

	}
	
	
	
}
