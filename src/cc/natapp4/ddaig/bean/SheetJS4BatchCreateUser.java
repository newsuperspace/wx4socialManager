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
	private String state ="";
	
	// 决定当前用户数据是否会被创建的标记符号
	private boolean canCreate = false;
	
	
	/**
	 *  数据是否合法，可以创建用户或创建已存在用户的member
	 * (1) 如果用户数据的canCreate = false,则该数据不会被创建
	 * （2） 如果用户数据的canCreate = true，则进一步检测style
	 *     ① 如果 style = table-warning，说明用户已存在，则进一步检测该用户是否在当前层级存在member，
	 *     不存在就立即创建
	 *     ② 如果 style 为空，则直接创建user数据，并在当前层级创建member
	 * @return
	 */
	public boolean isCanCreate() {
		return canCreate;
	}
	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}
	
	
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
