/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import org.junit.*;

import static junit.framework.TestCase.assertEquals;

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
     *
     */
    @Test
    public void testIsValidDateString()
    {
        assertEquals(ValidationStatus.NULL, DateValidator.isValidDateString(""));
        assertEquals(ValidationStatus.OUT_OF_RANGE,
                DateValidator.isValidDateString("12/12/9999"));
        assertEquals(ValidationStatus.VALID, DateValidator.isValidDateString("20/03/2015"));
        assertEquals(ValidationStatus.PATTERN_MISMATCH,
                DateValidator.isValidDateString("20/03/15"));
    }
    
}
