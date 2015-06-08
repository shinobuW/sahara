/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

/**
 * Test class for the DateValidator
 *
 * @author Jordane
 */
public class DateValidatorTest {
    /**
     * Test of isValidBirthdate method, of class DateValidator.
     */
    @Test
    public void testIsValidBirthdate() {
        Assert.assertEquals(ValidationStatus.NULL, DateValidator.isValidBirthdate(""));
        Assert.assertEquals(ValidationStatus.OUT_OF_RANGE,
                DateValidator.isValidBirthdate("12/12/9999"));
        Assert.assertEquals(ValidationStatus.VALID, DateValidator.isValidBirthdate("20/03/2015"));
        Assert.assertEquals(ValidationStatus.PATTERN_MISMATCH,
                DateValidator.isValidBirthdate("20/03/15"));
    }

    /**
     * Test of isValidAllocationDate method, of class DateValidator
     */
    @Test
    public void testIsValidAllocationDate() {
        Project proj = new Project();
        Team team = new Team();
        Allocation projAlloc = new Allocation(proj, team,
                LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2016, Month.JANUARY, 1));
        proj.add(projAlloc);
        team.add(projAlloc);

        Assert.assertEquals(ValidationStatus.ALLOCATION_DATES_WRONG_ORDER,
                DateValidator.validateAllocation(proj, team, LocalDate.of(2016, Month.JANUARY, 1),
                        LocalDate.of(2015, Month.JANUARY, 1)));

        Assert.assertEquals(ValidationStatus.START_OVERLAP,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2015, Month.JUNE, 1), LocalDate.of(2016, Month.JUNE, 1)));

        Assert.assertEquals(ValidationStatus.END_OVERLAP,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2015, Month.JUNE, 1)));

        Assert.assertEquals(ValidationStatus.SUPER_OVERLAP,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2016, Month.JUNE, 1)));

        Assert.assertEquals(ValidationStatus.SUB_OVERLAP,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2015, Month.JANUARY, 2), LocalDate.of(2015, Month.DECEMBER, 12)));

        Assert.assertEquals(ValidationStatus.VALID,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2016, Month.JANUARY, 2), LocalDate.of(2017, Month.JANUARY, 1)));
    }

    /**
     * test editing of start date and end date of allocation
     */
    public void testEdits() {
        Project proj = new Project();
        Team team = new Team();
        Allocation testAlloc = new Allocation(proj, team, LocalDate.of(2015, Month.JANUARY, 1),
                LocalDate.of(2016, Month.JANUARY, 1));

        Project proj2 = new Project();

        Assert.assertEquals(ValidationStatus.VALID,
                DateValidator.validateAllocation(proj, team, LocalDate.of(2015, Month.JANUARY, 2),
                        LocalDate.of(2015, Month.DECEMBER, 30), testAlloc));

        Assert.assertEquals(ValidationStatus.ALLOCATION_DATES_WRONG_ORDER,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2015, Month.DECEMBER, 30),
                        LocalDate.of(2015, Month.JANUARY, 2), testAlloc));

        Assert.assertEquals(ValidationStatus.START_OVERLAP,
                DateValidator.validateAllocation(proj, team,
                        LocalDate.of(2015, Month.JUNE, 1),
                        LocalDate.of(2016, Month.JUNE, 1), testAlloc));

        //Allocation with the same date exist but for a different date
        Assert.assertEquals(ValidationStatus.ALLOCATION_DATES_EQUAL,
                DateValidator.validateAllocation(proj2, team,
                        LocalDate.of(2015, Month.JANUARY, 1),
                        LocalDate.of(2016, Month.DECEMBER, 31), testAlloc));

        Assert.assertEquals(ValidationStatus.END_OVERLAP,
                DateValidator.validateAllocation(proj2, team,
                        LocalDate.of(2015, Month.JANUARY, 1),
                        LocalDate.of(2016, Month.DECEMBER, 31), testAlloc));

        Assert.assertEquals(ValidationStatus.SUPER_OVERLAP,
                DateValidator.validateAllocation(proj2, team,
                        LocalDate.of(2014, Month.JUNE, 1),
                        LocalDate.of(2016, Month.JUNE, 1), testAlloc));

        Assert.assertEquals(ValidationStatus.SUB_OVERLAP,
                DateValidator.validateAllocation(proj2, team,
                        LocalDate.of(2015, Month.JANUARY, 2),
                        LocalDate.of(2015, Month.DECEMBER, 12), testAlloc));
    }

    /**
     * Test of dateBefore method, of class DateValidator
     */

    public void testDateBefore() {
        LocalDate date1 = LocalDate.parse("01/01/2015", Global.dateFormatter);
        LocalDate date2 = LocalDate.parse("01/01/2016", Global.dateFormatter);

        Assert.assertEquals(true, DateValidator.dateBefore(date1, date2));
        Assert.assertEquals(false, DateValidator.dateBefore(date2, date1));
        Assert.assertEquals(false, DateValidator.dateBefore(null, date2));
        Assert.assertEquals(true, DateValidator.dateBefore(date1, null));
    }

    /**
     * Test of dateAfter method, of class DateValidator
     */
    @Test
    public void testDateAfter() {
        LocalDate date1 = LocalDate.parse("01/01/2015", Global.dateFormatter);
        LocalDate date2 = LocalDate.parse("01/01/2016", Global.dateFormatter);

        Assert.assertEquals(false, DateValidator.dateAfter(date1, date2));
        Assert.assertEquals(true, DateValidator.dateAfter(date2, date1));
        Assert.assertEquals(true, DateValidator.dateAfter(null, date2));
        Assert.assertEquals(false, DateValidator.dateAfter(date1, null));
    }

    /**
     * Tests the future date validation
     */
    @Test
    public void testIsFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        LocalDate date1 = LocalDate.now();
        Assert.assertTrue(DateValidator.isFutureDate(date1.plusYears(12)));
    }

    /**
     * Tests that the string to date conversion is equivalent
     */
    @Test
    public void testStringToDate() {
        LocalDate testDate = DateValidator.stringToDate("12/12/2015");
        Assert.assertEquals(LocalDate.parse("12/12/2015", Global.dateFormatter), testDate);
    }
}
