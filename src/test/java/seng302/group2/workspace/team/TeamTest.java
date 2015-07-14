/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.workspace.Workspace;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * A series of tests relating to Teams
 *
 * @author Cameron Williams (crw73)
 */
public class TeamTest {

    /**
     * A simple test for the Team constructors
     */
    @Test
    public void testTeamConstructors() {
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
    public void testTeamSetters() {
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
        //Assert.assertEquals(RoleType.PRODUCT_OWNER, team.getProductOwner().getRole().getType());
        //Assert.assertEquals(RoleType.SCRUM_MASTER, team.getScrumMaster().getRole().getType());
    }

    /**
     * Tests the addition and removal of people in teams
     */
    @Test
    public void testAddPerson() {
        Team team = new Team();
        Person person = new Person();

        team.add(person, true);
        Assert.assertTrue(team.getPeople().contains(person));

        team.getPeople().remove(person);
        Assert.assertFalse(team.getPeople().contains(person));

        team.add(person, true);
        Assert.assertTrue(team.getPeople().contains(person));

        team.getPeople().remove(person);
        Assert.assertFalse(team.getPeople().contains(person));
    }

    /**
     * Test the addition and removal of team's project allocations
     */
    @Test
    public void testAddRemoveAllocation() {
        Team team = new Team();
        Project proj = new Project();
        LocalDate startDate = LocalDate.of(2014, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2014, Month.JANUARY, 10);
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
    public void testIsUnassignedTeam() {
        Team unass = Team.createUnassignedTeam();
        Team ateam = new Team();

        Assert.assertTrue(unass.isUnassignedTeam());
        Assert.assertFalse(ateam.isUnassignedTeam());
    }


    /**
     * Tests that a team properly prepares for serialization
     */
    @Test
    public void testPrepSerialization() {
        Team testTeam = new Team();
        Person testPerson = new Person();

        testTeam.getSerializablePeople().clear();
        Assert.assertTrue(testTeam.getSerializablePeople().isEmpty());

        testTeam.add(testPerson, true);
        testTeam.prepSerialization();

        Assert.assertTrue(testTeam.getSerializablePeople().contains(testPerson));
    }

    /**
     * Tests that a team properly post-pares after deserialization
     */
    @Test
    public void testPostSerialization() {
        Team testTeam = new Team();
        Person testPerson = new Person();

        testTeam.getPeople().clear();
        Assert.assertTrue(testTeam.getPeople().isEmpty());

        testTeam.getSerializablePeople().add(testPerson);
        testTeam.postSerialization();

        Assert.assertTrue(testTeam.getPeople().contains(testPerson));
    }

    /**
     * Tests the compareTo method of Team to ensure it correctly returns an int representing if a
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo() {
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setShortName("A");
        team2.setShortName("Z");

        Assert.assertTrue(team1.compareTo(team2) <= 0);
        Assert.assertTrue(team2.compareTo(team1) >= 0);
        Assert.assertTrue(team1.compareTo(team1) == 0);
    }


    @Test
    public void testBasicEdit() {
        Team team = new Team("Arctic Falcon", "An awesome team name");

        team.edit("Antarctic Eagle", "An even awesomer team name");
        Assert.assertEquals("Antarctic Eagle", team.getShortName());
        Assert.assertEquals("An even awesomer team name", team.getDescription());

        Global.commandManager.undo();

        Assert.assertEquals("Arctic Falcon", team.getShortName());
        Assert.assertEquals("An awesome team name", team.getDescription());
    }


    @Test
    public void testDelete() {
        Global.currentWorkspace = new Workspace();
        Team team = new Team();
        Person p1 = new Person();
        team.getPeople().add(p1);
        Global.currentWorkspace.add(p1);
        Global.currentWorkspace.add(team);
        Assert.assertTrue(Global.currentWorkspace.getTeams().contains(team));
        team.deleteTeam();
        Assert.assertFalse(Global.currentWorkspace.getTeams().contains(team));
        Assert.assertEquals(null, p1.getTeam());
        Global.commandManager.undo();
        Assert.assertTrue(Global.currentWorkspace.getTeams().contains(team));
    }


    @Test
    public void testDeleteCascading() {
        Team team = new Team();
        Person p1 = new Person();
        Person p2 = new Person();
        team.getPeople().addAll(p1, p2);

        Global.currentWorkspace.add(team);
        Global.currentWorkspace.add(p1);
        Global.currentWorkspace.add(p2);

        Assert.assertTrue(Global.currentWorkspace.getTeams().contains(team));
        Assert.assertTrue(Global.currentWorkspace.getPeople().contains(p1));
        Assert.assertTrue(Global.currentWorkspace.getPeople().contains(p2));

        team.deleteTeamCascading();

        Assert.assertFalse(Global.currentWorkspace.getTeams().contains(team));
        Assert.assertFalse(Global.currentWorkspace.getPeople().contains(p1));
        Assert.assertFalse(Global.currentWorkspace.getPeople().contains(p2));

        Global.commandManager.undo();

        Assert.assertTrue(Global.currentWorkspace.getTeams().contains(team));
        Assert.assertTrue(Global.currentWorkspace.getPeople().contains(p1));
        Assert.assertTrue(Global.currentWorkspace.getPeople().contains(p2));
    }


    @Test
    public void testExtendedEdit() {
        Team team = new Team("Arctic Falcon", "An awesome team name");
        Person bronson = new Person();
        Person moffat = new Person();
        Person andrew = new Person();
        Person shinobu = new Person();
        ArrayList<Person> members = new ArrayList<>();
        members.add(bronson);
        members.add(shinobu);
        members.add(moffat);
        members.add(andrew);
        ArrayList<Person> devs = new ArrayList<>();
        devs.add(bronson);

        team.edit("Antarctic Eagle", "An even awesomer team name", members,
                moffat, andrew, devs);

        Assert.assertEquals("Antarctic Eagle", team.getShortName());
        Assert.assertEquals("An even awesomer team name", team.getDescription());
        Assert.assertTrue(team.getPeople().containsAll(members));
        Assert.assertTrue(team.getDevs().contains(bronson));
        Assert.assertEquals(andrew, team.getScrumMaster());
        Assert.assertEquals(moffat, team.getProductOwner());

        Global.commandManager.undo();

        Assert.assertEquals("Arctic Falcon", team.getShortName());
        Assert.assertEquals("An awesome team name", team.getDescription());
        Assert.assertFalse(team.getPeople().containsAll(members));
        Assert.assertFalse(team.getDevs().contains(bronson));
        Assert.assertNull(team.getScrumMaster());
        Assert.assertNull(team.getProductOwner());

    }
}
