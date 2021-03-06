package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

public class Action_SearchOppBySC extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String user;
	private String beginDate;
	private String endDate;
	private String stuContact;
	public String result;
	
	public String searchOpp(){
		//获取当前用户及当前用户所创建的渠道商
		UserDao userDao = new UserDaoImpl();
		List<User> channelList = userDao.getUserByCreator(user);
		List<String> channelNames = new ArrayList<String>();
		for (User user : channelList) {
			channelNames.add(user.getChannelName());
		}
		//转换时间（1,都为空，则查询所有;2起始为空，结束不为空，查询小于结束时间的所有商机;
		//3,起始不为空，结束为空，查询大于起始的所有商机;4，都不为空，则查询大于起始时间，小于结束时间的所有商机）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = new Date();
		Date end = new Date();
		if("".equals(beginDate) || beginDate == null){
			begin = null;
		}else {
			try {
				begin = sdf.parse(beginDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("SearchOppAction:转换时间出错:" + e.getMessage());
			}
		}
		if("".equals(endDate) || endDate == null){
			end = null;
		}else {
			try {
				end = sdf.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("SearchOppAction:转换时间出错:" + e.getMessage());
			}
		}
		//获取满足条件的所有商机
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OpportunityDao oppDao = new OpportunityDaoImpl();
		//获取当前市场人员上传的商机
		List<Opportunity> oppList0 = new ArrayList<Opportunity>();
		if(channelList.size() > 0){
			oppList0 = oppDao.getOppByChannelListAndDate(channelNames, begin, end);
		}
		//获取当前市场人员所创建的渠道商提交的商机
		List<Opportunity> oppList1 = oppDao.getOppByChannelAndDate(user, begin, end);
		//链接两个商机列表
		oppList0.addAll(oppList1);
		List<Opportunity> oppList = oppList0;
		
		//按照学员联系方式进行排查
		List<Opportunity> oppList2 = new ArrayList<Opportunity>();
		if(!"".equals(stuContact) && stuContact != null){
			for (Opportunity opportunity : oppList) {
				if(stuContact.equals(opportunity.getContactTel1()) || stuContact.equals(opportunity.getContactTel2())){
					oppList2.add(opportunity);
				}
			}
			oppList = oppList2;
		}
		//转换成json数据
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String,String>>();
		for (Opportunity opportunity : oppList) {
			HashMap<String, String> map = new HashMap<String, String>();
			if(opportunity.getStuName() == null || "".equals(opportunity.getStuName())){
				opportunity.setStuName("无");
			}
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
			map.put("stuName", opportunity.getStuName());
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
			if(opportunity.getMark() == 0){
				map.put("isValid", "有效性验证中");
			}else if(opportunity.getMark() == 1){
				if(opportunity.getIsValid() == 0){
					map.put("isValid", "无效");
				}else if(opportunity.getIsValid() == 1){
					map.put("isValid", "有效");
				}
			}
			map.put("noValidReason", opportunity.getNoValidReason());
			if(opportunity.getIsAssign() == 0){
				map.put("isAssign", "未分配");
			}else if(opportunity.getIsAssign() == 1){
				map.put("isAssign", "已分配");
			}
			map.put("assignEmployee", opportunity.getAssignEmployee());
			map.put("channelType", opportunity.getChannelType());
			mapList.add(map);
		}
		result = JSONArray.toJSONString(mapList);
		return SUCCESS;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
	public String getStuContact() {
		return stuContact;
	}
	public void setStuContact(String stuContact) {
		this.stuContact = stuContact;
	}
}
