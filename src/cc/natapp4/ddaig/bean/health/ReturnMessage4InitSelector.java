package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;

public class ReturnMessage4InitSelector implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	private List<Bean4InitSelector> dataSource;
	private List<String> defaultValue;				// 前端的picker-extend组建用于回显各个wheel的选中位置，形如 [0、0、0、0、1、2]
	
	
	public List<Bean4InitSelector> getDataSource() {
		return dataSource;
	}
	
	/**
	 * 用于前端基于majunchang/picker-extend 实现多级联动picker显示的数据源
	 * https://github.com/majunchang/picker-extend#readme
	 * 
	 * @param dataSource
	 */
	public void setDataSource(List<Bean4InitSelector> dataSource) {
		this.dataSource = dataSource;
	}
	public List<String> getDefaultValue() {
		return defaultValue;
	}
	/**
	 * 如果有需要回显选择的位置，可在这里通过给出形如
	 * [0、0、0、0、0、0、]的数组选定各级滚轮的位置
	 * 
	 * @param defaultValue
	 */
	public void setDefaultValue(List<String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	
	
}
