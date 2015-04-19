package seng302.group2.util.validation;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;

import java.text.ParseException;
import java.util.Date;

/**
 * Tests that the short name validator works as expected
 * Created by Jordane on 19/04/2015.
 */
public class ShortNameValidatorTest extends TestCase
{
    @BeforeClass
    public void setUp()
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
     * A test to see if names are being validated properly
     */
    public void testValidateShortName()
    {
        assertEquals(ValidationStatus.INVALID, ShortNameValidator.validateShortName(""));
        assertEquals(ValidationStatus.NON_UNIQUE,
                ShortNameValidator.validateShortName("btm38"));
        assertEquals(ValidationStatus.VALID, ShortNameValidator.validateShortName("new"));
        assertEquals(ValidationStatus.OUT_OF_RANGE, ShortNameValidator.validateShortName("this is much more" +
                "than 20 characters long"));
    }
}