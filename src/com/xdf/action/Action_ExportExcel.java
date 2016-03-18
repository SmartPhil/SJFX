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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

/**
 * export opportunity by date and contact and user name
 * @author Phil
 * @ KF reference this action
 */
public class Action_ExportExcel extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String beginDate;
	private String endDate;
	private String stuContact;
	private String username;
	
	public String export() throws IOException{
		//获取当前用户所管理的部门列表
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getUserByUserName(username);
		User curtUser = new User();
		if(userList.size() > 0){
			curtUser = userList.get(0);
		}
		String managementString = curtUser.getManagement();
		String[] managementArr = managementString.split(",");
		//转换时间（1,都为空，则查询所有;2起始为空，结束不为空，查询小于结束时间的所有商机;
		//3,起始不为空，结束为空，查询大于起始的所有商机;4，都不为空，则查询大于起始时间，小于结束时间的所有商机）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = new Date();
		Date end = new Date();
		if("".equals(beginDate) || beginDate == null){
			begin = null;
		}else {
			try {
				begin = sdf.parse(beginDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("SearchOppAction:转换时间出错:" + e.getMessage());
			}
		}
		if("".equals(endDate) || endDate == null){
			endDate = null;
		}else {
			try {
				end = sdf.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("SearchOppAction:转换时间出错:" + e.getMessage());
			}
		}
		//获取满足条件的所有商机
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OpportunityDao oppDao = new OpportunityDaoImpl();
		List<Opportunity> oppList = oppDao.searchOpp(begin, end, managementArr);
		List<Opportunity> oppList2 = new ArrayList<Opportunity>();
		if(!"".equals(stuContact) && stuContact != null){
			for (Opportunity opportunity : oppList) {
				if(stuContact.equals(opportunity.getContactTel1()) || stuContact.equals(opportunity.getContactTel2())){
					oppList2.add(opportunity);
				}
			}
			oppList = oppList2;
		}
		
		String[] headers = {"学生姓名","家长姓名","联系方式1","联系方式2","需求课程","所属部门","渠道商","渠道类型","创建日期","是否有效","无效原因","是否已分配","分配员工"};
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("商机");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (32*10));
		style.setFont(font);
		for(int i = 0; i < headers.length; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, 32*150);
		}
		
		for(int i = 0; i < oppList.size(); i++){
			row = sheet.createRow(i + 1);
			Opportunity opp = oppList.get(i);
			row.createCell(0).setCellValue(opp.getStuName());
			row.createCell(1).setCellValue(opp.getParentName());
			row.createCell(2).setCellValue(opp.getContactTel1());
			row.createCell(3).setCellValue(opp.getContactTel2());
			row.createCell(4).setCellValue(opp.getNeedCls());
			row.createCell(5).setCellValue(opp.getManagement());
			row.createCell(6).setCellValue(opp.getChannelName());
			row.createCell(7).setCellValue(opp.getChannelType());
			row.createCell(8).setCellValue(sdf2.format(opp.getCreateDate()));
			if(opp.getIsValid() == 0){
				row.createCell(9).setCellValue("无效");
			}else if(opp.getIsValid() == 1){
				row.createCell(9).setCellValue("有效");
			}
			row.createCell(10).setCellValue(opp.getNoValidReason());
			if(opp.getIsAssign() == 0){
				row.createCell(11).setCellValue("未分配");
			}else if(opp.getIsAssign() == 1){
				row.createCell(11).setCellValue("已分配");
			}
			row.createCell(12).setCellValue(opp.getAssignEmployee());
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=opportunity.xls");
		OutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		wb.close();
		return null;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStuContact() {
		return stuContact;
	}
	public void setStuContact(String stuContact) {
		this.stuContact = stuContact;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
