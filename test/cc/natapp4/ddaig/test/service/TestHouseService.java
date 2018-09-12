package cc.natapp4.ddaig.test.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.service_interface.HouseService;

public class TestHouseService {
	
	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	
	private static final HouseService service = (HouseService) context.getBean("houseService");
	/**
	 * 【通过】
	 */
	@Test
	public void testSave(){
		House h  =  new  House(); 
		h.setDescription("这是测试用房的测试描述");
		h.setName("这是一间测试用房");
		service.save(h);
	}
	
	/**
	 * 【通过】
	 */
	@Test
	public void testQueryAndUpdate(){
		House house = service.queryEntityById("402881e465cb48a10165cb48bf340000");
		System.out.println(house.isEnable());
		house.setDescription("修改后的描述");
		service.update(house);
	}
	
	/**
	 * 【通过】
	 */
	@Test
	public void testDelete(){
		service.deleteById("402881e465cb48a10165cb48bf340000");
	}
}
