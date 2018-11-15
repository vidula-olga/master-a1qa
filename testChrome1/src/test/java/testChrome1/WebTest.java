package testChrome1;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

        List<WebElement> menuItemsLevel1Spans = driver.findElements(By.xpath("//*[@id=\"Catalog\"]/div//div/span[@data-level=1]"));
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
        WebElement menuLink = driver.findElement(By.xpath(xpath));
        menuLink.click();
    }

    static WebDriver driver;

    @BeforeClass
    public static void setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void closeChromeDriver() {
//        if (driver != null) {
//            driver.close();
//        }
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        driver.get("https://www.shop.by");


        FluentWait<WebDriver> wait = new FluentWait<>(driver);

        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/span"));
                return element != null && element.isDisplayed();
            }
        });

        WebElement loginElement = driver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/span"));
        loginElement.click();

        WebElement phone = driver.findElement(By.xpath("//*[@id=\"LLoginForm_phone\"]"));
        phone.sendKeys("(44)765-90-60"); //+375447659060
        WebElement passw = driver.findElement(By.xpath("//*[@id=\"LLoginForm_password\"]"));
        passw.sendKeys("testa1qa");


        WebElement login = driver.findElement(By.xpath("//*[@name=\"yt2\"]"));
        login.click();
        //    List<MenuItem> menuItems = getMenuLevel1();

        //    MenuItem selectedItem = menuItems.get(0);
        //    openMenuItem(selectedItem);


        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(By.xpath("//*[@id=\"Header__Authentication\"]/div[2]/a/div/div/span/div"));
                return element != null && element.getText().equals("userShop.by_20");
            }
        });
    }
}
