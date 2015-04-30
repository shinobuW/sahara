/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import java.util.Calendar;
import java.util.Date;
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
    
    public void testIsFutureDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date futureDate = cal.getTime();
        Assert.assertTrue(DateValidator.isFutureDate(futureDate));
    }
    
    public void testStringToDate()
    {
        Date testDate = DateValidator.stringToDate("12/12/2015");
        Assert.assertEquals(new Date("12/12/2015"), testDate);   
    }
}
