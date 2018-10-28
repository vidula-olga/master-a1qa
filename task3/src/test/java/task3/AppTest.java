package task3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldBeDefinedVariableAPP_NAME() {
        Assert.assertNotNull(System.getenv("APP_NAME"));
        Assert.assertEquals(System.getenv("APP_NAME"), "olgatest3");
    }

    public void shouldBeDefinedSystemProperty() {
        Assert.assertEquals(System.getProperty("propertyName"), "propertyValue");
    }
}
