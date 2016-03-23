package com.xdf.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.User;

public class Action_AddChannelUser extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String channelName;
	private String channelType;
	private String creator;
	private String result;
	
	public String addChannelUser(){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setChannelName(channelName);
		if(Integer.valueOf(channelType) == 1){
			channelType = "数据合作";
		}else if(Integer.valueOf(channelType) == 2){
			channelType = "网络合作";
		}else if(Integer.valueOf(channelType) == 3){
			channelType = "市场推荐";
		}
		user.setChannelType(channelType);
		user.setCreator(creator);
		user.setRole(4);
		UserDao userDao = new UserDaoImpl();
		boolean addResult = userDao.addUser(user);
		Map<String, Object> map = new HashMap<String, Object>();
		if(addResult){
			map.put("result", "success");
		}else{
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
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
