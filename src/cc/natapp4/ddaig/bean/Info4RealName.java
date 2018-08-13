package cc.natapp4.ddaig.bean;

import java.io.Serializable;

/**
 * 本Bean用作PersonalCenterAction类的realName()方法进行实名认证时，封装回传给前端的信息
 * 然后放入到值栈栈顶，然后通过结果集索引字符串跳转到消息页，提示用户。
 * @author Administrator
 *
 */
public class Info4RealName implements Serializable {

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
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetailsURL() {
		return detailsURL;
	}
	public void setDetailsURL(String detailsURL) {
		this.detailsURL = detailsURL;
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
