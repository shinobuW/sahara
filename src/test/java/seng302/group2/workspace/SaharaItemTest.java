package seng302.group2.workspace;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.workspace.Workspace;

import static org.junit.Assert.*;

public class SaharaItemTest {
    @Test
    public void testSetStartId() throws Exception {
        SaharaItem.setStartId(0, true);
        Person person = new Person();
        Assert.assertEquals(0, person.getId());
        Assert.assertNotEquals(0, SaharaItem.NEXT_ID);

        // No force, takes the max
        SaharaItem.setStartId(0, false);
        Assert.assertNotEquals(0, SaharaItem.NEXT_ID);

        // Forcing to reset to 0, even if it's not the max
        SaharaItem.setStartId(0, true);
        Assert.assertNotEquals(0, SaharaItem.NEXT_ID);

        SaharaItem.setStartId(9001, false);
        Assert.assertNotEquals(9001, SaharaItem.NEXT_ID);
    }
}