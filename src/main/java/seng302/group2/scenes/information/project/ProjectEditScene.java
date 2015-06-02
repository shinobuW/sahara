package seng302.group2.scenes.information.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.Project;

import java.util.Collections;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the project edit scene.
 * Created by jml168 on 7/04/15.
 */
public class ProjectEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @param currentProject The project to display the editable information of
     * @return The Workspace Edit information scene
     */
    public static ScrollPane getProjectEditScene(Project currentProject)
    {
        informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description:", 300);

        shortNameCustomField.setMaxWidth(275);
        longNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        VBox teamButtons = new VBox();
        teamButtons.spacingProperty().setValue(10);
        teamButtons.getChildren().add(btnAdd);
        teamButtons.getChildren().add(btnDelete);
        teamButtons.setAlignment(Pos.CENTER);

        /*Project tempProject = new Project();
        for (Team team : currentProject.getTeams()) 
        {
            tempProject.addWithoutUndo(team);
        }
	
        ListView projectTeamsBox = new ListView(tempProject.getTeams());
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        projectTeamsBox.setMaxHeight(150);


        ObservableList<Team> availableTeams = observableArrayList();
        ObservableList<Team> addedTeams = observableArrayList();
	
	
        for (TreeViewItem projectTeam : Global.currentWorkspace.getTeams())
        {
            if (!((Team)projectTeam).isUnassignedTeam()
                    && !currentProject.getTeams().contains(projectTeam))
            {
                availableTeams.add((Team) projectTeam);
                addedTeams.add((Team) projectTeam);
            }
        }

        ListView membersBox = new ListView(availableTeams);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        membersBox.setMaxHeight(150);*/

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(longNameCustomField);
        informationPane.getChildren().add(descriptionTextArea);

        /*HBox h1 = new HBox(10);
        VBox v1 = new VBox(10);
        v1.getChildren().add(new Label("Teams: "));
        v1.getChildren().add(projectTeamsBox);

        VBox v2 = new VBox(10);
        v2.getChildren().add(new Label("Available Teams: "));
        v2.getChildren().add(membersBox);

        h1.getChildren().addAll(v1, teamButtons, v2);
        informationPane.getChildren().add(h1);*/
        informationPane.getChildren().add(buttons);

        btnSave.setOnAction((event) ->
            {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                    currentProject.getShortName());
                boolean longNameUnchanged = longNameCustomField.getText().equals(
                    currentProject.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                    currentProject.getDescription());

                // If no fields have been changed
                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged)
                {
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT, currentProject);
                    return;
                }

                boolean correctShortName = validateShortName(shortNameCustomField,
                    currentProject.getShortName());
                boolean correctLongName = validateName(longNameCustomField);

                if (correctShortName && correctLongName)
                {
                    currentProject.edit(shortNameCustomField.getText(),
                        longNameCustomField.getText(), descriptionTextArea.getText(),
                        observableArrayList()
                    );

                    Collections.sort(Global.currentWorkspace.getProjects());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT, currentProject);
                    MainScene.treeView.refresh();
                }
                else
                {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT, currentProject);
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }


    /**
     * Asks if a user is sure they want to move the team to a different project.
     * @param team The team to be moved
     * @param tempProject The project to move the team to
     */
    /*
    @Deprecated
    private static void teamCheckDialog(Team team, Project tempProject)
    {
        
        Dialog dialog = new Dialog(null, "Already Assigned to a Project");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
               
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnYes, btnNo);
        
        grid.getChildren().add(new Label("The team " + team.getShortName() + " is already assigned"
                + " to a project. Are you sure you want to move them to a different one?"));
        grid.getChildren().add(buttons);
        
        btnYes.setOnAction((event) ->
            {
                tempProject.addWithoutUndo(team);
                dialog.hide();
            });
        
        btnNo.setOnAction((event) ->
            {
                dialog.hide();
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }*/
}
