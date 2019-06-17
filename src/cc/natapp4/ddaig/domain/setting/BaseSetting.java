package cc.natapp4.ddaig.domain.setting;

import java.io.Serializable;

public class BaseSetting implements Serializable {
	
	// 【主键】
	private String settingid;
	
	// 【字段】
	private String needJoinApply;
	private String tag;
	private String lid;
	
	// 【外键】暂时没有，但是如有子设置项，可以挂载在baseSetting（主配置）中，这是未来设想
	
	// =========================SETTER/GETTER==========================
	public String getSettingid() {
		return settingid;
	}
	public void setSettingid(String settingid) {
		this.settingid = settingid;
	}
	public String getNeedJoinApply() {
		return needJoinApply;
	}
	public void setNeedJoinApply(String needJoinApply) {
		this.needJoinApply = needJoinApply;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	
	
	

}
