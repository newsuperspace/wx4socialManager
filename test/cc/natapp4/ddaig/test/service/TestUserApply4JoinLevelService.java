package cc.natapp4.ddaig.test.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.natapp4.ddaig.domain.Approve4UserJoinLevel;
import cc.natapp4.ddaig.domain.Reply4UserJoinLevelApprove;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.UserApply4JoinLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.service_implement.TestUserServiceImpl;
import cc.natapp4.ddaig.service_interface.Approve4UserJoinLevelService;
import cc.natapp4.ddaig.service_interface.Reply4UserJoinLevelApproveService;
import cc.natapp4.ddaig.service_interface.UserApply4JoinLevelService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;

/**
 * （1）测试数据库
 * Reply4UserJoinLevelApprove
	UserApply4JoinLevel
	Approve4UserJoinLevel
	创建是否正确
	（2）检测UserApply4JoinLevelService 和 Approve4UserJoinLevelService 两个service 到Dao再到数据库
	运行是否正常。
	
 * @author Administrator
 *
 */
public class TestUserApply4JoinLevelService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext4Test.xml");
	private UserApply4JoinLevelService  applyService  =  (UserApply4JoinLevelService) context.getBean("userApply4JoinLevelService");
	private Approve4UserJoinLevelService  approveService  =  (Approve4UserJoinLevelService) context.getBean("approve4UserJoinLevelService");
	private Reply4UserJoinLevelApproveService replyService = (Reply4UserJoinLevelApproveService) context.getBean("reply4UserJoinLevelApproveService");
	
	private ZeroLevelService  zeroLevelService  =  (ZeroLevelService) context.getBean("zeroLevelService");
	// 之所以使用testUserService而不直接使用UserService是因为，userService需要DI注入weixinService4Setting而该bean需要servlet环境支持
	private UserService userService = (UserService) context.getBean("testUserService");
	
	@Test
	public void testSave(){
		// 新建用户申请对象
		UserApply4JoinLevel  apply  =  new UserApply4JoinLevel();
		// 新建层级管理者审核对象
		Approve4UserJoinLevel  approve = new Approve4UserJoinLevel();
		// 查找一个当前数据库中真实存在的zeroLevel层级对象
		ZeroLevel zeroLevel = zeroLevelService.queryEntityById("e9d0a77e-75d3-4f13-ac0a-7ffed167827e");
		// 查找一个当前数据库中真是存在的user对象
		User user = userService.queryEntityById("a0083e31-141a-4e12-b315-7621dc1017c6");
		
		// -----------------------开始组装数据--------------------
		// 建立外键关联
		apply.setApprove4UserJoinLevel(approve);
		approve.setUserApply4JoinLevel(apply);
		
		apply.setUser(user);
		List<UserApply4JoinLevel> applies = user.getUserApply4JoinLevels();
		if(null==applies){
			applies = new ArrayList<UserApply4JoinLevel>();
			user.setUserApply4JoinLevels(applies);
		}
		applies.add(apply);
		
		// 设置apply的所有值
		apply.setBeread(false);
		apply.setStatus(0);
		apply.setTheDesire("我自愿加入中国共产主义先锋团");
		apply.setTheExpertise("成为好人");
		apply.setTheReason("我想做一个好人");
		// 得到当前时间的格里高利历偏移量毫秒值
		long timeStamp  =  System.currentTimeMillis();
		SimpleDateFormat  formater  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = formater.format(new Date(timeStamp));
		apply.setTimeStr(timeStr);
		apply.setTimeStamp(timeStamp);
		// 设置approve的所有值
		approve.setBeread(false);
		approve.setLid(zeroLevel.getZid());
		approve.setReplies(new ArrayList<Reply4UserJoinLevelApprove>());
		approve.setTag("zero");

		applyService.save(apply);
	}
	
	@Test
	public void testDelete(){
		UserApply4JoinLevel apply = applyService.queryEntityById("402881e4684638090168463826930000");
		User  user  =  apply.getUser();
		System.out.println("待删除的申请是来自——"+user.getUsername());
		Approve4UserJoinLevel approve = apply.getApprove4UserJoinLevel();
		System.out.println("待删除的申请的审核者的层级是——"+approve.getTag());
		System.out.println("开始删除");
		user.getUserApply4JoinLevels().remove(apply);
		userService.update(user);
		applyService.delete(apply);
		if(null!=approve.getReplies() && 0!=approve.getReplies().size()){
			// 先删除approve中的所有回复reply记录
			for(Reply4UserJoinLevelApprove r: approve.getReplies()){
				replyService.delete(r);
			}
		}
		approveService.delete(approve);
	}
	
}
