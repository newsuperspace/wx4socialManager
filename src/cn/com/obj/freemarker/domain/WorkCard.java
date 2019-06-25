package cn.com.obj.freemarker.domain;

import java.io.Serializable;

public class WorkCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// =======================数据成员=========================
	// 网络显示部分
	private String  qrcodeFullPath;
	private String username;
	private String phone;
	private String sex;
	
	// FreeMarker使用部分
	private String qrcodeB64Str;
	private int indexNum;
	private String minusFirstName;
	private String zeroName;
	private String levelName;
	
	// 前端反馈信息之用
	private String message;
	private boolean result;
	
	// =======================GETTER/SETTER=========================
	public String getLevelName() {
		return levelName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getQrcodeFullPath() {
		return qrcodeFullPath;
	}
	public void setQrcodeFullPath(String qrcodeFullPath) {
		this.qrcodeFullPath = qrcodeFullPath;
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
	public String getQrcodeB64Str() {
		return qrcodeB64Str;
	}
	public void setQrcodeB64Str(String qrcodeB64Str) {
		this.qrcodeB64Str = qrcodeB64Str;
	}
	public int getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
	public String getMinusFirstName() {
		return minusFirstName;
	}
	public void setMinusFirstName(String minusFirstName) {
		this.minusFirstName = minusFirstName;
	}
	public String getZeroName() {
		return zeroName;
	}
	public void setZeroName(String zeroName) {
		this.zeroName = zeroName;
	}
	
	// =======================构造器=========================
	/**
	 * 构造器——线上显示
	 * @param qrcodeFullPath   二维码全路径，形如：“ddaig.nat200.top/weixin/qrcode/1/2/379jfd9sf97s983u32jr.gif”
	 * @param username   		用户姓名
	 * @param phone					用户电话
	 * @param sex						用户性别
	 */
	public WorkCard(String qrcodeFullPath, String username, String phone, String sex) {
		this.qrcodeFullPath = qrcodeFullPath;
		this.username = username;
		this.phone = phone;
		this.sex = sex;
	}
	
	/**
	 * 构造器——基于FreeMarker生成工作证打印文档
	 * @param username				用户姓名
	 * @param phone						用户电话
	 * @param sex							用户性别
	 * @param qrcodeB64Str			二维码base64编码字符串
	 * @param indexNum				用户在组织内的序号
	 * @param minusFirstName		用户所在组织所属街道名称
	 * @param zeroName				用户所在组织所属社区名称
	 * @param levelName				用户所在组织的名称
	 */
	public WorkCard(String username, String phone, String sex, String qrcodeB64Str, int indexNum, String minusFirstName,
			String zeroName, String levelName) {
		this.username = username;
		this.phone = phone;
		this.sex = sex;
		this.qrcodeB64Str = qrcodeB64Str;
		this.indexNum = indexNum;
		this.minusFirstName = minusFirstName;
		this.zeroName = zeroName;
		this.levelName = levelName;
	}
	
	/**
	 * 默认（空）构造器
	 */
	public WorkCard() {
	}
	
	
}
