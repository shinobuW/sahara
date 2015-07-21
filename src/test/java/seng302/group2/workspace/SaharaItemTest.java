package seng302.group2.workspace;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.workspace.Workspace;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import seng302.group2.workspace.categories.PeopleCategory;

public class SaharaItemTest {
    /**
     * A test to check the functionality of the ID functions
     */
    @Test
    public void testSetStartId() {
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

    @Test
    public void testId() {
        SaharaItem.setStartId(0, true);
        Person person = new Person();
        SaharaItem.setStartId(0, true);
        Person person2 = new Person();
        Assert.assertEquals(person.getId(), person2.getId());

        Person person3 = new Person();
        Assert.assertNotEquals(person.getId(), person3.getId());

        Set<Long> ids = new HashSet<>();
        int i = 0;
        while (i < 100) {
            SaharaItem item = new Person();
            Assert.assertTrue(ids.add(item.getId()));
            i++;
        }
    }
    
    @Test
    public void testEquivalentTo() {
        Person person1 = new Person();
        Person person2 = new Person();
        
        PeopleCategory peopleCategory1 = new PeopleCategory();
        
        Assert.assertTrue(person1.equivalentTo(person1));
        Assert.assertFalse(person1.equivalentTo(person2));
        
        Assert.assertFalse(person1.equivalentTo(peopleCategory1));
    }
}