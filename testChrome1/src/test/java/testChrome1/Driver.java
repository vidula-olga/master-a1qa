package testChrome1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by New User on 15.11.2018.
 */
public class Driver {
    private static Driver instance;

    private Driver() {
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
                throw new RuntimeException("unknown browser " + browser);
        }
        webDriver.manage().window().maximize();
    }

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static Driver getInstance() {
        if (instance == null) {
            instance = new Driver();
        }
        return instance;

    }

}
