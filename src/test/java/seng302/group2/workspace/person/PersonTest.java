/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.person;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;

/**
 * A series of tests relating to Persons
 * @author Jordane Lew (jml168)
 * @author Bronson McNaughton (btm38)
 */
public class PersonTest extends TestCase
{
    /**
     * Create the test case.
     * @param testName name of the test case
     */
    public PersonTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(PersonTest.class);
    }

    /**
     * A simple test for the Person constructors
     */
    public void testPersonConstructors()
    {
        Person pers = new Person();
        Assert.assertEquals("lastName", pers.getLastName());
        Assert.assertEquals("firstName", pers.getFirstName());
        Assert.assertEquals("unnamed", pers.getShortName());
        Assert.assertEquals("", pers.getEmail());
        Assert.assertEquals("", pers.getDescription());
        Assert.assertNotNull(pers.getBirthDate());
        Assert.assertEquals("unnamed", pers.toString());
        Assert.assertEquals(null, pers.getChildren());
        
        Person pers2 = new Person("btm38", "Bronson", "McNaughton", "btm38@gmail.com", 
                "A really cool dude",  new Date(1994, 12, 19)); 
        Assert.assertEquals("McNaughton", pers2.getLastName());
        Assert.assertEquals("Bronson", pers2.getFirstName());
        Assert.assertEquals("btm38", pers2.getShortName());
        Assert.assertEquals("btm38@gmail.com", pers2.getEmail());
        Assert.assertEquals("A really cool dude", pers2.getDescription());
        Assert.assertEquals(new Date(1994, 12, 19), pers2.getBirthDate());
        Assert.assertEquals("btm38", pers2.toString());
        
        Person pers3 = new Person("btm38", "Billy", "Mays", "billymays@gmail.com", 
                "Pretty cool, but not as cool as Bronson",  new Date(1958, 7, 20)); 
        Assert.assertEquals("Mays", pers3.getLastName());
        Assert.assertEquals("Billy", pers3.getFirstName());
        // Should this be btm38~1? There is code written to prevent unique names yet does not seem
        // to be working.
        Assert.assertEquals("btm38", pers3.getShortName());
        Assert.assertEquals("billymays@gmail.com", pers3.getEmail());
        Assert.assertEquals("Pretty cool, but not as cool as Bronson", pers3.getDescription());
        Assert.assertEquals(new Date(1958, 7, 20), pers3.getBirthDate());    
        Assert.assertEquals("btm38", pers3.toString());
    }
    
    /**
     * Tests for Persons' setter methods.
     */
    public void testPersonSetters()
    {
        Person pers = new Person();
        pers.setShortName("btm38");
        pers.setLastName("McNaughton");
        pers.setFirstName("Bronson");
        pers.setEmail("btm38@gmail.com");
        pers.setDescription("A really cool dude");
        pers.setBirthDate(new Date(1994, 12, 19));
        
        Assert.assertEquals("McNaughton", pers.getLastName());
        Assert.assertEquals("Bronson", pers.getFirstName());
        Assert.assertEquals("btm38", pers.getShortName());
        Assert.assertEquals("btm38@gmail.com", pers.getEmail());
        Assert.assertEquals("A really cool dude", pers.getDescription());
        Assert.assertEquals(new Date(1994, 12, 19), pers.getBirthDate());
        Assert.assertEquals("btm38", pers.toString());
    }
}
