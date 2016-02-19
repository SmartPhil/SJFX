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
    	System.out.println("������");
       //�жϲ����Ƿ�Ϊ�ջ���û������  
        /*if(null == inputfile && "".equals(inputfile.trim())) {  
              
        } */
        //ȡ���ļ�����׺��ֵ��fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        
        //����������  
        /*try {
			is = new FileInputStream(inputfile);
		} catch (FileNotFoundException e1) {
			System.out.println("����������ʧ�ܣ�" + e1.getMessage());
		}*/
        //�ж��ļ���ʽ  
        try {
			workbook = new XSSFWorkbook(inputfile);
		} catch (Exception e) {
			System.out.println("����XSSFWorkbookʧ�ܣ�" + e.getMessage());
		}  
        //����sheet��  
        this.sheet = workbook.getSheetAt(0);
        /*if(fileType.equalsIgnoreCase("txt")) {  
            //�����txt��ֱ�Ӵ���BufferReader��ȡ  
            reader = new BufferedReader(new InputStreamReader(is));  
        }  
        else if(fileType.equalsIgnoreCase("xlsx")) {  
            //�����Excel�ļ��򴴽�HSSFWorkbook��ȡ  
           
        }*/
    }  
   
    //����getLine����sheet��һ������  
   /* private String getLine (XSSFSheet sheet,int row) {  
        sheet = this.sheet;
    	//��������ȡ��sheet��һ��  
        XSSFRow rowLine = sheet.getRow(row);  
        //�����ַ���������  
        StringBuffer buffer = new StringBuffer();  
        //��ȡ��ǰ�е�����  
        int filledColumns = rowLine.getLastCellNum();  
        XSSFCell cell = null;  
        //ѭ������������  
        for(int i=0;i<filledColumns;i++) {  
            //ȡ�õ�ǰcell  
            cell = rowLine.getCell(i);  
            String cellValue = null;  
            if(null != cell) {  
                //�жϵ�ǰcell��type  
                switch(cell.getCellType()) {  
                    //�����ǰcell��typeΪNUMERIC  
                    case XSSFCell.CELL_TYPE_NUMERIC : {  
                        //�жϵ�ǰcell�Ƿ�ΪDate  
                        if(HSSFDateUtil.isCellDateFormatted(cell)){  
                            //�����Date���ͣ�ȡ�ø�Cell��Dateֵ  
                            Date date = cell.getDateCellValue();  
                            //��Dateת���ɱ��ظ�ʽ���ַ���  
                            cellValue = new  java.text.SimpleDateFormat( " yyyy-MM-dd HH:mm " ).format(cell.getDateCellValue());  
                        }  
                        //����Ǵ�����  
                        else {  
                            //ȡ�õ�ǰcell����ֵ  
                            Integer num = new Integer((int)cell.getNumericCellValue());//Ĭ�Ϸ���ʱdouble����  
                            cellValue = String.valueOf(num);  
                        }  
                        break;  
                    }  
                    //�����ǰcell��typeΪString  
                    case HSSFCell.CELL_TYPE_STRING :  
                        //ȡ�õ�ǰshell���ַ���  
                        cellValue = cell.getStringCellValue().replaceAll("\'", "\""); 
                        break;  
                    //Ĭ�ϵ�cellֵ  
                    default:  
                        cellValue = "";  
                }  
            }else {  
                cellValue = "";  
            }  
            //��ÿ���ֶ�֮�����ָ���  
            buffer.append(cellValue).append(EXCEL_LINE_DELIMITER);  
        }  
        //���ַ������ظ��е�����  
        return buffer.toString();  
    }  */
      
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
