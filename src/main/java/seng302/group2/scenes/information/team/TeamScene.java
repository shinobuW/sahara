package seng302.group2.scenes.information.team;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the Team Scene
 *
 * @author crw73
 */
public class TeamScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    /**
     * Constructor for the Team scene. Creates an instance of the TeamInfoTab and displays it. If the team is not
     * the unassigned team, also creates an instance of the TeamHistoryTab to display.
     * 
     * @param currentTeam The currently selected Team.
     */
    public TeamScene(Team currentTeam) {
        super(ContentScene.TEAM, currentTeam);

        // Define and add the tabs
        SearchableTab informationTab = new TeamInfoTab(currentTeam);
        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(searchableTabs);

        if (!currentTeam.isUnassignedTeam()) {
            SearchableTab historyTab = new TeamHistoryTab(currentTeam);
            this.getTabs().addAll(historyTab);  // Add the tabs to the pane
            Collections.addAll(searchableTabs, historyTab);
        }
    }

    /**
     * Constructor for the PersonScene. This should only used to display an edit tab.
     * @param currentTeam the team who will be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public TeamScene(Team currentTeam, boolean editScene) {
        super(ContentScene.TEAM_EDIT, currentTeam);

        // Define and add the tabs
        SearchableTab editTab = new TeamEditTab(currentTeam);
        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(editTab);

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