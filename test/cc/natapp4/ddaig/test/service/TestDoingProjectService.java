package cc.natapp4.ddaig.test.service;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.service_interface.DoingProjectService;

public class TestDoingProjectService {

	private static  ApplicationContext context  =  new  ClassPathXmlApplicationContext("spring/applicationContext.xml");
	
	@Test  
	public void  testQueryAndUpdate(){
		DoingProjectService service = (DoingProjectService) context.getBean("doingProjectService");
		DoingProject  dp  =  service.queryEntityById("402881fa61d000380161d0004e140000");
		dp.setLaborCost(15555555);
		// 已经证实，如果不显式地调用update()方法保存修改，则二级缓存机制不会生效，所有对持久化状态对象的数据修改都会被回滚
		service.update(dp);
	}
	

	
}
