package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestSecondLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private	FirstLevelService firstLevelService = (FirstLevelService) context.getBean("firstLevelService");
	private SecondLevelService secondLevelService = (SecondLevelService) context.getBean("secondLevelService");
	

	@Test  // pass
	public void testSave() {
		SecondLevel   sc  =  new  SecondLevel();
		sc.setScid(UUID.randomUUID().toString());
		sc.setDescription("这是一个测试用SecondLevel层级");
		sc.setName("SecondLevel1-1-1-1");
		
		FirstLevel firstLevel = firstLevelService.queryEntityById("25a81d2f-9090-4711-bd46-681b1df2c5d0");
		sc.setParent(firstLevel);
		
		secondLevelService.save(sc);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		SecondLevelService service = (SecondLevelService) context.getBean("secondLevelService");
		SecondLevel sc = service.queryEntityById("402881fa61d08d970161d08dc8160000");
		sc.setDescription(sc.getDescription()+"111");
/*
 * 	如果这里使用save而不是update的话就会在数据库新建一条数据，虽然操作的持久化状态对象中存有主键，
 * 但由于在SecondLevel.hbm.xml映射文件中规定了secondleve表主键产生机制是UUID（自动产生机制的一种）
 * 因此Hibernate调用save()方法会重新为持久化状态对象随机生成一个主键并调用SETTER方法覆盖掉持久化状态对象中的旧主键值
 * 然后数据库新建数据。
 */
//		service.save(sc);
		service.update(sc);
	}

	@Test  // test
	public void testDelete(){
		SecondLevelService service = (SecondLevelService) context.getBean("secondLevelService");
		SecondLevel sc = service.queryEntityById("402881fa61d08d970161d08dc8160000");
		service.delete(sc);
	}
	
}
