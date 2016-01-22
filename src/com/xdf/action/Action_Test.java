package com.xdf.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.util.HttpPostUtil;

public class Action_Test extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String result;
	//private String url = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetOldStudent&signKey=562KThrcprmdqm8Tll1p9A==";
	private String url1 = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetStudentRegistInfo&signKey=562KThrcprmdqm8Tll1p9A==";
	public String test(){
		System.out.println("测试……");
		HttpPostUtil httpPostUtil = new HttpPostUtil();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("sMobile1", "13501976967"); //13817595709
		map1.put("sMobile2", "");
		String map1jsonString = JSONObject.toJSONString(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("nSchoolId", 2);
		map2.put("OrderJson", map1jsonString);
		String paramJson = JSONObject.toJSONString(map2);
		System.out.println(paramJson);
		Date now = new Date();
		String Timestamp = String.valueOf(now.getTime());
		System.out.println(Timestamp);
		url1 += "&Timestamp" + Timestamp;
		String param = "paramJson=" + paramJson;
		try {
			result = httpPostUtil.doPost(url1, param);
			System.out.println(result);
			JSONObject obj1 = JSONObject.parseObject(result);
			String responseDataString = obj1.getString("ResponseData");
			JSONArray array = JSONArray.parseArray(responseDataString);
			String inDate = array.getJSONObject(0).getString("dtInDate");
			System.out.println("进班日期：" + inDate);
			System.out.println("进班日期前半部分：" + inDate.split("T")[0]);
			System.out.println("进班日期后半部分：" + inDate.split("T")[1]);
			System.out.println("进班日期重组：" + inDate.split("T")[0] + " " + inDate.split("T")[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return SUCCESS;
	}
}
