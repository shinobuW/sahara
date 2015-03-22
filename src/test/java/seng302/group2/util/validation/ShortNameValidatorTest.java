/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
     * Test of isValidPerson method, of class ShortNameValidator.
     */
    @Test
    public void testIsValidPerson()
    {
        Assert.assertEquals(ValidationStatus.INVALID, ShortNameValidator.validateShortName(""));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE,
                ShortNameValidator.validateShortName("btm38"));
        Assert.assertEquals(ValidationStatus.VALID, ShortNameValidator.validateShortName("new"));
    }
    
}
