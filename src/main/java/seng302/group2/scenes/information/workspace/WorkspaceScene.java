package seng302.group2.scenes.information.workspace;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class WorkspaceScene extends TrackedTabPane {

    Workspace currentWorkspace;
    boolean editScene = false;

    SearchableTab informationTab;
    SearchableTab editTab;

    /**
     * Constructor for the Workspace Scene. Creates an instance of the WorkspaceInfoTab and displays it.
     * 
     * @param currentWorkspace The currently selected Workspace. 
     */
    public WorkspaceScene(Workspace currentWorkspace) {
        super(ContentScene.WORKSPACE, currentWorkspace);

        this.currentWorkspace = currentWorkspace;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(getSearchableTabs(), informationTab);
        this.getTabs().addAll(getSearchableTabs());  // Add the tabs to the pane
    }

    /**
     * Constructor for the Workspace Scene. This creates an instance of the WorkspaceEditTab tab and displays it.
     *
     * @param currentWorkspace the person who will be edited
     * @param editScene boolean - if the scene if an edit scene
     */
    public WorkspaceScene(Workspace currentWorkspace, boolean editScene) {
        super(ContentScene.WORKSPACE_EDIT, currentWorkspace);

        this.editScene = editScene;
        this.currentWorkspace = currentWorkspace;

        // Define and add the tabs
        updateAllTabs();
        Collections.addAll(getSearchableTabs(), editTab);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }

    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();

        if (editScene) {
            if (editTab != selectedTab) {
                editTab = new WorkspaceEditTab(currentWorkspace);
            }
        }
        else {
            if (informationTab != selectedTab) {
                informationTab = new WorkspaceInfoTab(currentWorkspace);
            }
        }
    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new WorkspaceEditTab(currentWorkspace);
        }
        else {
            informationTab = new WorkspaceInfoTab(currentWorkspace);
        }
    }
}
