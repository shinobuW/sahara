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
import seng302.group2.workspace.team.Team;

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
     * @param project the project to allocate team to
     * @param team the team to allocate
     * @param startDate to validate
     * @param endDate to validate
     * @param allocation allocation to be edited if method used for validating allocation
     * @returns validation status
     */
    public static ValidationStatus validateAllocation(Project project, Team team,
                                                      LocalDate startDate, LocalDate endDate,
                                                      Allocation allocation)
    {
        if (allocation != null)
        {
            project = allocation.getProject();
            team = allocation.getTeam();
        }

        if (endDate != null)
        {
            if (dateAfter(startDate, endDate))
            {
                return ValidationStatus.ALLOCATION_DATES_WRONG_ORDER;
            }
        }
        //Go through project's teams
        for (Allocation alloc : project.getTeamAllocations())
        {
            if (alloc != allocation && alloc.getTeam() == team) //&& alloc.getProject() == project
            {
                ValidationStatus currentStatus = validateAllocationDate(alloc.getStartDate(),
                        alloc.getEndDate(),
                        startDate, endDate);
                if (currentStatus != ValidationStatus.VALID)
                {
                    return currentStatus;
                }
            }
        }

        Allocation currentAlloc = team.getCurrentAllocation();
        ValidationStatus projectComparison = ValidationStatus.VALID;
        if (currentAlloc != null && currentAlloc != allocation)
        {
            //Check that the team's currently allocated project doesn't overlap with
            // the new allocation
            LocalDate currentAllocStartDate = currentAlloc.getStartDate();
            LocalDate currentAllocEndDate = currentAlloc.getEndDate();
            projectComparison = validateAllocationDate(currentAllocStartDate, currentAllocEndDate,
                    startDate, endDate);
        }

        return projectComparison;
    }

    /**
     * @returns clashAllocation The first allocation instance it clashes with
     */
    public static ValidationStatus validateAllocation(Project project, Team team,
                                                      LocalDate startDate, LocalDate endDate)
    {
        return validateAllocation(project, team, startDate, endDate, null);
    }

//    /**
//    * Determines the Validation status of a new allocation, checking if there are any illegal
//    * overlaps with the start or end dates of the team's allocations.
//    * @param alloc The new allocation to check
//    * @param proj The project within which to check the allocations
//    * @return The validation status of the new allocation
//    */
//    public static ValidationStatus validAllocation(Allocation alloc, Project proj)
//    {
//        LocalDate allocStart = alloc.getStartDate();
//        LocalDate allocEnd = alloc.getEndDate();
//
//        if (allocEnd != null)
//        {
//            if (dateAfter(allocStart, allocEnd))
//            {
//                return ValidationStatus.ALLOCATION_DATES_WRONG_ORDER;
//            }
//        }
//
//        for (Allocation projAlloc : proj.getTeamAllocations())
//        {
//            if (projAlloc != alloc)
//            {
//                if (projAlloc.getTeam() == alloc.getTeam())
//                {
//                    // Check for date overlaps within a single Teams total allocations
//                    for (Allocation teamAlloc : projAlloc.getTeam().getProjectAllocations())
//                    {
//                        LocalDate teamStart = teamAlloc.getStartDate();
//                        LocalDate teamEnd = teamAlloc.getEndDate();
//                        //Validate with other teams
//                        ValidationStatus teamComparison = validateAllocationDate(teamStart,
// teamEnd,
//                                allocStart, allocEnd);
//                        if (teamComparison != ValidationStatus.VALID)
//                        {
//                            return teamComparison;
//                        }
//                    }
//                }
//            }
//        }
//
//        Allocation currentAlloc = alloc.getTeam().getCurrentAllocation();
//        ValidationStatus projectComparison = ValidationStatus.VALID;
//        if (currentAlloc != null)
//        {
//            //Check that the team's currently allocated project doesn't overlap with
//            // the new allocation
//            LocalDate currentAllocStartDate = currentAlloc.getStartDate();
//            LocalDate currentAllocEndDate = currentAlloc.getEndDate();
//            projectComparison = validateAllocationDate(currentAllocStartDate, currentAllocEndDate,
//                    allocStart, allocEnd);
//        }
//        return projectComparison;
//    }


    /**
     * Determines the Validation status of a new allocation, checking if there are any illegal
     * overlaps with the start or end dates of other allocations.
     * @param startDate1 the start date to check
     * @param endDate1 the end date to check
     * @param startDate2 the start date to compare to
     * @param endDate2 the end date to compare to
     * @return
     */
    public static ValidationStatus validateAllocationDate(LocalDate startDate1, LocalDate endDate1,
                                                          LocalDate startDate2, LocalDate endDate2)
    {
        if (datesEqual(startDate1, startDate2)
                && datesEqual(endDate1, endDate2))
        {
            return ValidationStatus.ALLOCATION_DATES_EQUAL;
        }
        else if (dateAfter(startDate1, startDate2)
                && dateBefore(endDate1, endDate2))
        {
            return ValidationStatus.SUPER_OVERLAP; // teamAlloc is between startDate and endDate
        }
        else if (dateAfter(startDate1, startDate2)
                && datesEqual(endDate1, endDate2))
        {
            return ValidationStatus.SUPER_OVERLAP; //when bigger
        }
        else if (datesEqual(startDate1, startDate2)
                && dateBefore(endDate1, endDate2))
        {
            return ValidationStatus.SUPER_OVERLAP;
        }
        else if (dateBefore(startDate1, startDate2)
                && dateAfter(endDate1, endDate2))
        {
            return ValidationStatus.SUB_OVERLAP; // new Allocation between start date and endDate
        }
        else if (dateBefore(startDate1, startDate2)
                && datesEqual(endDate1, endDate2))
        {
            return ValidationStatus.SUB_OVERLAP;
        }
        else if (datesEqual(startDate1, startDate2)
                && dateAfter(endDate1, endDate2))
        {
            return ValidationStatus.SUB_OVERLAP;
        }
        else if (dateBefore(startDate1, startDate2)
                && dateAfter(endDate1, startDate2))
        {
            return ValidationStatus.START_OVERLAP;
        }
        else if (dateAfter(endDate1, startDate2)
                && dateBefore(startDate1, endDate2))
        {
            return ValidationStatus.END_OVERLAP;
        }
        return ValidationStatus.VALID;
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
                    dateField.showErrorField("* Format must be dd/MM/yyyy");
                    return false;
                }
                LocalDate parsedLocalDate = LocalDate.parse(dateString, Global.dateFormatter);

                dateField.hideErrorField();
                return true;

            }
            catch (Exception ex)
            {
                dateField.showErrorField("* Format must be dd/MM/yyyy");
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
                customBirthDate.showErrorField("* Format must be dd/MM/yyyy");
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
