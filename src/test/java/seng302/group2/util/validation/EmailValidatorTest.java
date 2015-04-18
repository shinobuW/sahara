/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;

import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * A test for the Email validation class
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
        Global.currentWorkspace = new Workspace();
        Person pers = new Person("btm38", "McNaughton", "Bronson", "btm38@gmail.com",
                "A really cool dude", new Date(1994, 12, 19));
        Global.currentWorkspace.add(pers);
    }


    /**
     * Test of validEmail method, of class EmailValidator.
     */
    @Test
    public void testValidEmail()
    {
        assertEquals(ValidationStatus.INVALID, EmailValidator.validEmail(""));
        assertEquals(ValidationStatus.VALID, EmailValidator.validEmail("abc@gmail.com"));
    }
    
}
