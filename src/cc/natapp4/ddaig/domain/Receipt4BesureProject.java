package cc.natapp4.ddaig.domain;

import java.io.Serializable;

/**
 * 存放回复待审项目的信息的数据库表
 * @author Administrator
 *
 */
public class Receipt4BesureProject implements Serializable {

	// 【主键】——建议主键产生器使用sequence，节省空间消耗
	private String rbpid;
	// 回复人姓名
	private String rname;
	/*
	 * 信息类型（当前硬编码至此）
	 * succeed: 通过审核
	 * note: 修改通知
	 * lost：未通过
	 */
	private String type;
	// 回复的信息内容
	private String content;
	// 回复的待审项目
	private BesureProject  besureProject;
	// 回复时间 2018-1-23 13:32:01（格里高利历毫秒值偏移）
	private long time;
	
	
	
	
}
