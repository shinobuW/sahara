package seng302.group2.workspace.project;

import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.io.Serializable;

/**
 * A class representing real-world projects
 * Created by Jordane on 18/04/2015.
 */
public class Project extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;


    /**
     * Basic Workspace constructor.
     */
    public Project()
    {
        super("Untitled Project");
        this.shortName = "Untitled Project";
        this.longName = "Untitled Project";
        this.description = "A blank project.";
    }


    /**
     * Basic project constructor with input.
     * @param shortName A unique short name to identify the Project
     * @param fullName The full Project name
     * @param description A description of the Project
     */
    public Project(String shortName, String fullName, String description)
    {
        super(shortName);
        this.shortName = shortName;
        this.longName = fullName;
        this.description = description;
    }


    /**
     * Gets the short name of the project
     * @return Short name of the project
     */
    public String getShortName()
    {
        return shortName;
    }


    /**
     * Sets the short name of the project
     * @param shortName The short name to set to the project
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }


    /**
     * Gets the long name of the project
     * @return The long name of the project
     */
    public String getLongName()
    {
        return longName;
    }


    /**
     * Sets the long name of the project
     * @param longName The long name to set to the project
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }


    /**
     * Gets the description of the project
     * @return The description of the project
     */
    public String getDescription()
    {
        return description;
    }


    /**
     * Sets the description of the project
     * @param description The description to set to the project
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}
