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
 * Manage  记录了其所属user（与user是多对一关系）对于某一层级的管理
 * Manager  与层级对象是多对一关系，每个Manager标记着其唯一绑定的user对某个层级对象的管理权
 * 一个user可以有多个Manager也就代表可以管理多个层级对象，这源自于一个user可以有多个member
 * 也就代表一个user可以在层级金字塔中同时属于多个层级对象，只要是直属的对象（直属的意思是member中
 * 所有层级对象外键，依照从minuse到fourth的顺序查找，最后一个非null的层级就是该对象直属的层级）都可以被
 * 该层级任命为次层级的管理者，因此同一个user可以掌管多个manager对应的层级对象。
 * 
 * 这更符合客观实际，因为贾支书是一个支部书记是党组织的带头人，同时他也是一个文体队伍带头人
 * 而党组织中的成员也有相当一部分是文体队伍的，这就出现了同一个人兼任两个职务和同一个人属于两只队伍
 * 的现象。
 * 
 * @author Administrator
 *
 */
public class Manager implements Serializable {

	// 主键
	private int managerid;
	// 与User表同步的信息
	/*
	 * 外键关联的容器（一对多） 借助user.grouping.tag 来确定从以下哪个集合中查找当前用户所管理的层级对象★
	 */
	private MinusFirstLevel minusFirstLevel;
	private ZeroLevel zeroLevel;
	private FirstLevel firstLevel;
	private SecondLevel secondLevel;
	private ThirdLevel thirdLevel;
	private FourthLevel fourthLevel;
	
	// 当前管理者角色所一一对应的memeber对象，因为memeber中保存着grouping.tag信息，可以从minusFirstLevel~fourthLevel中精准定位具体的层级对象
	private Member  member;
	
	
	// ------------------------------------------非数据库字段--------------------------------------
	
	// 仅供給前端用來获知当前manager所绑定的层级对象的lid（包括六个层级的层级对象的主键，需要通过tag进一步确定）
	public String lid;
	// 仅提供给前端用来在managerList.jsp页面上显示所管理的层级对象的名称
	public String levelName;
	
	// ===============================功能性的方法========================
	
	public String getLevelName() {
		if(null==member){
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
		String tag = member.getGrouping().getTag();
		String level = "";
		switch (tag) {
		case "minus_first":
			level = minusFirstLevel.getName();
			break;
		case "zero":
			level  = zeroLevel.getName();
			break;
		case "first":
			level = firstLevel.getName();
			break;
		case "second":
			level = secondLevel.getName();
			break;
		case "third":
			level = thirdLevel.getName();
			break;
		case "fourth":
			level = fourthLevel.getName();
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
		if(null==member){
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
		String tag = member.getGrouping().getTag();
		switch (tag) {
		case "minus_first":
			lid = minusFirstLevel.getMflid();
			break;
		case "zero":
			lid = zeroLevel.getZid();
			break;
		case "first":
			lid = firstLevel.getFlid();
			break;
		case "second":
			lid = secondLevel.getScid();
			break;
		case "third":
			lid = thirdLevel.getThid();
			break;
		case "fourth":
			lid = fourthLevel.getFoid();
			break;
		}
		return this.lid;
	}
	


	// ===============================SETTERs/GETTERs================================
	// 不设@JSON
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	public MinusFirstLevel getMinusFirstLevel() {
		return minusFirstLevel;
	}
	public void setMinusFirstLevel(MinusFirstLevel minusFirstLevel) {
		this.minusFirstLevel = minusFirstLevel;
	}

	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}
	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}

	public FirstLevel getFirstLevel() {
		return firstLevel;
	}
	public void setFirstLevel(FirstLevel firstLevel) {
		this.firstLevel = firstLevel;
	}

	public SecondLevel getSecondLevel() {
		return secondLevel;
	}
	public void setSecondLevel(SecondLevel secondLevel) {
		this.secondLevel = secondLevel;
	}

	public ThirdLevel getThirdLevel() {
		return thirdLevel;
	}
	public void setThirdLevel(ThirdLevel thirdLevel) {
		this.thirdLevel = thirdLevel;
	}

	public FourthLevel getFourthLevel() {
		return fourthLevel;
	}
	public void setFourthLevel(FourthLevel fourthLevel) {
		this.fourthLevel = fourthLevel;
	}

	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}

	
	
	
	
}
