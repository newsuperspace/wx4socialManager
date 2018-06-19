package cc.natapp4.ddaig.bean;

import java.io.Serializable;

public class SignBean implements Serializable {

	// 微信加密签名
    public String signature ;
    // 时间戳
    public String timestamp;
    // 随机数
    public String nonce;
    // 随机字符串
    public String echostr;
    // 来访用户的openid
    public String openid;
    
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
    
    
}
