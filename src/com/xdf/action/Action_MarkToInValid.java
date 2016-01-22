package com.xdf.action;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;

public class Action_MarkToInValid extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String contactTel1;
	private String contactTel2;
	private String invalidReason;
	private String result;
	
	public String mark(){
		if("��".equals(contactTel2)){
			contactTel2 = "";
		}
		if("0".equals(invalidReason)){
			invalidReason = "�¶���ѧԱ";
		}else if("1".equals(invalidReason)){
			invalidReason = "����������";
		}else if("2".equals(invalidReason)){
			invalidReason = "���䲻����";
		}else if("3".equals(invalidReason)){
			invalidReason = "��ѧϰ����";
		}else if("4".equals(invalidReason)){
			invalidReason = "�պŴ��";
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		boolean markResult = oppDao.markToInvalid(contactTel1, contactTel2, invalidReason);
		HashMap<String, String> map = new HashMap<String, String>();
		if(markResult){
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

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
