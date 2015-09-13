package seng302.group2.workspace.tag;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;

import java.util.HashSet;

/**
 * A series of tests relating to Workspaces
 *
 * @author Bronson McNaughton (btm38)
 */
public class TagTest {

    @Test
    public void testSetGetColor() throws Exception {
        Tag tag = new Tag("hash");
        Assert.assertEquals(Color.ROYALBLUE, tag.getColor());
    }

    @Test
    public void testGetItemsSet() throws Exception {
        Tag tag = new Tag("hash");
        Assert.assertEquals(new HashSet<SaharaItem>(), tag.getItemsSet());
    }

    @Test
    public void testGenerateXML() throws Exception {
        Tag tag = new Tag("hash");
        Assert.assertEquals(null, tag.generateXML());
    }

    @Test
    public void testToString() throws Exception {
        Tag tag = new Tag("hash");
        Tag tag2 = new Tag("tag");
        Assert.assertEquals("hash", tag.toString());
        Assert.assertEquals("tag", tag2.toString());
    }

    /**
     * Tests the deletion of a tag globally. //TODO Add in items with the tag, then delete it.
     */
    @Test
    public void testDeleteGlobalTag() {
        Tag tag = new Tag("New tag");

        tag.deleteGlobalTag();
        Assert.assertFalse(Global.currentWorkspace.getGlobalTags().contains(tag));

        Global.commandManager.undo();

        Assert.assertTrue(Global.currentWorkspace.getGlobalTags().contains(tag));

        Global.commandManager.redo();

        Assert.assertFalse(Global.currentWorkspace.getGlobalTags().contains(tag));
    }
}