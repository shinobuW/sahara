package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.release.ReleaseEditTab;
import seng302.group2.scenes.information.project.release.ReleaseInfoTab;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the Backlog Scene.
 *
 * Created by cvs20 on 19/05/15.
 */
public class BacklogScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    Backlog currentBacklog;
    boolean editScene = false;

    SearchableTab informationTab;
    SearchableTab editTab;
    
    /**
     * Constructor for the Backlog scene
     * 
     * @param currentBacklog The currently selected Backlog
     */
    public BacklogScene(Backlog currentBacklog) {
        super(ContentScene.BACKLOG, currentBacklog.getProject());

        this.currentBacklog = currentBacklog;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the BacklogScene. This creates an instance of the BacklogEditTab tab and displays it.
     *
     * @param currentBacklog The currently selected Backlog
     * @param editScene a boolean - if the scene is an edit scene
     */
    public BacklogScene(Backlog currentBacklog, boolean editScene) {
        super(ContentScene.BACKLOG_EDIT, currentBacklog.getProject());

        this.currentBacklog = currentBacklog;
        this.editScene = editScene;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of tabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }


    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();

        if (editScene) {
            if (editTab != selectedTab) {
                editTab = new BacklogEditTab(currentBacklog);
            }
        }
        else {
            if (informationTab != selectedTab) {
                informationTab = new BacklogInfoTab(currentBacklog);
            }
        }
    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new BacklogEditTab(currentBacklog);
        }
        else {
            informationTab = new BacklogInfoTab(currentBacklog);
        }
    }
}
