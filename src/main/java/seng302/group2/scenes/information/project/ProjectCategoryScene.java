package seng302.group2.scenes.information.project;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the project category. Contains information
 * about all the projects in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class ProjectCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the ProjectCategoryScene class. Creates a tab
     * of ProjectCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public ProjectCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.PROJECT_CATEGORY, currentWorkspace);

        // Define and add the tabs
        Tab categoryTab = new ProjectCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}
