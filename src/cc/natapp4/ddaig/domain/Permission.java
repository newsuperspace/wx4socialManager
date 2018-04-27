package cc.natapp4.ddaig.domain;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;


public class Permission implements Serializable {

	private String pid;   // 主键
	private String permissionName;   // 权限标识符，如： save、update、delete等
	private String description;   // 描述权限作用
	
	private boolean enabled;		// 是否可用(默认是true)
	
	/*
	 * 该属性为非数据库字段，仅用作在设置某一层级对象的权限是，用来标记该权限是否被某一层级对象所拥有
	 * true是拥有该权限
	 * false是不拥有
	 */
	private boolean isOpen;
	// ------Foreign Key--------
	// 当前权限所属类型，如user、project、activity等
	private PermissionType  permissionType;
	
	// 拥有当前权限的所有层级化对象
	private Set<MinusFirstLevel>  minusFirstLevels;
	private Set<ZeroLevel>  zeroLevels;
	private Set<FirstLevel> firstLevels;
	private Set<SecondLevel>  secondLevels;
	private Set<ThirdLevel>  thirdLevels;
	private Set<FourthLevel>  fourthLevels;
	

	// ===========GETTERs/SETTERs=========
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// no @JSON
	public PermissionType getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	@JSON(serialize=false)
	public Set<MinusFirstLevel> getMinusFirstLevels() {
		return minusFirstLevels;
	}
	public void setMinusFirstLevels(Set<MinusFirstLevel> minusFirstLevels) {
		this.minusFirstLevels = minusFirstLevels;
	}

	@JSON(serialize=false)
	public Set<ZeroLevel> getZeroLevels() {
		return zeroLevels;
	}
	public void setZeroLevels(Set<ZeroLevel> zeroLevels) {
		this.zeroLevels = zeroLevels;
	}

	@JSON(serialize=false)
	public Set<FirstLevel> getFirstLevels() {
		return firstLevels;
	}
	public void setFirstLevels(Set<FirstLevel> firstLevels) {
		this.firstLevels = firstLevels;
	}

	@JSON(serialize=false)
	public Set<SecondLevel> getSecondLevels() {
		return secondLevels;
	}
	public void setSecondLevels(Set<SecondLevel> secondLevels) {
		this.secondLevels = secondLevels;
	}

	@JSON(serialize=false)
	public Set<ThirdLevel> getThirdLevels() {
		return thirdLevels;
	}
	public void setThirdLevels(Set<ThirdLevel> thirdLevels) {
		this.thirdLevels = thirdLevels;
	}

	@JSON(serialize=false)
	public Set<FourthLevel> getFourthLevels() {
		return fourthLevels;
	}
	public void setFourthLevels(Set<FourthLevel> fourthLevels) {
		this.fourthLevels = fourthLevels;
	}
	
	
	
}
