package com.xdf.action;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.User;

public class Action_UserNameIsExist extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private String username;
	private String result;
	
	public String getUserNameIsExist(){
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getUserByUserName(username);
		int size = userList.size();
		HashMap<String, String> map = new HashMap<String, String>();
		if(size >= 1){
			map.put("result", "isExist");
		}else{
			map.put("result", "notExist");
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
