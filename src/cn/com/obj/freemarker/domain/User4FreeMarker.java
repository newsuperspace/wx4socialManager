package cn.com.obj.freemarker.domain;

import java.io.Serializable;

public class User4FreeMarker implements Serializable {

	public User4FreeMarker() {
	}
	
	public User4FreeMarker(String username, String phone, String sickname, String address, String email, int age,
			String base64Str) {
		super();
		this.username = username;
		this.phone = phone;
		this.sickname = sickname;
		this.address = address;
		this.email = email;
		this.age = age;
		this.base64Str = base64Str;
	}
	private String username;
	private String phone;
	private String sickname;
	private String address;
	private String email;
	private int age;
	private String base64Str;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSickname() {
		return sickname;
	}
	public void setSickname(String sickname) {
		this.sickname = sickname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBase64Str() {
		return base64Str;
	}
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

	
}
