package com.xdf.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dto.Opportunity;
import com.xdf.util.ExcelReader;

@SuppressWarnings("serial")
public class Action_ImportExcelOpp extends ActionSupport {
	private File file_upload;
	private String fileName;
	
	public String importExcel(){
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/UploadFiles");
		File target = new File(path, "file_upload.xls");
		System.out.println(target.getPath());
		try {
			FileUtils.copyFile(file_upload, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ExcelReader excelReader = new ExcelReader(target.getPath());
			List<String> resultList = excelReader.readAllData();
			for (String oppString : resultList) {
				Opportunity opportunity = new Opportunity();
				String[] oppStrings = oppString.split(",");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
}
