package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;

public class Geographic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 【主键】
	private String geoid;
	// 经度坐标
	private double longitude;
	// 纬度坐标
	private double latitude;
	// 有效签到半径
	private int radus;
	// 当前坐标是否可用，新建默认为true（可用），如果设置为false则在新建活动时将看不到该坐标
	private boolean enable;
	// 点位名称
	private String name;
	// 点位描述
	private String description;
	// 该地理点位上曾经发起过的活动
	private List<Activity> activities;
	// 该点位信息创建者的层级（-1、0、1、2、3、4）,用来快速定位从下面那个层级对象属性中找到归属层级
	private int level;
	// 点位所处层级对象
	private MinusFirstLevel minusFirstLevel;
	private ZeroLevel zeroLevel;
	private FirstLevel firstLevel;
	private SecondLevel secondLevel;
	private ThirdLevel thirdLevel;
	private FourthLevel fourthLevel;
	
	// ==========================SETTER/GETTER===========================
	
	public boolean isEnable() {
		return enable;
	}
	public int getRadus() {
		return radus;
	}
	public void setRadus(int radus) {
		this.radus = radus;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	@JSON(serialize=false)
	public List<Activity> getActivities() {
		return activities;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public MinusFirstLevel getMinusFirstLevel() {
		return minusFirstLevel;
	}
	public void setMinusFirstLevel(MinusFirstLevel minusFirstLevel) {
		this.minusFirstLevel = minusFirstLevel;
	}
	public ZeroLevel getZeroLevel() {
		return zeroLevel;
	}
	public void setZeroLevel(ZeroLevel zeroLevel) {
		this.zeroLevel = zeroLevel;
	}
	public FirstLevel getFirstLevel() {
		return firstLevel;
	}
	public void setFirstLevel(FirstLevel firstLevel) {
		this.firstLevel = firstLevel;
	}
	public SecondLevel getSecondLevel() {
		return secondLevel;
	}
	public void setSecondLevel(SecondLevel secondLevel) {
		this.secondLevel = secondLevel;
	}
	public ThirdLevel getThirdLevel() {
		return thirdLevel;
	}
	public void setThirdLevel(ThirdLevel thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	public FourthLevel getFourthLevel() {
		return fourthLevel;
	}
	public void setFourthLevel(FourthLevel fourthLevel) {
		this.fourthLevel = fourthLevel;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	
	public String getGeoid() {
		return geoid;
	}
	public void setGeoid(String geoid) {
		this.geoid = geoid;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
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

}
