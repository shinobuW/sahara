/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.release;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.workspace.project.Project;

import java.util.Date;

/**
 *
 * @author Shinobu
 */
public class ReleaseTest extends TestCase
{
     /**
     * Create the test case.
     * @param testName name of the test case
     */
    public ReleaseTest(String testName)
    {
        super(testName);
    }
    
     /**
     * @return the suite of tests being tested.
     */
    public static Test suite()
    {
        return new TestSuite(ReleaseTest.class);
    }
    
    /**
     * Test constructors
     */
    public void testReleaseConstructor()
    {
        Release release1 = new Release();
        Assert.assertEquals("Untitled Release", release1.getShortName());
        Assert.assertEquals("Release without project assigned should not exist", 
                release1.getDescription());
        Assert.assertEquals(new Date("20/20/2020"), release1.getEstimatedDate());
        Assert.assertEquals("Untitled Project", release1.getProject().getShortName());
        
        Project testProject = new Project();
        Release release2 = new Release("Test2", testProject);
        Assert.assertEquals("Test2", release2.getShortName());
        Assert.assertEquals("Untitled Project", release2.getProject().getShortName());
        
        Release release3 = new Release("Test", "description", new Date("12/12/2020"), testProject);
        Assert.assertEquals("Test", release3.getShortName());
        Assert.assertEquals("description", release3.getDescription());
        Assert.assertEquals(new Date("12/12/2020"), release3.getEstimatedDate());
        Assert.assertEquals("Untitled Project", release3.getProject().getShortName());
    }
    
    public void testReleaseSetters()
    {
        Release testRelease = new Release();
        testRelease.setShortName("Release 2.0");
        testRelease.setDescription("Second Release");
        testRelease.setEstimatedDate(new Date("26/03/2016"));
        testRelease.setProject(new Project("Test Project", "Long name", "Description"));
        
        Assert.assertEquals("Release 2.0", testRelease.getShortName());
        Assert.assertEquals("Second Release", testRelease.getDescription());
        Assert.assertEquals(new Date("26/03/2016"), testRelease.getEstimatedDate());
        Assert.assertEquals("Test Project", testRelease.getProject().getShortName());   
    }
}