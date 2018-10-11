package cc.natapp4.ddaig.test.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.ArticleService;
import cc.natapp4.ddaig.service_interface.BesureProjectService;

public class TestActivityService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private ActivityService  activityService  =  (ActivityService) context.getBean("activityService");
	private ArticleService  articleService  =  (ArticleService) context.getBean("articleService");

	@Test
	public void testSave(){
		
		Activity  a =  new  Activity();
		a.setAid(UUID.randomUUID().toString());
		a.setName("测试活动A");
		a.setDescription("这是一个用来参与Junit测试的活动数据");
		
		Article art  =  new  Article();
		art.setActivity(a);
		art.setContent("");
		art.setForwardingNum(0);
		art.setReadingNum(0);
		art.setTitle("");
		a.setArticle(art);
		
		activityService.save(a);
	}
	
	@Test
	public void testQuery(){
		Activity activity = activityService.queryEntityById("03be1550-434c-4484-a8d9-7e05be79688a");
		System.out.println(activity.getName());
		System.out.println(activity.getArticle().getArtid());
		System.out.println("========================");
		Article article = articleService.queryEntityById("03be1550-434c-4484-a8d9-7e05be79688a");
		System.out.println("activity的id是"+article.getActivity().getAid());
	}
	
	
}
