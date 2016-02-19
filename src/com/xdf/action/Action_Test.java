package com.xdf.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.util.HttpPostUtil;

public class Action_Test extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File file_upload;
	private String fileName;
	private String result;
	//private String url = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetOldStudent&signKey=562KThrcprmdqm8Tll1p9A==";
	private String url1 = "http://yhapi.cc.xdf.cn/BaseInfo/GetSHStudentInfo.ashx?method=Student.GetStudentRegistInfo&signKey=562KThrcprmdqm8Tll1p9A==";
	public String test(){
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/UploadFiles");
		File target = new File(path, "file_upload.xlsx");
		try {
			FileUtils.copyFile(file_upload, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(target.getPath());
		try {
			//OPCPackage opcPackage = OPCPackage.open(target);
			//InputStream is = new FileInputStream(target.getPath());
			Workbook wb = new XSSFWorkbook();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			
		} catch (Exception e1) {
			System.out.println("创建输入流失败：" + e1.getMessage());
		}*/
		System.out.println("结束了！");
		/*try {
			
		} catch (Exception e) {
			System.out.println("创建XSSFWorkbook失败：" + e.getMessage());
		}*/
		return SUCCESS;
		/*System.out.println("测试……");
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
		return SUCCESS;*/
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
