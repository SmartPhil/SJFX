package com.xdf.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
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
import com.xdf.dao.DealDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.User;

/**
 * @description export deal information by date and contactTel and user name
 * @author Phil
 *
 */
@SuppressWarnings("serial")
public class Action_ExportDealInfoByUser extends ActionSupport{
	private String userName;
	private String begin;
	private String end;
	private String contactTel;
	
	public String exportDealInfo(){
		System.out.println(userName);
		System.out.println(begin);
		System.out.println(end);
		System.out.println(contactTel);
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByUserName(userName).get(0);
		String[] managements = user.getManagement().split(",");
		List<String> managementList = new ArrayList<String>();
		for (int i = 0; i < managements.length; i++) {
			managementList.add(managements[i]);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DealDao dealDao = new DealDaoImpl();
		List<Deal> deals = new ArrayList<Deal>();
		Date beginDate = new Date();
		if("".equals(begin) || begin == null){
			beginDate = null;
		}else {
			try {
				beginDate = sdf.parse(begin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date endDate = new Date();
		if("".equals(end) || end == null){
			endDate = null;
		}else {
			try {
				endDate = sdf.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		deals = dealDao.getAllDeal(beginDate, endDate, contactTel);
		System.out.println("数据数量：" + deals.size());
		for (Deal deal : deals) {
			System.out.println("报名号：" + deal.getCardCode());
			System.out.println("商机id：" + deal.getOpportunity().getId());
			System.out.println("商机学员姓名：" + deal.getOpportunity().getStuName());
		}
		for (int i = 0; i < deals.size(); i++) {
			if(!managementList.contains(deals.get(i).getOpportunity().getManagement())){
				deals.remove(i);
			}
		}
		HSSFWorkbook hw = new HSSFWorkbook();
		HSSFSheet sheet = hw.createSheet("成单数据");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = hw.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell header = row.createCell(0);
		String title = begin + "至" + end + "数据";
		header.setCellValue(title);
		header.setCellStyle(style);
		CellRangeAddress address = new CellRangeAddress(0, 0, 0, 12);
		sheet.addMergedRegion(address);
		String[] columns = {"学员姓名", "家长姓名", "联系方式1", "联系方式2", "渠道商", "听课证号", "班级名称", "进班日期", "管理部门", "学费", "开课日期", "结课日期"};
		HSSFRow columnRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = columnRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(columns[i]);
		}
		int mark = 2;
		for (Deal deal : deals) {
			HSSFRow dataRow = sheet.createRow(mark);
			HSSFCell cell1 = dataRow.createCell(0);
			cell1.setCellValue(deal.getOpportunity().getStuName());
			cell1.setCellStyle(style);
			HSSFCell cell2 = dataRow.createCell(1);
			cell2.setCellValue(deal.getOpportunity().getParentName());
			cell2.setCellStyle(style);
			HSSFCell cell3 = dataRow.createCell(2);
			cell3.setCellValue(deal.getOpportunity().getContactTel1());
			cell3.setCellStyle(style);
			HSSFCell cell4 = dataRow.createCell(3);
			cell4.setCellValue(deal.getOpportunity().getContactTel2());
			cell4.setCellStyle(style);
			HSSFCell cell5 = dataRow.createCell(4);
			cell5.setCellValue(deal.getOpportunity().getChannelName());
			cell5.setCellStyle(style);
			HSSFCell cell6 = dataRow.createCell(5);
			cell6.setCellValue(deal.getCardCode());
			cell6.setCellStyle(style);
			HSSFCell cell7 = dataRow.createCell(6);
			cell7.setCellValue(deal.getClassName());
			cell7.setCellStyle(style);
			HSSFCell cell8 = dataRow.createCell(7);
			cell8.setCellValue(sdf.format(deal.getInDate()));
			cell8.setCellStyle(style);
			HSSFCell cell9 = dataRow.createCell(8);
			cell9.setCellValue(deal.getDeptName());
			cell9.setCellStyle(style);
			HSSFCell cell10 = dataRow.createCell(9);
			cell10.setCellValue(deal.getPay());
			cell10.setCellStyle(style);
			HSSFCell cell11 = dataRow.createCell(10);
			cell11.setCellValue(sdf.format(deal.getBeginDate()));
			cell11.setCellStyle(style);
			HSSFCell cell12 = dataRow.createCell(11);
			cell12.setCellValue(sdf.format(deal.getEndDate()));
			cell12.setCellStyle(style);
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
}
