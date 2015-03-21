/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import static seng302.group2.util.validation.ValidationStatus.OUT_OF_RANGE;
import static seng302.group2.util.validation.ValidationStatus.PATTERN_MISMATCH;
import static seng302.group2.util.validation.ValidationStatus.VALID;

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
                return OUT_OF_RANGE;
            }
            else
            {
                return VALID;
            }
        }
        catch (Exception ex)
        {
            return PATTERN_MISMATCH;
        }

    }
}
