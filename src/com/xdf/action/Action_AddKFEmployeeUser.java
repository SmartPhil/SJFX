package com.xdf.action;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.User;

public class Action_AddKFEmployeeUser extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String name;
	private String management;
	private String result;
	
	public String addKFEmployeeUser(){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(3);
		user.setManagement(management);
		user.setName(name);
		UserDao userDao = new UserDaoImpl();
		boolean addResult = userDao.addUser(user);
		HashMap<String, String> map = new HashMap<String, String>();
		if(addResult){
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		result = JSON.toJSONString(map);
		return SUCCESS;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
