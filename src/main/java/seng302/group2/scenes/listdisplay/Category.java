/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import seng302.group2.Global;

/**
 *
 * @author Jordane
 */
public class Category extends TreeViewItem
{
    private String name;
    
    /**
     * Basic constructor for a TreeView category
     * @param name The name of the category
     */
    public Category(String name)
    {
        super(name);
        this.name = name;
    }
    
    
    /**
     * An overridden version for the String representation of a Person
     * @return The short name of the Person
     */
    @Override
    public String toString()
    {
        return this.name;
    }
    
    
    /**
     * Gets the children of the category
     * @return the children of the category
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        switch (name)
        {
            case "People":
                return Global.currentProject.getPeople();
            case "Skills":
                return Global.currentProject.getSkills();
            case "Teams":
                return Global.currentProject.getTeams();
            default:
                return null;
        }
    }
}
