package seng302.group2.util.config;

import junit.framework.TestCase;
import org.junit.Assert;
import seng302.group2.Global;

/**
 * A class to test the configuration
 * Created by Jordane on 16/04/2015.
 */
public class ConfigurationTest extends TestCase {
    /**
     * Test that the configuration updates to the values of the runtime state (used before saving)
     */
    public void testUpdateConfiguration() {
        String testLocation = "/aTestLocation/";
        String anotherTestLocation = "/anotherTestLocation/";
        Configuration config = new Configuration();

        config.setLastSaveLocation(testLocation);
        Global.lastSaveLocation = anotherTestLocation;

        Assert.assertNotEquals(config.getLastSaveLocation(), Global.lastSaveLocation);

        config.updateConfiguration();

        Assert.assertEquals(config.getLastSaveLocation(), Global.lastSaveLocation);
        Assert.assertEquals(config.getLastSaveLocation(), anotherTestLocation);
    }


    /**
     * Test that the runtime updates to the values of the configuration (used after loading)
     */
    public void testUpdateRuntime() {
        String testLocation = "/aTestLocation/";
        String anotherTestLocation = "/anotherTestLocation/";
        Configuration config = new Configuration();

        config.setLastSaveLocation(testLocation);
        Global.lastSaveLocation = anotherTestLocation;

        Assert.assertNotEquals(config.getLastSaveLocation(), Global.lastSaveLocation);

        config.updateRuntime();

        Assert.assertEquals(config.getLastSaveLocation(), Global.lastSaveLocation);
        Assert.assertEquals(config.getLastSaveLocation(), testLocation);
    }
}