package com.ford.logs.aumation;

import static com.ford.logs.automation.utilities.BrowserEvents.clickByLocator;
import static com.ford.logs.automation.utilities.BrowserEvents.closeDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.createDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.doPaste;
import static com.ford.logs.automation.utilities.BrowserEvents.downloadZipLogFile;
import static com.ford.logs.automation.utilities.BrowserEvents.enterText;
import static com.ford.logs.automation.utilities.BrowserEvents.findElement;
import static com.ford.logs.automation.utilities.BrowserEvents.getSizeuptoPreviousLog;
import static com.ford.logs.automation.utilities.BrowserEvents.getValueOfXPath;
import static com.ford.logs.automation.utilities.BrowserEvents.isTextPresent;
import static com.ford.logs.automation.utilities.BrowserEvents.openUrl;
import static com.ford.logs.automation.utilities.BrowserEvents.selectByVisibleText;
import static com.ford.logs.automation.utilities.BrowserEvents.waitForSeconds;
import static com.ford.logs.automation.utilities.ReadExcelData.getFordLogInfo;
import static com.ford.logs.automation.utilities.ReadExcelData.updateFordInfo;
import static com.ford.logs.automation.utilities.XPathConstants.URL;
import static com.ford.logs.automation.utilities.XPathConstants.autoitDownloadLogScriptPath;
import static com.ford.logs.automation.utilities.XPathConstants.autoitScriptPath;
import static com.ford.logs.automation.utilities.XPathConstants.browseURL;
import static com.ford.logs.automation.utilities.XPathConstants.confirmPwd;
import static com.ford.logs.automation.utilities.XPathConstants.continueBtn;
import static com.ford.logs.automation.utilities.XPathConstants.continueButton;
import static com.ford.logs.automation.utilities.XPathConstants.hteamText;
import static com.ford.logs.automation.utilities.XPathConstants.hteamURL;
import static com.ford.logs.automation.utilities.XPathConstants.manageActiveLogs;
import static com.ford.logs.automation.utilities.XPathConstants.multiRetrievalBtn;
import static com.ford.logs.automation.utilities.XPathConstants.password;
import static com.ford.logs.automation.utilities.XPathConstants.prod;
import static com.ford.logs.automation.utilities.XPathConstants.retrieveLogButton;
import static com.ford.logs.automation.utilities.XPathConstants.selectButton;
import static com.ford.logs.automation.utilities.XPathConstants.selectProject;
import static com.ford.logs.automation.utilities.XPathConstants.submitBtn;
import static com.ford.logs.automation.utilities.XPathConstants.tableRows;
import static com.ford.logs.automation.utilities.XPathConstants.userId;
import static com.ford.logs.automation.utilities.XPathConstants.wasTools;
import static com.ford.logs.automation.utilities.XPathConstants.zipColumnName;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ford.logs.automation.utilities.FileUtilities;
import com.ford.logs.automation.utilities.XPathConstants;
import com.ford.logs.automation.utilities.XPathConstants.BlackScreenCreds;

public class FordLogsAutomationNoAssertions extends TimerTask{
	
	final static Logger log= Logger.getLogger(FordLogsAutomationNoAssertions.class);
	
	public void loadDriver(){
		loggerConfiguration();
		createDriver("firefox");
	}
	
