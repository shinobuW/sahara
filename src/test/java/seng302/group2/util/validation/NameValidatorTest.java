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
public class NameValidatorTest
{
    
    public NameValidatorTest()
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
     * Test of validateName method, of class NameValidator.
     */
    @Test
    public void testValidateName()
    {
        Assert.assertEquals(ValidationStatus.INVALID, NameValidator.validateName(""));
        Assert.assertEquals(ValidationStatus.VALID, NameValidator.validateName("Greg"));
    }
    
}
