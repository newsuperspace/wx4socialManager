package cc.natapp4.ddaig.test.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.utils.ConfigUtils;

public class TestGroupService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private Properties p = ConfigUtils.getProperties("wxConfig/initTags.properties");
	private GroupingService groupingService = (GroupingService) context.getBean("groupingService");

	/**
	 * 用于在工程阶段，根据initTags.properties中记录的tag内容来构建数据库中grouping表中的数据信心
	 * 而在实际环境中，关于系统tag的初始化操作是由weixinService4SettingImpl 中的initLocalTag()完成的
	 * 也就是在初始化微信（在微信的后台管理页面接入本应用程序的时候）自动完成，无需我们手动准备。
	 */
	@Test
	public void testSave() {
		Iterator iterator = p.entrySet().iterator();
		Grouping g = new Grouping();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			g.setGroupName(entry.getValue());
			g.setTag(entry.getKey());
			groupingService.save(g);
		}
	}

	
	@Test
	public void testQuery() {
		Grouping grouping = groupingService.queryByTagName("common");
		System.out.println(grouping.getGroupName());
	}

	
	/**
	 * 批量创建Tag标签
	 */
	@Test
	public void testBatchCreateGrouping() {
		// （必须，没有与公众号产生交互） 初始化Grouping
		List<Grouping> groupings = this.groupingService.queryEntities(); // 获取本地数据库中存放的Grouping对象，每个对象代表一个标签
		Properties p = ConfigUtils.getProperties("wxConfig/initTags.properties"); // 这里存放着系统初始化需要的标签
		Iterator it = null;
		if (null != groupings) {
			// 如果本地数据库中存在Grouping，那么需要比对现有grouping数量与initTag.properties记录的应有数量

			it = p.entrySet().iterator();
			while (it.hasNext()) {
				// 标记属性——用来表示当前tag是否已经存在于数据库了，默认为false
				boolean isExist = false;
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				for (Grouping g : groupings) {
					// 循环遍历数据库grouping表中已有的tag数据，目标是检测该tag是否已经存在于数据库中了，存在则pass不存在则向数据库新建
					if (g.getTag().equals(key)) {
						// 数据库中存在该tag，直接结束循环
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					// 如果标签不存在，则向数据库中新建一个grouping数据
					Grouping grouping = new Grouping();
					grouping.setTag(key);
					grouping.setGroupName(value);
					this.groupingService.save(grouping);
				}
			}
		} else {
			// 如果得到groupings容器是null，则说明grouping数据库还没有任何一个标签数据，则彻底按照iniTags.properties的标准创新Grouping数据
			it = p.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				Grouping g = new Grouping();
				g.setTag(key);
				g.setGroupName(value);
				this.groupingService.save(g);
			}
		}
	}

	
	
	
	
	
}
