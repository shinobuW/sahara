package seng302.group2.scenes.information.role;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying all roles currently created in a workspace.
 *
 * @author David Moseley
 */
public class RoleCategoryScene extends TrackedTabPane {
    public RoleCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.ROLE_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new RoleCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}