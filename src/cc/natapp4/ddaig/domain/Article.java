package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

/**
 * 在活动结束后记录活动详情（文章+照片）的活动记录文章之用
 * @author Administrator
 *
 */
public class Article implements Serializable {

	// 【主键】
	private String artid;
	// 【外键】本文章所属哪个活动（一对一）
	private Activity activity;
	// 文章文本内容（500）
	private String content;
	// 文章标题（50）
	private String title;
	// 文章照片
	private List<ArticlePhoto> photos;
	// 文章阅读数
	private int readingNum;
	// 文章转发数
	private int forwardingNum;
		
	public int getReadingNum() {
		return readingNum;
	}
	public void setReadingNum(int readingNum) {
		this.readingNum = readingNum;
	}
	public int getForwardingNum() {
		return forwardingNum;
	}
	public void setForwardingNum(int forwardingNum) {
		this.forwardingNum = forwardingNum;
	}
	public String getArtid() {
		return artid;
	}
	public void setArtid(String artid) {
		this.artid = artid;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ArticlePhoto> getPhotos() {
		return photos;
	}
	public void setPhotos(List<ArticlePhoto> photos) {
		this.photos = photos;
	}
	
}
