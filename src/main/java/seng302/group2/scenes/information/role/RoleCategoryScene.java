package seng302.group2.scenes.information.role;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the role category. Contains information
 * about all the roles in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class RoleCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the RoleCategoryScene class. Creates a tab
     * of RoleCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public RoleCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.ROLE_CATEGORY, currentWorkspace);

        // Define and add the tabs
        Tab categoryTab = new RoleCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}