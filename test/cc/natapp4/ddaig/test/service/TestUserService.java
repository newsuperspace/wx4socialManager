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
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCheckRealName;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.UserService;

public class TestUserService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	
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
		FirstLevel firstLevel = firstLevelService.queryEntityById("402881e961eb71540161eb71af290000");
		fls.add(firstLevel);
		manager.setFls(fls);
		/*
		 *  ★ 由于Manager与User的基于主键的一对一关联，因此Manager的主键产生策略是Foreign
		 *  也就是基于Manager的持久化类中的某个引用其多一对一对应的主表（User表）持久化状态对象的主键值
		 *  因此为了能让Hibernate在创建Manager数据的时候可以获取到所一对一关联的User数据的主键，因此必须
		 *  给Manager.user赋予非null的值，这样新建的Manager数据的主键就是Manager.user.uid一样了。
		 */
		manager.setUser(user);  
		user.setManager(manager);
		
		Member  member  =  new  Member();
		/*
		 *  ★ 由于Member与User的基于主键的一对一关联，因此Member的主键产生策略是Foreign
		 *  也就是基于Member的持久化类中的某个引用其多一对一对应的主表（User表）持久化状态对象的主键值
		 *  因此为了能让Hibernate在创建Member数据的时候可以获取到所一对一关联的User数据的主键，因此必须
		 *  给Member.user赋予非null的值，这样新建的Member数据的主键就是Member.user.uid一样了。
		 */
		member.setUser(user); 
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
		UserService service = (UserService) context.getBean("userService");
		User user = service.queryEntityById("e11caec6-a5e0-48a8-92fb-535a6d498c8c");
		String groupName = user.getGrouping().getGroupName();
		switch (groupName) {
		case "minus_first":
			Set<MinusFirstLevel> mfls = user.getManager().getMfls();
			System.out.println("当前用户是街道管理者，T管理的层级对象是：");
			for(MinusFirstLevel mfl: mfls){
				System.out.println(mfl.getName());
			}
			break;
		case "zero":
			break;
		case "first":
			Set<FirstLevel> fls = user.getManager().getFls();
			System.out.println("当前用户是First层级管理者，T管理的层级对象是：");
			for(FirstLevel fl: fls){
				System.out.println(fl.getName());
			}
			break;
		case "second":
			break;
		case "third":
			break;
		case "fourth":
			break;
		}
		
	}
	
	@Test
	public void testUpdate(){

	}
	
	@Test
	public void testDelete(){

	}
	
	@Test
	public void testCheckRealNae() throws Throwable{
		UserService userService = (UserService) context.getBean("testUserService");
		userService.checkRealName("okNKU0Vb9EQtWTfteAyS3nVMd0Iw", "jixiang", "2", "1988-08-21", "65856442");
	}
	
	
	
}
