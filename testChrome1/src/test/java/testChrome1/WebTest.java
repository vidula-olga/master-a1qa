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

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        WebDriverSingleton singletoneInstance = WebDriverSingleton.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        String username = "(44)765-90-60";
        String password = "testa1qa";
        String displayName = "userShop.by_20";
        driver.get("https://www.shop.by");

        Steps steps = Steps.getInstance();
        steps.login(username, password, displayName);

        List<MenuItem> menuItems = steps.getMenuLevel1();
        int randomIndex = (int) (Math.random() * menuItems.size());
        MenuItem selectedItem = menuItems.get(randomIndex);
        openMenuItem(selectedItem);

        steps.openMainPage();

        List<String> names = steps.getItemNamesWithReview();
        try {
            steps.saveToCSV(names);
        } catch (IOException e) {
            e.printStackTrace();
        }

        steps.logout();
    }
}
