package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * Created by swi67 on 10/05/15.
 */
public class ProjectInfoTab extends Tab
{
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

        ListView projectTeamsBox = new ListView(currentProject.getTeams());
        projectTeamsBox.setMaxHeight(150);
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Team> dialogTeams = observableArrayList();
        for (TreeViewItem projectTeam : Global.currentWorkspace.getTeams())
        {
            if (!((Team)projectTeam).isUnassignedTeam()
                    && !currentProject.getTeams().contains(projectTeam))
            {
                dialogTeams.add((Team)projectTeam);
            }
        }

        ListView projectReleaseBox = new ListView(currentProject.getReleases());
        projectReleaseBox.setMaxHeight(150);

        Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: "));
        basicInfoPane.getChildren().add(new Label("Project Description: "));

        basicInfoPane.getChildren().add(new Label(currentProject.getShortName()));
        basicInfoPane.getChildren().add(new Label(currentProject.getDescription()));

        basicInfoPane.getChildren().add(separator);
        basicInfoPane.getChildren().add(new Label("Teams: "));
        basicInfoPane.getChildren().add(projectTeamsBox);
        basicInfoPane.getChildren().add(new Label("Releases: "));
        basicInfoPane.getChildren().add(projectReleaseBox);

        basicInfoPane.getChildren().add(btnEdit);


        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT_EDIT, currentProject);
            });

    }
}
