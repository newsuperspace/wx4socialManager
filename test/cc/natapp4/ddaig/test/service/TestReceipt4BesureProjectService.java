package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.Receipt4BesureProject;
import cc.natapp4.ddaig.service_interface.BesureProjectService;
import cc.natapp4.ddaig.service_interface.Receipt4BesureProjectService;

public class TestReceipt4BesureProjectService {

	private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext.xml");
	
	@Test
	public  void  testSave(){
		Receipt4BesureProjectService  service  =(Receipt4BesureProjectService) context.getBean("receipt4BesureProjectService");
		BesureProjectService   service2  =  (BesureProjectService) context.getBean("besureProjectService");

		BesureProject project = service2.queryEntityById("4028810161dafacd0161dafb6ef30000");
		for(int i=0;i<4;i++){
			Receipt4BesureProject rp = new  Receipt4BesureProject();
			rp.setBesureProject(project);
			rp.setContent("chuangyibucuo");
			rp.setName(""+i);
			rp.setTime(System.currentTimeMillis());
			rp.setType("repeat");
			service.save(rp);
		}

//		project  =  service2.queryEntityById("4028810161dad2f90161dad330800000");
//		for(int i=0;i<4;i++){
//			Receipt4BesureProject rp = new  Receipt4BesureProject();
//			rp.setBesureProject(project);
//			rp.setContent("chuangyibucuo");
//			rp.setName(""+i);
//			rp.setTime(System.currentTimeMillis());
//			rp.setType("repeat");
//			service.save(rp);
//		}
		
	}
	
	@Test
	public  void  testQueryAndUpdate(){
	
	}
	
	
	
	
	
}
