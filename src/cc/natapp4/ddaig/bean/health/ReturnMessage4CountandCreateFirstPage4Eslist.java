package cc.natapp4.ddaig.bean.health;

import java.util.List;

import cc.natapp4.ddaig.domain.health.EnclosedScale;

public class ReturnMessage4CountandCreateFirstPage4Eslist {

	
	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	
	private List<EnclosedScale> enclosedScales;
	private long count;
	
	public List<EnclosedScale> getEnclosedScales() {
		return enclosedScales;
	}
	public void setEnclosedScales(List<EnclosedScale> enclosedScales) {
		this.enclosedScales = enclosedScales;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
	
}
