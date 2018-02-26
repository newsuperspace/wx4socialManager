package cc.natapp4.ddaig.test.service;

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
			"spring/applicationContext.xml");

	@Test  // pass
	public void testSave() {
		ThirdLevelService service = (ThirdLevelService) context.getBean("thirdLevelService");
		ThirdLevel   th  =  new  ThirdLevel();
		th.setDescription("这是一个测试用ThirdLevel层级");
		th.setLevel(LevelInterface.LEVEL_THREE);
		th.setName("SecondLevel000");
		
		service.save(th);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		FirstLevelService  flService  =  (FirstLevelService) context.getBean("firstLevelService");
		SecondLevelService  scService  =  (SecondLevelService) context.getBean("secondLevelService");
		ThirdLevelService  thService = (ThirdLevelService) context.getBean("thirdLevelService");
		
		FirstLevel fl = flService.queryEntityById("402881fa61d079800161d0799dce0000");
		SecondLevel sc = scService.queryEntityById("402881fa61d08d970161d08dc8160000");
		ThirdLevel th = thService.queryEntityById("402881fa61d0b6c70161d0b6e6950000");
		
		th.setParent(sc);
		sc.setParent(fl);
		thService.update(th);
	}

	@Test  // test
	public void testDelete(){
		// 略
	}
	
}
