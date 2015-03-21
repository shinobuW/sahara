/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;
import static seng302.group2.util.validation.ValidationStatus.INVALID;
import static seng302.group2.util.validation.ValidationStatus.VALID;

/**
 *
 * @author Jordane
 */
public class EmailValidatorTest
{
    
    public EmailValidatorTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        Global.currentProject = new Project();
        Person pers = new Person("btm38", "McNaughton", "Bronson", "btm38@gmail.com",
                "A really cool dude", new Date(1994, 12, 19));
        Global.currentProject.addPerson(pers);
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of validEmail method, of class EmailValidator.
     */
    @Test
    public void testValidEmail()
    {
        assertEquals(INVALID, EmailValidator.validEmail(""));
        assertEquals(VALID, EmailValidator.validEmail("abc@gmail.com"));
    }
    
}
