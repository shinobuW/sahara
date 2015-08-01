package seng302.group2.scenes.information.workspace;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class WorkspaceScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the Workspace Scene. Creates an instance of the WorkspaceInfoTab and displays it.
     * 
     * @param currentWorkspace The currently selected Workspace. 
     */
    public WorkspaceScene(Workspace currentWorkspace) {
        super(ContentScene.WORKSPACE, currentWorkspace);

        // Define and add the tabs
        SearchableTab informationTab = new WorkspaceInfoTab(currentWorkspace);
        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Workspace Scene. This creates an instance of the WorkspaceEditTab tab and displays it.
     *
     * @param currentWorkspace the person who will be edited
     * @param editScene boolean - if the scene if an edit scene
     */
    public WorkspaceScene(Workspace currentWorkspace, boolean editScene) {
        super(ContentScene.WORKSPACE_EDIT, currentWorkspace);

        // Define and add the tabs
        SearchableTab editTab = new WorkspaceEditTab(currentWorkspace);
        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }


    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of SearchableTabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
