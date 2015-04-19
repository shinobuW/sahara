/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.RoleType;

/**
 * A series of tests relating to Teams
 * @author Cameron Williams (crw73)
 */
public class TeamTest extends TestCase
{
    /**
     * Create the test case.
     * @param testName name of the test case
     */
    public TeamTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(TeamTest.class);
    }

    /**
     * A simple test for the Team constructors
     */
    public void testTeamConstructors()
    {
        Team team = new Team();
        Assert.assertEquals("unnamed", team.getShortName());
        Assert.assertEquals("", team.getDescription());
        Assert.assertEquals("unnamed", team.toString());
        Assert.assertEquals(null, team.getChildren());
        Assert.assertEquals(null, team.getScrumMaster());
        Assert.assertEquals(null, team.getProductOwner());
        
        Team team2 = new Team("Arctic Falcon", "An awesome team name"); 
        Assert.assertEquals("Arctic Falcon", team2.getShortName());
        Assert.assertEquals("An awesome team name", team2.getDescription());
        Assert.assertEquals("Arctic Falcon", team2.toString());
        
    }
    
    /**
     * Tests for Teams' setter methods.
     */
    public void testTeamSetters()
    {
        Team team = new Team();
        Person po = new Person();
        po.setShortName("Chardonnay");
        team.setShortName("Arctic Falcon");
        team.setDescription("An awesome team name");
        team.setScrumMaster(new Person());
        team.setProductOwner(po);
        
        Assert.assertEquals("Arctic Falcon", team.getShortName());
        Assert.assertEquals("An awesome team name", team.getDescription());
        Assert.assertEquals("Arctic Falcon", team.toString());
        
        Assert.assertEquals("Chardonnay", team.getProductOwner().getShortName());
        Assert.assertEquals("unnamed", team.getScrumMaster().getShortName());
        Assert.assertEquals(RoleType.ProductOwner, team.getProductOwner().getRole().getType());
        Assert.assertEquals(RoleType.ScrumMaster, team.getScrumMaster().getRole().getType());
    }
}
