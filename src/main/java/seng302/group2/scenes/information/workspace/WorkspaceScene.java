package seng302.group2.scenes.information.workspace;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.workspace.Workspace;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class WorkspaceScene extends TrackedTabPane {
    public WorkspaceScene(Workspace currentWorkspace) {
        super(SceneSwitcher.ContentScene.WORKSPACE);

        // Define and add the tabs
        Tab informationTab = new WorkspaceInfoTab(currentWorkspace);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
