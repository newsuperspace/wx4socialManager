package cc.natapp4.ddaig.bean;

import java.io.Serializable;


/**
 * userAction_batchCreateUser.action 的AJAX前端响应之用
 * 
 * @author Administrator
 *
 */
public class Info4BatchCreateUserResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int totalNum;   // 遍历的数据总量
	private int successNum;  // 本次批量操作成功创建的用户数量
	private int failedNum;    // 本次批量操作创建失败的用户数量
	
	private String message;    // 存放需要向前端返回的关键信息，通常是异常警告
	private boolean result;   // 只要有一个用户被成功创建这里就是true，否则为false
	
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailedNum() {
		return failedNum;
	}
	public void setFailedNum(int failedNum) {
		this.failedNum = failedNum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}
