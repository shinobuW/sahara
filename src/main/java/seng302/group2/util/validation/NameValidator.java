/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

/**
 *
 * @author Jordane
 */
public class NameValidator
{
    public static ValidationStatus validateName(String name)
    {
        if (name != null && !name.equals(""))
        {
            return ValidationStatus.VALID;
        }
        else
        {
            return ValidationStatus.INVALID;
        }
    }
}
