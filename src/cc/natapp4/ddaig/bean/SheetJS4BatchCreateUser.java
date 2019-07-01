package cc.natapp4.ddaig.bean;

import java.io.Serializable;


/**
 * 当前JavaBean可用于两种用途：
 * 
 * （1）在userAction.batchCreate()中用作Gson解析Json字符串时封装数据，但当前封装数据采用的是直接封装入ArrayList<HashMap<String, String>>容器
 * （2）用作向前端返回后端对于SheetJS解析出的excel数据的校验结果
 * 
 * @author Administrator
 *
 */
public class SheetJS4BatchCreateUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String username;
	private String phone;
	private String sex;
	private String age;
	
	// 存放前端表格行的样式类型
	private String style;
	// 存放前端表格行的数据校验结果
	private String state;
	
	
	
	public String getStyle() {
		return style;
	}
	/**
	 * 默认可以选择
	 * table-warning  数据库已存在该用户，如果该用户没有当前层级的member身份，将只创建身份不重复创建user
	 * table-danger   数据缺失或数据格式不正确，无法根据该数据创建用户
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



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
