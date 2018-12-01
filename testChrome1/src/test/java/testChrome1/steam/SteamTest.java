package testChrome1.steam;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testChrome1.WebConfigration;
import testChrome1.WebDriverSingleton;
import testChrome1.shop.by.WebTest;

import java.util.List;

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
    public void test000000AgeCheck() {
        WebDriverSingleton.getInstance().getWebDriver().get("https://store.steampowered.com/agecheck/app/863550/");
        Steps.getInstance().passAgeCheckIfNeeded();
    }

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
    public void test04OpenItemWithMaxDiscount() {
        List<MenuItem> menuItems = Steps.getInstance().getListOfGames();
        MenuItem menuItemWithMaxDiscount = Steps.getInstance().findElementWithMaxDiscount(menuItems);
        Assert.assertNotNull(menuItemWithMaxDiscount);
        // TODO: open(menuItemWithMaxDiscount)

        // TODO: check real discount  -> get real discount && compare with discount of menuItemWithMaxDiscount
        Integer realDiscount = Steps.getInstance().openMaxDiscountGameAndDiscountCompare(menuItemWithMaxDiscount);
        Assert.assertThat(realDiscount, CoreMatchers.is(menuItemWithMaxDiscount.getDiscount()));
    }

    @Test
    public void test05DownloadFile() {

    }
}
