package com.ford.logs.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcelData {
	static String className;
	public void getClassName(){
		className = this.getClass().getSimpleName();
	}
	
	public static void readExcel(){
		try {
			Workbook workBook =  Workbook.getWorkbook(new FileInputStream("E:/automation/docs/scripts.xls"));
			
			String[] sheetNames = workBook.getSheetNames();
			
			for(int sheetIndex=0;sheetIndex < sheetNames.length;sheetIndex++){
				Sheet sheet = workBook.getSheet(sheetNames[sheetIndex]);
				//return number of rows and columns in the sheet.
				int noOfRows = sheet.getRows();
				int noOfCols = sheet.getColumns();
				
				for(int i=0;i < noOfRows;i++){
					for(int j=0;j < noOfCols;j++){
						System.out.print(sheet.getCell(j, i).getContents()+"\t");
					}
					System.out.println("");
				}
			}
			
			System.out.println("Class name:"+className);
			
			//workBook.getSheet("Script_results");
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeToExcel(){
		try {
			String currentFilePath = System.getProperty("user.dir");
			WritableWorkbook newWorkBook = Workbook.createWorkbook(new FileOutputStream(currentFilePath+"/ford-log-details.xls"));
			WritableSheet sheet = newWorkBook.createSheet("sheet_results",0);
			
			Label label = new Label(1,1,"hello");
			sheet.addCell(label);
			
			newWorkBook.write();
			newWorkBook.close();
			
		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateFordInfo(String logtype, String logName) throws IOException{
		String currentFilePath = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(new File(currentFilePath+"/ford-log-details.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook (fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		if("fordlog".equals(logtype)){
			HSSFRow row1 = sheet.getRow(1);
			HSSFCell cell1 = row1.getCell(1);
			cell1.setCellValue(logName);
		}else if("fordlogzip".equals(logtype)){
			HSSFRow row1 = sheet.getRow(1);
			HSSFCell cell1 = row1.getCell(2);
			cell1.setCellValue(logName);
		}
		fis.close();
		FileOutputStream fos =new FileOutputStream(new File(currentFilePath+"/ford-log-details.xls"));
	    workbook.write(fos);
	    fos.close();
	}
	
	public static String getFordLogInfo(String logtype) {
		String currentFilePath = System.getProperty("user.dir");
		try{
			FileInputStream fis = new FileInputStream(new File(currentFilePath+"/ford-log-details.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook (fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			if("fordlog".equals(logtype)){
				HSSFRow row1 = sheet.getRow(1);
				HSSFCell cell = row1.getCell(1);
				System.out.println(cell.toString());
				return cell.toString();
			}else if("fordlogzip".equals(logtype)){
				HSSFRow row1 = sheet.getRow(1);
				HSSFCell cell = row1.getCell(2);
				System.out.println(cell.toString());
				return cell.toString();
			}
			fis.close();
			FileOutputStream fos =new FileOutputStream(new File(currentFilePath+"/ford-log-details.xls"));
		    workbook.write(fos);
		    fos.close();
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	public static void main(String[] args) {
		/*new ReadExcelData().getClassName();
		ReadExcelData.readExcel();*/
			//ReadExcelData.updateFordInfo("fordlog","ljl-adf-adf-afd-af");
			//ReadExcelData.updateFordInfo("fordlogzip","ljl-adf-adf-afd-af89.zip");
			//getFordLogInfo("fordlog");
			getFordLogInfo("fordlogzip");
	}
}
