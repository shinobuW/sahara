package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;

/**
 * Category item in the list display, uses as a header for all the elements of one type, ie 
 * "People" for all persons within the workspace.
 * @author Jordane
 */
public abstract class Category extends TreeViewItem
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
    public abstract ObservableList getChildren();
    /*{
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
            case "Roles":
                return Global.currentWorkspace.getRoles();
            default:
                return null;
        }
    }*/

    /**
     * Overrides that a category is equal if it has the same children
     * @param obj the object to compare
     * @return if the objects are equal/equivalent
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Category))
        {
            return false;
        }
        return ((Category) obj).getChildren() == this.getChildren()
                && ((Category) obj).name.equals(this.name);
    }
}
