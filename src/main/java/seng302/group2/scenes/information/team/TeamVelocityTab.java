package seng302.group2.scenes.information.team;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.scenes.control.search.TagLabel;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by cvs20 on 24/09/15.
 */
public class TeamVelocityTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    Team currentTeam;

    /**
     * Constructor for team basic information tab
     *
     * @param currentTeam currently selected team
     */
    public TeamVelocityTab(Team currentTeam) {
        this.currentTeam = currentTeam;
        construct();
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Tab settings
        this.setText("Team Velocity");

        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);



        basicInfoPane.getChildren().addAll();
        Collections.addAll(searchControls);

    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Team Velocity Tab";
    }

}
