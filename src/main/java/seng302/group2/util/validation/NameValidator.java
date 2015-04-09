/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import seng302.group2.scenes.control.RequiredField;

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
    
        /**
     * Checks whether the name is valid
     * @param nameField the text field
     * @return If the name is valid
     */
    public static boolean validateName(RequiredField nameField)
    {
        switch (NameValidator.validateName(nameField.getText()))
        {
            case VALID:
                nameField.hideErrorField();
                return true;
            case INVALID:
                nameField.showErrorField("* Please insert a name");
                return false;
            default:
                nameField.showErrorField("* Not a valid name");
                return false;
        }
    }
}
