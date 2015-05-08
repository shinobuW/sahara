package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Date;

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

        proj.remove(release);
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
        Date startDate = new Date();
        Date endDate = new Date();
        Project proj = new Project();
        Team team = new Team();

        Allocation allocation = new Allocation(proj, team, startDate, endDate);
        proj.add(allocation);
        Assert.assertTrue(proj.getTeamAllocations().contains(allocation));

        proj.remove(allocation);
        Assert.assertTrue(proj.getTeamAllocations().isEmpty());
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
        Assert.assertEquals(children, proj.getChildren());

        Release release = new Release("test release", proj);
        proj.add(release);
        children.clear();
        releasesCategory = new ReleaseCategory("Releases", proj);
        children.add(releasesCategory);
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
}