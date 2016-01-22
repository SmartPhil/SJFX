package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

@SuppressWarnings("serial")
public class Action_GetOppDealInfoByChannel extends ActionSupport {
	private String username;
	private String begin;
	private String end;
	private String stuContactTel;
	private String isCurtMonFlag;
	private String result;
	
	public String getOppDealInfoByChannel(){
		//获取当前用户的channelName
		UserDao userDao = new UserDaoImpl();
		OpportunityDao oppDao = new OpportunityDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("".equals(username) || username == null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", "访问出错！请先登录！");
			map.put("oppInfo", "");
			result = JSONObject.toJSONString(map);
		}else {
			if("".equals(stuContactTel) || stuContactTel == null){
				User user = userDao.getUserByUserName(username).get(0);
				String channelName = user.getChannelName();
				Date beginDate = new Date();
				Date endDate = new Date();
				
				if("true".equals(isCurtMonFlag)){
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
					beginDate = beginCalendar.getTime();
					try {
						beginDate = sdf.parse(sdf.format(beginDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.add(Calendar.MONTH, 1);
					endCalendar.set(Calendar.DAY_OF_MONTH, 1);
					endCalendar.add(Calendar.DAY_OF_MONTH, -1);
					endDate = endCalendar.getTime();
					try {
						endDate = sdf.parse(sdf.format(endDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else if("false".equals(isCurtMonFlag)){
					if("".equals(begin) || begin == null){
						beginDate = null;
					}else {
						try {
							beginDate = sdf.parse(begin);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if("".equals(end) || end == null){
						endDate = null;
					}else {
						try {
							endDate = sdf.parse(end);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				List<Opportunity> oppList = oppDao.getOppByChannelAndDate(channelName, beginDate, endDate);
				List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
				for (Opportunity opportunity : oppList) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("createTime", sdf1.format(opportunity.getCreateDate()));
					if("".equals(opportunity.getStuName()) || opportunity.getStuName() == null){
						map.put("stuName", "无");
					}else {
						map.put("stuName", opportunity.getStuName());
					}
					if("".equals(opportunity.getParentName()) || opportunity.getParentName() == null){
						map.put("parentName", "无");
					}else {
						map.put("parentName", opportunity.getParentName());
					}
					map.put("contactTel1", opportunity.getContactTel1());
					if("".equals(opportunity.getContactTel2()) || opportunity.getContactTel2() == null){
						map.put("contactTel2", "无");
					}else {
						map.put("contactTel2", opportunity.getContactTel2());
					}
					map.put("channelName", opportunity.getChannelName());
					map.put("channelType", opportunity.getChannelType());
					if("".equals(opportunity.getNeedCls()) || opportunity.getNeedCls() == null){
						map.put("needCls", "无");
					}else {
						map.put("needCls", opportunity.getNeedCls());
					}
					map.put("management", opportunity.getManagement());
					if(opportunity.getState() == 0){
						map.put("state", "待跟进");
					}else if(opportunity.getState() == 1){
						map.put("state", "已成单");
					}
					maps.add(map);
				}
				String oppInfo = JSONArray.toJSONString(maps);
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", "success");
				resultMap.put("oppInfo", oppInfo);
				result = JSONObject.toJSONString(resultMap);
			}else {
				//如果学员电话不为空！则按学员电话进行查询
				List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
				Opportunity opportunity = oppDao.getOppByContact(stuContactTel);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("createTime", sdf1.format(opportunity.getCreateDate()));
				if("".equals(opportunity.getStuName()) || opportunity.getStuName() == null){
					map.put("stuName", "无");
				}else {
					map.put("stuName", opportunity.getStuName());
				}
				if("".equals(opportunity.getParentName()) || opportunity.getParentName() == null){
					map.put("parentName", "无");
				}else {
					map.put("parentName", opportunity.getParentName());
				}
				map.put("contactTel1", opportunity.getContactTel1());
				if("".equals(opportunity.getContactTel2()) || opportunity.getContactTel2() == null){
					map.put("contactTel2", "无");
				}else {
					map.put("contactTel2", opportunity.getContactTel2());
				}
				map.put("channelName", opportunity.getChannelName());
				map.put("channelType", opportunity.getChannelType());
				if("".equals(opportunity.getNeedCls()) || opportunity.getNeedCls() == null){
					map.put("needCls", "无");
				}else {
					map.put("needCls", opportunity.getNeedCls());
				}
				map.put("management", opportunity.getManagement());
				if(opportunity.getState() == 0){
					map.put("state", "待跟进");
				}else if(opportunity.getState() == 1){
					map.put("state", "已成单");
				}
				maps.add(map);
				String oppInfo = JSONArray.toJSONString(maps);
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", "success");
				resultMap.put("oppInfo", oppInfo);
				result = JSONObject.toJSONString(resultMap);
			}
		}
		return SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getStuContactTel() {
		return stuContactTel;
	}
	public void setStuContactTel(String stuContactTel) {
		this.stuContactTel = stuContactTel;
	}
	public String getIsCurtMonFlag() {
		return isCurtMonFlag;
	}
	public void setIsCurtMonFlag(String isCurtMonFlag) {
		this.isCurtMonFlag = isCurtMonFlag;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
