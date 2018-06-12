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
	
	
	/**
	 * 用于在工程阶段，根据initTags.properties中记录的tag内容来构建数据库中grouping表中的数据信心
	 * 而在实际环境中，关于系统tag的初始化操作是由weixinService4SettingImpl 中的initLocalTag()完成的
	 * 也就是在初始化微信（在微信的后台管理页面接入本应用程序的时候）自动完成，无需我们手动准备。
	 */
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
