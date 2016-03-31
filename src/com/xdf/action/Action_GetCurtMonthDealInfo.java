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
public class Action_GetCurtMonthDealInfo extends ActionSupport {
	private String result;
	private String username;
	
	public String getCurtMonthDealInfo(){
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance(); 
		beginCal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			beginCal.setTime(sdf.parse(sdf.format(beginCal.getTime()).split(" ")[0] + " 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		DealDao dealDao = new DealDaoImpl();
		List<Deal> dealList = dealDao.getDealByDate(beginCal.getTime(), endCal.getTime());
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Deal deal : dealList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			int oppId = deal.getOppId();
			Opportunity opp = oppDao.getOppById(oppId);
			UserDao userDao = new UserDaoImpl();
			List<User> userList = userDao.getUserByCreator(username);
			List<String> byCreatChannel = new ArrayList<String>();
			for (User user : userList) {
				byCreatChannel.add(user.getChannelName());
			}
			if(username.equals(opp.getChannelName()) || byCreatChannel.contains(opp.getChannelName())){
				map.put("id", deal.getId());
				map.put("createTime", sdf.format(opp.getCreateDate()));
				map.put("stuName", opp.getStuName());
				map.put("parentName", opp.getParentName());
				map.put("contactTel1", opp.getContactTel1());
				map.put("contactTel2", opp.getContactTel2());
				map.put("cardCode", deal.getCardCode());
				map.put("clsName", deal.getClassName());
				map.put("inDate", deal.getInDate());
				map.put("pay", deal.getPay());
				map.put("beginDate", deal.getBeginDate());
				map.put("endDate", deal.getEndDate());
				map.put("channelName", opp.getChannelName());
				map.put("management", deal.getDeptName());
				if(deal.getRebate() != 0){
					map.put("rebate", deal.getRebate());
				}else {
					map.put("rebate", "0.1");
				}
				map.put("commission", deal.getCommission());
				maps.add(map);
			}
		}
		result = JSONArray.toJSONString(maps);
		return SUCCESS;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
