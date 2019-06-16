package cc.natapp4.ddaig.domain.setting;

import java.io.Serializable;

public class BaseSetting implements Serializable {
	
	// 【主键】
	private String settingid;
	
	// 【字段】
	private boolean needJoinApply;

	
	
	// 【外键】
	
	
	// =========================SETTER/GETTER==========================
	public String getSettingid() {
		return settingid;
	}

	public void setSettingid(String settingid) {
		this.settingid = settingid;
	}

	public boolean isNeedJoinApply() {
		return needJoinApply;
	}

	public void setNeedJoinApply(boolean needJoinApply) {
		this.needJoinApply = needJoinApply;
	}

}
