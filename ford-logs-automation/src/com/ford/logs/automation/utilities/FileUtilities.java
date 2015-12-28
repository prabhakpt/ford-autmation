package com.ford.logs.automation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtilities {
	
		public static String getLastDownloadedLogName(String fileName){
		String currentFilePath = System.getProperty("user.dir");
		String filename = currentFilePath+"/resources/"+fileName+".txt";
		FileInputStream file = null;

		try{
			file = new FileInputStream(filename);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			String line;
			while((line = reader.readLine()) != null){ 
			  System.out.println(line);
			  return line;
			}
			reader.close();
		} catch (IOException e) {
		  e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
		
		public static void writeLastLogNameDownloaded(String fileName,String logName){
			System.out.println("log Name is"+logName);
			String currentFilePath = System.getProperty("user.dir");
			String filename = currentFilePath+"/resources/"+fileName+".txt";
			FileOutputStream file = null;
			Writer write = null;
			try {
				file = new FileOutputStream(filename);
				write = new BufferedWriter(new OutputStreamWriter(file));
				write.write(logName);
				System.out.println("Successfully written in file..");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void main(String[] args) {
			//writeLastLogNameDownloaded("hello.......");
		}
}
