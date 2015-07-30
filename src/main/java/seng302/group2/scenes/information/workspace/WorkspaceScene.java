package seng302.group2.scenes.information.workspace;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class WorkspaceScene extends TrackedTabPane {
    
    /**
     * Constructor for the Workspace Scene. Creates an instance of the WorkspaceInfoTab and displays it.
     * 
     * @param currentWorkspace The currently selected Workspace. 
     */
    public WorkspaceScene(Workspace currentWorkspace) {
        super(ContentScene.WORKSPACE, currentWorkspace);

        // Define and add the tabs
        Tab informationTab = new WorkspaceInfoTab(currentWorkspace);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Workspace Scene. Creates an instance of the WorkspaceInfoTab and displays it.
     *
     * @param currentWorkspace The currently selected Workspace.
     */
    public WorkspaceScene(Workspace currentWorkspace, boolean editScene) {
        super(ContentScene.WORKSPACE_EDIT, currentWorkspace);

        // Define and add the tabs
        Tab editTab = new WorkspaceEditTab(currentWorkspace);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }
}
