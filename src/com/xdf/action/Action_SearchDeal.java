
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
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.User;

@SuppressWarnings("serial")
public class Action_SearchDeal extends ActionSupport {
	private String beginDate;
	private String endDate;
	private String channelName;
	private String stuContactTel;
	private String username;
	private String result;
	
	public String searchDeal(){
		/*System.out.println(username);
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByUserName(username).get(0);
		String managementString = user.getManagement();
		String[] managements = managementString.split(",");
		List<String> managementList = new ArrayList<String>();
		for (int i = 0; i < managements.length; i++) {
			managementList.add(managements[i]);
		}*/
		
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
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Deal deal : deals) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", deal.getId());
			map.put("stuName", deal.getOpportunity().getStuName());
			map.put("parentName", deal.getOpportunity().getParentName());
			map.put("contactTel1", deal.getOpportunity().getContactTel1());
			map.put("contactTel2", deal.getOpportunity().getContactTel2());
			map.put("cardCode", deal.getCardCode());
			map.put("clsName", deal.getClassName());
			map.put("inDate", sdf1.format(deal.getInDate()));
			map.put("pay", deal.getPay());
			map.put("beginDate", sdf1.format(deal.getBeginDate()));
			map.put("endDate", sdf1.format(deal.getEndDate()));
			map.put("channelName", deal.getOpportunity().getChannelName());
			map.put("management", deal.getDeptName());
			map.put("commission", deal.getCommission());
			if(deal.getRebate() != 0){
				map.put("rebate", deal.getRebate());
			}else {
				map.put("rebate", "0.1");
			}
			map.put("commission", deal.getCommission());
			maps.add(map);
		}
		result = JSONArray.toJSONString(maps);
		System.out.println(result);
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
