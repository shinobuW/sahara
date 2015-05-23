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
import seng302.group2.workspace.team.Allocation;
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
     * Tests the addition and removal of teams within projects
     */
    @Test
    public void testAddRemoveTeam()
    {
        Project proj = new Project();
        Team testTeam = new Team();

        proj.getTeams().clear();
        proj.add(testTeam);
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(testTeam);

        Assert.assertEquals(teams, proj.getTeams());

        proj.remove(testTeam);
        Assert.assertTrue(!proj.getTeams().contains(testTeam));

        // The second add method without undo
        proj.getTeams().clear();
        proj.addWithoutUndo(testTeam);
        teams = new ArrayList<>();
        teams.add(testTeam);

        Assert.assertEquals(teams, proj.getTeams());

        proj.removeWithoutUndo(testTeam);
        Assert.assertTrue(!proj.getTeams().contains(testTeam));
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

        //Global.commandManager.undo();
        //proj.remove(allocation);
        //Assert.assertFalse(proj.getTeamAllocations().contains(allocation));
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

    /**
     * Tests that a project properly prepares for serialization
     */
    @Test
    public void testPrepSerialization()
    {
        Project proj = new Project();
        Team testTeam = new Team();
        Release testRelease = new Release();

        proj.getSerializableReleases().clear();
        proj.getSerializableTeams().clear();

        Assert.assertEquals(new ArrayList<Skill>(), proj.getSerializableTeams());
        Assert.assertEquals(new ArrayList<Skill>(), proj.getSerializableReleases());

        proj.add(testTeam);
        proj.add(testRelease);

        proj.prepSerialization();

        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<Release> releases = new ArrayList<>();
        teams.add(testTeam);
        releases.add(testRelease);

        Assert.assertEquals(teams, proj.getSerializableTeams());
        Assert.assertEquals(releases, proj.getSerializableReleases());
    }

    /**
     * Tests that a project properly post-pares after deserialization
     */
    @Test
    public void testPostSerialization()
    {
        Project proj = new Project();
        Team testTeam = new Team();
        Release testRelease = new Release();

        proj.getReleases().clear();
        proj.getTeams().clear();

        Assert.assertEquals(new ArrayList<Skill>(), proj.getTeams());
        Assert.assertEquals(new ArrayList<Skill>(), proj.getReleases());

        proj.getSerializableTeams().add(testTeam);
        proj.getSerializableReleases().add(testRelease);

        proj.postSerialization();

        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<Release> releases = new ArrayList<>();
        teams.add(testTeam);
        releases.add(testRelease);

        Assert.assertEquals(teams, proj.getTeams());
        Assert.assertEquals(releases, proj.getReleases());
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

       System.out.print(proj.compareTo(proj2));

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