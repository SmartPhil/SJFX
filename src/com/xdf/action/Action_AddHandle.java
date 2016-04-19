package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.FollowContentDao;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.FollowContentDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.FollowContent;
import com.xdf.dto.Opportunity;

@SuppressWarnings("serial")
public class Action_AddHandle extends ActionSupport {
	private String contactTel1;
	private String contactTel2;
	private String followTime;
	private String followContent;
	private String followEmployee;
	private String answer;
	
	private String addResult;
	
	public String addHandle(){
		if("无".equals(contactTel1)){
			contactTel1 = "";
		}
		if("无".equals(contactTel2)){
			contactTel2 = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dtFollowTime = new Date();
		try {
			dtFollowTime = sdf.parse(followTime);
		} catch (ParseException e) {
			System.out.println("转换处理时间出错：" + e.getMessage());
		} 
		//查出当前商机id
		OpportunityDao oppDao = new OpportunityDaoImpl();
		Opportunity opp = oppDao.getOpportunityByContact(contactTel1, contactTel2);
		//更新跟进时间
		opp.setFollowTime(dtFollowTime);
		oppDao.update(opp);
		int oppId = opp.getId();
		
		//添加跟进记录
		HashMap<String, Object> map = new HashMap<String, Object>();
			
		FollowContent fCon = new FollowContent();
		fCon.setOppId(oppId);
		fCon.setTime(dtFollowTime);
		fCon.setContent(followContent);
		fCon.setEmployee(followEmployee);
		if("1".equals(answer)){
			fCon.setAnswer("正常接听");
		}else if ("2".equals(answer)) {
			fCon.setAnswer("无法接通");
		}else if ("3".equals(answer)) {
			fCon.setAnswer("无人接听");
		}else if ("4".equals(answer)) {
			fCon.setAnswer("空号错号");
		}
		FollowContentDao followContentDao = new FollowContentDaoImpl();
		int fConId = followContentDao.insertFollowContent(fCon);
		if(fConId != 0){
			map.put("result", "success");
		}else{
			map.put("result", "fail");
		}
		addResult = JSONObject.toJSONString(map);
		return SUCCESS;
	}

	public String getAddResult() {
		return addResult;
	}
	public void setAddResult(String addResult) {
		this.addResult = addResult;
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
	public String getFollowTime() {
		return followTime;
	}
	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public String getFollowEmployee() {
		return followEmployee;
	}
	public void setFollowEmployee(String followEmployee) {
		this.followEmployee = followEmployee;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
