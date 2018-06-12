package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 项目类型用来分类项目种类（类似Grouping对用户的区分）
 * @author Administrator
 *
 */
public class ProjectType implements Serializable {

	// 【主键】
	private String ptid;
	// 类型名称（显示在前端发起项目页面，类型选择的selector的选项用）
	private String name;
	// 类型说明
	private String description;
	// 类型可见性
//	private String visible;
	// 该类型下的全部待审项目（由于BesureProject与DoingProject是一对一关系，因此通过它也可以找到某一ProjectType下的全部DoingProject项目）
	private Set<BesureProject> besureProjects;

	// ====================SETTERs/GETTERs=====================
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
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
	
	@JSON(serialize=false)
	public Set<BesureProject> getBesureProjects() {
		return besureProjects;
	}
	public void setBesureProjects(Set<BesureProject> besureProjects) {
		this.besureProjects = besureProjects;
	}
	
	
	
	
}
