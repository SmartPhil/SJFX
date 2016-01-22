package com.xdf.action;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;

public class Action_AssignEmployee extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String contactTel1;
	private String contactTel2;
	private String employee;
	
	private String result; 
	
	public String assignEmployee(){
		System.out.println(employee);
		if("нч".equals(contactTel1)){
			contactTel1 = "";
		}
		if("нч".equals(contactTel2)){
			contactTel2 = "";
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		boolean updateResult = oppDao.updateEmployee(employee, contactTel1, contactTel2);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(updateResult){
			map.put("result", "success");
		}else{
			map.put("result", "fail");
		}
		result = JSONObject.toJSONString(map);
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
}
