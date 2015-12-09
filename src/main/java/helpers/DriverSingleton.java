package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 06.11.2015.
 */
public class DriverSingleton {
    private static WebDriver driver;
    private DriverSingleton() {

    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver("default");
        }
        return driver;
    }

    private static void initDriver(String browser) {
        String browserName = System.getProperty("browser", browser);
        String browserRemoteName = System.getProperty("remote", "local");

        if (browserRemoteName.equals("local")){
            switch (browserName) {
                case "chrome": default:
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "ie":
                    driver = new InternetExplorerDriver();
                    break;
                case "html":
                    driver = new HtmlUnitDriver();
                    break;
                case "phantomjs":
                    driver = new PhantomJSDriver();
                    break;
            }
        } else {
            DesiredCapabilities caps;
            switch (browserName) {
                case "chrome": default:
                    caps = DesiredCapabilities.chrome();
                    break;
                case "firefox":
                    caps = DesiredCapabilities.firefox();
                    break;
                case "ie":
                    caps = DesiredCapabilities.internetExplorer();
                    break;
                case "html":
                    caps = DesiredCapabilities.htmlUnitWithJs();
                    break;
                case "phantomjs":
                    caps = DesiredCapabilities.phantomjs();
                    break;
            }
            try {
                driver = new RemoteWebDriver(new URL(browserRemoteName), caps);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
