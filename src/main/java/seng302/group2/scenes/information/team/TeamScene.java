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
    public TeamScene(Team currentTeam) {
        super(ContentScene.TEAM);

        // Define and add the tabs
        Tab informationTab = new TeamInfoTab(currentTeam);
        this.getTabs().addAll(informationTab);

        if (!currentTeam.isUnassignedTeam()) {
            Tab historyTab = new TeamHistoryTab(currentTeam);
            this.getTabs().addAll(historyTab);  // Add the tabs to the pane
        }
    }
}