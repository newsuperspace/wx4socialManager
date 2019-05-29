package cc.natapp4.ddaig.domain;

import java.io.Serializable;

public class Signin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 【主键】
	private String sid;
	// 签名的base64编码字符串
	private String base64Str;
	// 签名所属人姓名
	private String name;
	// 【外键】签名一对一关联的visitor对象
	private Visitor visitor;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getBase64Str() {
		return base64Str;
	}
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
