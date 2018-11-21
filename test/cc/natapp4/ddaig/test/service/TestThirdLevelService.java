package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestThirdLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private SecondLevelService secondLevelService = (SecondLevelService) context.getBean("secondLevelService");
	private	ThirdLevelService thirdLevelService = (ThirdLevelService) context.getBean("thirdLevelService");
	
	
	@Test  // pass
	public void testSave() {
		ThirdLevel   th  =  new  ThirdLevel();
		th.setThid(UUID.randomUUID().toString());
		th.setDescription("这是一个测试用ThirdLevel层级");
		th.setName("SecondLevel000");
		
		SecondLevel secondLevel = secondLevelService.queryEntityById("9f2d1d92-4ea4-400d-89b0-a444c1584836");
		th.setParent(secondLevel);
		
		thirdLevelService.save(th);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		
	}

	@Test  // test
	public void testDelete(){
		// 略
	}
	
}
