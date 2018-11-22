package testChrome1;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class WebTest {

    /// global
    public static String username = "(44)765-90-60";
    public static String password = "testa1qa";
    public static String displayName = "userShop.by_20";
    public static String url = "https://www.shop.by";

    ///end global
    public static class MenuItem {
        private String id;
        private String name;

        public MenuItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }


    public void openMenuItem(MenuItem item) {
        String xpath = String.format("//*[@id=\"%s\"]", item.getId());
        WebElement menuLink = WebDriverSingleton.getInstance().getWebDriver().findElement(By.xpath(xpath));
        menuLink.click();
    }

    @AfterClass
    public static void closeChromeDriver() {
        if (WebDriverSingleton.getInstance().getWebDriver() != null) {
            WebDriverSingleton.getInstance().getWebDriver().close();
        }
    }

    @Test
    public void testLogin() {
        //*
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();


        Steps.getInstance().login(url, username, password, displayName);
        System.out.println();
    }

    @Test
    public void testGetMenuItems() {
        List<MenuItem> menuItems = Steps.getInstance().getMenuLevel1();
        //Assert.assertEquals();
        System.out.println();
    }

    @Test
    public void testGetMenuRandom() {
        List<MenuItem> menuItems = Steps.getInstance().getMenuLevel1();
        int randomIndex = (int) (Math.random() * menuItems.size());
        MenuItem selectedItem = menuItems.get(randomIndex);
        openMenuItem(selectedItem);
        //Assert.assertEquals();
        System.out.println();
    }

    @Test
    public void testGoToMainPage() {
        Steps.getInstance().openMainPage();
        //Assert.assertEquals();
        System.out.println();
    }

    @Test
    public void testSaveReviewToCSV() {

        String filename;
        List<String> names = Steps.getInstance().getItemNamesWithReview();
        try {
            Steps.getInstance().saveToCSV(filename, names);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert.assertEquals();
        System.out.println();
    }

    @Test
    public void testLogout() {
        Steps.getInstance().logout();
        // Assert.assertEquals();
        System.out.println();
    }


}
