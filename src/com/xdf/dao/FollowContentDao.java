package com.xdf.dao;

import java.util.List;

import com.xdf.dto.FollowContent;

public interface FollowContentDao {
	/**
	 * insert a new follow content into data base
	 * @param followContent
	 * @return insert result(boolean)
	 */
	public Integer insertFollowContent(FollowContent followContent);
	
	/**
	 * get follow contents by opportunity id 
	 * @param id
	 * @return List<FollowContent>
	 */
	public List<FollowContent> getFollowContentById(int id);
}
