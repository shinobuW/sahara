package seng302.group2.workspace.team;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.project.Project;

import java.time.LocalDate;
import java.time.Month;

public class AllocationTest
{

    /**
     *
     */

        //Set test Allocations
        LocalDate startDate = LocalDate.of(2015, Month.APRIL, 12);
        LocalDate endDate = LocalDate.of(2100, Month.MAY, 30);
        Project proj = new Project();
        Team team = new Team();

    @Test
    public void testConstructors()
    {
        Allocation alloc = new Allocation(proj, team, startDate, endDate);
        Assert.assertEquals("Untitled Project", alloc.getProject().getShortName());
        Assert.assertEquals("unnamed", alloc.getTeam().getShortName());
        Assert.assertEquals(startDate, alloc.getStartDate());
        Assert.assertEquals(endDate, alloc.getEndDate());
    }

    @Test
    public void testIsCurrentAllocation() throws Exception
    {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        Allocation alloc2 = new Allocation(proj, team, startDate, tomorrow);
        Assert.assertTrue(alloc2.isCurrentAllocation());
    }
}