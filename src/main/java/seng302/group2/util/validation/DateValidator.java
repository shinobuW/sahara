/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import seng302.group2.Global;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author Jordane
 */
public class DateValidator
{
    public static ValidationStatus isValidBirthdate(String birthDate)
    {
        if (birthDate.isEmpty())
        {
            return ValidationStatus.NULL;
        }

        Global.datePattern.setLenient(false);
        try
        {
            String[] date = birthDate.split("/"); //returns an array with the day, month and year
            String year = date[date.length - 1];
            if (year.length() != 4)
            {
                return ValidationStatus.PATTERN_MISMATCH;
            }

            Date parsedBirthDate = Global.datePattern.parse(birthDate);

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


    // TODO Make after allocation
    /*
    public static Allocation.AllocationStatus isValidAllocationDate(Allocation alloc, Project proj)
    {
        allocStart = alloc.getStartDate();
        allocEnd = alloc.getEndDate();

        for (Allocation projAlloc : proj.getAllocations())
        {
            if (projAlloc.isCurrentAllocation() == Allocation.AllocationStatus.PAST)
            {
                if (allocStart.before(projAlloc.getEndDate()))
                {
                    return Allocation.AllocationStatus.PAST;
                }
            }
        }
        return true;
    }*/



    public static boolean isFutureDate(Date date)
    {
        if (date.after(Date.from(Instant.now())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isCorrectDateFormat(CustomDateField dateField)
    {
        String dateString = dateField.getText();
        if (dateString.isEmpty())
        {
            return true;
        }
        else
        {
            Global.datePattern.setLenient(false);
            try
            {
                String[] date = dateString.split("/"); //returns an array with the day, month and yr
                String year = date[date.length - 1];
                if (year.length() != 4)
                {
                    dateField.showErrorField("* Format must be dd/MM/yyyy e.g 12/03/1990");
                    return false;
                }

                Date parsedDate = Global.datePattern.parse(dateString);
                dateField.hideErrorField();
                return true;

            }
            catch (Exception ex)
            {
                dateField.showErrorField("* Format must be dd/MM/yyyy e.g 12/03/1990");
                return false;

            }
        }
    }

    /**
     * Checks whether the birth date format is correct
     * Shows error message and red borders if incorrect
     * @param customBirthDate the birth date error GUI label
     * @return true if correct format
     **/
    public static boolean validateBirthDateField(CustomDateField customBirthDate)
    {
        switch (DateValidator.isValidBirthdate(customBirthDate.getText()))
        {
            case VALID:
                customBirthDate.hideErrorField();
                return true;
            case NULL:
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

    /**
     * Converts string to date
     * @param releaseDateString String to be converted
     * @return date
     */
    public static Date stringToDate(String releaseDateString)
    {
        Date releaseDate = new Date();
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(releaseDateString);
            releaseDate = df.parse(releaseDateString);
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing date");
        }
        return releaseDate;
    }
}