	public void autoDownloadLogs() throws InterruptedException, IOException{
		loadDriver();
		openUrl(URL);
		log.info("Entering username and rsa token and click on login button");
		
		doLogin();
		if(isTextPresent("Login failed.")){
			log.info("please hold on for 50 seconds will retry to login again.");
			Thread.sleep(30000);
			doLogin();
		}
		log.info("verfying is other token for confirmatin page loaded");
		if(isTextPresent("More information is required to log in.")){
			log.info("Enter other token for confirmation");
			log.info("please hold on for 50 seconds will retry to login again.");
			Thread.sleep(40000);
			log.info("Running autoit script to copy rsa token");
			Runtime.getRuntime().exec(autoitScriptPath);
			Thread.sleep(2000);
			doPaste(confirmPwd[0], confirmPwd[1],XPathConstants.paste);
			Thread.sleep(1000);
			clickByLocator(continueBtn[0], continueBtn[1]);
		}
		Thread.sleep(2000);
		enterText(hteamURL[0], hteamURL[1], hteamURL[2]);
		Thread.sleep(3000);
		clickByLocator(browseURL[0], browseURL[1]);
		
		log.info("checking is black screen loaded");
		
		if(isTextPresent(BlackScreenCreds.bodyText)){
			log.info("black screen loaded");
			enterText(BlackScreenCreds.UserId[0], BlackScreenCreds.UserId[1], BlackScreenCreds.UserId[2]);
			enterText(BlackScreenCreds.Pwd[0], BlackScreenCreds.Pwd[1], BlackScreenCreds.Pwd[2]);
			clickByLocator(BlackScreenCreds.concourButton[0], BlackScreenCreds.concourButton[1]);
		}
		
		if(isTextPresent(hteamText)){
			log.info("directly hitting prod link");
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
			log.info("starting WAS Tools");
			clickByLocator(wasTools[0], wasTools[1]);
			selectByVisibleText(selectProject[0],selectProject[1],selectProject[2]);
			clickByLocator(selectButton[0], selectButton[1]);
			Thread.sleep(3000);
			clickByLocator(manageActiveLogs[0], manageActiveLogs[1]);
			Thread.sleep(3000);
			clickByLocator(prod[0], prod[1]);
			Thread.sleep(5000);
		}
		
		clickByLocator(retrieveLogButton[0], retrieveLogButton[1]);
		
		if(downloadLogZIPFile()){
			clickByLocator(prod[0], prod[1]);
			downloadRetrivalLogZipFile();
			Thread.sleep(5000);
		}else{
			clickByLocator(prod[0], prod[1]);
			Thread.sleep(5000);
		}
		
		clickByLocator(continueButton[0], continueButton[1]);
		Thread.sleep(6000);
		log.info("loading ford logs.");
		
		int lastLogSize = getSizeuptoPreviousLog(tableRows[0],tableRows[1],"fordlog");// need to create constant
		
		String nextLog = null;
		String logName = null;
		String logNameInFile = null;
		
		// loop through all trs and tds
		for(int i=lastLogSize-1;i>=1;i--){
			log.info("Getting "+i+"Row value");
				System.out.print("Getting "+i+" Row  column value====");
				logName = getValueOfXPath(tableRows[0], tableRows[1]+"["+i+"]/td["+3+"]");
				int lentght = logName.length();
				if(lentght> 43){
					nextLog = logName;
					log.info("selecting the log "+logName+"to download.");
					findElement(tableRows[0], tableRows[1]+"["+i+"]/td["+1+"]").click();
				}
		}
		
		Thread.sleep(5000);
		logNameInFile = getFordLogInfo(XPathConstants.logFile); // need to create constant

		// taking backup of last log file name downloaded in text file
		if(null != nextLog){
			clickByLocator(multiRetrievalBtn[0], multiRetrievalBtn[1]);
			log.info("The log name to be stored in file is: "+nextLog);
			String zipLogFileName = getValueOfXPath(zipColumnName[0], zipColumnName[1]);
			updateFordInfo(XPathConstants.logFile,nextLog);
			FileUtilities.writeLastLogNameDownloaded(XPathConstants.fordLogZip, zipLogFileName);
		}else if(logNameInFile.equals(nextLog)){
			log.info("No new files to download");
		}
		closeFordDriver();
	}
	
	public void closeFordDriver(){
		closeDriver();
	}
	
	public static void doLogin(){
		try {
			enterText(userId[0], userId[1], userId[2]);
			log.info("Running autoit script to copy rsa token");
			log.info("path : "+autoitScriptPath);
			Runtime.getRuntime().exec(autoitScriptPath);
			Thread.sleep(4000);
			doPaste(password[0], password[1],XPathConstants.paste);
			waitForSeconds(1);
			clickByLocator(submitBtn[0], submitBtn[1]);
		} catch (IOException | InterruptedException e) {
				e.printStackTrace();
		}
	}
	
	public static void downloadRetrivalLogZipFile(){
		try {
			Runtime.getRuntime().exec(autoitDownloadLogScriptPath);
			Thread.sleep(4000);
		} catch (IOException | InterruptedException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public static boolean downloadLogZIPFile(){
		log.info("Started downloading zip log files");
		return downloadZipLogFile(tableRows[0],tableRows[1],"zipfile");
	}
	
	public void loggerConfiguration(){
		BasicConfigurator.configure();
		String log4jPorperties = "E:\\automation\\testing\\ford-logs-automation\\classes\\log4j.properties";
		PropertyConfigurator.configure(log4jPorperties);
	}

	@Override
	public void run() {
		try {
			autoDownloadLogs();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*public static void main(String[] args) {
		
		JUnitCore.main("com.ford.logs.aumation.FordLogsAutomation");
	}*/
}
