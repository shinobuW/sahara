package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.BacklogCategory;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.StoryCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * A series of tests relating to Projects
 * @author Jordane Lew (jml168)
 */
public class ProjectTest extends TestCase
{
    /**
     * A simple test for the Workspace constructors and getters.
     */
    @Test
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


    /**
     * Tests the projects' setter methods.
     */
    @Test
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
     * Tests that releases are added to projects properly
     */
    @Test
    public void testAddRelease()
    {
        Project proj = new Project();
        Release release = new Release("test release", proj);
        proj.add(release);
        
        assertTrue(proj.getReleases().contains(release));

        Global.commandManager.undo();

        assertFalse(proj.getReleases().contains(release));
    }


    /**
     * Tests the addition and removal of project's team allocation
     */
    @Test
    public void testAddRemoveTeamAllocation()
    {
        LocalDate startDate = LocalDate.of(2015, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2015, Month.JANUARY, 2);
        Project proj = new Project();
        Team team = new Team();

        Allocation allocation = new Allocation(proj, team, startDate, endDate);
        proj.add(allocation);
        Assert.assertTrue(proj.getTeamAllocations().contains(allocation));

        Global.commandManager.undo();
        Assert.assertFalse(proj.getTeamAllocations().contains(allocation));
    }


    /**
     * Tests the addition and removal of project's backlogs
     */
    @Test
    public void testAddRemoveTeamBacklog()
    {
        Project proj = new Project();
        Backlog back = new Backlog();

        proj.add(back);
        Assert.assertTrue(proj.getBacklogs().contains(back));

        Global.commandManager.undo();
        Assert.assertFalse(proj.getBacklogs().contains(back));

        Global.commandManager.redo();
    }


    /**
     * Tests the addition and removal of project's team allocation
     */
    @Test
    public void testGetAllocationMethods()
    {
        LocalDate startDate = LocalDate.now().minusYears(1);
        LocalDate endDate = LocalDate.now().plusYears(1);
        Project proj = new Project();
        Team team = new Team();
        Allocation currentAllocation = new Allocation(proj, team, startDate, endDate);

        LocalDate startDate2 = LocalDate.now().plusYears(2);
        LocalDate endDate2 = LocalDate.now().plusYears(3);
        Team team2 = new Team();
        Allocation futureAllocation = new Allocation(proj, team2, startDate2, endDate2);

        LocalDate startDate3 = LocalDate.now().minusYears(3);
        LocalDate endDate3 = LocalDate.now().minusYears(2);
        Team team3 = new Team();
        Allocation pastAllocation = new Allocation(proj, team3, startDate3, endDate3);

        Project anotherProj = new Project();
        Allocation anotherAllocation = new Allocation(anotherProj, team3, startDate3, endDate3);

        Global.currentWorkspace.getTeams().addAll(team, team2, team3);

        proj.add(currentAllocation);
        proj.add(futureAllocation);
        proj.add(pastAllocation);
        proj.add(anotherAllocation);  // Should not add correctly as it has another project as param

        Assert.assertFalse(proj.getTeamAllocations().contains(anotherAllocation));

        Assert.assertTrue(proj.getCurrentTeams().contains(team));
        Assert.assertFalse(proj.getCurrentTeams().contains(team2));

        Assert.assertTrue(proj.getAllTeams().contains(team));
        Assert.assertTrue(proj.getAllTeams().contains(team2));

        Assert.assertFalse(proj.getPastAllocations().contains(currentAllocation));
        Assert.assertFalse(proj.getPastAllocations().contains(futureAllocation));
        Assert.assertTrue(proj.getPastAllocations().contains(pastAllocation));
    }


    /**
     * Tests that a list of releases as TreeViewItems are fetched correctly
     */
    @Test
    public void testTreeViewReleases()
    {
        Project proj = new Project();
        proj.getReleases().clear();
        Assert.assertTrue(proj.getTreeViewReleases().size() == 0);

        Release release = new Release("test release", proj);
        proj.add(release);

        Assert.assertTrue(proj.getTreeViewReleases().size() == 1);
        Assert.assertEquals(proj.getTreeViewReleases().get(0), release);
    }


    /**
     * Tests that a projects children are correct
     */
    @Test
    public void testGetChildren()
    {
        Project proj = new Project();
        ObservableList<TreeViewItem> children = observableArrayList();
        ReleaseCategory releasesCategory = new ReleaseCategory("Releases", proj);
        children.add(releasesCategory);
        BacklogCategory backlogCategory = new BacklogCategory("Backlog", proj);
        children.add(backlogCategory);
        StoryCategory storiesCategory = new StoryCategory("Unassigned Stories", proj);
        children.add(storiesCategory);

        Assert.assertEquals(children, proj.getChildren());

        Release release = new Release("test release", proj);
        proj.add(release);
        Backlog backlog = new Backlog();
        proj.add(backlog);
        Story story = new Story();
        proj.add(story);
        children.clear();
        releasesCategory = new ReleaseCategory("Releases", proj);
        children.add(releasesCategory);
        backlogCategory = new BacklogCategory("Backlog", proj);
        children.add(backlogCategory);
        storiesCategory = new StoryCategory("Unassigned Stories", proj);
        children.add(storiesCategory);

        Assert.assertEquals(children, proj.getChildren());
    }


