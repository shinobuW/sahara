/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A series of tests relating to Projects
 * @author Jordane Lew (jml168)
 */
public class ProjectTest
    extends TestCase
{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public ProjectTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ProjectTest.class );
    }

    /**
     * A simple test for the Project constructors
     */
    public void testProjectConstructors()
    {
        Project proj = new Project("aShortName", "aLongName", "aDescription");
        assertEquals("aShortName", proj.getShortName());
        assertEquals("aLongName", proj.getLongName());
        assertEquals("aDescription", proj.getDescription());
    }
}