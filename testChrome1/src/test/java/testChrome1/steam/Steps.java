package testChrome1.steam;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import testChrome1.WebConfigration;
import testChrome1.WebDriverFactory;
import testChrome1.WebDriverSingleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by New User on 24.11.2018.
 */
public class Steps {
    private Steps() {

    }

    private static testChrome1.steam.Steps steps;

    public static testChrome1.steam.Steps getInstance() {
        if (steps == null) {
            steps = new testChrome1.steam.Steps();
        }

        return steps;
    }

    public void openSteam(String urlSteam, String lang) {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        driver.get(urlSteam);
        try {
            chooseLang(lang);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebConfigration configration = WebConfigration.getInstance();


        FluentWait<WebDriver> pageLoadWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(configration.getPolling()))
                .withTimeout(Duration.ofSeconds(configration.getTimeout()))
                .ignoring(NoSuchElementException.class);
        WebElement gameElement = pageLoadWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement loginElement = webDriver.findElement(By.xpath("//*[@id=\"genre_tab\"]/span"));
                if (loginElement != null && loginElement.isDisplayed()) {
                    return loginElement;
                }
                return null;
            }
        });


    }

    private void chooseLang(String language) throws InterruptedException {
        //   String pageSource = webDriver.getPageSource();
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebConfigration configration = WebConfigration.getInstance();
        String currentLang = driver.findElement(By.xpath("/html")).getAttribute("lang");
        String shortLang;
        switch (language) {
            case "russian":
                shortLang = "ru";
                break;
            case "english":
                shortLang = "en";
                break;
            default:
                throw new RuntimeException("неизвестный язык");
        }

        if (currentLang.equals(shortLang)) {
            return;
        }
        WebElement languageChoice = driver.findElement(By.xpath("//*[@id=\"language_pulldown\"]"));
        languageChoice.click();
        String xpath = String.format(".//a[@href=\"?l=%s\"]", language);
        WebElement languageElement = driver.findElement(By.xpath(xpath));
        languageElement.click();

    }

    public void openGamesActions() {

        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebConfigration configration = WebConfigration.getInstance();
        WebElement games = driver.findElement(By.xpath("//*[@id=\"genre_tab\"]/span"));
        Actions actions = new Actions(driver);
        actions.moveToElement(games).build().perform();


        WebElement actionsElement = (new WebDriverWait(driver, configration.getTimeout()))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"genre_flyout\"]/div/a[16]")));
        actions.moveToElement(actionsElement);
        actionsElement.click();
    }

    public void openTop() {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebConfigration configration = WebConfigration.getInstance();
        Actions actions = new Actions(driver);
        WebElement actionsElement = (new WebDriverWait(driver, configration.getTimeout()))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tab_select_TopSellers\"]/div")));
        actions.moveToElement(actionsElement);
        actionsElement.click();

    }

    public List<MenuItem> getListOfGames() {
        List<MenuItem> items = new ArrayList<>();

        List<WebElement> menuItemsGamesSpans = WebDriverSingleton.getInstance().getWebDriver().findElements(By.xpath("//*[@id=\"TopSellersRows\"]//div[(@class='discount_pct' or @class='bundle_base_discount') and text() != '']/../.."));
        //*[@id="tab_content_TopSellers"]
        for (WebElement element : menuItemsGamesSpans) {
            String href = element.getAttribute("href");
            String discountValue = element.findElement(By.xpath(".//div[(@class='discount_pct' or @class='bundle_base_discount')]")).getText();
            String discountWithoutPercent = discountValue.replace("%", "");
            if (discountWithoutPercent.isEmpty()) {
                continue;
            }
            Integer discount = -Integer.valueOf(discountWithoutPercent); // -10 -> 10
            MenuItem menuItem = new MenuItem(href, discount);
            items.add(menuItem);
        }

        return items;
    }

    public MenuItem findElementWithMaxDiscount(List<MenuItem> itemsForMaxDiscountChoice) {

        if (itemsForMaxDiscountChoice == null || itemsForMaxDiscountChoice.isEmpty()) {
            return null;
        }
        MenuItem max = itemsForMaxDiscountChoice.get(0);
        for (MenuItem element : itemsForMaxDiscountChoice) {
            if (max.getDiscount() < element.getDiscount()) {
                max = element;
            }
        }

        return max;
    }

    public Integer openMaxDiscountGameAndDiscountCompare(MenuItem item) {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebConfigration configration = WebConfigration.getInstance();
        driver.get(item.getId());
        Steps.getInstance().passAgeCheckIfNeeded();
        WebElement realDiscountElement = (new WebDriverWait(driver, configration.getTimeout()))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[(@class='discount_block game_purchase_discount')]")));
        //TODO: better find element discount
        String realDiscountValue = realDiscountElement.findElement(By.xpath(".//div[(@class='discount_pct' or @class='bundle_base_discount')]")).getText();
        String realDiscountWithoutPercent = realDiscountValue.replace("%", "");
        Integer realDiscount = -Integer.valueOf(realDiscountWithoutPercent); //
        return realDiscount;
    }

    public void passAgeCheckIfNeeded() {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        WebElement ageCkeckBlock;
        try {
            ageCkeckBlock = driver.findElement(By.id("app_agegate"));
            // boolean blockAgePresent = ageCkeckBlock.isDisplayed();
        } catch (NoSuchElementException e) {
            ageCkeckBlock = null;
        }
        boolean blockAgePresent = ageCkeckBlock != null;
        if (blockAgePresent) {
            //if (driver.getCurrentUrl().contains("agecheck")) {
            if (driver.getPageSource().contains("ageYear")) {
                WebElement ageYearsElement = driver.findElement(By.xpath("//*[@id=\"ageYear\"]"));
                ageYearsElement.sendKeys("1999");
                driver.findElement(By.xpath("//*[@id=\"app_agegate\"]/div[1]/div[3]/a[1]/span")).click();
            } else {
                WebElement openPageAfterAgeCheck = driver.findElement(By.xpath("//*[@id=\"app_agegate\"]/div[3]/a[1]/span"));
                openPageAfterAgeCheck.click();
            }

        }
    }

    private String waitUntilDownloadComplete(String directory, String filename) {
        Path path = Paths.get(directory, filename);
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();

        new WebDriverWait(driver, Duration.ofMinutes(1).getSeconds(), Duration.ofSeconds(10).getSeconds())
                .until(webDriver -> {
                            return Files.exists(path);
                        }
                );

        new WebDriverWait(driver, Duration.ofMinutes(5).getSeconds(), Duration.ofSeconds(10).getSeconds())
                .until(new Function<WebDriver, Boolean>() {
                           private long fileSize = 0;

                           @Override
                           public Boolean apply(WebDriver webDriver) {
                               try {
                                   long currentSize = Files.size(path);
                                   if (currentSize > fileSize) {
                                       fileSize = currentSize;
                                       return Boolean.FALSE;
                                   }

                                   return Boolean.TRUE;
                               } catch (IOException e) {
                                   throw new RuntimeException(e);
                               }
                           }
                       }
                );
        return path.toAbsolutePath().toString();
    }

    public void downloadStreamInstaller() {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();

        WebConfigration configration = WebConfigration.getInstance();
        driver.findElement(By.xpath("//*[@id=\"global_action_menu\"]/div[1]/a")).click();
        WebElement downloadStreamInstallerElement = (new WebDriverWait(driver, configration.getTimeout()))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"about_install_steam_link\"]/span")));
        downloadStreamInstallerElement.click();

        String downloadsDirectory = WebDriverFactory.getDownloadsDirectory();

        waitUntilDownloadComplete(downloadsDirectory, "SteamSetup.exe");
    }
}
