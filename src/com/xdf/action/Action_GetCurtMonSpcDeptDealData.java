package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.DealDao;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

@SuppressWarnings("serial")
public class Action_GetCurtMonSpcDeptDealData extends ActionSupport {
	private String userName;
	private String result;
	
	public String getSpcDeal(){
		UserDao userDao = new UserDaoImpl();
		List<User> users = userDao.getUserByUserName(userName);
		User user = users.get(0);
		String managementString = user.getManagement();
		String[] managements = managementString.split(",");
		List<String> managementList = new ArrayList<String>();
		for (int i = 0; i < managements.length; i++) {
			managementList.add(managements[i]);
		}
		//查询出当前月所有成单数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
		endCalendar.add(Calendar.MONTH, 1);
		endCalendar.set(Calendar.DAY_OF_MONTH, 1);
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);
		try {
			beginCalendar.setTime(sdf.parse(sdf.format(beginCalendar.getTime())));
			endCalendar.setTime(sdf.parse(sdf.format(endCalendar.getTime())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DealDao dealDao = new DealDaoImpl();
		List<Deal> deals = dealDao.getDealByDate(beginCalendar.getTime(), endCalendar.getTime());
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Deal deal : deals) {
			int oppId = deal.getOppId();
			Opportunity opp = oppDao.getOppById(oppId);
			if(managementList.contains(opp.getManagement())){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("stuName", opp.getStuName());
				map.put("parentName", opp.getParentName());
				map.put("contactTel1", opp.getContactTel1());
				map.put("contactTel2", opp.getContactTel2());
				map.put("needCls", opp.getNeedCls());
				map.put("channelName", opp.getChannelName());
				map.put("channelType", opp.getChannelType());
				map.put("createDate", sdf1.format(opp.getCreateDate()));
				if("".equals(opp.getAssignEmployee()) || opp.getAssignEmployee() == null){
					map.put("assignEmployee", "无");
				}else {
					map.put("assignEmployee", opp.getAssignEmployee());
				}
				map.put("cardCode", deal.getCardCode());
				map.put("clsName", deal.getClassName());
				map.put("inDate", sdf1.format(deal.getInDate()));
				map.put("management", deal.getDeptName());
				map.put("pay", deal.getPay());
				map.put("beginDate", sdf1.format(deal.getBeginDate()));
				map.put("endDate", sdf1.format(deal.getEndDate()));
				maps.add(map);
			}
		}
		result = JSONArray.toJSONString(maps);
		return SUCCESS;
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
