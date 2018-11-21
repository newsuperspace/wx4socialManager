package cc.natapp4.ddaig.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

public class TestGeographicService {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private static final ZeroLevelService zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	private GeographicService  service  =  (GeographicService) context.getBean("geographicService");
	/**
	/**
	 * 【通过】
	 */
	@Test
	public void testSave(){
		
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("64976f64-c13e-4d78-8c0b-62b858e53a5a");
		
		Geographic  g =  new Geographic();
		g.setName("测试坐标1");
		g.setDescription("这是一个测试坐标");
		g.setLatitude(33.33333);
		g.setLongitude(33.33333);
		g.setLevel(0);
		g.setZeroLevel(zeroLevel);
		
		List<Geographic> geographics = zeroLevel.getGeographics();
		if(null==geographics){
			geographics  =  new  ArrayList<Geographic>();
			zeroLevel.setGeographics(geographics);
		}
		geographics.add(g);
		
		service.save(g);
	}

	/**
	 * 【通过】
	 */
	@Test
	public void testQueryAndUpdate(){
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
		Geographic geographic = service.queryEntityById("402881e46705985d0167059892d70000");
		service.delete(geographic);
	}
	
	
}
