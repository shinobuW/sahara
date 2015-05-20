/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.story;

import junit.framework.Assert;
import junit.framework.TestSuite;
import org.junit.Test;
import junit.framework.TestCase;
import seng302.group2.workspace.project.Project;

/**
 * A series of tests relating to Story
 * @author swi67
 */
public class StoryTest extends TestCase
{
    
    /**
     * Create the test case.
     * @param testName name of the test case
     */
    public StoryTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested.
     */
    public static junit.framework.Test suite()
    {
        return new TestSuite(StoryTest.class);
    }
    
    /**
     * Test for the story constructors
     */
    @Test
    public void testStoryConstructors()
    {
        Story story = new Story();
        Project project = new Project();
        Assert.assertEquals("Untitled Story", story.getShortName());
        Assert.assertEquals("", story.getDescription());
        Assert.assertEquals("Untitled Story", story.toString());
        Assert.assertEquals(null, story.getCreator());

        Story testStory = new Story("Test Story", "A long Name", "test description",
                "Tyler the Creator", project, "1");
        Assert.assertEquals("Test Story", testStory.getShortName());
        Assert.assertEquals("A long Name", testStory.getLongName());
        Assert.assertEquals("test description", testStory.getDescription());
        Assert.assertEquals("1", testStory.getPriority());
        Assert.assertEquals("Tyler the Creator", testStory.getCreator());
        Assert.assertEquals("Test Story", testStory.toString()); 
        Assert.assertEquals(project, testStory.getProject());
    }
    
    /**
     * Test Story setters
     */
    @Test
    public void testStorySetters()
    {
        Story story = new Story();
        story.setShortName("Test Story");
        story.setLongName("Test Long Name");
        story.setDescription("description");
        story.setPriority("5");
        story.setCreator("Shinobu");
        
        Assert.assertEquals("Test Story", story.getShortName());
        Assert.assertEquals("Test Long Name", story.getLongName());
        Assert.assertEquals("description", story.getDescription());
        Assert.assertEquals("5", story.getPriority());
        Assert.assertEquals("Shinobu", story.getCreator());
    }
    
    /**
     * Tests the compareTo method of Story to ensure it correctly returns an int representing if a 
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo()
    {
        Story story1 = new Story();
        Story story2 = new Story();
        story1.setShortName("A");
        story2.setShortName("Z");

        Assert.assertTrue(story1.compareTo(story2) <= 0);
        Assert.assertTrue(story2.compareTo(story1) >= 0);
        Assert.assertTrue(story1.compareTo(story1) == 0);
    }
    
}
