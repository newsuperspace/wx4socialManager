package cc.natapp4.ddaig.bean.health;

import java.io.Serializable;

public class Option4Opgroup4CreateEnclosedScale implements Serializable {

	/**
	 *  版本号
	 */
	private static final long serialVersionUID = 1L;

	public Option4Opgroup4CreateEnclosedScale() {
	}

//	"order": 1,
//    "opid": "dofud12sof7c987v9w3or2",
//    "content": "符合",
//    "score": 1
	
	private int order;
	private String opid;
	private String content;
	private int score;

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Option4Opgroup4CreateEnclosedScale [order=" + order + ", opid=" + opid + ", content=" + content
				+ ", score=" + score + "]";
	}

	
	
	
}
