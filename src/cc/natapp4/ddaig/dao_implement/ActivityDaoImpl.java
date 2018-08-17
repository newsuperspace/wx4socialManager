package cc.natapp4.ddaig.dao_implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cc.natapp4.ddaig.dao_interface.ActivityDao;
import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl<Activity> implements ActivityDao {

	/*
	 * Spring的DI诸如的注解引用法，name属性指定向目标数据成员中DI注入哪个Bean（Spring容器中的Bean），
	 * 就用applicationContext.xml 配置文件中声明的Bean的name来作为该属性的参数值
	 */
	@Resource(name = "hibernateTemplate")
	public HibernateTemplate template;

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return this.template;
	}

	// =======================================Dao方法====================================

	@Override
	public List<Activity> getCanJoinActivityList(String openid) {
		// 先获取当前用户的user对象
		User user = (User) this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0);

		// 先从数据库找到所有与该用户所属层级对象有关的活动
		List<Activity> allActivities = this.getAllActivities(openid);
		List<Activity> list = new ArrayList<Activity>();
		// 开始从中筛选符合条件的（没过报名截止期的、没报名的）
		long currentTime = System.currentTimeMillis();
		for (Activity a : allActivities) {
			if (a.getBaoMingEndTime() < currentTime) {
				// 活动的报名期限已经超时了
				continue;
			}
			// 获取该还未过报名期的活动的参与者列表（也是报名列表）
			List<Visitor> visitors = a.getVisitors();
			// 如果还没有人报名该活动，则该活动是我们要找的
			if (visitors == null || visitors.size() == 0) {
				list.add(a);
				continue;
			} else {
				// 标记位
				boolean canAdd = true;
				// 如果已经存在活动参与者List，则需要进一步筛选该活动列表是否已经存在本用户了
				for (Visitor v : visitors) {
					if (v.getUser().getUid().equals(user.getUid())) {
						// 该用户已经是该活动的参与者了，则该活动就不用被选取了
						canAdd = false;
						break;
					}
				}
				if (canAdd) {
					list.add(a);
				}
			}
		}
		return list;
	}

	@Override
	public List<Activity> getJoiningActivityList(String openid) {
		// 先获取当前用户的user对象
		User user = (User) this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0);

		// 先从数据库找到所有与该用户所属层级对象有关的活动
		List<Activity> allActivities = this.getAllActivities(openid);
		List<Activity> list = new ArrayList<Activity>();
		// 开始从中筛选符合条件的（没过活动结束期的，已报名的）
		long currentTime = System.currentTimeMillis();
		for (Activity a : allActivities) {
			if (a.getActivityEndTime() < currentTime) {
				continue;
			}
			
			List<Visitor> visitors = a.getVisitors();
			if (visitors == null || visitors.size() == 0) {
				continue;
			} else {
				// 标记位
				boolean canAdd = false;
				for (Visitor v : visitors) {
					// 已报名了吗？
					if (v.getUser().getUid().equals(user.getUid())) {
						// 已经报名了
						canAdd = true;
						break;
					}
				}
				if (canAdd) {
					list.add(a);
				}
			}
		}
		return list;
	}

	@Override
	public List<Activity> getJoinedActivityList(String openid) {
		// 先获取当前用户的user对象
		User user = (User) this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0);

		// 先从数据库找到所有与该用户所属层级对象有关的活动
		List<Activity> allActivities = this.getAllActivities(openid);
		List<Activity> list = new ArrayList<Activity>();
		// 开始从中筛选符合条件的（已过活动结束期的，已报名的）
		long currentTime = System.currentTimeMillis();
		for (Activity a : allActivities) {
			if (a.getBaoMingEndTime() > currentTime) {
				continue;
			}
			
			List<Visitor> visitors = a.getVisitors();
			if (visitors == null || visitors.size() == 0) {
				continue;
			} else {
				// 标记位
				boolean canAdd = false;
				for (Visitor v : visitors) {
					if (v.getUser().getUid().equals(user.getUid())) {
						canAdd = true;
						break;
					}
				}
				if (canAdd) {
					list.add(a);
				}
			}
		}
		return list;
	}

	
	
	/**
	 * 【通过JUnit初步测试】
	 */
	@Override
	public List<Activity> getAllActivities(String openid) {
		// 存放符合条件的查询结果的List容器
		List<Activity> list = new ArrayList<Activity>();

		// 先通过openid找到该用户的user对象
		User user = (User) this.getHibernateTemplate().find("from User u where u.openid=? ", openid).get(0);
		// 然后进一步获取user的member，得到其在层级结构中的位置
		Member member = user.getMember();
		// 然后开始分析member中各个层级对象的设置，利用已经外键关联的层级对象在数据库查找这些层级对象的所有activity
		Set<DoingProject> doingProjects = null;
		if (null != member.getFourthLevel() || null != member.getThirdLevel()) {
			// 用户属于某个第四层级或属于某个第三层级时，他能参加从第三层级到街道层级的所有活动
			// 从3层级的活动找起
			ThirdLevel thirdLevel = member.getThirdLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = thirdLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				list.addAll((List<Activity>) this.getHibernateTemplate()
						.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
			}
			// 再找2层级的活动
			SecondLevel secondLevel = member.getSecondLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = secondLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getThirdLevel() == null) {
					// 当前查找的层级对象是2层级，如果所遍历出来的doingProject对象不具备次一层级也就是3层级对象，说明该doingProject是该第二层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 然后从1层级找
			FirstLevel firstLevel = member.getFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = firstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getSecondLevel() == null) {
					// 当前查找的层级对象是1层级，如果所遍历出来的doingProject对象不具备次一层级也就是2层级对象，说明该doingProject是该第1层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 之后是从社区层级找
			ZeroLevel zeroLevel = member.getZeroLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = zeroLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getFirstLevel() == null) {
					// 当前查找的层级对象是社区层级，如果所遍历出来的doingProject对象不具备次一层级也就是1层级对象，说明该doingProject是该社区层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 最后是街道层级
			MinusFirstLevel minusFirstLevel = member.getMinusFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = minusFirstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getZeroLevel() == null) {
					// 当前查找的层级对象是街道层级，如果所遍历出来的doingProject对象不具备次一层级也就是社区层级对象，说明该doingProject是该街道层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 至此，从该用户所属的第三层级到街道层级所有的活动（各种状态的活动）都已经找出来放在list中了

		} else if (null != member.getSecondLevel()) {
			// 如果第四、第三为null，第二层级不为空，用户属于某个第二层级，可参加第二层级到街道层级活动
			// 再找2层级的活动
			SecondLevel secondLevel = member.getSecondLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = secondLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getThirdLevel() == null) {
					// 当前查找的层级对象是2层级，如果所遍历出来的doingProject对象不具备次一层级也就是3层级对象，说明该doingProject是该第二层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 然后从1层级找
			FirstLevel firstLevel = member.getFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = firstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getSecondLevel() == null) {
					// 当前查找的层级对象是1层级，如果所遍历出来的doingProject对象不具备次一层级也就是2层级对象，说明该doingProject是该第1层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 之后是从社区层级找
			ZeroLevel zeroLevel = member.getZeroLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = zeroLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getFirstLevel() == null) {
					// 当前查找的层级对象是社区层级，如果所遍历出来的doingProject对象不具备次一层级也就是1层级对象，说明该doingProject是该社区层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 最后是街道层级
			MinusFirstLevel minusFirstLevel = member.getMinusFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = minusFirstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getZeroLevel() == null) {
					// 当前查找的层级对象是街道层级，如果所遍历出来的doingProject对象不具备次一层级也就是社区层级对象，说明该doingProject是该街道层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}

		} else if (null != member.getFirstLevel()) {
			// 从4到2都是空，而1不为空，用户属于某个第一层级，可参加从街道到1级的活动
			// 然后从1层级找
			FirstLevel firstLevel = member.getFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = firstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getSecondLevel() == null) {
					// 当前查找的层级对象是1层级，如果所遍历出来的doingProject对象不具备次一层级也就是2层级对象，说明该doingProject是该第1层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 之后是从社区层级找
			ZeroLevel zeroLevel = member.getZeroLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = zeroLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getFirstLevel() == null) {
					// 当前查找的层级对象是社区层级，如果所遍历出来的doingProject对象不具备次一层级也就是1层级对象，说明该doingProject是该社区层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 最后是街道层级
			MinusFirstLevel minusFirstLevel = member.getMinusFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = minusFirstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getZeroLevel() == null) {
					// 当前查找的层级对象是街道层级，如果所遍历出来的doingProject对象不具备次一层级也就是社区层级对象，说明该doingProject是该街道层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
		} else if (null != member.getZeroLevel()) {
			// 从4到1都是空，社区层级不为null，用户属于某个社区层级，可参加从街道、社区两级活动
			// 之后是从社区层级找
			ZeroLevel zeroLevel = member.getZeroLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = zeroLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getFirstLevel() == null) {
					// 当前查找的层级对象是社区层级，如果所遍历出来的doingProject对象不具备次一层级也就是1层级对象，说明该doingProject是该社区层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
			// 最后是街道层级
			MinusFirstLevel minusFirstLevel = member.getMinusFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = minusFirstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getZeroLevel() == null) {
					// 当前查找的层级对象是街道层级，如果所遍历出来的doingProject对象不具备次一层级也就是社区层级对象，说明该doingProject是该街道层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
		} else if (null != member.getMinusFirstLevel()) {
			// 从4到0都是空，街道层级不为null，用户只属于街道层级，可参加从街道发起的活动
			// 最后是街道层级
			MinusFirstLevel minusFirstLevel = member.getMinusFirstLevel();
			// 这里获取到的是当前层级及所有子层级的管理项目
			doingProjects = minusFirstLevel.getDoingProjects();
			for (DoingProject dp : doingProjects) {
				if (dp.getZeroLevel() == null) {
					// 当前查找的层级对象是街道层级，如果所遍历出来的doingProject对象不具备次一层级也就是社区层级对象，说明该doingProject是该街道层级自己的doingProject
					// 然后我们只获取层级自己的doingProject的全部活动，而不获取子层级的doingProject的活动
					list.addAll((List<Activity>) this.getHibernateTemplate()
							.find("from Activity a inner join fetch a.project dp where dp.dpid=?", dp.getDpid()));
				}
			}
		} else {
			// 不属于任何层级（直接扫描公众号二维码加入的公众号），没有活动可参加
			// 不做任何事
		}
		return list;
	}

}
