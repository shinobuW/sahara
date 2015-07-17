/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.workspace;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A series of tests relating to Workspaces
 *
 * @author Jordane Lew (jml168)
 * @author Bronson McNaughton (btm38)
 */
public class WorkspaceTest {

    /**
     * A simple test for the Workspace constructors and getters.
     */
    @Test
    public void testWorkspaceConstructors() {
        Workspace work = new Workspace();
        ObservableList<SaharaItem> people = observableArrayList();
        Assert.assertEquals("Untitled Workspace", work.getShortName());
        Assert.assertEquals("Untitled Workspace", work.getLongName());
        Assert.assertEquals("A blank workspace.", work.getDescription());
        Assert.assertEquals("Untitled Workspace", work.toString());
        Assert.assertEquals(people, work.getPeople());

        Workspace work2 = new Workspace("aShortName", "aLongName", "aDescription");
        Assert.assertEquals("aShortName", work2.getShortName());
        Assert.assertEquals("aLongName", work2.getLongName());
        Assert.assertEquals("aDescription", work2.getDescription());
        Assert.assertEquals("aShortName", work2.toString());
    }


    /**
     * Tests Projects' setter methods.
     */
    @Test
    public void testWorkspaceSetters() {
        Workspace work = new Workspace();
        work.setShortName("aShortName");
        work.setLongName("aLongName");
        work.setDescription("aDescription");

        Assert.assertEquals("aShortName", work.getShortName());
        Assert.assertEquals("aLongName", work.getLongName());
        Assert.assertEquals("aDescription", work.getDescription());
        Assert.assertEquals("aShortName", work.toString());
    }


    /**
     * Tests that people are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddPerson() {
        Workspace work = new Workspace();
        Global.currentWorkspace = work;
        Person pers = new Person();
        work.add(pers);

        Assert.assertTrue(work.getPeople().contains(pers));

        work.add(pers);

        Assert.assertEquals(2, work.getPeople().size());

        Global.commandManager.undo();
        Assert.assertEquals(1, work.getPeople().size());

        Global.commandManager.undo();
        Assert.assertEquals(0, work.getPeople().size());
    }


    /**
     * Tests that projects are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddProject() {
        Workspace work = new Workspace();
        Global.currentWorkspace = work;
        Project proj = new Project();
        work.add(proj);

        Assert.assertTrue(work.getProjects().contains(proj));

        work.add(proj);
        Assert.assertEquals(2, work.getProjects().size());
        Global.commandManager.undo();
        Assert.assertEquals(1, work.getProjects().size());
        Global.commandManager.undo();
        Assert.assertEquals(0, work.getProjects().size());
    }


    /**
     * Tests that skills are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddSkill() {
        Workspace work = new Workspace();
        Global.currentWorkspace = work;
        Skill skill = new Skill();
        work.add(skill);

        //System.out.println(work.getSkills());
        Assert.assertTrue(work.getSkills().contains(skill));

        work.add(skill);

        // Also account for the two default skills in workspaces, SM, PO
        Assert.assertEquals(4, work.getSkills().size());
        Global.commandManager.undo();
        Assert.assertEquals(3, work.getSkills().size());
        Global.commandManager.undo();
        Assert.assertEquals(2, work.getSkills().size());
        Global.commandManager.undo();  // Can't remove the default skills
        Assert.assertEquals(2, work.getSkills().size());
    }


    /**
     * Tests that teams are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddTeam() {
        Workspace work = new Workspace();
        Global.currentWorkspace = work;
        Team team = new Team();

        work.add(team);
        Assert.assertTrue(work.getTeams().contains(team));

        work.add(team);
        Assert.assertEquals(3, work.getTeams().size());  // Including the unassigned team

        Global.commandManager.undo();
        Assert.assertEquals(2, work.getTeams().size());
        Global.commandManager.undo();
        Assert.assertEquals(1, work.getTeams().size());
        Global.commandManager.undo();
        Assert.assertEquals(1, work.getTeams().size());  // Can't remove the unassigned team
    }

    @Test
    public void testGenerateXml() {
        Workspace ws = new Workspace();
        Person assignedPerson = new Person("Assigned Person", "first name", "last name", "email", "description", LocalDate.now());
        Person unassginedPerson = new Person("Unassigned Person", "first name", "last name", "email", "description", LocalDate.now());
        Skill assignedSkill = new Skill("Assigned Skill", "description");
        Skill unassignedSkill = new Skill("unassigned Skill", "description");
        Project proj = new Project();
        Team assignedTeam = new Team("assigned team", "description");
        Team unassignedTeam = Team.createUnassignedTeam();
        Allocation alloc = new Allocation(proj, unassignedTeam, LocalDate.now(), LocalDate.now());
        proj.add(alloc);

        assignedTeam.add(assignedPerson);

        ws.add(proj);
        ws.add(unassignedTeam);
        ws.add(assignedTeam);
        ws.add(assignedPerson);
        ws.add(unassginedPerson);
        ws.add(assignedSkill);
        ws.add(unassignedSkill);

//        Element workspaceElement = ws.generateXML();
//        Assert.assertEquals("[#text: Test]", projectElement.getChildNodes().item(1).getChildNodes().item(0).toString());
//        Assert.assertEquals("[#text: Test project]", projectElement.getChildNodes().item(2).getChildNodes().item(0).toString());
//        Assert.assertEquals("[#text: Used for testing]", projectElement.getChildNodes().item(3).getChildNodes().item(0).toString());
    }
}