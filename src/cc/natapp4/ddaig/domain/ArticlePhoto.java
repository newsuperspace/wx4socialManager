package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

/**
 * 在活动结束后记录活动详情中的照片
 * @author Administrator
 *
 */
public class ArticlePhoto implements Serializable {

	// 【主键】
	private String apid;
	// 【外键】本照片属于那篇文章（多图对一文）
	private Article article;
	// 图片描述，可以用作图片描述小字
	private String description;
	// 图片保存的路径，一般为 ddaig.nat200.top/weixin 作为根目录下的  photo/1/12/图片名.jpg
	private String path;
	
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	//  =======================非数据库字段=============================
	/*
	 * 供给前端页面显示图片只用的URL路径数据
	 */
	private String  url = null;
	public String getUrl() {
		if(null==this.url){
			this.url  =  ServletActionContext.getServletContext().getContextPath()+"/"+this.path;
		}
		return this.url;
	}
	
	
}
