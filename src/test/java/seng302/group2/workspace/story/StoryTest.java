/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.story;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.project.Project;

/**
 * A series of tests relating to Story
 * @author swi67
 */
public class StoryTest
{
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
                "Tyler the Creator", project, 1);
        Assert.assertEquals("Test Story", testStory.getShortName());
        Assert.assertEquals("A long Name", testStory.getLongName());
        Assert.assertEquals("test description", testStory.getDescription());
        Assert.assertEquals(1, testStory.getPriority(), 0);
        Assert.assertEquals("Tyler the Creator", testStory.getCreator());
        Assert.assertEquals("Test Story", testStory.toString()); 
        Assert.assertEquals(project, testStory.getProject());

        Assert.assertNull(testStory.getChildren());
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
        story.setPriority(5);
        story.setCreator("Shinobu");
        
        Assert.assertEquals("Test Story", story.getShortName());
        Assert.assertEquals("Test Long Name", story.getLongName());
        Assert.assertEquals("description", story.getDescription());
        Assert.assertEquals(5, story.getPriority(), 0);
        Assert.assertEquals("Shinobu", story.getCreator());
    }
    

    @Test
    public void testComparators()
    {
        Story defaultStory = new Story();
        Story story = new Story();

        // Short name comparator
        story.setShortName("a story");
        Assert.assertTrue(0 < Story.StoryNameComparator.compare(story, defaultStory));

        // Priority comparator
        story.setPriority(1);
        defaultStory.setPriority(5);
        Assert.assertTrue(0 > Story.StoryPriorityComparator.compare(story, defaultStory));
    }


    @Test
    public void testDeleteStory()
    {
        Project proj = new Project();
        Backlog back = new Backlog();
        proj.add(back);

        Story story = new Story("short", "long", "desc", "creator", null, 5);
        Story story2 = new Story("short2", "long", "desc", "creator", null, 5);
        story.setProject(proj);
        story2.setProject(proj);
        story2.setBacklog(back);

        proj.add(story);
        back.add(story2);

        Assert.assertTrue(back.getStories().contains(story2));
        Assert.assertTrue(proj.getUnallocatedStories().contains(story));

        story.deleteStory();
        story2.deleteStory();

        Assert.assertFalse(back.getStories().contains(story2));
        Assert.assertFalse(proj.getUnallocatedStories().contains(story));

        Global.commandManager.undo();
        Global.commandManager.undo();

        Assert.assertTrue(back.getStories().contains(story2));
        Assert.assertTrue(proj.getUnallocatedStories().contains(story));
    }

}
