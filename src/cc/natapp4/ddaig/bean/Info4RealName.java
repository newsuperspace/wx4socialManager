package cc.natapp4.ddaig.bean;

import java.io.Serializable;

/**
 * 本Bean用作PersonalCenterAction类的realName()方法进行实名认证时，封装回传给前端的信息
 * 然后放入到值栈栈顶，然后通过结果集索引字符串跳转到消息页，提示用户。
 * @author Administrator
 *
 */
public class Info4RealName implements Serializable {
	
	private String total;
	// 使用的图标名称
	private String icon;
	// 消息标题
	private String title;
	// 消息正文
	private String message;
	// 默认为“详情”的连接
	private String details = "详情";
	// 用户点击“详情”连接后跳转的URL
	private String detailsURL;
	
	public String getIcon() {
		return icon;
	}
	/**
	 * weui-icon-warn-red: 红色强烈警告
	 * weui-icon-success: 绿色成功提示
	 * weui-icon-info：蓝色提示
	 * 
	 * 
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	/**
	 * 设置提示信息
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	/**
	 * 如果不显示设置的话，这里将在页面上默认显示"详情"两个字
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetailsURL() {
		return detailsURL;
	}
	/**
	 * 当用户点击"详情"是所跳转的URL路径
	 * @param detailsURL
	 */
	public void setDetailsURL(String detailsURL) {
		this.detailsURL = detailsURL;
	}
	
	
	public String getTotal() {
		return total;
	}
	/**
	 * JSP页面的<title></title>标签的显示
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	// ==========================构造器=======================
	public Info4RealName(String icon, String title, String message, String details, String detailsURL) {
		this.icon = icon;
		this.title = title;
		this.message = message;
		this.details = details;
		this.detailsURL = detailsURL;
	}
	
	public Info4RealName() {
		// 默认（空）构造器
	}
}
