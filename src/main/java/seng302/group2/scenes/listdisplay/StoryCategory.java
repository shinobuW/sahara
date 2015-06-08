package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import seng302.group2.workspace.project.Project;

/**
 * Created by drm127 on 17/05/15.
 */
public class StoryCategory extends Category {
    private Project project;

    /**
     * Basic constructor for a TreeView category
     *
     * @param name    The name of the category
     * @param project The parent project of this release category
     */
    public StoryCategory(String name, Project project) {
        super(name);
        this.project = project;
    }

    /**
     * Gets the story category's project
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
        return project.getUnallocatedStories();
    }

    /**
     * Overrides that a story category is equal if it has the same children
     *
     * @param obj the object to compare
     * @return if the objects are equal/equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StoryCategory)) {
            return false;
        }
        if (((StoryCategory) obj).getChildren() == this.getChildren()
                && ((StoryCategory) obj).project == this.project
                && obj.toString().equals(this.toString())) {
            return true;
        }
        return false;
    }
}
