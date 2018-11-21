package cc.natapp4.ddaig.dao_implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
		User user = (User) (this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0));

		// 先从数据库找到所有与该用户所属的所有层级有关的活动（已经去重了）
		List<Activity> allActivities = this.getAllActivities(openid);
		// 新建一个list容器用来存放最终确定可以参加的活动
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
		User user = (User) (this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0));

		// 先从数据库找到所有与该用户所属层级对象有关的活动
		List<Activity> allActivities = this.getAllActivities(openid);
		// 新建容器，存放返回结果
		List<Activity> list = new ArrayList<Activity>();
		// 开始从中筛选符合条件的（没过活动结束期的，已报名的）
		long currentTime = System.currentTimeMillis();
		for (Activity a : allActivities) {
			if ((a.getActivityEndTime() + 1000L * 60 * 30) < currentTime) {
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
		User user = (User) (this.getHibernateTemplate().find("from User u where u.openid=?", openid).get(0));

		// 先从数据库找到所有与该用户所属层级对象有关的活动
		List<Activity> allActivities = this.getAllActivities(openid);
		// 存放结果
		List<Activity> list = new ArrayList<Activity>();
		// 开始从中筛选符合条件的（已过活动结束期的，已报名的）
		long currentTime = System.currentTimeMillis();
		for (Activity a : allActivities) {
			if ((a.getActivityEndTime() + 1000L * 60 * 30) >= currentTime) {
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
	 * 【通过JUnit初步测试】 为用户获取其可报名参加的所有活动， 这些活动来自于用户所参加的所有层级对象，通过user.members
	 * 定位到层级对象 然后再获取每个层级对象的activity，并返回到前端显示出来
	 */
	@Override
	public List<Activity> getAllActivities(String openid) {
		// 存放符合条件的查询结果的List容器
		List<Activity> list = new ArrayList<Activity>();
		// 先通过openid找到该用户的user对象
		User user = (User) (this.getHibernateTemplate().find("from User u where u.openid=? ", openid).get(0));
		Set<DoingProject> doingProjects = null;
		// 然后进一步获取user的所有member，得到其在层级结构中的所有位置
		List<Member> members = user.getMembers(); 
		for (Member member : members) {
			// 然后开始分析member中各个层级对象的设置，利用已经外键关联的层级对象在数据库查找这些层级对象的所有activity
			if (null != member.getFourthLevel() || null != member.getThirdLevel()) {
				// 用户属于某个第四层级或属于某个第三层级时，他能参加从第三层级到街道层级的所有活动,
				// 因为目前来说第四层级因为没有项目因此不能创建活动
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
		}
		
		//  开始List容器的去重操作
		List<Activity>  list4result  =  new  ArrayList<Activity>();
		// 设置一个HashSet集合，集合的特性就是不允许存在相同的对象
		Set set = new HashSet();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			// 我们遍历list容器中的每个活动
			Object element = iter.next();
			// 并尝试将活动放入到set集合中
			if (set.add(element)){
				// 如果返回true，说明set集合中还不存在相同的活动，则可以把该活动放入到list4result
				list4result.add((Activity)element);
			}
			// 否则说明Set集合中已经存在该活动，不应该再放入到list4result容器中，从而实现去重操作
		}
		return list4result;
	}

	@Override
	public List<Activity> getActivities4House(String hid) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long millis = System.currentTimeMillis();
		String format2 = format.format(new Date(millis));
		Date parse = null;
		try {
			parse = format.parse(format2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long startTime = parse.getTime() + 1000L * 60 * 60 * 24;
		long endTime = startTime + 1000L * 60 * 60 * 24 * 15;

		List<Activity> list = (List<Activity>) this.getHibernateTemplate().find(
				"from Activity a inner join fetch a.house h where h.hid=? and a.activityBeginTime >? and a.activityBeginTime <?",
				hid, startTime, endTime);

		return list;
	}

}
