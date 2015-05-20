package seng302.group2.util.validation;

import seng302.group2.scenes.control.RequiredField;

/**
 * A class for validating the input on number only text fields
 * Created by drm127 on 20/05/15.
 */
public class NumberFieldValidator
{

    public static ValidationStatus validateNumberField(String input)
    {
        if (input == null || input.equals(""))
        {
            return ValidationStatus.NULL;
        }
        else
        {
            try
            {
                Integer.parseInt(input);
            }
            catch (NullPointerException ex)
            {
                return ValidationStatus.NULL;
            }
            catch (NumberFormatException ex)
            {
                return ValidationStatus.INVALID;
            }
            return ValidationStatus.VALID;
        }
    }

    /**
     * Checks whether the number is valid
     * @param numberField the number field
     * @return If the number is valid
     */
    public static boolean validateNumberField(RequiredField numberField)
    {
        switch (NumberFieldValidator.validateNumberField(numberField.getText()))
        {
            case VALID:
                numberField.hideErrorField();
                return true;
            case INVALID:
                numberField.showErrorField("* You must enter integer values only");
                return false;
            case NULL:
                numberField.showErrorField("* You must enter an integer priority for this story");
                return false;
            default:
                numberField.showErrorField("* Not a valid number");
                return false;
        }
    }


}
