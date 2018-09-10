package cc.natapp4.ddaig.test.service;

import java.util.List;

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
	 * 【通过】
	 */
	@Test
	public void testQueryAndUpdate(){
		GeographicService  service  =  (GeographicService) context.getBean("geographicService");
		List<Geographic> list = service.queryEntities();
		Geographic geo  = null;
		for(Geographic g:list){
			geo = g;
			System.out.println("当前查找出的坐标对象的name："+g.getName());
		}
		geo.setDescription("22222");
		service.update(geo);
	}
	
	/**
	 * 
	 */
	@Test
	public void testDelete(){
		GeographicService  service  =  (GeographicService) context.getBean("geographicService");
		Geographic geographic = service.queryEntityById("402881e465c187730165c18796100000");
		service.delete(geographic);
	}
	
	
}
