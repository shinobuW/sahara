package seng302.group2.scenes.listdisplay;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

/**
 * Created by Jordane on 27/04/2015.
 */
public class ReleaseCategoryTest
{

    /**
     * Tests that a release category's project is returned correctly
     */
    @Test
    public void testGetProject()
    {
        Project proj = new Project();
        ReleaseCategory category = new ReleaseCategory("Releases", proj);
        ReleaseCategory category2 = new ReleaseCategory("Releases", null);

        Assert.assertEquals(proj, category.getProject());
        Assert.assertEquals(null, category2.getProject());
    }

    /**
     * Tests the children of a release category are returned correctly
     */
    @Test
    public void testGetChildren()
    {
        Project proj = new Project();
        ReleaseCategory category = new ReleaseCategory("Releases", proj);
        Assert.assertEquals(proj.getReleases(), category.getProject().getReleases());

        Release release = new Release();
        proj.add(release);
        Assert.assertEquals(proj.getReleases(), category.getProject().getReleases());
    }

    /**
     * Tests the equivalence of two release categories are as expected
     */
    @Test
    public void testEquals()
    {
        Project proj = new Project();
        ReleaseCategory category = new ReleaseCategory("Releases", proj);
        ReleaseCategory category2 = new ReleaseCategory("Releases", null);
        ReleaseCategory category3 = new ReleaseCategory("Release", proj);
        ReleaseCategory category4 = new ReleaseCategory("Releases", proj);

        Assert.assertEquals(category, category4);
        Assert.assertNotEquals(category, category2);
        Assert.assertNotEquals(category3, category2);
        Assert.assertNotEquals(category, category3);
        Assert.assertNotEquals(category, "Releases");
    }
}