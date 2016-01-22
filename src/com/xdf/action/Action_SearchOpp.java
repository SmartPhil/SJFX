package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Opportunity;

public class Action_SearchOpp extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	public String beginDate;
	public String endDate;
	public String result;
	
	public String searchOpp() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		String managementString = session.get("management").toString();
		String[] managementArr = managementString.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf.parse(beginDate);
		Date end = sdf.parse(endDate);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<Opportunity> oppList = oppDao.searchOpp(begin, end, managementArr);
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String,String>>();
		for (Opportunity opportunity : oppList) {
			HashMap<String, String> map = new HashMap<String, String>();
			if(opportunity.getParentName() == null || "".equals(opportunity.getParentName())){
				opportunity.setParentName("无");
			}
			if(opportunity.getContactTel2() == null || "".equals(opportunity.getContactTel2())){
				opportunity.setContactTel2("无");
			}
			if(opportunity.getNoValidReason() == null || "".equals(opportunity.getNoValidReason())){
				opportunity.setNoValidReason("无");
			}
			if(opportunity.getAssignEmployee() == null || "".equals(opportunity.getAssignEmployee())){
				opportunity.setAssignEmployee("无");
			}
			map.put("name", opportunity.getStuName());
			map.put("parentName", opportunity.getParentName());
			map.put("contactTel1", opportunity.getContactTel1());
			map.put("contactTel2", opportunity.getContactTel2());
			map.put("channelName", opportunity.getChannelName());
			String createDate;
			if(opportunity.getCreateDate() != null && !"".equals(opportunity.getCreateDate())){
				createDate = sdf2.format(opportunity.getCreateDate());
			}else {
				createDate = "";
			}
			map.put("createDate", createDate);
			map.put("needCls", opportunity.getNeedCls());
			map.put("management", opportunity.getManagement());
			if(opportunity.getIsValid() == 0){
				map.put("isValid", "无效");
			}else if(opportunity.getIsValid() == 1){
				map.put("isValid", "有效");
			}
			map.put("noValidReason", opportunity.getNoValidReason());
			if(opportunity.getIsAssign() == 0){
				map.put("isAssign", "未分配");
			}else if(opportunity.getIsAssign() == 1){
				map.put("isAssign", "已分配");
			}
			map.put("assignEmployee", opportunity.getAssignEmployee());
			map.put("channelType", opportunity.getChannelType());
			//String followTime = "";
			//String nextFollowTime = "";
			/*if(opportunity.getFollowTime() != null && !"".equals(opportunity.getFollowTime())){
				followTime = sdf2.format(opportunity.getFollowTime()); 
			}else {
				followTime = "";
			}
			if(opportunity.getNextFollowTime() != null && !"".equals(opportunity.getNextFollowTime())){
				nextFollowTime = sdf2.format(opportunity.getNextFollowTime());
			}else {
				nextFollowTime = "";
			}*/
			//map.put("followTime", followTime);
			//map.put("nextFollowTime", nextFollowTime);
			//map.put("state", String.valueOf(opportunity.getState()));
			mapList.add(map);
		}
		result = JSONArray.toJSONString(mapList);
		return SUCCESS;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
