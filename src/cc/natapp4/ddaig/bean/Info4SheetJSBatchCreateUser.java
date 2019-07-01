package cc.natapp4.ddaig.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 专门用于在batch4CreateUser.jsp页面中上传EXCEL文档后，通过SheetJS将文档数据解析成JSON字符串通过Ajax
 * 传回到服务器后，服务器用本类存放校验结果的信息的javaBean
 * @author Administrator
 *
 */
public class Info4SheetJSBatchCreateUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 只要在userAction.batchCreate()中监测出异常字段（entrySet的key不符合“姓名”、“性别”、“年龄”、“电话”中的任意一个）就直接设置result为false
	 * 前端会首先检测该字段，如果发现结果是false，则直接显示message字段的提示信息
	 */
	private boolean result;
	/*
	 * 当result=fasle时，存放错误信息之用
	 */
	private String message;
	
	// 正确数据的数量
	private int normalNum = 0;
	// 无效数据（有缺失字段）的数量
	private int invaliableNum = 0;
	// 已存在用户的数据数量
	private int existNum =0;
	
	// 存放每条数据的校验结果，用作前端展示
	private List<SheetJS4BatchCreateUser> list;

	
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(int normalNum) {
		this.normalNum = normalNum;
	}

	public int getInvaliableNum() {
		return invaliableNum;
	}

	public void setInvaliableNum(int invaliableNum) {
		this.invaliableNum = invaliableNum;
	}

	public int getExistNum() {
		return existNum;
	}

	public void setExistNum(int existNum) {
		this.existNum = existNum;
	}

	public List<SheetJS4BatchCreateUser> getList() {
		return list;
	}

	public void setList(List<SheetJS4BatchCreateUser> list) {
		this.list = list;
	}
	
}
