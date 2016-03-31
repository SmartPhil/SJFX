package com.xdf.dao;

import java.util.List;

import com.xdf.dto.User;

public interface UserDao {
	
	/**
	 * get user by username and password
	 * @param username
	 * @param password
	 * @return user
	 */
	public User getUserByNameAndPsw(String username,String password);
	
	/**
	 * get user by management
	 * @param managements
	 * @return List<User>
	 */
	public List<User> getUserByManagement(String[] managements);
	
	/**
	 * get user which is channel
	 * @return List<User>
	 */
	public List<User> getChannelUser();
	
	/**
	 * add channel user
	 * @return add result
	 */
	public boolean addUser(User user);
	
	/**
	 * get users by user name
	 * @param username
	 * @return List<User>
	 */
	public List<User> getUserByUserName(String username);
	
	/**
	 * 获取所有用户
	 * @return List<User>
	 */
	public List<User> getAllUser();
	
	/**
	 * 通过渠道商创建者查询渠道商
	 * @return List<User>
	 */
	public List<User> getUserByCreator(String creator);
}
