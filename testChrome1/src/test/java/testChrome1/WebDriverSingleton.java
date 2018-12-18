package testChrome1;

import org.openqa.selenium.WebDriver;

/**
 * Created by New User on 15.11.2018.
 */
public class WebDriverSingleton {
    private static WebDriverSingleton instance;

    private WebDriverSingleton() {
        String browser = "chrome";
        webDriver = WebDriverFactory.getWebDriver(browser);
        webDriver.manage().window().maximize();
    }

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static WebDriverSingleton getInstance() {
        if (instance == null) {
            instance = new WebDriverSingleton();
        }
        return instance;

    }

}
