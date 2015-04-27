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
 * @author Jordane Lew (jml168)
 * @author Bronson McNaughton (btm38)
 */
public class WorkspaceTest
{

    /**
     * A simple test for the Workspace constructors and getters.
     */
    @Test
    public void testWorkspaceConstructors()
    {
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
    public void testWorkspaceSetters()
    {
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
    public void testAddPerson()
    {
        Workspace work = new Workspace();
        Person pers = new Person();
        work.add(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        people.add(pers);

        Assert.assertEquals(people, work.getPeople());
        
        work.add(pers);
        work.add(pers);
        Assert.assertEquals(3, work.getPeople().size());
    }
    
    /**
     * Tests that people are correctly removed from the workspace through the remove() method.
     */
    @Test
    public void testRemovePerson()
    {
        Workspace work = new Workspace();
        Person pers = new Person();
        work.add(pers);
        work.remove(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        
        Assert.assertEquals(people, work.getPeople());
        
        work.add(pers);
        work.add(pers);
        work.remove(pers);
        work.remove(pers);
        
        Assert.assertEquals(0, work.getPeople().size());
    }
    
    /**
     * Tests that projects are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddProject()
    {
        Workspace work = new Workspace();
        Project proj = new Project();
        work.add(proj);
        
        ObservableList<TreeViewItem> projects = observableArrayList();
        projects.add(proj);

        Assert.assertEquals(projects, work.getProjects());
        
        work.add(proj);
        work.add(proj);
        Assert.assertEquals(3, work.getProjects().size());
    }
    
    /**
     * Tests that projects are correctly removed from the workspace through the remove() method.
     */
    @Test
    public void testRemoveProject()
    {
        Workspace work = new Workspace();
        Project proj = new Project();
        work.add(proj);
        work.remove(proj);
        
        ObservableList<TreeViewItem> projects = observableArrayList();
        
        Assert.assertEquals(projects, work.getProjects());
        
        work.add(proj);
        work.add(proj);
        work.remove(proj);
        work.remove(proj);
        
        Assert.assertEquals(0, work.getProjects().size());
    }
    
    /**
     * Tests that skills are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddSkill()
    {
        Workspace work = new Workspace();
        Skill skill = new Skill();
        work.add(skill);
        
        ObservableList<TreeViewItem> skills = observableArrayList();
        skills.add(skill);

        Assert.assertEquals(skills, work.getSkills());
        
        work.add(skill);
        work.add(skill);
        Assert.assertEquals(3, work.getSkills().size());
    }
    
    /**
     * Tests that skills are correctly removed from the workspace through the remove() method.
     */
    @Test
    public void testRemoveSkill()
    {
        Workspace work = new Workspace();
        Skill skill = new Skill();
        work.add(skill);
        work.remove(skill);
        
        ObservableList<TreeViewItem> skills = observableArrayList();
        
        Assert.assertEquals(skills, work.getSkills());
        
        work.add(skill);
        work.add(skill);
        work.remove(skill);
        work.remove(skill);
        
        Assert.assertEquals(0, work.getSkills().size());
    }
    
    /**
     * Tests that teams are correctly added to the workspace through the add() method.
     */
    @Test
    public void testAddTeam()
    {
        Workspace work = new Workspace();
        Team team = new Team();
        
        work.add(team);
        
        work.add(team);
        work.add(team);
        Assert.assertEquals(4, work.getTeams().size());
    }
    
    /**
     * Tests that teams are correctly removed from the workspace through the remove() method.
     */
    @Test
    public void testRemoveTeam()
    {
        Workspace work = new Workspace();
        Global.currentWorkspace = work;
        Team team = new Team();
        
        work.add(team);
        work.remove(team);

        work.add(team);
        work.add(team);
        work.remove(team);
        work.remove(team);

        Assert.assertEquals(1, work.getTeams().size());

        work.remove(Global.getUnassignedTeam());
        Assert.assertTrue(work.getTeams().contains(Global.getUnassignedTeam()));
    }
}