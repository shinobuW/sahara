package seng302.group2.util.config;

import junit.framework.TestCase;
import org.junit.Assert;
import seng302.group2.Global;

/**
 * Created by Jordane on 16/04/2015.
 */
public class ConfigurationTest extends TestCase
{

    public void testUpdateConfiguration()
    {
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

    public void testUpdateRuntime() throws Exception
    {
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