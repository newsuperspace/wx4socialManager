package cc.natapp4.ddaig.bean;

import java.io.Serializable;

public class SheetJS4BatchCreateUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String username;
	private String phone;
	private String sex;
	private String age;
	
	
	
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



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}



	@Override
	public String toString() {
		return "SheetJS4BatchCreateUser [username=" + username + ", phone=" + phone + ", sex=" + sex + ", age=" + age
				+ "]";
	}
	
	
	
	

}
