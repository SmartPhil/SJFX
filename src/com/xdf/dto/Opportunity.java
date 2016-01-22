package com.xdf.dto;

import java.util.Date;

public class Opportunity {
	
	private int id;
	private String stuName;
	private String parentName;
	private String contactTel1;
	private String contactTel2;
	private String channelName;
	private String channelType;
	private Date createDate;
	private String needCls;
	private String management;
	private int isValid;  // 0-无效；1-有效；新增商机默认有效
	private String noValidReason;
	private int isAssign; // 0-未分配；1-已分配；新增商机默认未分配
	private String assignEmployee;
	private Date followTime;
	private Date nextFollowTime;
	private int state; // 0--待跟进；1--已成单
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getContactTel1() {
		return contactTel1;
	}
	public void setContactTel1(String contactTel1) {
		this.contactTel1 = contactTel1;
	}
	public String getContactTel2() {
		return contactTel2;
	}
	public void setContactTel2(String contactTel2) {
		this.contactTel2 = contactTel2;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getNeedCls() {
		return needCls;
	}
	public void setNeedCls(String needCls) {
		this.needCls = needCls;
	}
	public String getManagement() {
		return management;
	}
	public void setManagement(String management) {
		this.management = management;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public String getNoValidReason() {
		return noValidReason;
	}
	public void setNoValidReason(String noValidReason) {
		this.noValidReason = noValidReason;
	}
	public int getIsAssign() {
		return isAssign;
	}
	public void setIsAssign(int isAssign) {
		this.isAssign = isAssign;
	}
	public String getAssignEmployee() {
		return assignEmployee;
	}
	public void setAssignEmployee(String assignEmployee) {
		this.assignEmployee = assignEmployee;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
}
