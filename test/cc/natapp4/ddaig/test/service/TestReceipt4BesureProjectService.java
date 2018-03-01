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
	
	
	/**
	 * ★★★★★
	 * Receipt4BesureProject  是作为 其主表——BesureProject的List容器属性的元素存在的
	 * 因此为了能够让Hibernate自动维护排序属性（在主表BesureProject.hbm.xml中在<list-index>或<index>标签中规定的用来表明从表中数据排序
	 * 的特殊字段，该字段是由Hibernate自行维护，无需在从表Receipt4BesureProject的持久化类中出现对应的属性和配套的SET/GET方法）
	 * 而如果你需要让Hibernate自动维护，那么就必须让Hibernate知道当前List容器中的已有元素数量，这样Hibernate才能按顺序设置后续值，这就要求
	 * 在存储Receipt4BesureProject数据的时候，必须从BesureProject主表来级联操作，也就是先通过BesureProject.getReceipts()来从从表
	 * Receipt4BesureProject查找出所有数据项目到List容器，这样就能知道ORDER属性应该顺延写多少了，然后再把新建的Receipt数据放入到List容器中
	 * 并且通过BesureProjectService来update或saveBesureProject数据，这样Receipt4BesureProject的新建数据也就级联地被保存到数据库了。
	 * 
	 * 因此，我们不能也不需要通过Receipt4BesureProjectService来新建数据，一切关于新建、修改、查询都可以通过主表BesureProjectService来实现。
	 */
	@Test
	public  void  testSave(){
		Receipt4BesureProjectService  service  =(Receipt4BesureProjectService) context.getBean("receipt4BesureProjectService");
		BesureProjectService   service2  =  (BesureProjectService) context.getBean("besureProjectService");

		BesureProject project = service2.queryEntityById("402881e961dca78e0161dca7c43a0000");
		for(int i=0;i<4;i++){
			Receipt4BesureProject rp = new  Receipt4BesureProject();
			rp.setBesureProject(project);
			rp.setContent("chuangyibucuo");
			rp.setName(""+(i+1));
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
