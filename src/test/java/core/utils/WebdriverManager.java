package core.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/***
 * @author Sai Ram Prasath
 */

public class WebdriverManager {
    private static Logger logger = Logger.getLogger(WebdriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        logger.info("returning the current driver");
        return driver.get();
    }

    protected static void setDriver(String browserName) {
        boolean headless = System.getProperty("headless", "true").equalsIgnoreCase("true");
        String runEnv = System.getProperty("runEnv", "local");
        switch (browserName.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(headless);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(headless);
                options.addArguments("--disable-notifications", "--no-sandbox");
                options.setAcceptInsecureCerts(true);
                String downloadFilepath = System.getProperty("user.dir") + File.separator + "downloads";
                System.out.println(downloadFilepath);
                WebDriverManager.chromedriver().setup();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("download.default_directory", downloadFilepath);
                chromePrefs.put("plugins.plugins_disabled", new String[] {
                        "Chrome PDF Viewer"
                });
                chromePrefs.put("plugins.always_open_pdf_externally", true);
                driver.set(new ChromeDriver(options));
                break;
            case "edge":
                EdgeOptions edgeOptions=new EdgeOptions();
                edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);

                driver.set(new EdgeDriver(edgeOptions));
                break;
            case "internetexplorer":
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.requireWindowFocus();
                internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptions.ignoreZoomSettings();
                //internetExplorerOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
                DesiredCapabilities capabilities=new DesiredCapabilities();
                capabilities.acceptInsecureCerts();
                //capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                driver.set(new InternetExplorerDriver(internetExplorerOptions));
                //driver.set(new InternetExplorerDriver(capabilities));
                break;
            case "safari":
                driver.set(new SafariDriver());
                break;
        }

        logger.info("===========" + browserName + " is launched====================");

    }

    protected static void setWhatsappDriver(String browserName) {
        boolean headless = System.getProperty("headless", "false").equalsIgnoreCase("true");
        String runEnv = System.getProperty("runEnv", "local");
        switch (browserName.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(headless);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(headless);
                options.addArguments("--disable-notifications", "--no-sandbox");
                options.setAcceptInsecureCerts(true);
                String downloadFilepath = System.getProperty("user.dir") + File.separator + "downloads";
                System.out.println(downloadFilepath);
                WebDriverManager.chromedriver().setup();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("download.default_directory", downloadFilepath);
                chromePrefs.put("plugins.plugins_disabled", new String[] {
                        "Chrome PDF Viewer"
                });
                options.addArguments("user-data-dir=C:/Users/arunk/AppData/Local/Google/Chrome/User Data");
                options.setExperimentalOption("prefs", chromePrefs);
                driver.set(new ChromeDriver(options));
                break;
            case "edge":
                EdgeOptions edgeOptions=new EdgeOptions();
                edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);

                driver.set(new EdgeDriver(edgeOptions));
                break;
            case "internetexplorer":
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.requireWindowFocus();
                internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptions.ignoreZoomSettings();
                //internetExplorerOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
                DesiredCapabilities capabilities=new DesiredCapabilities();
                capabilities.acceptInsecureCerts();
                //capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                driver.set(new InternetExplorerDriver(internetExplorerOptions));
                //driver.set(new InternetExplorerDriver(capabilities));
                break;
            case "safari":
                driver.set(new SafariDriver());
                break;
        }

        logger.info("===========" + browserName + " is launched====================");

    }
}
