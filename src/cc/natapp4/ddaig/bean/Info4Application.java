package cc.natapp4.ddaig.bean;

import java.io.Serializable;

/**
 * 用于在填写加入组织的用户申请的时候，在application.jsp表单页面回显数据之用
 * @author Administrator
 *
 */
public class Info4Application implements Serializable {

	private	String tag;
	private String lid;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	// ==========构造器=========
	public Info4Application(String tag, String lid) {
		this.tag = tag;
		this.lid = lid;
	}
	public Info4Application() {
	}

	
	
}
