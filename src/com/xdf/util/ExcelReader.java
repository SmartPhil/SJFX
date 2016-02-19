package com.xdf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;



import java.util.ArrayList;
import java.util.List;

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
    	System.out.println("进来了");
       //判断参数是否为空或者没有意义  
        /*if(null == inputfile && "".equals(inputfile.trim())) {  
              
        } */
        //取得文件名后缀赋值给fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        
        //创建输入流  
        /*try {
			is = new FileInputStream(inputfile);
		} catch (FileNotFoundException e1) {
			System.out.println("创建输入流失败：" + e1.getMessage());
		}*/
        //判断文件格式  
        try {
			workbook = new XSSFWorkbook(inputfile);
		} catch (Exception e) {
			System.out.println("创建XSSFWorkbook失败：" + e.getMessage());
		}  
        //设置sheet数  
        this.sheet = workbook.getSheetAt(0);
        /*if(fileType.equalsIgnoreCase("txt")) {  
            //如果是txt则直接创建BufferReader读取  
            reader = new BufferedReader(new InputStreamReader(is));  
        }  
        else if(fileType.equalsIgnoreCase("xlsx")) {  
            //如果是Excel文件则创建HSSFWorkbook读取  
           
        }*/
    }  
   
    //函数getLine返回sheet的一行数据  
   /* private String getLine (XSSFSheet sheet,int row) {  
        sheet = this.sheet;
    	//根据行数取得sheet的一行  
        XSSFRow rowLine = sheet.getRow(row);  
        //创建字符串缓冲区  
        StringBuffer buffer = new StringBuffer();  
        //获取当前行的列数  
        int filledColumns = rowLine.getLastCellNum();  
        XSSFCell cell = null;  
        //循环遍历所有列  
        for(int i=0;i<filledColumns;i++) {  
            //取得当前cell  
            cell = rowLine.getCell(i);  
            String cellValue = null;  
            if(null != cell) {  
                //判断当前cell的type  
                switch(cell.getCellType()) {  
                    //如果当前cell的type为NUMERIC  
                    case XSSFCell.CELL_TYPE_NUMERIC : {  
                        //判断当前cell是否为Date  
                        if(HSSFDateUtil.isCellDateFormatted(cell)){  
                            //如果是Date类型，取得该Cell的Date值  
                            Date date = cell.getDateCellValue();  
                            //把Date转换成本地格式的字符串  
                            cellValue = new  java.text.SimpleDateFormat( " yyyy-MM-dd HH:mm " ).format(cell.getDateCellValue());  
                        }  
                        //如果是纯数字  
                        else {  
                            //取得当前cell的数值  
                            Integer num = new Integer((int)cell.getNumericCellValue());//默认返回时double类型  
                            cellValue = String.valueOf(num);  
                        }  
                        break;  
                    }  
                    //如果当前cell的type为String  
                    case HSSFCell.CELL_TYPE_STRING :  
                        //取得当前shell的字符串  
                        cellValue = cell.getStringCellValue().replaceAll("\'", "\""); 
                        break;  
                    //默认的cell值  
                    default:  
                        cellValue = "";  
                }  
            }else {  
                cellValue = "";  
            }  
            //在每个字段之间插入分隔符  
            buffer.append(cellValue).append(EXCEL_LINE_DELIMITER);  
        }  
        //以字符串返回该行的数据  
        return buffer.toString();  
    }  */
      
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
    	if(columnNum > 0){
    		for (Row row : sheet) {
    			String[] onelineString = new String[columnNum];
				for (int i = 0; i < columnNum; i++) {
					String cellvalue = "";
					Cell cell = row.getCell(i);
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
					onelineString[i] = cellvalue;
				}
				result.add(onelineString);
			}
    	}
    	return result;
    }
}
