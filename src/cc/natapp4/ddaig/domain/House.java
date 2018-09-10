package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

public class House implements Serializable {

	// 【主键】
	private String hid;
	// 房间名称
	private String name;
	// 房间功能描述
	private String description;
	// 房间坐标位置
	private Geographic geographic;
	// 房间所开展的活动
	private List<Activity> activities;
	// 所属社区
	private ZeroLevel zeroLevel;
	
	
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
	public Geographic getGeographic() {
		return geographic;
	}
	public void setGeographic(Geographic geographic) {
		this.geographic = geographic;
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
	
}
