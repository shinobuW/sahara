/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.person;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.Global;

import java.text.ParseException;
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
        Date dob = new Date();

        try
        {
            dob = Global.datePattern.parse("19/12/1994");
        }
        catch (ParseException e)
        {
            fail("Date parsing error, needs fixing");
        }

        Person pers = new Person();
        assertEquals("lastName", pers.getLastName());
        assertEquals("firstName", pers.getFirstName());
        assertEquals("unnamed", pers.getShortName());
        assertEquals("", pers.getEmail());
        assertEquals("", pers.getDescription());
        assertNull(pers.getBirthDate());
        assertEquals("unnamed", pers.toString());
        assertEquals(null, pers.getChildren());
        assertEquals("", pers.getDateString());

        Person pers2 = new Person("btm38", "Bronson", "McNaughton", "btm38@gmail.com", 
                "A really cool dude", dob);
        assertEquals("McNaughton", pers2.getLastName());
        assertEquals("Bronson", pers2.getFirstName());
        assertEquals("btm38", pers2.getShortName());
        assertEquals("btm38@gmail.com", pers2.getEmail());
        assertEquals("A really cool dude", pers2.getDescription());
        assertNotNull(pers2.getBirthDate());
        assertEquals(dob, pers2.getBirthDate());
        assertEquals("btm38", pers2.toString());
        assertEquals("19/12/1994", pers2.getDateString());
        
        Person pers3 = new Person("btm38", "Billy", "Mays", "billymays@gmail.com", 
                "Pretty cool, but not as cool as Bronson", null);
        assertEquals("Mays", pers3.getLastName());
        assertEquals("Billy", pers3.getFirstName());
        // Should this be btm38~1? There is code written to prevent unique names yet does not seem
        // to be working.
        assertEquals("btm38", pers3.getShortName());
        assertEquals("billymays@gmail.com", pers3.getEmail());
        assertEquals("Pretty cool, but not as cool as Bronson", pers3.getDescription());
        assertNull(pers3.getBirthDate());
        assertEquals("btm38", pers3.toString());
        assertEquals("", pers3.getDateString());
    }
    
    /**
     * Tests for Persons' setter methods.
     */
    public void testPersonSetters()
    {
        Date dob = new Date();

        try
        {
            dob = Global.datePattern.parse("19/12/1994");
        }
        catch (ParseException e)
        {
            fail("Date parsing error, needs fixing");
        }

        Person pers = new Person();
        pers.setShortName("btm38");
        pers.setLastName("McNaughton");
        pers.setFirstName("Bronson");
        pers.setEmail("btm38@gmail.com");
        pers.setDescription("A really cool dude");
        pers.setBirthDate(dob);
        
        assertEquals("McNaughton", pers.getLastName());
        assertEquals("Bronson", pers.getFirstName());
        assertEquals("btm38", pers.getShortName());
        assertEquals("btm38@gmail.com", pers.getEmail());
        assertEquals("A really cool dude", pers.getDescription());
        assertEquals(dob, pers.getBirthDate());
        assertEquals("19/12/1994", pers.getDateString());
        assertEquals("btm38", pers.toString());
    }
}
