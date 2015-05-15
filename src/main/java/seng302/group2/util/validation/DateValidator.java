/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import org.controlsfx.dialog.Dialogs;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

        //Global.datePattern.setLenient(false);
        try
        {
            String[] date = birthDate.split("/"); //returns an array with the day, month and year
            String year = date[date.length - 1];
            if (year.length() != 4)
            {
                return ValidationStatus.PATTERN_MISMATCH;
            }

            LocalDate parsedBirthDate = LocalDate.parse(birthDate, Global.dateFormatter);

            if (parsedBirthDate.isAfter(LocalDate.now()))
            {
                return ValidationStatus.OUT_OF_RANGE;
            }
            else
            {
                return ValidationStatus.VALID;
            }
        }
        catch (DateTimeParseException ex)
        {
            return ValidationStatus.PATTERN_MISMATCH;

        }
    }


    /**
    * Determines the Validation status of a new allocation, checking id there are any illegal
    * overlaps with the start or end dates of other allocations.
    * @param alloc The new allocation to check
    * @param proj The project within which to check the allocations
    * @return The validation status of the new allocation
    */
    public static ValidationStatus isValidAllocationDate(Allocation alloc, Project proj)
    {
        LocalDate allocStart = alloc.getStartDate();
        LocalDate allocEnd = alloc.getEndDate();

        if (allocEnd != null)
        {
            if (dateAfter(allocStart, allocEnd))
            {
                return ValidationStatus.ALLOCATION_DATES_WRONG_ORDER;
            }
        }

        for (Allocation projAlloc : proj.getTeamAllocations())
        {
            if (projAlloc != alloc)
            {
                if (projAlloc.getTeam() == alloc.getTeam())
                {
                    // Check for date overlaps within a single Teams total allocations
                    for (Allocation teamAlloc : projAlloc.getTeam().getProjectAllocations())
                    {
                        LocalDate teamStart = teamAlloc.getStartDate();
                        LocalDate teamEnd = teamAlloc.getEndDate();

                        if (datesEqual(teamStart, allocStart)
                                && datesEqual(teamEnd, allocEnd))
                        {
                            return ValidationStatus.ALLOCATION_DATES_EQUAL;
                        }
                        else if (dateAfter(teamStart, allocStart)
                                && dateBefore(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUPER_OVERLAP;
                        }
                        else if (dateAfter(teamStart, allocStart)
                                && datesEqual(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUPER_OVERLAP;
                        }
                        else if (datesEqual(teamStart, allocStart)
                                && dateBefore(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUPER_OVERLAP;
                        }
                        else if (dateBefore(teamStart, allocStart)
                                && dateAfter(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUB_OVERLAP;
                        }
                        else if (dateBefore(teamStart, allocStart)
                                && datesEqual(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUB_OVERLAP;
                        }
                        else if (datesEqual(teamStart, allocStart)
                                && dateAfter(teamEnd, allocEnd))
                        {
                            return ValidationStatus.SUB_OVERLAP;
                        }
                        else if (dateBefore(teamStart, allocStart)
                                && dateAfter(teamEnd, allocStart))
                        {
                            return ValidationStatus.START_OVERLAP;
                        }
                        else if (dateAfter(teamEnd, allocEnd)
                                && dateBefore(teamStart, allocEnd))
                        {
                            return ValidationStatus.END_OVERLAP;
                        }
                    }
                }
            }
        }
        return ValidationStatus.VALID;
    }

    /**
     * Determines whether an allocation is valid and what course of action to take
     * @param alloc The new allocation
     * @param proj The project within which the new allocation is made
     * @return Whether the allocation is a valid one based on its start and end dates
     */
    public static boolean validateAllocation(Allocation alloc, Project proj)
    {
        switch (isValidAllocationDate(alloc, proj))
        {
            case VALID:
                return true;
            case ALLOCATION_DATES_WRONG_ORDER:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("The end date of your new allocation cannot be before the start"
                                + " date!")
                        .showError();
                return false;
            case ALLOCATION_DATES_EQUAL:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("An allocation for that team with those start and end dates"
                                + " already exists!")
                        .showError();
                return false;
            case START_OVERLAP:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("The start date of your new allocation overlaps with an already"
                                + " existing allocation for that team.")
                        .showError();
                return false;
            case END_OVERLAP:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("The end date of your new allocation overlaps with an already"
                                + " existing allocation for that team.")
                        .showError();
                return false;
            case SUPER_OVERLAP:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("The start and end dates of your new allocation encompass an"
                                + " existing allocation for that team.")
                        .showError();
                return false;
            case SUB_OVERLAP:
                Dialogs.create()
                        .title("Allocation Date Error")
                        .message("The start and end dates of your new allocation are encompassed"
                                + " by an existing allocation for that team.")
                        .showError();
                return false;
            default:
                return true;
        }
    }

    /**
     * Checks if date1 and date2 are equal
     * @param date1 the first date
     * @param date2 the second date
     * @return if date1 and date2 are equal
     */
    public static boolean datesEqual(LocalDate date1, LocalDate date2)
    {
        if (date1 == null && date2 == null)
        {
            return true;
        }
        else if (date1 == null)
        {
            return false;
        }
        else if (date2 == null)
        {
            return false;
        }
        else
        {
            return date1.equals(date2);
            
        }
    }

    /**
     * Checks if date1 comes before date2 in the case of 'infinite' nulls
     * @param date1 the date to check
     * @param date2 the date to compare to
     * @return if date1 comes before date2
     */
    public static boolean dateBefore(LocalDate date1, LocalDate date2)
    {
        if (date1 == null)
        {
            return false;
        }
        else if (date2 == null)
        {
            return true;
        }
        else
        {
            return date1.isBefore(date2);
        }
    }

    /**
     * Checks if date1 comes after date2 in the case of 'infinite' nulls
     * @param date1 the date to check
     * @param date2 the date to compare to
     * @return if date1 comes after date2
     */
    public static boolean dateAfter(LocalDate date1, LocalDate date2)
    {
        if (date1 == null)
        {
            return true;
        }
        else if (date2 == null)
        {
            return false;
        }
        else
        {
            return date1.isAfter(date2);
        }
    }


    /**
     * Determines if a LocalDate is in the future.
     * @param date The date to check against the current time
     * @return A boolean showing if the date is in the future (true) or not (false).
     */
    public static boolean isFutureDate(LocalDate date)
    {
        if (date.isAfter(LocalDate.now()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks that the text in the given CustomDateField is in the "dd/mm/yyyy" format
     * Displays appropriate errors if wrong format
     * @param dateField The CustomDateField to check
     * @return true if correct format
     */
    public static boolean isCorrectDateFormat(CustomDateField dateField)
    {
        String dateString = dateField.getText();
        if (dateString.isEmpty())
        {
            return true;
        }
        else
        {
            //Global.datePattern.setLenient(false);
            try
            {
                String[] date = dateString.split("/"); //returns an array with the day, month and yr
                String year = date[date.length - 1];
                if (year.length() != 4)
                {
                    dateField.showErrorField("* Format must be dd/MM/yyyy e.g 12/03/1990");
                    return false;
                }
                LocalDate parsedLocalDate = LocalDate.parse(dateString, Global.dateFormatter);

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
     * @return true if correct format// TODO Make after allocation
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
    public static LocalDate stringToDate(String releaseDateString)
    {
        LocalDate releaseDate = null;
        try
        {
            releaseDate = LocalDate.parse(releaseDateString, Global.dateFormatter);
        }
        catch (DateTimeParseException e)
        {
            System.out.println("Error parsing date");
        }
        return releaseDate;
    }
}
