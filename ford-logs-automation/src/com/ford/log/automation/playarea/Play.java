package com.ford.log.automation.playarea;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Play {

	final static Logger log= Logger.getLogger(Play.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		String log4jPorperties = "E:\\automation\\testing\\ford-logs-automation\\classes\\log4j.properties";
		PropertyConfigurator.configure(log4jPorperties);
		log.info("ownerservicesa_2_stdout_15.12.22_02.01.57.log size is"+("ownerservicesa_2_stdout_15.12.22_02.01.57.log").length());
	}
}
