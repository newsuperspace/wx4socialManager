package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestFirstLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private ZeroLevelService zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	private	FirstLevelService firstLevelService = (FirstLevelService) context.getBean("firstLevelService");

	
	@Test  // pass
	public void testSave() {
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("64976f64-c13e-4d78-8c0b-62b858e53a5a");
		FirstLevel   fl  =  new  FirstLevel();
		fl.setFlid(UUID.randomUUID().toString());
		fl.setDescription("这是一个测试用FirstLevel层级");
		fl.setName("FirstLevel1-1-1");
		fl.setParent(zeroLevel);
		
		firstLevelService.save(fl);
	}
	
//	@Test  // pass
//	public void testQueryAndUpdate(){
//		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
//		FirstLevel fl = service.queryEntityById("402881e961eb71540161eb71af290000");
//		Manager manager = fl.getManager();
//		System.out.println(manager.getUser().getUsername());
//	}

	@Test  // test
	public void testDelete(){
		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel fl = service.queryEntityById("402881fa61d079800161d0799dce0000");
		service.delete(fl);
	}
	
}
