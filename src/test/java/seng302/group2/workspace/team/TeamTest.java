/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

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
        assertEquals("unnamed", team.getShortName());
        assertEquals("", team.getDescription());
        assertEquals("unnamed", team.toString());
        assertEquals(null, team.getChildren());
        assertEquals(null, team.getScrumMaster());
        assertEquals(null, team.getProductOwner());
        
        Team team2 = new Team("Arctic Falcon", "An awesome team name"); 
        assertEquals("Arctic Falcon", team2.getShortName());
        assertEquals("An awesome team name", team2.getDescription());
        assertEquals("Arctic Falcon", team2.toString());
        
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
        
        assertEquals("Arctic Falcon", team.getShortName());
        assertEquals("An awesome team name", team.getDescription());
        assertEquals("Arctic Falcon", team.toString());
        
        assertEquals("Chardonnay", team.getProductOwner().getShortName());
        assertEquals("unnamed", team.getScrumMaster().getShortName());
        assertEquals(RoleType.ProductOwner, team.getProductOwner().getRole().getType());
        assertEquals(RoleType.ScrumMaster, team.getScrumMaster().getRole().getType());
    }
}
