package com.ford.logs.aumation;

import static com.ford.logs.automation.utilities.BrowserEvents.clickByLocator;
import static com.ford.logs.automation.utilities.BrowserEvents.closeDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.createDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.enterText;
import static com.ford.logs.automation.utilities.BrowserEvents.findElement;
import static com.ford.logs.automation.utilities.BrowserEvents.getSizeuptoPreviousLog;
import static com.ford.logs.automation.utilities.BrowserEvents.getValueOfXPath;
import static com.ford.logs.automation.utilities.BrowserEvents.isTextPresent;
import static com.ford.logs.automation.utilities.BrowserEvents.openUrl;
import static com.ford.logs.automation.utilities.BrowserEvents.selectByVisibleText;
import static com.ford.logs.automation.utilities.XPathConstants.URL;
import static com.ford.logs.automation.utilities.XPathConstants.browseURL;
import static com.ford.logs.automation.utilities.XPathConstants.continueButton;
import static com.ford.logs.automation.utilities.XPathConstants.hteamText;
import static com.ford.logs.automation.utilities.XPathConstants.hteamURL;
import static com.ford.logs.automation.utilities.XPathConstants.manageActiveLogs;
import static com.ford.logs.automation.utilities.XPathConstants.prod;
import static com.ford.logs.automation.utilities.XPathConstants.selectButton;
import static com.ford.logs.automation.utilities.XPathConstants.selectProject;
import static com.ford.logs.automation.utilities.XPathConstants.tableRows;
import static com.ford.logs.automation.utilities.XPathConstants.wasTools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ford.logs.automation.utilities.FileUtilities;
import com.ford.logs.automation.utilities.XPathConstants.BlackScreenCreds;

public class FordLogsAutomation {

	@Before
	public void loadDriver(){
		createDriver("firefox");
	}
	
	@Test
	public void autoDownloadLogs() throws InterruptedException{
		openUrl(URL);
		System.out.println("Please enter username and rsa token and click on login button");
		Thread.sleep(20000);
		enterText(hteamURL[0], hteamURL[1], hteamURL[2]);
		Thread.sleep(3000);
		clickByLocator(browseURL[0], browseURL[1]);
		
		System.out.println("checking is black screen loaded");
		if(isTextPresent(BlackScreenCreds.bodyText)){
			System.out.println("black screen loaded");
			enterText(BlackScreenCreds.UserId[0], BlackScreenCreds.UserId[1], BlackScreenCreds.UserId[2]);
			enterText(BlackScreenCreds.Pwd[0], BlackScreenCreds.Pwd[1], BlackScreenCreds.Pwd[2]);
			clickByLocator(BlackScreenCreds.concourButton[0], BlackScreenCreds.concourButton[1]);
		}
		if(isTextPresent(hteamText)){
			System.out.println("directly hitting prod link");
			clickByLocator(prod[0], prod[1]);
			Thread.sleep(4000);
		}else if(isTextPresent(manageActiveLogs[1])){
			Thread.sleep(3000);
			clickByLocator(manageActiveLogs[0], manageActiveLogs[1]);
			Thread.sleep(3000);
			clickByLocator(prod[0], prod[1]);
			Thread.sleep(5000);
		}else{
			Thread.sleep(4000);
			System.out.println("starting WAS Tools");
			clickByLocator(wasTools[0], wasTools[1]);
			selectByVisibleText(selectProject[0],selectProject[1],selectProject[2]);
			clickByLocator(selectButton[0], selectButton[1]);
			Thread.sleep(3000);
			clickByLocator(manageActiveLogs[0], manageActiveLogs[1]);
			Thread.sleep(3000);
			clickByLocator(prod[0], prod[1]);
			Thread.sleep(5000);
		}
		clickByLocator(continueButton[0], continueButton[1]);
		Thread.sleep(6000);
		System.out.println("loading ford logs.");
		
		int lastLogSize = getSizeuptoPreviousLog();
		
		String nextLog = null;
		String logName = null;
		
		// loop through all trs and tds
		for(int i=lastLogSize-1;i>=1;i--){
			System.out.println("Getting "+i+"Row value");
				System.out.print("Getting "+i+" Row  column value====");
				logName = getValueOfXPath(tableRows[0], tableRows[1]+"["+i+"]/td["+3+"]");
				int lentght = logName.length();
				if(lentght> 43){
					nextLog = logName;
					System.out.println("selecting the log "+logName+"to download.");
					findElement(tableRows[0], tableRows[1]+"["+i+"]/td["+1+"]").click();
				}
		}
		Thread.sleep(5000);
		logName = FileUtilities.getLastDownloadedLogName();
		// taking backup of last log file name downloaded in text file
		if(null != nextLog){
			clickByLocator("name", "multiRetrievalBtn");
			System.out.println("The log name to be stored in file is: "+nextLog);
			FileUtilities.writeLastLogNameDownloaded(nextLog);
		}else{
			System.out.println("No new files to download");
		}
		
		/*clickByLocator(retrieveLogButton[0], retrieveLogButton[1]);
		Thread.sleep(4000);
		clickByLocator(downloadZipLog[0], downloadZipLog[1]);
		Thread.sleep(20000);*/
	}
	
	@After
	public void closeFordDriver(){
		closeDriver();
	}

}
