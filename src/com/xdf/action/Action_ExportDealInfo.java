package com.xdf.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.DealDaoImpl;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Deal;
import com.xdf.dto.Opportunity;

/**
 * export deal information by sc
 * @author phil
 * @issue don't kown how sc want to export deal information
 */
@SuppressWarnings("serial")
public class Action_ExportDealInfo extends ActionSupport {
	private String begin;
	private String end;
	private String stuContactTel;
	private String username;
	private String result;
	
	public String export(){
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
		CellRangeAddress address = new CellRangeAddress(0, 0, 0, 6);
		sheet.addMergedRegion(address);
		String[] columns = {"姓名", "班级名称", "学费", "渠道", "所属部门", "返佣比例", "佣金"};
		HSSFRow columnRow = sheet.createRow(1);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = columnRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(columns[i]);
		}
		DealDao dealDao = new DealDaoImpl();
		OpportunityDao oppDao = new OpportunityDaoImpl();
		
		Date beginDate = new Date();
		Date endDate = new Date();
		try {
			beginDate = sdf.parse(begin);
			endDate = sdf.parse(end);
		} catch (Exception e) {
			System.out.println("导出成单数据 转换起截止时间失败：" + e.getMessage());
		}
		List<Deal> deals = dealDao.getDealByDate(beginDate, endDate);
		int mark = 2;
		for (Deal deal : deals) {
			int oppId = deal.getOpportunity().getId();
			Opportunity opportunity = oppDao.getOppById(oppId);
			if(!"".equals(stuContactTel) && stuContactTel != null){
				System.out.println(stuContactTel + "/n" + opportunity.getContactTel1());
				if(stuContactTel.equals(opportunity.getContactTel1()) || stuContactTel.equals(opportunity.getContactTel2())){
					HSSFRow dataRow = sheet.createRow(mark);
					HSSFCell cell1 = dataRow.createCell(0);
					cell1.setCellValue(opportunity.getStuName());
					cell1.setCellStyle(style);
					HSSFCell cell2 = dataRow.createCell(1);
					cell2.setCellValue(deal.getClassName());
					cell2.setCellStyle(style);
					HSSFCell cell3 = dataRow.createCell(2);
					cell3.setCellValue(deal.getPay());
					cell3.setCellStyle(style);
					HSSFCell cell4 = dataRow.createCell(3);
					cell4.setCellValue(opportunity.getChannelName());
					cell4.setCellStyle(style);
					HSSFCell cell5 = dataRow.createCell(4);
					cell5.setCellValue(deal.getDeptName());
					cell5.setCellStyle(style);
					HSSFCell cell6 = dataRow.createCell(5);
					cell6.setCellValue(deal.getRebate());
					cell6.setCellStyle(style);
					HSSFCell cell7 = dataRow.createCell(6);
					cell7.setCellValue(deal.getCommission());
					cell7.setCellStyle(style);
					mark++;
				}
			}else {
				HSSFRow dataRow = sheet.createRow(mark);
				HSSFCell cell1 = dataRow.createCell(0);
				cell1.setCellValue(opportunity.getStuName());
				cell1.setCellStyle(style);
				HSSFCell cell2 = dataRow.createCell(1);
				cell2.setCellValue(deal.getClassName());
				cell2.setCellStyle(style);
				HSSFCell cell3 = dataRow.createCell(2);
				cell3.setCellValue(deal.getPay());
				cell3.setCellStyle(style);
				HSSFCell cell4 = dataRow.createCell(3);
				cell4.setCellValue(opportunity.getChannelName());
				cell4.setCellStyle(style);
				HSSFCell cell5 = dataRow.createCell(4);
				cell5.setCellValue(deal.getDeptName());
				cell5.setCellStyle(style);
				HSSFCell cell6 = dataRow.createCell(5);
				cell6.setCellValue(deal.getRebate());
				cell6.setCellStyle(style);
				HSSFCell cell7 = dataRow.createCell(6);
				cell7.setCellValue(deal.getCommission());
				cell7.setCellStyle(style);
				mark++;
			}
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
}
