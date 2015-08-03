package seng302.group2.scenes.information.project.backlog;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.story.TestingCellTestTab;
import seng302.group2.workspace.project.backlog.Backlog;

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
    
    /**
     * Constructor for the Backlog scene
     * 
     * @param currentBacklog The currently selected Backlog
     */
    public BacklogScene(Backlog currentBacklog) {
        super(ContentScene.BACKLOG, currentBacklog.getProject());

        // Define and add the tabs
        SearchableTab informationTab = new BacklogInfoTab(currentBacklog);
        SearchableTab scaleInfoTab = new StoryEstimationScaleInfoTab();

        Collections.addAll(searchableTabs, informationTab, scaleInfoTab);
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

        // Define and add the tabs
        SearchableTab editTab = new BacklogEditTab(currentBacklog);
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
}
