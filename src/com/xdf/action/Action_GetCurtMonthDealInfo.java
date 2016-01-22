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
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.Opportunity;

@SuppressWarnings("serial")
public class Action_GetCurtMonthDealInfo extends ActionSupport {
	private String result;
	
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
			map.put("id", deal.getId());
			map.put("stuName", opp.getStuName());
			map.put("clsName", deal.getClassName());
			map.put("pay", deal.getPay());
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
		result = JSONArray.toJSONString(maps);
		return SUCCESS;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
