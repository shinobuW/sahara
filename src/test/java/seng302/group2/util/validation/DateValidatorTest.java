/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the DateValidator
 * @author Jordane
 */
public class DateValidatorTest
{
    /**
     * Test of isValidDateString method, of class DateValidator.
     */
    @Test
    public void testIsValidDateString()
    {
        Assert.assertEquals(ValidationStatus.NULL, DateValidator.isValidDateString(""));
        Assert.assertEquals(ValidationStatus.OUT_OF_RANGE,
                DateValidator.isValidDateString("12/12/9999"));
        Assert.assertEquals(ValidationStatus.VALID, DateValidator.isValidDateString("20/03/2015"));
        Assert.assertEquals(ValidationStatus.PATTERN_MISMATCH,
                DateValidator.isValidDateString("20/03/15"));
    }
}
