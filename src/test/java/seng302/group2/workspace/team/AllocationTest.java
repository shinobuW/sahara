package seng302.group2.workspace.team;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.project.Project;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AllocationTest
{

    /**
     *
     */

        //Set test Allocations
        Date startDate = new Date();
        Date endDate = new Date();
        Project proj = new Project();
        Team team = new Team();

    @Before
    public void setUp()
    {
        //Set test dates
        try
        {
            startDate = Global.datePattern.parse("12/04/2015");
            endDate = Global.datePattern.parse("30/05/2100");
        }
        catch (Exception e)
        {
            Assert.fail("The date was not parsed correctly, please review");
        }
    }


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
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

        Allocation alloc2 = new Allocation(proj, team, startDate, tomorrow);
        Assert.assertTrue(alloc2.isCurrentAllocation());
    }
}