package cn.com.obj.freemarker.domain;

import java.io.Serializable;

public class Signin4FreeMarker implements Serializable {

	
	
	
	public Signin4FreeMarker() {}
	
	public Signin4FreeMarker(String name, String base64Str) {
		this.name = name;
		this.base64Str = base64Str;
	}
	
	private String name;
	private String base64Str;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBase64Str() {
		return base64Str;
	}
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
	
	
}
