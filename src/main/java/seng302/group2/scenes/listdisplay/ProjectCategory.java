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
public class ProjectCategory extends Category
{
    private String name;
    private Project project;

    /**
     * Basic constructor for a TreeView category
     * @param name The name of the category
     */
    public ProjectCategory(String name, Project project)
    {
        super(name);
        this.name = name;
        this.project = project;
    }
    
    
    /**
     * Gets the children of the category
     * @return the children of the category
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        ObservableList<TreeViewItem> children = observableArrayList();

        for (Release release : this.project.getReleases())
        {
            children.add(release);
        }

        return children;
    }
}
