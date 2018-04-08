package cc.natapp4.ddaig.test.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.utils.ConfigUtils;

public class TestGroupService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	private Properties  p  =  ConfigUtils.getProperties("wxConfig/initTags.properties");
	private  GroupingService  groupingService   =   (GroupingService) context.getBean("groupingService");
	
	@Test
	public void testSave(){
		Iterator iterator = p.entrySet().iterator();
		Grouping g  =  new  Grouping();
		
		while(iterator.hasNext()){
			Map.Entry<String, String>  entry =  (Map.Entry<String, String>)iterator.next();
			g.setGroupName(entry.getValue());
			g.setTag(entry.getKey());
			groupingService.save(g);
		}
		
	}
	
	@Test
	public void testQuery(){
		Grouping grouping = groupingService.queryByTagName("common");
		System.out.println(grouping.getGroupName());
	}
	
	
	
	
}
