package com.xdf.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.AreaDao;
import com.xdf.dao.GradeDao;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.AreaDaoImpl;
import com.xdf.dao.impl.GradeDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.util.HttpPostUtil;

public class Action_AddOpportunity extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String stuName;
	private String parentName;
	private String contactTel1;
	private String contactTel2;
	private String consultCls;
	private String kfManagement;
	private String giveOrg;
	private String channelType;
	private String area;
	private String address;
	private String school;
	private String grade;
	
	private String result;
	
	private String url = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetOldStudent&signKey=562KThrcprmdqm8Tll1p9A==";
	
	public String addOpportunity(){
		OpportunityDao opportunityDao = new OpportunityDaoImpl();
		
		boolean hasResult1 = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Opportunity opportunity = opportunityDao.getOpportunityByContact(contactTel1, contactTel2);
		if(opportunity != null){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, -6);
			System.out.println(sdf.format(now.getTime()));
			
			Date oppCreateTime = opportunity.getCreateDate();
			long timeDiff = now.getTimeInMillis() - oppCreateTime.getTime();
			if(timeDiff < 0){
				hasResult1 = true;
			}else {
				hasResult1 = false;
			}
		}
		
		//判断此学生是否是半年以内的老生
		boolean hasResult2 = false;
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("sMobile1", contactTel1);
		map1.put("sMobile2", contactTel2);
		String map1jsonString = JSONObject.toJSONString(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("nSchoolId", 2);
		map2.put("OrderJson", map1jsonString);
		String paramJson = JSONObject.toJSONString(map2);
		
		Date now = new Date();
		String Timestamp = String.valueOf(now.getTime());
		url += "&Timestamp" + Timestamp;
		String param = "paramJson=" + paramJson;
		
		HttpPostUtil httpPostUtil = new HttpPostUtil();
		String postResult = "";
		try {
			postResult = httpPostUtil.doPost(url, param);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject object = JSONObject.parseObject(postResult);
		String invocationResult = object.getString("Status");
		String responseResult = object.getString("ResponseData");
		System.out.println(invocationResult + "----" + responseResult);
		if("1".equals(invocationResult) && "true".equals(responseResult)){
			hasResult2 = true;
		}else if("1".equals(invocationResult) && "false".equals(responseResult)){
			hasResult2 = false;
		}
		
		if(hasResult1 || hasResult2){
			//判断此商机是否是半年以内提交！
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("result", "fail");
			map.put("failReason","haveSame");
			result = JSONObject.toJSONString(map);
		}else{
			Opportunity opp = new Opportunity();
			opp.setStuName(stuName);
			opp.setParentName(parentName);
			opp.setContactTel1(contactTel1);
			opp.setContactTel2(contactTel2);
			opp.setNeedCls(consultCls);
			opp.setManagement(kfManagement);
			opp.setChannelName(giveOrg);
			opp.setChannelType(channelType);
			opp.setCreateDate(new Date());
			opp.setIsValid(1);
			opp.setIsAssign(0);
			opp.setState(0);
			opp.setMark(1);
			AreaDao areaDao = new AreaDaoImpl();
			if("".equals(area) || area == null){
				area = "";
			} else {
				area = areaDao.getAreaByNum(area);
			}
			opp.setArea(area);
			opp.setAddress(address);
			opp.setSchool(school);
			GradeDao gradeDao = new GradeDaoImpl();
			if("".equals(grade) || grade == null){
				grade = "";
			}else {
				grade = gradeDao.getGradeByNum(grade);
			}
			opp.setGrade(grade);
			boolean saveResult = opportunityDao.insertOpportunity(opp);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(saveResult){
				map.put("result", "success");
			}else {
				map.put("result", "fail");
			}
			result = JSONObject.toJSONString(map);
		}
		return SUCCESS;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getConsultCls() {
		return consultCls;
	}

	public void setConsultCls(String consultCls) {
		this.consultCls = consultCls;
	}

	public String getKfManagement() {
		return kfManagement;
	}

	public void setKfManagement(String kfManagement) {
		this.kfManagement = kfManagement;
	}

	public String getGiveOrg() {
		return giveOrg;
	}

	public void setGiveOrg(String giveOrg) {
		this.giveOrg = giveOrg;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
