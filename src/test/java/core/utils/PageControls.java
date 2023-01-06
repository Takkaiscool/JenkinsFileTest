package core.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/***
 * @author Sai Ram Prasath
 */
public class PageControls {
    private static Logger logger = Logger.getLogger(PageControls.class);

    public void launchWhatsappBrowser(String browser) {
        WebdriverManager.setWhatsappDriver(browser);
        logger.info(browser + " is launched");

    }

    public void launchBrowser(String browser) {
        WebdriverManager.setDriver(browser);
        logger.info(browser + " is launched");

    }



    public void launchBrowser() {
        String browser = System.getProperty("browser", "chrome");
        WebdriverManager.setDriver(browser);
        logger.info(browser + " is launched");
    }

    public String getUrl() {
        String returnUrl = WebdriverManager.getDriver().getCurrentUrl();
        logger.info("Get URL invoked: get url is " + returnUrl);
        return returnUrl;
    }

    public void loadURL(String url) {
        WebdriverManager.getDriver().get(url);
        logger.info("URL " + url + " is loaded");
    }

    private void waitForAlertPopup(int timeout) {
        WebDriverWait wait = new WebDriverWait(WebdriverManager.getDriver(), timeout);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Waiting for alert to get present");
    }

    public void acceptAlert(int timeout) {
        waitForAlertPopup(timeout);
        WebdriverManager.getDriver().switchTo().alert().accept();
        logger.info("Alert is been accepted");
    }

    public void declineAlert(int timeout) {
        waitForAlertPopup(timeout);
        WebdriverManager.getDriver().switchTo().alert().dismiss();
        logger.info("Alert is been declined");
    }

    public String getAlertText(int timeout) {
        waitForAlertPopup(timeout);
        String text = WebdriverManager.getDriver().switchTo().alert().getText();
        logger.info("Alert text is: " + text);
        return text;
    }

    public String getTitle() {
        String title = WebdriverManager.getDriver().getTitle();
        logger.info("Title of the current page is: " + title);
        return title;
    }

    public void closeBrowser() {
        WebdriverManager.getDriver().close();
        logger.info("Current browser tab is closed");
    }

    public void quitBrowser() {
        WebdriverManager.getDriver().quit();
        logger.info("Browser is closed");
    }

    public byte[] takeScreenShot() {
        byte[] screeshot = ((TakesScreenshot) WebdriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        logger.info("Screenshot is captured");
        return screeshot;
    }

    public void maximizeWindow() {
        WebdriverManager.getDriver().manage().window().maximize();
        logger.info("Browser window is maximized");
    }

    public void setWindowSize(int x, int y) {
        WebdriverManager.getDriver().manage().window().setSize(new Dimension(x, y));
        logger.info("Browser window is maximized");
    }

    public void refreshPage() {
        WebdriverManager.getDriver().navigate().refresh();
        logger.info("Current page is refreshed");
    }

    public void implicitWait(int timeInSec) {
        WebdriverManager.getDriver().manage().timeouts().implicitlyWait(timeInSec, TimeUnit.SECONDS);
        logger.info("Implicit wait is been set to " + timeInSec + " seconds");
    }

    public void removeImplicitWait() {
        WebdriverManager.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        logger.info("Implicit wait is been removed");

    }

    public void pageLoadTimeout(int timeInSec) {
        WebdriverManager.getDriver().manage().timeouts().pageLoadTimeout(timeInSec, TimeUnit.SECONDS);
        logger.info("Page time out is been set to " + timeInSec + " seconds");
    }

    public void fullScreen() {
        WebdriverManager.getDriver().manage().window().fullscreen();
        logger.info("Browser window screen is made to full screen");
    }

    public void deleteAllCookies() {
        WebdriverManager.getDriver().manage().deleteAllCookies();
        logger.info("Deleted all the existing cookies");
    }

    public void deleteCookie(Cookie cookie) {
        WebdriverManager.getDriver().manage().deleteCookie(cookie);
        logger.info("Deleted the " + cookie.toJson().toString() + " cookie");
    }

    public void setCookie(Cookie cookie) {
        WebdriverManager.getDriver().manage().addCookie(cookie);
        logger.info("Deleted the " + cookie.toJson().toString() + " cookie");
    }

    public String getEntirePageSource() {
        String source = WebdriverManager.getDriver().getPageSource();
        logger.info("Page source code: " + source);
        return source;
    }

    public void navigateToMainWindow() {
        Set<String> windowHandles = WebdriverManager.getDriver().getWindowHandles();
        String mainWindow = windowHandles.iterator().next();
        WebdriverManager.getDriver().switchTo().window(mainWindow);
        logger.info("Driver is switched to main window:" + mainWindow);
    }

    public void navigateToLastChildWindow() {
        Set<String> windowHandles = WebdriverManager.getDriver().getWindowHandles();
        Iterator<String> handles = windowHandles.iterator();
        System.out.println(windowHandles.size());
        String lastWindow = new String();
        while (handles.hasNext()) {
            lastWindow = handles.next();
        }
        WebdriverManager.getDriver().switchTo().window(lastWindow);
        logger.info("Driver is switched to last window:" + lastWindow);
    }

    public void waitForNumberOfWindows(int windows,int timeout) {
        WebDriverWait wait=new WebDriverWait(WebdriverManager.getDriver(),timeout);
        wait.until(ExpectedConditions.numberOfWindowsToBe(windows));
    }

    public void navigateToWindow(int windowNumber) {
        Set<String> windowHandles = WebdriverManager.getDriver().getWindowHandles();
        Iterator<String> handles = windowHandles.iterator();
        String lastWindow = new String();
        while (handles.hasNext() && windowNumber > 0) {
            lastWindow = handles.next();
            windowNumber--;
        }
        WebdriverManager.getDriver().switchTo().window(lastWindow);
        logger.info("Driver is switched to " + windowNumber + " window:" + lastWindow);
    }



    public void scrollToBottomPage() {
        JavascriptExecutor jsExcutor = (JavascriptExecutor) WebdriverManager.getDriver();
        jsExcutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        logger.info("Scrolling to bottom of the page");
    }

    public void scrollToMiddlePage() {
        JavascriptExecutor jsExcutor = (JavascriptExecutor) WebdriverManager.getDriver();
        jsExcutor.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
        logger.info("Scrolling to bottom of the page");
    }

    public void scrollToQuaterPage() {
        JavascriptExecutor jsExcutor = (JavascriptExecutor) WebdriverManager.getDriver();
        jsExcutor.executeScript("window.scrollTo(0, document.body.scrollHeight/4)");
        logger.info("Scrolling to bottom of the page");
    }

    public void setLocalStorage(String key, String value) {
        //LocalStorage localStorage = ((WebStorage) WebdriverManager.getDriver()).getLocalStorage();
        //localStorage.setItem(key, value);
        JavascriptExecutor js=(JavascriptExecutor)WebdriverManager.getDriver();
        js.executeScript(String.format("window.localStorage.setItem('%s','%s');",key,value));
        logger.info("Setting the local storage as key:" + key + " value:" + value);
    }

    public void clearSessionStorage() {
        SessionStorage localStorage = ((WebStorage) WebdriverManager.getDriver()).getSessionStorage();
        localStorage.clear();

    }
}
