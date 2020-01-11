package cc.natapp4.ddaig.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
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

public class TestManagerService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private UserService userService = (UserService) context.getBean("testUserService");
	private ManagerService managerService = (ManagerService) context.getBean("managerService");
	private MemberService memberService = (MemberService) context.getBean("memberService");

	private GroupingService groupingService = (GroupingService) context.getBean("groupingService");

	private HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");

	private MinusFirstLevelService minusFirstLevelService = (MinusFirstLevelService) context
			.getBean("minusFirstLevelService");
	private ZeroLevelService zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	private FirstLevelService firstLevelService = (FirstLevelService) context.getBean("firstLevelService");
	private SecondLevelService secondLevelService = (SecondLevelService) context.getBean("secondLevelService");
	private ThirdLevelService thirdLevelService = (ThirdLevelService) context.getBean("thirdLevelService");
	private FourthLevelService fourthLevelService = (FourthLevelService) context.getBean("fourthLevelService");

	
	
	/**
	 * ♥♥♥♥
	 * 这是一部非常有建设性意见的测试，它帮助我对 
	 * org.springframework.dao.DuplicateKeyException: 
	 * A different object with the same identifier value was already associated with the session : 
	 * [cc.natapp4.ddaig.domain.cengji.FirstLevel#25a81d2f-9090-4711-bd46-681b1df2c5d0]; 
	 * nested exception is org.hibernate.NonUniqueObjectException:
	 *  A different object with the same identifier value was already associated with the session :
	 *   [cc.natapp4.ddaig.domain.cengji.FirstLevel#25a81d2f-9090-4711-bd46-681b1df2c5d0]
	 *   异常有了更深的理解，当我在使用（已经注解掉了）
	 *   	FirstLevel firstLevel = null;
			for (FirstLevel fl : zeroLevel.getChildren()) {
				firstLevel = fl;
			}
	 *   提前获取一个等待任命的firstLevel持久化状态对象的时候，殊不知在抓取member的时候也会自动获取到同一个id下
	 *   的另一个持久化状态对象firstLevel，而在新建manager的时候我们又会通过
	 *   manager.setFirstLevel()   和  manager.setMember()  时同时摄入到这两个相同主键id的firstLevel持久化状态对象
	 *   这才是爆出异常的根源所在。
	 *   
	 */

}
