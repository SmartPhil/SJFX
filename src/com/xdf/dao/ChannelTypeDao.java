package com.xdf.dao;

import java.util.List;

import com.xdf.dto.ChannelType;

/**
 * 渠道商类型接口
 * @author 30975
 *
 */
public interface ChannelTypeDao {
	/**
	 * 获取所有渠道商类型
	 * @return List<ChannelType>
	 */
	public List<ChannelType> getAllChannelType();
}
