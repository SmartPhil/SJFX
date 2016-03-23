package com.xdf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.examples.CellTypes;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	//创建文件输入流  
    private BufferedReader reader = null;  
    //文件类型  
    private String fileType;  
      
    //文件二进制输入流  
    private InputStream is = null;  
    
    //XSSFWorkbook  
    XSSFWorkbook workbook = null;
    //sheet
    private XSSFSheet sheet;
      
    //构造函数创建一个ExcelReader  
    public ExcelReader(String inputfile) {  
        //取得文件名后缀赋值给fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        
        //判断文件格式  
        try {
			workbook = new XSSFWorkbook(inputfile);
		} catch (Exception e) {
			System.out.println("创建XSSFWorkbook失败：" + e.getMessage());
		}  
        //设置sheet数  
        this.sheet = workbook.getSheetAt(0);
    }  
   
    //close函数执行流的关闭操作  
    public void close() {  
        //如果id不为空，则关闭InputStream文件输入流  
        if(is != null) {  
            try {  
                is.close();  
            }catch(IOException e) {  
                is = null;  
            }  
        }  
          
        //如果reader不为空，则关闭BufferedReader文件输入流  
        if(reader != null) {  
            try {  
                reader.close();  
            }catch(IOException e) {  
                reader = null;  
            }  
        }  
    }   
    
    //读取excel数据
    public List<String[]> readAllData(){
    	//结果列表
    	List<String[]> result = new ArrayList<String[]>();
    	int columnNum = 0;
    	if(sheet.getRow(0) != null){
    		columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
    	}
    	
    	//List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
    	//得到文件最后一行的num值
    	int rowNum = sheet.getLastRowNum() + 1;
    	if(rowNum > 1){
    		for (int i = 2; i < rowNum; i++) {
    			Row row = sheet.getRow(i);
    			//判断电话号码1是否为空，若为空则忽略此行。
    			Cell cell2 = row.getCell(2);
    			if(cell2 != null){
    				cell2.setCellType(Cell.CELL_TYPE_STRING);
        			if("".equals(cell2.getStringCellValue().trim()) || cell2.getStringCellValue() == null){
        				continue;
        			}else {
        				//若电话号码1不为空，则开始读取此行数据。
        				String[] onelineString = new String[10];
        				for (int j = 0; j < 10; j++) {
        					String cellvalue = "";
        					Cell cell = row.getCell(j);
        					if(cell != null){
        						switch (cell.getCellType()) {
        							case Cell.CELL_TYPE_BLANK:
        								cellvalue = "";
        								break;
        							case Cell.CELL_TYPE_BOOLEAN:
        								cellvalue = Boolean.toString(cell.getBooleanCellValue());
        								break;
        							case Cell.CELL_TYPE_NUMERIC:
        								if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
        									cellvalue = String.valueOf(cell.getDateCellValue());
        								}else {
        									cell.setCellType(Cell.CELL_TYPE_STRING);
        									cellvalue = cell.getStringCellValue();
        								}
        								break;
        							case Cell.CELL_TYPE_STRING:
        								cellvalue = cell.getStringCellValue();
        								break;
        							case Cell.CELL_TYPE_ERROR:
        								cellvalue = "";
        								break;
        							case Cell.CELL_TYPE_FORMULA:
        								cell.setCellType(Cell.CELL_TYPE_STRING);
        								cellvalue = cell.getStringCellValue();
        								break;
        							default:
        								cellvalue = "";
        								break;
        						}
        					}else {
        						cellvalue = "";
        					}
        					onelineString[j] = cellvalue;
        				}
        				result.add(onelineString);
					}
    			}else {
					continue;
				}
			}
    	}
    	return result;
    }
}
