/*
 * Decompiled with CFR 0_110.
 */
package com.ford.logs.automation.utilities;

public class XPathConstants {
    public static String URL = "https://webvpn213.ford.com/+CSCOE+/logon.html";
    public static String webvpnURL = "https://webvpn213.ford.com";
    public static String[] userId = new String[]{"id", "username", "user_name"};// update column name here and in excel sheet according to you.
    public static String[] password = new String[]{"id", "password_input"};
    public static String[] submitBtn = new String[]{"name", "Login"};
    public static String moreInfoToLogin = "More information is required to log in.";
    public static String loginFailed = "Login failed.";
    public static String autoitScriptPath = String.valueOf(System.getProperty("user.dir")) + "\\resources\\rsatoken.exe";
    public static String autoitScriptNextTokenPath = String.valueOf(System.getProperty("user.dir")) + "\\resources\\RSANextToken.exe";
    public static String autoitDownloadLogScriptPath = String.valueOf(System.getProperty("user.dir")) + "\\resources\\download.exe";
    public static String[] confirmPwd = new String[]{"id", "password"};
    public static String[] continueBtn = new String[]{"name", "Continue"};
    public static String[] hteamURL = new String[]{"id", "unicorn_form_url", "www.hteam.ford.com"};
    public static String[] browseURL = new String[]{"id", "browse_text"};
    public static String fileName = "";
    public static String fordLogZip = "zip_log_name";// update column name here and in excel sheet according to you.
    public static String logFile = "log_name";// update column name here and in excel sheet according to you.
    public static String userName = "user_name";// update column name here and in excel sheet according to you.
    
    public static String outofMemoryError = "Error 500: javax.servlet.ServletException: java.lang.OutOfMemoryError: Java heap space";
    public static String prodText = "PROD";
    public static String managerActiveLogsText = "Manage Active Logs";
    public static String[] wasTools = new String[]{"link", "WASTools"};
    public static String[] selectProject = new String[]{"name", "projectName", "www.ownerservicesa.ford.com"};
    public static String[] selectButton = new String[]{"name", "page"};
    public static String[] manageActiveLogs = new String[]{"link", "Manage Active Logs"};
    public static String[] prod = new String[]{"link", "PROD"};
    public static String[] continueButton = new String[]{"name", "activeLogBtn"};
    public static String[] retrieveLogButton = new String[]{"name", "retrieveLogBtn"};
    public static String[] downloadZipLog = new String[]{"xpath", "//table[@id='row']/tbody/tr[", "]/td[4]/input[@title='Download File']"};
    public static String[] retrieveLogTab = new String[]{"xpath", "//table[@id='row']/tbody/tr"};
    public static String noLogs = "No files to select from";
    public static String exceptionInRetrieveLog = "An exception occurred while processing your request. Please try the request again.";
    public static String[] tableRows = new String[]{"xpath", "//table[@id='row']/tbody/tr"};
    public static String[] multiRetrievalBtn = new String[]{"name", "multiRetrievalBtn"};
    public static String[] zipColumnName = new String[]{"xpath", "//table[@class='thinBorder']/tbody/tr[6]/td[2]"};
    public static String paste = "v";
    public static class BlackScreenCreds{
		public static String bodyText = "WEB SINGLE LOGIN";
		public static String userId[]={"id","ADloginUserIdInput","user_name"};// update column name here and in excel sheet according to you.
		public static String pwd[]={"id","ADloginPasswordInput","balck_screen_pwd"};// update column name here and in excel sheet according to you.
		public static String concourButton[] = {"id","ADloginWSLSubmitButton"};
	}
}
