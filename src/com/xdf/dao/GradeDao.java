package com.xdf.dao;

import java.util.List;

import com.xdf.dto.Grade;

/**
 * 年级接口
 * @author 30975
 *
 */
public interface GradeDao {
	
	/**
	 * get grade name by grade number
	 * @param gradeNum
	 * @return grade name
	 */
	public String getGradeByNum(String gradeNum);
	
	/**
	 * get all grade
	 * @return List<Grade>
	 */
	public List<Grade> getAll();
}
