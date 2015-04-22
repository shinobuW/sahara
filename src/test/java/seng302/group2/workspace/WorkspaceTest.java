/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import javafx.collections.ObservableList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.person.Person;

import static javafx.collections.FXCollections.observableArrayList;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

/**
 * A series of tests relating to Workspaces
 * @author Jordane Lew (jml168)
 * @author Bronson McNaughton (btm38)
 */
public class WorkspaceTest extends TestCase
{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public WorkspaceTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(WorkspaceTest.class);
    }

    // A simple test for the Workspace constructors & getters.
    public void testWorkspaceConstructors()
    {
        Workspace work = new Workspace();
        ObservableList<TreeViewItem> people = observableArrayList();
        assertEquals("Untitled Workspace", work.getShortName());
        assertEquals("Untitled Workspace", work.getLongName());
        assertEquals("A blank workspace.", work.getDescription());
        assertEquals("Untitled Workspace", work.toString());
        assertEquals(people, work.getPeople());
        
        Workspace work2 = new Workspace("aShortName", "aLongName", "aDescription");
        assertEquals("aShortName", work2.getShortName());
        assertEquals("aLongName", work2.getLongName());
        assertEquals("aDescription", work2.getDescription());    
        assertEquals("aShortName", work2.toString());
    }
    
    // Tests Projects' setter methods.
    public void testWorkspaceSetters()
    {
        Workspace work = new Workspace();
        work.setShortName("aShortName");
        work.setLongName("aLongName");
        work.setDescription("aDescription");
        
        assertEquals("aShortName", work.getShortName());
        assertEquals("aLongName", work.getLongName());
        assertEquals("aDescription", work.getDescription());
        assertEquals("aShortName", work.toString());
    }
    

    /**
     * Tests that people are correctly added to the workspace through the add() method.
     */
    public void testAddPerson()
    {
        Workspace work = new Workspace();
        Person pers = new Person();
        work.add(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        people.add(pers);

        assertEquals(people, work.getPeople());
        
        work.add(pers);
        work.add(pers);
        assertEquals(3, work.getPeople().size());
    }
    
    /**
     * Tests that people are correctly removed from the workspace through the remove() method.
     */
    public void testRemovePerson()
    {
        Workspace work = new Workspace();
        Person pers = new Person();
        work.add(pers);
        work.remove(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        
        assertEquals(people, work.getPeople());
        
        work.add(pers);
        work.add(pers);
        work.remove(pers);
        work.remove(pers);
        
        assertEquals(0, work.getPeople().size());
    }
    
    /**
     * Tests that projects are correctly added to the workspace through the add() method.
     */
    public void testAddProject()
    {
        Workspace work = new Workspace();
        Project proj = new Project();
        work.add(proj);
        
        ObservableList<TreeViewItem> projects = observableArrayList();
        projects.add(proj);

        assertEquals(projects, work.getProjects());
        
        work.add(proj);
        work.add(proj);
        assertEquals(3, work.getProjects().size());
    }
    
    /**
     * Tests that projects are correctly removed from the workspace through the remove() method.
     */
    public void testRemoveProject()
    {
        Workspace work = new Workspace();
        Project proj = new Project();
        work.add(proj);
        work.remove(proj);
        
        ObservableList<TreeViewItem> projects = observableArrayList();
        
        assertEquals(projects, work.getProjects());
        
        work.add(proj);
        work.add(proj);
        work.remove(proj);
        work.remove(proj);
        
        assertEquals(0, work.getProjects().size());
    }
    
    /**
     * Tests that skills are correctly added to the workspace through the add() method.
     */
    public void testAddSkill()
    {
        Workspace work = new Workspace();
        Skill skill = new Skill();
        work.add(skill);
        
        ObservableList<TreeViewItem> skills = observableArrayList();
        skills.add(skill);

        assertEquals(skills, work.getSkills());
        
        work.add(skill);
        work.add(skill);
        assertEquals(3, work.getSkills().size());
    }
    
    /**
     * Tests that skills are correctly removed from the workspace through the remove() method.
     */
    public void testRemoveSkill()
    {
        Workspace work = new Workspace();
        Skill skill = new Skill();
        work.add(skill);
        work.remove(skill);
        
        ObservableList<TreeViewItem> skills = observableArrayList();
        
        assertEquals(skills, work.getSkills());
        
        work.add(skill);
        work.add(skill);
        work.remove(skill);
        work.remove(skill);
        
        assertEquals(0, work.getSkills().size());
    }
    
    /**
     * Tests that teams are correctly added to the workspace through the add() method.
     */
    public void testAddTeam()
    {
        Workspace work = new Workspace();
        Team team = new Team();
        
        work.add(team);
        
        work.add(team);
        work.add(team);
        assertEquals(4, work.getTeams().size());
    }
    
    /**
     * Tests that teams are correctly removed from the workspace through the remove() method.
     */
    public void testRemoveTeam()
    {
        Workspace work = new Workspace();
        Team team = new Team();
        
        work.add(team);
        work.remove(team);

        work.add(team);
        work.add(team);
        work.remove(team);
        work.remove(team);
        
        assertEquals(1, work.getTeams().size());
    }
}