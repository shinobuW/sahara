package seng302.group2.scenes.listdisplay.categories.subCategory.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.group2.scenes.dialog.CreateBacklogDialog;
import seng302.group2.scenes.listdisplay.categories.subCategory.SubCategory;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project.BacklogCategorySwitchStrategy;
import seng302.group2.workspace.project.Project;

/**
 * THe category item for Backlogs (shown under each projects)
 * Created by cvs20 on 19/05/15.
 */
public class BacklogCategory extends SubCategory {

    /**
     * Basic constructor for a TreeView category
     *
     * @param project The parent project of this release category
     */
    public BacklogCategory(Project project) {
        super("Backlogs", project);
        setCategorySwitchStrategy(new BacklogCategorySwitchStrategy());
    }

    /**
     * Gets the Backlogs category's project
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
        return ((Project) parent).getBacklogs();
    }

    @Override
    public void showCreationDialog() {
        CreateBacklogDialog.show();
    }
}
