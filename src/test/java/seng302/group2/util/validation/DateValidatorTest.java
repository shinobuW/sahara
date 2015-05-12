/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

/**
 * Test class for the DateValidator
 * @author Jordane
 */
public class DateValidatorTest
{
    /**
     * Test of isValidBirthdate method, of class DateValidator.
     */
    @Test
    public void testIsValidBirthdate()
    {
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
    public void testIsValidAllocationDate()
    {
        Project proj = new Project();
        Team team = new Team();
        Allocation projAlloc = new Allocation(proj, team,
                LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2016, Month.JANUARY, 1));
        proj.add(projAlloc);
        team.add(projAlloc);

        Allocation allocDatesWrongOrder = new Allocation(proj, team,
                LocalDate.of(2016, Month.JANUARY, 1), LocalDate.of(2015, Month.JANUARY, 1));
        Assert.assertEquals(ValidationStatus.ALLOCATION_DATES_WRONG_ORDER,
                DateValidator.isValidAllocationDate(allocDatesWrongOrder, proj));

        Allocation allocStartOverlap = new Allocation(proj, team,
                LocalDate.of(2015, Month.JUNE, 1), LocalDate.of(2016, Month.JUNE, 1));
        Assert.assertEquals(ValidationStatus.START_OVERLAP,
                DateValidator.isValidAllocationDate(allocStartOverlap, proj));

        Allocation allocEndOverlap = new Allocation(proj, team,
                LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2015, Month.JUNE, 1));
        Assert.assertEquals(ValidationStatus.END_OVERLAP,
                DateValidator.isValidAllocationDate(allocEndOverlap, proj));

        Allocation allocSuperOverlap = new Allocation(proj, team,
                LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2016, Month.JUNE, 1));
        Assert.assertEquals(ValidationStatus.SUPER_OVERLAP,
                DateValidator.isValidAllocationDate(allocSuperOverlap, proj));

        Allocation allocSubOverlap = new Allocation(proj, team,
                LocalDate.of(2015, Month.JANUARY, 2), LocalDate.of(2015, Month.DECEMBER, 12));
        Assert.assertEquals(ValidationStatus.SUB_OVERLAP,
                DateValidator.isValidAllocationDate(allocSubOverlap, proj));

        Allocation allocValid = new Allocation(proj, team,
                LocalDate.of(2016, Month.JANUARY, 2),LocalDate.of(2017, Month.JANUARY, 1));
        Assert.assertEquals(ValidationStatus.VALID,
                DateValidator.isValidAllocationDate(allocValid, proj));

    }

    /**
     * Test of dateBefore method, of class DateValidator
     */
    @Test
    public void testDateBefore()
    {
        Date date1 = new Date("01/01/2015");
        Date date2 = new Date("01/01/2016");

        Assert.assertEquals(true, DateValidator.dateBefore(date1, date2));
        Assert.assertEquals(false, DateValidator.dateBefore(date2, date1));
        Assert.assertEquals(false, DateValidator.dateBefore(null, date2));
        Assert.assertEquals(true, DateValidator.dateBefore(date1, null));
    }

    /**
     * Test of dateAfter method, of class DateValidator
     */
    @Test
    public void testDateAfter()
    {
        Date date1 = new Date("01/01/2015");
        Date date2 = new Date("01/01/2016");

        Assert.assertEquals(false, DateValidator.dateAfter(date1, date2));
        Assert.assertEquals(true, DateValidator.dateAfter(date2, date1));
        Assert.assertEquals(true, DateValidator.dateAfter(null, date2));
        Assert.assertEquals(false, DateValidator.dateAfter(date1, null));
    }

    /**
     * Tests the future date validation
     */
    @Test
    public void testIsFutureDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Assert.assertTrue(DateValidator.isFutureDate(futureDate));
    }

    /**
     * Tests that the string to date conversion is equivalent
     */
    @Test
    public void testStringToDate()
    {
        Date testDate = DateValidator.stringToDate("12/12/2015");
        Assert.assertEquals(new Date("12/12/2015"), testDate);   
    }
}
