package com.ford.logs.automation.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class BrowserEvents {
    private static WebDriver driver;
    static final Logger log = Logger.getLogger(BrowserEvents.class);

    /*
     * Exception decompiling
     */
    public static void createDriver(String driverName) {
        
    	switch (driverName) {
		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		default:
			break;
		}
       
    }

    public static void closeDriver() {
        log.info("Closing Driver");
        log.info("**************************** Ending downloading logs ******************************");
        try {
            Thread.sleep(7200000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Browser Closing Time: " + BrowserEvents.dataTime("yyyy-MM-dd HH:mm:ss"));
        driver.quit();
    }

    public static void takeScreenShotOnfailure(String filename) {
        File image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(image, new File(String.valueOf(System.getProperty("user.dir")) + "//screenshots_on_failure//" + filename + "_" + BrowserEvents.dataTime("MMddhhmmss") + ".jpg"));
        }
        catch (Throwable e) {
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
        String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        log.info(("user name is:" + userId + date));
        return String.valueOf(userId) + date + "@gmail.com";
    }

    public static void waitForElementPresent(int maxWaitTime, String identifyBy, String locator) {
        long totalWaitTime = 0;
        try {
            while (!BrowserEvents.isElementPresent(identifyBy, locator)) {
                long time = 1000;
                Thread.sleep(1000);
                if ((totalWaitTime += time) < (long)maxWaitTime) {
                    continue;
                }
                break;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isElementPresentStatus(int maxWaitTime, String identifyBy, String locator) throws InterruptedException {
        long totalWaitTime = 0;
        boolean isPresent = BrowserEvents.isElementPresent(identifyBy, locator);
        while (!isPresent) {
            log.info("Element not present..");
            long time = 1000;
            Thread.sleep(1000);
            isPresent = BrowserEvents.isElementPresent(identifyBy, locator);
            if ((totalWaitTime += time) >= (long)maxWaitTime) break;
        }
        return isPresent;
    }

    public static boolean isElementPresent(String identifyBy, String locator) {
      
			 if (!identifyBy.equalsIgnoreCase("xpath") || driver.findElements(By.xpath((String)locator)).size() <= 0){
			    return true;
			}
			if (!identifyBy.equalsIgnoreCase("link") || driver.findElements(By.linkText((String)locator)).size() <= 0){
			    return true;
			}
			if (!identifyBy.equalsIgnoreCase("id") || driver.findElements(By.id((String)locator)).size() <= 0){
			    return true;
			}
			if (!identifyBy.equalsIgnoreCase("name") || driver.findElements(By.name((String)locator)).size() <= 0){
			    return true;
			}
			if (!identifyBy.equalsIgnoreCase("css") || driver.findElements(By.cssSelector((String)locator)).size() <= 0){
			return true;
			}
            if (identifyBy.equals("tagName") && driver.findElements(By.tagName((String)"body")).size() > 0) {
                return true;
            }
        log.info("Returning false...");
        return false;
    }

    public static String getTooltip(String identityBy, String locator) {
        String title = null;
        if (BrowserEvents.getWebElement(identityBy, locator) != null) {
            title = BrowserEvents.getWebElement(identityBy, locator).getAttribute("title");
        }
        return title;
    }

    public static WebElement getWebElement(String identifyBy, String locator) {
        WebElement webElement = null;
        BrowserEvents.waitForElementPresent(1000, identifyBy, locator);
        if (identifyBy.equalsIgnoreCase("xpath")) {
            webElement = driver.findElement(By.xpath((String)locator));
        } else if (identifyBy.equalsIgnoreCase("id")) {
            webElement = driver.findElement(By.id((String)locator));
        } else if (identifyBy.equalsIgnoreCase("name")) {
            webElement = driver.findElement(By.name((String)locator));
        } else if (identifyBy.equalsIgnoreCase("css")) {
            webElement = driver.findElement(By.cssSelector((String)locator));
        } else if (identifyBy.equalsIgnoreCase("link")) {
            webElement = driver.findElement(By.linkText((String)locator));
        } else if (identifyBy.equalsIgnoreCase("partialLinkText")) {
            webElement = driver.findElement(By.partialLinkText((String)locator));
        } else if (identifyBy.equalsIgnoreCase("tagName")) {
            webElement = driver.findElement(By.tagName((String)"body"));
        }
        return webElement;
    }

    public static String getselectedLabel(String identifyBy, String locator) throws InterruptedException {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        if (identifyBy.equalsIgnoreCase("xpath")) {
            Select selectbox = new Select(driver.findElement(By.xpath((String)locator)));
            String option1 = selectbox.getFirstSelectedOption().getText();
            return option1;
        }
        if (identifyBy.equalsIgnoreCase("id")) {
            Select selectbox = new Select(driver.findElement(By.id((String)locator)));
            String option1 = selectbox.getFirstSelectedOption().getText();
            return option1;
        }
        if (identifyBy.equalsIgnoreCase("name")) {
            Select selectbox = new Select(driver.findElement(By.name((String)locator)));
            String option1 = selectbox.getFirstSelectedOption().getText();
            return option1;
        }
        return null;
    }

    public static String getselectedLabelText(String identifyBy, String locator) throws InterruptedException {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        if (identifyBy.equalsIgnoreCase("xpath")) {
            String val = BrowserEvents.getValue("xpath", locator);
            return BrowserEvents.getText("xpath", "//option[@value='" + val + "']");
        }
        if (identifyBy.equalsIgnoreCase("id")) {
            Select selectbox1 = new Select(driver.findElement(By.id((String)locator)));
            String option1 = selectbox1.getFirstSelectedOption().getText();
            return option1;
        }
        if (identifyBy.equalsIgnoreCase("name")) {
            Select selectbox1 = new Select(driver.findElement(By.name((String)locator)));
            String option1 = selectbox1.getFirstSelectedOption().getText();
            return option1;
        }
        return null;
    }

    public static String getValue(String identifyBy, String locator) throws InterruptedException {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        return BrowserEvents.getWebElement(identifyBy, locator).getAttribute("value");
    }

    public static String getText(String identifyBy, String locator) throws InterruptedException {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        return BrowserEvents.getWebElement(identifyBy, locator).getText();
    }

    public static int getXpahtCount(String xPath) {
        return driver.findElements(By.xpath((String)xPath)).size();
    }

    public static void returnXpathtoClick() {
    }

    public static void waitForTextPresent(int maxWaitTime, String text) throws InterruptedException {
        long totalWaitTime = 0;
        int i = 1;
        while (!BrowserEvents.isTextPresent(text)) {
            long time = 1000;
            log.info((String.valueOf(i) + "Second"));
            Thread.sleep(1000);
            ++i;
            if ((totalWaitTime += time) >= (long)maxWaitTime) break;
        }
    }

    public static boolean isTextPresent(String text) {
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean isTextExist = driver.findElement(By.tagName((String)"body")).getText().contains(text);
        log.info(("isText - " + text + " - present:" + isTextExist));
        return isTextExist;
    }

    public static void mouseoverByxPath(String menu) throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath((String)menu));
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(webElement);
        hoverOverRegistrar.perform();
        Thread.sleep(2000);
    }

    public static void mouseoverByWebElement(WebElement webElement) throws InterruptedException {
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(webElement);
        hoverOverRegistrar.perform();
        Thread.sleep(2000);
    }

    public static void mouseOverByIdentityTypeLocator(String identifyBy, String locator) throws InterruptedException {
        WebElement webElement = null;
        try {
            if (identifyBy.equalsIgnoreCase("xpath")) {
                webElement = driver.findElement(By.xpath((String)locator));
                log.info( ("Executed" + identifyBy + "without exception"));
            } else if (identifyBy.equalsIgnoreCase("id")) {
                webElement = driver.findElement(By.id((String)locator));
            } else if (identifyBy.equalsIgnoreCase("name")) {
                webElement = driver.findElement(By.name((String)locator));
            } else if (identifyBy.equalsIgnoreCase("link")) {
                webElement = driver.findElement(By.linkText((String)locator));
            }
        }
        catch (Exception ex) {
            log.info("Exception occured..");
        }
        if (webElement != null) {
            Actions builder = new Actions(driver);
            Actions hoverOverRegistrar = builder.moveToElement(webElement);
            hoverOverRegistrar.perform();
        }
        Thread.sleep(2000);
    }

    public static void mouseoverandclick(String identifyBy, String locator) throws InterruptedException {
        WebElement mouseOverElement = BrowserEvents.findElement(identifyBy, locator);
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(mouseOverElement);
        hoverOverRegistrar.perform();
        mouseOverElement.click();
    }

    public static WebElement findElement(String identifyBy, String locator) {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        try {
            if (identifyBy.equalsIgnoreCase("xpath")) {
                return driver.findElement(By.xpath((String)locator));
            }
            if (identifyBy.equalsIgnoreCase("id")) {
                return driver.findElement(By.id((String)locator));
            }
            if (identifyBy.equalsIgnoreCase("name")) {
                return driver.findElement(By.name((String)locator));
            }
            if (identifyBy.equalsIgnoreCase("link")) {
                return driver.findElement(By.linkText((String)locator));
            }
            if (identifyBy.equalsIgnoreCase("partialLinkText")) {
                return driver.findElement(By.partialLinkText((String)locator));
            }
        }
        catch (Exception ex) {
            log.info("Exception occured..");
        }
        return null;
    }

    public static void pressEnter(String identifyBy, String locator) {
        try {
            if (identifyBy.equalsIgnoreCase("xpath")) {
                driver.findElement(By.xpath((String)locator)).sendKeys(Keys.ENTER);
            } else if (identifyBy.equalsIgnoreCase("id")) {
                driver.findElement(By.id((String)locator)).sendKeys(Keys.ENTER);
            } else if (identifyBy.equalsIgnoreCase("name")) {
                driver.findElement(By.name((String)locator)).sendKeys(Keys.ENTER);
            } else if (identifyBy.equalsIgnoreCase("link")) {
                driver.findElement(By.linkText((String)locator)).sendKeys(Keys.ENTER);
            } else {
                log.info(("Nothing identfyBy is matched with existing conditions please add condition for:" + identifyBy));
            }
        }
        catch (Exception ex) {
            log.info("Exception occured..");
        }
    }

    public static void enterText(String identifyBy, String locator, String text) {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        try {
            BrowserEvents.getWebElement(identifyBy, locator).sendKeys(text);
        }
        catch (Throwable e) {
            BrowserEvents.takeScreenShotOnfailure("Textbox not found");
            log.info(e.getMessage());
        }
    }

    public static boolean isElementDispalyed(String identifyBy, String locator) {
        boolean isDisplayed = false;
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        WebElement element = BrowserEvents.getWebElement(identifyBy, locator);
        if (element != null) {
            isDisplayed = element.isDisplayed();
            log.info(("is element displayed :" + isDisplayed));
        }
        return isDisplayed;
    }

    public static void controlEvents(String identifyBy, String locator, String selectTag) {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement element = BrowserEvents.findElement(identifyBy, locator);
        log.info("Ready to run contol function..");
        element.sendKeys(Keys.chord(Keys.CONTROL, selectTag));
        log.info("Done with control key function");
    }

    public static void controlMouseClick(String identifyBy, String locator) {
        log.info("finding the element for control enter");
        WebElement element = BrowserEvents.findElement(identifyBy, locator);
        log.info("got the element starting contl enter");
        String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
        element.sendKeys(keysPressed);
        log.info("successfully entered..");
    }

    public static void alertText() {
        log.info(("Text in alert:" + driver.switchTo().alert().getText()));
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void alertAccept() {
        log.info("accepting the alert");
        driver.switchTo().alert().accept();
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void alertReject() {
        log.info("reject the alert");
        driver.switchTo().alert().dismiss();
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void isRadioButtonSelected(String identifyBy, String locator) throws InterruptedException {
        WebElement element = BrowserEvents.findElement(identifyBy, locator);
        Thread.sleep(3000);
        log.info(("is radio Button selected:" + element.isSelected()));
    }

    public static void selectRadioButton(String identifyBy, String locator) throws InterruptedException {
        WebElement element = BrowserEvents.findElement(identifyBy, locator);
        Thread.sleep(3000);
        log.info(("is radio Button selected:" + element.isSelected()));
        if (!element.isSelected()) {
            element.click();
        }
        Thread.sleep(3000);
    }

    public static void clickByLocator(String identifyBy, String locator) {
        WebElement element = BrowserEvents.findElement(identifyBy, locator);
        try {
            if (element != null) {
                Thread.sleep(1000);
                element.click();
            }
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void openUrl(String url) {
        driver.get(url);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().window().maximize();
    }

    public static boolean verifyForTagName(String tagName, String text) {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElement(By.tagName((String)tagName)).getText().contains(text);
    }

    public static void selectByVisibleText(String identifyBy, String locator, String visibleText) {
        try {
            BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
            if (identifyBy.equalsIgnoreCase("xpath")) {
                Select selectbox = new Select(driver.findElement(By.id((String)locator)));
                selectbox.selectByVisibleText(visibleText);
            } else if (identifyBy.equalsIgnoreCase("id")) {
                Select selectbox = new Select(driver.findElement(By.id((String)locator)));
                selectbox.selectByVisibleText(visibleText);
            } else if (identifyBy.equalsIgnoreCase("name")) {
                Select selectbox = new Select(driver.findElement(By.name((String)locator)));
                selectbox.selectByVisibleText(visibleText);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getCountOfXPath(String identifyBy, String locator) {
        BrowserEvents.waitForElementPresent(10000, identifyBy, locator);
        if (identifyBy.equals("xpath")) {
            return driver.findElements(By.xpath((String)locator)).size();
        }
        return 0;
    }

    public static String getValueOfXPath(String identifyBy, String locator) {
        BrowserEvents.waitForElementPresent(11000, identifyBy, locator);
        return driver.findElement(By.xpath((String)locator)).getText();
    }

    public static int getSizeuptoPreviousLog(String tableXtype, String locator) {
        String lastLogName = ReadExcelData.getFordLogDetails(XPathConstants.logFile);
        int sizeOfRows = BrowserEvents.getCountOfXPath(tableXtype, locator);
        int i = 1;
        while (i <= sizeOfRows) {
            String currentLogName = BrowserEvents.getValueOfXPath(tableXtype, String.valueOf(locator) + "[" + i + "]/td[" + 3 + "]");
            log.info((" " + currentLogName));
            if (lastLogName.equals(currentLogName)) {
                log.info(("name matches to the previous log- returning with size==" + i));
                return i;
            }
            ++i;
        }
        return sizeOfRows;
    }

    public static boolean downloadZipLogFile(String tableXtype, String locator) {
        String downloadLogName = ReadExcelData.getFordLogDetails((String)XPathConstants.fordLogZip);
        boolean isfilesPresent = false;
        if (BrowserEvents.isTextPresent(XPathConstants.exceptionInRetrieveLog)) {
            isfilesPresent = false;
        } else if (BrowserEvents.isTextPresent(XPathConstants.noLogs)) {
            isfilesPresent = false;
        } else {
            int sizeOfRows = BrowserEvents.getCountOfXPath(tableXtype, locator);
            log.info(("Size of rows:" + sizeOfRows));
            boolean zipNameMatched = false;
            int i = 1;
            while (i <= sizeOfRows) {
                String currentLogName = BrowserEvents.getValueOfXPath(tableXtype, String.valueOf(locator) + "[" + i + "]/td[" + 1 + "]");
                log.info(("Zip Log to download : " + currentLogName));
                if (downloadLogName.equals(currentLogName)) {
                    zipNameMatched = true;
                    log.info(("name matches to the previous log- returning with size==" + i));
                    BrowserEvents.clickByLocator(XPathConstants.downloadZipLog[0], String.valueOf(XPathConstants.downloadZipLog[1]) + i + XPathConstants.downloadZipLog[2]);
                }
                ++i;
            }
            if (!zipNameMatched) {
                log.info("Zip name is not available to download...");
            }
            isfilesPresent = true;
        }
        return isfilesPresent;
    }

    public static void doPaste(String identifyBy, String locator, String paste) {
        log.info("pasting the passcode..");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        BrowserEvents.getWebElement(identifyBy, locator).sendKeys(Keys.chord(Keys.CONTROL, paste));
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(BrowserEvents.dataTime("yyyy-MM-dd HH:mm:ss"));
    }
}
