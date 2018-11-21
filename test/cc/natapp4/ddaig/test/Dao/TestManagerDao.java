package cc.natapp4.ddaig.test.Dao;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.dao_interface.ManagerDao;
import cc.natapp4.ddaig.domain.Manager;

/**
 * 用来测试我们所搭建的domain和domian.cengji 中定义的ORM类和xxx.hbm.xml配置的正确性
 * 是否能正确在数据库中创建我们需要的数据库表
 * @author Administrator
 *
 */
public class TestManagerDao {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext.xml");

//	@Test
//	public void  testQuery(){
//		ManagerDao  dao =(ManagerDao)context.getBean("managerDao");
//		List<Manager> managers = dao.getManagers("zero");
//		for(Manager  m: managers){
//			System.out.println(m.getUser().getUsername());
//		}
//	}
	
}
