package com.ford.logs.automation.utilities;

import static com.ford.logs.automation.utilities.FileUtilities.getLastDownloadedLogName;
import static com.ford.logs.automation.utilities.XPathConstants.downloadZipLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import static com.ford.logs.automation.utilities.ReadExcelData.*;

public class BrowserEvents {

	private static WebDriver driver;
	
	final static Logger log= Logger.getLogger(BrowserEvents.class);
	
	public static void createDriver(String driverName) {
		switch (driverName) {
			case "firefox":
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "\\src\\config\\chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case "ie":
				System.setProperty(
						"webdriver.ie.driver",
						System.getProperty("user.dir").concat(
								"\\src\\config\\IEDriverServer.exe"));
				driver = new InternetExplorerDriver();
				break;
		}
	}

	public static void closeDriver() {
		log.info("Closing Driver");
		driver.quit();
	}

	public static void takeScreenShotOnfailure(String filename) {
		File image = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(image, new File(System.getProperty("user.dir")
					+ "//screenshots_on_failure//" + filename + "_"
					+ dataTime("MMddhhmmss") + ".jpg"));
		} catch (Throwable e) {
			e.printStackTrace();

		}
	}

	public static String dataTime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);

		return date.format(cal.getTime());
	}

	public static String generateEmail() {
		String userId = "prabha";
		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		log.info("user name is:" + (userId + date));
		return (userId + date) + "@gmail.com";
	}

	public static void waitForElementPresent(int maxWaitTime,
			String identifyBy, String locator) {

		long totalWaitTime = 0;
		try{
			while (!isElementPresent(identifyBy, locator)) {
				long time = 1000;
				totalWaitTime = totalWaitTime + time;
				Thread.sleep(1000);
				if (totalWaitTime >= maxWaitTime) {
					break;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static boolean isElementPresentStatus(int maxWaitTime, String identifyBy, String locator) throws InterruptedException {

			long totalWaitTime = 0;
			boolean isPresent = isElementPresent(identifyBy, locator);
			while (!isPresent) {
				log.info("Element not present..");
				long time = 1000;
				totalWaitTime = totalWaitTime + time;
				Thread.sleep(1000);
				isPresent = isElementPresent(identifyBy, locator);
				if (totalWaitTime >= maxWaitTime) {
					break;
				}
			}
			return isPresent;
		}
	

	public static boolean isElementPresent(String identifyBy, String locator) {

		// Use findElements as it does not throw an exception if the element doesn't exist

		try {
			if (identifyBy.equalsIgnoreCase("xpath")) {
				if (driver.findElements(By.xpath(locator)).size() > 0) {
					return true;
				}
			}
			if (identifyBy.equalsIgnoreCase("link")) {
				if (driver.findElements(By.linkText(locator)).size() > 0) {
					return true;
				}
			}
			if (identifyBy.equalsIgnoreCase("id")) {
				if (driver.findElements(By.id(locator)).size() > 0) {
					return true;
				}
			}
			if (identifyBy.equalsIgnoreCase("name")) {
				if (driver.findElements(By.name(locator)).size() > 0) {
					return true;
				}
			}
			if (identifyBy.equalsIgnoreCase("css")) {
				if (driver.findElements(By.cssSelector(locator)).size() > 0) {
					return true;
				}
			}
			if(identifyBy.equals("tagName")){
				if(driver.findElements(By.tagName("body")).size()>0){
					return true;
				}
			}
		} catch (Exception ex) {
			log.info("Exception occured..");
			// ex.printStackTrace();

		}
		log.info("Returning false...");
		return false;

	}

	public static String getTooltip(String identityBy, String locator) {
		// //article[@id='post-542']/div/header/div/div/span/a
		String title = null;
		if (getWebElement(identityBy, locator) != null) {
			title = getWebElement(identityBy, locator).getAttribute("title");
		}

		return title;

	}

	public static WebElement getWebElement(String identifyBy, String locator) {
		WebElement webElement = null;
		// wait for the element present
		waitForElementPresent(1000, identifyBy, locator);
		// once success execute..
		if (identifyBy.equalsIgnoreCase("xpath")) {
			webElement = driver.findElement(By.xpath(locator));
		} else if (identifyBy.equalsIgnoreCase("id")) {
			webElement = driver.findElement(By.id(locator));
		} else if (identifyBy.equalsIgnoreCase("name")) {
			webElement = driver.findElement(By.name(locator));
		} else if (identifyBy.equalsIgnoreCase("css")) {
			webElement = driver.findElement(By.cssSelector(locator));
		} else if (identifyBy.equalsIgnoreCase("link")) {
			webElement = driver.findElement(By.linkText(locator));
		} else if (identifyBy.equalsIgnoreCase("partialLinkText")) {
			webElement = driver.findElement(By.partialLinkText(locator));
		}else if(identifyBy.equalsIgnoreCase("tagName")){
			webElement = driver.findElement(By.tagName("body"));
		}
		return webElement;
	}

	public static String getselectedLabel(String identifyBy, String locator)
			throws InterruptedException {
		waitForElementPresent(10000, identifyBy, locator);

		if (identifyBy.equalsIgnoreCase("xpath")) {
			Select selectbox = new Select(driver.findElement(By.xpath(locator)));
			String option1 = selectbox.getFirstSelectedOption().getText();
			return option1;

		} else if (identifyBy.equalsIgnoreCase("id")) {

			Select selectbox = new Select(driver.findElement(By.id(locator)));
			String option1 = selectbox.getFirstSelectedOption().getText();
			return option1;

		} else if (identifyBy.equalsIgnoreCase("name")) {
			Select selectbox = new Select(driver.findElement(By.name(locator)));
			String option1 = selectbox.getFirstSelectedOption().getText();
			return option1;
		}

		else
			return null;
	}

	public static String getselectedLabelText(String identifyBy, String locator)
			throws InterruptedException {
		waitForElementPresent(10000, identifyBy, locator);
		if (identifyBy.equalsIgnoreCase("xpath")) {
			String val = getValue("xpath", locator);
			return getText("xpath", "//option[@value='" + val + "']");
			/*
			 * Select selectbox1 = new Select(driver.findElement(By
			 * .xpath("selectWithLabelsOnly"))); String option1 =
			 * selectbox1.getFirstSelectedOption().getText(); return option1;
			 */
		} else if (identifyBy.equalsIgnoreCase("id")) {

			Select selectbox1 = new Select(driver.findElement(By.id(locator)));
			String option1 = selectbox1.getFirstSelectedOption().getText();
			return option1;
		} else if (identifyBy.equalsIgnoreCase("name")) {
			Select selectbox1 = new Select(driver.findElement(By.name(locator)));
			String option1 = selectbox1.getFirstSelectedOption().getText();
			return option1;
		}

		else
			return null;
	}

	public static String getValue(String identifyBy, String locator)
			throws InterruptedException {
		waitForElementPresent(10000, identifyBy, locator);
		return getWebElement(identifyBy, locator).getAttribute("value");

	}

	public static String getText(String identifyBy, String locator)
			throws InterruptedException {
		waitForElementPresent(10000, identifyBy, locator);
		return getWebElement(identifyBy, locator).getText();

	}

	public static int getXpahtCount(String xPath) {

		return driver.findElements(By.xpath(xPath)).size();
	}
	
	public static void returnXpathtoClick(){
		
	}

	public static void waitForTextPresent(int maxWaitTime, String string)
			throws InterruptedException {

		long totalWaitTime = 0;
		int i = 1;
		while (!isTextPresent(string)) {
			long time = 1000;
			log.info(i + "Second");
			totalWaitTime = totalWaitTime + time;
			Thread.sleep(1000);
			i++;
			if (totalWaitTime >= maxWaitTime) {
				break;
			}

		}
	}

	public static boolean isTextPresent(String text) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean isTextExist = driver.findElement(By.tagName("body")).getText()
				.contains(text);
		log.info("isText present:" + isTextExist);
		return isTextExist;
	}

	public static void mouseoverByxPath(String menu)
			throws InterruptedException {
		// waitForElementPresent(10000,menu);
		WebElement webElement = driver.findElement(By.xpath(menu));// Menu

		Actions builder = new Actions(driver);
		Actions hoverOverRegistrar = builder.moveToElement(webElement);
		hoverOverRegistrar.perform();

		Thread.sleep(2000);

	}

	public static void mouseoverByWebElement(WebElement webElement)
			throws InterruptedException {

		Actions builder = new Actions(driver);
		Actions hoverOverRegistrar = builder.moveToElement(webElement);
		hoverOverRegistrar.perform();

		Thread.sleep(2000);
	}

	public static void mouseOverByIdentityTypeLocator(String identifyBy, String locator) throws InterruptedException {
		
		WebElement webElement = null;
		try{
			if (identifyBy.equalsIgnoreCase("xpath")) {
				webElement = driver.findElement(By.xpath(locator));
				log.info("Executed"+ identifyBy +"without exception");
			} else if (identifyBy.equalsIgnoreCase("id")) {
				webElement = driver.findElement(By.id(locator));
			} else if (identifyBy.equalsIgnoreCase("name")) {
				webElement = driver.findElement(By.name(locator));
			}else if(identifyBy.equalsIgnoreCase("link")){
				webElement = driver.findElement(By.linkText(locator));
			}
		}catch(Exception ex){
			log.info("Exception occured..");
		}
		if(webElement != null){
			Actions builder = new Actions(driver);
			Actions hoverOverRegistrar = builder.moveToElement(webElement);
			hoverOverRegistrar.perform();
		}
		Thread.sleep(2000);
	}

	public static void mouseoverandclick(String identifyBy,String locator)
			throws InterruptedException {
		WebElement mouseOverElement = findElement(identifyBy,locator);
		Actions builder = new Actions(driver);
		Actions hoverOverRegistrar = builder.moveToElement(mouseOverElement);
		hoverOverRegistrar.perform();
		mouseOverElement.click();
	}
	
	public static WebElement findElement(String identifyBy,String locator){
		waitForElementPresent(10000, identifyBy, locator);
		try{
			if (identifyBy.equalsIgnoreCase("xpath")) {
				return driver.findElement(By.xpath(locator));
				
			} else if (identifyBy.equalsIgnoreCase("id")) {
				return driver.findElement(By.id(locator));
			} else if (identifyBy.equalsIgnoreCase("name")) {
				return driver.findElement(By.name(locator));
			}else if(identifyBy.equalsIgnoreCase("link")){
				return driver.findElement(By.linkText(locator));
			}else if(identifyBy.equalsIgnoreCase("partialLinkText")){
				return driver.findElement(By.partialLinkText(locator));
			}
		}catch(Exception ex){
			log.info("Exception occured..");
		}
		
		return null;
	}
	
	public static void pressEnter(String identifyBy,String locator ){
		try{
			if (identifyBy.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(locator)).sendKeys(Keys.ENTER);
				
			} else if (identifyBy.equalsIgnoreCase("id")) {
				driver.findElement(By.id(locator)).sendKeys(Keys.ENTER);
			} else if (identifyBy.equalsIgnoreCase("name")) {
				driver.findElement(By.name(locator)).sendKeys(Keys.ENTER);
			}else if(identifyBy.equalsIgnoreCase("link")){
				driver.findElement(By.linkText(locator)).sendKeys(Keys.ENTER);
			}else{
				log.info("Nothing identfyBy is matched with existing conditions please add condition for:"+identifyBy);
			}
			
		}catch(Exception ex){
			log.info("Exception occured..");
		}
	}
	
	public static void enterText(String identifyBy, String locator,
			String text) {
		waitForElementPresent(10000,identifyBy, locator);
		try {
			getWebElement(identifyBy, locator).sendKeys(text);
		} catch (Throwable e) {
			takeScreenShotOnfailure("Textbox not found");// "+datetime("MMddhhmmss")
			log.info(e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
		}
	}
	
	public static boolean isElementDispalyed(String identifyBy, String locator){
		boolean isDisplayed = false;
		waitForElementPresent(10000, identifyBy, locator);
		WebElement element = getWebElement(identifyBy,locator);
		if(element != null){
			isDisplayed = element.isDisplayed();
			log.info("is element displayed :"+isDisplayed);
		}
		return isDisplayed;
	}
	
	public static void controlEvents(String identifyBy, String locator,String selectTag){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement element = findElement(identifyBy, locator);
		log.info("Ready to run contol function..");
		element.sendKeys(Keys.chord(Keys.CONTROL, selectTag));
		log.info("Done with control key function");
	}

	public static void controlMouseClick(String identifyBy,String locator){
		log.info("finding the element for control enter");
		WebElement element = findElement(identifyBy, locator);
		log.info("got the element starting contl enter");
		String keysPressed =  Keys.chord(Keys.CONTROL, Keys.RETURN);
		   element.sendKeys(keysPressed) ;
		   log.info("successfully entered..");
	}
	
	public static void alertText(){
		
		log.info("Text in alert:"+driver.switchTo().alert().getText());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void alertAccept(){
			log.info("accepting the alert");
			driver.switchTo().alert().accept();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public static void alertReject(){
			log.info("reject the alert");
			driver.switchTo().alert().dismiss();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public static void isRadioButtonSelected(String identifyBy,String locator) throws InterruptedException{
		WebElement element = findElement(identifyBy, locator);
		Thread.sleep(3000);
		//input[@value='male']
		log.info("is radio Button selected:"+element.isSelected());
	}
	
	public static void selectRadioButton(String identifyBy,String locator) throws InterruptedException{
		WebElement element = findElement(identifyBy, locator);
		Thread.sleep(3000);
		log.info("is radio Button selected:"+element.isSelected());
		if(!element.isSelected()){
			element.click();
			
		}
		Thread.sleep(3000);
	}
	
	public static void clickByLocator(String identifyBy, String locator){
		WebElement element = findElement(identifyBy,locator);
		try {
				if(element != null){
					Thread.sleep(1000);
					element.click();
				}
				Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void openUrl(String url){
		driver.get(url);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.manage().window().maximize();
	}
	
	// created to check for text in body tag
	public static boolean verifyForTagName(String tagName, String text){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return driver.findElement(By.tagName(tagName)).getText().contains(text);
	}
	
	/**
	 * Select a text in select dropdown list
	 * @param identifyBy
	 * @param locator
	 * @param visibleText
	 */
	public static void selectByVisibleText(String identifyBy, String locator,String visibleText){
		try{
			waitForElementPresent(10000, identifyBy, locator);
			if (identifyBy.equalsIgnoreCase("xpath")) {
				Select selectbox = new Select(driver.findElement(By.id(locator)));
				selectbox.selectByVisibleText(visibleText);
			} else if (identifyBy.equalsIgnoreCase("id")) {
				Select selectbox = new Select(driver.findElement(By.id(locator)));
				selectbox.selectByVisibleText(visibleText);
			} else if (identifyBy.equalsIgnoreCase("name")) {
				Select selectbox = new Select(driver.findElement(By.name(locator)));
				selectbox.selectByVisibleText(visibleText);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static int getCountOfXPath(String identifyBy,String locator){
		waitForElementPresent(10000,identifyBy,locator);
		if(identifyBy.equals("xpath")){
			log.info("counting the list of rows present  in table");
			return driver.findElements(By.xpath(locator)).size();
		}
		// no such locator is found / xpath
		return 0;
	}
	/**
	 * get text of td value in table of rows from list of logs table in ford site
	 * @param identifyBy
	 * @param locator
	 * @return
	 */
	public static String getValueOfXPath(String identifyBy,String locator){
		waitForElementPresent(11000, identifyBy, locator);
		return driver.findElement(By.xpath(locator)).getText();
	}
	
	// get size of previous log
	public static int getSizeuptoPreviousLog(String tableXtype,String locator,String fileName){
		
		//reading the last log name from text file.
		String lastLogName = getFordLogInfo(XPathConstants.logFile);
		
		int sizeOfRows = getCountOfXPath(tableXtype,locator);
		
		// loop through all trs and tds
		for(int i=1;i<=sizeOfRows;i++){
			log.info("Getting "+i+"Row value");
				System.out.print("Getting "+i+" Row "+3+" column value====");
				String currentLogName = getValueOfXPath(tableXtype, locator+"["+i+"]/td["+3+"]");
				log.info(" "+currentLogName);
				if(lastLogName.equals(currentLogName)){
					log.info("name matches to the previous log- returning with size=="+i);
					return i;
				}
		}
		return sizeOfRows;
	}
	
	// get size of previous log
	public static boolean downloadZipLogFile(String tableXtype,String locator,String fileName){
		
		//reading the last log name from text file.
		//String downloadLogName = getLastDownloadedLogName(fileName);
		String downloadLogName = getFordLogInfo(XPathConstants.fordLogZip);
		boolean isfilesPresent = false;
		if(isTextPresent("No files to select from")){
			isfilesPresent = false;
		}else{
		
			int sizeOfRows = getCountOfXPath(tableXtype,locator);
			
			log.info("Size of rows:"+sizeOfRows);
			boolean zipNameMatched = false;
			
			// loop through all trs and tds
			for(int i=1;i<=sizeOfRows;i++){
				log.info("Getting "+i+"Row value");
					String currentLogName = getValueOfXPath(tableXtype, locator+"["+i+"]/td["+1+"]");
					log.info(" "+currentLogName);
					if(downloadLogName.equals(currentLogName)){
						zipNameMatched = true;
						log.info("name matches to the previous log- returning with size=="+i);
						clickByLocator(downloadZipLog[0], downloadZipLog[1]+i+downloadZipLog[2]);
					}
			}
			
			if(zipNameMatched == false)
			log.info("Zip name is not available to download...");
			isfilesPresent = true;
	}
		return isfilesPresent;
	}
	
	public static void doPaste(String identifyBy,String locator,String paste){
		log.info("doing paste..");
		getWebElement(identifyBy,locator).sendKeys(Keys.chord(Keys.CONTROL,paste));
		
	}
	
	public static void waitForSeconds(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//log.info("pra"+dataTime("MMddhhmmss"));
		log.info(3*1000);
	}
}
