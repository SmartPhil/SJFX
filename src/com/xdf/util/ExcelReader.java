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
	//�����ļ�������  
    private BufferedReader reader = null;  
    //�ļ�����  
    private String fileType;  
      
    //�ļ�������������  
    private InputStream is = null;  
    
    //XSSFWorkbook  
    XSSFWorkbook workbook = null;
    //sheet
    private XSSFSheet sheet;
      
    //���캯������һ��ExcelReader  
    public ExcelReader(String inputfile) {  
        //ȡ���ļ�����׺��ֵ��fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        
        //�ж��ļ���ʽ  
        try {
			workbook = new XSSFWorkbook(inputfile);
		} catch (Exception e) {
			System.out.println("����XSSFWorkbookʧ�ܣ�" + e.getMessage());
		}  
        //����sheet��  
        this.sheet = workbook.getSheetAt(0);
    }  
   
    //close����ִ�����Ĺرղ���  
    public void close() {  
        //���id��Ϊ�գ���ر�InputStream�ļ�������  
        if(is != null) {  
            try {  
                is.close();  
            }catch(IOException e) {  
                is = null;  
            }  
        }  
          
        //���reader��Ϊ�գ���ر�BufferedReader�ļ�������  
        if(reader != null) {  
            try {  
                reader.close();  
            }catch(IOException e) {  
                reader = null;  
            }  
        }  
    }   
    
    //��ȡexcel����
    public List<String[]> readAllData(){
    	//����б�
    	List<String[]> result = new ArrayList<String[]>();
    	int columnNum = 0;
    	if(sheet.getRow(0) != null){
    		columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
    	}
    	
    	//List<HashMap<String, Object>> maps = new ArrayList<HashMap<String,Object>>();
    	//�õ��ļ����һ�е�numֵ
    	int rowNum = sheet.getLastRowNum() + 1;
    	if(rowNum > 1){
    		for (int i = 2; i < rowNum; i++) {
    			Row row = sheet.getRow(i);
    			//�жϵ绰����1�Ƿ�Ϊ�գ���Ϊ������Դ��С�
    			Cell cell2 = row.getCell(2);
    			if(cell2 != null){
    				cell2.setCellType(Cell.CELL_TYPE_STRING);
        			if("".equals(cell2.getStringCellValue().trim()) || cell2.getStringCellValue() == null){
        				continue;
        			}else {
        				//���绰����1��Ϊ�գ���ʼ��ȡ�������ݡ�
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
