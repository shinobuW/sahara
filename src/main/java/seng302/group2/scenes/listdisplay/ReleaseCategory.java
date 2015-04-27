package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Category item in the list display, uses as a header for all the elements of one type, ie 
 * "People" for all persons within the workspace.
 * @author Jordane
 */
public class ReleaseCategory extends Category
{
    private Project project;

    /**
     * Basic constructor for a TreeView category
     * @param name The name of the category
     */
    public ReleaseCategory(String name, Project project)
    {
        super(name);
        this.project = project;
    }
    
    
    /**
     * Gets the children of the category
     * @return the children of the category
     */
    @Override
    public ObservableList getChildren()
    {
        return project.getReleases();
    }


    /**
     * Overrides that a release category is equal if it has the same children
     * @param obj the object to compare
     * @return if the objects are equal/equivalent
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ReleaseCategory))
        {
            return false;
        }
        if (((ReleaseCategory) obj).getChildren() == this.getChildren()
                && ((ReleaseCategory) obj).project == this.project
                && obj.toString().equals(this.toString()))
        {
            return true;
        }
        return false;
    }
}
