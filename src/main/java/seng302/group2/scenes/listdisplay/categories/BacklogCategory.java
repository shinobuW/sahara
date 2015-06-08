package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.workspace.project.Project;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogCategory extends Category {
    private Project project;

    /**
     * Basic constructor for a TreeView category
     *
     * @param name    The name of the category
     * @param project The parent project of this release category
     */
    public BacklogCategory(String name, Project project) {
        super(name);
        this.project = project;
    }

    /**
     * Gets the Backlogs category's project
     *
     * @return the story category's project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the children of the category
     *
     * @return the children of the category
     */
    @Override
    public ObservableList getChildren() {
        if (project == null) {
            return null;
        }
        return project.getBacklogs();
    }

    /**
     * Overrides that a backlog category is equal if it has the same children
     *
     * @param obj the object to compare
     * @return if the objects are equal/equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BacklogCategory)) {
            return false;
        }
        if (((BacklogCategory) obj).getChildren() == this.getChildren()
                && ((BacklogCategory) obj).project == this.project
                && obj.toString().equals(this.toString())) {
            return true;
        }
        return false;
    }
}
