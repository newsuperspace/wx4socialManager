package cc.natapp4.ddaig.test.service;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.BesureProjectService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

public class TestDoingProjectService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private BesureProjectService  bservice  =  (BesureProjectService) context.getBean("besureProjectService");
	private DoingProjectService  dservice = (DoingProjectService) context.getBean("doingProjectService");
	
	private MinusFirstLevelService  minusFirstLevelService = (MinusFirstLevelService) context.getBean("minusFirstLevelService");
	private ZeroLevelService  zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	private FirstLevelService  firstLevelService = (FirstLevelService) context.getBean("firstLevelService");
	private SecondLevelService  secondLevelService = (SecondLevelService) context.getBean("secondLevelService");
	private ThirdLevelService  thirdLevelService = (ThirdLevelService) context.getBean("thirdLevelService");
	private FourthLevelService  fourthLevelService = (FourthLevelService) context.getBean("fourthLevelService");
	
	@Test
	public void testSave(){
		BesureProject  bp  =  bservice.queryEntityById("402881e466d33a030166d33a34b00000");
		DoingProject  dp  =  new DoingProject();
		ThirdLevel third = thirdLevelService.queryEntityById("ad50e453-d037-4d5f-9737-7de5ffd823fc");
		
		dp.setBesureProject(bp);
		dp.setMinusFirstLevel(third.getParent().getParent().getParent().getParent());
		dp.setZeroLevel(third.getParent().getParent().getParent());
		dp.setFirstLevel(third.getParent().getParent());
		dp.setSecondLevel(third.getParent());
		dp.setThirdLevel(third);
		
		dp.setLaborCost(10298301);
		dp.setLastLaborCost(1231231);
		
		dservice.save(dp);
	}

	
}
