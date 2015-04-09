/*
 * SENG302 Group 2
 */
package seng302.group2.project.team;

import java.io.Serializable;
import java.util.Date;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A basic class to represent a Person in the real world.
 * @author crw73
 */
public class Team extends TreeViewItem implements Serializable
{
    public static String birthDatePattern = "dd/MM/yyyy";
    
    private String shortName;
    private String description;
    
    
    /**
     * Basic Person constructor
     */
    public Team()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "";
    }
    
    
    /**
     * Basic person constructor with all fields
     * @param shortName A unique short name to identify a Person
     * @param firstName The first name of the Person
     * @param lastName The last name of the Person
     * @param email The email of the Person
     * @param birthDate A description of the Person
     * @param description The date of birth of a Person
     */
    public Team(String shortName, String descriptionDate)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        this.shortName = shortName;
        this.description = description;
    }
       
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Gets the person's short name
     * @return The short name of the person
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets the person's description
     * @return The description of the person
     */
    public String getDescription()
    {
        return this.description;
    }
    
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Sets the person's short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Gets the person's description
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
     * An overridden version for the String representation of a Person
     * @return The short name of the Person
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
