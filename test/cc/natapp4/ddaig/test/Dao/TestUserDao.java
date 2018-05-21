package cc.natapp4.ddaig.test.Dao;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.dao_interface.ManagerDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.User;

/**
 * 用来测试我们所搭建的domain和domian.cengji 中定义的ORM类和xxx.hbm.xml配置的正确性
 * 是否能正确在数据库中创建我们需要的数据库表
 * @author Administrator
 *
 */
public class TestUserDao {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test
	public void  testQueryManagerByTag(){
		UserDao  dao =(UserDao)context.getBean("userDao");
		List<User> users = dao.getManagers("zero");
		for(User  u: users){
			System.out.println(u.getUsername());
		}
	}
	
	@Test
	public void testQueryByUsername(){
		UserDao  dao  =  (UserDao) context.getBean("userDao");
		User user = dao.getUserByUsername("张三");
		if(null!=user){
			System.out.println("查出了数据");
		}else{
			System.out.println("没查出数据");
		}
	}
	
	
	
}
