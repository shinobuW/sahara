/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import java.text.ParseException;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;

import java.util.Date;
import static junit.framework.Assert.fail;

/**
 *
 * @author Jordane
 */
public class ShortNameValidatorTest
{
    @BeforeClass
    public static void setUp()
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

        Global.currentWorkspace = new Workspace();
        Person pers = new Person("btm38", "McNaughton", "Bronson", "btm38@gmail.com",
                "A really cool dude", dob);
        Global.currentWorkspace.add(pers);
    }


    /**
     * Test of isValidPerson method, of class ShortNameValidator.
     */
    @Test
    public void testIsValidPerson()
    {
        Assert.assertEquals(ValidationStatus.INVALID, ShortNameValidator.validateShortName(""));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE,
                ShortNameValidator.validateShortName("btm38"));
        Assert.assertEquals(ValidationStatus.VALID, ShortNameValidator.validateShortName("new"));
        Assert.assertEquals(ValidationStatus.OUT_OF_RANGE, ShortNameValidator.validateShortName(
                "this is much more than 20 characters long"));
    }
    
}
