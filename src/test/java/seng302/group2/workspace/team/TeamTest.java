/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;

import java.util.ArrayList;
import java.util.Date;

/**
 * A series of tests relating to Teams
 * @author Cameron Williams (crw73)
 */
public class TeamTest
{

    /**
     * A simple test for the Team constructors
     */
    @Test
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
    @Test
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
        //Setting Team and People Roles should be seperate
        //Assert.assertEquals(RoleType.ProductOwner, team.getProductOwner().getRole().getType());
        //Assert.assertEquals(RoleType.ScrumMaster, team.getScrumMaster().getRole().getType());
    }

    /**
     * Tests the addition and removal of people in teams
     */
    @Test
    public void testAddPerson()
    {
        Team team = new Team();
        Person person = new Person();

        team.add(person);
        Assert.assertTrue(team.getPeople().contains(person));

        team.remove(person);
        Assert.assertFalse(team.getPeople().contains(person));

        team.add(person, true);
        Assert.assertTrue(team.getPeople().contains(person));

        team.remove(person, true);
        Assert.assertFalse(team.getPeople().contains(person));
    }

    /**
     * Test the addition and removal of team's project allocations
     */
    @Test
    public void testAddRemoveAllocation()
    {
        Team team = new Team();
        Project proj = new Project();
        Date startDate = new Date();
        Date endDate = new Date();
        Allocation alloc = new Allocation(proj, team, startDate, endDate);
        
        team.add(alloc);
        Assert.assertTrue(team.getProjectAllocations().contains(alloc));

        team.remove(alloc);
        Assert.assertTrue(team.getProjectAllocations().isEmpty());
    }


    /**
     * Tests whether or not the unassigned team getter works correctly
     */
    @Test
    public void testIsUnassignedTeam()
    {
        Team unass = Team.createUnassignedTeam();
        Team ateam = new Team();

        Assert.assertTrue(unass.isUnassignedTeam());
        Assert.assertFalse(ateam.isUnassignedTeam());
    }


    /**
     * Tests that a team properly prepares for serialization
     */
    @Test
    public void testPrepSerialization()
    {
        Team testTeam = new Team();
        Person testPerson = new Person();

        testTeam.getSerializablePeople().clear();
        Assert.assertTrue(testTeam.getSerializablePeople().isEmpty());

        testTeam.add(testPerson);
        testTeam.prepSerialization();

        Assert.assertTrue(testTeam.getSerializablePeople().contains(testPerson));
    }

    /**
     * Tests that a team properly post-pares after deserialization
     */
    @Test
    public void testPostSerialization()
    {
        Team testTeam = new Team();
        Person testPerson = new Person();

        testTeam.getPeople().clear();
        Assert.assertTrue(testTeam.getPeople().isEmpty());

        testTeam.getSerializablePeople().add(testPerson);
        testTeam.postSerialization();

        Assert.assertTrue(testTeam.getPeople().contains(testPerson));
    }
}
