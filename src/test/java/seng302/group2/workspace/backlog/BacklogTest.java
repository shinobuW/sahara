package seng302.group2.workspace.backlog;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

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
        List<Category> exp = observableArrayList();
        Assert.assertEquals(exp, backlog.getChildren());
    }


    @Test
    public void testToString()
    {
        Backlog backlogDefault = new Backlog();
        Backlog backlog = new Backlog("short", "long", "desc", null, null);
        Assert.assertEquals("Untitled Backlog", backlogDefault.toString());
        Assert.assertEquals("short", backlog.toString());
    }


    @Test
    public void testCompareTo()
    {
        Backlog backlogDefault = new Backlog();
        Backlog backlog = new Backlog("short", "long", "desc", null, null);
        Assert.assertTrue(0 < backlog.compareTo(backlogDefault));
        Assert.assertEquals(30, backlog.compareTo(backlogDefault));
    }


    @Test
    public void testEdit()
    {
        Backlog backlog = new Backlog();
        Story oldStory = new Story();
        backlog.add(oldStory);

        Person po = new Person();
        Project proj = new Project();
        Collection<Story> stories = new ArrayList<>();
        Story story = new Story();
        stories.add(story);

        backlog.edit("short", "long", "desc", po, proj, stories);

        Assert.assertEquals("short", backlog.getShortName());
        Assert.assertEquals("long", backlog.getLongName());
        Assert.assertEquals("desc", backlog.getDescription());
        Assert.assertEquals(po, backlog.getProductOwner());
        Assert.assertEquals(proj, backlog.getProject());
        Assert.assertEquals(stories, backlog.getStories());
        Assert.assertTrue(story.getBacklog() == backlog);
        Assert.assertFalse(oldStory.getBacklog() == backlog);

        Global.commandManager.undo();

        Assert.assertEquals("Untitled Backlog", backlog.getShortName());
        Assert.assertEquals("Untitled Backlog", backlog.getLongName());
        Assert.assertEquals("", backlog.getDescription());
        Assert.assertEquals(null, backlog.getProductOwner());
        Assert.assertEquals(null, backlog.getProject());
        Assert.assertTrue(backlog.getStories().contains(oldStory));
        Assert.assertEquals(1, backlog.getStories().size());
        Assert.assertFalse(story.getBacklog() == backlog);
        Assert.assertTrue(oldStory.getBacklog() == backlog);

        Global.commandManager.redo();

        Assert.assertEquals("short", backlog.getShortName());
        Assert.assertEquals("long", backlog.getLongName());
        Assert.assertEquals("desc", backlog.getDescription());
        Assert.assertEquals(po, backlog.getProductOwner());
        Assert.assertEquals(proj, backlog.getProject());
        Assert.assertEquals(stories, backlog.getStories());
        Assert.assertTrue(story.getBacklog() == backlog);
        Assert.assertFalse(oldStory.getBacklog() == backlog);
    }


    @Test
    public void testDeleteBacklog()
    {
        Backlog backlog = new Backlog();
        Project project = new Project();
        project.add(backlog);
        Global.currentWorkspace.add(project);

        backlog.deleteBacklog();
        Assert.assertFalse(project.getBacklogs().contains(backlog));
        Assert.assertNotEquals(project, backlog.getProject());

        Global.commandManager.undo();
        Assert.assertTrue(project.getBacklogs().contains(backlog));
        Assert.assertEquals(project, backlog.getProject());
    }


    /**
     * Tests both the prepSerialization and postDeserialization methods
     */
    @Test
    public void testSerializationMethods()
    {
        Backlog backlog = new Backlog();

        Story story = new Story();
        backlog.getStories().add(story);
        backlog.prepSerialization();
        Assert.assertTrue(backlog.getSerializableStories().contains(story));

        Story story2 = new Story();
        backlog.getSerializableStories().add(story2);
        backlog.postDeserialization();
        Assert.assertTrue(backlog.getStories().contains(story2));
        Assert.assertEquals(2, backlog.getStories().size());
    }
}
