package testChrome1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.w3c.dom.ranges.RangeException;

/**
 * Created by New User on 15.11.2018.
 */
public class ClassDriver {
    private static ClassDriver instance;

    private ClassDriver() {
        String browser = "chrome";
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
                // throw new RuntimeException("unknown browser " + browser);
        }
        webDriver.manage().window().maximize();
    }

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static ClassDriver getInstance() {
        if (instance == null) {
            instance = new ClassDriver();
        }
        return instance;

    }

}
