package com.ford.logs.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelData {
    Logger log = Logger.getLogger(ReadExcelData.class);

    public static void updateFordInfo(String logtype, String logName) throws IOException {
        boolean isCellValUpdated = false;
        System.out.println("Started Updating Excel file");
        String currentFilePath = System.getProperty("user.dir");
        FileInputStream fis = new FileInputStream(new File(String.valueOf(currentFilePath) + "/resources/ford-log-details.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        
        for(Cell cell :row){
        	if(cell.getStringCellValue().equals(logtype)){
        		isCellValUpdated = true;
        		System.out.println("column index:"+cell.getColumnIndex());
        		int cellIndex = cell.getColumnIndex();
        		row = sheet.getRow(1);
        		System.out.println("cell value:"+row.getCell(cellIndex).getStringCellValue());
        		Cell cellVal = row.getCell(cellIndex);
        		cellVal.setCellValue(logName);
        	}
        }
        if(isCellValUpdated){
        	System.out.println("Call object value is updated and ready update in file");
        }
        fis.close();
        FileOutputStream fos = new FileOutputStream(new File(String.valueOf(currentFilePath) + "/resources/ford-log-details.xls"));
        workbook.write((OutputStream)fos);
        fos.close();
        System.out.println("Done with Updating Excel file");
    }
    
    public static String getFordLogDetails(String cellName) {
        String currentFilePath = System.getProperty("user.dir");
        boolean isCellNameAvaliable = false;
        String cellVal = null;
        try {
            System.out.println("Started Reading xcel file in " + currentFilePath + " path");
            FileInputStream fis = new FileInputStream(new File(String.valueOf(currentFilePath) + "/resources/ford-log-details.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);

            for(Cell cell :row){
            	if(cell.getStringCellValue().equals(cellName)){
            		isCellNameAvaliable = true;
            		System.out.println("column index:"+cell.getColumnIndex());
            		int cellIndex = cell.getColumnIndex();
            		row = sheet.getRow(1);
            		cellVal = row.getCell(cellIndex).getStringCellValue();
            		System.out.println("cell value:"+row.getCell(cellIndex).getStringCellValue());
            	}
            }
            fis.close();
            if(isCellNameAvaliable){
            	System.out.println("Cell name aVaiable");
            }else{
            	System.out.println("Sorry, Given cell name is not avaialble. Make shure column name in excel sheet should be equal to the input filed name");
            }
            return cellVal;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
       // ReadExcelData.getFordLogInfo("userName");
    	//getFordLogsDetails("Balck_screen_pwd");
    	try {
			updateFordInfo("zip_log_name", "prabhakar");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
