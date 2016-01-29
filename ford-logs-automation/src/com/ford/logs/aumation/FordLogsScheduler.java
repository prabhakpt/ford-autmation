package com.ford.logs.aumation;

import java.util.Timer;

public class FordLogsScheduler {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting ford logs downlod autmation");
		FordLogsAutomationNoAssertions ford = new FordLogsAutomationNoAssertions();
		Timer timer = new Timer();
		long interval = 1000*60*60;
		timer.schedule(ford, 0,interval);
	}
}	
