package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.service_interface.FirstLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestFirstLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext.xml");

	@Test  // pass
	public void testSave() {
		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel   fl  =  new  FirstLevel();
		fl.setDescription("这是一个测试用FirstLevel层级");
		fl.setLevel(LevelInterface.LEVEL_ONE);
		fl.setName("FirstLevel000");
		
		service.save(fl);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel fl = service.queryEntityById("402881fa61d079800161d0799dce0000");
		fl.setDescription(fl.getDescription()+"111");
		service.update(fl);
	}

	@Test  // test
	public void testDelete(){
		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel fl = service.queryEntityById("402881fa61d079800161d0799dce0000");
		service.delete(fl);
	}
	
}
