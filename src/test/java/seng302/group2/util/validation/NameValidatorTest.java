/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Jordane
 */
public class NameValidatorTest
{
    
    public NameValidatorTest()
    {
    }


    /**
     * Test of validateName method, of class NameValidator.
     */
    @Test
    public void testValidateName()
    {
        assertEquals(ValidationStatus.INVALID, NameValidator.validateName(""));
        assertEquals(ValidationStatus.VALID, NameValidator.validateName("Greg"));
    }
}
