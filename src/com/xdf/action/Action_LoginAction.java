package com.xdf.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.ManagementDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.ManagementDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Management;
import com.xdf.dto.User;

public class Action_LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	private String returnInfo;
	
	private Map<String, Object> session;
	
	public String login(){
		//判定该用户是否存在，若存在取回该用户的详细信息
		UserDao userDao = new UserDaoImpl();
		User curtUser = userDao.getUserByNameAndPsw(username, password);
		ManagementDao managementDao = new ManagementDaoImpl();
		List<Management> managementList = managementDao.getManagementAll();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(curtUser == null){
			map.put("loginResult", "fail");
		}else {
			map.put("loginResult", "success");
			map.put("role", curtUser.getRole());
			session = ActionContext.getContext().getSession();
			session.put("username", curtUser.getUsername());
			session.put("management", curtUser.getManagement());
			session.put("managementList", managementList);
			if(curtUser.getRole() == 4 || curtUser.getRole() == 5){
				session.put("channelName", curtUser.getChannelName());
				session.put("channelType", curtUser.getChannelType());
			}
		}
		returnInfo = com.alibaba.fastjson.JSON.toJSONString(map);
		System.out.println(returnInfo);
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

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
}
