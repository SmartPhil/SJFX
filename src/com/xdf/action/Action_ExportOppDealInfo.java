package com.xdf.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

public class Action_ExportOppDealInfo extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String begin;
	private String end;
	private String stuContactTel;
	private String username;
	private String result;
	
	public String exportOppDealInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("".equals(begin) || begin == null){
			begin = "1949-10-01";
		}
		if("".equals(end) || end == null){
			end = sdf.format(new Date());
		}
		HSSFWorkbook hw = new HSSFWorkbook();
		HSSFSheet sheet = hw.createSheet("成单数据");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = hw.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell header = row.createCell(0);
		String title = begin + "至" + end + username + "数据";
		header.setCellValue(title);
		header.setCellStyle(style);
		CellRangeAddress address = new CellRangeAddress(0, 0, 0, 13);
		sheet.addMergedRegion(address);
		String[] columns = {"创建时间","学员姓名", "家长姓名", "联系方式1", "联系方式2", "需求课程", "所属部门", "行政区域", "联系地址", "学校", "年级", "是否有效", "无效原因","成单情况"};
		HSSFRow columnRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = columnRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(columns[i]);
		}
		Date beginDate = new Date();
		Date endDate = new Date();
		try {
			beginDate = sdf.parse(begin);
			endDate = sdf.parse(end);
		} catch (Exception e) {
			System.out.println("导出渠道商商机成单情况数据 转换起截止时间失败：" + e.getMessage());
		}
		OpportunityDao oppDao = new OpportunityDaoImpl();
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByUserName(username).get(0);
		List<Opportunity> oppList = oppDao.getOppByChannelAndDate(user.getChannelName(), beginDate, endDate);
		if(!"".equals(stuContactTel) && stuContactTel != null){
			List<Opportunity> contactOppList = new ArrayList<Opportunity>();
			for (Opportunity opportunity : oppList) {
				if(stuContactTel.equals(opportunity.getContactTel1()) || stuContactTel.equals(opportunity.getContactTel2())){
					contactOppList.add(opportunity);
				}
			}
			oppList = contactOppList;
		}
		
		int mark = 2;
		for (Opportunity opp : oppList) {
			HSSFRow dataRow = sheet.createRow(mark);
			HSSFCell cell1 = dataRow.createCell(0);
			cell1.setCellValue(sdf.format(opp.getCreateDate()));
			cell1.setCellStyle(style);
			HSSFCell cell2 = dataRow.createCell(1);
			cell2.setCellValue(opp.getStuName());
			cell2.setCellStyle(style);
			HSSFCell cell3 = dataRow.createCell(2);
			cell3.setCellValue(opp.getParentName());
			cell3.setCellStyle(style);
			HSSFCell cell4 = dataRow.createCell(3);
			cell4.setCellValue(opp.getContactTel1());
			cell4.setCellStyle(style);
			HSSFCell cell5 = dataRow.createCell(4);
			cell5.setCellValue(opp.getContactTel2());
			cell5.setCellStyle(style);
			HSSFCell cell6 = dataRow.createCell(5);
			cell6.setCellValue(opp.getNeedCls());
			cell6.setCellStyle(style);
			HSSFCell cell7 = dataRow.createCell(6);
			cell7.setCellValue(opp.getManagement());
			cell7.setCellStyle(style);
			HSSFCell cell8 = dataRow.createCell(7);
			cell8.setCellValue(opp.getArea());
			cell8.setCellStyle(style);
			HSSFCell cell9 = dataRow.createCell(8);
			cell9.setCellValue(opp.getAddress());
			cell9.setCellStyle(style);
			HSSFCell cell10 = dataRow.createCell(9);
			cell10.setCellValue(opp.getSchool());
			cell10.setCellStyle(style);
			HSSFCell cell11 = dataRow.createCell(10);
			cell11.setCellValue(opp.getGrade());
			cell11.setCellStyle(style);
			HSSFCell cell12 = dataRow.createCell(11);
			if(opp.getMark() == 0){
				cell12.setCellValue("有效性验证中");
			}else if(opp.getMark() == 1){
				if(opp.getIsValid() == 0){
					cell12.setCellValue("无效");
				}else if(opp.getIsValid() == 1){
					cell12.setCellValue("有效");
				}
			}
			HSSFCell cell13 = dataRow.createCell(12);
			cell13.setCellValue(opp.getNoValidReason());
			cell13.setCellStyle(style);
			HSSFCell cell14 = dataRow.createCell(13);
			if(opp.getState() == 0){
				cell14.setCellValue("待跟进");
			}else if(opp.getState() == 1){
				cell14.setCellValue("已成单");
			}
			cell14.setCellStyle(style);
			mark++;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=deal.xls");
		try {
			OutputStream out = response.getOutputStream();
			hw.write(out);
			out.flush();
			out.close();
			hw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
