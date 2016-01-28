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

public class Action_SearchOpp extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String user;
	private String beginDate;
	private String endDate;
	private String stuContact;
	public String result;
	
	public String searchOpp(){
		//��ȡ��ǰ�û�������Ĳ����б�
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getUserByUserName(user);
		User curtUser = new User();
		if(userList.size() > 0){
			curtUser = userList.get(0);
		}
		String managementString = curtUser.getManagement();
		String[] managementArr = managementString.split(",");
		//ת��ʱ�䣨1,��Ϊ�գ����ѯ����;2��ʼΪ�գ�������Ϊ�գ���ѯС�ڽ���ʱ��������̻�;
		//3,��ʼ��Ϊ�գ�����Ϊ�գ���ѯ������ʼ�������̻�;4������Ϊ�գ����ѯ������ʼʱ�䣬С�ڽ���ʱ��������̻���
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
				System.out.println("SearchOppAction:ת��ʱ�����:" + e.getMessage());
			}
		}
		if("".equals(endDate) || endDate == null){
			endDate = null;
		}else {
			try {
				end = sdf.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("SearchOppAction:ת��ʱ�����:" + e.getMessage());
			}
		}
		//��ȡ���������������̻�
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<Opportunity> oppList = oppDao.searchOpp(begin, end, managementArr);
		List<Opportunity> oppList2 = new ArrayList<Opportunity>();
		if(!"".equals(stuContact) && stuContact != null){
			for (Opportunity opportunity : oppList) {
				if(stuContact.equals(opportunity.getContactTel1()) || stuContact.equals(opportunity.getContactTel2())){
					oppList2.add(opportunity);
				}
			}
			oppList = oppList2;
		}
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String,String>>();
		for (Opportunity opportunity : oppList) {
			HashMap<String, String> map = new HashMap<String, String>();
			if(opportunity.getParentName() == null || "".equals(opportunity.getParentName())){
				opportunity.setParentName("��");
			}
			if(opportunity.getContactTel2() == null || "".equals(opportunity.getContactTel2())){
				opportunity.setContactTel2("��");
			}
			if(opportunity.getNoValidReason() == null || "".equals(opportunity.getNoValidReason())){
				opportunity.setNoValidReason("��");
			}
			if(opportunity.getAssignEmployee() == null || "".equals(opportunity.getAssignEmployee())){
				opportunity.setAssignEmployee("��");
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
				map.put("isValid", "��Ч");
			}else if(opportunity.getIsValid() == 1){
				map.put("isValid", "��Ч");
			}
			map.put("noValidReason", opportunity.getNoValidReason());
			if(opportunity.getIsAssign() == 0){
				map.put("isAssign", "δ����");
			}else if(opportunity.getIsAssign() == 1){
				map.put("isAssign", "�ѷ���");
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
