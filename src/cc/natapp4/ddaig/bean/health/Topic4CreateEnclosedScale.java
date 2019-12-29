package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;

public class Topic4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	public Topic4CreateEnclosedScale() {
	}

//	 "order": 1,
//     "tid": "sjdoifuc9x87vh32u934",
//     "content": "您最近的睡眠质量还不错？",
//     "keyword": "睡眠",
//     "factor_order": 1,
//     "opGroup_order": 1
	
	private int order;
	private String tid;
	private String content;
	private String keyword;
	private int factor_order;
	private int opGroup_order;

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getFactor_order() {
		return factor_order;
	}
	public void setFactor_order(int factor_order) {
		this.factor_order = factor_order;
	}
	public int getOpGroup_order() {
		return opGroup_order;
	}
	public void setOpGroup_order(int opGroup_order) {
		this.opGroup_order = opGroup_order;
	}
	
	@Override
	public String toString() {
		return "Topic4CreateEnclosedScale [order=" + order + ", tid=" + tid + ", content=" + content + ", keyword="
				+ keyword + ", factor_order=" + factor_order + ", opGroup_order=" + opGroup_order + "]";
	}
	

	
}
