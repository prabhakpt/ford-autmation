package com.ford.log.automation.playarea;

import java.util.Timer;

import com.ford.logs.aumation.FordLogsAutomationNoAssertions;

public class ScheduleTest {
	public static void main(String[] args) throws InterruptedException {
		//SchedulerPlay st = new SchedulerPlay();
		FordLogsAutomationNoAssertions ford = new FordLogsAutomationNoAssertions();
		Timer timer = new Timer();
		timer.schedule(ford, 0,300000);
		
		/*for (int i = 0; i <= 5; i++) {
			System.out.println("Execution of main method...");
			Thread.sleep(2000);
			if(i==5){
				System.out.println("application is terminating...");
				System.exit(0);
			}
		}*/
	}
}	
