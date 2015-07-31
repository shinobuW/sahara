package seng302.group2.scenes.information.team;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the team category. Contains information
 * about all the teams in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class TeamCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the TeamCategoryScene class. Creates a tab
     * of TeamCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public TeamCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.TEAM_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new TeamCategoryTab(currentWorkspace);
        Collections.addAll(searchableTabs, categoryTab);

        this.getTabs().addAll(searchableTabs);
    }

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}