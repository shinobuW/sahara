package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.scene.control.Label;
import org.controlsfx.dialog.Dialog;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the project edit scene.
 * Created by jml168 on 7/04/15.
 */
public class ProjectEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @return The Workspace Edit information scene
     */
    public static GridPane getProjectEditScene(Project currentProject)
    {
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField longNameCustomField = new RequiredField("Long Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description", 300);

        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        VBox teamButtons = new VBox();
        teamButtons.getChildren().add(btnAdd);
        teamButtons.getChildren().add(btnDelete);
        teamButtons.setAlignment(Pos.CENTER);

	Project tempProject = new Project();
        for (Team team : currentProject.getTeams()) 
        {
            tempProject.addTeam(team, false);
        }
	
        ListView projectTeamsBox = new ListView(tempProject.getTeams());
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        ObservableList<Team> dialogTeams = observableArrayList();
	ObservableList<Team> dialogTeamsCopy = observableArrayList();
	
	
        for (TreeViewItem projectTeam : Global.currentWorkspace.getTeams())
        {
            if (!((Team)projectTeam).isUnassignedTeam()
                    && !currentProject.getTeams().contains(projectTeam))
            {
                dialogTeams.add((Team)projectTeam);
            }
        }

        ListView membersBox = new ListView(dialogTeams);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(longNameCustomField, 0, 1);
        informationGrid.add(descriptionTextArea, 0, 2);
        informationGrid.add(projectTeamsBox, 0, 3);

        informationGrid.add(teamButtons, 1, 3);

        informationGrid.add(membersBox, 2, 3);
        informationGrid.add(buttons, 0, 4);

        btnAdd.setOnAction((event) ->
            {
                ObservableList<Team> selectedTeams =
                        membersBox.getSelectionModel().getSelectedItems();
                for (Team item : selectedTeams)
                {
		    if (item.getProject() == null) 
                    {
                        tempProject.addTeam(item, false);
                    }
                    else 
                    {
                        teamCheckDialog(item, tempProject);
                    }
                }

                dialogTeams.clear();
                for (TreeViewItem projectTeams : Global.currentWorkspace.getTeams())
                {
                    if (!tempProject.getTeams().contains((Team)projectTeams))
                    {
			if (!((Team)projectTeams).isUnassignedTeam()
				&& !currentProject.getTeams().contains(projectTeams))
			{
			    dialogTeams.add((Team)projectTeams);
			}
                    }
		    
                }
            });

        btnDelete.setOnAction((event) ->
            {
                ObservableList<Team> selectedTeams =
                        projectTeamsBox.getSelectionModel().getSelectedItems();
                System.out.println(selectedTeams.size());
                for (int i = selectedTeams.size() - 1; i >= 0 ; i--)
                {
                    tempProject.removeTeam(selectedTeams.get(i), false);
                }

                dialogTeams.clear();
                for (TreeViewItem projectTeams : Global.currentWorkspace.getTeams())
                {
                    if (!tempProject.getTeams().contains((Team)projectTeams))
                    {
                        dialogTeams.add((Team)projectTeams);
                    }
                }
            });

        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ProjectScene.getProjectScene((Project) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
              
                boolean correctShortName;
                boolean correctLongName;   
                
                if (shortNameCustomField.getText().equals(currentProject.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }
                
                if (longNameCustomField.getText().equals(currentProject.getLongName()))
                {
                    correctLongName = true;
                }
                else
                {
                    correctLongName = validateShortName(longNameCustomField);
                }

                if (correctShortName && correctLongName)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
                    if (shortNameCustomField.getText() != currentProject.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME,
                                        currentProject.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME,
                                        shortNameCustomField.getText())));
                    }

		    
                    if (longNameCustomField.getText() != currentProject.getLongName())
                    {
			System.out.println(longNameCustomField.getText());
			System.out.println(currentProject.getLongName());
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME,
                                        currentProject.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME,
                                        longNameCustomField.getText())));
                    }

                    if (descriptionTextArea.getText() != currentProject.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION,
                                        currentProject.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION,
                                        descriptionTextArea.getText())));
                    }
		    
		     for (Team team : tempProject.getTeams())
                    {
                        if (!currentProject.getTeams().contains(team))
                        {
                            undoActions.add(new UndoableItem(
                                    team,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, 
                                            team.getProject()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, 
                                            currentProject)));

                            undoActions.add(new UndoableItem(
                                    team,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_PROJECT, 
                                            team.getProject()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_PROJECT, 
                                            currentProject)));


			    if (team.getProject() != null)
			    {
				team.getProject().removeTeam(team, false);
			    }
                            team.setProject(currentProject);
                            currentProject.addTeam(team, false);
                        }
                    }
                    
                    for (Team team : dialogTeams)
                    {
                        if (!dialogTeamsCopy.contains(team))
                        {
                            undoActions.add(new UndoableItem(
                                    team,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, 
                                            team.getProject()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, 
                                            team.getProject())));

                            undoActions.add(new UndoableItem(
                                    team,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_PROJECT, 
                                            team.getProject()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.TEAM_PROJECT, 
                                            null)));
                                         
                            team.getProject().removeTeam(team, false);
                            team.setProject(null);
                        }
                    }

                    Global.undoRedoMan.add(new UndoableItem(
                            currentProject,
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
                                    undoActions),
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
                                    undoActions)
                    ));
                           

                    // Save the edits.
                    currentProject.setDescription(descriptionTextArea.getText());
                    currentProject.setShortName(shortNameCustomField.getText());
                    currentProject.setLongName(longNameCustomField.getText());
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    ProjectScene.getProjectScene(currentProject);
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getChildren().add(treeView);
                    App.content.getChildren().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);
                }
                else
                {
                    event.consume();
                }
            });

        return informationGrid;
    }
    
    private static void teamCheckDialog(Team team, Project tempProject) 
    {
        
        Dialog dialog = new Dialog(null, "Already Assigned to a Team");
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
        
        grid.getChildren().add(new Label("Are you sure you want to change teams?"));
        grid.getChildren().add(buttons);
        
        btnYes.setOnAction((event) ->
            {
                tempProject.addTeam(team, false);
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
    }
    
}
