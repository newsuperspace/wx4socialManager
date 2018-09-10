package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.service_interface.GeographicService;

public class TestGeographicService {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");

	/**
	 * 【通过】
	 */
	@Test
	public void testSave(){
		GeographicService  service  =  (GeographicService) context.getBean("geographicService");
		Geographic  g =  new Geographic();
		g.setDescription("这是一个测试坐标");
		g.setLatitude(33.33333);
		g.setLongitude(33.33333);
		g.setLevel(1);
		g.setName("测试坐标1");
		service.save(g);
	}

	/**
	 * 
	 */
	@Test
	public void testUpdate(){
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testDelete(){
		
	}
	
	
}
