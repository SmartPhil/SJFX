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
	//�����ļ�������  
    private BufferedReader reader = null;  
    //�ļ�����  
    private String fileType;  
      
    //�ļ�������������  
    private InputStream is = null;  
    //��ǰsheet  
    private int currSheet;  
    //��ǰλ��  
    private int currPosition;  
    //sheet����  
    private int numOfSheets;  
    //HSSFWorkbook  
    HSSFWorkbook workbook = null;  
    //����cell֮���Կո�ָ�  
    private static String EXCEL_LINE_DELIMITER = "";
    //sheet
    private HSSFSheet sheet;
      
    //���캯������һ��ExcelReader  
    public ExcelReader(String inputfile) throws IOException,Exception {  
        //�жϲ����Ƿ�Ϊ�ջ���û������  
        if(null == inputfile && "".equals(inputfile.trim())) {  
            throw new IOException("no input file specified");  
        }  
        //ȡ���ļ�����׺��ֵ��fileType  
        this.fileType = inputfile.substring(inputfile.lastIndexOf(".")+1);  
        //���ÿ�ʼ��Ϊ0  
        currPosition = 0;  
        //���õ�ǰλ��Ϊ0  
        currSheet = 0;  
        //����������  
        is = new FileInputStream(inputfile);  
        //�ж��ļ���ʽ  
        if(fileType.equalsIgnoreCase("txt")) {  
            //�����txt��ֱ�Ӵ���BufferReader��ȡ  
            reader = new BufferedReader(new InputStreamReader(is));  
        }  
        else if(fileType.equalsIgnoreCase("xls")) {  
            //�����Excel�ļ��򴴽�HSSFWorkbook��ȡ  
            workbook = new HSSFWorkbook(is);  
            //����sheet��  
            numOfSheets = workbook.getNumberOfSheets();
            this.sheet = workbook.getSheetAt(0);
        }else {  
            throw new Exception("File Type not Supported");  
        }  
          
    }  
      
    //����readLine��ȡ�ı���һ��  
    public String readLine() throws IOException {  
        //�����txt��ͨ��reader��ȡ  
        if(fileType.equalsIgnoreCase("txt")) {  
            String str = reader.readLine();  
            //��������ȥ��ֱ�Ӷ�ȡ��һ��  
            while(str.trim().equals("")) {  
                str = reader.readLine();  
            }  
            return str;  
        }  
        //�����xls�ļ���ͨ��POI�ṩ����API��ȡ�ļ�  
        else if(fileType.equalsIgnoreCase("xls")) {  
            //����currSheetֵ��õ�ǰ��sheet  
            HSSFSheet sheet = workbook.getSheetAt(currSheet);  
            //�жϵ�ǰ���Ƿ񵽵�ǰsheet�Ľ�β  
            if(currPosition > sheet.getLastRowNum()) {  
                //��ǰ��λ������  
                currPosition = 0;  
                //�ж��Ƿ���Sheet  
                while(currSheet != numOfSheets -1){  
                    //�õ���һ��sheet  
                    sheet = workbook.getSheetAt(currSheet+1);  
                    //�жϵ�ǰ���Ƿ񵽵�ǰsheet�Ľ�β  
                    if(currPosition == sheet.getLastRowNum()) {  
                        currSheet++;  
                        continue;  
                    }else {  
                        //��ȡ��ǰ����  
                        int row = currPosition;  
                        currPosition++;  
                        //��ȡ��ǰ������  
                        return getLine(sheet,row);  
                    }  
                }  
                return null;  
            }  
            //��ȡ��ǰ����  
            int row = currPosition;  
            currPosition++;  
            //��ȡ��ǰ������  
            return getLine(sheet,row);  
        }  
        return null;  
    }  
   
    //����getLine����sheet��һ������  
    private String getLine (HSSFSheet sheet,int row) {  
        sheet = this.sheet;
    	//��������ȡ��sheet��һ��  
        HSSFRow rowLine = sheet.getRow(row);  
        //�����ַ���������  
        StringBuffer buffer = new StringBuffer();  
        //��ȡ��ǰ�е�����  
        int filledColumns = rowLine.getLastCellNum();  
        HSSFCell cell = null;  
        //ѭ������������  
        for(int i=0;i<filledColumns;i++) {  
            //ȡ�õ�ǰcell  
            cell = rowLine.getCell(i);  
            String cellValue = null;  
            if(null != cell) {  
                //�жϵ�ǰcell��type  
                switch(cell.getCellType()) {  
                    //�����ǰcell��typeΪNUMERIC  
                    case HSSFCell.CELL_TYPE_NUMERIC : {  
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
    public List<String> readAllData(){
    	//����б�
    	List<String> result = new ArrayList<String>();
    	//int rowNum = this.sheet.getLastRowNum();
    	Iterator<Row> iterator = this.sheet.rowIterator();
    	System.out.println("iterator��������ʼ��");
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
