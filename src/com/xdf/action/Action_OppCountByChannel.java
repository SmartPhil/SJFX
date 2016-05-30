package com.xdf.action;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Channel;
import com.xdf.dto.User;

public class Action_OppCountByChannel extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private List<Channel> channelList = new ArrayList<Channel>();
	private String result;
	
	public String getOpp() throws ParseException{
		OpportunityDao oppDao = new OpportunityDaoImpl();
		//获取系统所有用户
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getAllUser();
		for (User user : userList) {
			Channel channel = new Channel();
			if(user.getRole() == 4){
				channel.setName(user.getChannelName());
			}else {
				channel.setName(user.getUsername());
			}
			
			
			Calendar begin1 = Calendar.getInstance();
			if(begin1.get(Calendar.DAY_OF_WEEK) == 1){
				begin1.add(Calendar.DAY_OF_MONTH, -1);
			}
			begin1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date beginDate1 = begin1.getTime();
			Calendar end1 = Calendar.getInstance();
			Date endDate1 = end1.getTime();
			int curtWeekCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate1, endDate1);
			channel.setCurtWeekCount(curtWeekCount);
			
			Calendar begin2 = Calendar.getInstance();
			if(begin2.get(Calendar.DAY_OF_WEEK) == 1){
				begin2.add(Calendar.DAY_OF_MONTH, -1);
			}
			begin2.add(Calendar.WEEK_OF_YEAR, -1);
			begin2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date beginDate2 = begin2.getTime();
			Calendar end2 = Calendar.getInstance();
			end2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Date endDate2 = end2.getTime();
			int lastWeekCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate2, endDate2);
			channel.setLastWeekCount(lastWeekCount);
			double weekRisePercent = 0;
			if(lastWeekCount != 0){
				weekRisePercent = Double.parseDouble(new DecimalFormat("#.000").format((curtWeekCount - lastWeekCount)/lastWeekCount));
			}else {
				weekRisePercent = 0;
			}
			channel.setWeekRisePercent(weekRisePercent);
			
			Calendar begin3 = Calendar.getInstance();
			begin3.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate3 = begin3.getTime();
 			Calendar end3 = Calendar.getInstance();
			Date endDate3 = end3.getTime();
			int curtMonthCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate3, endDate3);
			channel.setCurtMonthCount(curtMonthCount);
			
			Calendar begin4 = Calendar.getInstance();
			begin4.add(Calendar.MONTH, -1);
			begin4.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate4 = begin4.getTime();
			Calendar end4 = Calendar.getInstance();
			end4.set(Calendar.DAY_OF_MONTH, 1);
			end4.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate4 = end4.getTime();
			int lastMonthCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate4, endDate4);
			channel.setLastMonthCount(lastMonthCount);
			
			Calendar begin5 = Calendar.getInstance();
			int currentMonth = begin5.get(Calendar.MONTH) + 1;
			if(currentMonth >= 1 && currentMonth <= 3){
				begin5.set(Calendar.MONTH, 0);
			}else if(currentMonth >= 4 && currentMonth <= 6){
				begin5.set(Calendar.MONTH, 3);
			}else if(currentMonth >= 7 && currentMonth <= 9){
				begin5.set(Calendar.MONTH, 6);
			}else if(currentMonth >= 10 && currentMonth <= 12){
				begin5.set(Calendar.MONTH, 9);
			}
			begin5.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate5 = begin5.getTime();
			Calendar end5 = Calendar.getInstance();
			Date endDate5 = end5.getTime();
			int curtQuarterCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate5, endDate5);
			channel.setCurtQuarter(curtQuarterCount);
			
			channelList.add(channel);
		}
		
		// 统计商机的数据结果channel转化为json数据
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
		for (Channel channel : channelList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", channel.getName());
			map.put("type", channel.getType());
			map.put("curtWeekCount", channel.getCurtWeekCount());
			map.put("lastWeekCount", channel.getLastWeekCount());
			map.put("weekRisePercent", String.valueOf(channel.getWeekRisePercent() * 100) + "%");
			map.put("curtMonthCount", channel.getCurtMonthCount());
			map.put("lastMonthCount", channel.getLastMonthCount());
			map.put("curtQuarter", channel.getCurtQuarter());
			maps.add(map);
		}
		result = JSONArray.toJSONString(maps);
		
		//渠道商类型列表转化为json数据
		/*ChannelTypeDao channelTypeDao = new ChannelTypeDaoImpl();
		List<ChannelType> typeList = channelTypeDao.getAllChannelType();
		List<HashMap<String, Object>> typemaps = new ArrayList<HashMap<String,Object>>();
		for (ChannelType channelType : typeList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", channelType.getTypeId());
			map.put("typeName", channelType.getTypeName());
			typemaps.add(map);
		}
		String typeJson = JSONArray.toJSONString(typemaps);
		
		//生成结果json集并赋值给result
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("channel", channelJson);
		resultMap.put("type", typeJson);
		result = JSON.toJSONString(resultMap);*/
		return SUCCESS;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
