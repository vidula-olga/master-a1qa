package testChrome1.steam;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testChrome1.WebConfigration;
import testChrome1.WebDriverSingleton;
import testChrome1.shop.by.WebTest;

/**
 * Created by New User on 24.11.2018.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SteamTest {


    public void openMenuItem(WebTest.MenuItem item) {
        String xpath = String.format("//*[@id=\"%s\"]", item.getId());
        WebElement menuLink = WebDriverSingleton.getInstance().getWebDriver().findElement(By.xpath(xpath));
        menuLink.click();
    }

//    @AfterClass
  /*  public static void closeChromeDriver() {
        if (WebDriverSingleton.getInstance().getWebDriver() != null) {
            WebDriverSingleton.getInstance().getWebDriver().close();
        }
    }*/

    @Test
    public void test01OpenSteam() {
        WebConfigration configration = WebConfigration.getInstance();
        testChrome1.steam.Steps.getInstance().openSteam(configration.getUrlSteam());
    }

    @Test
    public void test02OpenGamesActions() {
        Steps.getInstance().openGamesActions();
    }

    @Test
    public void test03OpenTop() {
        Steps.getInstance().openTop();
    }

    @Test
    public void test04FindMaxDiscount() {
        Steps.getInstance().getListOfGames();
    }

    @Test
    public void test05OpenMaxDiscountGame() {

    }

    @Test
    public void test06DiscountCompare() {

    }

    @Test
    public void test07DownloadFile() {

    }
}
