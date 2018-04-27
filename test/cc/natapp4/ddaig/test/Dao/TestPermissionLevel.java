package cc.natapp4.ddaig.test.Dao;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import cc.natapp4.ddaig.dao_interface.PermissionLevelDao;
import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.User;

/**
 * 用来测试我们所搭建的domain和domian.cengji 中定义的ORM类和xxx.hbm.xml配置的正确性
 * 是否能正确在数据库中创建我们需要的数据库表
 * @author Administrator
 *
 */
public class TestPermissionLevel {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext.xml");

	@Test   // 通过
	public void testQueryByLevel(){
		PermissionLevelDao  dao =(PermissionLevelDao)context.getBean("permissionLevelDao");
		PermissionLevel permissionLevel = dao.queryEntityByLevel(-1);
		System.out.println(permissionLevel.getDescription());
	}
	
}
