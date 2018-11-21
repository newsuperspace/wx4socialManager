package cc.natapp4.ddaig.test.service;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cc.natapp4.ddaig.domain.ProjectType;
import cc.natapp4.ddaig.service_interface.ProjectTypeService;
import cc.natapp4.ddaig.utils.ConfigUtils;

public class TestProjectTypeService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");

	@Test
	public void testBatchCreateProjectType() {

		Properties p = ConfigUtils.getProperties("wxConfig/projectTypes.properties");
		Enumeration<String> keys = (Enumeration<String>) p.propertyNames();

		ProjectType pt = null;
		List<ProjectType> projectTypes = this.service.queryEntities();
		if (null == projectTypes || 0 == projectTypes.size()) {
			// 数据库projecttype中没有任何数据，则直接重新创建
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				pt = new ProjectType();
				pt.setDescription(p.getProperty(key));
				pt.setName(key);
				service.save(pt);
			}
		} else {
			// 数据库中存在旧projectType数据信息，需要经过比对修复
			while (keys.hasMoreElements()) {
				boolean ishere = false;
				// 先获取到待比对的projectType的name字段值
				String name = keys.nextElement();
				for (ProjectType proT : projectTypes) {
					// 与数据库中已有projectType的name字段值进行比较，相同则说明已经存在无需再在数据库中创建了
					if (name.equals(proT.getName())) {
						// 以防万一更新一下该projectType的description
						proT.setDescription(p.getProperty(name));
						service.update(proT);
						ishere = true;
						break;
					}
				}
				if (!ishere) {
					pt = new ProjectType();
					pt.setDescription(p.getProperty(name));
					pt.setName(name);
					service.save(pt);
				}
			}
		}
	}

	@Test // pass
	public void testSave() {
		ProjectType t = new ProjectType();
		t.setDescription("这是一个测试用项目类型");
		t.setName("ProjectType001");
		service.save(t);

	}

	@Test // pass
	public void testQueryAndUpdate() {
		ProjectType t = service.queryEntityById("402881fa61d04d0f0161d04d3da80000");
		t.setDescription(t.getDescription() + "111");
		service.update(t);
	}

	@Test // pass
	public void testDelete() {
		ProjectType t = service.queryEntityById("402881fa61d04d0f0161d04d3da80000");
		service.delete(t);
	}

	/**
	 * 用于在项目的构建阶段，根据projectTypes.properties中的内容批量向数据ProjectType创建数据。
	 * 类似于grouping的作用
	 */
	@Test // pass
	public void batchCreateProjectType() {

		Properties p = ConfigUtils.getProperties("wxConfig/projectTypes.properties");
		Enumeration<String> keys = (Enumeration<String>) p.propertyNames();

		ProjectType pt = null;

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			pt = new ProjectType();
			pt.setDescription(p.getProperty(key));
			pt.setName(key);
			ProjectTypeService service = (ProjectTypeService) context.getBean("projectTypeService");
			service.save(pt);
		}

	}

}
