package cc.natapp4.ddaig.test.Dao;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import cc.natapp4.ddaig.dao_interface.UserDao;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;

/**
 * 用来测试我们所搭建的domain和domian.cengji 中定义的ORM类和xxx.hbm.xml配置的正确性
 * 是否能正确在数据库中创建我们需要的数据库表
 * @author Administrator
 *
 */
public class TestUser_Manager_Member {

	private static final ApplicationContext  context =  new ClassPathXmlApplicationContext("spring/applicationContext.xml");

	/**
	 * 级联的新建【通过】
	 */
	@Test
	public void saveUserWithMemberAndManager(){
		Member  m  =  new Member();
		Manager  manager =  new Manager();
		User u  =  new  User();
		u.setAge(67);
		u.setAddress("北京市朝阳区朝外大街东路幸福大街221号楼203室");
		u.setCardid("110101189908324637");
		u.setEmail("xxoo@qq.com");
		u.setIshere(true);
		u.setLocked(false);
		String uid  =  UUID.randomUUID().toString();
		u.setUid(uid);
		u.setOpenid("xoudofsu977329j2ojfdofus");
		
		/*
		 * ★★
		 * 基于“主键关联”的一对一关系映射的持久化状态对象与其他一对多或多对多关系映射的
		 * 持久化状态对象的操作是不同的。
		 * 普通的一对多或多对多关系映射，只要在当前所操作的持久化状态对象的类的***.hbm.xml
		 * 中针对外键关联的其他从表设置了cascade（为save-update或all都可）级联操作了，那么只需
		 * 将从表的持久化状态对象放入到当前操作的主表持久化状态对象中，然后直接通过dao.save()新建
		 * 主表对象，那么从表的数据也会被级联地生成到各自对应的表中去了,这一过程并不需要也在其他
		 * 从表的持久化对象中放入主表的持久化对象（如果是双向关联那么在从表的持久化类中也保留有
		 * 用来引用当前主表的引用属性）
		 * 多对多和一对多从表的主键是依据各自xxx.hbm.xml主键产生策略生成的（通常
		 * 都是uuid/assign/increment等）因此不需要参考主表数据，自然也就不用在从表持久化对象
		 * 中引用主表的持久化对象。
		 * 但是对于“基于主键”的一对一映射，由于 manager和member的主键产生需要参考user
		 * （主表）的主键，因此在各自依据持久化状态对象向数据库创建数据的时候，当在创建
		 * manager表和member表的uid（主键）数据的时候，根据各自的xxx.hbm.xml配置说明
		 * 需要参考持久化对象中名叫“user”的属性所引用的持久化状态对象的主键（user的主键是由
		 * User.hbm.xml确定的），而如果你不将user（主表持久化对象）分别放入到manager和member
		 * 的user属性，则Hibernate在创建数据时当要参考user属性所引用的主表持久化对象的数据时
		 * 引用到的是一个null，自然就会爆出错误来
		 */
		u.setMember(m);
		m.setUser(u);
		u.setManager(manager);
		manager.setUser(u);
		
		UserDao  dao  =  (UserDao)context.getBean("userDao");
		dao.save(u);
	}
	
	/**
	 * 级联的查询【通过】
	 */
	@Test
	public void  queryUserWithManagerAndMember(){
		UserDao  dao  =  (UserDao)context.getBean("userDao");
		User u= dao.queryEntityById("c2a24056-3325-47b1-bac7-fc7386b30c2e");
		System.out.println(u.getManager().getUid());
		System.out.println(u.getMember().getUid());
	}

	@Test
	public void updateUserWithManagerAndMember(){
		UserDao  dao  =  (UserDao)context.getBean("userDao");
		User u= dao.queryEntityById("c2a24056-3325-47b1-bac7-fc7386b30c2e");
		u.setUid(UUID.randomUUID().toString());
		dao.update(u);
	}
	
	/**
	 * 级联的删除【通过】
	 */
	@Test
	public void deleteUserWithManagerAndMember(){
		UserDao  dao  =  (UserDao)context.getBean("userDao");
		User u= dao.queryEntityById("e7f4a91b-d46c-4a7c-8395-2f2ccf5e08dc");
		dao.delete(u);
	}

}
