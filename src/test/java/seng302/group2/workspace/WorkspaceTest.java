/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

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
        ObservableList<TreeViewItem> people = observableArrayList();
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
}