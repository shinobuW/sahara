package seng302.group2.util.validation;

import junit.framework.TestCase;

/**
 * Tests that the short name validator works as expected
 * Created by Jordane on 19/04/2015.
 */
public class ShortNameValidatorTest extends TestCase
{

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