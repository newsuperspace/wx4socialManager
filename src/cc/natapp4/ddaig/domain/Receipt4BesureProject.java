package cc.natapp4.ddaig.domain;

import java.io.Serializable;

/**
 * 存放回复待审项目的信息的数据库表
 * @author Administrator
 *
 */
public class Receipt4BesureProject implements Serializable {

	// 【主键】——建议主键产生器使用sequence，节省空间消耗
	private int rbpid;
	// 回复人姓名
	private String name;
	/*
	 * 消息类型（当前硬编码至此）
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
	/*
	 * ★
	 * 这个是用来记录当前数据库数据的排序的序号字段，
	 * 虽然是由Hibernate自动维护的，但也要像配置普通字段那样
	 * 设置好SETTERs/GETTERs和ORM映射的<property />描述
	 */
	private int  index4bproject;
	
	
	// ==============SETTERs/GETTERs================
	
	public int getIndex4bproject() {
		return index4bproject;
	}
	public void setIndex4bproject(int index4bproject) {
		this.index4bproject = index4bproject;
	}

	public int getRbpid() {
		return rbpid;
	}
	public void setRbpid(int rbpid) {
		this.rbpid = rbpid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BesureProject getBesureProject() {
		return besureProject;
	}
	public void setBesureProject(BesureProject besureProject) {
		this.besureProject = besureProject;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
