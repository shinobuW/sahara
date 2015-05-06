package seng302.group2.workspace.story;

import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.io.Serializable;
import javafx.collections.ObservableList;

/**
 * Created by swi67 on 6/05/15.
 */
public class Story extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String creator;

    /**
     * Basic Story constructor
     */
    public Story()
    {
        this.shortName = "Untitled Story";
        this.longName = "Untitled Story";
        this.description = "";
        this.creator = null;
    }

    /**
     * Stort Constructor with all fields
     */
    public Story(String shortName, String longName, String description, String creator)
    {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.creator = creator;
    }

    /**
     * Gets the short name of the story
     * @return the short name
     */
    public String getShortName()
    {
        return this.shortName;
    }

    /**
     * Gets the long name of the story
     * @return the long name
     */
    public String getLongName()
    {
        return this.longName;
    }

    /**
     * Gets the description of the story
     * @return descirption
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the person who created the story
     * @return name of the person
     */
    public String getCreator()
    {
        return this.creator;
    }

    /**
     * Sets the short name of the story
     * @param shortName short name to be set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Sets the long name of the story
     * @param longName long name to be set
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    /**
     * Sets the description of the story
     * @param description description to be set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the person who created the story
     * @param creator name of the person to be set.
     */
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    /**
     * Gets the children of the TreeViewItem
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }
    
    /**
     * An overridden version for the String representation of a Story
     * @return The short name of the Story
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
