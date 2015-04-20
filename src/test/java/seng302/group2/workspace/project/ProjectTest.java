package seng302.group2.workspace.project;

import java.util.Arrays;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.workspace.release.Release;


/**
 * A series of tests relating to Projects
 * @author Jordane Lew (jml168)
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

    // A simple test for the Workspace constructors & getters.
    public void testProjectConstructors()
    {
        Project proj = new Project();
        assertEquals("Untitled Project", proj.getShortName());
        assertEquals("Untitled Project", proj.getLongName());
        assertEquals("A blank project.", proj.getDescription());
        assertEquals("Untitled Project", proj.toString());

        Project proj2 = new Project("aShortName", "aLongName", "aDescription");
        assertEquals("aShortName", proj2.getShortName());
        assertEquals("aLongName", proj2.getLongName());
        assertEquals("aDescription", proj2.getDescription());
        assertEquals("aShortName", proj2.toString());
    }

    // Tests Projects' setter methods.
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
    
    public void testAddRelease()
    {
        Project proj = new Project();
        Release release = new Release("test release", proj);
        proj.addRelease(release);
        
        assertTrue(proj.getReleases().contains(release));
    }
}