package cc.natapp4.ddaig.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

public class TestHouseService {
	
	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private static final HouseService service = (HouseService) context.getBean("houseService");
	private static final ZeroLevelService zeroLevelService = (ZeroLevelService) context.getBean("zeroLevelService");
	/**
	 * 【通过】
	 */
	@Test
	public void testSave(){
		House h  =  new  House(); 
		h.setDescription("这是测试用房的测试描述");
		h.setName("这是一间测试用房");
		h.setLatitude(34.393843);
		h.setLongitude(89.892372);
		h.setRadus(100);
		
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("64976f64-c13e-4d78-8c0b-62b858e53a5a");
		h.setZeroLevel(zeroLevel);
		List<House> houses = zeroLevel.getHouses();
		if(null==houses){
			houses = new ArrayList<House>();
			zeroLevel.setHouses(houses);
		}
		houses.add(h);
		
		service.save(h);
	}
	
	/**
	 * 【通过】
	 */
	@Test
	public void testQueryAndUpdate(){
		
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("64976f64-c13e-4d78-8c0b-62b858e53a5a");
		List<House> houses = zeroLevel.getHouses();
		for(int i=0;i<houses.size();i++){
			houses.get(i).setName(houses.get(i).getName()+i);
			houses.get(i).setEnable(false);
		}
		zeroLevelService.update(zeroLevel);
	}
	
	/**
	 * 【通过】
	 */
	@Test
	public void testDelete(){
		service.deleteById("402881e465cb48a10165cb48bf340000");
	}
}
