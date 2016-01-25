package com.ford.logs.aumation;

import static com.ford.logs.automation.utilities.BrowserEvents.clickByLocator;
import static com.ford.logs.automation.utilities.BrowserEvents.closeDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.createDriver;
import static com.ford.logs.automation.utilities.BrowserEvents.doPaste;
import static com.ford.logs.automation.utilities.BrowserEvents.downloadZipLogFile;
import static com.ford.logs.automation.utilities.BrowserEvents.enterText;
import static com.ford.logs.automation.utilities.BrowserEvents.findElement;
import static com.ford.logs.automation.utilities.BrowserEvents.getSizeuptoPreviousLog;
import static com.ford.logs.automation.utilities.BrowserEvents.getValue;
import static com.ford.logs.automation.utilities.BrowserEvents.getValueOfXPath;
import static com.ford.logs.automation.utilities.BrowserEvents.isTextPresent;
import static com.ford.logs.automation.utilities.BrowserEvents.openUrl;
import static com.ford.logs.automation.utilities.BrowserEvents.selectByVisibleText;
import static com.ford.logs.automation.utilities.BrowserEvents.waitForSeconds;
import static com.ford.logs.automation.utilities.XPathConstants.URL;
import static com.ford.logs.automation.utilities.XPathConstants.autoitDownloadLogScriptPath;
import static com.ford.logs.automation.utilities.XPathConstants.autoitScriptPath;
import static com.ford.logs.automation.utilities.XPathConstants.browseURL;
import static com.ford.logs.automation.utilities.XPathConstants.confirmPwd;
import static com.ford.logs.automation.utilities.XPathConstants.continueBtn;
import static com.ford.logs.automation.utilities.XPathConstants.continueButton;
import static com.ford.logs.automation.utilities.XPathConstants.fordLogZip;
import static com.ford.logs.automation.utilities.XPathConstants.hteamText;
import static com.ford.logs.automation.utilities.XPathConstants.hteamURL;
import static com.ford.logs.automation.utilities.XPathConstants.logFile;
import static com.ford.logs.automation.utilities.XPathConstants.loginFailed;
import static com.ford.logs.automation.utilities.XPathConstants.manageActiveLogs;
import static com.ford.logs.automation.utilities.XPathConstants.moreInfoToLogin;
import static com.ford.logs.automation.utilities.XPathConstants.multiRetrievalBtn;
import static com.ford.logs.automation.utilities.XPathConstants.password;
import static com.ford.logs.automation.utilities.XPathConstants.paste;
import static com.ford.logs.automation.utilities.XPathConstants.prod;
import static com.ford.logs.automation.utilities.XPathConstants.retrieveLogButton;
import static com.ford.logs.automation.utilities.XPathConstants.selectButton;
import static com.ford.logs.automation.utilities.XPathConstants.selectProject;
import static com.ford.logs.automation.utilities.XPathConstants.submitBtn;
import static com.ford.logs.automation.utilities.XPathConstants.tableRows;
import static com.ford.logs.automation.utilities.XPathConstants.userId;
import static com.ford.logs.automation.utilities.XPathConstants.userName;
import static com.ford.logs.automation.utilities.XPathConstants.wasTools;
import static com.ford.logs.automation.utilities.XPathConstants.zipColumnName;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ford.logs.automation.utilities.ReadExcelData;
import com.ford.logs.automation.utilities.XPathConstants.BlackScreenCreds;

