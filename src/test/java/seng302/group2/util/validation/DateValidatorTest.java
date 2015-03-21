/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static seng302.group2.util.validation.ValidationStatus.OUT_OF_RANGE;
import static seng302.group2.util.validation.ValidationStatus.PATTERN_MISMATCH;
import static seng302.group2.util.validation.ValidationStatus.VALID;

/**
 *
 * @author Jordane
 */
public class DateValidatorTest
{
    
    public DateValidatorTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
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
     * Test of isValidDateString method, of class DateValidator.
     */
    @Test
    public void testIsValidDateString()
    {
        assertEquals(PATTERN_MISMATCH, DateValidator.isValidDateString(""));
        assertEquals(OUT_OF_RANGE, DateValidator.isValidDateString("12/12/9999"));
        assertEquals(VALID, DateValidator.isValidDateString("20/03/2015"));
    }
    
}
