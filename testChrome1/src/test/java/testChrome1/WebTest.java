package testChrome1;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
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

    public List<MenuItem> getMenuLevel1() {

        List<MenuItem> items = new ArrayList<>();

        List<WebElement> menuItemsLevel1Spans = ClassDriver.getInstance().getWebDriver().findElements(By.xpath("//*[@id=\"Catalog\"]/div//div/span[@data-level=1]"));
        for (WebElement element : menuItemsLevel1Spans) {
            String id = element.getAttribute("id");
            String name = element.getText();
            MenuItem menuItem = new MenuItem(id, name);
            items.add(menuItem);
        }

        return items;
    }

    public void openMenuItem(MenuItem item) {
        String xpath = String.format("//*[@id=\"%s\"]", item.getId());
        WebElement menuLink = ClassDriver.getInstance().getWebDriver().findElement(By.xpath(xpath));
        menuLink.click();
    }

    @AfterClass
    public static void closeChromeDriver() {
        if (ClassDriver.getInstance().getWebDriver() != null) {
            ClassDriver.getInstance().getWebDriver().close();
        }
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        ClassDriver singletoneInstance = ClassDriver.getInstance();
        WebDriver driver = singletoneInstance.getWebDriver();
        String username = "(44)765-90-60";
        String password = "testa1qa";
        String displayName = "userShop.by_20";
        driver.get("https://www.shop.by");

        Steps steps = Steps.getInstance();
        steps.login(username, password, displayName);

        //    List<MenuItem> menuItems = getMenuLevel1();

        //    MenuItem selectedItem = menuItems.get(0);
        //    openMenuItem(selectedItem);


    }
}
