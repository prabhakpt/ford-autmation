package com.ford.logs.automation.utilities;

public class XPathConstants {
	
	public static String URL = "https://webvpn213.ford.com/+CSCOE+/logon.html";
	public static String userId[] = {"id","username","spenmet1"};
	public static String password[] = {"id","password_input"};
	public static String submitBtn[] = {"name","Login"};
	
	public static String autoitScriptPath = System.getProperty("user.dir")+"\\rsatoken.exe";
	public static String autoitDownloadLogScriptPath = System.getProperty("user.dir")+"\\download.exe";
	
	public static String confirmPwd[] = {"id","password"};
	public static String continueBtn[] = {"name","Continue"}; 
	
	public static String hteamURL[] = {"id","unicorn_form_url","www.hteam.ford.com"};
	public static String browseURL[] = {"id","browse_text"};
	
	public static String fileName = "";
	public static String fordLogZip = "fordlogzip";
	public static String logFile = "fordlog";
	//after hteam browser
	public static String hteamText = "PROD";
	public static String managerActiveLogsText = "Manage Active Logs";
	public static String wasTools[] = {"link","WASTools"};
	public static String selectProject[] = {"name","projectName","www.ownerservicesa.ford.com"}; 
	public static String selectButton[] = {"name","page"}; 
	public static String manageActiveLogs[] = {"link","Manage Active Logs"};
	public static String prod[] = {"link","PROD"};
	
	// default all logs selected and using continue
	public static String continueButton[] = {"name","activeLogBtn"};
	public static String retrieveLogButton[] = {"name","retrieveLogBtn"};
	
	public static String downloadZipLog[] = {"xpath","//table[@id='row']/tbody/tr[","]/td[4]/input[@title='Download File']"};
	
	public static String retrieveLogTab[] = {"xpath","//table[@id='row']/tbody/tr"};
	
	//after loading logs
	public static String tableRows[] = {"xpath","//table[@id='row']/tbody/tr"};
	public static String multiRetrievalBtn[] = {"name","multiRetrievalBtn"};
	public static String zipColumnName[] = {"xpath","//table[@class='thinBorder']/tbody/tr[6]/td[2]"};
	public static String paste = "v";
	
	public static class BlackScreenCreds{
		public static String bodyText = "WEB SINGLE LOGIN";
		public static String UserId[]={"id","ADloginUserIdInput","SPENMET1"};
		public static String Pwd[]={"id","ADloginPasswordInput","owner8"};
		public static String concourButton[] = {"id","ADloginWSLSubmitButton"};
	}
}
