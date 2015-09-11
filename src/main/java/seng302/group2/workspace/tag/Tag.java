package seng302.group2.workspace.tag;

import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import seng302.group2.workspace.SaharaItem;

import java.util.HashSet;
import java.util.Set;

/**
 * A class representation of tags, used to highlight and identify different states of other Sahara model objects.
 */
public class Tag extends SaharaItem {

    private String name;
    private Color color = Color.ROYALBLUE;


    /**
     * Basic constructor for a Tag
     * @param tagName The name of the tag
     */
    Tag(String tagName) {
        this.name = tagName;
    }


    /**
     * Sets the colour of the tag for use on the visual tag nodes
     * @param color The colour to set the tag
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Gets the colour of the tag for use on the visual tag nodes
     * @return The colour of the tag
     */
    public Color getColor() {
        return color;
    }


    /**
     * SaharaItems 'belonging' to the Tag
     *
     * @return Tag empty set as tags do not have any items belonging to
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }


    /**
     * Method for creating an XML element for the Log within report generation
     *
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        return null;
    }


    /**
     * Overridden method for displaying the tag as a string
     * @return The name of the tag
     */
    @Override
    public String toString() {
        return name;
    }
}