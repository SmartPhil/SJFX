
package com.xdf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.DealDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dto.Deal;

@SuppressWarnings("serial")
public class Action_SearchDeal extends ActionSupport {
	private String beginDate;
	private String endDate;
	private String channelName;
	private String stuContactTel;
	private String result;
	
	public String searchDeal(){
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
			}
		}
		if("".equals(endDate) || endDate == null){
			end = null;
		}else {
			try {
				end = sdf.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		DealDao dealDao = new DealDaoImpl();
		List<Deal> deals = dealDao.getAllDeal(begin, end, stuContactTel);
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Deal deal : deals) {
			if(channelName.equals(deal.getOpportunity().getChannelName())){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", deal.getId());
				map.put("stuName", deal.getOpportunity().getStuName());
				map.put("clsName", deal.getClassName());
				map.put("pay", deal.getPay());
				map.put("channelName", deal.getOpportunity().getChannelName());
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
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getStuContactTel() {
		return stuContactTel;
	}
	public void setStuContactTel(String stuContactTel) {
		this.stuContactTel = stuContactTel;
	}
}
