package com.xdf.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.FollowContentDao;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.FollowContentDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.FollowContent;
import com.xdf.dto.Opportunity;

public class Action_GetFollowContent extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String contactTel1;
	private String contactTel2;
	private String result;
	
	public String getFollowCon(){
		//获得商机id
		if("无".equals(contactTel1)){
			contactTel1 = "";
		}
		if("无".equals(contactTel2)){
			contactTel2 = "";
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		Opportunity opportunity = oppDao.getOpportunityByContact(contactTel1, contactTel2);
		int id = opportunity.getId();
		FollowContentDao fConDao = new FollowContentDaoImpl();
		List<FollowContent> followContentList = fConDao.getFollowContentById(id);
		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (FollowContent fCon : followContentList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("followTime", sdf.format(fCon.getTime()));
			map.put("followContent", fCon.getContent());
			map.put("followEmployee", fCon.getEmployee());
			map.put("answer", fCon.getAnswer());
			mapList.add(map);
		}
		result = JSONArray.toJSONString(mapList);
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
}
