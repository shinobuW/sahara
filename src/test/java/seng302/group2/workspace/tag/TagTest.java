package seng302.group2.workspace.tag;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
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
        new ReportGenerator();
        Tag tag = new Tag("hash");

        Element tagElement = tag.generateXML();
        Assert.assertEquals("[hash: null]", tagElement.toString());
    }

    @Test
    public void testToString() throws Exception {
        Tag tag = new Tag("hash");
        Tag tag2 = new Tag("tag");
        Assert.assertEquals("hash", tag.toString());
        Assert.assertEquals("tag", tag2.toString());
    }

    /**
     * Tests the deletion of a tag globally. //TODO Bronson Add in items with the tag, then delete it.
     */
    @Test
    public void testDeleteGlobalTag() {
        Tag tag = new Tag("New tag");

        tag.deleteGlobalTag();

        Assert.assertFalse(Global.currentWorkspace.getAllTags().contains(tag));

        Global.commandManager.undo();

        Assert.assertTrue(Global.currentWorkspace.getAllTags().contains(tag));

        Global.commandManager.redo();

        Assert.assertFalse(Global.currentWorkspace.getAllTags().contains(tag));
    }
}