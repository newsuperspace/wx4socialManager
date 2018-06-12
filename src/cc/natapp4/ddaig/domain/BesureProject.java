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
 * besureProjectList.jsp中列出的信息
 * 
 * 待审核的项目列表
 * @author Administrator
 * 外键排序形态与实际业务情况对照（基于此对照可以在Dao层建立起满足特定查询需要的查询方法）：
 *1、只有社区（zeroLevel）和街道（minusFirstLevel）两个层级可以发起有积分项目，可通过验证laborCost字段是否>0可知
 *①社区发起的有积分项目：  
 *						minusFirstLevel														zeroLevel		firstLevel		secondLevel	  	thirdLevel		fourthLevel
 *							√(有积分项目的街道外键必定存在)			←				√						null					null						null						null
 *														先填入对应发起方层级对象的外键，然后向高层补全
 *②街道发起的有积分项目：
 *						minusFirstLevel														zeroLevel		firstLevel		secondLevel	  	thirdLevel		fourthLevel
 *							√(有积分项目的街道外键必定存在)							null					null					null						null						null
 *
 *2、zero/first/second/third/fourth 发起的无积分项目，可验证laborCost = 0来判断，下面以一个second层级对象发起的项目为例子
 *   来说明如何在层级化结构中定位发起方
 * 						minusFirstLevel												zeroLevel				firstLevel				secondLevel	 		 	thirdLevel				fourthLevel
 *							null(无积分项目的街道外键必定为null)			 √				   ←			√					←				√									×								×
 *																									先填入发起方层级对象的外键再向高层补全（只到zero）
 */
public class BesureProject implements Serializable {

	// -----------------------------------基本数据（所有项目都必须有）-------------------------------
	// 【主键】
	private String bpid;
	// 项目名称
	private String name; 
	// 项目简介（150字）
	private String description;
	// 项目类型
	private ProjectType projectType;
	/*
	 *  项目状态(硬编码)
	 * 												【“已回复”】  提交者在此状态下可修改项目
	 *  										/
	 *  continuing  进行中   分为—   根据examinedTime和commitTime的比对结果进一步分成两种状态
	 *  										\
	 *  											【“待审阅”】  提交者不能修改项目，等待上级审核
	 *  
	 *  succeed  		审核通过
	 *  lost          		审核未通过
	 */
	private String state;
	/*
	 *  项目审定日期（格里高利历毫秒值偏移量，默认=0）
	 *  当项目评审者对项目做出审定时（向Receipt4BesureProject表中写入数据时），这里保存最新的项目审定时间
	 *  则， 当examinedTime > commitTime的时候，表示“已回复”则在前端页面状态位置显示【“已回复”】
	 */
	private long examinedTime;
	/*
	 * 最新的项目提交日期(格里高利历毫秒值偏移量)
	 * 当状态位置显示“已回复”后，提交人才能根据Receipt4BesureProject中的回复信息来修改项目，否则项目不可被发起人修改 ★
	 * 项目提交或修改后commitTime覆盖为最新的提交时间，此时只要判断出commitTime > examinedTime，则就说明项目当前
	 * 状态是【“待审阅”】
	 */
	private long commitTime;
	// 修改次数
	private int updateNum;
	// 项目开始时间（格里高利历的偏移量的毫秒值）,且开始时间必须提前于commitTime一周时间
	private long startTime;
	// 项目结束时间（格里高利历偏移量的毫秒值）
	private long endTime;
	// 预计活动开展场次数
	private int activityTotal;
	// -----------------------------------审核者回馈的数据信息-------------------------------
	private List<Receipt4BesureProject>   receipts;
	// ----------------------------定位项目的发起者（某个层级化对象）在层级化体系中的位置------------------------
	private MinusFirstLevel minusFirstLevel;     // 空值是null
	private ZeroLevel zeroLevel; 						// 空值是null
	private FirstLevel firstLevel;							// 空值是null
	private SecondLevel secondLevel;				// 空值是null
	private ThirdLevel thirdLevel;						// 空值是null
	// -----------------------------------有积分数据必须有-------------------------------
	// 项目书存储相对路径————“2/10/项目书.doc”
	private String   filePath;				
	// 劳务经费数(元)必须是正整数
	private int  laborCost;					// 0为无积分项目；>0为有积分项目
	// 物资采买，可以是浮点数，限定到小数点后1位★
	private float purchaseCost;			// 空值是0
	// -----------------项目通过审核后与DoingProject数据一一对应----------------
	private DoingProject  doingProject;

	// ============================SETTERs/GETTERs======================
	
	public String getBpid() {
		return bpid;
	}
	public void setBpid(String bpid) {
		this.bpid = bpid;
	}	
	
	public int getActivityTotal() {
		return activityTotal;
	}
	public void setActivityTotal(int activityTotal) {
		this.activityTotal = activityTotal;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ProjectType getProjectType() {
		return projectType;
	}
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public long getExaminedTime() {
		return examinedTime;
	}
	public void setExaminedTime(long examinedTime) {
		this.examinedTime = examinedTime;
	}
	
	public long getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(long commitTime) {
		this.commitTime = commitTime;
	}
	
	public int getUpdateNum() {
		return updateNum;
	}
	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	@JSON(serialize=false)
	public List<Receipt4BesureProject> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<Receipt4BesureProject> receipts) {
		this.receipts = receipts;
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
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public int getLaborCost() {
		return laborCost;
	}
	public void setLaborCost(int laborCost) {
		this.laborCost = laborCost;
	}
	
	public float getPurchaseCost() {
		return purchaseCost;
	}
	public void setPurchaseCost(float purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	
	@JSON(serialize=false)
	public DoingProject getDoingProject() {
		return doingProject;
	}
	public void setDoingProject(DoingProject doingProject) {
		this.doingProject = doingProject;
	}
	
}
