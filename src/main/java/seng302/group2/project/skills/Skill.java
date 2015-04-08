/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.skills;

import java.io.Serializable;
import java.util.Date;
import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 *
 * @author crw73
 */
public class Skill extends TreeViewItem implements Serializable
{
    
    private String shortName;
    private String description;
    
    /**
    * Basic Skill constructor
    */
    public Skill()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "no description";
       
    }
    
     /**
     * Basic Skill constructor with all fields
     * @param shortName A unique short name to identify a Skill
     * @param description The Description of a skill
     */
    public Skill(String shortName, String firstName, String lastName, String email, 
            String description, Date birthDate)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        String newShortName = validateShortName(shortName);
        
        // Continue constructing
        this.shortName = newShortName;
        this.description = description;
    }
        
    // <editor-fold defaultstate="collapsed" desc="Getters">

    
    /**
     * Gets a skills short name
     * @return The short name of the skill
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets a skills description
     * @return The description of the skill
     */
    public String getDescription()
    {
        return this.description;
    }
  
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    
     /**
     * Sets a skills short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
  
    /**
     * Gets a skills description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
  
    //</editor-fold>
    
    public String validateShortName(String shortName) 
    {
        String newShortName = shortName;
        int i = 0;
        for (TreeViewItem person : Global.currentProject.getPeople())
        {
            if (person.toString().equals(newShortName))
            {
                i++;
                newShortName = shortName + "~" + String.valueOf(i);
            }
        }
        return newShortName;
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
     * An overridden version for the String representation of a Skill
     * @return The short name of the Skill
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
    
}
