package cc.natapp4.ddaig.test.service;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.service_interface.ManagerService;
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

public class TestMemberService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");

	private UserService userService = (UserService) context.getBean("testUserService");
	private ManagerService  managerService  =  (ManagerService) context.getBean("managerService");
	private MemberService  memberService  =  (MemberService) context.getBean("memberService");
	private GroupingService  groupingService =  (GroupingService) context.getBean("groupingService");
	
	private  HibernateTemplate  hibernateTemplate  =  (HibernateTemplate) context.getBean("hibernateTemplate");
	
	private	MinusFirstLevelService minusFirstLevelService = (MinusFirstLevelService) context.getBean("minusFirstLevelService");
	private	ZeroLevelService zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	private	FirstLevelService firstLevelService = (FirstLevelService) context.getBean("firstLevelService");
	private	SecondLevelService secondLevelService = (SecondLevelService) context.getBean("secondLevelService");
	private	ThirdLevelService thirdLevelService = (ThirdLevelService) context.getBean("thirdLevelService");
	private	FourthLevelService fourthLevelService = (FourthLevelService) context.getBean("fourthLevelService");
	
	@Test
	public  void  testCreate01(){
		User user = userService.queryEntityById("423e4529-209c-4c20-82a6-4042ac300a65");
		
		List<FourthLevel> fourths = fourthLevelService.queryEntities();
		FourthLevel  fourthLevel = fourths.get(0);
		
		Member  member  =  new  Member();
		member.setFourthLevel(fourthLevel);
		member.setThirdLevel(fourthLevel.getParent());
		member.setSecondLevel(fourthLevel.getParent().getParent());
		member.setFirstLevel(fourthLevel.getParent().getParent().getParent());
		member.setZeroLevel(fourthLevel.getParent().getParent().getParent().getParent());
		member.setMinusFirstLevel(fourthLevel.getParent().getParent().getParent().getParent().getParent());
		
		Grouping common = groupingService.queryByTagName("common");
		member.setGrouping(common);
		
		member.setUser(user);
		user.getMembers().add(member);
		
//		hibernateTemplate.merge(member);
		memberService.save(member);
	}
	
	/**
	 * 由于从User中会级联查找出MinusFirst对象A，且zeroLevel也会级联查找出其父层级MinusFirst对象B
	 * 如果两个对象是同一个（数据库中主键相同A.id === B.id），且新建数据对象member.minusFirstLevel中会用到B，
	 * 则在向数据库save该member的时候，由于A和B同时存在于Hibernate的二级缓存中，则Hibernate不知道应该向数据库
	 * 中级联save（因为在hbm文件中我们设置了Member和MinusFirst的cascade=“save-update”）哪个对象中的数据
	 * 因此会爆出 a different object with the same identifier value was already associated with the session 异常
	 * 解决办法就是尽量避免在session中同时查询到多个相同数据对象，因此我将Member.hbm.xml中从minusFirstLevel到fourthLevel
	 * 的多对一映射中的lazy属性懒加载设置为了proxy，这样就不会在获取到user的member的时候自动级联获取层级对象了。
	 */
	@Test
	public  void  testCreate02(){
		/*
		 * user →	member →	Levels（MinusFirstLevel、zeroLevel、firstLevel、secondLevel、thirdLevel、fourthLevel）
		 */
		User user = userService.queryEntityById("423e4529-209c-4c20-82a6-4042ac300a65");
		System.out.println(user.getUid());
		/*
		 * zeroLevel → parent(MinusFilrstLevel)
		 */
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("9b035966-2c38-471c-a4cd-bb0af0b7f27b");
		
		Member  member  =  new  Member();
		member.setZeroLevel(zeroLevel);
		member.setMinusFirstLevel(zeroLevel.getParent());
		
		Grouping common = groupingService.queryByTagName("common");
		member.setGrouping(common);
		
		member.setUser(user);
		user.getMembers().add(member);
		
		memberService.save(member);
	}
	
	
	
}
