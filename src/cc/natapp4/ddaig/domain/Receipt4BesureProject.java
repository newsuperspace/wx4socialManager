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
	private String name;
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

	// ==============SETTERs/GETTERs================
	public String getRbpid() {
		return rbpid;
	}
	public void setRbpid(String rbpid) {
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
