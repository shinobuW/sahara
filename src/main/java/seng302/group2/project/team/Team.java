/*
 * SENG302 Group 2
 */
package seng302.group2.project.team;

import java.io.Serializable;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A basic class to represent a Team in the real world.
 * @author crw73
 */
public class Team extends TreeViewItem implements Serializable
{
    public static String birthDatePattern = "dd/MM/yyyy";
    
    private String shortName;
    private String description;
    
    
    /**
     * Basic Team constructor
     */
    public Team()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "";
    }
    
    
    /**
     * Basic team constructor with all fields
     * @param shortName A unique short name to identify a Team
     * @param description of a Team
     */
    public Team(String shortName, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        this.shortName = shortName;
        this.description = description;
    }
       
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Gets the Team's short name
     * @return The short name of the team
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets the team's description
     * @return The description of the team
     */
    public String getDescription()
    {
        return this.description;
    }
    
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Sets the team's short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Gets the team's description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
        //</editor-fold>
    
    
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
     * An overridden version for the String representation of a Team
     * @return The short name of the Team
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
