package testChrome1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by New User on 20.11.2018.
 */
public class WebDriverFactory {
    private static Path downloadsDirectory;

    public static String getDownloadsDirectory() {
        if (downloadsDirectory == null) {
            try {
                downloadsDirectory = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "steam");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return downloadsDirectory.toAbsolutePath().toString();
    }

    public static WebDriver getWebDriver(String browser) {
        WebDriver webDriver;
        switch (browser) {
            case "firefox": {
                System.setProperty("webdriver.gecko.driver", "C:\\IdeaProjects\\geckodriver-v0.23.0-arm7hf\\geckodriver");
                webDriver = new FirefoxDriver();
                break;
            }
            case "chrome": {
                System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");

                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.prompt_for_download", false);
                chromePrefs.put("download.default_directory", getDownloadsDirectory());
                chromePrefs.put("download.directory_upgrade", true);
                chromePrefs.put("safebrowsing.enabled", true);

                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);

                options.addArguments(
                        "--test-type",
                        "--disable-extensions",
                        "--disable-web-security",
                        "--safebrowsing-disable-download-protection");

                webDriver = new ChromeDriver(options);

                break;
            }
            default:
                RuntimeException exception = new RuntimeException("unknown browser " + browser);
                throw exception;
        }

        return webDriver;
    }
}
