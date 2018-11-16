package testChrome1;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

/**
 * Created by New User on 16.11.2018.
 */
public class Steps {
    private Steps (){

    }

    private static Steps steps;

    public static Steps getInstance() {
        if (steps == null) {
            steps = new Steps();
        }

        return steps;
    }


    public void login(String userLogin, String password, String displayName) {

        ClassDriver singletoneInstance = ClassDriver.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        FluentWait<WebDriver> pageLoadWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(5))
                .withTimeout(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        pageLoadWait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/span"));
                return element != null && element.isDisplayed();
            }
        });

        WebElement loginElement = driver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/span"));
        loginElement.click();

        WebElement phone = driver.findElement(By.xpath("//*[@id=\"LLoginForm_phone\"]"));
        phone.sendKeys(userLogin); //+375447659060
        WebElement passw = driver.findElement(By.xpath("//*[@id=\"LLoginForm_password\"]"));
        passw.sendKeys(password);


        WebElement login = driver.findElement(By.xpath("//*[@name=\"yt2\"]"));
        login.click();

        FluentWait<WebDriver> loginWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(5))
                .withTimeout(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        loginWait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/a/div/div/span/div"));
                return element != null && element.getText().equals(displayName);
            }
        });
    }
}
