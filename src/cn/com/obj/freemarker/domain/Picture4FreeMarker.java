package cn.com.obj.freemarker.domain;

import java.io.Serializable;

public class Picture4FreeMarker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 图片的代称
	private String name;
	// 一般用作图片下方的解释说明
	private String description;
	// 图片的base64编码字符串
	private String base64Str;
	
	
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
	public String getBase64Str() {
		return base64Str;
	}
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
	
	

}
