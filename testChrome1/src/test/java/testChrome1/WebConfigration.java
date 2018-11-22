package testChrome1;

/**
 * Created by New User on 22.11.2018.
 */
public class WebConfigration {

    /// global
    private String username = "(44)765-90-60";
    private String password = "testa1qa";
    private String displayName = "userShop.by_20";
    private String url = "https://www.shop.by";
    private String SAMPLE_CSV_FILE = "./test.csv";
    private Integer pollingTimeout = 5;
    private Integer durationTimeout = 60;

    private static WebConfigration configration;


    private WebConfigration() {

    }

    public static WebConfigration getInstance() {
        if (configration == null) {
            configration = new WebConfigration();
        }
        return configration;

    }

    public Integer getPolling() {
        return pollingTimeout;
    }

    public Integer getTimeout() {
        return durationTimeout;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public String getSAMPLE_CSV_FILE() {
        return SAMPLE_CSV_FILE;
    }
}
