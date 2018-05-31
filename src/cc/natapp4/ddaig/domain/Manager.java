package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

/**
 * 该类与User类是一对一的关系，如果一个用户的grouping.tag是
 * minus_first/zero/first/second/third/fourth/admin 中的任何一个，则
 * 就会在当前Manager表中建立一条对应的数据，用来表示其作为社区管理参与者 的身份，同时有对应其管理层级的字段容器用来表示其在该管理层级上所管理的
 * 层级对象的数量（一个管理者可以管理数个同级层级对象）
 * 
 * @author Administrator
 *
 */
public class Manager implements Serializable {

	// 主键
	private String uid;
	// 与User表同步的信息
	/*
	 * 外键关联的容器（一对多） 借助user.grouping.tag 来确定从以下哪个集合中查找当前用户所管理的层级对象★
	 */
	private Set<MinusFirstLevel> mfls;
	private Set<ZeroLevel> zls;
	private Set<FirstLevel> fls;
	private Set<SecondLevel> scls;
	private Set<ThirdLevel> tls;
	private Set<FourthLevel> fols;
	// 当前管理者的具体用户信息
	private User user;
	// 仅供給前端用來获知当前manager所绑定的层级对象的lid（包括六个层级的层级对象的主键，需要通过tag进一步确定）
	public String lid;
	// 仅提供给前端用来再managerList.jsp页面上显示所管理的层级对象的名称
	public String levelName;
	// ===============================功能性的方法========================
	
	public String getLevelName() {
		if(null==user){
			/*
			 * 需要使用到levelName属性的是通过Struts2值栈组建的managerList.jsp页面，而ajax通讯并不需要用到本属性
			 * 而Ajax通讯是是通过user.manager4Ajax来浅拷贝user.manager（因为manager的GETTER被@JSON掉了）
			 * 而manager4Ajax.user与user.manager4Ajax是一对一的关系，因此为了防止在JSON解析时出现死循环，我们不得
			 * 不切断user.manager4Ajax.user = null 的联系，从而导致getLevelName()方法中的
			 * String tag = user.getGrouping.getTag(); 无法使用而出现
			 * java.lang.reflect.InvocationTargetException 异常
			 * 因此这里判断一下user属性是否为null是十分必要的。
			 */
			return null;
		}
		String tag = user.getGrouping().getTag();
		String level = "";
		switch (tag) {
		case "minus_first":
			Iterator<MinusFirstLevel> iterator = mfls.iterator();
			if(iterator.hasNext()){
				level = iterator.next().getName();
			}
			break;
		case "zero":
			Iterator<ZeroLevel> iterator0 = zls.iterator();
			if(iterator0.hasNext()){
				level = iterator0.next().getName();
			}
			break;
		case "first":
			Iterator<FirstLevel> iterator1 = fls.iterator();
			if(iterator1.hasNext()){
				level = iterator1.next().getName();
			}
			break;
		case "second":
			Iterator<SecondLevel> iterator2 = scls.iterator();
			if(iterator2.hasNext()){
				level = iterator2.next().getName();
			}
			break;
		case "third":
			Iterator<ThirdLevel> iterator3 = tls.iterator();
			if(iterator3.hasNext()){
				level = iterator3.next().getName();
			}
			break;
		case "fourth":
			Iterator<FourthLevel> iterator4 = fols.iterator();
			if(iterator4.hasNext()){
				level = iterator4.next().getName();
			}
			break;
		}
		return level;
	}
	
	
	/**
	 * 以目前的系統功能來說，每个管理者还只能委派一个层级对象，
	 * 因此前端页面managerList.jsp中获取某个管理者所绑定的层级对象的时候，需要通过本系列方法
	 * 从集合中返回一个层级对象即可。
	 * @return
	 */
	public String getLid() {
		if(null==user){
			/*
			 * 需要使用到levelName属性的是通过Struts2值栈组建的managerList.jsp页面，而ajax通讯并不需要用到本属性
			 * 而Ajax通讯是是通过user.manager4Ajax来浅拷贝user.manager（因为manager的GETTER被@JSON掉了）
			 * 而manager4Ajax.user与user.manager4Ajax是一对一的关系，因此为了防止在JSON解析时出现死循环，我们不得
			 * 不切断user.manager4Ajax.user = null 的联系，从而导致getLevelName()方法中的
			 * String tag = user.getGrouping.getTag(); 无法使用而出现
			 * java.lang.reflect.InvocationTargetException 异常
			 * 因此这里判断一下user属性是否为null是十分必要的。
			 */
			return null;
		}
		String tag = user.getGrouping().getTag();
		switch (tag) {
		case "minus_first":
			Iterator<MinusFirstLevel> iterator = mfls.iterator();
			if(iterator.hasNext()){
				lid = iterator.next().getMflid();
			}
			break;
		case "zero":
			Iterator<ZeroLevel> iterator0 = zls.iterator();
			if(iterator0.hasNext()){
				lid = iterator0.next().getZid();
			}
			break;
		case "first":
			Iterator<FirstLevel> iterator1 = fls.iterator();
			if(iterator1.hasNext()){
				lid = iterator1.next().getFlid();
			}
			break;
		case "second":
			Iterator<SecondLevel> iterator2 = scls.iterator();
			if(iterator2.hasNext()){
				lid = iterator2.next().getScid();
			}
			break;
		case "third":
			Iterator<ThirdLevel> iterator3 = tls.iterator();
			if(iterator3.hasNext()){
				lid =  iterator3.next().getThid();
			}
			break;
		case "fourth":
			Iterator<FourthLevel> iterator4 = fols.iterator();
			if(iterator4.hasNext()){
				lid = iterator4.next().getFoid();
			}
			break;
		}
		return this.lid;
	}
	


	// ===============================SETTERs/GETTERs================================
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	// 不设@JSON
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取所管理的FirstLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<FirstLevel> getFls() {
		return fls;
	}

	/**
	 * 获取所管理的MinusFirstLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<MinusFirstLevel> getMfls() {
		return mfls;
	}

	public void setMfls(Set<MinusFirstLevel> mfls) {
		this.mfls = mfls;
	}

	/**
	 * 获取所管理的ZeroLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<ZeroLevel> getZls() {
		return zls;
	}

	public void setZls(Set<ZeroLevel> zls) {
		this.zls = zls;
	}

	public void setFls(Set<FirstLevel> fls) {
		this.fls = fls;
	}

	/**
	 * 获取所管理的SecondLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<SecondLevel> getScls() {
		return scls;
	}

	public void setScls(Set<SecondLevel> scls) {
		this.scls = scls;
	}

	/**
	 * 获取所管理的ThirdLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<ThirdLevel> getTls() {
		return tls;
	}

	public void setTls(Set<ThirdLevel> tls) {
		this.tls = tls;
	}

	/**
	 * 获取所管理的FourthLevel层级对象列表
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public Set<FourthLevel> getFols() {
		return fols;
	}

	public void setFols(Set<FourthLevel> fols) {
		this.fols = fols;
	}

}
