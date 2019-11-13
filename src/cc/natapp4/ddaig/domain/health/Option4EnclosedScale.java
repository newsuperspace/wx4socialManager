package cc.natapp4.ddaig.domain.health;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;


/**
 * 封闭式问卷（EnclosedScale），每个题目的评分标准（选项）
 * @author Administrator
 *
 */
public class Option4EnclosedScale implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ===============数据库相关字段=============
	 */
	/*
	 *  -----------外键----------
	 */
	private EnclosedScale enclosedScale;   // 当前题目所属问卷
	/*
	 * ----------普通字段--------
	 */
	private String oid;  // 主键ID
	private int ord;  // 从0开始的序号
	private String description;   // 选项描述，形如：“一般”、“严重、“非常严重”
	
	@JSON(serialize=false)
	public EnclosedScale getEnclosedScale() {
		return enclosedScale;
	}
	public void setEnclosedScale(EnclosedScale enclosedScale) {
		this.enclosedScale = enclosedScale;
	}
	
	/*
	 *  主键ID
	 */
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	/*
	 *  从0开始的序号
	 */
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	
	/*
	 * 选项描述，形如：“一般”、“严重、“非常严重”
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
