package seng302.group2.workspace.tag;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.SaharaItem;

import java.util.HashSet;

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
}