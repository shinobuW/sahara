package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * Created by swi67 on 10/05/15.
 */
public class ProjectInfoTab extends Tab
{
    ObservableList<Team> currentTeams = observableArrayList();

    public ProjectInfoTab(Project currentProject)
    {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        Label title = new TitleLabel(currentProject.getLongName());

        Button btnEdit = new Button("Edit");


        this.setOnSelectionChanged(event ->
            {
                currentTeams.clear();
                for (Team team : currentProject.getCurrentTeams())
                {
                    currentTeams.add(team);
                }
            });

        Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: " + currentProject.getShortName()));
        basicInfoPane.getChildren().add(new Label("Project Description: "));

        basicInfoPane.getChildren().add(new Label(currentProject.getDescription()));

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
        teamsBox.getChildren().addAll(new Label("Teams:"), projectTeamsBox);
        releaseBox.getChildren().addAll(new Label("Releases:"), projectReleaseBox);
        listBoxes.getChildren().addAll(teamsBox, releaseBox);

        basicInfoPane.getChildren().add(listBoxes);


        basicInfoPane.getChildren().add(btnEdit);
        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT_EDIT, currentProject);
            });

    }
}
