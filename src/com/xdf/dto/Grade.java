package com.xdf.dto;

/**
 * 年级
 * @author 30975
 *
 */
public class Grade {
	private int id;
	private String gradeNum; //年级编码
	private String grade; //年级名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGradeNum() {
		return gradeNum;
	}
	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
