/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A series of tests relating to Projects
 * @author Jordane Lew (jml168)
 * @author Bronson McNaughton (btm38)
 */
public class ProjectTest extends TestCase
{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public ProjectTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(ProjectTest.class);
    }

    /**
     * A simple test for the Project constructors & getters.
     */
    public void testProjectConstructors()
    {
        Project proj = new Project();
        ObservableList<TreeViewItem> people = observableArrayList();
        assertEquals("Untitled", proj.getShortName());
        assertEquals("Untitled Project", proj.getLongName());
        assertEquals("A blank project.", proj.getDescription());
        assertEquals("Untitled", proj.toString());
        assertEquals(people, proj.getPeople());
        // assertEquals(null, proj.getChildren());
        // assertEquals(null, proj.getCategories());
        
        Project proj2 = new Project("aShortName", "aLongName", "aDescription");
        assertEquals("aShortName", proj2.getShortName());
        assertEquals("aLongName", proj2.getLongName());
        assertEquals("aDescription", proj2.getDescription());    
        assertEquals("aShortName", proj2.toString());
    }
    
    /**
     * Tests Projects' setter methods.
     */
    public void testProjectSetters()
    {
        Project proj = new Project();
        proj.setShortName("aShortName");
        proj.setLongName("aLongName");
        proj.setDescription("aDescription");
        
        assertEquals("aShortName", proj.getShortName());
        assertEquals("aLongName", proj.getLongName());
        assertEquals("aDescription", proj.getDescription());
        assertEquals("aShortName", proj.toString());
    }
    
    /**
     * Tests Projects' AddPerson method.
     */
    public void testAddPerson()
    {
        Project proj = new Project();
        Person pers = new Person();
        proj.addPerson(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        people.add(pers);

        assertEquals(people, proj.getPeople());
    }
}