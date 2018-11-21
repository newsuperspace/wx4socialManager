package cc.natapp4.ddaig.test.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.domain.Receipt4BesureProject;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.BesureProjectService;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.service_interface.UserService;



public class TestBesureProjectService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private BesureProjectService  service  =  (BesureProjectService) context.getBean("besureProjectService");
	private ProjectTypeService  projectTypeService  =  (ProjectTypeService) context.getBean("projectTypeService");
	
	@Test   // pass
	public void testSave() {
		// -------------------First
		BesureProject  p=  new BesureProject();
		p.setName("测试Bp");
		p.setState("新立项");
		p.setActivityTotal(10);
		p.setCommitTime(System.currentTimeMillis());
		p.setDescription("222222222222222222");
		p.setLaborCost(10000);
		
		List<ProjectType> projectTypes = projectTypeService.queryEntities();
		for(ProjectType pt: projectTypes){
			if("为老服务".equals(pt.getDescription())){
				p.setProjectType(pt);
			}
		}
		
		List<Receipt4BesureProject>  list =  new  ArrayList<Receipt4BesureProject>();
		for(int i=0;i<4;i++){
			Receipt4BesureProject rp = new  Receipt4BesureProject();
			rp.setContent("chuangyibucuo");
			rp.setName(""+i);
			rp.setTime(System.currentTimeMillis());
			rp.setType("repeat");
			rp.setBesureProject(p);
			list.add(rp);
		}
		p.setReceipts(list);
		service.save(p);
	}
	
	@Test  // pass
	public void  testQueryAndUpdate(){
		// ---------------------------------------Zero
		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
		BesureProject bp = service.queryEntityById("4028810161e094fa0161e09525ee0000");
		
		DoingProject  dp  =  new  DoingProject();
		dp.setLaborCost(bp.getLaborCost());
		dp.setBesureProject(bp);   // ★
		
		bp.setDoingProject(dp);
		
		service.update(bp);
		// ---------------------------------------First
//		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
//		BesureProject bp = service.queryEntityById("4028810161df6db10161df6dc60d0000");
//		DoingProject  dp  =  new  DoingProject();
//		dp.setBesureProject(bp);
//		dp.setLaborCost(bp.getLaborCost());
//		bp.setDoingProject(dp);
//		// 已经证实，如果不显式地调用update()方法保存修改，则二级缓存机制不会生效，所有对持久化状态对象的数据修改都会被回滚
//		service.update(bp);
		// --------------------------------------Second
		/*
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
//		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
//		BesureProject bp = service.queryEntityById("4028810161df63ee0161df6416ed0000");
//		List<Receipt4BesureProject> list = bp.getReceipts();
//		System.out.println("List is  length:"+list.size());
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i).getName());
//		}
		// --------------------------------------third
		
		
	}
	
	@Test   // pass
	public void testDelete(){
		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
		BesureProject bp = service.queryEntityById("402881fa61cfec660161cfec92a70000");
		service.delete(bp);
	}
	
}
