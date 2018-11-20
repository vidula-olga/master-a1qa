package testChrome1;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by New User on 16.11.2018.
 */
public class Steps {
    private Steps() {

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
                .pollingEvery(Duration.ofSeconds(2))
                .withTimeout(Duration.ofSeconds(60))
                .ignoring(NoSuchElementException.class);

        WebElement loginElement = pageLoadWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement loginElement = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/span"));
                if (loginElement != null && loginElement.isDisplayed()) {
                    return loginElement;
                }
                return null;
            }
        });

        loginElement.click();

        WebElement phone = driver.findElement(By.xpath("//*[@id=\"LLoginForm_phone\"]"));
        phone.sendKeys(userLogin); //+375447659060
        WebElement passw = driver.findElement(By.xpath("//*[@id=\"LLoginForm_password\"]"));
        passw.sendKeys(password);


        WebElement login = driver.findElement(By.xpath("//*[@name=\"yt2\"]"));
        login.click();

        System.out.println(System.currentTimeMillis() / 1000);
        FluentWait<WebDriver> loginWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(5))
                .withTimeout(Duration.ofSeconds(60))
                .ignoring(NoSuchElementException.class);

        try {
            loginWait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement element = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/a/div/div/span/div"));
                    return element != null && element.getText().equals(displayName);
                }
            });
        } finally {
            System.out.println(System.currentTimeMillis() / 1000);
        }
    }

    public void logout() {
        ClassDriver singletoneInstance = ClassDriver.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(30))
                .ignoring(NoSuchElementException.class);

        WebElement logoutMenu = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement logoutMenu = driver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/a"));
                driver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/a"));
                if (logoutMenu.isDisplayed()) {
                    return logoutMenu;
                }
                return null;
            }
        });

        logoutMenu.click();

        WebElement logoutElement = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"yt0\"]"));
                if (element != null && element.isDisplayed()) {
                    return element;
                }

                return null;
            }
        });

        try {
            Thread.sleep(Duration.ofSeconds(3).toMillis());  // TODO: wait while animation completed
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
        logoutElement.click();
    }

    public List<WebTest.MenuItem> getMenuLevel1() {
        List<WebTest.MenuItem> items = new ArrayList<>();

        List<WebElement> menuItemsLevel1Spans = ClassDriver.getInstance().getWebDriver().findElements(By.xpath("//*[@id=\"Catalog\"]/div//div/span[@data-level=1]"));
        for (WebElement element : menuItemsLevel1Spans) {
            String id = element.getAttribute("id");
            String name = element.getText();
            WebTest.MenuItem menuItem = new WebTest.MenuItem(id, name);
            items.add(menuItem);
        }

        return items;
    }
}
