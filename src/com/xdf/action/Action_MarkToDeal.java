package com.xdf.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.DealDao;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.Opportunity;
import com.xdf.util.HttpPostUtil;

@SuppressWarnings("serial")
public class Action_MarkToDeal extends ActionSupport {
	private String contactTel1;
	private String contactTel2;
	private String result;

	private String url = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetStudentRegistInfo&signKey=562KThrcprmdqm8Tll1p9A==";
	public String markToDeal(){
		if("无".equals(contactTel2)){
			contactTel2 = "";
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();	
		
		//get the deal information from http request and save into local database
		Date now = new Date();
		String timeStamp = String.valueOf(now.getTime());
		url += "&Timestamp=" + timeStamp;
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("sMobile1", contactTel1);
		map1.put("sMobile2", contactTel2);
		String map1jsonString = JSONObject.toJSONString(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("nSchoolId", 2);
		map2.put("OrderJson", map1jsonString);
		String paramString = JSONObject.toJSONString(map2);
		String paramJson = "paramJson=" + paramString;
		HttpPostUtil httpPostUtil = new HttpPostUtil();
		String returnDealInformation = "";
		try {
			returnDealInformation = httpPostUtil.doPost(url, paramJson);
		} catch (IOException e) {
			System.out.println("获取成单信息发生错误：" + e.getMessage());
		}
		System.out.println(returnDealInformation);
		JSONObject jsonObject = JSONObject.parseObject(returnDealInformation);
		String invocationResult = jsonObject.getString("Status");
		String responseDataString = jsonObject.getString("ResponseData");
		System.out.println(responseDataString);
		JSONArray responseResult = JSONObject.parseArray(responseDataString);
		System.out.println(invocationResult);
		if("1".equals(invocationResult)){
			//调用成功！解析返回的详细订单数据。
			if(responseResult.size() != 0){
				//获取关联opportunity's id
				Opportunity tOpportunity = oppDao.getOpportunityByContact(contactTel1, contactTel2);
				int oppId = tOpportunity.getId();
				boolean insertResult = true;
				for (int i = 0; i < responseResult.size(); i++) {
					String cardCode = responseResult.getJSONObject(i).getString("sCardCode");
					int channel = responseResult.getJSONObject(i).getInteger("nChannel");
					String channelName = responseResult.getJSONObject(i).getString("nChannelName");
					String className = responseResult.getJSONObject(i).getString("className");
					String inDate = responseResult.getJSONObject(i).getString("dtInDate");
					String deptName = responseResult.getJSONObject(i).getString("sDeptName");
					double pay = responseResult.getJSONObject(i).getDouble("dPay");
					String beginDate = responseResult.getJSONObject(i).getString("dtBeginDate");
					String endDate = responseResult.getJSONObject(i).getString("dtEndDate");
					
					Deal deal = new Deal();
					SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					deal.setOppId(oppId);
					deal.setCardCode(cardCode);
					deal.setChannel(channel);
					deal.setChannelName(channelName);
					deal.setClassName(className);
					if(!"".equals(inDate)){
						try {
							deal.setInDate(sdf.parse(inDate.split("T")[0] + " " + inDate.split("T")[1]));
						} catch (ParseException e) {
							System.out.println("转换进班日期失败：" + e.getMessage());
						}
					}else {
						deal.setInDate(null);
					}
					deal.setDeptName(deptName);
					deal.setPay(pay);
					if(!"".equals(beginDate)){
						try {
							deal.setBeginDate(sdf.parse(beginDate.split("T")[0] + " " + beginDate.split("T")[1]));
						} catch (Exception e) {
							System.out.println("转换开课日期失败：" + e.getMessage());
						}
					}else {
						deal.setBeginDate(null);
					}
					if(!"".equals(endDate)){
						try {
							deal.setEndDate(sdf.parse(endDate.split("T")[0] + " " + endDate.split("T")[1]));
						} catch (Exception e) {
							System.out.println("转换结课日期失败：" + e.getMessage());
						}
					}else {
						deal.setEndDate(null);
					}
					
					DealDao dealDao = new DealDaoImpl();
					insertResult = insertResult&dealDao.insertDeal(deal);
					System.out.println(insertResult);
				}
				if(insertResult){
					//mark this opportunity to deal
					boolean markResult = oppDao.markToDeal(contactTel1, contactTel2);
					HashMap<String, Object> map = new HashMap<String, Object>();
					if(markResult){
						map.put("markResult", "success");
					}else {
						//调用接口成功！并且成功获取关联商机详细信息，并且成功插入本地数据库，但将关联商机标记为已成单失败！
						map.put("markResult", "fail");
						map.put("failReason", "mark the relate opportunity's nState to has deal fail!");
						//删除之前插入的与之相关的详细成单数据
						DealDao dealDao = new DealDaoImpl();
						dealDao.deleteDealByOppId(oppId);
					}
					result = JSONObject.toJSONString(map);
				}else {
					//调用接口成功！并且成功获取到关联商机的详细信息。但插入本地数据库失败！
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("markResult", "fail");
					map.put("failReason", "Cannot insert the deal information into local database!");
					result = JSONObject.toJSONString(map);
				}
			}else {
				//调用接口成功！但未获取到关联商机的成单详细信息。
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("markResult", "fail");
				map.put("failReason", "Cannot get the deal information from interface!");
				result = JSONObject.toJSONString(map);
			}
		}else if("0".equals(invocationResult)){
			//调用失败！请联系管理员
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("markResult", "fail");
			map.put("failReason", "Invocation interface fail,please contact system administrator!");
			result = JSONObject.toJSONString(map);
		}
 		return SUCCESS;
	}

	public String getContactTel1() {
		return contactTel1;
	}

	public void setContactTel1(String contactTel1) {
		this.contactTel1 = contactTel1;
	}

	public String getContactTel2() {
		return contactTel2;
	}

	public void setContactTel2(String contactTel2) {
		this.contactTel2 = contactTel2;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
