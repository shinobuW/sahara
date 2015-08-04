package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * Created by swi67 on 10/05/15.
 */
public class ProjectInfoTab extends SearchableTab {
    ObservableList<Team> currentTeams = observableArrayList();

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for Project info tab
     * 
     * @param currentProject currently selected project
     */
    public ProjectInfoTab(Project currentProject) {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        SearchableText title = new SearchableTitle(currentProject.getLongName(), searchControls);

        Button btnEdit = new Button("Edit");


        this.setOnSelectionChanged(event -> {
                currentTeams.clear();
                for (Team team : currentProject.getCurrentTeams()) {
                    currentTeams.add(team);
                }
            });

        Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new SearchableText("Short Name: " + currentProject.getShortName(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Project Description: "
                + currentProject.getDescription(), searchControls));
        basicInfoPane.getChildren().add(separator);


        ListView projectTeamsBox = new ListView(currentTeams);
        projectTeamsBox.setPrefHeight(192);
        projectTeamsBox.setMaxHeight(150);
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ListView projectReleaseBox = new ListView(currentProject.getReleases());
        projectReleaseBox.setPrefHeight(192);
        projectReleaseBox.setMaxHeight(150);

        HBox listBoxes = new HBox(12);
        VBox teamsBox = new VBox();
        VBox releaseBox = new VBox();
        teamsBox.getChildren().addAll(new SearchableText("Current Teams:", searchControls), projectTeamsBox);
        releaseBox.getChildren().addAll(new SearchableText("Releases:", searchControls), projectReleaseBox);
        listBoxes.getChildren().addAll(teamsBox, releaseBox);

        basicInfoPane.getChildren().add(listBoxes);


        basicInfoPane.getChildren().add(btnEdit);
        btnEdit.setOnAction((event) -> {
                currentProject.switchToInfoScene(true);
            });

    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
