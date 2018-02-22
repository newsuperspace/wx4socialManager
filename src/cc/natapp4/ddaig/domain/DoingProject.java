package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

/**
 * 正在进行中的项目列表
 * @author Administrator
 * 
 * 通过外键序列的形态，定位当前项目的执行方
 * 
 * 【基本原则】
 * 1、关于项目委派原则。有积分项目可以被委派给下层级对象执行；无积分项目只能落户到发起方自己手中使用
 * ①街道_first层级发起的项目，只可以委派到社区zero层级对象上
 * ②社区zero层级对象掌管的项目（包括自己发起和从街道委派得来的）可以委派到包括first/second/third的任何层级对象上
 * ③委派的原则是：大项目需要大人力资源，则委派给高层级对象；小项目只需要少量人力资源;则委派给低层级对象
 * 
 * 2、关于项目执行方的定位原则。
 * ①无积分项目被发起方的高层级过审后，在创建DoingProject数据时先自动将发起方的外键关联到当前表的对应字段上，
 * 然后补全到zid的字段（_first和低层级的外键保持为null）。这里以某second层级对象发起的项目得到过审为例子：
 * minusFirstLevel			zeroLevel				firstLevel				secondLevel	  		thirdLevel			
 *				null						√					←			√					←				√							null					
 * 	这样就能确定该项目执行方式secondLevel的一个层级对象了，那么当前端要求查找某个second层级对象直接运行的项目的时候
 * 就可以通过 WHERE  secondLevel='123' AND  thirdLevel=null 找到了。
 * ②街道发起的有积分项目的定位
 * 只要minusFirstLevel !=null  就说明该项目是街道发起的项目，然后再查找外键序列中第一个null的外键字段的上一个外键字段
 * 保存的就是该项目的直接执行方，举个列子说明一下
 * <1>街道项目自己过审后
 * minusFirstLevel			zeroLevel				firstLevel				secondLevel	  		thirdLevel			
 *				√							null							null							null							null	
 *<2>街道对该项目可以采取的唯一操作时委派给社区，当完成社区委派后...
 * minusFirstLevel			zeroLevel				firstLevel				secondLevel	  		thirdLevel			
 *				√							√							null							null							null	
 *<3> 社区可以根据项目的规模选择项目的执行方，例如项目不大不小，second层级对象即可完成则...
 *首先在secondLevel外键上填入执行方的scid，完成外键关联
 *minusFirstLevel			zeroLevel				firstLevel				secondLevel	  		thirdLevel			
 *				√							√							null									√							null	
 *然后再补全到zeroLevel的高层级的外键管理即可完成"委派任务"
 * minusFirstLevel			zeroLevel				firstLevel				 secondLevel	  		thirdLevel			
 *				√							√								√					←				√							null	
 * ③社区发起的有积分项目定位
 * <1>新建数据在DoingProject表中的初始状态（刚过审未委派）
 * minusFirstLevel			zeroLevel					firstLevel					secondLevel	  			thirdLevel			
 *				null							√								null									null							null	
 * <2>完成向secondLevel层级对象的委派后
 *  minusFirstLevel			zeroLevel					firstLevel					secondLevel	  		thirdLevel			
 *				null							√					←				 √					←					√							null	
 * 
 * 
 */
public class DoingProject implements Serializable {

	// 主键
	private String pid;
	/*
	 *  项目劳务积分,从BesuereProject.laborCost的金钱直接1:10兑换成积分存储在这里
	 *  之所以是1:10的意思就是 1块钱相当于10积分，也就是说1积分相当于0.1块钱
	 */
	private int	laborCost;       // 项目总劳务积分
	/*
	 *  项目剩余劳务积分
	 *  lastLaborCost/laborCost =  项目开展进度
	 */
	private int	lastLaborCost; 
	
	// -------------------------------------Foreign Key---------------------
	// 一对一(通过它可以了解到项目的审核历史以及确定项目的发起方等信息，因此还是很有用的)
	private BesureProject  besureProject;
	// 定位项目的在层级化结构中的执行方
	private MinusFirstLevel minusFirstLevel;     // 空值是null
	private ZeroLevel zeroLevel; 						// 空值是null
	private FirstLevel firstLevel;							// 空值是null
	private SecondLevel secondLevel;				// 空值是null
	private ThirdLevel thirdLevel;						// 空值是null
	
	// 项目包含的活动列表信息【以项目来管理活动的原则体现★】
	private List<Activity>  activities;
	
	// TODO 项目材料资金部分管理实体(一对一)
	private ProjectAboutShoping   projectAboutShoping;

	// ======================SETTERs/GETTERs=============
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(int laborCost) {
		this.laborCost = laborCost;
	}

	public int getLastLaborCost() {
		return lastLaborCost;
	}

	public void setLastLaborCost(int lastLaborCost) {
		this.lastLaborCost = lastLaborCost;
	}

	// no @JSON
	public BesureProject getBesureProject() {
		return besureProject;
	}
	public void setBesureProject(BesureProject besureProject) {
		this.besureProject = besureProject;
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

	@JSON(serialize=false)
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	// no @JSON
	public ProjectAboutShoping getProjectAboutShoping() {
		return projectAboutShoping;
	}
	public void setProjectAboutShoping(ProjectAboutShoping projectAboutShoping) {
		this.projectAboutShoping = projectAboutShoping;
	}
	
	
}
