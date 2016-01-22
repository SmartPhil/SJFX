package com.xdf.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReader {
	//创建文件输入流  
    private BufferedReader reader = null;  
    //文件类型  
    private String fileType;  
      
    //文件二进制输入流  
    private InputStream is = null;  
    //当前sheet  
    private int currSheet;  
    //当前位置  
    private int currPosition;  
    //sheet数量  
    private int numOfSheets;  
    //HSSFWorkbook  
    HSSFWorkbook workbook = null;  
    //设置cell之间以空格分隔  
    private static String EXCEL_LINE_DELIMITER = "";
    //sheet
    private HSSFSheet sheet;
      
    //构造函数创建一个ExcelReader  
    public ExcelReader(String inputfile) throws IOException,Exception {  
        //判断参数是否为空或者没有意义  
        if(null == inputfile && "".equals(inputfile.trim())) {  
            throw new IOException("no input file specified");  
        }  
        //取得文件名后缀赋值给fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        //设置开始行为0  
        currPosition = 0;  
        //设置当前位置为0  
        currSheet = 0;  
        //创建输入流  
        is = new FileInputStream(inputfile);  
        //判断文件格式  
        if(fileType.equalsIgnoreCase("txt")) {  
            //如果是txt则直接创建BufferReader读取  
            reader = new BufferedReader(new InputStreamReader(is));  
        }  
        else if(fileType.equalsIgnoreCase("xls")) {  
            //如果是Excel文件则创建HSSFWorkbook读取  
            workbook = new HSSFWorkbook(is);  
            //设置sheet数  
            numOfSheets = workbook.getNumberOfSheets();
            this.sheet = workbook.getSheetAt(0);
        }else {  
            throw new Exception("File Type not Supported");  
        }  
          
    }  
      
    //函数readLine读取文本的一行  
    public String readLine() throws IOException {  
        //如果是txt则通过reader读取  
        if(fileType.equalsIgnoreCase("txt")) {  
            String str = reader.readLine();  
            //空行则略去，直接读取下一行  
            while(str.trim().equals("")) {  
                str = reader.readLine();  
            }  
            return str;  
        }  
        //如果是xls文件则通过POI提供给的API读取文件  
        else if(fileType.equalsIgnoreCase("xls")) {  
            //根据currSheet值获得当前的sheet  
            HSSFSheet sheet = workbook.getSheetAt(currSheet);  
            //判断当前行是否到当前sheet的结尾  
            if(currPosition > sheet.getLastRowNum()) {  
                //当前行位置清零  
                currPosition = 0;  
                //判断是否还有Sheet  
                while(currSheet != numOfSheets -1){  
                    //得到下一个sheet  
                    sheet = workbook.getSheetAt(currSheet+1);  
                    //判断当前行是否到当前sheet的结尾  
                    if(currPosition == sheet.getLastRowNum()) {  
                        currSheet++;  
                        continue;  
                    }else {  
                        //获取当前行数  
                        int row = currPosition;  
                        currPosition++;  
                        //读取当前行数据  
                        return getLine(sheet,row);  
                    }  
                }  
                return null;  
            }  
            //获取当前行数  
            int row = currPosition;  
            currPosition++;  
            //读取当前行数据  
            return getLine(sheet,row);  
        }  
        return null;  
    }  
   
    //函数getLine返回sheet的一行数据  
    private String getLine (HSSFSheet sheet,int row) {  
        sheet = this.sheet;
    	//根据行数取得sheet的一行  
        HSSFRow rowLine = sheet.getRow(row);  
        //创建字符串缓冲区  
        StringBuffer buffer = new StringBuffer();  
        //获取当前行的列数  
        int filledColumns = rowLine.getLastCellNum();  
        HSSFCell cell = null;  
        //循环遍历所有列  
        for(int i=0;i<filledColumns;i++) {  
            //取得当前cell  
            cell = rowLine.getCell(i);  
            String cellValue = null;  
            if(null != cell) {  
                //判断当前cell的type  
                switch(cell.getCellType()) {  
                    //如果当前cell的type为NUMERIC  
                    case HSSFCell.CELL_TYPE_NUMERIC : {  
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
    public List<String> readAllData(){
    	//结果列表
    	List<String> result = new ArrayList<String>();
    	//int rowNum = this.sheet.getLastRowNum();
    	Iterator<Row> iterator = this.sheet.rowIterator();
    	System.out.println("iterator遍历器开始：");
    	while (iterator.hasNext()) {
			HSSFRow row = (HSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			String dataString = "";
			while (cellIterator.hasNext()) {
				HSSFCell cell = (HSSFCell) cellIterator.next();
				String cellValue = cell.getStringCellValue();
				dataString += (cellValue + ",");
			}
			System.out.println(dataString);
			result.add(dataString);
		}
    	
    	return result;
    }
}
