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

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.util.ExcelReader;

@SuppressWarnings("serial")
public class Action_ImportExcelOpp extends ActionSupport {
	private File file_upload;
	private String fileName;
	private Object result;
	private String username;
	public String importExcel(){
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/UploadFiles");
		File target = new File(path, "file_upload.xlsx");
		try {
			FileUtils.copyFile(file_upload, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(target.getPath());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
		ExcelReader excelReader = new ExcelReader(target.getPath());
		List<String[]> resultList = excelReader.readAllData();
		List<Opportunity> oppList = new ArrayList<Opportunity>();
		if(resultList.size() >= 2){
			for (int i = 1; i < resultList.size(); i++) {
				Opportunity opportunity = new Opportunity();
				if("".equals(resultList.get(i)[0]) || resultList.get(i)[0] == null){
					opportunity.setCreateDate(new Date());
				}else {
					try {
						opportunity.setCreateDate(simpleDateFormat.parse(resultList.get(i)[0]));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				String name = resultList.get(i)[1];
				if("".equals(name) || name == null){
					opportunity.setStuName("");
					opportunity.setParentName("");
				}else {
					if(name.contains("/")){
						String[] names = name.split("/");
						opportunity.setParentName(names[0]);
						opportunity.setStuName(names[1]);
					}else {
						opportunity.setParentName(name);
					}
				}
				opportunity.setContactTel1(resultList.get(i)[2]);
				opportunity.setContactTel2(resultList.get(i)[3]);
				opportunity.setKeyword(resultList.get(i)[4]);
				opportunity.setGrade(resultList.get(i)[5]);
				opportunity.setAddress(resultList.get(i)[6]);
				opportunity.setNeedCls(resultList.get(i)[7]);
				opportunity.setManagement(resultList.get(i)[8]);
				opportunity.setComment(resultList.get(i)[9]);
				if("".equals(resultList.get(i)[10]) || resultList.get(i)[10] == null || "是".equals(resultList.get(i)[10])){
					opportunity.setIsValid(1);
				}else {
					opportunity.setIsValid(0);
				}
				if(opportunity.getIsValid() == 0){
					opportunity.setNoValidReason(resultList.get(i)[11]);
				}else {
					opportunity.setNoValidReason("");
				}
				opportunity.setChannelName(username);
				opportunity.setChannelType("内部渠道");
				oppList.add(opportunity);
			}
			//剔除不符合条件的商机(仅仅判断本系统内是否有此学员存在以及是否是本系统半年以内的老生)
			List<Opportunity> canImportOpp = new ArrayList<Opportunity>();
			List<Opportunity> notImportOpp = new ArrayList<Opportunity>();
			OpportunityDao oppDao = new OpportunityDaoImpl();
			for (Opportunity opp : oppList) {
				if(!oppDao.isOldOpp(opp)){
					canImportOpp.add(opp);
				}else {
					notImportOpp.add(opp);
				}
			}
			
			List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
			for (Opportunity opp : notImportOpp) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				if(opp.getCreateDate() != null){
					map.put("createTime", sdf.format(opp.getCreateDate()));
				}else {
					map.put("createTime", "");
				}
				map.put("stuName", opp.getStuName());
				map.put("parentName", opp.getParentName());
				map.put("contactTel1", opp.getContactTel1());
				map.put("contactTel2", opp.getContactTel2());
				map.put("channelName", opp.getChannelName());
				map.put("channelType", opp.getChannelType());
				map.put("needCls", opp.getNeedCls());
				map.put("management", opp.getManagement());
				map.put("comment", opp.getComment());
				map.put("grade", opp.getGrade());
				map.put("degree", opp.getDegree());
				map.put("address", opp.getAddress());
				map.put("keyword", opp.getKeyword());
				maps.add(map);
			}
			result = JSONArray.toJSON(maps);
			System.out.println(result);
		}else {
			System.out.println("请不要上传空文件！");
		}
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
