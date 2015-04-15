/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;

import java.util.Date;

/**
 *
 * @author Jordane
 */
public class ShortNameValidatorTest
{
    
    public ShortNameValidatorTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        Global.currentProject = new Project();
        Person pers = new Person("btm38", "McNaughton", "Bronson", "btm38@gmail.com",
                "A really cool dude", new Date(1994, 12, 19));
        Global.currentProject.add(pers);
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
        Assert.assertEquals(ValidationStatus.OUT_OF_RANGE, ShortNameValidator.validateShortName("this is much more" +
                "than 20 characters long"));
    }
    
}
