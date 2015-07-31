package seng302.group2.scenes.information.team;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.team.Team;

/**
 * A class for displaying the Team Scene
 *
 * @author crw73
 */
public class TeamScene extends TrackedTabPane {
    
    /**
     * Constructor for the Team scene. Creates an instance of the TeamInfoTab and displays it. If the team is not
     * the unassigned team, also creates an instance of the TeamHistoryTab to display.
     * 
     * @param currentTeam The currently selected Team.
     */
    public TeamScene(Team currentTeam) {
        super(ContentScene.TEAM, currentTeam);

        // Define and add the tabs
        Tab informationTab = new TeamInfoTab(currentTeam);
        this.getTabs().addAll(informationTab);

        if (!currentTeam.isUnassignedTeam()) {
            Tab historyTab = new TeamHistoryTab(currentTeam);
            this.getTabs().addAll(historyTab);  // Add the tabs to the pane
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
        Tab editTab = new TeamEditTab(currentTeam);
        this.getTabs().addAll(editTab);

    }
}