package com.ford.logs.log4j.customfileappender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;

public class CustomFileAppender extends FileAppender{
	@Override
	public void setFile(String fileName){
		if(fileName.indexOf("%timestamp") > 0){
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
			fileName = fileName.replaceAll("%timestamp", dateFormat.format(date));
		}
		super.setFile(fileName);
	}
}
