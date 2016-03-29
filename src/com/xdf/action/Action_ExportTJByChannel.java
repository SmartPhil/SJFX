package com.xdf.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
import com.xdf.dto.Channel;
import com.xdf.dto.User;

public class Action_ExportTJByChannel extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private List<Channel> channelList = new ArrayList<Channel>();
	
	public String exportTj() throws IOException{
		OpportunityDao oppDao = new OpportunityDaoImpl();
		UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getAllUser();
		for (User user : userList) {
			Channel channel = new Channel();
			if(user.getRole() == 4){
				channel.setName(user.getChannelName());
			}else {
				channel.setName(user.getUsername());
			}
			
			Calendar begin1 = Calendar.getInstance();
			if(begin1.get(Calendar.DAY_OF_WEEK) == 1){
				begin1.add(Calendar.DAY_OF_MONTH, -1);
			}
			begin1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date beginDate1 = begin1.getTime();
			Calendar end1 = Calendar.getInstance();
			Date endDate1 = end1.getTime();
			int curtWeekCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate1, endDate1);
			channel.setCurtWeekCount(curtWeekCount);
			
			Calendar begin2 = Calendar.getInstance();
			if(begin2.get(Calendar.DAY_OF_WEEK) == 1){
				begin2.add(Calendar.DAY_OF_MONTH, -1);
			}
			begin2.add(Calendar.WEEK_OF_YEAR, -1);
			begin2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date beginDate2 = begin2.getTime();
			Calendar end2 = Calendar.getInstance();
			end2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Date endDate2 = end2.getTime();
			int lastWeekCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate2, endDate2);
			channel.setLastWeekCount(lastWeekCount);
			double weekRisePercent = 0;
			if(lastWeekCount != 0){
				weekRisePercent = Double.parseDouble(new DecimalFormat("#.000").format((curtWeekCount - lastWeekCount)/lastWeekCount));
			}else {
				weekRisePercent = 0;
			}
			channel.setWeekRisePercent(weekRisePercent);
			
			Calendar begin3 = Calendar.getInstance();
			begin3.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate3 = begin3.getTime();
 			Calendar end3 = Calendar.getInstance();
			Date endDate3 = end3.getTime();
			int curtMonthCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate3, endDate3);
			channel.setCurtMonthCount(curtMonthCount);
			
			Calendar begin4 = Calendar.getInstance();
			begin4.add(Calendar.MONTH, -1);
			begin4.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate4 = begin4.getTime();
			Calendar end4 = Calendar.getInstance();
			end4.set(Calendar.DAY_OF_MONTH, 1);
			end4.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate4 = end4.getTime();
			int lastMonthCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate4, endDate4);
			channel.setLastMonthCount(lastMonthCount);
			
			Calendar begin5 = Calendar.getInstance();
			int currentMonth = begin5.get(Calendar.MONTH) + 1;
			if(currentMonth >= 1 && currentMonth <= 3){
				begin5.set(Calendar.MONTH, 0);
			}else if(currentMonth >= 4 && currentMonth <= 6){
				begin5.set(Calendar.MONTH, 3);
			}else if(currentMonth >= 7 && currentMonth <= 9){
				begin5.set(Calendar.MONTH, 6);
			}else if(currentMonth >= 10 && currentMonth <= 12){
				begin5.set(Calendar.MONTH, 9);
			}
			begin5.set(Calendar.DAY_OF_MONTH, 1);
			Date beginDate5 = begin5.getTime();
			Calendar end5 = Calendar.getInstance();
			Date endDate5 = end5.getTime();
			int curtQuarterCount = oppDao.getOppCountByChannelAndTime(user.getChannelName(), beginDate5, endDate5);
			channel.setCurtQuarter(curtQuarterCount);
			
			channelList.add(channel);
		}
		
		
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("商机统计");
		HSSFRow row = sheet.createRow(0);
		//HSSFCell cell = row.createCell(0);
		/*cell.setCellValue("结算模式");
		CellRangeAddress address = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(address);*/
		HSSFCell cell2 = row.createCell(0);
		cell2.setCellValue("渠道");
		CellRangeAddress address2 = new CellRangeAddress(0,1,0,0);
		sheet.addMergedRegion(address2);
		HSSFCell cell3 = row.createCell(1);
		cell3.setCellValue("当周数据");
		CellRangeAddress address3 = new CellRangeAddress(0,0,1,3);
		sheet.addMergedRegion(address3);
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("累计数据");
		CellRangeAddress address4 = new CellRangeAddress(0,0,4,6);
		sheet.addMergedRegion(address4);
		
		HSSFRow row2 = sheet.createRow(1);
		HSSFCell cell5 = row2.createCell(1);
		cell5.setCellValue("本周");
		HSSFCell cell6 = row2.createCell(2);
		cell6.setCellValue("上周");
		HSSFCell cell7 = row2.createCell(3);
		cell7.setCellValue("增幅");
		HSSFCell cell8 = row2.createCell(4);
		cell8.setCellValue("本月");
		HSSFCell cell9 = row2.createCell(5);
		cell9.setCellValue("上月");
		HSSFCell cell10 = row2.createCell(6);
		cell10.setCellValue("本季");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (32*10));
		style.setFont(font);
		cell2.setCellStyle(style);
		cell3.setCellStyle(style);
		cell4.setCellStyle(style);
		cell5.setCellStyle(style);
		cell6.setCellStyle(style);
		cell7.setCellStyle(style);
		cell8.setCellStyle(style);
		cell9.setCellStyle(style);
		cell10.setCellStyle(style);
		
		/*List<Channel> dataCollaList = new ArrayList<Channel>();
		List<Channel> netCollaList = new ArrayList<Channel>();
		List<Channel> marketCollaList = new ArrayList<Channel>();
		for (Channel channel : channelList) {
			if("数据合作".equals(channel.getType())){
				dataCollaList.add(channel);
			}else if("网络合作".equals(channel.getType())){
				netCollaList.add(channel);
			}else if("市场推荐".equals(channel.getType())){
				marketCollaList.add(channel);
			}
		}*/
		
		HSSFRow dataRow1 = sheet.createRow(2);
		/*HSSFCell dataCell1 = dataRow1.createCell(0);
		dataCell1.setCellValue("数据合作");
		int dataCount = dataCollaList.size();
		CellRangeAddress dataAddress = new  CellRangeAddress(2,2 + (dataCount - 1),0,0);
		sheet.addMergedRegion(dataAddress);*/
		
		for (int i = 0; i < channelList.size(); i++) {
			 HSSFRow dataRow2 = sheet.createRow(2 + i);
			 HSSFCell dataCell_Name1 = dataRow2.createCell(0);
			 dataCell_Name1.setCellValue(channelList.get(i).getName());
			 HSSFCell dataCell_CurtWeek1 = dataRow2.createCell(1);
			 dataCell_CurtWeek1.setCellValue(channelList.get(i).getCurtWeekCount());
			 HSSFCell dataCell_LastWeek1 = dataRow2.createCell(2);
			 dataCell_LastWeek1.setCellValue(channelList.get(i).getLastWeekCount());
			 HSSFCell dataCell_WeekRisePer1 = dataRow2.createCell(3);
			 dataCell_WeekRisePer1.setCellValue(channelList.get(i).getWeekRisePercent());
			 HSSFCell dataCell_CurtMonth1 = dataRow2.createCell(4);
			 dataCell_CurtMonth1.setCellValue(channelList.get(i).getCurtMonthCount());
			 HSSFCell dataCell_LastMonth1 = dataRow2.createCell(5);
			 dataCell_LastMonth1.setCellValue(channelList.get(i).getLastMonthCount());
			 HSSFCell dataCell_CurtQuartor1 = dataRow2.createCell(6);
			 dataCell_CurtQuartor1.setCellValue(channelList.get(i).getCurtQuarter());
			
			 /*if(i == 0){
				 HSSFCell dataCell_Name = dataRow1.createCell(1);
				 dataCell_Name.setCellValue(dataCollaList.get(0).getName());
				 HSSFCell dataCell_CurtWeek = dataRow1.createCell(2);
				 dataCell_CurtWeek.setCellValue(dataCollaList.get(0).getCurtWeekCount());
				 HSSFCell dataCell_LastWeek = dataRow1.createCell(3);
				 dataCell_LastWeek.setCellValue(dataCollaList.get(0).getLastWeekCount());
				 HSSFCell dataCell_WeekRisePer = dataRow1.createCell(4);
				 dataCell_WeekRisePer.setCellValue(dataCollaList.get(0).getWeekRisePercent());
				 HSSFCell dataCell_CurtMonth = dataRow1.createCell(5);
				 dataCell_CurtMonth.setCellValue(dataCollaList.get(0).getCurtMonthCount());
				 HSSFCell dataCell_LastMonth = dataRow1.createCell(6);
				 dataCell_LastMonth.setCellValue(dataCollaList.get(0).getLastMonthCount());
				 HSSFCell dataCell_CurtQuartor = dataRow1.createCell(7);
				 dataCell_CurtQuartor.setCellValue(dataCollaList.get(0).getCurtQuarter());
			 }else {
				 
			}*/
		}
		
		/*HSSFRow netRow = sheet.createRow(2 + dataCollaList.size());
		HSSFCell netCell1 = netRow.createCell(0);
		netCell1.setCellValue("网络合作");
		int netCount = netCollaList.size();
		CellRangeAddress netAddress = new CellRangeAddress(2 + dataCollaList.size(), 2 + dataCollaList.size() + netCount - 1, 0, 0);
		sheet.addMergedRegion(netAddress);
		
		for(int i = 0; i < netCollaList.size(); i++){
			if(i == 0){
				HSSFCell netCell_Name = netRow.createCell(1);
				netCell_Name.setCellValue(netCollaList.get(0).getName());
				HSSFCell netCell_CurtWeek = netRow.createCell(2);
				netCell_CurtWeek.setCellValue(netCollaList.get(0).getCurtWeekCount());
				HSSFCell netCell_LastWeek = netRow.createCell(3);
				netCell_LastWeek.setCellValue(netCollaList.get(0).getLastWeekCount());
				HSSFCell netCell_WeekRisePer = netRow.createCell(4);
				netCell_WeekRisePer.setCellValue(netCollaList.get(0).getWeekRisePercent());
				HSSFCell netCell_CurtMonth = netRow.createCell(5);
				netCell_CurtMonth.setCellValue(netCollaList.get(0).getCurtMonthCount());
				HSSFCell netCell_LastMonth = netRow.createCell(6);
				netCell_LastMonth.setCellValue(netCollaList.get(0).getLastMonthCount());
				HSSFCell netCell_CurtQuartor = netRow.createCell(7);
				netCell_CurtQuartor.setCellValue(netCollaList.get(0).getCurtQuarter());
			}else {
				HSSFRow netRow2 = sheet.createRow(2 + dataCollaList.size() + i);
				HSSFCell netCell_Name = netRow2.createCell(1);
				netCell_Name.setCellValue(netCollaList.get(i).getName());
				HSSFCell netCell_CurtWeek = netRow2.createCell(2);
				netCell_CurtWeek.setCellValue(netCollaList.get(i).getCurtWeekCount());
				HSSFCell netCell_LastWeek = netRow2.createCell(3);
				netCell_LastWeek.setCellValue(netCollaList.get(i).getLastWeekCount());
				HSSFCell netCell_WeekRisePer = netRow2.createCell(4);
				netCell_WeekRisePer.setCellValue(netCollaList.get(i).getWeekRisePercent());
				HSSFCell netCell_CurtMonth = netRow2.createCell(5);
				netCell_CurtMonth.setCellValue(netCollaList.get(i).getCurtMonthCount());
				HSSFCell netCell_LastMonth = netRow2.createCell(6);
				netCell_LastMonth.setCellValue(netCollaList.get(i).getLastMonthCount());
				HSSFCell netCell_CurtQuartor = netRow2.createCell(7);
				netCell_CurtQuartor.setCellValue(netCollaList.get(i).getCurtQuarter());
			}
		}
		
		HSSFRow marketRow = sheet.createRow(2 + dataCollaList.size() + netCollaList.size());
		HSSFCell marketCell1 = marketRow.createCell(0);
		marketCell1.setCellValue("市场推荐");
		int firstRow = 2 + dataCollaList.size() + netCollaList.size();
		int lastRow = 2 + dataCollaList.size() + netCollaList.size() + marketCollaList.size() - 1;
		CellRangeAddress marketAddress = new CellRangeAddress(firstRow, lastRow, 0, 0);
		sheet.addMergedRegion(marketAddress);
		
		for(int i = 0; i < marketCollaList.size(); i++){
			if(i == 0){
				HSSFCell marketCell_Name = marketRow.createCell(1);
				marketCell_Name.setCellValue(marketCollaList.get(0).getName());
				HSSFCell marketCell_CurtWeek = marketRow.createCell(2);
				marketCell_CurtWeek.setCellValue(marketCollaList.get(0).getCurtWeekCount());
				HSSFCell marketCell_LastWeek = marketRow.createCell(3);
				marketCell_LastWeek.setCellValue(marketCollaList.get(0).getLastWeekCount());
				HSSFCell marketCell_WeekRisePer = marketRow.createCell(4);
				marketCell_WeekRisePer.setCellValue(marketCollaList.get(0).getWeekRisePercent());
				HSSFCell marketCell_CurtMonth = marketRow.createCell(5);
				marketCell_CurtMonth.setCellValue(marketCollaList.get(0).getCurtMonthCount());
				HSSFCell marketCell_LastMonth = marketRow.createCell(6);
				marketCell_LastMonth.setCellValue(marketCollaList.get(0).getLastMonthCount());
				HSSFCell marketCell_CurtQuartor = marketRow.createCell(7);
				marketCell_CurtQuartor.setCellValue(marketCollaList.get(0).getCurtQuarter());
			}else {
				HSSFRow marketRow2 = sheet.createRow(firstRow + i);
				HSSFCell marketCell_Name = marketRow2.createCell(1);
				marketCell_Name.setCellValue(marketCollaList.get(i).getName());
				HSSFCell marketCell_CurtWeek = marketRow2.createCell(2);
				marketCell_CurtWeek.setCellValue(marketCollaList.get(i).getCurtWeekCount());
				HSSFCell marketCell_LastWeek = marketRow2.createCell(3);
				marketCell_LastWeek.setCellValue(marketCollaList.get(i).getLastWeekCount());
				HSSFCell marketCell_WeekRisePer = marketRow2.createCell(4);
				marketCell_WeekRisePer.setCellValue(marketCollaList.get(i).getWeekRisePercent());
				HSSFCell marketCell_CurtMonth = marketRow2.createCell(5);
				marketCell_CurtMonth.setCellValue(marketCollaList.get(i).getCurtMonthCount());
				HSSFCell marketCell_LastMonth = marketRow2.createCell(6);
				marketCell_LastMonth.setCellValue(marketCollaList.get(i).getLastMonthCount());
				HSSFCell marketCell_CurtQuartor = marketRow2.createCell(7);
				marketCell_CurtQuartor.setCellValue(marketCollaList.get(i).getCurtQuarter());
			}
		}*/
		
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

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}
}
