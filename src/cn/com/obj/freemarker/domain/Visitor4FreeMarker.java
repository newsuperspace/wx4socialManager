package cn.com.obj.freemarker.domain;

import java.io.Serializable;

public class Visitor4FreeMarker implements Serializable {

	private String username;
	private String sex;
	private String startTimeStr;
	private String endTimeStr;
	private int score;
	private long workTime;
	
	private Signin4FreeMarker signin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getWorkTime() {
		return workTime;
	}

	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}

	public Signin4FreeMarker getSignin() {
		return signin;
	}

	public void setSignin(Signin4FreeMarker signin) {
		this.signin = signin;
	}
	
	
	
}
