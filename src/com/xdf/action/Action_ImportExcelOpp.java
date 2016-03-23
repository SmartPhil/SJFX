package com.xdf.action;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;
import com.xdf.util.ExcelReader;

@SuppressWarnings("serial")
public class Action_ImportExcelOpp extends ActionSupport {
	private File file_upload;
	private String fileName;
	private Object result;
	private String username;
	public String importExcel(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/UploadFiles");
			File target = new File(path, "file_upload.xlsx");
			try {
				FileUtils.copyFile(file_upload, target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(target.getPath());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
			ExcelReader excelReader = new ExcelReader(target.getPath());
			List<String[]> resultList = excelReader.readAllData();
			List<Opportunity> oppList = new ArrayList<Opportunity>();
			if(resultList.size() >= 1){
				//查询上传者信息
				UserDao userDAO = new UserDaoImpl();
				User user = userDAO.getUserByUserName(username).get(0);
				String channelType = "";
				if( "网络合作".equals(user.getChannelType()) ||
					"数据合作".equals(user.getChannelType()) ||
					"市场推荐".equals(user.getChannelType())){
					username = user.getChannelName();
					channelType = user.getChannelType();
				}else {
					channelType = "内部渠道";
				}
					
				for (int i = 0; i < resultList.size(); i++) {
					Opportunity opportunity = new Opportunity();
					/*if("".equals(resultList.get(i)[0]) || resultList.get(i)[0] == null){
						opportunity.setCreateDate(new Date());
					}else {
						try {
							opportunity.setCreateDate(simpleDateFormat.parse(resultList.get(i)[0]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}*/
					opportunity.setCreateDate(new Date());
					opportunity.setStuName(resultList.get(i)[0]);
					opportunity.setParentName(resultList.get(i)[1]);
					opportunity.setContactTel1(resultList.get(i)[2]);
					opportunity.setContactTel2(resultList.get(i)[3]);
					opportunity.setNeedCls(resultList.get(i)[4]);
					opportunity.setManagement(resultList.get(i)[5]);
					opportunity.setArea(resultList.get(i)[6]);
					opportunity.setAddress(resultList.get(i)[7]);
					opportunity.setSchool(resultList.get(i)[8]);
					opportunity.setGrade(resultList.get(i)[9]);
					opportunity.setIsValid(1);
					opportunity.setChannelName(username);
					opportunity.setChannelType(channelType);
					opportunity.setMark(0);
					oppList.add(opportunity);
				}
				
				OpportunityDao oppDao = new OpportunityDaoImpl();
				//保存商机
				for (Opportunity opp : oppList) {
					oppDao.insertOpportunity(opp);
				}
				map.put("result", "success");
			}else {
				System.out.println("上传的是空文件！");
				map.put("result", "null");
			}
		} catch (Exception e) {
			System.out.println("批量上传商机失败：Action_ImportExcelOpp.java:" + e.getMessage());
			map.put("result", "fail");
		}
		result = JSON.toJSON(map);
		return SUCCESS;
	}
	
	public File getFile_upload() {
		return file_upload;
	}
	public void setFile_upload(File file_upload) {
		this.file_upload = file_upload;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
