package seng302.group2.workspace.backlog;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;

/**
 * A series if tests relating to Backlog
 * Created by cvs20 on 22/05/15.
 */
public class BacklogTest
{

    /**
     * Test for the backlog constructors
     */
    @Test
    public void testStoryConstructors()
    {
        Backlog backlog = new Backlog();
        Project project = new Project();
        Person codie = new Person();

        Assert.assertEquals("Untitled Backlog", backlog.getShortName());
        Assert.assertEquals("Untitled Backlog", backlog.getLongName());
        Assert.assertEquals("", backlog.getDescription());
        Assert.assertEquals(null, backlog.getProductOwner());
        Assert.assertEquals(null, backlog.getProject());

        Backlog testBacklog = new Backlog("Test Story", "A long name",
                "Test description", codie, project);
        Assert.assertEquals("Test Story", testBacklog.getShortName());
        Assert.assertEquals("A long name", testBacklog.getLongName());
        Assert.assertEquals("Test description", testBacklog.getDescription());
        Assert.assertEquals(codie, testBacklog.getProductOwner());
        Assert.assertEquals(project, testBacklog.getProject());
    }

    /**
     * Test backlog setters
     */
    @Test
    public void testBacklogSetters()
    {
        Backlog backlog = new Backlog();
        Person codie = new Person();
        Project project = new Project();

        backlog.setShortName("Test Backlog");
        backlog.setLongName("Test Long Name");
        backlog.setDescription("Description");
        backlog.setProductOwner(codie);
        backlog.setProject(project);

        Assert.assertEquals("Test Backlog", backlog.getShortName());
        Assert.assertEquals("Test Long Name", backlog.getLongName());
        Assert.assertEquals("Description", backlog.getDescription());
        Assert.assertEquals(codie, backlog.getProductOwner());
        Assert.assertEquals(project, backlog.getProject());
    }

    @Test
    public void testAddStory()
    {
        Backlog backlog = new Backlog();
        Story story = new Story();

        backlog.add(story);
        Assert.assertTrue(backlog.getStories().contains(story));

        Global.commandManager.undo();
        Assert.assertFalse(backlog.getStories().contains(story));

        Global.commandManager.redo();
        Assert.assertTrue(backlog.getStories().contains(story));
    }


    @Test
    public void testGetChildren()
    {
        Backlog backlog = new Backlog();
        Assert.assertNull(backlog.getChildren());
    }


    @Test
    public void testToString()
    {
        Backlog backlogDefault = new Backlog();
        Backlog backlog = new Backlog("short", "long", "desc", null, null);
        Assert.assertEquals("Untitled Backlog", backlogDefault.toString());
        Assert.assertEquals("short", backlog.toString());
    }
}