public class FordLogsAutomationNoAssertions
extends TimerTask {
    static final Logger log = Logger.getLogger(FordLogsAutomationNoAssertions.class);

    public void loadDriver() {
        this.loggerConfiguration();
        createDriver("chrome");
    }

    public void autoDownloadLogs() throws InterruptedException, IOException {
        
    	loadDriver();
        
        openUrl(URL);
        
        log.info("Entering username and rsa token and click on login button");
        
        doLogin();
        
        this.tryLoggingIn();
        this.verifyingForMoreInfo();
        this.tryLoggingIn();
        this.verifyingForMoreInfo();
        Thread.sleep(2000);
        
        enterText(hteamURL[0], hteamURL[1], hteamURL[2]);
        Thread.sleep(3000);
        clickByLocator(browseURL[0], browseURL[1]);

        log.info("checking is black screen loaded");
        if (isTextPresent(BlackScreenCreds.bodyText)) {
        	//reading required data from Excel
        	String black_Screen_Password = ReadExcelData.getFordLogDetails(BlackScreenCreds.pwd[2]);
        	String userName= ReadExcelData.getFordLogDetails(BlackScreenCreds.userId[2]);
        	
            log.info("black screen loaded");
            enterText(BlackScreenCreds.userId[0], BlackScreenCreds.userId[1], userName);
            enterText(BlackScreenCreds.pwd[0], BlackScreenCreds.pwd[1], black_Screen_Password);
            clickByLocator(BlackScreenCreds.concourButton[0], BlackScreenCreds.concourButton[1]);
        }
        if (isTextPresent(hteamText)) {
            log.info("directly hitting prod link");
            clickByLocator(prod[0], prod[1]);
            Thread.sleep(4000);
        } else if (isTextPresent(manageActiveLogs[1])) {
            Thread.sleep(3000);
            clickByLocator(manageActiveLogs[0], manageActiveLogs[1]);
            Thread.sleep(3000);
            clickByLocator(prod[0], prod[1]);
            Thread.sleep(5000);
        } else {
            Thread.sleep(4000);
            log.info("starting WAS Tools");
            clickByLocator(wasTools[0], wasTools[1]);
            selectByVisibleText(selectProject[0], selectProject[1], selectProject[2]);
            clickByLocator(selectButton[0], selectButton[1]);
            Thread.sleep(3000);
            clickByLocator(manageActiveLogs[0], manageActiveLogs[1]);
            Thread.sleep(3000);
            clickByLocator(prod[0], prod[1]);
            Thread.sleep(5000);
        }
        clickByLocator(retrieveLogButton[0], retrieveLogButton[1]);
        if (downloadLogZIPFile()) {
            clickByLocator(prod[0], prod[1]);
            FordLogsAutomationNoAssertions.downloadRetrivalLogZipFile();
            Thread.sleep(5000);
        } else {
            clickByLocator(prod[0], prod[1]);
            Thread.sleep(5000);
        }
        clickByLocator(continueButton[0], continueButton[1]);
        Thread.sleep(6000);
        log.info("loading ford logs.");
        int lastLogSize = getSizeuptoPreviousLog(tableRows[0], tableRows[1]);
        String nextLog = null;
        String logName = null;
        String logNameInFile = null;
        int i = lastLogSize - 1;
        while (i >= 1) {
            log.info(("Getting " + i + "Row value"));
            System.out.print("Getting " + i + " Row  column value====");
            logName = getValueOfXPath(tableRows[0], (String.valueOf(tableRows[1]) + "[" + i + "]/td[" + 3 + "]"));
            int lentght = logName.length();
            if (lentght > 43) {
                nextLog = logName;
                log.info(("selecting the log " + logName + "to download."));
                findElement(tableRows[0], (String.valueOf(tableRows[1]) + "[" + i + "]/td[" + 1 + "]")).click();
            }
            --i;
        }
        Thread.sleep(5000);
        logNameInFile = ReadExcelData.getFordLogDetails(logFile);
        if (nextLog != null) {
            clickByLocator(multiRetrievalBtn[0], multiRetrievalBtn[1]);
            log.info(("The log name to be stored in file is: " + nextLog));
            String zipLogFileName = getValueOfXPath(zipColumnName[0], zipColumnName[1]);
            ReadExcelData.updateFordInfo(logFile, nextLog);
            log.info(("Storing Zip file name: " + zipLogFileName));
            ReadExcelData.updateFordInfo(fordLogZip, zipLogFileName);
        } else if (logNameInFile.equals(nextLog)) {
            log.info("No new files to update / download");
        }
        this.closeFordDriver();
    }

    public void closeFordDriver() {
        closeDriver();
    }

    public static void doLogin() {
        try {
            String userNameVal = ReadExcelData.getFordLogDetails(userName);
            
            System.out.println("User Name from excel sheet is:" + userNameVal);
            enterText(userId[0], userId[1], userNameVal);
            log.info("Running autoit script to copy rsa token");
            log.info(("path : " + autoitScriptPath));
            Runtime.getRuntime().exec(autoitScriptPath);
            Thread.sleep(3000);
            doPaste(password[0], password[1], paste);
            waitForSeconds(2);
            log.info(("Pasted pascode value is: " + getValue(password[0], password[1])));
            clickByLocator(submitBtn[0], submitBtn[1]);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void downloadRetrivalLogZipFile() {
        try {
            Runtime.getRuntime().exec(autoitDownloadLogScriptPath);
            Thread.sleep(4000);
        }
        catch (IOException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static boolean downloadLogZIPFile() {
        log.info("Started downloading zip log files");
        return downloadZipLogFile(tableRows[0], tableRows[1]);
    }

    public void loggerConfiguration() {
        BasicConfigurator.configure();
        String currentFilePath = System.getProperty("user.dir");
        String log4jPorperties = currentFilePath.concat("\\resources\\log4j.properties");
        PropertyConfigurator.configure(log4jPorperties);
    }

    public void tryLoggingIn() {
        int loginAttempts = 1;
        try {
            while (isTextPresent(loginFailed)) {
                log.info("please hold on for 50 seconds will retry to login again.");
                Thread.sleep(50000);
                if (++loginAttempts == 3) {
                    log.info("Attempting 3rd time with same user. Exiting from script.");
                    log.info("Please check user name entered in excel sheet and token value in rsatoken.au3 are correct then re-run the script once again");
                    System.exit(1);
                }
                FordLogsAutomationNoAssertions.doLogin();
            }
        }
        catch (InterruptedException ex) {
            log.info(("Exception wile attempting to login: Caused BY: " + ex.getMessage()));
        }
    }

    public void verifyingForMoreInfo() {
        log.info("verfying is other token for confirmatin page loaded");
        try {
            if (isTextPresent(moreInfoToLogin)) {
                log.info("Enter other token for confirmation");
                log.info("please hold on for 40 seconds will retry to login again.");
                Thread.sleep(40000);
                log.info("Running autoit script to copy rsa token");
                Runtime.getRuntime().exec(autoitScriptPath);
                Thread.sleep(4000);
                doPaste(confirmPwd[0], confirmPwd[1], paste);
                Thread.sleep(2000);
                log.info(("Pasted pascode value is: " + getValue(confirmPwd[0], confirmPwd[1])));
                clickByLocator(continueBtn[0], continueBtn[1]);
            }
        }
        catch (IOException | InterruptedException ex) {
            log.info(("Exception occured while logging for verifyig more Info: " + ex.getMessage()));
        }
    }

    @Override
    public void run() {
        try {
            this.autoDownloadLogs();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }
}
