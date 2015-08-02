package seng302.group2.workspace.project.sprint;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.team.Team;
import seng302.group2.workspace.workspace.Workspace;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class SprintTest {

    /**
     * Test for the Sprint constructors
     */
    @Test
    public void testSprintConstructors() {
        Sprint sprint = new Sprint();
        Backlog backlog = new Backlog();
        Team team = new Team();
        Release release = new Release();

        Assert.assertEquals("Untitled Sprint/Goal", sprint.getGoal());
        Assert.assertEquals("Untitled Sprint/Goal", sprint.getLongName());
        Assert.assertEquals("", sprint.getDescription());
        Assert.assertEquals(null, sprint.getBacklog());
        Assert.assertEquals(null, sprint.getTeam());
        Assert.assertEquals(null, sprint.getRelease());
        Assert.assertEquals(LocalDate.now(), sprint.getStartDate());
        Assert.assertEquals(LocalDate.now().plusWeeks(2), sprint.getEndDate());

        Sprint testSprint = new Sprint("Goal", "LongName", "Description", LocalDate.now(), LocalDate.now().plusDays(5),
                backlog, team, release);

        Assert.assertEquals("Goal", testSprint.getGoal());
        Assert.assertEquals("LongName", testSprint.getLongName());
        Assert.assertEquals("Description", testSprint.getDescription());
        Assert.assertEquals(LocalDate.now(), testSprint.getStartDate());
        Assert.assertEquals(LocalDate.now().plusDays(5), testSprint.getEndDate());
        Assert.assertEquals(backlog, testSprint.getBacklog());
        Assert.assertEquals(team, testSprint.getTeam());
        Assert.assertEquals(release, testSprint.getRelease());
    }

    /**
     * Tests the prepSerialization method of sprint
     */
    @Test
    public void testPrepSerialization() {
        Sprint sprint = new Sprint();

        Story story = new Story();
        sprint.getStories().add(story);
        sprint.prepSerialization();
        Assert.assertTrue(sprint.getSerializableStories().contains(story));

    }

    /**
     * Tests the postDeserialization method of sprint
     */
    @Test
    public void testPostDeserialization() {
        Sprint sprint  = new Sprint();

        Story story = new Story();
        sprint.getSerializableStories().add(story);
        sprint.postDeserialization();
        Assert.assertTrue(sprint.getStories().contains(story));
        Assert.assertEquals(1, sprint.getStories().size());
    }

    @Test
    public void testAddStory() {
        Sprint sprint = new Sprint();
        Story story = new Story();

        sprint.add(story);
        Assert.assertTrue(sprint.getStories().contains(story));

        Global.commandManager.undo();
        Assert.assertFalse(sprint.getStories().contains(story));

        Global.commandManager.redo();
        Assert.assertTrue(sprint.getStories().contains(story));
    }

    @Test
    public void testGenerateXML() {
        new ReportGenerator();
        Sprint sprint = new Sprint("goal", "longname", "description", LocalDate.now(), LocalDate.now().plusDays(5), new Backlog(), new Team(), new Release());

        Element sprintElement = sprint.generateXML();
        Assert.assertEquals("[#text: goal]", sprintElement.getChildNodes().item(1).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: longname]", sprintElement.getChildNodes().item(2).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: description]", sprintElement.getChildNodes().item(3).getChildNodes().item(0).toString());


        Assert.assertEquals("[#text: Untitled Backlog]", sprintElement.getChildNodes().item(6).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: Untitled Team]", sprintElement.getChildNodes().item(7).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: Untitled Release]", sprintElement.getChildNodes().item(8).getChildNodes().item(0).toString());

        Assert.assertEquals(10, sprintElement.getChildNodes().getLength());

    }

    @Test
    public void testToString() {
        Sprint sprintDefault = new Sprint();
        Sprint sprint = new Sprint("goal", "long", "desc", LocalDate.now(), LocalDate.now().plusDays(1), null, null, null);
        Assert.assertEquals("Untitled Sprint/Goal", sprintDefault.toString());
        Assert.assertEquals("goal", sprint.toString());
    }

    @Test
    public void testEdit() {
        Global.currentWorkspace = new Workspace();
        Sprint sprint = new Sprint();
        Collection<Story> oldStories = new ArrayList<>();
        Story oldStory = new Story();
        oldStories.add(oldStory);
        sprint.add(oldStory);

        Backlog backlog = new Backlog();
        Team team = new Team();
        Release release = new Release();
        Collection<Story> stories = new ArrayList<>();
        Story story = new Story();
        stories.add(story);

        sprint.edit("goal", "long", "desc", LocalDate.now(), LocalDate.now().plusDays(1), backlog, team, release, stories);

        Assert.assertEquals("goal", sprint.getGoal());
        Assert.assertEquals("long", sprint.getLongName());
        Assert.assertEquals("desc", sprint.getDescription());
        Assert.assertEquals(LocalDate.now(), sprint.getStartDate());
        Assert.assertEquals(LocalDate.now().plusDays(1), sprint.getEndDate());
        Assert.assertEquals(backlog, sprint.getBacklog());
        Assert.assertEquals(team, sprint.getTeam());
        Assert.assertEquals(release, sprint.getRelease());
        Assert.assertEquals(stories, sprint.getStories());

        Global.commandManager.undo();

        Assert.assertEquals("Untitled Sprint/Goal", sprint.getGoal());
        Assert.assertEquals("Untitled Sprint/Goal", sprint.getLongName());
        Assert.assertEquals("", sprint.getDescription());
        Assert.assertEquals(LocalDate.now(), sprint.getStartDate());
        Assert.assertEquals(LocalDate.now().plusWeeks(2), sprint.getEndDate());
        Assert.assertEquals(null, sprint.getBacklog());
        Assert.assertEquals(null, sprint.getTeam());
        Assert.assertEquals(null, sprint.getRelease());
        Assert.assertEquals(oldStories, sprint.getStories());

        Global.commandManager.redo();

        Assert.assertEquals("goal", sprint.getGoal());
        Assert.assertEquals("long", sprint.getLongName());
        Assert.assertEquals("desc", sprint.getDescription());
        Assert.assertEquals(LocalDate.now(), sprint.getStartDate());
        Assert.assertEquals(LocalDate.now().plusDays(1), sprint.getEndDate());
        Assert.assertEquals(backlog, sprint.getBacklog());
        Assert.assertEquals(team, sprint.getTeam());
        Assert.assertEquals(release, sprint.getRelease());
        Assert.assertEquals(stories, sprint.getStories());
    }

    @Test
    public void testDeleteSprint() {
        Backlog backlog = new Backlog();
        Sprint sprint = new Sprint("goal", "long", "desc", LocalDate.now(), LocalDate.now().plusDays(1), backlog, null, null);
        Project project = new Project();
        project.add(backlog);
        project.add(sprint);
        Global.currentWorkspace.add(project);

        sprint.deleteSprint();
        Assert.assertFalse(project.getSprints().contains(sprint));

        Global.commandManager.undo();
        Assert.assertTrue(project.getSprints().contains(sprint));
    }
}