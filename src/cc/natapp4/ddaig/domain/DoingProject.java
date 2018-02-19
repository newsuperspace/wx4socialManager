package cc.natapp4.ddaig.domain;

import java.io.Serializable;

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
	private String dpid;
	
	
	
	
	
	// 一对一(通过它可以了解到项目的审核历史以及确定项目的发起方等信息，因此还是很有用的)
	private BesureProject  besureProject;
	
}
