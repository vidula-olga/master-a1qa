package testChrome1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by New User on 20.11.2018.
 */
public class WebDriverFactory {
    public static WebDriver getWebDriver(String browser) {
        WebDriver webDriver;
        switch (browser) {
            case "ie": {
                webDriver = new InternetExplorerDriver();
                break;
            }
            case "chrome": {
                System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
                webDriver = new ChromeDriver();

                break;
            }
            default:
                RuntimeException exception = new RuntimeException("unknown browser " + browser);
                throw exception;
        }

        return webDriver;
    }
}
