package com.xdf.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class Action_GetCurrentMonthOpp extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String result;
	
	public String getCurrentMonthOpp(){
		Map<String,Object> session = ActionContext.getContext().getSession();
		String managementString = session.get("management").toString();
		String[] managementArray = managementString.split(",");
		
		OpportunityDao oppDao = new OpportunityDaoImpl();
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = beginCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			beginDate = sdf.parse(sdf.format(beginDate).split(" ")[0] + " 00:00:00");
		} catch (Exception e) {
			System.out.println("��ȡ��������-ת����ʼʱ��ʧ�ܣ�" + e.getMessage());
		}
		Calendar endCalendar = Calendar.getInstance();
		Date endDate = endCalendar.getTime();
		List<Opportunity> oppList = oppDao.searchOpp(beginDate, endDate, managementArray);
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Opportunity opportunity : oppList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("stuName", opportunity.getStuName());
			if(opportunity.getParentName() != null && !"".equals(opportunity.getParentName())){
				map.put("parentName", opportunity.getParentName());
			}else {
				map.put("parentName", "��");
			}
			map.put("contactTel1", opportunity.getContactTel1());
			if(opportunity.getContactTel1() != null && !"".equals(opportunity.getContactTel1())){
				map.put("contactTel2", opportunity.getContactTel2());
			}else {
				map.put("contactTel2", "��");
			}
			map.put("channelName", opportunity.getChannelName());
			map.put("channelType", opportunity.getChannelType());
			map.put("createDate", sdf.format(opportunity.getCreateDate()));
			map.put("needCls", opportunity.getNeedCls());
			map.put("management", opportunity.getManagement());
			if(opportunity.getIsValid() == 0){
				map.put("isValid", "��Ч");
			}else if(opportunity.getIsValid() == 1){
				map.put("isValid", "��Ч");
			}
			if(opportunity.getNoValidReason() != null && !"".equals(opportunity.getNoValidReason())){
				map.put("noValidReason", opportunity.getNoValidReason());
			}else {
				map.put("noValidReason", "��");
			}
			if(opportunity.getIsAssign() == 0){
				map.put("isAssign", "δ����");
			}else if(opportunity.getIsAssign() == 1) {
				map.put("isAssign", "�ѷ���");
			}
			if(opportunity.getAssignEmployee() != null && !"".equals(opportunity.getAssignEmployee())){
				map.put("assignEmployee", opportunity.getAssignEmployee());
			}else {
				map.put("assignEmployee", "��");
			}
			maps.add(map);
		}
		result = JSONArray.toJSONString(maps);
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
