package com.xdf.util;

import java.util.Calendar;
import java.util.Date;

public class WeekUtil {
	private Date monday;
	private Date tuesday;
	private Date wednesday;
	private Date thursday;
	private Date friday;
	private Date saturday;
	private Date sunday;
	
	public Date getMonday() {
		Calendar now = Calendar.getInstance();
		if(now.get(Calendar.DAY_OF_WEEK) == 1){
			now.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		return monday;
	}
	public void setMonday(Date monday) {
		this.monday = monday;
	}
	public Date getTuesday() {
		return tuesday;
	}
	public void setTuesday(Date tuesday) {
		this.tuesday = tuesday;
	}
	public Date getWednesday() {
		return wednesday;
	}
	public void setWednesday(Date wednesday) {
		this.wednesday = wednesday;
	}
	public Date getThursday() {
		return thursday;
	}
	public void setThursday(Date thursday) {
		this.thursday = thursday;
	}
	public Date getFriday() {
		return friday;
	}
	public void setFriday(Date friday) {
		this.friday = friday;
	}
	public Date getSaturday() {
		return saturday;
	}
	public void setSaturday(Date saturday) {
		this.saturday = saturday;
	}
	public Date getSunday() {
		return sunday;
	}
	public void setSunday(Date sunday) {
		this.sunday = sunday;
	}
}
