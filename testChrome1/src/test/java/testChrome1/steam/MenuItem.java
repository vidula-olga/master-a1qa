package testChrome1.steam;

/**
 * Created by New User on 27.11.2018.
 */
public class MenuItem {
    private String id;
    private String discount;

    public MenuItem(String id, String discount) {
        this.id = id;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public String getDiscount() {
        return discount;
    }
}