    @Test
    public void testDeleteProject()
    {
        Project proj = new Project();
        Global.currentWorkspace.add(proj);
        proj.deleteProject(Global.currentWorkspace);
        Assert.assertFalse(Global.currentWorkspace.getProjects().contains(proj));

        Global.commandManager.undo();

        Assert.assertTrue(Global.currentWorkspace.getProjects().contains(proj));
    }


    @Test
    public void testAddStory()
    {
        Project proj = new Project();
        Backlog back = new Backlog();
        Story loneStory = new Story();
        Story backStory = new Story();

        proj.add(back);
        back.add(backStory);
        proj.add(loneStory);

        Assert.assertTrue(proj.getUnallocatedStories().contains(loneStory));
        Assert.assertFalse(proj.getUnallocatedStories().contains(backStory));
        Assert.assertTrue(proj.getAllStories().contains(loneStory));
        Assert.assertTrue(proj.getAllStories().contains(backStory));

        Global.commandManager.undo();

        Assert.assertFalse(proj.getAllStories().contains(loneStory));
    }


    /**
     * Tests that a project properly prepares for serialization
     */
    @Test
    public void testPrepSerialization()
    {
        Project proj = new Project();
        Team testTeam = new Team();
        Release testRelease = new Release();
        Backlog testBacklog = new Backlog();
        Story testStory = new Story();
        Allocation testAllocation = new Allocation(proj, testTeam, LocalDate.now(),
                LocalDate.now());

        proj.getSerializableReleases().clear();
        proj.getSerilizableBacklogs().clear();
        proj.getSerilizableStories().clear();
        proj.getSerializableTeamAllocations();

        Assert.assertEquals(new ArrayList<Skill>(), proj.getSerilizableStories());
        Assert.assertEquals(new ArrayList<Backlog>(), proj.getSerilizableBacklogs());
        Assert.assertEquals(new ArrayList<Release>(), proj.getSerializableReleases());
        Assert.assertEquals(new ArrayList<Allocation>(), proj.getSerializableTeamAllocations());

        proj.add(testBacklog);
        proj.add(testStory);
        proj.add(testRelease);
        proj.add(testAllocation);

        proj.prepSerialization();

        ArrayList<Story> stories = new ArrayList<>();
        ArrayList<Release> releases = new ArrayList<>();
        ArrayList<Backlog> backlogs = new ArrayList<>();
        ArrayList<Allocation> allocations = new ArrayList<>();
        stories.add(testStory);
        releases.add(testRelease);
        backlogs.add(testBacklog);
        allocations.add(testAllocation);

        Assert.assertEquals(backlogs, proj.getSerilizableBacklogs());
        Assert.assertEquals(stories, proj.getSerilizableStories());
        Assert.assertEquals(releases, proj.getSerializableReleases());
        Assert.assertEquals(allocations, proj.getSerializableTeamAllocations());
    }

    /**
     * Tests that a project properly post-pares after deserialization
     */
    @Test
    public void testPostDeserialization()
    {
        Project proj = new Project();
        Release testRelease = new Release();
        Backlog testBacklog = new Backlog();
        Story testStory = new Story();
        Allocation testAllocation = new Allocation(proj, new Team(), LocalDate.now(),
                LocalDate.now());

        proj.getReleases().clear();
        proj.getUnallocatedStories().clear();
        proj.getBacklogs().clear();
        proj.getTeamAllocations().clear();

        Assert.assertEquals(new ArrayList<Skill>(), proj.getReleases());
        Assert.assertTrue(proj.getUnallocatedStories().isEmpty());
        Assert.assertTrue(proj.getBacklogs().isEmpty());
        Assert.assertTrue(proj.getTeamAllocations().isEmpty());

        proj.getSerializableReleases().add(testRelease);
        proj.getSerilizableStories().add(testStory);
        proj.getSerilizableBacklogs().add(testBacklog);
        proj.getSerializableTeamAllocations().add(testAllocation);

        proj.postSerialization();
        ArrayList<Release> releases = new ArrayList<>();
        releases.add(testRelease);

        Assert.assertEquals(releases, proj.getReleases());
        Assert.assertTrue(proj.getBacklogs().contains(testBacklog));
        Assert.assertTrue(proj.getUnallocatedStories().contains(testStory));
        Assert.assertTrue(proj.getTeamAllocations().contains(testAllocation));
    }

    
    /**
     * Tests the compareTo method of Project to ensure it correctly returns an int representing if a 
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo()
    {
        Project proj = new Project("aShortName", "aLongName", "aDescription");
        Project proj2 = new Project("zShortName", "Long Name", "Description");

       //System.out.print(proj.compareTo(proj2));

        Assert.assertTrue(proj.compareTo(proj2) <= 0);
        Assert.assertTrue(proj2.compareTo(proj) >= 0);
        Assert.assertTrue(proj.compareTo(proj) == 0);
    }


    @Test
    public void testEdit()
    {
        Project project = new Project("aShortName", "aLongName", "aDescription");
        project.edit("newShortname", "newLongName", "newDescription", null);

        assertEquals("newShortname", project.getShortName());
        assertEquals("newLongName", project.getLongName());
        assertEquals("newDescription", project.getDescription());

        Global.commandManager.undo();

        assertEquals("aShortName", project.getShortName());
        assertEquals("aLongName", project.getLongName());
        assertEquals("aDescription", project.getDescription());
    }
}