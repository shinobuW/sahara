/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.role;

import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.io.Serializable;

/**
 *
 * @author swi67
 */
public class Role extends TreeViewItem implements Serializable
{
    private String shortName;
    private String description;
    private RoleType type;
    
    /**Basic Role constructor
     */
    public Role()
    {
        // Initialize as a TreeViewItem
        super("unnamed");
        this.shortName = "role Name";
        this.description = "";
        this.type = RoleType.Others;
    }
    
    /**Role Constructor 
     * 
     * @param shortName short name to be set 
     * @param type type of role to be set
     */
    public Role(String shortName, RoleType type)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.type = type;
    }
    
    /**Role Constructor
     * @param shortName short name to be set
     * @param type type of role to be set
     * @param description brief description of role
     */
    public Role(String shortName, RoleType type, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.type = type;
    }
    

    // <editor-fold defaultstate="collapsed" desc="Getters">
     /**
     * Gets the Role's short name
     * @return The short name of the role
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
     /**
     * Gets the Role's description
     * @return The description name of the description
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Gets the type of role
     * @return Type of role
     */
    public RoleType getType()
    {
        return this.type;
    }
    
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
    * Sets the role's short name
    * @param shortName the short name to set
    */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
    * Sets the role's short name
    * @param description the short name to set
    */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
    * Sets the type of role
    * @param type RoleType to set
    */
    public void setType(RoleType type)
    {
        this.type = type;
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
    
    @Override
    public String toString()
    {
        return this.shortName;
    }
}