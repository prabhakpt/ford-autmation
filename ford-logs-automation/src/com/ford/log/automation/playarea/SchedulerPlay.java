package com.ford.log.automation.playarea;

import java.util.Date;
import java.util.TimerTask;

public class SchedulerPlay extends TimerTask{

	@Override
	public void run() {
		testScheduler();
		
	}
	
	public void testScheduler(){
		Date date = new Date();
		System.out.println("date is:::"+date);
	}
}