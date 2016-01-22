package com.xdf.dto;

public class User {
	private int id;
	private String username;
	private String password;
	private String name;
	private String management;
	private String managementCode;
	private int role;
	private String channelName;
	private String channelType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManagement() {
		return management;
	}
	public void setManagement(String management) {
		this.management = management;
	}
	public String getManagementCode() {
		return managementCode;
	}
	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	@Override
	public String toString(){
		String string = "id:" + this.id + "\n" +
				        "username:" + this.username + "\n" +
				        "password:" + this.password + "\n" +
				        "name:" + this.name + "\n" +
				        "mangement:" + this.management + "\n" + 
				        "managementCode:" + this.managementCode + "\n" +
				        "role:" + this.role + "\n" + 
				        "channelName:" + this.channelName;
		return string;
	}
}
