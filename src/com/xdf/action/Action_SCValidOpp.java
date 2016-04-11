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
		//��ѯ���г���Ա��������������
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getUserByCreator(username);
		List<String> channelName = new ArrayList<String>();
		channelName.add(username);
		for (User user : userList) {
			channelName.add(user.getChannelName());
		}
		//�����ǰ�û���������������δ��֤�̻�
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<Opportunity> oppList = oppDao.getOppByChannelAndMark(channelName);
		//��ʼ��֤��Ч��
		//����ʱ���
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		System.out.println(calendar.toString());
		for (Opportunity opp : oppList) {
			//�����ж��Ƿ��Ǳ�ϵͳ�ڰ������ڵ��ظ�����
			List<Opportunity> result = oppDao.isOldOpp(opp);
			if(result.size() > 1){
				for (Opportunity opportunity : result) {
					//�жϳ������̻������������̻��ı���
					if(opportunity.getCreateDate().compareTo(opp.getCreateDate()) != 0){
						Calendar oppCalendar = Calendar.getInstance();
						Date createDate = opportunity.getCreateDate();
						oppCalendar.setTime(createDate);
						int compareResult = oppCalendar.compareTo(calendar);
						//���compareResult > 0 oppCalendar��calendar֮��֤�����̻��ǰ������ڵ��ظ��̻���
						if(compareResult > 0){
							//���̻�Ϊ�������ڵ����ݣ���¼���̻�������Ҫ�󣬱�Ϊ��Ч����ֹͣѭ����
							opp.setIsValid(0);
							opp.setNoValidReason("�ظ�����");
							opp.setMark(1);
							break;
						}
					}
				}
			}
			
			//������̻��Ѿ���֤��������Ϊ��Ч������Ҫ�����µ��жϣ�ֱ�������˴�ѭ��
			if(opp.getMark() == 1){
				continue;
			}
			
			//���ж��Ƿ����¶����������ݿ��е���ѧԱ
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
					//��ȡ�ӿڳɹ������ؽ��Ϊ��ѧԱΪ�������ڵ�����
					opp.setIsValid(0);
					opp.setNoValidReason("�¶���ѧԱ");
					opp.setMark(1);
				}else if("1".equals(invocationResult) && "false".equals(responseResult)){
					//��ȡ�ӿڳɹ������ؽ��Ϊ��ѧԱΪ���������ѧԱ
					opp.setMark(1);
				}else if("0".equals(invocationResult)){
					//��ȡ�ӿ�ʧ�ܣ��ع�����
					opp.setMark(0);
				}
				continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("�г���֤��Ч�Ե�ȡ�ӿ�ʧ�ܣ�" + e.getMessage());
				//�������
				opp.setMark(0);
			}
		}
		
		//��Ч����֤��ϣ��ش����ݿ�
		/*for (Opportunity opp : oppList) {
			//ֻ�ش���Ч����
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
