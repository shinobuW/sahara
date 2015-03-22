/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
            Date parsedBirthDate = datePattern.parse(birthDate);
            if (parsedBirthDate.after( Date.from(Instant.now())))
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
}
