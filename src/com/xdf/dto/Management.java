package com.xdf.dto;

import java.io.Serializable;

public class Management implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String managementName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getManagementName() {
		return managementName;
	}
	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}
}
