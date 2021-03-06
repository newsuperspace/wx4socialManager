package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

public class House implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 【主键】
	private String hid;
	// 房间名称
	private String name;
	// 房间功能描述
	private String description;
	// 房间坐标位置（经度）
	private double latitude = -1;
	// 房间坐标位置（维度）
	private double longitude = -1;
	// 有效签到半径（单位 米）
	private int radus;
	// 房间所开展的活动
	private List<Activity> activities;
	
	// 关于zeroLevel和minusFirstLevel两个数据列上有且只能有一个存在数据，也就是说当前房屋要么属于街道要么属于一个社区，不存在同时属于或同时不属于的情况
	// 当前室内空间所属的社区
	private ZeroLevel zeroLevel;
	// 当前室内空间所属的街道
	private MinusFirstLevel  minusFirstLevel;
	
	// 状态  true可用(默认为可用)，false不可用
	private boolean enable = true;
	
	
	public int getRadus() {
		return radus;
	}
	public void setRadus(int radus) {
		this.radus = radus;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
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
	
	@JSON(serialize=false)
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}
	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}
	
	
	public MinusFirstLevel getMinusFirstLevel() {
		return minusFirstLevel;
	}
	public void setMinusFirstLevel(MinusFirstLevel minusFirstLevel) {
		this.minusFirstLevel = minusFirstLevel;
	}
	
}
