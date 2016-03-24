package com.xdf.dao;

import java.util.List;

import com.xdf.dto.Area;

/**
 * 行政区接口
 * @author 30975
 *
 */
public interface AreaDao {
	/**
	 * get area name by area number
	 * @param areaNum
	 * @return area name(string)
	 */
	public String getAreaByNum(String areaNum);
	
	/**
	 * get all area
	 * @return List<Area>
	 */ 
	public List<Area> getAll();
}
