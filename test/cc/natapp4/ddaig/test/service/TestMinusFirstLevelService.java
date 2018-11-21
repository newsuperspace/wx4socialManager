package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.LevelInterface;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;

/**
 * 测试与FirstLevel层级对象操作有关的业务方法
 * 
 * @author Administrator
 *
 */
public class TestMinusFirstLevelService {

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"spring/applicationContext4Test.xml");
	private  MinusFirstLevelService  service  =  (MinusFirstLevelService) context.getBean("minusFirstLevelService");

	@Test  // pass
	public void testSave() {
		MinusFirstLevel   mfl  =  new  MinusFirstLevel();
		mfl.setMflid(UUID.randomUUID().toString());
		mfl.setDescription("这是一个测试用MinusFirstLevel层级");
		mfl.setName("MinusFirstLevel01");
		
		service.save(mfl);
	}
	
	@Test  // pass
	public void testQueryAndUpdate(){
		
		MinusFirstLevelService  service   =  (MinusFirstLevelService) context.getBean("minusFirstLevelService");
		MinusFirstLevel m = service.queryEntityById("e60cec9c-52be-4dda-a8a2-f2bf0ccababe");
		/*
		 * Hibernate的关于查询数据的事务管理是这样
		 * 由于正常情况下数据查询出来就完事儿了，不会涉及后续的数据库事务操作，因此在通过service执行查询操作后
		 * 由于Spring基于AOP（AOP又是基于动态代理设计模式实现的）的声明式事务处理的识别注解已经添加到了所有
		 * service的父类DaoServiceImpl上了，因此所有service类中的方法都置于事务管理之下，也就是在方法执行前会自动
		 * 开启事务，方法调用完成后就提交了事务，而session也会随着事务的提交而关闭，这直接导致我们所
		 * 操作的持久化状态对象会因为session关闭而变成托管状态对象，如果你在xxx.hbm.xml中配置了某个引用属性
		 * 是延迟加载，那么此时你不能引用到任何数据，因为如果需要引用延迟加载的数据就必须继续通过原本使用的
		 * session连接数据库获取，但此时session已经销毁不复存在自然也就获取不到数据了。
		 * 解决办法是：
		 * ★（1）在实际SSH开发环境，Spring会提供一个名叫OpenSessionInViewFilter的过滤器，该过滤器负责统一
		 * 管理每次请求的session对象，因此不会随着查询操作的结束而过早的关闭session，因此你可以在当前这次请求的
		 * 整个MVC层中 的任何位置随时获取到懒加载的数据库数据。
		 * （2）修改Hibernate映射文件，将所有引用属性的懒加载关闭
		 */
//		if(!m.getChildren().isEmpty()){
//			for(ZeroLevel  z: m.getChildren()){
//				System.out.println("Zero层级对象的ID是："+z.getZid());
//				if(!z.getChildren().isEmpty()){
//					for(FirstLevel f: z.getChildren()){
//						System.out.println("First层级对象的ID是："+f.getFlid());
//						if(!f.getChildren().isEmpty()){
//							for(SecondLevel s: f.getChildren()){
//								System.out.println("Second层级对象的ID是："+s.getScid());
//								if(!s.getChildren().isEmpty()){
//									for(ThirdLevel tl:s.getChildren()){
//										System.out.println("Third层级对象的ID是："+tl.getThid());
//										if(!tl.getChildren().isEmpty()){
//											for(FourthLevel fth: tl.getChildren()){
//												System.out.println("Fourth层级对象的ID是："+fth.getFoid());
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		m.setDescription(m.getDescription()+System.currentTimeMillis());
		service.update(m);
	}

	@Test  // test
	public void testDelete(){
		FirstLevelService service = (FirstLevelService) context.getBean("firstLevelService");
		FirstLevel fl = service.queryEntityById("402881fa61d079800161d0799dce0000");
		service.delete(fl);
	}
	
}
