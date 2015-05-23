package seng302.group2.scenes.listdisplay;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;

/**
 * Created by David on 17/05/2015.
 */
public class StoryCategoryTest
{
    /**
     * Tests that a story category's project is returned correctly
     */
    @Test
    public void testGetProject()
    {
        Project proj = new Project();
        StoryCategory category = new StoryCategory("Stories", proj);
        StoryCategory category2 = new StoryCategory("Stories", null);

        Assert.assertEquals(proj, category.getProject());
        Assert.assertEquals(null, category2.getProject());
    }

    /**
     * Tests the children of a story category are returned correctly
     */
    @Test
    public void testGetChildren()
    {
        Project proj = new Project();
        StoryCategory category = new StoryCategory("Stories", proj);
        Assert.assertEquals(proj.getUnallocatedStories(), category.getProject().getUnallocatedStories());

        Story story = new Story();
        proj.add(story);
        Assert.assertEquals(proj.getUnallocatedStories(), category.getProject().getUnallocatedStories());
    }

    /**
     * Tests the equivalence of two release categories are as expected
     */
    @Test
    public void testEquals()
    {
        Project proj = new Project();
        StoryCategory category = new StoryCategory("Stories", proj);
        StoryCategory category2 = new StoryCategory("Stories", null);
        StoryCategory category3 = new StoryCategory("Story", proj);
        StoryCategory category4 = new StoryCategory("Stories", proj);

        Assert.assertEquals(category, category4);
        Assert.assertNotEquals(category, category2);
        Assert.assertNotEquals(category3, category2);
        Assert.assertNotEquals(category, category3);
        Assert.assertNotEquals(category, "Story");
    }
}