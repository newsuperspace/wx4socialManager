package cc.natapp4.ddaig.test.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.BesureProjectService;
import cc.natapp4.ddaig.service_interface.UserService;

public class TestBesureProjectService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	@Test   // pass
	public void testSave() {

		BesureProject  p=  new BesureProject();
		p.setActivityTotal(10);
		p.setCommitTime(System.currentTimeMillis());
		p.setDescription("这是一个非常非常非常非常非常NB的项目，能够改变人类的命运。");
		p.setLaborCost(10000);
		
		DoingProject  dp  =  new  DoingProject();
		dp.setBesureProject(p);
		p.setDoingProject(dp);
		dp.setLaborCost(p.getLaborCost());
		
		BesureProjectService  service  =  (BesureProjectService) context.getBean("besureProjectService");
		service.save(p);
	}
	
	@Test  // pass
	public void  testQueryAndUpdate(){
		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
		BesureProject bp = service.queryEntityById("402881fa61cfec660161cfec92a70000");
		bp.getDoingProject().setLaborCost(bp.getDoingProject().getLaborCost()*2);
		bp.setLaborCost(bp.getDoingProject().getLaborCost());
		// 已经证实，如果不显式地调用update()方法保存修改，则二级缓存机制不会生效，所有对持久化状态对象的数据修改都会被回滚
		service.update(bp);
	}
	
	@Test   // pass
	public void testDelete(){
		BesureProjectService service = (BesureProjectService) context.getBean("besureProjectService");
		BesureProject bp = service.queryEntityById("402881fa61cfec660161cfec92a70000");
		service.delete(bp);
	}
	
}
