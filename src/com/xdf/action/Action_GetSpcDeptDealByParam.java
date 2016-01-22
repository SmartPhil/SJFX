package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.DealDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.User;

@SuppressWarnings("serial")
public class Action_GetSpcDeptDealByParam extends ActionSupport {
	private String begin;
	private String end;
	private String contactTel;
	private String userName;
	private String result;
	
	public String getDealByParam(){
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByUserName(userName).get(0);
		String[] managements = user.getManagement().split(",");
		List<String> managementList = new ArrayList<String>();
		for (int i = 0; i < managements.length; i++) {
			managementList.add(managements[i]);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DealDao dealDao = new DealDaoImpl();
		List<Deal> deals = new ArrayList<Deal>();
		Date beginDate = new Date();
		if("".equals(begin) || begin == null){
			beginDate = null;
		}else {
			try {
				beginDate = sdf.parse(begin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date endDate = new Date();
		if("".equals(end) || end == null){
			endDate = null;
		}else {
			try {
				endDate = sdf.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		deals = dealDao.getAllDeal(beginDate, endDate, contactTel);
		System.out.println("数据数量：" + deals.size());
		for (Deal deal : deals) {
			System.out.println("报名号：" + deal.getCardCode());
			System.out.println("商机id：" + deal.getOpportunity().getId());
			System.out.println("商机学员姓名：" + deal.getOpportunity().getStuName());
		}
		for (int i = 0; i < deals.size(); i++) {
			if(!managementList.contains(deals.get(i).getOpportunity().getManagement())){
				deals.remove(i);
			}
		}
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Deal deal : deals) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("stuName", deal.getOpportunity().getStuName());
			map.put("parentName", deal.getOpportunity().getParentName());
			map.put("contactTel1", deal.getOpportunity().getContactTel1());
			map.put("contactTel2", deal.getOpportunity().getContactTel2());
			map.put("channelName", deal.getOpportunity().getChannelName());
			map.put("cardCode", deal.getCardCode());
			map.put("clsName", deal.getClassName());
			map.put("inDate", deal.getInDate());
			map.put("management", deal.getDeptName());
			map.put("pay", deal.getPay());
			map.put("beginDate", deal.getBeginDate());
			map.put("endDate", deal.getEndDate());
			maps.add(map);
		}
		result = JSON.toJSONString(maps);
		return SUCCESS;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
