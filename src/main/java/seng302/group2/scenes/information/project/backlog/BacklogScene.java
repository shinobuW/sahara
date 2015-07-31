package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.backlog.Backlog;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the Backlog Scene.
 *
 * Created by cvs20 on 19/05/15.
 */
public class BacklogScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();
    
    /**
     * Constructor for the Backlog scene
     * 
     * @param currentBacklog The currently selected Backlog
     */
    public BacklogScene(Backlog currentBacklog) {
        super(ContentScene.BACKLOG, currentBacklog.getProject());

        // Define and add the tabs
        Tab informationTab = new BacklogInfoTab(currentBacklog);
        Tab scaleInfoTab = new StoryEstimationScaleInfoTab();
        Tab testTab = new TestingCellTestTab(currentBacklog);

        this.getTabs().addAll(informationTab, scaleInfoTab, testTab);  // Add the tabs to the pane
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

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
