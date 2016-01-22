package com.xdf.dto;

public class Channel {
	
	private String name;
	private String type;
	private int curtWeekCount;
	private int lastWeekCount;
	private double weekRisePercent;
	private int curtMonthCount;
	private int lastMonthCount;
	private int curtQuarter;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCurtWeekCount() {
		return curtWeekCount;
	}
	public void setCurtWeekCount(int curtWeekCount) {
		this.curtWeekCount = curtWeekCount;
	}
	public int getLastWeekCount() {
		return lastWeekCount;
	}
	public void setLastWeekCount(int lastWeekCount) {
		this.lastWeekCount = lastWeekCount;
	}
	public double getWeekRisePercent() {
		return weekRisePercent;
	}
	public void setWeekRisePercent(double weekRisePercent) {
		this.weekRisePercent = weekRisePercent;
	}
	public int getCurtMonthCount() {
		return curtMonthCount;
	}
	public void setCurtMonthCount(int curtMonthCount) {
		this.curtMonthCount = curtMonthCount;
	}
	public int getLastMonthCount() {
		return lastMonthCount;
	}
	public void setLastMonthCount(int lastMonthCount) {
		this.lastMonthCount = lastMonthCount;
	}
	public int getCurtQuarter() {
		return curtQuarter;
	}
	public void setCurtQuarter(int curtQuarter) {
		this.curtQuarter = curtQuarter;
	}
}
