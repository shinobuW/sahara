package seng302.group2.util.config;

import junit.framework.TestCase;
import seng302.group2.Global;

import static org.junit.Assert.assertNotEquals;

/**
 * Tests configuration methods for synchronising between runtime and config settings
 * Created by Jordane on 16/04/2015.
 */
public class ConfigurationTest extends TestCase
{
    /**
     * Test that the configuration updates to the values of the runtime state (used before saving)
     */
    public void testUpdateConfiguration()
    {
        String testLocation = "/aTestLocation/";
        String anotherTestLocation = "/anotherTestLocation/";
        Configuration config = new Configuration();

        config.setLastSaveLocation(testLocation);
        Global.lastSaveLocation = anotherTestLocation;

        assertNotEquals(config.getLastSaveLocation(), Global.lastSaveLocation);

        config.updateConfiguration();

        assertEquals(config.getLastSaveLocation(), Global.lastSaveLocation);
        assertEquals(config.getLastSaveLocation(), anotherTestLocation);
    }


    /**
     * Test that the runtime updates to the values of the configuration (used after loading)
     */
    public void testUpdateRuntime()
    {
        String testLocation = "/aTestLocation/";
        String anotherTestLocation = "/anotherTestLocation/";
        Configuration config = new Configuration();

        config.setLastSaveLocation(testLocation);
        Global.lastSaveLocation = anotherTestLocation;

        assertNotEquals(config.getLastSaveLocation(), Global.lastSaveLocation);

        config.updateRuntime();

        assertEquals(config.getLastSaveLocation(), Global.lastSaveLocation);
        assertEquals(config.getLastSaveLocation(), testLocation);
    }
}