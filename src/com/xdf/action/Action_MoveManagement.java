package com.xdf.action;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;

@SuppressWarnings("serial")
public class Action_MoveManagement extends ActionSupport{
	private String contactTel1;
	private String contactTel2;
	private String management;
	
	private String updateResult;
	
	public String moveManagement(){
		if("нч".equals(contactTel1)){
			contactTel1 = "";
		}
		if("нч".equals(contactTel2)){
			contactTel2 = "";
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		boolean result = oppDao.updateManagement(management, contactTel1, contactTel2);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result){
			map.put("result", "success");
		}else{
			map.put("result", "fail");
		}
		updateResult = JSONObject.toJSONString(map);
		return SUCCESS;
	}

	public String getContactTel1() {
		return contactTel1;
	}

	public void setContactTel1(String contactTel1) {
		this.contactTel1 = contactTel1;
	}

	public String getContactTel2() {
		return contactTel2;
	}

	public void setContactTel2(String contactTel2) {
		this.contactTel2 = contactTel2;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}
}
