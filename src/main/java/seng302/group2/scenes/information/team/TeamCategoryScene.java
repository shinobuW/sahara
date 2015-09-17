package seng302.group2.scenes.information.team;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the team category. Contains information
 * about all the teams in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class TeamCategoryScene extends TrackedTabPane {

    /**
     * Constructor for the TeamCategoryScene class. Creates a tab
     * of TeamCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public TeamCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.TEAM_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new TeamCategoryTab(currentWorkspace);
        Collections.addAll(getSearchableTabs(), categoryTab);

        this.getTabs().addAll(getSearchableTabs());
    }
}