package cc.natapp4.ddaig.domain;

import java.io.Serializable;

/**
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
	
	
	
}
