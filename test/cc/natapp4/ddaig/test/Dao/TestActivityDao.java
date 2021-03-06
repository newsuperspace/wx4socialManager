package cc.natapp4.ddaig.test.Dao;



import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.dao_interface.ActivityDao;
import cc.natapp4.ddaig.domain.Activity;

/**
 * 用来测试我们所搭建的domain和domian.cengji 中定义的ORM类和xxx.hbm.xml配置的正确性
 * 是否能正确在数据库中创建我们需要的数据库表
 * @author Administrator
 *
 */
public class TestActivityDao {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private ActivityDao dao = (ActivityDao)context.getBean("activityDao");

	@Test
	public void testGetAllActivities(){
		List<Activity> allActivities = dao.getAllActivities("okNKU0Vb9EQtWTfteAyS3nVMd0Iw");
		System.out.println(allActivities.size());
	}

	@Test
	public void testGetActivities4House(){
		List<Activity> list = dao.getActivities4House("402881e465d1256a0165d12a29190001");
		for(Activity a:list){
			System.out.println(a.getName());
		}
	}
	
	
}
