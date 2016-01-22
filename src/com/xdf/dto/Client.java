package com.xdf.dto;

import java.util.Date;

public class Client {
	private int id;
	private int oppId;
	private Date followTime;
	private Date nextFollowTime;
	private int state; // 0--待跟进；1--已成单
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOppId() {
		return oppId;
	}
	public void setOppId(int oppId) {
		this.oppId = oppId;
	}
	public Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
	public Date getNextFollowTime() {
		return nextFollowTime;
	}
	public void setNextFollowTime(Date nextFollowTime) {
		this.nextFollowTime = nextFollowTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
