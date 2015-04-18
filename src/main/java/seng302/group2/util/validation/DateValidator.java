/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import seng302.group2.scenes.control.CustomDateField;

/**
 *
 * @author Jordane
 */
public class DateValidator
{
    private static SimpleDateFormat datePattern = new SimpleDateFormat("dd/MM/yyyy");
     
    public static ValidationStatus isValidDateString(String birthDate)
    {
        datePattern.setLenient(false);
        try
        {
            String[] date = birthDate.split("/"); //returns an array with the day, month and year
            String year = date[date.length - 1];
            if (year.length() != 4)
            {
                return ValidationStatus.PATTERN_MISMATCH;
            }

            Date parsedBirthDate = datePattern.parse(birthDate);

            if (parsedBirthDate.after(Date.from(Instant.now())))
            {
                return ValidationStatus.OUT_OF_RANGE;
            }
            else
            {
                return ValidationStatus.VALID;
            }
        }
        catch (Exception ex)
        {
            return ValidationStatus.PATTERN_MISMATCH;

        }
    }
    
    /**
     * Checks whether the birth date format is correct
     * Shows error message and red borders if incorrect
     * @param customBirthDate the birth date error GUI label
     * @return true if correct format
    **/
    public static boolean validateBirthDate(CustomDateField customBirthDate)
    {
        // It is okay for the field to be blank, otherwise validate
        if (customBirthDate.getText().isEmpty()) 
        {
            customBirthDate.hideErrorField();
            return true;
        }

        switch (DateValidator.isValidDateString(customBirthDate.getText()))
        {
            case VALID:
                customBirthDate.hideErrorField();
                return true;
            case OUT_OF_RANGE:
                customBirthDate.showErrorField("* This is not a valid birth date");
                return false;
            case PATTERN_MISMATCH:
                customBirthDate.showErrorField("* Format must be dd/MM/yyyy e.g 12/03/1990");
                return false;
            default:
                return true;
        }
    }
}
