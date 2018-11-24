package testChrome1.shop.by;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import testChrome1.WebConfigration;
import testChrome1.WebDriverSingleton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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


    public void login(String url, String userLogin, String password, String displayName) {

        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        driver.get(url);
        WebConfigration configration = WebConfigration.getInstance();

        FluentWait<WebDriver> pageLoadWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(configration.getPolling()))
                .withTimeout(Duration.ofSeconds(configration.getTimeout()))
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
                .pollingEvery(Duration.ofSeconds(configration.getPolling()))
                .withTimeout(Duration.ofSeconds(configration.getTimeout()))
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
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebConfigration configration = WebConfigration.getInstance();

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(configration.getPolling()))
                .withTimeout(Duration.ofSeconds(configration.getTimeout()))
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

        List<WebElement> menuItemsLevel1Spans = WebDriverSingleton.getInstance().getWebDriver().findElements(By.xpath("//*[@id=\"Catalog\"]/div//div/span[@data-level=1]"));
        for (WebElement element : menuItemsLevel1Spans) {
            String id = element.getAttribute("id");
            String name = element.getText();
            WebTest.MenuItem menuItem = new WebTest.MenuItem(id, name);
            items.add(menuItem);
        }

        return items;
    }

    public void openMainPage() {
        WebDriver driver = WebDriverSingleton.getInstance().getWebDriver();
        driver.get("https://www.shop.by");

        WebElement myDinamicElement = (new WebDriverWait(driver, 60))
                .until(new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver d) {
                        return d.findElement(By.xpath("//*[@id=\"yt0\"]"));
                    }
                });
    }

    public List<String> getItemNamesWithReview() {
        WebDriver webDriver = WebDriverSingleton.getInstance().getWebDriver();
        String pageSource = webDriver.getPageSource();

        Pattern reviewPattern = Pattern.compile("<a class=\"ModelReviewsHome__NameModel\".*>(.*)</a><a.*");

        String[] lines = pageSource.split("\n");

        List<String> regexpNames = new ArrayList<>();
        for(String line: lines) {
            Matcher m = reviewPattern.matcher(line);
            if (m.matches()) {
                String itemName = m.group(1);
                regexpNames.add(itemName);
            }
        }

        return regexpNames;
//        List<String> itemNames = new ArrayList<>();
//        List<WebElement> itemsWithReview = webDriver.findElements(By.xpath("//div[@class=\"ModelReviewsHome__ModelReview\"]"));
//        for (WebElement itemWithReview : itemsWithReview) {
//            String itemName = itemWithReview.findElement(By.xpath(".//a[@class=\"ModelReviewsHome__NameModel\"]")).getText();
//            itemNames.add(itemName);
//        }
//
//        return itemNames;
    }


    public void saveToCSV(String sampleCsvFile, List<String> items) throws IOException {
        WebConfigration configration = WebConfigration.getInstance();
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(configration.getSAMPLE_CSV_FILE()));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("index", "name"));
        int index = 0;
        for (String name : items) {
            csvPrinter.printRecord(index, name);
            index++;
        }
        csvPrinter.flush();
    }
}
