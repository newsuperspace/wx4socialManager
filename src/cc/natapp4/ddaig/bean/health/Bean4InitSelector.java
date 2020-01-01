package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;
import java.util.List;


/**
 * HealthAction → initSelector() 中用于向前端（health/users.jsp）的initSelector返回数据
 * 用于基于weui的层级过滤器（picker）的数据源
 * 
 * @author Administrator
 *
 */
public class Bean4InitSelector implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	private String label;
	private String value;
	private List<Bean4InitSelector> children;
	
	
	public Bean4InitSelector() {
	}

	public Bean4InitSelector(String label, String value, List<Bean4InitSelector> children) {
		super();
		this.label = label;
		this.value = value;
		this.children = children;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Bean4InitSelector> getChildren() {
		return children;
	}
	public void setChildren(List<Bean4InitSelector> children) {
		this.children = children;
	}
	
	
	
	
}
