package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestZeroLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext.xml");

	@Test  // pass
	public void testSave() {
		ZeroLevelService service = (ZeroLevelService) context.getBean("zeroLevelService");
		ZeroLevel   z  =  new  ZeroLevel();
		z.setDescription("这是一个测试用ZeroLevel层级");
		z.setLevel(LevelInterface.LEVEL_ZERO);
		z.setName("ZeroLevel000");
		
		MinusFirstLevelService  mService  =  (MinusFirstLevelService) context.getBean("minusFirstLevelService");
		MinusFirstLevel m = mService.queryEntityById("402881fa61d0d1f90161d0d20d210000");
		z.setParent(m);
		
		service.save(z);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		ZeroLevelService service = (ZeroLevelService) context.getBean("zeroLevelService");
		ZeroLevel z = service.queryEntityById("402881fa61d0dcf70161d0dd28770000");
		System.out.println("----------"+z.getName());
	}

	@Test  // test
	public void testDelete(){
		// 略
	}
	
}
