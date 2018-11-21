package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestFourthLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private	ThirdLevelService thirdLevelService = (ThirdLevelService) context.getBean("thirdLevelService");
	private	FourthLevelService fourthLevelService = (FourthLevelService) context.getBean("fourthLevelService");
	
	
	@Test  // pass
	public void testSave() {
		FourthLevel   fl  =  new  FourthLevel();
		fl.setFoid(UUID.randomUUID().toString());
		fl.setDescription("这是一个测试用FourthLevel层级");
		fl.setName("FourthLevel000");
		
		ThirdLevel thirdLevel = thirdLevelService.queryEntityById("ad50e453-d037-4d5f-9737-7de5ffd823fc");
		fl.setParent(thirdLevel);
		
		fourthLevelService.save(fl);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		ThirdLevelService  thService = (ThirdLevelService) context.getBean("thirdLevelService");
		FourthLevelService  flService  =  (FourthLevelService) context.getBean("fourthLevelService");
		
		ThirdLevel th = thService.queryEntityById("402881fa61d0b6c70161d0b6e6950000");
		FourthLevel  fl  =  flService.queryEntityById("402881fa61d0c6a00161d0c6bcc10000");
		fl.setParent(th);
		
		flService.update(fl);
	}

	@Test  // test
	public void testDelete(){
		// 略
	}
	
}
