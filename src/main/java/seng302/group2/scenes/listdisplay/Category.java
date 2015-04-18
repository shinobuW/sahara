package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import seng302.group2.Global;

/**
 * Category item in the list display, uses as a header for all the elements of one type, ie 
 * "People" for all persons within the workspace.
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
            case "Projects":
                return Global.currentWorkspace.getProjects();
            case "People":
                return Global.currentWorkspace.getPeople();
            case "Skills":
                return Global.currentWorkspace.getSkills();
            case "Teams":
                return Global.currentWorkspace.getTeams();
            default:
                return null;
        }
    }
}
