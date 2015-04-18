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
        Workspace proj = new Workspace();
        ObservableList<TreeViewItem> people = observableArrayList();
        assertEquals("Untitled Workspace", proj.getShortName());
        assertEquals("Untitled Workspace", proj.getLongName());
        assertEquals("A blank workspace.", proj.getDescription());
        assertEquals("Untitled Workspace", proj.toString());
        assertEquals(people, proj.getPeople());
        
        Workspace proj2 = new Workspace("aShortName", "aLongName", "aDescription");
        assertEquals("aShortName", proj2.getShortName());
        assertEquals("aLongName", proj2.getLongName());
        assertEquals("aDescription", proj2.getDescription());    
        assertEquals("aShortName", proj2.toString());
    }
    
    // Tests Projects' setter methods.
    public void testWorkspaceSetters()
    {
        Workspace proj = new Workspace();
        proj.setShortName("aShortName");
        proj.setLongName("aLongName");
        proj.setDescription("aDescription");
        
        assertEquals("aShortName", proj.getShortName());
        assertEquals("aLongName", proj.getLongName());
        assertEquals("aDescription", proj.getDescription());
        assertEquals("aShortName", proj.toString());
    }
    

    /**
     * Tests that people are correctly added to projects through the add() method.
     */
    public void testAddPerson()
    {
        Workspace proj = new Workspace();
        Person pers = new Person();
        proj.add(pers);
        
        ObservableList<TreeViewItem> people = observableArrayList();
        people.add(pers);

        assertEquals(people, proj.getPeople());
        
        proj.add(pers);
        proj.add(pers);
        assertEquals(3, proj.getPeople().size());
    }
    
}