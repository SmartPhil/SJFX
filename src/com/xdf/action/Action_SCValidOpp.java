package com.xdf.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;
import com.xdf.util.HttpPostUtil;

public class Action_SCValidOpp extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String url = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetOldStudent&signKey=562KThrcprmdqm8Tll1p9A==";
	private String username;
	private String result;
	
	public String valid(){
		//查询此市场人员所创建的渠道商
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getUserByCreator(username);
		List<String> channelName = new ArrayList<String>();
		channelName.add(username);
		for (User user : userList) {
			channelName.add(user.getChannelName());
		}
		//查出当前用户及其渠道商所有未验证商机
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<Opportunity> oppList = oppDao.getOppByChannelAndMark(channelName);
		//开始验证有效性
		//半年时间戳
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		System.out.println(calendar.toString());
		for (Opportunity opp : oppList) {
			//首先判断是否是本系统内半年以内的重复数据
			List<Opportunity> result = oppDao.isOldOpp(opp);
			if(result.size() > 1){
				for (Opportunity opportunity : result) {
					//判断出那条商机不属于这条商机的本体
					if(opportunity.getCreateDate().compareTo(opp.getCreateDate()) != 0){
						Calendar oppCalendar = Calendar.getInstance();
						Date createDate = opportunity.getCreateDate();
						oppCalendar.setTime(createDate);
						int compareResult = oppCalendar.compareTo(calendar);
						//如果compareResult > 0 oppCalendar在calendar之后，证明此商机是半年以内的重复商机。
						if(compareResult > 0){
							//此商机为半年以内的数据，新录入商机不符合要求，标为无效，并停止循环。
							opp.setIsValid(0);
							opp.setNoValidReason("重复数据");
							opp.setMark(1);
							break;
						}
					}
				}
			}
			
			//如果此商机已经验证过，被标为无效，不需要做以下的判断，直接跳出此次循环
			if(opp.getMark() == 1){
				continue;
			}
			
			//再判断是否是新东方集团数据库中的老学员
			HttpPostUtil httpPostUtil = new HttpPostUtil();
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("sMobile1", opp.getContactTel1());
			map1.put("sMobile2", opp.getContactTel2());
			String map1jsonString = JSONObject.toJSONString(map1);
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("nSchoolId", 2);
			map2.put("OrderJson", map1jsonString);
			String paramJson = JSONObject.toJSONString(map2);
			
			Date now = new Date();
			String Timestamp = String.valueOf(now.getTime());
			url += "&Timestamp" + Timestamp;
			String param = "paramJson=" + paramJson;
			
			try {
				String postResult = httpPostUtil.doPost(url, param);
				JSONObject object = JSONObject.parseObject(postResult);
				String invocationResult = object.getString("Status");
				String responseResult = object.getString("ResponseData");
				System.out.println(invocationResult + "----" + responseResult);
				if("1".equals(invocationResult) && "true".equals(responseResult)){
					//调取接口成功，返回结果为此学员为半年以内的老生
					opp.setIsValid(0);
					opp.setNoValidReason("新东方学员");
					opp.setMark(1);
				}else if("1".equals(invocationResult) && "false".equals(responseResult)){
					//调取接口成功，返回结果为此学员为半年以外的学员
					opp.setMark(1);
				}else if("0".equals(invocationResult)){
					//调取接口失败，回滚数据
					opp.setMark(0);
				}
				continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("市场验证有效性调取接口失败：" + e.getMessage());
				//程序出错
				opp.setMark(0);
			}
		}
		
		//有效性验证完毕，回存数据库
		/*for (Opportunity opp : oppList) {
			//只回存无效数据
			if(opp.getIsValid() == 0){
				oppDao.update(opp);
			}
		}*/
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		result = JSONObject.toJSONString(map);
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
