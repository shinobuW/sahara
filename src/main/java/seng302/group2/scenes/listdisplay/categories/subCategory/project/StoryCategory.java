package seng302.group2.scenes.listdisplay.categories.subCategory.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.scenes.listdisplay.categories.subCategory.SubCategory;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project.StoryCategorySwitchStrategy;
import seng302.group2.workspace.project.Project;

/**
 * Created by drm127 on 17/05/15.
 */
public class StoryCategory extends SubCategory {

    /**
     * Basic constructor for a TreeView category
     *
     * @param project The parent project of this release category
     */
    public StoryCategory(Project project) {
        super("Unassigned Stories", project);
        setCategorySwitchStrategy(new StoryCategorySwitchStrategy());
    }

    /**
     * Gets the story category's project
     *
     * @return the story category's project
     */
    public Project getProject() {
        return (Project) parent;
    }

    /**
     * Gets the children of the category
     *
     * @return the children of the category
     */
    @Override
    public ObservableList getChildren() {
        if (parent == null) {
            return FXCollections.observableArrayList();
        }
        return ((Project) parent).getUnallocatedStories();
    }

    @Override
    public void showCreationDialog() {
        CreateStoryDialog.show();
    }
}
