package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * The person information tab.
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
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle(currentProject.getLongName(), searchControls);

        Separator separator = new Separator();
        Button btnEdit = new Button("Edit");


        this.setOnSelectionChanged(event -> {
                currentTeams.clear();
                for (Team team : currentProject.getCurrentTeams()) {
                    currentTeams.add(team);
                }
            });

        SearchableText shortNameField = new SearchableText("Short Name: " + currentProject.getShortName());
        SearchableText description = new SearchableText("Project Description: " + currentProject.getDescription());
        SearchableText currentTeamsLabel = new SearchableText("Current Teams:");
        SearchableText releasesLabel = new SearchableText("Releases:");



        SearchableListView projectTeamsBox = new SearchableListView<>(currentTeams);
        projectTeamsBox.setPrefHeight(192);
        projectTeamsBox.setMaxHeight(150);
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        SearchableListView projectReleaseBox = new SearchableListView<>(currentProject.getReleases());
        projectReleaseBox.setPrefHeight(192);
        projectReleaseBox.setMaxHeight(150);

        HBox listBoxes = new HBox(12);
        VBox teamsBox = new VBox();
        VBox releaseBox = new VBox();
        teamsBox.getChildren().addAll(currentTeamsLabel, projectTeamsBox);
        releaseBox.getChildren().addAll(releasesLabel, projectReleaseBox);
        listBoxes.getChildren().addAll(teamsBox, releaseBox);

        // Events
        btnEdit.setOnAction((event) -> {
                currentProject.switchToInfoScene(true);
            });

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortNameField,
                description,
                separator,
                listBoxes,
                btnEdit
        );

        Collections.addAll(searchControls,
                title,
                shortNameField,
                description,
                currentTeamsLabel,
                projectTeamsBox,
                releasesLabel,
                projectReleaseBox
        );
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
