package com.xdf.dto;

/**
 *	行政区
 * @author 30975
 *
 */
public class Area {
	private int id;
	private String areaNum;  //行政区编码
	private String area;   //行政区名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
